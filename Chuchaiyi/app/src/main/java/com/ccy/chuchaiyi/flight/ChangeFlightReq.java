package com.ccy.chuchaiyi.flight;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chuck on 2016/9/21.
 */
public class ChangeFlightReq implements Serializable{
    private static final long serialVersionUID = -5734300171447720542L;

    public int SrcOrderId;
    public String ChangeReason;
    public int ChangeDifferencePrice;
    public FlightInfo ChangeRoute;




}
