package com.ds.aix.common.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ds
 * @date 2023/4/15
 * @description 日期工具
 */
public final class DateUtils extends DateUtil {

    private DateUtils() {
        throw new AssertionError("No com.ds.nas.lib.common.util.DateUtils instances for you!");
    }

    /**
     * 获取当前日期到前几天的日期集合
     *
     * @param days 天数
     * @return
     */
    public static Set<String> getPreDays(int days) {
        Set<String> result = new HashSet<>(days);
        result.add(DateUtils.today());
        for (int i = 1; i <= days; i++) {
            DateTime offset = DateUtils.offset(new Date(), DateField.DAY_OF_YEAR, -i);
            result.add(offset.toDateStr());
        }
        return result;
    }

    /**
     * 获取当前日期到后几天的日期集合
     *
     * @param days 天数
     * @return
     */
    public static Set<String> getAfterDays(int days) {
        Set<String> result = new HashSet<>(days);
        result.add(DateUtils.today());
        for (int i = 1; i <= days; i++) {
            DateTime offset = DateUtils.offset(new Date(), DateField.DAY_OF_YEAR, i);
            result.add(offset.toDateStr());
        }
        return result;
    }

}
