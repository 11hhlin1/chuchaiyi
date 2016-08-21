package com.ccy.chuchaiyi.flight;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/21.
 */
public class GetBookValidateRequest implements Serializable{

    /**
     * DepartureCode : string
     * ArrivalCode : string
     * FlightDate : string
     * FlightNo : string
     * BunkCode : string
     * FactBunkPrice : 0
     */

    private String DepartureCode;
    private String ArrivalCode;
    private String FlightDate;
    private String FlightNo;
    private String BunkCode;
    private int FactBunkPrice;

    public String getDepartureCode() {
        return DepartureCode;
    }

    public void setDepartureCode(String DepartureCode) {
        this.DepartureCode = DepartureCode;
    }

    public String getArrivalCode() {
        return ArrivalCode;
    }

    public void setArrivalCode(String ArrivalCode) {
        this.ArrivalCode = ArrivalCode;
    }

    public String getFlightDate() {
        return FlightDate;
    }

    public void setFlightDate(String FlightDate) {
        this.FlightDate = FlightDate;
    }

    public String getFlightNo() {
        return FlightNo;
    }

    public void setFlightNo(String FlightNo) {
        this.FlightNo = FlightNo;
    }

    public String getBunkCode() {
        return BunkCode;
    }

    public void setBunkCode(String BunkCode) {
        this.BunkCode = BunkCode;
    }

    public int getFactBunkPrice() {
        return FactBunkPrice;
    }

    public void setFactBunkPrice(int FactBunkPrice) {
        this.FactBunkPrice = FactBunkPrice;
    }
}
