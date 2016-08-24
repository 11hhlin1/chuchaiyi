package com.ccy.chuchaiyi.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table USER_INFO.
*/
public class UserInfoDao extends AbstractDao<UserInfo, Void> {

    public static final String TABLENAME = "USER_INFO";

    /**
     * Properties of entity UserInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Token = new Property(0, String.class, "Token", false, "TOKEN");
        public final static Property EmployeeId = new Property(1, Integer.class, "EmployeeId", false, "EMPLOYEE_ID");
        public final static Property EmployeeName = new Property(2, String.class, "EmployeeName", false, "EMPLOYEE_NAME");
        public final static Property Mobile = new Property(3, String.class, "Mobile", false, "MOBILE");
        public final static Property Email = new Property(4, String.class, "Email", false, "EMAIL");
        public final static Property IsAdmin = new Property(5, Boolean.class, "IsAdmin", false, "IS_ADMIN");
        public final static Property IsGreenChannel = new Property(6, Boolean.class, "IsGreenChannel", false, "IS_GREEN_CHANNEL");
        public final static Property CanBookingForOthers = new Property(7, Boolean.class, "CanBookingForOthers", false, "CAN_BOOKING_FOR_OTHERS");
        public final static Property CorpId = new Property(8, Integer.class, "CorpId", false, "CORP_ID");
        public final static Property CorpName = new Property(9, String.class, "CorpName", false, "CORP_NAME");
        public final static Property CorpBusinessTypes = new Property(10, String.class, "CorpBusinessTypes", false, "CORP_BUSINESS_TYPES");
        public final static Property CorpPayMode = new Property(11, String.class, "CorpPayMode", false, "CORP_PAY_MODE");
        public final static Property ApprovalRequired = new Property(12, Boolean.class, "ApprovalRequired", false, "APPROVAL_REQUIRED");
        public final static Property OverrunOption = new Property(13, String.class, "OverrunOption", false, "OVERRUN_OPTION");
        public final static Property IsProjectRequired = new Property(14, Boolean.class, "IsProjectRequired", false, "IS_PROJECT_REQUIRED");
    };


    public UserInfoDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'USER_INFO' (" + //
                "'TOKEN' TEXT," + // 0: Token
                "'EMPLOYEE_ID' INTEGER," + // 1: EmployeeId
                "'EMPLOYEE_NAME' TEXT," + // 2: EmployeeName
                "'MOBILE' TEXT," + // 3: Mobile
                "'EMAIL' TEXT," + // 4: Email
                "'IS_ADMIN' INTEGER," + // 5: IsAdmin
                "'IS_GREEN_CHANNEL' INTEGER," + // 6: IsGreenChannel
                "'CAN_BOOKING_FOR_OTHERS' INTEGER," + // 7: CanBookingForOthers
                "'CORP_ID' INTEGER," + // 8: CorpId
                "'CORP_NAME' TEXT," + // 9: CorpName
                "'CORP_BUSINESS_TYPES' TEXT," + // 10: CorpBusinessTypes
                "'CORP_PAY_MODE' TEXT," + // 11: CorpPayMode
                "'APPROVAL_REQUIRED' INTEGER," + // 12: ApprovalRequired
                "'OVERRUN_OPTION' TEXT," + // 13: OverrunOption
                "'IS_PROJECT_REQUIRED' INTEGER);"); // 14: IsProjectRequired
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USER_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        String Token = entity.getToken();
        if (Token != null) {
            stmt.bindString(1, Token);
        }
 
        Integer EmployeeId = entity.getEmployeeId();
        if (EmployeeId != null) {
            stmt.bindLong(2, EmployeeId);
        }
 
        String EmployeeName = entity.getEmployeeName();
        if (EmployeeName != null) {
            stmt.bindString(3, EmployeeName);
        }
 
        String Mobile = entity.getMobile();
        if (Mobile != null) {
            stmt.bindString(4, Mobile);
        }
 
        String Email = entity.getEmail();
        if (Email != null) {
            stmt.bindString(5, Email);
        }
 
        Boolean IsAdmin = entity.getIsAdmin();
        if (IsAdmin != null) {
            stmt.bindLong(6, IsAdmin ? 1l: 0l);
        }
 
        Boolean IsGreenChannel = entity.getIsGreenChannel();
        if (IsGreenChannel != null) {
            stmt.bindLong(7, IsGreenChannel ? 1l: 0l);
        }
 
        Boolean CanBookingForOthers = entity.getCanBookingForOthers();
        if (CanBookingForOthers != null) {
            stmt.bindLong(8, CanBookingForOthers ? 1l: 0l);
        }
 
        Integer CorpId = entity.getCorpId();
        if (CorpId != null) {
            stmt.bindLong(9, CorpId);
        }
 
        String CorpName = entity.getCorpName();
        if (CorpName != null) {
            stmt.bindString(10, CorpName);
        }
 
        String CorpBusinessTypes = entity.getCorpBusinessTypes();
        if (CorpBusinessTypes != null) {
            stmt.bindString(11, CorpBusinessTypes);
        }
 
        String CorpPayMode = entity.getCorpPayMode();
        if (CorpPayMode != null) {
            stmt.bindString(12, CorpPayMode);
        }
 
        Boolean ApprovalRequired = entity.getApprovalRequired();
        if (ApprovalRequired != null) {
            stmt.bindLong(13, ApprovalRequired ? 1l: 0l);
        }
 
        String OverrunOption = entity.getOverrunOption();
        if (OverrunOption != null) {
            stmt.bindString(14, OverrunOption);
        }
 
        Boolean IsProjectRequired = entity.getIsProjectRequired();
        if (IsProjectRequired != null) {
            stmt.bindLong(15, IsProjectRequired ? 1l: 0l);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public UserInfo readEntity(Cursor cursor, int offset) {
        UserInfo entity = new UserInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // Token
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // EmployeeId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // EmployeeName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Mobile
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // Email
            cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0, // IsAdmin
            cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0, // IsGreenChannel
            cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0, // CanBookingForOthers
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // CorpId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // CorpName
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // CorpBusinessTypes
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // CorpPayMode
            cursor.isNull(offset + 12) ? null : cursor.getShort(offset + 12) != 0, // ApprovalRequired
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // OverrunOption
            cursor.isNull(offset + 14) ? null : cursor.getShort(offset + 14) != 0 // IsProjectRequired
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserInfo entity, int offset) {
        entity.setToken(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setEmployeeId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setEmployeeName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMobile(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setEmail(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIsAdmin(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
        entity.setIsGreenChannel(cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0);
        entity.setCanBookingForOthers(cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0);
        entity.setCorpId(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setCorpName(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCorpBusinessTypes(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCorpPayMode(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setApprovalRequired(cursor.isNull(offset + 12) ? null : cursor.getShort(offset + 12) != 0);
        entity.setOverrunOption(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setIsProjectRequired(cursor.isNull(offset + 14) ? null : cursor.getShort(offset + 14) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(UserInfo entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(UserInfo entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
