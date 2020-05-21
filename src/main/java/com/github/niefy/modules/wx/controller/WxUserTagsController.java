package com.github.niefy.modules.wx.controller;

import com.github.niefy.common.utils.R;
import com.github.niefy.modules.wx.entity.WxUser;
import com.github.niefy.modules.wx.form.WxUserTaggingForm;
import com.github.niefy.modules.wx.service.WxUserService;
import com.github.niefy.modules.wx.service.WxUserTagsService;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wxUserTags")
public class WxUserTagsController {
    @Autowired
    WxUserTagsService wxUserTagsService;
    @Autowired
    WxUserService wxUserService;

    @GetMapping("/userTags")
    public R userTags(@CookieValue String openid){
        if(openid==null){
            return R.error("none_openid");
        }
        WxUser wxUser = wxUserService.getById(openid);
        if(wxUser==null){
            wxUser=wxUserService.refreshUserInfo(openid);
            if(wxUser==null)
                return R.error("not_subscribed");
        }
        return R.ok().put(wxUser.getTagidList());
    }

    @PostMapping("/tagging")
    public R tagging(@CookieValue String openid , @RequestBody WxUserTaggingForm form) throws WxErrorException {
        try {
            wxUserTagsService.tagging(form.getTagid(),openid);
        }catch (WxErrorException e){
            WxError error = e.getError();
            if(50005==error.getErrorCode()){//未关注公众号
                return R.error("not_subscribed");
            }else {
                return R.error(error.getErrorMsg());
            }
        }
        return R.ok();
    }

    @PostMapping("/untagging")
    public R untagging(@CookieValue String openid , @RequestBody WxUserTaggingForm form) throws WxErrorException {
        wxUserTagsService.untagging(form.getTagid(),openid);
        return R.ok();
    }
}
