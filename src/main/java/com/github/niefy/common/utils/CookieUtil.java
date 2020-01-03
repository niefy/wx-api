package com.github.niefy.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 */
public class CookieUtil {
	
	/**
	 * 设置cookie
	 * @param maxAge 秒
	 */
	public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue,
                                 int maxAge) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		//cookie.setDomain(domain);
		maxAge = maxAge > 0 ? maxAge : 0;
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void clearCookie(HttpServletResponse response, String cookieName, String domain) {
		Cookie cookie = new Cookie(cookieName, "");
		cookie.setMaxAge(0);
		cookie.setDomain(domain);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void refreshCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                     String domain, int maxAge) {
		Cookie cookie = getCookie(request, cookieName);
		if (cookie != null) {
			cookie.setMaxAge(maxAge);
			cookie.setDomain(domain);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie cookie = getCookie(request, cookieName);
		if (cookie != null) {
			return cookie.getValue();
		}
		return null;
	}

	private static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookieName.equals(cookies[i].getName())) {
					return cookies[i];
				}
			}
		}
		return null;
	}
}
