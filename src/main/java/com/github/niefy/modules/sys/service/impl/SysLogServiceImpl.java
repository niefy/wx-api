/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 *
 * 版权所有，侵权必究！
 */

package com.github.niefy.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.common.utils.IPUtils;
import com.github.niefy.modules.sys.dao.SysLogDao;
import com.github.niefy.modules.sys.enums.SysOperationEnum;
import com.github.niefy.common.utils.CookieUtil;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import com.github.niefy.modules.sys.entity.SysLogEntity;
import com.github.niefy.modules.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {
    /**
     * 未保存的日志队列
     */
    private static ConcurrentLinkedQueue<SysLogEntity> SysLogsQueue = new ConcurrentLinkedQueue<>();
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        IPage<SysLogEntity> page = this.page(
            new Query<SysLogEntity>().getPage(params),
            new QueryWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key),"username", key)
        );

        return new PageUtils(page);
    }
    /**
     * 添加系统日志到队列中，队列数据会定时批量插入到数据库
     * @param operation
     */
    public void addTaskLog(SysOperationEnum operation, String params) {
        SysLogEntity sysLog = new SysLogEntity(null,operation,params,null);
        SysLogsQueue.offer(sysLog);
    }
    /**
     * 添加系统日志到队列中，队列数据会定时批量插入到数据库
     * @param operation
     */
    public void addLog(SysOperationEnum operation, String params) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String openid = CookieUtil.getCookieValue(request,"openid");
        String ip = IPUtils.getIpAddr(request);
        SysLogEntity sysLog = new SysLogEntity(openid,operation,params,ip);
        SysLogsQueue.offer(sysLog);
    }

    /**
     * 定时将日志插入到数据库
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    synchronized void batchAddSysLog(){
        List<SysLogEntity> logs = new ArrayList<>();
        while (!SysLogsQueue.isEmpty()){
            logs.add(SysLogsQueue.poll());
        }
        if(!logs.isEmpty()){
            this.saveBatch(logs);
        }
    }
}
