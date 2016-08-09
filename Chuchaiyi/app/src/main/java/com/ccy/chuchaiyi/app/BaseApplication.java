package com.ccy.chuchaiyi.app;

import android.app.Application;

import com.gjj.applibrary.app.AppLib;
import com.gjj.applibrary.http.model.BundleKey;
import com.gjj.applibrary.util.PreferencesManager;
import com.lzy.okhttputils.OkHttpUtils;

/**
 * Created by Administrator on 2016/7/10.
 */
public class BaseApplication extends Application {
    private static BaseApplication mApp;
    private AppLib mAppLib;

    public static BaseApplication getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mAppLib = AppLib.onCreate(mApp);
        PreferencesManager.getInstance().put(BundleKey.TOKEN, "834320403214");
        OkHttpUtils.init(this);
    }
}

