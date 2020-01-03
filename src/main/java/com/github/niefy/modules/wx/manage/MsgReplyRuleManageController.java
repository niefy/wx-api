package com.github.niefy.modules.wx.manage;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

import com.github.niefy.modules.wx.service.MsgReplyRuleService;
import com.github.niefy.modules.wx.dto.RegexConstant;
import me.chanjar.weixin.common.api.WxConsts;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.niefy.modules.wx.entity.MsgReplyRule;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.R;



/**
 * 自动回复规则
 *
 * @author niefy
 * @email niefy@qq.com
 * @date 2019-11-12 18:30:15
 */
@RestController
@RequestMapping("/manage/msgreplyrule")
public class MsgReplyRuleManageController {
    @Autowired
    private MsgReplyRuleService msgReplyRuleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wx:msgreplyrule:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = msgReplyRuleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{ruleId}")
    @RequiresPermissions("wx:msgreplyrule:info")
    public R info(@PathVariable("ruleId") Integer ruleId){
		MsgReplyRule msgReplyRule = msgReplyRuleService.getById(ruleId);

        return R.ok().put("msgReplyRule", msgReplyRule);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wx:msgreplyrule:save")
    public R save(@RequestBody MsgReplyRule msgReplyRule){
        if(WxConsts.KefuMsgType.NEWS.equals(msgReplyRule.getReplyType()) &&
                !Pattern.matches(RegexConstant.NUMBER_ARRAY, msgReplyRule.getReplyContent())){
            return R.error("图文消息ID格式不正确");
        }
		msgReplyRuleService.save(msgReplyRule);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wx:msgreplyrule:update")
    public R update(@RequestBody MsgReplyRule msgReplyRule){
		msgReplyRuleService.updateById(msgReplyRule);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wx:msgreplyrule:delete")
    public R delete(@RequestBody Integer[] ruleIds){
		msgReplyRuleService.removeByIds(Arrays.asList(ruleIds));

        return R.ok();
    }

}
