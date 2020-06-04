package com.github.niefy.modules.wx.controller;

import com.github.niefy.common.utils.*;
import com.github.niefy.modules.sys.service.SysLogService;
import com.github.niefy.modules.wx.form.CodeToOpenidForm;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 微信网页授权相关
 */
@RestController
@RequestMapping("/wxAuth")
@RequiredArgsConstructor
public class WxAuthController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SysLogService sysLogService;
    private final WxMpService wxService;
    @Value("${wx.mp.configs[0].appid}")
    private String appId;

    @GetMapping("/getCode")
    @CrossOrigin
    public String getCode(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(required = false) String state,
                          @RequestParam(required = false) String code,
                          @RequestParam(required = false) String redirect) throws Exception {
        logger.info("获取微信授权code,redirect=" + redirect);
        logger.info("获取微信授权code,state=" + state);
        if (StringUtils.isEmpty(code) && !StringUtils.isEmpty(redirect)) {
            String wxClientOrigin = request.getHeader(Constant.WX_CLIENT_ORIGIN_HEADER);
            if(StringUtils.isEmpty(wxClientOrigin))
                return "header中缺少参数："+Constant.WX_CLIENT_ORIGIN_HEADER;
            String authUrl = wxService.oauth2buildAuthorizationUrl(wxClientOrigin + request.getRequestURI(), WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(redirect, "utf-8"));
            logger.info("获取微信授权code,重定向到" + authUrl);
            response.sendRedirect(authUrl);
            return null;
        } else if (!StringUtils.isEmpty(code) && Pattern.matches("[a-zA-z]+://[^\\s]*", state)) {
            String returnUrl = URIUtil.appendUri(state, "code=" + code);
            logger.info("授权完成，重定向到：" + returnUrl);
            response.sendRedirect(returnUrl);
            return null;
        }
        return "parameters error";

    }


    /**
     * 使用微信授权code换取openid
     *
     * @param request
     * @param response
     * @param form
     * @return
     */
    @PostMapping("/codeToOpenid")
    @CrossOrigin
    public R codeToOpenid(HttpServletRequest request, HttpServletResponse response, @RequestBody CodeToOpenidForm form) {
        String code = form.getCode();
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
            String openid = wxMpOAuth2AccessToken.getOpenId();
            CookieUtil.setCookie(response, "openid", openid, 365 * 24 * 60 * 60);
            String openidToken = MD5Util.getMD5AndSalt(openid);
            CookieUtil.setCookie(response, "openidToken", openidToken, 365 * 24 * 60 * 60);
            return R.ok().put(openid);
        } catch (WxErrorException e) {
            logger.error("code换取openid失败", e);
            return R.error(e.getError().getErrorMsg());
        }

    }

    /**
     * 获取微信分享的签名配置
     * 允许跨域（只有微信公众号添加了js安全域名的网站才能加载微信分享，故这里不对域名进行校验）
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/getShareSignature")
    public R getShareSignature(HttpServletRequest request, HttpServletResponse response) throws WxErrorException {
        // 1.拼接url（当前网页的URL，不包含#及其后面部分）
        String wxShareUrl = request.getHeader(Constant.WX_CLIENT_HREF_HEADER);
        if (StringUtils.isEmpty(wxShareUrl))
            return R.error("header中缺少"+Constant.WX_CLIENT_HREF_HEADER+"参数，微信分享加载失败");
        wxShareUrl = wxShareUrl.split("#")[0];
        Map<String, String> wxMap = new TreeMap<>();
        String wxNoncestr = UUID.randomUUID().toString();
        String wxTimestamp = (System.currentTimeMillis() / 1000) + "";
        wxMap.put("noncestr", wxNoncestr);
        wxMap.put("timestamp", wxTimestamp);
        wxMap.put("jsapi_ticket", wxService.getJsapiTicket());
        wxMap.put("url", wxShareUrl);

        // 加密获取signature
        StringBuilder wxBaseString = new StringBuilder();
        wxMap.forEach((key, value) -> wxBaseString.append(key).append("=").append(value).append("&"));
        String wxSignString = wxBaseString.substring(0, wxBaseString.length() - 1);
        // signature
        String wxSignature = SHA1Util.sha1(wxSignString);
        Map<String, String> resMap = new TreeMap<>();
        resMap.put("appId", appId);
        resMap.put("wxTimestamp", wxTimestamp);
        resMap.put("wxNoncestr", wxNoncestr);
        resMap.put("wxSignature", wxSignature);
        return R.ok().put(resMap);
    }
}
