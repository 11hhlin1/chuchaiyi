package com.ccy.chuchaiyi.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.app.BaseApplication;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.db.UserInfo;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.widget.EditTextWithDel;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.util.MD5Util;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/8/12.
 */
public class ChangePswFragment extends BaseFragment {
    @Bind(R.id.old_psw)
    EditTextWithDel mOldPsw;
    @Bind(R.id.new_psw)
    EditTextWithDel mNewPsw;
    @Bind(R.id.new_psw_again)
    EditTextWithDel mNewPswAgain;
    @Bind(R.id.btn_sure)
    Button btnSure;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_change_psw;
    }

    @Override
    public void initView() {
    }

    @OnClick(R.id.btn_sure)
    public void onClick() {
        final String oldPsw = mOldPsw.getText().toString().trim().replaceAll(" ", "");
        String newPsw = mNewPsw.getText().toString().trim().replaceAll(" ", "");
        String newPswAgain = mNewPswAgain.getText().toString().trim().replaceAll(" ", "");
        if (TextUtils.isEmpty(oldPsw) || TextUtils.isEmpty(newPsw)) {
            ToastUtil.shortToast(R.string.hint_login_psw);
            return;
        }

        if(!newPsw.equals(newPswAgain)) {
            ToastUtil.shortToast(R.string.enter_psw_error);
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("oldPassword", oldPsw);
        params.put("newPassword", newPsw);
        showLoadingDialog(R.string.submitting, true);

//        final JSONObject jsonObject = new JSONObject(params);
        StringBuilder url = Util.getThreadSafeStringBuilder();
        url.append(ApiConstants.CHANGE_PSW).append("?oldPassword=").append(oldPsw).append("&newPassword=").append(newPsw);
        OkHttpUtils.post(url.toString())
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
//                .params(params)
//                .postJson(JSON.toJSONString(params))
                .execute(new JsonCallback<String>(String.class) {
                    @Override
                    public void onResponse(boolean b, String s, Request request, @Nullable Response response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                                onBackPressed();
                                ToastUtil.shortToast(R.string.change_psw_success);
                            }
                        });

                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                            }
                        });
                    }
                });
    }
}
