package com.example.insight.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/11/1 上午11:25
 * @Version: 1.0
 */
public class NumberUtil {
    /**
     * 对入参保留最多两位小数(舍弃末尾的0)，如:
     * 3.345->3.34
     * 3.40->3.4
     * 3.0->3
     */
    public static String getNoMoreThanTwoDigits(double number) {
        DecimalFormat format = new DecimalFormat("0.##");
        //未保留小数的舍弃规则，RoundingMode.FLOOR表示直接舍弃。
        format.setRoundingMode(RoundingMode.FLOOR);
        return format.format(number);
    }
}
