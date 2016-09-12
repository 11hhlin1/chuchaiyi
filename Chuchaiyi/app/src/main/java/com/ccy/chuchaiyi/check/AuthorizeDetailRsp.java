package com.ccy.chuchaiyi.check;

import java.io.Serializable;

/**
 * Created by Chuck on 2016/9/12.
 */
public class AuthorizeDetailRsp implements Serializable{

    private static final long serialVersionUID = 959282993186512514L;


    /**
     * Code : 0
     * Message : string
     * AuthorizeDetail : {"AuthorizeId":0,"OrderType":"string","OrderId":0,"OrderNo":"string","TravellerName":"string","TravelDate":"string","OrderDesc":"string","AuditPositionName":"string","AuditPositionEmployeeNames":"string","AuditEmployeeName":"string","AuditDate":"string","Status":"string","CreateTime":"string","FlightOrder":{"OrderId":0,"OrderNo":"string","TravelType":"string","PayMode":"string","Status":"string","PaymentStatus":"string","ApprovalStatus":"string","AuthorizeStatus":"string","BookingEmployeeName":"string","CreateTime":"string","ContactName":"string","ContactEmail":"string","ContactMobile":"string","CanCancel":true,"CanPayment":true,"CanReturn":true,"CanChange":true,"CanNetCheckIn":true,"Passenger":{"BelongedDeptName":"string","PassengerName":"string","CertType":"string","CertNo":"string","InsuranceCount":0,"TravelPolicyInfo":{"LowPriceWarningMsg":"string","NotLowPriceReason":"string","PreNDaysWarningMsg":"string","NotPreNDaysReason":"string","DiscountLimitWarningMsg":"string","TwoCabinWarningMsg":"string"}},"Route":{"FlightNo":"string","AirlineName":"string","PlanTypeCode":"string","BunkName":"string","BunkCode":"string","Discount":0,"Departure":{"CityCode":"string","CityName":"string","AirportCode":"string","AirportName":"string","Terminal":"string","DateTime":"string"},"Arrival":{"CityCode":"string","CityName":"string","AirportCode":"string","AirportName":"string","Terminal":"string","DateTime":"string"},"ReturnPolicy":{"ReturnPolicyDesc":"string","ReturnPolicyReturnFee":0},"ChangePolicy":{"ChangePolicyDesc":"string","ChangeAllowed":true,"ChangePolicyChangeFee":0},"SignPolicy":{"SignPolicyDesc":"string","SignAllowed":true},"StopCity":"string"},"FeeInfo":{"TicketFee":0,"AirportFee":0,"OilFee":0,"InsuranceFee":0,"ReturnTicketFee":0,"ChangeTicketFee":0,"TicketServiceFee":0,"PaymentAmount":0}}}
     */

    private int Code;
    private String Message;
    /**
     * AuthorizeId : 0
     * OrderType : string
     * OrderId : 0
     * OrderNo : string
     * TravellerName : string
     * TravelDate : string
     * OrderDesc : string
     * AuditPositionName : string
     * AuditPositionEmployeeNames : string
     * AuditEmployeeName : string
     * AuditDate : string
     * Status : string
     * CreateTime : string
     * FlightOrder : {"OrderId":0,"OrderNo":"string","TravelType":"string","PayMode":"string","Status":"string","PaymentStatus":"string","ApprovalStatus":"string","AuthorizeStatus":"string","BookingEmployeeName":"string","CreateTime":"string","ContactName":"string","ContactEmail":"string","ContactMobile":"string","CanCancel":true,"CanPayment":true,"CanReturn":true,"CanChange":true,"CanNetCheckIn":true,"Passenger":{"BelongedDeptName":"string","PassengerName":"string","CertType":"string","CertNo":"string","InsuranceCount":0,"TravelPolicyInfo":{"LowPriceWarningMsg":"string","NotLowPriceReason":"string","PreNDaysWarningMsg":"string","NotPreNDaysReason":"string","DiscountLimitWarningMsg":"string","TwoCabinWarningMsg":"string"}},"Route":{"FlightNo":"string","AirlineName":"string","PlanTypeCode":"string","BunkName":"string","BunkCode":"string","Discount":0,"Departure":{"CityCode":"string","CityName":"string","AirportCode":"string","AirportName":"string","Terminal":"string","DateTime":"string"},"Arrival":{"CityCode":"string","CityName":"string","AirportCode":"string","AirportName":"string","Terminal":"string","DateTime":"string"},"ReturnPolicy":{"ReturnPolicyDesc":"string","ReturnPolicyReturnFee":0},"ChangePolicy":{"ChangePolicyDesc":"string","ChangeAllowed":true,"ChangePolicyChangeFee":0},"SignPolicy":{"SignPolicyDesc":"string","SignAllowed":true},"StopCity":"string"},"FeeInfo":{"TicketFee":0,"AirportFee":0,"OilFee":0,"InsuranceFee":0,"ReturnTicketFee":0,"ChangeTicketFee":0,"TicketServiceFee":0,"PaymentAmount":0}}
     */

