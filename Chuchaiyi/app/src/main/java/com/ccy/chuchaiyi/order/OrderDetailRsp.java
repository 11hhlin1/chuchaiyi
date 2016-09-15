package com.ccy.chuchaiyi.order;

import com.ccy.chuchaiyi.check.AuthorizeDetailRsp;

import java.io.Serializable;

/**
 * Created by user on 16/9/15.
 */
public class OrderDetailRsp implements Serializable{
    public AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean Order;
}
