package com.ccy.chuchaiyi.index;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by user on 16/8/18.
 */
public class PriceComparator implements Comparator<FlightInfo>{
    @Override
    public int compare(FlightInfo lhs, FlightInfo rhs) {
        int left = FlightsListAdapter.getMinPrice(lhs.getBunks());
        int right = FlightsListAdapter.getMinPrice(rhs.getBunks());
        if(left > right){
            return 1;
        } else if(left < right){
            return -1;
        }
        return 0;
    }
}
