package com.github.niefy.common.validator;

import com.github.niefy.common.exception.RRException;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据校验
 * @author Mark sunlightcs@gmail.com
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }
}
