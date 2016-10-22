package com.ccy.chuchaiyi.login;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.widget.EditTextWithDel;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/8/11.
 */
public class ForgetPswFragment extends BaseFragment {


    @Bind(R.id.send_again_btn)
    Button mGetSmsBtn;

    @Bind(R.id.login_name)
    EditTextWithDel loginName;

    @Bind(R.id.sms_code)
    EditTextWithDel smsCode;

    @Bind(R.id.psw_icon)
    CheckBox pswIcon;

    @Bind(R.id.new_psw)
    EditTextWithDel newPsw;

    @Bind(R.id.btn_sure)
    Button btnSure;

    @OnClick(R.id.btn_sure)
    void commit() {

        if(mgetSmsCode == null)
            return;
        String sms = smsCode.getText().toString().trim();
        if (!mgetSmsCode.getSmsValidateCode().equals(sms)) {
            ToastUtil.shortToast(R.string.check_sms_code_fail);
            return;
        }
        String psw = newPsw.getText().toString().trim();
        if (TextUtils.isEmpty(psw)) {
            ToastUtil.shortToast(R.string.hint_login_new_psw);
            return;
        }
        StringBuilder url = Util.getThreadSafeStringBuilder();
        url.append(ApiConstants.RESET_PSW).append("?corpId=").append(mgetSmsCode.getCorpId()).append("&employeeId=").append(mgetSmsCode.getEmployeeId()).append("&newPassword=").append(psw);
        OkHttpUtils.post(url.toString())
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
//                .postJson(jsonObject.toString())
                .execute(new JsonCallback<GetSmsCode>(GetSmsCode.class) {

                    @Override
                    public void onResponse(boolean b, GetSmsCode getSmsCode, Request request, @Nullable Response response) {
                        PageSwitcher.switchToTopNavPage(getActivity(), ChangePswSuccessFragment.class, null, getString(R.string.set_psw_done),null);
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }
                });

    }
    /**
     * 短信验证有效时间ms
     */
    private static final int SMS_VALIDITY = 60000;
    private InputMethodManager mInputMethodManager;
    private Counter mCounter;
    private GetSmsCode mgetSmsCode;
    @OnClick(R.id.send_again_btn)
    void getCode() {
        String un = loginName.getText().toString();
        if (!Util.isMobileNO(un)) {
            ToastUtil.shortToast(R.string.enter_mobile_error);
            return;
        }

//        HashMap<String, String> params = new HashMap<>();
//        params.put("mobile", un);
//        final JSONObject jsonObject = new JSONObject(params);
        StringBuilder url = Util.getThreadSafeStringBuilder();
        url.append(ApiConstants.GET_SMS_CODE).append("?mobile=").append(un);
        OkHttpUtils.post(url.toString())
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
//                .postJson(jsonObject.toString())
                .execute(new JsonCallback<GetSmsCode>(GetSmsCode.class) {

                    @Override
                    public void onResponse(boolean b, GetSmsCode getSmsCode, Request request, @Nullable Response response) {
                        mgetSmsCode = getSmsCode;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                countDownSms();
                            }
                        });
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        ToastUtil.shortToast(R.string.get_sms_code_fail);
                    }
                });
    }
    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_get_psw;
    }

    @Override
    public void initView() {
        pswIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    newPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    newPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

    }
    /**
     * 短信按钮不可点击
     *
     * @param text
     */
    private void disableGetSmsBtn(String text) {
        Button getSmsBtn = mGetSmsBtn;
        getSmsBtn.setEnabled(false);
        if (null != text) {
            getSmsBtn.setText(text);
        }
        getSmsBtn.setTextColor(getResources().getColor(R.color.secondary_gray));
    }
    /**
     * 短信重新发送倒计时
     */
    private void countDownSms() {
        disableGetSmsBtn(null);
        // mIdentifyCodeET.setEnabled(true);
        // mIdentifyCodeET.requestFocus();
        mInputMethodManager.showSoftInput(smsCode, InputMethodManager.SHOW_IMPLICIT);
        if (mCounter != null) {
            mCounter.cancel();
        }
        mCounter = new Counter(SMS_VALIDITY, 1000l);
        mCounter.start();
    }

    public void hideKeyboardForCurrentFocus() {
        if (getActivity().getCurrentFocus() != null) {
            mInputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    /* 定义一个倒计时的内部类 */
    class Counter extends CountDownTimer {
        public Counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (getActivity() == null) {
                return;
            }
            enableGetSmsBtn(getString(R.string.send_sms_again));
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (getActivity() == null) {
                return;
            }
            mGetSmsBtn.setText(sendSmsCountDown(millisUntilFinished / 1000l));
        }
    }

    /**
     * 短信按钮可点击
     *
     * @param text
     */
    private void enableGetSmsBtn(String text) {
        Button getSmsBtn = mGetSmsBtn;
        getSmsBtn.setEnabled(true);
        if (null != text) {
            getSmsBtn.setText(text);
        }
        getSmsBtn.setTextColor(getResources().getColor(R.color.secondary_gray));
    }


    /**
     * 设置按钮可用
     *
     * @param ms
     */


    private String sendSmsCountDown(long ms) {
        StringBuilder tip = Util.getThreadSafeStringBuilder();
        return tip.append("再次发送").append("(").append(ms).append(")").toString();
    }
}