    private AuthorizeDetailBean AuthorizeDetail;

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

    public AuthorizeDetailBean getAuthorizeDetail() {
        return AuthorizeDetail;
    }

    public void setAuthorizeDetail(AuthorizeDetailBean AuthorizeDetail) {
        this.AuthorizeDetail = AuthorizeDetail;
    }

    public static class AuthorizeDetailBean {
        private int AuthorizeId;
        private String OrderType;
        private int OrderId;
        private String OrderNo;
        private String TravellerName;
        private String TravelDate;
        private String OrderDesc;
        private String AuditPositionName;
        private String AuditPositionEmployeeNames;
        private String AuditEmployeeName;
        private String AuditDate;
        private String Status;
        private String CreateTime;
        /**
         * OrderId : 0
         * OrderNo : string
         * TravelType : string
         * PayMode : string
         * Status : string
         * PaymentStatus : string
         * ApprovalStatus : string
         * AuthorizeStatus : string
         * BookingEmployeeName : string
         * CreateTime : string
         * ContactName : string
         * ContactEmail : string
         * ContactMobile : string
         * CanCancel : true
         * CanPayment : true
         * CanReturn : true
         * CanChange : true
         * CanNetCheckIn : true
         * Passenger : {"BelongedDeptName":"string","PassengerName":"string","CertType":"string","CertNo":"string","InsuranceCount":0,"TravelPolicyInfo":{"LowPriceWarningMsg":"string","NotLowPriceReason":"string","PreNDaysWarningMsg":"string","NotPreNDaysReason":"string","DiscountLimitWarningMsg":"string","TwoCabinWarningMsg":"string"}}
         * Route : {"FlightNo":"string","AirlineName":"string","PlanTypeCode":"string","BunkName":"string","BunkCode":"string","Discount":0,"Departure":{"CityCode":"string","CityName":"string","AirportCode":"string","AirportName":"string","Terminal":"string","DateTime":"string"},"Arrival":{"CityCode":"string","CityName":"string","AirportCode":"string","AirportName":"string","Terminal":"string","DateTime":"string"},"ReturnPolicy":{"ReturnPolicyDesc":"string","ReturnPolicyReturnFee":0},"ChangePolicy":{"ChangePolicyDesc":"string","ChangeAllowed":true,"ChangePolicyChangeFee":0},"SignPolicy":{"SignPolicyDesc":"string","SignAllowed":true},"StopCity":"string"}
         * FeeInfo : {"TicketFee":0,"AirportFee":0,"OilFee":0,"InsuranceFee":0,"ReturnTicketFee":0,"ChangeTicketFee":0,"TicketServiceFee":0,"PaymentAmount":0}
         */

        private FlightOrderBean FlightOrder;

        public int getAuthorizeId() {
            return AuthorizeId;
        }

        public void setAuthorizeId(int AuthorizeId) {
            this.AuthorizeId = AuthorizeId;
        }

        public String getOrderType() {
            return OrderType;
        }

        public void setOrderType(String OrderType) {
            this.OrderType = OrderType;
        }

        public int getOrderId() {
            return OrderId;
        }

        public void setOrderId(int OrderId) {
            this.OrderId = OrderId;
        }

        public String getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(String OrderNo) {
            this.OrderNo = OrderNo;
        }

        public String getTravellerName() {
            return TravellerName;
        }

