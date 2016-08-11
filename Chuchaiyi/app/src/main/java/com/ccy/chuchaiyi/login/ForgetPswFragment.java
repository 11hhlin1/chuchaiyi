package com.ccy.chuchaiyi.login;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.widget.EditTextWithDel;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/8/11.
 */
public class ForgetPswFragment extends BaseFragment {


    @Bind(R.id.send_again_btn)
    Button sendAgainBtn;

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

    }

    @OnClick(R.id.send_again_btn)
    void getCode() {

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
    }

}
