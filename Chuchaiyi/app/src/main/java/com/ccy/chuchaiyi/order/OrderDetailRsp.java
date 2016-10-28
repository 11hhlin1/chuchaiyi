package com.ccy.chuchaiyi.order;

import com.ccy.chuchaiyi.check.AuthorizeDetailRsp;

import java.io.Serializable;

/**
 * Created by user on 16/9/15.
 */
public class OrderDetailRsp implements Serializable{
    private static final long serialVersionUID = 1939314599877043003L;
    public AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean Order;
}
