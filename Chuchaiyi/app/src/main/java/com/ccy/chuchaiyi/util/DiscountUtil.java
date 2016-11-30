package com.ccy.chuchaiyi.util;

/**
 * Created by user on 16/9/11.
 */
public class DiscountUtil {
    public static String getDis(int discount) {
        float dis = discount / 10.0f;
        if(dis >= 10) {
            return "全价";
        } else {
            return dis + "折";
        }
    }
}
