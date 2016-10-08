package com.ccy.chuchaiyi.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.ccy.chuchaiyi.db.DaoMaster;
import com.ccy.chuchaiyi.db.DaoSession;
import com.ccy.chuchaiyi.db.UserInfo;
import com.ccy.chuchaiyi.login.LoginActivity;
import com.ccy.chuchaiyi.main.MainActivity;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.user.UserMgr;
import com.gjj.applibrary.app.AppLib;
import com.gjj.applibrary.event.EventOfTokenError;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.http.model.BundleKey;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.network.NetworkStateMgr;
import com.gjj.applibrary.task.BackgroundTaskExecutor;
import com.gjj.applibrary.task.ForegroundTaskExecutor;
import com.gjj.applibrary.util.PreferencesManager;
import com.gjj.applibrary.util.ToastUtil;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.model.HttpHeaders;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

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

//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);     		// 初始化 JPush

        ForegroundTaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                initDB();
                NetworkStateMgr.getInstance();
                mUserMgr = new UserMgr();
                refreshUserInfo();
                AppLib.setInitialized(true);
            }
        });
    }

    private void refreshUserInfo() {
        if(mUserMgr.isLogin()) {
            OkHttpUtils.post(ApiConstants.REFRESH_LOGIN_INFO)
                    .tag(mApp)
                    .cacheMode(CacheMode.NO_CACHE)
                    .execute(new JsonCallback<UserInfo>(UserInfo.class) {
                        @Override
                        public void onResponse(boolean isFromCache, final UserInfo rspInfo, Request request, @Nullable Response response) {
                            UserMgr userMgr = BaseApplication.getUserMgr();
                            userMgr.saveUserInfo(rspInfo);
                        }
                        @Override
                        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        }
                    });
        }
    }
    public static UserMgr getUserMgr() {
        return mApp.mUserMgr;
    }
    public void initDB() {
        getDaoSession(this);
    }

    public static DaoMaster getDaoMaster(Context context) {
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
    public static DaoSession getDaoSession(Context context) {
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

