package com.ccy.chuchaiyi.flight;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chuck on 2016/9/7.
 */
public class EmployeePolicyInfoRsp implements Serializable{
    private static final long serialVersionUID = 7902696079642194162L;

    private List<String> PolicyItems;

    public List<String> getPolicyItems() {
        return PolicyItems;
    }

    public void setPolicyItems(List<String> PolicyItems) {
        this.PolicyItems = PolicyItems;
    }
}
