package com.github.niefy.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.modules.wx.dao.MsgTemplateMapper;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import com.github.niefy.modules.wx.entity.MsgTemplate;
import com.github.niefy.modules.wx.service.MsgTemplateService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("msgTemplateService")
@Slf4j
public class MsgTemplateServiceImpl extends ServiceImpl<MsgTemplateMapper, MsgTemplate> implements MsgTemplateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String) params.get("title");
        String name = (String) params.get("name");
        String appid = (String) params.get("appid");
        IPage<MsgTemplate> page = this.page(
            new Query<MsgTemplate>().getPage(params),
            new QueryWrapper<MsgTemplate>()
                .eq(StringUtils.hasText(appid), "appid", appid)
                .like(StringUtils.hasText(title), "title", title)
                .like(StringUtils.hasText(name), "name", name)
        );

        return new PageUtils(page);
    }

    @Override
    public MsgTemplate selectByName(String name) {
        Assert.hasText(name, "模板名称不得为空");
        return this.getOne(new QueryWrapper<MsgTemplate>()
            .eq("name", name)
            .eq("status", 1)
            .last("LIMIT 1"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void syncWxTemplate(String appid, List<WxMpTemplate> allPrivateTemplateList) throws WxErrorException {
        List<MsgTemplate> templates = allPrivateTemplateList.stream().map(item -> new MsgTemplate(item, appid)).collect(Collectors.toList());
        List<String> templateIds = templates.stream().map(MsgTemplate::getTemplateId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(templateIds)) {
            LambdaQueryWrapper<MsgTemplate> qryRoomUser = Wrappers.lambdaQuery();
            qryRoomUser.in(MsgTemplate::getTemplateId, templateIds);
            this.remove(qryRoomUser);
            this.saveBatch(templates);
            return;
        }
        log.info("没有模板 appid {} ", appid);
    }

}
