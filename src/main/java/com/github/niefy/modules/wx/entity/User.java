package com.github.niefy.modules.wx.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.github.niefy.common.utils.Json;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Nifury
 * @date 2017-9-27
 */
@TableName("wx_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.INPUT)
	private String openid;
	private String phone;
	private String nickname;
	private int sex;
	private String city;
	private String province;
	private String headimgurl;
	@JSONField(name="subscribe_time")
    private Date subscribeTime;
	private boolean subscribe;

    public User() {
    }
    public User(String openid) {
        this.openid = openid;
    }

	public User(WxMpUser userWxInfo) {
		this.openid = userWxInfo.getOpenId();
		this.nickname = userWxInfo.getNickname();
		this.sex = userWxInfo.getSex();
		this.city = userWxInfo.getCity();
		this.province = userWxInfo.getProvince();
		this.headimgurl = userWxInfo.getHeadImgUrl();
		this.subscribeTime = new Date(userWxInfo.getSubscribeTime());
	}

	@Override
	public String toString() {
		return Json.toJsonString(this);
	}
	
	public String getPhone() {
		return phone;
    }
	public void setPhone(String phone) {
			this.phone = phone;
    }
	public String getNickname() {
		return nickname;
    }
	public void setNickname(String nickname) {
			this.nickname = nickname;
    }
	public int getSex() {
		return sex;
    }
	public void setSex(int sex) {
			this.sex = sex;
    }
	public String getCity() {
		return city;
    }
	public void setCity(String city) {
			this.city = city;
    }
	public String getProvince() {
		return province;
    }
	public void setProvince(String province) {
			this.province = province;
    }
	public String getHeadimgurl() {
		return headimgurl;
    }
	public void setHeadimgurl(String headimgurl) {
			this.headimgurl = headimgurl;
    }
	public String getOpenid() {
		return openid;
    }
	public void setOpenid(String openid) {
			this.openid = openid;
    }
	public Date getSubscribeTime() {
		return subscribeTime;
    }
	public void setSubscribeTime(Date subscribeTime) {
			this.subscribeTime = subscribeTime;
    }

	public boolean isSubscribe() {
		return subscribe;
	}

	public void setSubscribe(boolean subscribe) {
		this.subscribe = subscribe;
	}
}