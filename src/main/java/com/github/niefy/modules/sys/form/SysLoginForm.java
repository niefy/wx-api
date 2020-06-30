package com.github.niefy.modules.sys.form;

import lombok.Data;

/**
 * 登录表单
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class SysLoginForm {
    private String username;
    private String password;
    private String captcha;
    private String uuid;


}
