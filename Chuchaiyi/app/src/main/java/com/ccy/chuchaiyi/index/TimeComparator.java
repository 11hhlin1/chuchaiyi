package com.ccy.chuchaiyi.index;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by user on 16/8/18.
 */
public class TimeComparator implements Comparator<FlightInfo>{
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Override
    public int compare(FlightInfo lhs, FlightInfo rhs) {
        try {
            Date left = simpleDateFormat.parse(lhs.getDeparture().getDateTime());
            Date right = simpleDateFormat.parse(rhs.getDeparture().getDateTime());
            return left.compareTo(right);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
