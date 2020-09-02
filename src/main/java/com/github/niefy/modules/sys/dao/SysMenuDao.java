package com.github.niefy.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.niefy.modules.sys.entity.SysMenuEntity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单管理
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
@CacheNamespace(flushInterval = 300000L)//缓存五分钟过期
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 获取用户所有的菜单
     * @param userId 用户id
     * @return
     */
    List<SysMenuEntity> queryUserAllMenu(Long userId);
}
