package com.gjj.applibrary.http.callback;

import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.gjj.applibrary.http.model.BaseList;
import com.lzy.okhttputils.OkHttpUtils;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * Created by Administrator on 2016/8/14.
 */
public abstract class ListCallback<T> extends CommonCallback<BaseList<T>> {
    private Class<T> clazz;
    public ListCallback(Class<T> clazz) {
        this.clazz = clazz;
    }
    @Override
    public BaseList<T> parseNetworkResponse(Response response) throws Exception {
        String responseData = response.body().string();
        if (TextUtils.isEmpty(responseData)) return null;
        JSONObject jsonObject = new JSONObject(responseData);
        final String msg = jsonObject.optString("msg", "");
        final int code = jsonObject.optInt("code", -1);
        String data = jsonObject.optString("data", "");
        switch (code) {
            case 0:
                BaseList baseList = new BaseList();
                if (clazz != null) {
                    baseList.list = JSON.parseArray(data, clazz);
                    return baseList;
                }
                break;
            case 104:
                //比如：用户授权信息无效，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("用户授权信息无效");
            case 105:
                //比如：用户收取信息已过期，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("用户收取信息已过期");
            case 106:
                //比如：用户账户被禁用，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("用户账户被禁用");
            default:
                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + msg);
        }
        //如果要更新UI，需要使用handler，可以如下方式实现，也可以自己写handler
        OkHttpUtils.getInstance().getDelivery().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(OkHttpUtils.getContext(), "错误代码：" + code + "，错误信息：" + msg, Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }


}
