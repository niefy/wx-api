package com.github.niefy.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 公众号账号
 * 
 * @author niefy
 * @date 2020-06-17 13:56:51
 */
@Data
@TableName("wx_account")
public class WxAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.INPUT)
	@NotEmpty(message = "appid不得为空")
	private String appid;
	/**
	 * 公众号名称
	 */
	@NotEmpty(message = "名称不得为空")
	private String name;
	/**
	 * 账号类型
	 */
	private int type;
	/**
	 * 认证状态
	 */
	private boolean verified;
	/**
	 * appsecret
	 */
	@NotEmpty(message = "appSecret不得为空")
	private String secret;
	/**
	 * token
	 */
	private String token;
	/**
	 * aesKey
	 */
	private String aesKey;

	public WxMpDefaultConfigImpl toWxMpConfigStorage(){
		WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
		configStorage.setAppId(appid);
		configStorage.setSecret(secret);
		configStorage.setToken(token);
		configStorage.setAesKey(aesKey);
		return configStorage;
	}

}
