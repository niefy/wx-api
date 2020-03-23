/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 *
 * 版权所有，侵权必究！
 */

package com.github.niefy.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.niefy.modules.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
@CacheNamespace(flushInterval = 300000L)//缓存五分钟过期
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(String token);
	
}
