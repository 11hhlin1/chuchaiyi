package com.ccy.chuchaiyi.flight;

import java.io.Serializable;

/**
 * Created by Chuck on 2016/9/2.
 */
public class ReturnFlightPolicy implements Serializable{
    private static final long serialVersionUID = -5501096959168119104L;


    private String ReturnPolicyDesc;
    private String ChangePolicyDesc;
    private String SignPolicyDesc;


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
