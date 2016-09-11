package com.ccy.chuchaiyi.net;

/**
 * Created by user on 16/7/16.
 */
public class ApiConstants {
    public static final String host = "http://www.chuchaiyi.com/api/";
    public static final String LOGIN = host + "Account/Login";
    public static final String LOGOUT = host + "Account/Logout";
    public static final String CHANGE_PSW = host + "Account/ChangePassword";
    public static final String REFRESH_LOGIN_INFO = host + "Account/RefreshLoginInfo";
    public static final String GET_FLIGHT_LOCATION = host+ "Flight/GetFlightLocations";
    public static final String GET_FLIGHT_LIST = host+ "Flight/GetFlights";
    public static final String GET_BOOK_VALIDATE= host+ "Flight/BookingValidate";
    public static final String GET_FLIGHT_STOPS= host+ "Flight/GetFlightStops";
    public static final String GET_FLIGHT_POLICY = host+ "Flight/GetFlightPolicy";
    public static final String COMMIT_ORDER = host+ "Flight/PlaceAskOrder";
    public static final String CONFIRM_BY_CORP_CREDIT = host+ "Flight/AskOrderConfirmByCorpCredit";
    public static final String GET_STORED_USER= host+ "Common/GetMyStoredUsers";
    public static final String GET_EMPLOYEE_POLICY= host+ "Common/GetEmployeePolicyInfo";
    public static final String GET_EMPLOYEES= host+ "Common/GetEmployees";
    public static final String GET_EMPLOYEE = host+ "Common/GetEmployee";
    public static final String GET_PROJECT = host+ "Common/GetProjects";
    public static final String GET_APPROVALS = host+ "Approval/GetApprovals";
    public static final String CREATE_APPROVALS = host+ "Approval/CreateAskApproval";
    public static final String GET_MY_APPROVALS = host+ "Approval/GetMyApprovals";
    public static final String GET_AUDIT_FOR_ME_APPROVALS = host+ "Approval/GetApprovalsToAuditForMe";
    public static final String GET_AUDITED_APPROVALS = host+ "Approval/GetApprovalsAuditedByMe";
    public static final String CANCEL_APPROVAL = host + "Approval/CancelApproval";
    public static final String GET_AUTHORIZE_SHEETS = host+ "Approval/GetAuthorizeSheetsToAuditForMe";
    public static final String GET_AUTHORIZED_SHEETS = host+ "Approval/GetAuthorizeSheetsAuditedByMe";
    public static final String AUDIT_PASS_APPROVAL = host+ "Approval/AuditPassApproval";
    public static final String GET_APPROVAL_DETAIL = host+ "Approval/GetApprovalDetail";

}
