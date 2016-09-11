package com.ccy.chuchaiyi.check;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 16/9/11.
 */
public class CheckDetailRsp implements Serializable{

    /**
     * Code : 0
     * Message : string
     * ApprovalDetail : {"ApprovalId":0,"ApprovalNo":"string","EmployeeName":"string","DepartmentName":"string","TravelDateStart":"string","TravelDateEnd":"string","TravelFrom":"string","TravelDestination":"string","TravelReason":"string","Transport":"string","Memo":"string","Status":"string","CreateTime":"string","AskEmployeeName":"string","FlightOrders":[{"OrderId":0,"OrderNo":"string","PassengerName":"string","DepartureCityName":"string","ArrivalCityName":"string","FlightDate":"string","AirlineName":"string","FlightNo":"string","BunkName":"string","Discount":0,"Amount":0,"OrderStatus":"string"}],"HotelOrders":[{"OrderId":0,"OrderNo":"string","GuestName":"string","CheckInDate":"string","CheckOutDate":"string","HotelName":"string","CityName":"string","RoomTypeName":"string","BedType":"string","Amount":0,"OrderStatus":"string"}],"ApprovalHis":[{"AuditLevel":0,"AuditPositionName":"string","AuditPositionEmployeeNames":"string","AuditEmployeeName":"string","AuditDate":"string","Status":"string"}]}
     */

    private int Code;
    private String Message;
    /**
     * ApprovalId : 0
     * ApprovalNo : string
     * EmployeeName : string
     * DepartmentName : string
     * TravelDateStart : string
     * TravelDateEnd : string
     * TravelFrom : string
     * TravelDestination : string
     * TravelReason : string
     * Transport : string
     * Memo : string
     * Status : string
     * CreateTime : string
     * AskEmployeeName : string
     * FlightOrders : [{"OrderId":0,"OrderNo":"string","PassengerName":"string","DepartureCityName":"string","ArrivalCityName":"string","FlightDate":"string","AirlineName":"string","FlightNo":"string","BunkName":"string","Discount":0,"Amount":0,"OrderStatus":"string"}]
     * HotelOrders : [{"OrderId":0,"OrderNo":"string","GuestName":"string","CheckInDate":"string","CheckOutDate":"string","HotelName":"string","CityName":"string","RoomTypeName":"string","BedType":"string","Amount":0,"OrderStatus":"string"}]
     * ApprovalHis : [{"AuditLevel":0,"AuditPositionName":"string","AuditPositionEmployeeNames":"string","AuditEmployeeName":"string","AuditDate":"string","Status":"string"}]
     */

    private ApprovalDetailBean ApprovalDetail;

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

    public ApprovalDetailBean getApprovalDetail() {
        return ApprovalDetail;
    }

    public void setApprovalDetail(ApprovalDetailBean ApprovalDetail) {
        this.ApprovalDetail = ApprovalDetail;
    }

    public static class ApprovalDetailBean {
        private int ApprovalId;
        private String ApprovalNo;
        private String EmployeeName;
        private String DepartmentName;
        private String TravelDateStart;
        private String TravelDateEnd;
        private String TravelFrom;
        private String TravelDestination;
        private String TravelReason;
        private String Transport;
        private String Memo;
        private String Status;
        private String CreateTime;
        private String AskEmployeeName;
        /**
         * OrderId : 0
         * OrderNo : string
         * PassengerName : string
         * DepartureCityName : string
         * ArrivalCityName : string
         * FlightDate : string
         * AirlineName : string
         * FlightNo : string
         * BunkName : string
         * Discount : 0
         * Amount : 0
         * OrderStatus : string
         */

        private List<FlightOrdersBean> FlightOrders;
        /**
         * OrderId : 0
         * OrderNo : string
         * GuestName : string
         * CheckInDate : string
         * CheckOutDate : string
         * HotelName : string
         * CityName : string
         * RoomTypeName : string
         * BedType : string
         * Amount : 0
         * OrderStatus : string
         */

        private List<HotelOrdersBean> HotelOrders;
        /**
         * AuditLevel : 0
         * AuditPositionName : string
         * AuditPositionEmployeeNames : string
         * AuditEmployeeName : string
         * AuditDate : string
         * Status : string
         */

        private List<ApprovalHisBean> ApprovalHis;

        public int getApprovalId() {
            return ApprovalId;
        }

        public void setApprovalId(int ApprovalId) {
            this.ApprovalId = ApprovalId;
        }

        public String getApprovalNo() {
            return ApprovalNo;
        }

        public void setApprovalNo(String ApprovalNo) {
            this.ApprovalNo = ApprovalNo;
        }

        public String getEmployeeName() {
            return EmployeeName;
        }

        public void setEmployeeName(String EmployeeName) {
            this.EmployeeName = EmployeeName;
        }

        public String getDepartmentName() {
            return DepartmentName;
        }

        public void setDepartmentName(String DepartmentName) {
            this.DepartmentName = DepartmentName;
        }

        public String getTravelDateStart() {
            return TravelDateStart;
        }

        public void setTravelDateStart(String TravelDateStart) {
            this.TravelDateStart = TravelDateStart;
        }

