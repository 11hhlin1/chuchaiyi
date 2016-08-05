package com.gjj.applibrary.http.callback;

import android.text.TextUtils;

import com.gjj.applibrary.http.model.BundleKey;
import com.gjj.applibrary.util.PreferencesManager;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.request.BaseRequest;

/**
 * Created by Chuck on 2016/7/26.
 */
public abstract class CommonCallback<T> extends AbsCallback<T> {
    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //如果账户已经登录，就添加 token 等等
        String token = PreferencesManager.getInstance().get(BundleKey.TOKEN);
        if(!TextUtils.isEmpty(token)) {
            request.params(BundleKey.TOKEN, token);
        }
    }
}