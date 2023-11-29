package com.github.niefy.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import com.github.niefy.modules.wx.dao.WxAccountMapper;
import com.github.niefy.modules.wx.entity.WxAccount;
import com.github.niefy.modules.wx.service.WxAccountService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.redis.WxRedisOps;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@Service("wxAccountService")
@Slf4j
public class WxAccountServiceImpl extends ServiceImpl<WxAccountMapper, WxAccount> implements WxAccountService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxRedisOps wxRedisOps;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        IPage<WxAccount> page = this.page(
                new Query<WxAccount>().getPage(params),
                new QueryWrapper<WxAccount>()
                        .like(StringUtils.hasText(name), "name", name)
        );

        return new PageUtils(page);
    }

    @Override
    public void loadWxMpConfigStorages(){
        log.info("加载公众号配置...");
        List<WxAccount> accountList = this.list();
        if (accountList == null || accountList.isEmpty()) {
            log.info("未读取到公众号配置，请在管理后台添加");
            return;
        }
        log.info("加载到{}条公众号配置",accountList.size());
        accountList.forEach(this::addAccountToRuntime);
        log.info("公众号配置加载完成");
    }

    @Override
    public boolean saveOrUpdateWxAccount(WxAccount entity) {
        Assert.notNull(entity, "WxAccount不得为空");
        boolean saveOrUpdate = saveOrUpdate(entity);
        log.info(" saveOrUpdate {} appid: {} ", saveOrUpdate, entity.getAppid());
        this.addAccountToRuntime(entity);
        return saveOrUpdate;
    }

    @Override
    public boolean removeByIds(Collection<?> idList) {
        Assert.notEmpty(idList,"WxAccount不得为空");
        // 更新wxMpService配置
        log.info("同步移除公众号配置");
        idList.forEach(id-> wxMpService.removeConfigStorage((String) id));
        return SqlHelper.retBool(this.baseMapper.deleteBatchIds(idList));
    }

    /**
     * 判断当前账号是存在
     * @param appid
     * @return
     */
    private boolean isAccountInRuntime(String appid){
        try {
            return wxMpService.switchover(appid);
        }catch (NullPointerException e){// sdk bug，未添加任何账号时configStorageMap为null会出错
            return false;
        }
    }
    /**
     * 添加账号到当前程序，如首次添加需初始化configStorageMap
     * @param entity
     */
    private synchronized void addAccountToRuntime(WxAccount entity) {
        String appid = entity.getAppid();
        WxMpDefaultConfigImpl config = buildWxMpConfigImpl(entity);
        wxMpService.addConfigStorage(appid, config);
    }

    private WxMpDefaultConfigImpl buildWxMpConfigImpl(WxAccount entity) {
        WxMpRedisConfigImpl configStorage = new WxMpRedisConfigImpl(wxRedisOps, "v1:wx:mp");
        configStorage.setAppId(entity.getAppid());
        configStorage.setSecret(entity.getSecret());
        configStorage.setToken(entity.getToken());
        configStorage.setAesKey(entity.getAesKey());
        return configStorage;
    }


}