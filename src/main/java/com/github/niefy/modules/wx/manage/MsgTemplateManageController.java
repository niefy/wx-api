package com.github.niefy.modules.wx.manage;

import java.util.Arrays;
import java.util.Map;

import com.github.niefy.modules.wx.entity.MsgTemplate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.niefy.modules.wx.service.MsgTemplateService;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.R;



/**
 * 消息模板
 *
 * @author niefy
 * @email niefy@qq.com
 * @date 2019-11-12 18:30:15
 */
@RestController
@RequestMapping("/manage/msgtemplate")
public class MsgTemplateManageController {
    @Autowired
    private MsgTemplateService msgTemplateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wx:msgtemplate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = msgTemplateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("wx:msgtemplate:info")
    public R info(@PathVariable("id") Long id){
		MsgTemplate msgTemplate = msgTemplateService.getById(id);

        return R.ok().put("msgTemplate", msgTemplate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wx:msgtemplate:save")
    public R save(@RequestBody MsgTemplate msgTemplate){
		msgTemplateService.save(msgTemplate);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wx:msgtemplate:update")
    public R update(@RequestBody MsgTemplate msgTemplate){
		msgTemplateService.updateById(msgTemplate);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wx:msgtemplate:delete")
    public R delete(@RequestBody String[] ids){
		msgTemplateService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
