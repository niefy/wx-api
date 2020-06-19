package com.github.niefy.modules.wx.manage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.niefy.modules.wx.entity.WxUser;
import com.github.niefy.modules.wx.service.WxUserService;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.R;


/**
 * 用户表
 *
 * @author niefy
 * @email niefy@qq.com
 * @date 2020-03-07 13:55:23
 */
@RestController
@RequestMapping("/manage/wxUser")
public class WxUserManageController {
    @Autowired
    private WxUserService userService;
    @Autowired
    private WxMpService wxMpService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("wx:wxuser:list")
    public R list(@CookieValue String appid,@RequestParam Map<String, Object> params) {
        params.put("appid",appid);
        PageUtils page = new PageUtils(userService.queryPage(params));

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @PostMapping("/listByIds")
    @RequiresPermissions("wx:wxuser:list")
    public R listByIds(@CookieValue String appid,@RequestBody String[] openids){
        List<WxUser> users = userService.listByIds(Arrays.asList(openids));
        return R.ok().put(users);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{openid}")
    @RequiresPermissions("wx:wxuser:info")
    public R info(@CookieValue String appid,@PathVariable("openid") String openid) {
        WxUser WxUser = userService.getById(openid);

        return R.ok().put("WxUser", WxUser);
    }

    /**
     * 同步用户列表
     */
    @PostMapping("/syncWxUsers")
    @RequiresPermissions("wx:wxuser:save")
    public R syncWxUsers(@CookieValue String appid) {
        wxMpService.switchoverTo(appid);
        userService.syncWxUsers(appid);

        return R.ok("任务已建立");
    }



    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("wx:wxuser:delete")
    public R delete(@CookieValue String appid,@RequestBody String[] ids) {
        userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