        public String getTravelDateEnd() {
            return TravelDateEnd;
        }

        public void setTravelDateEnd(String TravelDateEnd) {
            this.TravelDateEnd = TravelDateEnd;
        }

        public String getTravelFrom() {
            return TravelFrom;
        }

        public void setTravelFrom(String TravelFrom) {
            this.TravelFrom = TravelFrom;
        }

        public String getTravelDestination() {
            return TravelDestination;
        }

        public void setTravelDestination(String TravelDestination) {
            this.TravelDestination = TravelDestination;
        }

        public String getTravelReason() {
            return TravelReason;
        }

        public void setTravelReason(String TravelReason) {
            this.TravelReason = TravelReason;
        }

        public String getTransport() {
            return Transport;
        }

        public void setTransport(String Transport) {
            this.Transport = Transport;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
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

        public String getAskEmployeeName() {
            return AskEmployeeName;
        }

        public void setAskEmployeeName(String AskEmployeeName) {
            this.AskEmployeeName = AskEmployeeName;
        }

        public List<FlightOrdersBean> getFlightOrders() {
            return FlightOrders;
        }

        public void setFlightOrders(List<FlightOrdersBean> FlightOrders) {
            this.FlightOrders = FlightOrders;
        }

        public List<HotelOrdersBean> getHotelOrders() {
            return HotelOrders;
        }

        public void setHotelOrders(List<HotelOrdersBean> HotelOrders) {
            this.HotelOrders = HotelOrders;
        }

        public List<ApprovalHisBean> getApprovalHis() {
            return ApprovalHis;
        }

        public void setApprovalHis(List<ApprovalHisBean> ApprovalHis) {
            this.ApprovalHis = ApprovalHis;
        }

        public static class FlightOrdersBean {
            private int OrderId;
            private String OrderNo;
            private String PassengerName;
            private String DepartureCityName;
            private String ArrivalCityName;
            private String FlightDate;
            private String AirlineName;
            private String FlightNo;
            private String BunkName;
            private int Discount;
            private int Amount;
            private String OrderStatus;

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

            public String getPassengerName() {
                return PassengerName;
            }

            public void setPassengerName(String PassengerName) {
                this.PassengerName = PassengerName;
            }

            public String getDepartureCityName() {
                return DepartureCityName;
            }

            public void setDepartureCityName(String DepartureCityName) {
                this.DepartureCityName = DepartureCityName;
            }

            public String getArrivalCityName() {
                return ArrivalCityName;
            }

            public void setArrivalCityName(String ArrivalCityName) {
                this.ArrivalCityName = ArrivalCityName;
            }

            public String getFlightDate() {
                return FlightDate;
            }

            public void setFlightDate(String FlightDate) {
                this.FlightDate = FlightDate;
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

            public int getAmount() {
                return Amount;
            }

            public void setAmount(int Amount) {
                this.Amount = Amount;
            }

            public String getOrderStatus() {
                return OrderStatus;
            }

            public void setOrderStatus(String OrderStatus) {
                this.OrderStatus = OrderStatus;
            }
        }

        public static class HotelOrdersBean {
            private int OrderId;
            private String OrderNo;
            private String GuestName;
            private String CheckInDate;
            private String CheckOutDate;
            private String HotelName;
            private String CityName;
            private String RoomTypeName;
            private String BedType;
            private int Amount;
            private String OrderStatus;

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

            public String getGuestName() {
                return GuestName;
            }

            public void setGuestName(String GuestName) {
                this.GuestName = GuestName;
            }

            public String getCheckInDate() {
                return CheckInDate;
            }

            public void setCheckInDate(String CheckInDate) {
                this.CheckInDate = CheckInDate;
            }

            public String getCheckOutDate() {
                return CheckOutDate;
            }

            public void setCheckOutDate(String CheckOutDate) {
                this.CheckOutDate = CheckOutDate;
            }

            public String getHotelName() {
                return HotelName;
            }

            public void setHotelName(String HotelName) {
                this.HotelName = HotelName;
            }

            public String getCityName() {
                return CityName;
            }

            public void setCityName(String CityName) {
                this.CityName = CityName;
            }

            public String getRoomTypeName() {
                return RoomTypeName;
            }

            public void setRoomTypeName(String RoomTypeName) {
                this.RoomTypeName = RoomTypeName;
            }

            public String getBedType() {
                return BedType;
            }

            public void setBedType(String BedType) {
                this.BedType = BedType;
            }

            public int getAmount() {
                return Amount;
            }

            public void setAmount(int Amount) {
                this.Amount = Amount;
            }

            public String getOrderStatus() {
                return OrderStatus;
            }

            public void setOrderStatus(String OrderStatus) {
                this.OrderStatus = OrderStatus;
            }
        }

        public static class ApprovalHisBean {
            private int AuditLevel;
            private String AuditPositionName;
            private String AuditPositionEmployeeNames;
            private String AuditEmployeeName;
            private String AuditDate;
            private String Status;

            public int getAuditLevel() {
                return AuditLevel;
            }

            public void setAuditLevel(int AuditLevel) {
                this.AuditLevel = AuditLevel;
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
        }
    }
}
