package com.ccy.chuchaiyi.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.ccy.chuchaiyi.db.DaoMaster;
import com.ccy.chuchaiyi.db.DaoSession;
import com.ccy.chuchaiyi.login.LoginActivity;
import com.ccy.chuchaiyi.user.UserMgr;
import com.gjj.applibrary.app.AppLib;
import com.gjj.applibrary.event.EventOfTokenError;
import com.gjj.applibrary.http.model.BundleKey;
import com.gjj.applibrary.task.ForegroundTaskExecutor;
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
    private static final String DATABASE_NAME = "notes-db";
    private static BaseApplication mApp;
    private AppLib mAppLib;
    private UserMgr mUserMgr;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
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

        ForegroundTaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                initDB();
                mUserMgr = new UserMgr();
                AppLib.setInitialized(true);
            }
        });
    }

    public static UserMgr getUserMgr() {
        return mApp.mUserMgr;
    }
    public void initDB() {
        getDaoSession(this);
    }

    public static DaoMaster getDaoMaster(Context context)
    {
        if (daoMaster == null)
        {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context)
    {
        if (daoSession == null) {
            if (daoMaster == null)
            {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goLogin(EventOfTokenError event) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

