package com.ccy.chuchaiyi.base;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.ccy.chuchaiyi.app.BaseApplication;
import com.ccy.chuchaiyi.db.UserInfo;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.user.UserMgr;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;


import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/11/7.
 */

public class BaseFragmentActivity extends FragmentActivity {
    private boolean isLeave = false;
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        isLeave = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isLeave) {
            refreshUserInfo();
            isLeave = false;
        }
    }

    private void refreshUserInfo() {
        if(BaseApplication.getUserMgr().isLogin()) {
            OkHttpUtils.post(ApiConstants.REFRESH_LOGIN_INFO)
                    .tag(this)
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
}
