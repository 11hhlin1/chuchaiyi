package com.ccy.chuchaiyi.contact;

import java.io.Serializable;

/**
 * Created by Chuck on 2016/9/2.
 */
public class StoreUser implements Serializable{
    private static final long serialVersionUID = 4395115232993298722L;

    /**
     * Name : string
     * Gender : string
     * CertType : string
     * CertNo : string
     * Mobile : string
     * Email : string
     * IsEmployee : true
     * BelongedEmployeeId : 0
     * BelongedDepartmentId : 0
     * BelongedDepartmentName : string
     */

    private String Name;
    private String Gender;
    private String CertType;
    private String CertNo;
    private String Mobile;
    private String Email;
    private boolean IsEmployee;
    private int BelongedEmployeeId;
    private int BelongedDepartmentId;
    private String BelongedDepartmentName;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
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

    public boolean isIsEmployee() {
        return IsEmployee;
    }

    public void setIsEmployee(boolean IsEmployee) {
        this.IsEmployee = IsEmployee;
    }

    public int getBelongedEmployeeId() {
        return BelongedEmployeeId;
    }

    public void setBelongedEmployeeId(int BelongedEmployeeId) {
        this.BelongedEmployeeId = BelongedEmployeeId;
    }

    public int getBelongedDepartmentId() {
        return BelongedDepartmentId;
    }

    public void setBelongedDepartmentId(int BelongedDepartmentId) {
        this.BelongedDepartmentId = BelongedDepartmentId;
    }

    public String getBelongedDepartmentName() {
        return BelongedDepartmentName;
    }

    public void setBelongedDepartmentName(String BelongedDepartmentName) {
        this.BelongedDepartmentName = BelongedDepartmentName;
    }
}
