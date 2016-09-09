package com.ccy.chuchaiyi.check;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chuck on 2016/9/8.
 */
public class Approval implements Serializable{
    private static final long serialVersionUID = -5820760694628469881L;

    /**
     * ApprovalId : 0
     * ApprovalNo : string
     * EmployeeId : 0
     * EmployeeName : string
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
     */

    private List<ApprovalsBean> Approvals;

    public List<ApprovalsBean> getApprovals() {
        return Approvals;
    }

    public void setApprovals(List<ApprovalsBean> Approvals) {
        this.Approvals = Approvals;
    }

    public static class ApprovalsBean {
        private int ApprovalId;
        private String ApprovalNo;
        private int EmployeeId;
        private String EmployeeName;
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

        public int getEmployeeId() {
            return EmployeeId;
        }

        public void setEmployeeId(int EmployeeId) {
            this.EmployeeId = EmployeeId;
        }

        public String getEmployeeName() {
            return EmployeeName;
        }

        public void setEmployeeName(String EmployeeName) {
            this.EmployeeName = EmployeeName;
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
    }
}
