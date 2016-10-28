package com.ccy.chuchaiyi.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.app.BaseApplication;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.db.UserInfo;
import com.ccy.chuchaiyi.main.MainActivity;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.user.UserMgr;
import com.ccy.chuchaiyi.widget.CustomProgressDialog;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.network.NetworkStateMgr;
import com.gjj.applibrary.task.BackgroundTaskExecutor;
import com.gjj.applibrary.task.MainTaskExecutor;
import com.gjj.applibrary.util.AndroidBug5497Workaround;
import com.gjj.applibrary.util.AndroidUtil;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.gjj.applibrary.widget.YScrollLinearLayout;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/8/9.
 */
public class LoginActivity extends Activity implements AndroidBug5497Workaround.OnKeyboardListener {

    private InputMethodManager mInputMethodManager;
    private CustomProgressDialog mLoginDialog;
    @Bind(R.id.login_name)
    EditText mAccountET;

    @Bind(R.id.login_identify_code)
    EditText mPwdET;

    @Bind(R.id.top_layout)
    YScrollLinearLayout mContentLayout;

    @Bind(R.id.forget_psw)
    TextView mForgetPswTV;

    @Bind(R.id.imageView1)
    ImageView mIconImg;
    private int[] mIconImgLocation;
    @OnClick(R.id.forget_psw)
    void forgetPsw() {
        PageSwitcher.switchToTopNavPage(this,ForgetPswFragment.class,null,getString(R.string.forget_psw_title),null);
    }
    @OnClick(R.id.btn_login)
    void login() {
        String un = mAccountET.getText().toString();
        if (!Util.isMobileNO(un) && !Util.isEmail(un)) {
            ToastUtil.shortToast(R.string.enter_mobile_error);
            return;
        }

        String sms = mPwdET.getText().toString();
        if (TextUtils.isEmpty(sms)) {
            ToastUtil.shortToast(R.string.hint_login_psw);
            return;
        }
        hideKeyboardForCurrentFocus();
        CustomProgressDialog loginDialog = mLoginDialog;
        if (null == loginDialog) {
            loginDialog = new CustomProgressDialog(this);
            mLoginDialog = loginDialog;
            loginDialog.setTipText(R.string.login_ing_tip);
            loginDialog.setCancelable(false);
            loginDialog.setCanceledOnTouchOutside(false);
            loginDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    onBackPressed();
                }
            });
        }
        loginDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("LoginName", un);
        params.put("Password", sms);
//        params.put("Password", MD5Util.md5Hex(sms));
        String versionName = AndroidUtil.getVersionName(this);
        params.put("AppVersion", versionName);
        params.put("DeviceType", android.os.Build.MODEL);
        params.put("DeviceId", AndroidUtil.getDeviceIMEI(this));
        params.put("OSVersion", android.os.Build.VERSION.RELEASE);
        final JSONObject jsonObject = new JSONObject(params);
        OkHttpUtils.post(ApiConstants.LOGIN)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .postJson(jsonObject.toString())
                .execute(new JsonCallback<UserInfo>(UserInfo.class) {
                    @Override
                    public void onResponse(boolean isFromCache, final UserInfo rspInfo, Request request, @Nullable Response response) {
                        dismissProgressDialog();
                        if(rspInfo != null) {
                            L.d("LoginActivity [%s]" , rspInfo);
                            BackgroundTaskExecutor.executeTask(new Runnable() {
                                @Override
                                public void run() {
                                    UserMgr userMgr = BaseApplication.getUserMgr();
                                    userMgr.saveUserInfo(rspInfo);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            JPushInterface.setAliasAndTags(getApplicationContext(), String.valueOf(rspInfo.getEmployeeId()), null, mAliasCallback);
                                            Intent intent = new Intent();
                                            intent.setClass(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            });

                        }
                    }
                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        dismissProgressDialog();
                    }
                });
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, final String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    L.i(logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    L.i(logs);
                    if (NetworkStateMgr.getInstance().isNetworkAvailable()) {
                        MainTaskExecutor.scheduleTaskOnUiThread(1000 * 60, new Runnable() {
                            @Override
                            public void run() {
                                JPushInterface.setAliasAndTags(getApplicationContext(), alias, null, mAliasCallback);
                            }
                        });
                    } else {
                        L.i("No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    L.e(logs);
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AndroidBug5497Workaround.assistActivity(this);
        ButterKnife.bind(this);
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mPwdET.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                login();
                return false;
            }
        });
    }

    public void hideKeyboardForCurrentFocus() {
        if (getCurrentFocus() != null) {
            mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onShow(boolean showKeyboard, int heightDifference) {
        int[] iconImgLocation = mIconImgLocation;
        if (iconImgLocation == null) {
            iconImgLocation = new int[2];
            mIconImgLocation = iconImgLocation;
            mIconImg.getLocationOnScreen(iconImgLocation);
        }
        int tm = iconImgLocation[1] + mIconImg.getHeight();
        if (tm > heightDifference) {
            tm = heightDifference;
        }
        if (showKeyboard) {
            mContentLayout.yScrollTo(tm, 300);
        } else {
            mContentLayout.yScrollTo(0, 300);
        }
    }

    /**
     * 关闭登录提示框
     */
    private void dismissProgressDialog() {
        if (null != mLoginDialog) {
            mLoginDialog.dismiss();
        }
    }
}
