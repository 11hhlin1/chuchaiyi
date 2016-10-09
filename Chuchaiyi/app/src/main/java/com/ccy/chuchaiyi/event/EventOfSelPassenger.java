package com.ccy.chuchaiyi.event;

import com.ccy.chuchaiyi.contact.PassengerInfo;
import com.ccy.chuchaiyi.order.Passenger;

import java.io.Serializable;

/**
 * Created by Chuck on 2016/9/9.
 */
public class EventOfSelPassenger implements Serializable{
    private static final long serialVersionUID = -4636274926213220116L;
    public PassengerInfo mPassenger;
    public int ApprovalId;
    public int ProjectId;
    public String ProjectName;
    public String ApprovalNo;
}
