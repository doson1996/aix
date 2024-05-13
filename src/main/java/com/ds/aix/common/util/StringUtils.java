package com.ds.aix.common.util;

import cn.hutool.core.util.StrUtil;

/**
 * @author ds
 * @date 2023/1/14
 * @description 字符串工具类
 */
public final class StringUtils extends StrUtil {

    private StringUtils() {
        throw new AssertionError("No com.ds.nas.lib.common.util.StringUtils instances for you!");
    }

    /**
     * 多个字符串中是否为空白的字符串
     * @param str 被检测的字符串
     * @return 若有一个字符串为空白，则返回 true
     */
    public static boolean isBlank(CharSequence... str) {
        for (CharSequence charSequence : str) {
            if (isBlank(charSequence))
                return true;
        }
        return false;
    }

}
