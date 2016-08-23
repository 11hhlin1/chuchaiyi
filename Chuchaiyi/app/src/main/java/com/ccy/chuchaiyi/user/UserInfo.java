package com.ccy.chuchaiyi.user;

import java.io.Serializable;

/**
 * Created by Chuck on 2016/8/10.
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 8542504145409921635L;

    /**
     * Code : 0
     * Message : string
     * Token : string
     * EmployeeId : 0
     * EmployeeName : string
     * Mobile : string
     * Email : string
     * IsAdmin : true
     * IsGreenChannel : true
     * CanBookingForOthers : true
     * CorpId : 0
     * CorpName : string
     * CorpBusinessTypes : string
     * CorpPayMode : string
     * ApprovalRequired : true
     * OverrunOption : string
     * IsProjectRequired : true
     */

    private int Code;
    private String Message;
    private String Token;
    private int EmployeeId;
    private String EmployeeName;
    private String Mobile;
    private String Email;
    private boolean IsAdmin;
    private boolean IsGreenChannel;
    private boolean CanBookingForOthers;
    private int CorpId;
    private String CorpName;
    private String CorpBusinessTypes;
    private String CorpPayMode;
    private boolean ApprovalRequired;
    private String OverrunOption;
    private boolean IsProjectRequired;

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

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
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

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public boolean isIsAdmin() {
        return IsAdmin;
    }

    public void setIsAdmin(boolean IsAdmin) {
        this.IsAdmin = IsAdmin;
    }

    public boolean isIsGreenChannel() {
        return IsGreenChannel;
    }

    public void setIsGreenChannel(boolean IsGreenChannel) {
        this.IsGreenChannel = IsGreenChannel;
    }

    public boolean isCanBookingForOthers() {
        return CanBookingForOthers;
    }

    public void setCanBookingForOthers(boolean CanBookingForOthers) {
        this.CanBookingForOthers = CanBookingForOthers;
    }

    public int getCorpId() {
        return CorpId;
    }

    public void setCorpId(int CorpId) {
        this.CorpId = CorpId;
    }

    public String getCorpName() {
        return CorpName;
    }

    public void setCorpName(String CorpName) {
        this.CorpName = CorpName;
    }

    public String getCorpBusinessTypes() {
        return CorpBusinessTypes;
    }

    public void setCorpBusinessTypes(String CorpBusinessTypes) {
        this.CorpBusinessTypes = CorpBusinessTypes;
    }

    public String getCorpPayMode() {
        return CorpPayMode;
    }

    public void setCorpPayMode(String CorpPayMode) {
        this.CorpPayMode = CorpPayMode;
    }

    public boolean isApprovalRequired() {
        return ApprovalRequired;
    }

    public void setApprovalRequired(boolean ApprovalRequired) {
        this.ApprovalRequired = ApprovalRequired;
    }

    public String getOverrunOption() {
        return OverrunOption;
    }

    public void setOverrunOption(String OverrunOption) {
        this.OverrunOption = OverrunOption;
    }

    public boolean isIsProjectRequired() {
        return IsProjectRequired;
    }

    public void setIsProjectRequired(boolean IsProjectRequired) {
        this.IsProjectRequired = IsProjectRequired;
    }
}
