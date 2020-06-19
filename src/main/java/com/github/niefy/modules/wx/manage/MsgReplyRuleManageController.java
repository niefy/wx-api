package com.github.niefy.modules.wx.manage;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

import com.github.niefy.modules.wx.service.MsgReplyRuleService;
import com.github.niefy.modules.wx.dto.RegexConstant;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/manage/msgReplyRule")
public class MsgReplyRuleManageController {
    @Autowired
    private MsgReplyRuleService msgReplyRuleService;
    @Autowired
    private WxMpService wxMpService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("wx:msgreplyrule:list")
    public R list(@CookieValue String appid,@RequestParam Map<String, Object> params) {
        params.put("appid",appid);
        PageUtils page = msgReplyRuleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{ruleId}")
    @RequiresPermissions("wx:msgreplyrule:info")
    public R info(@PathVariable("ruleId") Integer ruleId) {
        MsgReplyRule msgReplyRule = msgReplyRuleService.getById(ruleId);

        return R.ok().put("msgReplyRule", msgReplyRule);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("wx:msgreplyrule:save")
    public R save(@RequestBody MsgReplyRule msgReplyRule) {
        msgReplyRuleService.save(msgReplyRule);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("wx:msgreplyrule:update")
    public R update(@RequestBody MsgReplyRule msgReplyRule) {
        msgReplyRuleService.updateById(msgReplyRule);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("wx:msgreplyrule:delete")
    public R delete(@RequestBody Integer[] ruleIds) {
        msgReplyRuleService.removeByIds(Arrays.asList(ruleIds));

        return R.ok();
    }

}
