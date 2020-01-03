/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 *
 * 版权所有，侵权必究！
 */

package com.github.niefy.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.niefy.modules.sys.enums.SysOperationEnum;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 添加系统日志到队列中，队列数据会定时批量插入到数据库
     * @param operation
     */
    public void addTaskLog(SysOperationEnum operation, String params);

    /**
     * 添加系统日志到队列中，队列数据会定时批量插入到数据库
     * @param operation
     */
    public void addLog(SysOperationEnum operation, String params);

}
