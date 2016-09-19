package com.ccy.chuchaiyi.event;

/**
 * Created by Chuck on 2016/8/12.
 */
public class EventOfSelDate {
    public static final int SET_OUT_DATE = -1;
    public static final int CHANGE_SET_OUT_DATE = -3;
    public static final int RETURN_DATE = -2;
    public static final int START_DATE = 1;
    public static final int END_DATE = 2;

//    public int mPage;
    //0 代表出发， 1代表返回
    public int mDateType;
    public String mDate;
}
