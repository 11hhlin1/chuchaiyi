package com.ccy.chuchaiyi.flight;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/21.
 */
public class BookValidateInfo implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * Code : 0
     * Message : string
     * WarningInfo : {"LowPriceWarningMsg":"string","PreNDaysWarningMsg":"string","DiscountLimitWarningMsg":"string","TwoCabinWarningMsg":"string","LowPriceReasons":["string"],"PreNDaysReasons":["string"]}
     * Flight : {"Departure":{"CityCode":"string","CityName":"string","AirportCode":"string","AirportName":"string","Terminal":"string","DateTime":"string"},"Arrival":{"CityCode":"string","CityName":"string","AirportCode":"string","AirportName":"string","Terminal":"string","DateTime":"string"},"Airline":"string","AirlineName":"string","FlightNo":"string","PlanType":"string","StopInfo":{"StopLocations":[{"City":"string","Arrival":"string","Departure":"string"}]},"ShareFlight":"string","Distance":0,"Meal":"string","YBunkPrice":0,"CBunkPrice":0,"FBunkPrice":0,"AirportFee":0,"OilFee":0,"InsuranceFeeUnitPrice":0,"TicketServiceFee":0,"Bunks":[{"BunkType":"string","BunkCode":"string","BunkName":"string","ReturnPolicy":{"ReturnPolicyDesc":"string","ReturnPolicyReturnFee":0},"ChangePolicy":{"ChangePolicyDesc":"string","ChangeAllowed":true,"ChangePolicyChangeFee":0},"SignPolicy":{"SignPolicyDesc":"string","SignAllowed":true},"PolicyRemark":"string","RemainNum":0,"BunkPrice":{"Discount":0,"BunkPrice":0,"ProtocolCode":"string","ProtocolDiscount":0,"FactBunkPrice":0,"FactDiscount":0,"CorpAddedPrice":0},"PriceSource":"string"}]}
     */

    private int Code;
    private String Message;
    /**
     * LowPriceWarningMsg : string
     * PreNDaysWarningMsg : string
     * DiscountLimitWarningMsg : string
     * TwoCabinWarningMsg : string
     * LowPriceReasons : ["string"]
     * PreNDaysReasons : ["string"]
     */

    private WarningInfoBean WarningInfo;
    /**
     * Departure : {"CityCode":"string","CityName":"string","AirportCode":"string","AirportName":"string","Terminal":"string","DateTime":"string"}
     * Arrival : {"CityCode":"string","CityName":"string","AirportCode":"string","AirportName":"string","Terminal":"string","DateTime":"string"}
     * Airline : string
     * AirlineName : string
     * FlightNo : string
     * PlanType : string
     * StopInfo : {"StopLocations":[{"City":"string","Arrival":"string","Departure":"string"}]}
     * ShareFlight : string
     * Distance : 0
     * Meal : string
     * YBunkPrice : 0
     * CBunkPrice : 0
     * FBunkPrice : 0
     * AirportFee : 0
     * OilFee : 0
     * InsuranceFeeUnitPrice : 0
     * TicketServiceFee : 0
     * Bunks : [{"BunkType":"string","BunkCode":"string","BunkName":"string","ReturnPolicy":{"ReturnPolicyDesc":"string","ReturnPolicyReturnFee":0},"ChangePolicy":{"ChangePolicyDesc":"string","ChangeAllowed":true,"ChangePolicyChangeFee":0},"SignPolicy":{"SignPolicyDesc":"string","SignAllowed":true},"PolicyRemark":"string","RemainNum":0,"BunkPrice":{"Discount":0,"BunkPrice":0,"ProtocolCode":"string","ProtocolDiscount":0,"FactBunkPrice":0,"FactDiscount":0,"CorpAddedPrice":0},"PriceSource":"string"}]
     */

    public FlightInfo Flight;

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

    public WarningInfoBean getWarningInfo() {
        return WarningInfo;
    }

    public void setWarningInfo(WarningInfoBean WarningInfo) {
        this.WarningInfo = WarningInfo;
    }

    public static class WarningInfoBean implements Serializable{
        private static final long serialVersionUID = -2086450458683547684L;
        private String LowPriceWarningMsg;
        private String PreNDaysWarningMsg;
        private String DiscountLimitWarningMsg;
        private String TwoCabinWarningMsg;
        private List<String> LowPriceReasons;
        private List<String> PreNDaysReasons;

        public String getLowPriceWarningMsg() {
            return LowPriceWarningMsg;
        }

        public void setLowPriceWarningMsg(String LowPriceWarningMsg) {
            this.LowPriceWarningMsg = LowPriceWarningMsg;
        }

        public String getPreNDaysWarningMsg() {
            return PreNDaysWarningMsg;
        }

        public void setPreNDaysWarningMsg(String PreNDaysWarningMsg) {
            this.PreNDaysWarningMsg = PreNDaysWarningMsg;
        }

        public String getDiscountLimitWarningMsg() {
            return DiscountLimitWarningMsg;
        }

        public void setDiscountLimitWarningMsg(String DiscountLimitWarningMsg) {
            this.DiscountLimitWarningMsg = DiscountLimitWarningMsg;
        }

        public String getTwoCabinWarningMsg() {
            return TwoCabinWarningMsg;
        }

        public void setTwoCabinWarningMsg(String TwoCabinWarningMsg) {
            this.TwoCabinWarningMsg = TwoCabinWarningMsg;
        }

        public List<String> getLowPriceReasons() {
            return LowPriceReasons;
        }

        public void setLowPriceReasons(List<String> LowPriceReasons) {
            this.LowPriceReasons = LowPriceReasons;
        }

        public List<String> getPreNDaysReasons() {
            return PreNDaysReasons;
        }

        public void setPreNDaysReasons(List<String> PreNDaysReasons) {
            this.PreNDaysReasons = PreNDaysReasons;
        }
    }



}
