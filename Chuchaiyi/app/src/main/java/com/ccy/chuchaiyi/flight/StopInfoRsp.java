package com.ccy.chuchaiyi.flight;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chuck on 2016/9/7.
 */
public class StopInfoRsp implements Serializable{

    private static final long serialVersionUID = 2023467262198772750L;

    /**
     * Code : 0
     * Message : string
     * Stops : [{"City":"string","Arrival":"string","Departure":"string"}]
     */

    private int Code;
    private String Message;
    /**
     * City : string
     * Arrival : string
     * Departure : string
     */

    private List<FlightInfo.StopInfoBean.StopLocationsBean> Stops;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<FlightInfo.StopInfoBean.StopLocationsBean> getStops() {
        return Stops;
    }

    public void setStops(List<FlightInfo.StopInfoBean.StopLocationsBean> Stops) {
        this.Stops = Stops;
    }


}
