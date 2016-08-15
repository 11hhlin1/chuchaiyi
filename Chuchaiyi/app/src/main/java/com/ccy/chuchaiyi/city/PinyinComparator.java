package com.ccy.chuchaiyi.city;

import java.util.Comparator;

/**
 * 用来对ListView中的数据根据A-Z进行排序，前面两个if判断主要是将不是以汉字开头的数据放在后面
 */
public class PinyinComparator implements Comparator<CitySort> {

    public int compare(CitySort o1, CitySort o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        String s1 = String.valueOf(o1.getPinyinShort().charAt(0));
        String s2 = String.valueOf(o2.getPinyinShort().charAt(0));
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
