package com.ccy.chuchaiyi.order;

import com.ccy.chuchaiyi.flight.FlightInfo;
import com.ccy.chuchaiyi.flight.PolicyResultInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chuck on 2016/9/9.
 */
public class PlaceAskOrderRequest implements Serializable{
    private static final long serialVersionUID = 1310800849049010540L;
    public List<Passenger> Passengers;
    public String ContactName;
    public String ContactMobile;
    public String ContactEmail;
    public FlightInfo FirstRoute;
    public PolicyResultInfo FirstRoutePolicyInfo;
    public FlightInfo SecondRoute;
    public PolicyResultInfo SecondRoutePolicyInfo;
}
