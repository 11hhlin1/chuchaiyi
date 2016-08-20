package com.ccy.chuchaiyi.flight;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/20.
 */
public class FlightPolicy implements Serializable{

    /**
     * Code : 0
     * Message : string
     * ReturnPolicyDesc : string
     * ChangePolicyDesc : string
     * SignPolicyDesc : string
     */

    private int Code;
    private String Message;
    private String ReturnPolicyDesc;
    private String ChangePolicyDesc;
    private String SignPolicyDesc;

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

    public String getReturnPolicyDesc() {
        return ReturnPolicyDesc;
    }

    public void setReturnPolicyDesc(String ReturnPolicyDesc) {
        this.ReturnPolicyDesc = ReturnPolicyDesc;
    }

    public String getChangePolicyDesc() {
        return ChangePolicyDesc;
    }

    public void setChangePolicyDesc(String ChangePolicyDesc) {
        this.ChangePolicyDesc = ChangePolicyDesc;
    }

    public String getSignPolicyDesc() {
        return SignPolicyDesc;
    }

    public void setSignPolicyDesc(String SignPolicyDesc) {
        this.SignPolicyDesc = SignPolicyDesc;
    }
}
