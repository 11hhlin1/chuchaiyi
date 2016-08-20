package com.ccy.chuchaiyi.flight;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chuck on 2016/8/17.
 */
public class FlightInfo implements Serializable{
    private static final long serialVersionUID = -2594514491074216173L;

    /**
     * CityCode : string
     * CityName : string
     * AirportCode : string
     * AirportName : string
     * Terminal : string
     * DateTime : string
     */

    private DepartureBean Departure;
    /**
     * CityCode : string
     * CityName : string
     * AirportCode : string
     * AirportName : string
     * Terminal : string
     * DateTime : string
     */

    private ArrivalBean Arrival;
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

    private String Airline;
    private String AirlineName;
    private String FlightNo;
    private String PlanType;
    private StopInfoBean StopInfo;
    private String ShareFlight;
    private int Distance;
    private String Meal;
    private int YBunkPrice;
    private int CBunkPrice;
    private int FBunkPrice;
    private int AirportFee;
    private int OilFee;
    private int InsuranceFeeUnitPrice;
    private int TicketServiceFee;
    /**
     * BunkType : string
     * BunkCode : string
     * BunkName : string
     * ReturnPolicy : {"ReturnPolicyDesc":"string","ReturnPolicyReturnFee":0}
     * ChangePolicy : {"ChangePolicyDesc":"string","ChangeAllowed":true,"ChangePolicyChangeFee":0}
     * SignPolicy : {"SignPolicyDesc":"string","SignAllowed":true}
     * PolicyRemark : string
     * RemainNum : 0
     * BunkPrice : {"Discount":0,"BunkPrice":0,"ProtocolCode":"string","ProtocolDiscount":0,"FactBunkPrice":0,"FactDiscount":0,"CorpAddedPrice":0}
     * PriceSource : string
     */

    private List<BunksBean> Bunks;

    public DepartureBean getDeparture() {
        return Departure;
    }

    public void setDeparture(DepartureBean Departure) {
        this.Departure = Departure;
    }

    public ArrivalBean getArrival() {
        return Arrival;
    }

    public void setArrival(ArrivalBean Arrival) {
        this.Arrival = Arrival;
    }

    public String getAirline() {
        return Airline;
    }

    public void setAirline(String Airline) {
        this.Airline = Airline;
    }

    public String getAirlineName() {
        return AirlineName;
    }

    public void setAirlineName(String AirlineName) {
        this.AirlineName = AirlineName;
    }

    public String getFlightNo() {
        return FlightNo;
    }

    public void setFlightNo(String FlightNo) {
        this.FlightNo = FlightNo;
    }

    public String getPlanType() {
        return PlanType;
    }

    public void setPlanType(String PlanType) {
        this.PlanType = PlanType;
    }

    public StopInfoBean getStopInfo() {
        return StopInfo;
    }

    public void setStopInfo(StopInfoBean StopInfo) {
        this.StopInfo = StopInfo;
    }

    public String getShareFlight() {
        return ShareFlight;
    }

    public void setShareFlight(String ShareFlight) {
        this.ShareFlight = ShareFlight;
    }

    public int getDistance() {
        return Distance;
    }

    public void setDistance(int Distance) {
        this.Distance = Distance;
    }

    public String getMeal() {
        return Meal;
    }

    public void setMeal(String Meal) {
        this.Meal = Meal;
    }

    public int getYBunkPrice() {
        return YBunkPrice;
    }

    public void setYBunkPrice(int YBunkPrice) {
        this.YBunkPrice = YBunkPrice;
    }

    public int getCBunkPrice() {
        return CBunkPrice;
    }

    public void setCBunkPrice(int CBunkPrice) {
        this.CBunkPrice = CBunkPrice;
    }

    public int getFBunkPrice() {
        return FBunkPrice;
    }

    public void setFBunkPrice(int FBunkPrice) {
        this.FBunkPrice = FBunkPrice;
    }

    public int getAirportFee() {
        return AirportFee;
    }

    public void setAirportFee(int AirportFee) {
        this.AirportFee = AirportFee;
    }

    public int getOilFee() {
        return OilFee;
    }

    public void setOilFee(int OilFee) {
        this.OilFee = OilFee;
    }

    public int getInsuranceFeeUnitPrice() {
        return InsuranceFeeUnitPrice;
    }

    public void setInsuranceFeeUnitPrice(int InsuranceFeeUnitPrice) {
        this.InsuranceFeeUnitPrice = InsuranceFeeUnitPrice;
    }

    public int getTicketServiceFee() {
        return TicketServiceFee;
    }

