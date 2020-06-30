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
    /**
     * 分页查询用户数据
     * @param params 查询参数
     * @return PageUtils 分页结果
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存配置信息
     */
    void saveConfig(SysConfigEntity config);

    /**
     * 更新配置信息
     */
    void update(SysConfigEntity config);

    /**
     * 根据key，更新value
     */
    void updateValueByKey(String key, String value);

    /**
     * 删除配置信息
     */
    void deleteBatch(Long[] ids);

    /**
     * 根据key，获取配置的value值
     * @param key           key
     */
    String getValue(String key);
    
	/**
	 * 根据key，获取配置的SysConfig信息
	 *
	 * @param key           key
	 */
    SysConfigEntity getSysConfig(String key);

    /**
     * 根据key，获取value的Object对象
     * @param key    key
     * @param clazz  Object对象
     */
    <T> T getConfigObject(String key, Class<T> clazz);

}
