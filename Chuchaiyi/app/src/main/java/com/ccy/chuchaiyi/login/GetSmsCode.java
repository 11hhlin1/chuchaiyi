package com.ccy.chuchaiyi.login;

import java.io.Serializable;

/**
 * Created by Chuck on 2016/10/13.
 */

public class GetSmsCode implements Serializable{
    private static final long serialVersionUID = -1812990726664212860L;
    private int EmployeeId;
    private int CorpId;
    private String SmsValidateCode;


    public int getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(int EmployeeId) {
        this.EmployeeId = EmployeeId;
    }

    public int getCorpId() {
        return CorpId;
    }

    public void setCorpId(int CorpId) {
        this.CorpId = CorpId;
    }

    public String getSmsValidateCode() {
        return SmsValidateCode;
    }

    public void setSmsValidateCode(String SmsValidateCode) {
        this.SmsValidateCode = SmsValidateCode;
    }
}
