package com.ccy.chuchaiyi.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.login.LoginActivity;
import com.ccy.chuchaiyi.main.MainActivity;
import com.gjj.applibrary.http.model.BundleKey;
import com.gjj.applibrary.task.MainTaskExecutor;
import com.gjj.applibrary.util.PreferencesManager;

/**
 * Created by Chuck on 2016/8/9.
 */
public class SplashActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MainTaskExecutor.scheduleTaskOnUiThread(500, new Runnable() {
            @Override
            public void run() {
                String token = PreferencesManager.getInstance().get(BundleKey.TOKEN);

                if(TextUtils.isEmpty(token)) {
                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
