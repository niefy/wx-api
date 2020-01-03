package com.github.niefy.common.utils;

import com.alibaba.fastjson.JSON;

public class Json {

	/**
	 * 对象序列化为JSON字符串
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object){
		return JSON.toJSONString(object);
	}

	public static <T> T fromJson(String jsonStr, Class<T> clazz){
		return JSON.parseObject(jsonStr, clazz);
	}
}
