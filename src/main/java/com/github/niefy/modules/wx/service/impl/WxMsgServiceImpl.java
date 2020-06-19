package com.github.niefy.modules.wx.service.impl;

import com.github.niefy.modules.wx.entity.TemplateMsgLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;

import com.github.niefy.modules.wx.dao.WxMsgMapper;
import com.github.niefy.modules.wx.entity.WxMsg;
import com.github.niefy.modules.wx.service.WxMsgService;


@Service("wxMsgService")
public class WxMsgServiceImpl extends ServiceImpl<WxMsgMapper, WxMsg> implements WxMsgService {
    /**
     * 未保存的队列
     */
    private static final ConcurrentLinkedQueue<WxMsg> logsQueue = new ConcurrentLinkedQueue<>();

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String msgTypes = (String)params.get("msgTypes");
        String startTime = (String)params.get("startTime");
        String openid = (String)params.get("openid");
        String appid = (String) params.get("appid");
        IPage<WxMsg> page = this.page(
                new Query<WxMsg>().getPage(params),
                new QueryWrapper<WxMsg>()
                        .eq(!org.springframework.util.StringUtils.isEmpty(appid), "appid", appid)
                        .in(StringUtils.isNotEmpty(msgTypes),"msg_type", Arrays.asList(msgTypes.split(",")))
                        .eq(StringUtils.isNotEmpty(openid),"openid",openid)
                        .ge(StringUtils.isNotEmpty(startTime),"create_time",startTime)
        );

        return new PageUtils(page);
    }

    /**
     * 添加访问log到队列中，队列数据会定时批量插入到数据库
     * @param log
     */
    @Override
    public void addWxMsg(WxMsg log) {
        logsQueue.offer(log);
    }

    /**
     * 定时将日志插入到数据库
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    synchronized void batchAddLog(){
        List<WxMsg> logs = new ArrayList<>();
        while (!logsQueue.isEmpty()){
            logs.add(logsQueue.poll());
        }
        if(!logs.isEmpty()){
            this.saveBatch(logs);
        }
    }

}