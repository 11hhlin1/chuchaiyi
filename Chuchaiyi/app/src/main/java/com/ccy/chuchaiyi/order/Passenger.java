package com.ccy.chuchaiyi.order;

import java.io.Serializable;

/**
 * Created by Chuck on 2016/9/9.
 */
public class Passenger implements Serializable{

    private static final long serialVersionUID = 8790213620782813057L;
    /**
     * PassengerName : string
     * IsEmployee : true
     * EmployeeId : 0
     * PassengerType : string
     * CertType : string
     * CertNo : string
     * Mobile : string
     * BelongedDeptId : 0
     * ApprovalId : 0
     * ProjectId : 0
     * InsuranceCount : 0
     * ReceiveFlightDynamic : true
     */

    public String PassengerName;
    public boolean IsEmployee;
    public int EmployeeId;
    public String PassengerType;
    public String CertType;
    public String CertNo;
    public String Mobile;
    public int BelongedDeptId;
    public int ApprovalId;
    public int ProjectId;
    public int InsuranceCount;
    public boolean ReceiveFlightDynamic;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passenger passenger = (Passenger) o;

        if (EmployeeId != passenger.EmployeeId) return false;
        if (PassengerName != null ? !PassengerName.equals(passenger.PassengerName) : passenger.PassengerName != null)
            return false;
        return Mobile != null ? Mobile.equals(passenger.Mobile) : passenger.Mobile == null;

    }

    @Override
    public int hashCode() {
        int result = PassengerName != null ? PassengerName.hashCode() : 0;
        result = 31 * result + EmployeeId;
        result = 31 * result + (Mobile != null ? Mobile.hashCode() : 0);
        return result;
    }
}
