package com.github.niefy.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.modules.wx.dao.MsgTemplateMapper;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import com.github.niefy.common.validator.Assert;
import com.github.niefy.modules.wx.entity.MsgTemplate;
import com.github.niefy.modules.wx.service.MsgTemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("msgTemplateService")
public class MsgTemplateServiceImpl extends ServiceImpl<MsgTemplateMapper, MsgTemplate> implements MsgTemplateService {
    @Autowired
    private WxMpService wxService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String) params.get("title");
        String name = (String) params.get("name");
        String appid = (String) params.get("appid");
        IPage<MsgTemplate> page = this.page(
            new Query<MsgTemplate>().getPage(params),
            new QueryWrapper<MsgTemplate>()
                .eq(!StringUtils.isEmpty(appid), "appid", appid)
                .like(!StringUtils.isEmpty(title), "title", title)
                .like(!StringUtils.isEmpty(name), "name", name)
        );

        return new PageUtils(page);
    }

    @Override
    public MsgTemplate selectByName(String name) {
        Assert.isBlank(name, "模板名称不得为空");
        return this.getOne(new QueryWrapper<MsgTemplate>()
            .eq("name", name)
            .eq("status", 1)
            .last("LIMIT 1"));
    }

    @Override
    public void syncWxTemplate(String appid) throws WxErrorException {
        List<WxMpTemplate> wxMpTemplateList= wxService.getTemplateMsgService().getAllPrivateTemplate();
        List<MsgTemplate> templates = wxMpTemplateList.stream().map(item->new MsgTemplate(item,appid)).collect(Collectors.toList());
        this.saveBatch(templates);
    }

}
