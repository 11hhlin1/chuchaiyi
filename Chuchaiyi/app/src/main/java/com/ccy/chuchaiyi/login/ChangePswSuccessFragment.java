package com.ccy.chuchaiyi.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/10/14.
 */

public class ChangePswSuccessFragment extends BaseFragment {
    @Bind(R.id.back_login_btn)
    Button backLoginBtn;

    @Override
    public boolean goBack(boolean fromKeyboard) {
        getActivity().finish();
        return super.goBack(fromKeyboard);
    }

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_change_psw_success;
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.back_login_btn)
    public void onClick() {
        getActivity().finish();
    }
}
