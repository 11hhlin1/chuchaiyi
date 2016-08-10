package com.gjj.applibrary.app;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Chuck on 2016/7/26.
 */
public class AppLib {

    private static AppLib mAppLib;
    private Application mApplication;

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
}
