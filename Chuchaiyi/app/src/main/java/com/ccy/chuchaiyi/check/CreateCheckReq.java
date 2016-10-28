package com.ccy.chuchaiyi.check;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chuck on 2016/9/7.
 */
public class CreateCheckReq implements Serializable{

    private static final long serialVersionUID = -5957871766414950644L;
    /**
     * CorpId : 0
     * AskEmployeeId : 0
     * TravelReason : string
     * TravelDateStart : string
     * TravelDateEnd : string
     * Transport : string
     * TravelFrom : string
     * TravelDestination : string
     * Memo : string
     * Employees : [0]
     */

    public int CorpId;
    public int AskEmployeeId;
    public String TravelReason;
    public String TravelDateStart;
    public String TravelDateEnd;
    public String Transport;
    public String TravelFrom;
    public String TravelDestination;
    public String Memo;
    public List<Integer> Employees;

}
