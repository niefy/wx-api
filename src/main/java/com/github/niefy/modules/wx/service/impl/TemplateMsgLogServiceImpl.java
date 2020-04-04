package com.github.niefy.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.modules.wx.dao.TemplateMsgLogMapper;
import com.github.niefy.modules.wx.entity.TemplateMsgLog;
import com.github.niefy.modules.wx.service.TemplateMsgLogService;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TemplateMsgLogServiceImpl extends ServiceImpl<TemplateMsgLogMapper, TemplateMsgLog> implements TemplateMsgLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TemplateMsgLog> page = this.page(
            new Query<TemplateMsgLog>().getPage(params),
            new QueryWrapper<TemplateMsgLog>()
        );

        return new PageUtils(page);
    }

    /**
     * 未保存的队列
     */
    private static ConcurrentLinkedQueue<TemplateMsgLog> logsQueue = new ConcurrentLinkedQueue<>();

    /**
     * 添加访问log到队列中，队列数据会定时批量插入到数据库
     *
     * @param log
     */
    @Override
    public void addLog(TemplateMsgLog log) {
        logsQueue.offer(log);
    }

    /**
     * 定时将日志插入到数据库
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    synchronized void batchAddLog() {
        List<TemplateMsgLog> logs = new ArrayList<>();
        while (!logsQueue.isEmpty()) {
            logs.add(logsQueue.poll());
        }
        if (!logs.isEmpty()) {
            this.saveBatch(logs);
        }
    }
}