    public void setTicketServiceFee(int TicketServiceFee) {
        this.TicketServiceFee = TicketServiceFee;
    }

    public List<BunksBean> getBunks() {
        return Bunks;
    }

    public void setBunks(List<BunksBean> Bunks) {
        this.Bunks = Bunks;
    }

    public static class DepartureBean {
        private String CityCode;
        private String CityName;
        private String AirportCode;
        private String AirportName;
        private String Terminal;
        private String DateTime;

        public String getCityCode() {
            return CityCode;
        }

        public void setCityCode(String CityCode) {
            this.CityCode = CityCode;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }

        public String getAirportCode() {
            return AirportCode;
        }

        public void setAirportCode(String AirportCode) {
            this.AirportCode = AirportCode;
        }

        public String getAirportName() {
            return AirportName;
        }

        public void setAirportName(String AirportName) {
            this.AirportName = AirportName;
        }

        public String getTerminal() {
            return Terminal;
        }

        public void setTerminal(String Terminal) {
            this.Terminal = Terminal;
        }

        public String getDateTime() {
            return DateTime;
        }

        public void setDateTime(String DateTime) {
            this.DateTime = DateTime;
        }
    }

    public static class ArrivalBean {
        private String CityCode;
        private String CityName;
        private String AirportCode;
        private String AirportName;
        private String Terminal;
        private String DateTime;

        public String getCityCode() {
            return CityCode;
        }

        public void setCityCode(String CityCode) {
            this.CityCode = CityCode;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }

        public String getAirportCode() {
            return AirportCode;
        }

        public void setAirportCode(String AirportCode) {
            this.AirportCode = AirportCode;
        }

        public String getAirportName() {
            return AirportName;
        }

        public void setAirportName(String AirportName) {
            this.AirportName = AirportName;
        }

        public String getTerminal() {
            return Terminal;
        }

        public void setTerminal(String Terminal) {
            this.Terminal = Terminal;
        }

        public String getDateTime() {
            return DateTime;
        }

        public void setDateTime(String DateTime) {
            this.DateTime = DateTime;
        }
    }

    public static class StopInfoBean {
        /**
         * City : string
         * Arrival : string
         * Departure : string
         */

        private List<StopLocationsBean> StopLocations;

        public List<StopLocationsBean> getStopLocations() {
            return StopLocations;
        }

        public void setStopLocations(List<StopLocationsBean> StopLocations) {
            this.StopLocations = StopLocations;
        }

        public static class StopLocationsBean {
            private String City;
            private String Arrival;
            private String Departure;

            public String getCity() {
                return City;
            }

            public void setCity(String City) {
                this.City = City;
            }

            public String getArrival() {
                return Arrival;
            }

            public void setArrival(String Arrival) {
                this.Arrival = Arrival;
            }

            public String getDeparture() {
                return Departure;
            }

            public void setDeparture(String Departure) {
                this.Departure = Departure;
            }
        }
    }

    public static class BunksBean {
        private String BunkType;
        private String BunkCode;
        private String BunkName;
        /**
         * ReturnPolicyDesc : string
         * ReturnPolicyReturnFee : 0
         */

        private ReturnPolicyBean ReturnPolicy;
        /**
         * ChangePolicyDesc : string
         * ChangeAllowed : true
         * ChangePolicyChangeFee : 0
         */

        private ChangePolicyBean ChangePolicy;
        /**
         * SignPolicyDesc : string
         * SignAllowed : true
         */

        private SignPolicyBean SignPolicy;
        private String PolicyRemark;
        private int RemainNum;
        /**
         * Discount : 0
         * BunkPrice : 0
         * ProtocolCode : string
         * ProtocolDiscount : 0
         * FactBunkPrice : 0
         * FactDiscount : 0
         * CorpAddedPrice : 0
         */

        private BunkPriceBean BunkPrice;
        private String PriceSource;

        public String getBunkType() {
            return BunkType;
        }

        public void setBunkType(String BunkType) {
            this.BunkType = BunkType;
        }

        public String getBunkCode() {
            return BunkCode;
        }

        public void setBunkCode(String BunkCode) {
            this.BunkCode = BunkCode;
        }

        public String getBunkName() {
            return BunkName;
        }

        public void setBunkName(String BunkName) {
            this.BunkName = BunkName;
        }

        public ReturnPolicyBean getReturnPolicy() {
            return ReturnPolicy;
        }

        public void setReturnPolicy(ReturnPolicyBean ReturnPolicy) {
            this.ReturnPolicy = ReturnPolicy;
        }

        public ChangePolicyBean getChangePolicy() {
            return ChangePolicy;
        }