        public void setTravellerName(String TravellerName) {
            this.TravellerName = TravellerName;
        }

        public String getTravelDate() {
            return TravelDate;
        }

        public void setTravelDate(String TravelDate) {
            this.TravelDate = TravelDate;
        }

        public String getOrderDesc() {
            return OrderDesc;
        }

        public void setOrderDesc(String OrderDesc) {
            this.OrderDesc = OrderDesc;
        }

        public String getAuditPositionName() {
            return AuditPositionName;
        }

        public void setAuditPositionName(String AuditPositionName) {
            this.AuditPositionName = AuditPositionName;
        }

        public String getAuditPositionEmployeeNames() {
            return AuditPositionEmployeeNames;
        }

        public void setAuditPositionEmployeeNames(String AuditPositionEmployeeNames) {
            this.AuditPositionEmployeeNames = AuditPositionEmployeeNames;
        }

        public String getAuditEmployeeName() {
            return AuditEmployeeName;
        }

        public void setAuditEmployeeName(String AuditEmployeeName) {
            this.AuditEmployeeName = AuditEmployeeName;
        }

        public String getAuditDate() {
            return AuditDate;
        }

        public void setAuditDate(String AuditDate) {
            this.AuditDate = AuditDate;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public FlightOrderBean getFlightOrder() {
            return FlightOrder;
        }

        public void setFlightOrder(FlightOrderBean FlightOrder) {
            this.FlightOrder = FlightOrder;
        }

        public static class FlightOrderBean {
            private int OrderId;
            private String OrderNo;
            private String TravelType;
            private String PayMode;
            private String Status;
            private String PaymentStatus;
            private String ApprovalStatus;
            private String AuthorizeStatus;
            private String BookingEmployeeName;
            private String CreateTime;
            private String ContactName;
            private String ContactEmail;
            private String ContactMobile;
            private boolean CanCancel;
            private boolean CanPayment;
            private boolean CanReturn;
            private boolean CanChange;
            private boolean CanNetCheckIn;
            /**
             * BelongedDeptName : string
             * PassengerName : string
             * CertType : string
             * CertNo : string
             * InsuranceCount : 0
             * TravelPolicyInfo : {"LowPriceWarningMsg":"string","NotLowPriceReason":"string","PreNDaysWarningMsg":"string","NotPreNDaysReason":"string","DiscountLimitWarningMsg":"string","TwoCabinWarningMsg":"string"}
             */

            private PassengerBean Passenger;
            /**
             * FlightNo : string
             * AirlineName : string
             * PlanTypeCode : string
             * BunkName : string
             * BunkCode : string
             * Discount : 0
             * Departure : {"CityCode":"string","CityName":"string","AirportCode":"string","AirportName":"string","Terminal":"string","DateTime":"string"}
             * Arrival : {"CityCode":"string","CityName":"string","AirportCode":"string","AirportName":"string","Terminal":"string","DateTime":"string"}
             * ReturnPolicy : {"ReturnPolicyDesc":"string","ReturnPolicyReturnFee":0}
             * ChangePolicy : {"ChangePolicyDesc":"string","ChangeAllowed":true,"ChangePolicyChangeFee":0}
             * SignPolicy : {"SignPolicyDesc":"string","SignAllowed":true}
             * StopCity : string
             */

            private RouteBean Route;
            /**
             * TicketFee : 0
             * AirportFee : 0
             * OilFee : 0
             * InsuranceFee : 0
             * ReturnTicketFee : 0
             * ChangeTicketFee : 0
             * TicketServiceFee : 0
             * PaymentAmount : 0
             */

            private FeeInfoBean FeeInfo;

            public int getOrderId() {
                return OrderId;
            }

            public void setOrderId(int OrderId) {
                this.OrderId = OrderId;
            }

            public String getOrderNo() {
                return OrderNo;
            }

            public void setOrderNo(String OrderNo) {
                this.OrderNo = OrderNo;
            }

            public String getTravelType() {
                return TravelType;
            }

            public void setTravelType(String TravelType) {
                this.TravelType = TravelType;
            }

            public String getPayMode() {
                return PayMode;
            }

            public void setPayMode(String PayMode) {
                this.PayMode = PayMode;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }

            public String getPaymentStatus() {
                return PaymentStatus;
            }

            public void setPaymentStatus(String PaymentStatus) {
                this.PaymentStatus = PaymentStatus;
            }

            public String getApprovalStatus() {
                return ApprovalStatus;
            }

            public void setApprovalStatus(String ApprovalStatus) {
                this.ApprovalStatus = ApprovalStatus;
            }

            public String getAuthorizeStatus() {
                return AuthorizeStatus;
            }

            public void setAuthorizeStatus(String AuthorizeStatus) {
                this.AuthorizeStatus = AuthorizeStatus;
            }

            public String getBookingEmployeeName() {
                return BookingEmployeeName;
            }

            public void setBookingEmployeeName(String BookingEmployeeName) {
                this.BookingEmployeeName = BookingEmployeeName;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public String getContactName() {
                return ContactName;
            }

            public void setContactName(String ContactName) {
                this.ContactName = ContactName;
            }

            public String getContactEmail() {
                return ContactEmail;
            }

            public void setContactEmail(String ContactEmail) {
                this.ContactEmail = ContactEmail;
            }

            public String getContactMobile() {
                return ContactMobile;
            }

            public void setContactMobile(String ContactMobile) {
                this.ContactMobile = ContactMobile;
            }

            public boolean isCanCancel() {
                return CanCancel;
            }

            public void setCanCancel(boolean CanCancel) {
                this.CanCancel = CanCancel;
            }

            public boolean isCanPayment() {
                return CanPayment;
            }

            public void setCanPayment(boolean CanPayment) {
                this.CanPayment = CanPayment;
            }

            public boolean isCanReturn() {
                return CanReturn;
            }

            public void setCanReturn(boolean CanReturn) {
                this.CanReturn = CanReturn;
            }

            public boolean isCanChange() {
                return CanChange;
            }

            public void setCanChange(boolean CanChange) {
                this.CanChange = CanChange;
            }

            public boolean isCanNetCheckIn() {
                return CanNetCheckIn;
            }

            public void setCanNetCheckIn(boolean CanNetCheckIn) {
                this.CanNetCheckIn = CanNetCheckIn;
            }

            public PassengerBean getPassenger() {
                return Passenger;
            }

            public void setPassenger(PassengerBean Passenger) {
                this.Passenger = Passenger;
            }

            public RouteBean getRoute() {
                return Route;
            }

            public void setRoute(RouteBean Route) {
                this.Route = Route;
            }

            public FeeInfoBean getFeeInfo() {
                return FeeInfo;
            }

            public void setFeeInfo(FeeInfoBean FeeInfo) {
                this.FeeInfo = FeeInfo;
            }

            public static class PassengerBean {
                private String BelongedDeptName;
                private String PassengerName;
                private String CertType;
                private String CertNo;
                private int InsuranceCount;
                /**
                 * LowPriceWarningMsg : string
                 * NotLowPriceReason : string
                 * PreNDaysWarningMsg : string
                 * NotPreNDaysReason : string
                 * DiscountLimitWarningMsg : string
                 * TwoCabinWarningMsg : string
                 */

                private TravelPolicyInfoBean TravelPolicyInfo;

                public String getBelongedDeptName() {
                    return BelongedDeptName;
                }

                public void setBelongedDeptName(String BelongedDeptName) {
                    this.BelongedDeptName = BelongedDeptName;
                }

                public String getPassengerName() {
                    return PassengerName;
                }

                public void setPassengerName(String PassengerName) {
                    this.PassengerName = PassengerName;
                }

                public String getCertType() {
                    return CertType;
                }

                public void setCertType(String CertType) {
                    this.CertType = CertType;
                }

                public String getCertNo() {
                    return CertNo;
                }

                public void setCertNo(String CertNo) {
                    this.CertNo = CertNo;
                }

                public int getInsuranceCount() {
                    return InsuranceCount;
                }

                public void setInsuranceCount(int InsuranceCount) {
                    this.InsuranceCount = InsuranceCount;
                }

                public TravelPolicyInfoBean getTravelPolicyInfo() {
                    return TravelPolicyInfo;
                }

                public void setTravelPolicyInfo(TravelPolicyInfoBean TravelPolicyInfo) {
                    this.TravelPolicyInfo = TravelPolicyInfo;
                }

                public static class TravelPolicyInfoBean {
                    private String LowPriceWarningMsg;
                    private String NotLowPriceReason;
                    private String PreNDaysWarningMsg;
                    private String NotPreNDaysReason;
                    private String DiscountLimitWarningMsg;
                    private String TwoCabinWarningMsg;

                    public String getLowPriceWarningMsg() {
                        return LowPriceWarningMsg;
                    }

                    public void setLowPriceWarningMsg(String LowPriceWarningMsg) {
                        this.LowPriceWarningMsg = LowPriceWarningMsg;
                    }

                    public String getNotLowPriceReason() {
                        return NotLowPriceReason;
                    }

                    public void setNotLowPriceReason(String NotLowPriceReason) {
                        this.NotLowPriceReason = NotLowPriceReason;
                    }

                    public String getPreNDaysWarningMsg() {
                        return PreNDaysWarningMsg;
                    }

                    public void setPreNDaysWarningMsg(String PreNDaysWarningMsg) {
                        this.PreNDaysWarningMsg = PreNDaysWarningMsg;
                    }

                    public String getNotPreNDaysReason() {
                        return NotPreNDaysReason;
                    }

                    public void setNotPreNDaysReason(String NotPreNDaysReason) {
                        this.NotPreNDaysReason = NotPreNDaysReason;
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
                }
            }

            public static class RouteBean {
                private String FlightNo;
                private String AirlineName;
                private String PlanTypeCode;
                private String BunkName;
                private String BunkCode;
                private int Discount;
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
                private String StopCity;

                public String getFlightNo() {
                    return FlightNo;
                }

                public void setFlightNo(String FlightNo) {
                    this.FlightNo = FlightNo;
                }

                public String getAirlineName() {
                    return AirlineName;
                }

                public void setAirlineName(String AirlineName) {
                    this.AirlineName = AirlineName;
                }

                public String getPlanTypeCode() {
                    return PlanTypeCode;
                }

                public void setPlanTypeCode(String PlanTypeCode) {
                    this.PlanTypeCode = PlanTypeCode;
                }

                public String getBunkName() {
                    return BunkName;
                }

                public void setBunkName(String BunkName) {
                    this.BunkName = BunkName;
                }

                public String getBunkCode() {
                    return BunkCode;
                }

                public void setBunkCode(String BunkCode) {
                    this.BunkCode = BunkCode;
                }

                public int getDiscount() {
                    return Discount;
                }

                public void setDiscount(int Discount) {
                    this.Discount = Discount;
                }

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

                public String getStopCity() {
                    return StopCity;
                }

                public void setStopCity(String StopCity) {
                    this.StopCity = StopCity;
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
            }

            public static class FeeInfoBean {
                private int TicketFee;
                private int AirportFee;
                private int OilFee;
                private int InsuranceFee;
                private int ReturnTicketFee;
                private int ChangeTicketFee;
                private int TicketServiceFee;
                private int PaymentAmount;

                public int getTicketFee() {
                    return TicketFee;
                }

                public void setTicketFee(int TicketFee) {
                    this.TicketFee = TicketFee;
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

                public int getInsuranceFee() {
                    return InsuranceFee;
                }

                public void setInsuranceFee(int InsuranceFee) {
                    this.InsuranceFee = InsuranceFee;
                }

                public int getReturnTicketFee() {
                    return ReturnTicketFee;
                }

                public void setReturnTicketFee(int ReturnTicketFee) {
                    this.ReturnTicketFee = ReturnTicketFee;
                }

                public int getChangeTicketFee() {
                    return ChangeTicketFee;
                }

                public void setChangeTicketFee(int ChangeTicketFee) {
                    this.ChangeTicketFee = ChangeTicketFee;
                }

                public int getTicketServiceFee() {
                    return TicketServiceFee;
                }

                public void setTicketServiceFee(int TicketServiceFee) {
                    this.TicketServiceFee = TicketServiceFee;
                }

                public int getPaymentAmount() {
                    return PaymentAmount;
                }

                public void setPaymentAmount(int PaymentAmount) {
                    this.PaymentAmount = PaymentAmount;
                }
            }
        }
    }
}
