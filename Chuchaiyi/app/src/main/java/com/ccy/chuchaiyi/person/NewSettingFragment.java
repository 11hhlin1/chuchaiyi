package com.ccy.chuchaiyi.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.widget.EaseSwitchButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/8/12.
 */
public class NewSettingFragment extends BaseFragment {


    @Bind(R.id.voice_switch_btn)
    EaseSwitchButton voiceSwitchBtn;
    @Bind(R.id.vibrate_switch_btn)
    EaseSwitchButton vibrateSwitchBtn;

    @OnClick(R.id.voice_switch_btn)
    void setVoiceSwitchBtn() {
        if(voiceSwitchBtn.isSwitchOpen()) {
            voiceSwitchBtn.closeSwitch();
        } else {
            voiceSwitchBtn.openSwitch();
        }
    }

    @OnClick(R.id.vibrate_switch_btn)
    void setVibrateSwitchBtn() {
        if(vibrateSwitchBtn.isSwitchOpen()) {
            vibrateSwitchBtn.closeSwitch();
        } else {
            vibrateSwitchBtn.openSwitch();
        }
    }
    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_new_setting;
    }

    @Override
    public void initView() {

    }

}
