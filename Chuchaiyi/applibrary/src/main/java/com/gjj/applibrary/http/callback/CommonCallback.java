package com.gjj.applibrary.http.callback;

import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.gjj.applibrary.event.EventOfTokenError;
import com.gjj.applibrary.http.model.BundleKey;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.util.PreferencesManager;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.request.BaseRequest;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import okhttp3.Response;

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
            request.headers(BundleKey.TOKEN, token);
        }
    }

    @Override
    public T parseNetworkResponse(Response response) throws Exception {
        String responseData = response.body().string();
        if (TextUtils.isEmpty(responseData)) return null;
        JSONObject jsonObject = new JSONObject(responseData);
        final String msg = jsonObject.optString("Message", "");
        final int code = jsonObject.optInt("Code", 0);
        switch (code) {
            case 401:
                //比如：用户授权信息无效，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                EventBus.getDefault().post(new EventOfTokenError());
                throw new IllegalStateException("用户授权信息无效");
            case 105:
                //比如：用户收取信息已过期，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("用户收取信息已过期");
            case 106:
                //比如：用户账户被禁用，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("用户账户被禁用");
            case 300:
                //比如：其他乱七八糟的等，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("其他乱七八糟的等");
            default:
                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + msg);
        }

    }
}