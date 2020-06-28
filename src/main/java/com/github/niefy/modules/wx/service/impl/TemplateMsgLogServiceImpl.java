package com.github.niefy.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import com.github.niefy.modules.wx.dao.TemplateMsgLogMapper;
import com.github.niefy.modules.wx.entity.TemplateMsgLog;
import com.github.niefy.modules.wx.service.TemplateMsgLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class TemplateMsgLogServiceImpl extends ServiceImpl<TemplateMsgLogMapper, TemplateMsgLog> implements TemplateMsgLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String appid = (String) params.get("appid");
        IPage<TemplateMsgLog> page = this.page(
            new Query<TemplateMsgLog>().getPage(params),
            new QueryWrapper<TemplateMsgLog>()
                    .eq(!StringUtils.isEmpty(appid), "appid", appid)
        );

        return new PageUtils(page);
    }

    /**
     * 记录log，异步入库
     *
     * @param log
     */
    @Override
    @Async
    public void addLog(TemplateMsgLog log) {
        this.baseMapper.insert(log);
    }
}
