package com.ccy.chuchaiyi.contact;

import java.io.Serializable;

/**
 * Created by Chuck on 2016/9/2.
 */
public class PassengerInfo implements Serializable{
    private static final long serialVersionUID = 8172833080447585311L;

    /**
     * EmployeeId : 1
     * EmployeeName : 出差易
     * Gender : 女
     * DepartmentId : 2
     * DepartmentName : 总经办
     * WorkNo : 1001
     * Mobile :
     * Email : Test@chuchaiyi.com
     * DefaultCertType : 身份证
     * DefaultCertNo : **************4026
     */

    private int EmployeeId;
    private String EmployeeName;
    private String Gender;
    private int DepartmentId;
    private String DepartmentName;
    private String WorkNo;
    private String Mobile;
    private String Email;
    private String DefaultCertType;
    private String DefaultCertNo;

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

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }

    public String getWorkNo() {
        return WorkNo;
    }

    public void setWorkNo(String WorkNo) {
        this.WorkNo = WorkNo;
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

    public String getDefaultCertType() {
        return DefaultCertType;
    }

    public void setDefaultCertType(String DefaultCertType) {
        this.DefaultCertType = DefaultCertType;
    }

    public String getDefaultCertNo() {
        return DefaultCertNo;
    }

    public void setDefaultCertNo(String DefaultCertNo) {
        this.DefaultCertNo = DefaultCertNo;
    }
}
