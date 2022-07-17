package com.zpedroo.stockexchange.managers;

import com.zpedroo.stockexchange.utils.formatter.NumberFormatter;

public class StatisticManager {

    public static String getStatistics(int value1, int value2) {
        StringBuilder ret = new StringBuilder();

        if (value1 > value2) {
            ret.append("§a⬆");
        } else {
            ret.append("§c⬇");
        }

        double increase = value1 - value2;
        double divide = increase / value2;

        ret.append(NumberFormatter.getInstance().formatDecimal(divide * 100)).append("%");
        return ret.toString().replace("-", "");
    }
}