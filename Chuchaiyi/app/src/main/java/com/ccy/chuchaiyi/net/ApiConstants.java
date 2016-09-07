package com.ccy.chuchaiyi.net;

/**
 * Created by user on 16/7/16.
 */
public class ApiConstants {
    public static final String host = "http://www.chuchaiyi.com/api/";
    public static final String LOGIN = host + "Account/Login";
    public static final String LOGOUT = host + "Account/Logout";
    public static final String CHANGE_PSW = host + "Account/ChangePassword";
    public static final String GET_FLIGHT_LOCATION = host+ "Flight/GetFlightLocations";
    public static final String GET_FLIGHT_LIST = host+ "Flight/GetFlights";
    public static final String GET_BOOK_VALIDATE= host+ "Flight/BookingValidate";
    public static final String GET_FLIGHT_STOPS= host+ "Flight/GetFlightStops";
    public static final String GET_FLIGHT_POLICY = host+ "Flight/GetFlightPolicy";
    public static final String GET_STORED_USER= host+ "Common/GetMyStoredUsers";
    public static final String GET_EMPLOYEE_POLICY= host+ "Common/GetEmployeePolicyInfo";
    public static final String GET_EMPLOYEES= host+ "Common/GetEmployees";
    public static final String GET_EMPLOYEE = host+ "Common/GetEmployee";
    public static final String GET_APPROVALS = host+ "Approval/GetApprovals";
    public static final String CREATE_APPROVALS = host+ "Approval/CreateAskApproval";

}
