package com.github.niefy.modules.wx.manage;

import com.github.niefy.common.utils.R;
import com.github.niefy.modules.wx.config.EventMessageListenerContainerConfig;
import com.github.niefy.modules.wx.entity.WxAccount;
import com.github.niefy.modules.wx.service.WxAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;



/**
 * 公众号账号
 *
 * @author niefy
 * @date 2020-06-17 13:56:51
 */
@RestController
@RequestMapping("/manage/wxAccount")
@Api(tags = {"公众号账号-管理后台"})
public class WxAccountManageController {

    @Autowired
    private WxAccountService wxAccountService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("wx:wxaccount:list")
    @ApiOperation(value = "列表")
    public R list(){
        List<WxAccount> list = wxAccountService.list();

        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{appid}")
    @RequiresPermissions("wx:wxaccount:info")
    @ApiOperation(value = "详情")
    public R info(@PathVariable("appid") String appid){
		WxAccount wxAccount = wxAccountService.getById(appid);
        return R.ok().put("wxAccount", wxAccount);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("wx:wxaccount:save")
    @ApiOperation(value = "保存")
    public R save(@RequestBody WxAccount wxAccount){
		wxAccountService.saveOrUpdateWxAccount(wxAccount);
        redisTemplate.convertAndSend(EventMessageListenerContainerConfig.WX_ACCOUNT_UPDATE, wxAccount.getAppid());
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("wx:wxaccount:delete")
    @ApiOperation(value = "删除")
    public R delete(@RequestBody String[] appids){
		wxAccountService.removeByIds(Arrays.asList(appids));

        return R.ok();
    }

}
