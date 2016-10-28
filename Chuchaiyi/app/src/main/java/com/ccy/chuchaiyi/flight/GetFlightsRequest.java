package com.ccy.chuchaiyi.flight;

import java.io.Serializable;

/**
 * Created by Chuck on 2016/8/17.
 */
public class GetFlightsRequest implements Serializable{

    private static final long serialVersionUID = 6386732683403106970L;
    /**
     * DepartureCode : SZX
     * DepartureCodeIsCity : true
     * ArrivalCode : WUH
     * ArrivalCodeIsCity : true
     * FlightDate : 2016-08-18
     * BunkType :
     * Airlines :
     * FlightNo :
     * FactBunkPriceLowestLimit : 0
     */

    private String DepartureCode;
    private boolean DepartureCodeIsCity;
    private String ArrivalCode;
    private boolean ArrivalCodeIsCity;
    private String FlightDate;
    private String BunkType;
    private String Airlines;
    private String FlightNo;
    private int FactBunkPriceLowestLimit;

    public String getDepartureCode() {
        return DepartureCode;
    }

    public void setDepartureCode(String DepartureCode) {
        this.DepartureCode = DepartureCode;
    }

    public boolean isDepartureCodeIsCity() {
        return DepartureCodeIsCity;
    }

    public void setDepartureCodeIsCity(boolean DepartureCodeIsCity) {
        this.DepartureCodeIsCity = DepartureCodeIsCity;
    }

    public String getArrivalCode() {
        return ArrivalCode;
    }

    public void setArrivalCode(String ArrivalCode) {
        this.ArrivalCode = ArrivalCode;
    }

    public boolean isArrivalCodeIsCity() {
        return ArrivalCodeIsCity;
    }

    public void setArrivalCodeIsCity(boolean ArrivalCodeIsCity) {
        this.ArrivalCodeIsCity = ArrivalCodeIsCity;
    }

    public String getFlightDate() {
        return FlightDate;
    }

    public void setFlightDate(String FlightDate) {
        this.FlightDate = FlightDate;
    }

    public String getBunkType() {
        return BunkType;
    }

    public void setBunkType(String BunkType) {
        this.BunkType = BunkType;
    }

    public String getAirlines() {
        return Airlines;
    }

    public void setAirlines(String Airlines) {
        this.Airlines = Airlines;
    }

    public String getFlightNo() {
        return FlightNo;
    }

    public void setFlightNo(String FlightNo) {
        this.FlightNo = FlightNo;
    }

    public int getFactBunkPriceLowestLimit() {
        return FactBunkPriceLowestLimit;
    }

    public void setFactBunkPriceLowestLimit(int FactBunkPriceLowestLimit) {
        this.FactBunkPriceLowestLimit = FactBunkPriceLowestLimit;
    }
}
