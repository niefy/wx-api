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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("msgTemplateService")
public class MsgTemplateServiceImpl extends ServiceImpl<MsgTemplateMapper, MsgTemplate> implements MsgTemplateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String)params.get("title");
        String name = (String)params.get("name");
        IPage<MsgTemplate> page = this.page(
                new Query<MsgTemplate>().getPage(params),
                new QueryWrapper<MsgTemplate>()
                        .like(!StringUtils.isEmpty(title),"title",title)
                        .like(!StringUtils.isEmpty(name),"name",name)
        );

        return new PageUtils(page);
    }

    @Override
    public MsgTemplate selectByName(String name) {
        Assert.isBlank(name,"模板名称不得为空");
        return this.getOne(new QueryWrapper<MsgTemplate>()
                .eq("name",name)
                .eq("status",1)
                .last("LIMIT 1"));
    }

}