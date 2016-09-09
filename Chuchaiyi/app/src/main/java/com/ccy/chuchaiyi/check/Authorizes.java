package com.ccy.chuchaiyi.check;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chuck on 2016/9/9.
 */
public class Authorizes implements Serializable{

    private static final long serialVersionUID = 6005919384953701967L;

    /**
     * AuthorizeId : 0
     * OrderType : string
     * OrderId : 0
     * OrderNo : string
     * TravellerName : string
     * TravelDate : string
     * OrderDesc : string
     * Status : string
     * AuditOpinion : string
     * CreateTime : string
     */

    private List<AuthorizesBean> Authorizes;

    public List<AuthorizesBean> getAuthorizes() {
        return Authorizes;
    }

    public void setAuthorizes(List<AuthorizesBean> Authorizes) {
        this.Authorizes = Authorizes;
    }

    public static class AuthorizesBean {
        private int AuthorizeId;
        private String OrderType;
        private int OrderId;
        private String OrderNo;
        private String TravellerName;
        private String TravelDate;
        private String OrderDesc;
        private String Status;
        private String AuditOpinion;
        private String CreateTime;

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

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getAuditOpinion() {
            return AuditOpinion;
        }

        public void setAuditOpinion(String AuditOpinion) {
            this.AuditOpinion = AuditOpinion;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
