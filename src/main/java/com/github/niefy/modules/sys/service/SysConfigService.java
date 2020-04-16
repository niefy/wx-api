/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * 版权所有，侵权必究！
 */

package com.github.niefy.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.niefy.modules.sys.entity.SysConfigEntity;
import com.github.niefy.common.utils.PageUtils;

import java.util.Map;

/**
 * 系统配置信息
 * @author Mark sunlightcs@gmail.com
 */
public interface SysConfigService extends IService<SysConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存配置信息
     */
    public void saveConfig(SysConfigEntity config);

    /**
     * 更新配置信息
     */
    public void update(SysConfigEntity config);

    /**
     * 根据key，更新value
     */
    public void updateValueByKey(String key, String value);

    /**
     * 删除配置信息
     */
    public void deleteBatch(Long[] ids);

    /**
     * 根据key，获取配置的value值
     * @param key           key
     */
    public String getValue(String key);
    
	/**
	 * 根据key，获取配置的SysConfig信息
	 *
	 * @param key           key
	 */
	public SysConfigEntity getSysConfig(String key);

    /**
     * 根据key，获取value的Object对象
     * @param key    key
     * @param clazz  Object对象
     */
    public <T> T getConfigObject(String key, Class<T> clazz);

}
