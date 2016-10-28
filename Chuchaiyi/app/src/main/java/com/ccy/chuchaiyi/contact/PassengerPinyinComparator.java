package com.ccy.chuchaiyi.contact;

import com.ccy.chuchaiyi.city.PinyinUtils;

import java.util.Comparator;

/**
 * Created by Chuck on 2016/9/2.
 */
public class PassengerPinyinComparator implements Comparator<PassengerInfo> {

    public int compare(PassengerInfo o1, PassengerInfo o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        String s1;
        if(isChinese(o1.getEmployeeName().charAt(0))) {
            s1 = String.valueOf(PinyinUtils.getAlpha(o1.getEmployeeName()).charAt(0)).toUpperCase();
        } else {
            s1 = String.valueOf(o1.getEmployeeName().charAt(0)).toUpperCase();
        }
        String s2;
        if(isChinese(o2.getEmployeeName().charAt(0))) {
            s2 = String.valueOf(PinyinUtils.getAlpha(o2.getEmployeeName()).charAt(0)).toUpperCase();
        } else {
            s2 = String.valueOf(o2.getEmployeeName().charAt(0)).toUpperCase();
        }
//        String s2 = String.valueOf(PinyinUtils.getAlpha(o2.getEmployeeName()).charAt(0));
        if (s1.equals("@")
                || s2.equals("#")) {
            return -1;
        } else if (s1.equals("#")
                || s2.equals("@")) {
            return 1;
        } else {
            return s1.compareTo(s2);
        }
    }

    public static boolean isChinese(char c) {
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
    }
}
