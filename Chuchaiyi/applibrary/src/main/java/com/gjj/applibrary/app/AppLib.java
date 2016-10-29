package com.gjj.applibrary.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;

import java.util.List;

/**
 * Created by Chuck on 2016/7/26.
 */
public class AppLib {
    /** 前台进程标志 */
    public static final int PROCESS_MAIN = 2;
    private static AppLib mAppLib;
    private Application mApplication;
    private boolean mIsInitialized = false;

    public AppLib(Application app) {
        mApplication = app;
    }

    public static AppLib onCreate(Application app) {
        mAppLib = new AppLib(app);
        return mAppLib;
    }

    public static Context getContext() {
        return mAppLib.mApplication;
    }

    public static Resources getResources() {
        return mAppLib.mApplication.getResources();
    }

    public static String getString(int resId) {
        return mAppLib.mApplication.getString(resId);
    }

    public static String getString(int resId, Object... formatArgs) {
        return mAppLib.mApplication.getString(resId, formatArgs);
    }

    public static ContentResolver getContentResolver() {
        return mAppLib.mApplication.getContentResolver();
    }
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    public static boolean isInitialized() {
        return mAppLib.mIsInitialized;
    }

    public static void setInitialized(boolean value) {
        mAppLib.mIsInitialized = value;
    }


    public static int checkMainProcess(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
        String pkgName = context.getPackageName();
        int pid = android.os.Process.myPid();
        int processType = 0;
        for (ActivityManager.RunningAppProcessInfo appProcess : infoList) {
            if (pid == appProcess.pid) {
                String name = appProcess.processName;
                if (name.equals(pkgName)) {
                    processType = AppLib.PROCESS_MAIN;
                }
                break;
            }
        }
        return processType;
    }
}
