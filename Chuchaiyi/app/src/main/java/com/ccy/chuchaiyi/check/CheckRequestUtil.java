package com.ccy.chuchaiyi.check;

import android.content.Context;
import android.support.annotation.Nullable;

import com.ccy.chuchaiyi.event.EventOfAgreeCheck;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/9/12.
 */
public class CheckRequestUtil {
    public static void checkApproval(Context context, String url, int approvalId) {
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(url).append("?").append("approvalId=").append(approvalId)
                .append("&opinion=").append(" ");
        OkHttpUtils.post(stringBuilder.toString())
                .tag(context)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<String>(String.class) {
                    @Override
                    public void onResponse(boolean b, String s, Request request, @Nullable Response response) {
                        EventBus.getDefault().post(new EventOfAgreeCheck());
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }
                });
    }
}
