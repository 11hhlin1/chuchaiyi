package com.ccy.chuchaiyi.app;

import android.app.Application;
import android.content.Intent;

import com.ccy.chuchaiyi.login.LoginActivity;
import com.gjj.applibrary.app.AppLib;
import com.gjj.applibrary.event.EventOfTokenError;
import com.gjj.applibrary.http.model.BundleKey;
import com.gjj.applibrary.util.PreferencesManager;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.model.HttpHeaders;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
//        PreferencesManager.getInstance().put(BundleKey.TOKEN, "834320403214");
        OkHttpUtils.init(this);
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.HEAD_KEY_ACCEPT, "application/json");
        headers.put(HttpHeaders.HEAD_KEY_CONTENT_TYPE, "application/json");
        OkHttpUtils.getInstance().addCommonHeaders(headers);
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goLogin(EventOfTokenError event) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

