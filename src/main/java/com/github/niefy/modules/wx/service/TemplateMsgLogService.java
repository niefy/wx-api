package com.github.niefy.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.modules.wx.entity.TemplateMsgLog;

import java.util.Map;

public interface TemplateMsgLogService extends IService<TemplateMsgLog> {
    /**
     * 分页查询用户数据
     * @param params 查询参数
     * @return PageUtils 分页结果
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 记录log，异步入库
     *
     * @param log
     */
    void addLog(TemplateMsgLog log);
}
