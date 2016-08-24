package com.ccy.chuchaiyi.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.app.BaseApplication;
import com.ccy.chuchaiyi.login.LoginActivity;
import com.ccy.chuchaiyi.main.MainActivity;
import com.ccy.chuchaiyi.user.UserMgr;
import com.gjj.applibrary.app.AppLib;
import com.gjj.applibrary.http.model.BundleKey;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.task.BackgroundTaskExecutor;
import com.gjj.applibrary.task.ForegroundTaskExecutor;
import com.gjj.applibrary.task.MainTaskExecutor;
import com.gjj.applibrary.util.PreferencesManager;

/**
 * Created by Chuck on 2016/8/9.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        BackgroundTaskExecutor.scheduleTask(1000, new Runnable() {
            @Override
            public void run() {
                while (!AppLib.isInitialized()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        L.e(e);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UserMgr userMgr = BaseApplication.getUserMgr();
                        if(userMgr.isLogin()) {
                            Intent intent = new Intent();
                            intent.setClass(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
}