        public void setChangePolicy(ChangePolicyBean ChangePolicy) {
            this.ChangePolicy = ChangePolicy;
        }

        public SignPolicyBean getSignPolicy() {
            return SignPolicy;
        }

        public void setSignPolicy(SignPolicyBean SignPolicy) {
            this.SignPolicy = SignPolicy;
        }

        public String getPolicyRemark() {
            return PolicyRemark;
        }

        public void setPolicyRemark(String PolicyRemark) {
            this.PolicyRemark = PolicyRemark;
        }

        public int getRemainNum() {
            return RemainNum;
        }

        public void setRemainNum(int RemainNum) {
            this.RemainNum = RemainNum;
        }

        public BunkPriceBean getBunkPrice() {
            return BunkPrice;
        }

        public void setBunkPrice(BunkPriceBean BunkPrice) {
            this.BunkPrice = BunkPrice;
        }

        public String getPriceSource() {
            return PriceSource;
        }

        public void setPriceSource(String PriceSource) {
            this.PriceSource = PriceSource;
        }

        public static class ReturnPolicyBean {
            private String ReturnPolicyDesc;
            private int ReturnPolicyReturnFee;

            public String getReturnPolicyDesc() {
                return ReturnPolicyDesc;
            }

            public void setReturnPolicyDesc(String ReturnPolicyDesc) {
                this.ReturnPolicyDesc = ReturnPolicyDesc;
            }

            public int getReturnPolicyReturnFee() {
                return ReturnPolicyReturnFee;
            }

            public void setReturnPolicyReturnFee(int ReturnPolicyReturnFee) {
                this.ReturnPolicyReturnFee = ReturnPolicyReturnFee;
            }
        }

        public static class ChangePolicyBean {
            private String ChangePolicyDesc;
            private boolean ChangeAllowed;
            private int ChangePolicyChangeFee;

            public String getChangePolicyDesc() {
                return ChangePolicyDesc;
            }

            public void setChangePolicyDesc(String ChangePolicyDesc) {
                this.ChangePolicyDesc = ChangePolicyDesc;
            }

            public boolean isChangeAllowed() {
                return ChangeAllowed;
            }

            public void setChangeAllowed(boolean ChangeAllowed) {
                this.ChangeAllowed = ChangeAllowed;
            }

            public int getChangePolicyChangeFee() {
                return ChangePolicyChangeFee;
            }

            public void setChangePolicyChangeFee(int ChangePolicyChangeFee) {
                this.ChangePolicyChangeFee = ChangePolicyChangeFee;
            }
        }

        public static class SignPolicyBean {
            private String SignPolicyDesc;
            private boolean SignAllowed;

            public String getSignPolicyDesc() {
                return SignPolicyDesc;
            }

            public void setSignPolicyDesc(String SignPolicyDesc) {
                this.SignPolicyDesc = SignPolicyDesc;
            }

            public boolean isSignAllowed() {
                return SignAllowed;
            }

            public void setSignAllowed(boolean SignAllowed) {
                this.SignAllowed = SignAllowed;
            }
        }

        public static class BunkPriceBean {
            private int Discount;
            private int BunkPrice;
            private String ProtocolCode;
            private int ProtocolDiscount;
            private int FactBunkPrice;
            private int FactDiscount;
            private int CorpAddedPrice;

            public int getDiscount() {
                return Discount;
            }

            public void setDiscount(int Discount) {
                this.Discount = Discount;
            }

            public int getBunkPrice() {
                return BunkPrice;
            }

            public void setBunkPrice(int BunkPrice) {
                this.BunkPrice = BunkPrice;
            }

            public String getProtocolCode() {
                return ProtocolCode;
            }

            public void setProtocolCode(String ProtocolCode) {
                this.ProtocolCode = ProtocolCode;
            }

            public int getProtocolDiscount() {
                return ProtocolDiscount;
            }

            public void setProtocolDiscount(int ProtocolDiscount) {
                this.ProtocolDiscount = ProtocolDiscount;
            }

            public int getFactBunkPrice() {
                return FactBunkPrice;
            }

            public void setFactBunkPrice(int FactBunkPrice) {
                this.FactBunkPrice = FactBunkPrice;
            }

            public int getFactDiscount() {
                return FactDiscount;
            }

            public void setFactDiscount(int FactDiscount) {
                this.FactDiscount = FactDiscount;
            }

            public int getCorpAddedPrice() {
                return CorpAddedPrice;
            }

            public void setCorpAddedPrice(int CorpAddedPrice) {
                this.CorpAddedPrice = CorpAddedPrice;
            }
        }
    }
}
