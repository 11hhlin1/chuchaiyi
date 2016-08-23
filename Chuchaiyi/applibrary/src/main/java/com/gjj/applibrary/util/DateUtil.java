package com.gjj.applibrary.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Chuck on 2016/8/23.
 */
public class DateUtil {

    /**
     *
     * @param date 2011-03-06
     * @return
     */
    public static String getDateTitle(String date) {
        String[] dates = date.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]) - 1, Integer.valueOf(dates[2]));
        StringBuilder dateTitle = Util.getThreadSafeStringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        dateTitle.append(simpleDateFormat.format(calendar.getTime()));
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekDay == Calendar.MONDAY) {
            dateTitle.append(" 周一");
        } else if (weekDay == Calendar.TUESDAY) {
            dateTitle.append(" 周二");
        } else if (weekDay == Calendar.WEDNESDAY) {
            dateTitle.append(" 周三");
        } else if (weekDay == Calendar.THURSDAY) {
            dateTitle.append(" 周四");
        } else if (weekDay == Calendar.FRIDAY) {
            dateTitle.append(" 周五");
        } else if (weekDay == Calendar.SATURDAY) {
            dateTitle.append(" 周六");
        } else if (weekDay == Calendar.SUNDAY) {
            dateTitle.append(" 周日");
        }
        return dateTitle.toString();
    }
}
