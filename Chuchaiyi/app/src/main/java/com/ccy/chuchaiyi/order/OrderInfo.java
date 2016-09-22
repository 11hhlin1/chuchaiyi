package com.ccy.chuchaiyi.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chuck on 2016/9/13.
 */
public class OrderInfo implements Serializable{
    private static final long serialVersionUID = 2448947305691347980L;


    /**
     * Code : 0
     * Message : string
     * TotalCount : 0
     * Orders : [{"OrderId":0,"OrderNo":"string","TravelType":"string","PayMode":"string","Status":"string","PaymentStatus":"string","ApprovalStatus":"string","AuthorizeStatus":"string","BookingEmployeeName":"string","PassengerName":"string","CreateTime":"string","DepartureDateTime":"string","FlightNo":"string","AirlineName":"string","BunkName":"string","Discount":0,"DepartureCityCode":"string","DepartureCityName":"string","DepartureAirportCode":"string","DepartureAirportName":"string","ArrivalCityCode":"string","ArrivalCityName":"string","ArrivalAirportCode":"string","ArrivalAirportName":"string","FactTicketPrice":0,"PaymentAmount":0,"CanCancel":true,"CanPayment":true,"CanReturn":true,"CanChange":true,"CanNetCheckIn":true}]
     */

    private int Code;
    private String Message;
    private int TotalCount;
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
     * PassengerName : string
     * CreateTime : string
     * DepartureDateTime : string
     * FlightNo : string
     * AirlineName : string
     * BunkName : string
     * Discount : 0
     * DepartureCityCode : string
     * DepartureCityName : string
     * DepartureAirportCode : string
     * DepartureAirportName : string
     * ArrivalCityCode : string
     * ArrivalCityName : string
     * ArrivalAirportCode : string
     * ArrivalAirportName : string
     * FactTicketPrice : 0
     * PaymentAmount : 0
     * CanCancel : true
     * CanPayment : true
     * CanReturn : true
     * CanChange : true
     * CanNetCheckIn : true
     */

    private List<OrdersBean> Orders;

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

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public List<OrdersBean> getOrders() {
        return Orders;
    }

    public void setOrders(List<OrdersBean> Orders) {
        this.Orders = Orders;
    }

    public static class OrdersBean implements Serializable{
        private static final long serialVersionUID = 8736054381564733075L;
        private int OrderId;
        private String OrderNo;
        private String TravelType;
        private String PayMode;
        private String Status;
        private String PaymentStatus;
        private String ApprovalStatus;
        private String AuthorizeStatus;
        private String BookingEmployeeName;
        private String PassengerName;
        private String CreateTime;
        private String DepartureDateTime;
        private String FlightNo;
        private String AirlineName;
        private String BunkName;
        private int Discount;
        private String DepartureCityCode;
        private String DepartureCityName;
        private String DepartureAirportCode;
        private String DepartureAirportName;
        private String ArrivalCityCode;
        private String ArrivalCityName;
        private String ArrivalAirportCode;
        private String ArrivalAirportName;
        private int FactTicketPrice;
        private int PaymentAmount;
        private boolean CanCancel;
        private boolean CanPayment;
        private boolean CanReturn;
        private boolean CanChange;
        private boolean CanNetCheckIn;

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

        public String getPassengerName() {
            return PassengerName;
        }

        public void setPassengerName(String PassengerName) {
            this.PassengerName = PassengerName;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getDepartureDateTime() {
            return DepartureDateTime;
        }

        public void setDepartureDateTime(String DepartureDateTime) {
            this.DepartureDateTime = DepartureDateTime;
        }

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

        public String getBunkName() {
            return BunkName;
        }

        public void setBunkName(String BunkName) {
            this.BunkName = BunkName;
        }

        public int getDiscount() {
            return Discount;
        }

        public void setDiscount(int Discount) {
            this.Discount = Discount;
        }

        public String getDepartureCityCode() {
            return DepartureCityCode;
        }

        public void setDepartureCityCode(String DepartureCityCode) {
            this.DepartureCityCode = DepartureCityCode;
        }

        public String getDepartureCityName() {
            return DepartureCityName;
        }

        public void setDepartureCityName(String DepartureCityName) {
            this.DepartureCityName = DepartureCityName;
        }

        public String getDepartureAirportCode() {
            return DepartureAirportCode;
        }

        public void setDepartureAirportCode(String DepartureAirportCode) {
            this.DepartureAirportCode = DepartureAirportCode;
        }

        public String getDepartureAirportName() {
            return DepartureAirportName;
        }

        public void setDepartureAirportName(String DepartureAirportName) {
            this.DepartureAirportName = DepartureAirportName;
        }

        public String getArrivalCityCode() {
            return ArrivalCityCode;
        }

        public void setArrivalCityCode(String ArrivalCityCode) {
            this.ArrivalCityCode = ArrivalCityCode;
        }

        public String getArrivalCityName() {
            return ArrivalCityName;
        }

        public void setArrivalCityName(String ArrivalCityName) {
            this.ArrivalCityName = ArrivalCityName;
        }

        public String getArrivalAirportCode() {
            return ArrivalAirportCode;
        }

        public void setArrivalAirportCode(String ArrivalAirportCode) {
            this.ArrivalAirportCode = ArrivalAirportCode;
        }

        public String getArrivalAirportName() {
            return ArrivalAirportName;
        }

        public void setArrivalAirportName(String ArrivalAirportName) {
            this.ArrivalAirportName = ArrivalAirportName;
        }

        public int getFactTicketPrice() {
            return FactTicketPrice;
        }

        public void setFactTicketPrice(int FactTicketPrice) {
            this.FactTicketPrice = FactTicketPrice;
        }

        public int getPaymentAmount() {
            return PaymentAmount;
        }

        public void setPaymentAmount(int PaymentAmount) {
            this.PaymentAmount = PaymentAmount;
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
    }
}
