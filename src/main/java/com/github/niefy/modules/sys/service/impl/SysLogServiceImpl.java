/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * 版权所有，侵权必究！
 */

package com.github.niefy.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import com.github.niefy.modules.sys.dao.SysLogDao;
import com.github.niefy.modules.sys.entity.SysLogEntity;
import com.github.niefy.modules.sys.service.SysLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {
    /**
     * 未保存的日志队列
     */
    private static final ConcurrentLinkedQueue<SysLogEntity> SysLogsQueue = new ConcurrentLinkedQueue<>();

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");

        IPage<SysLogEntity> page = this.page(
            new Query<SysLogEntity>().getPage(params),
            new QueryWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key), "username", key)
        );

        return new PageUtils(page);
    }
}
