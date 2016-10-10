package com.ccy.chuchaiyi.contact;

import com.ccy.chuchaiyi.city.PinyinUtils;

import java.util.Comparator;

/**
 * Created by Chuck on 2016/10/10.
 */
public class PassengerListComparator  implements Comparator<PassengerData> {

    public int compare(PassengerData o1, PassengerData o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        String s1 = o1.sortLetters.toUpperCase();
        String s2 = o2.sortLetters.toUpperCase();
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
}