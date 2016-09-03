package com.ccy.chuchaiyi.order;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.contact.ChooseCheckNumFragment;
import com.ccy.chuchaiyi.contact.PassengerInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/21.
 */
public class EditPassengerFragment extends BaseFragment {
    @Bind(R.id.passenger_tv)
    TextView passengerTv;
    @Bind(R.id.passenger_value)
    TextView passengerValue;
    @Bind(R.id.card_type_tv)
    TextView cardTypeTv;
    @Bind(R.id.card_type_value)
    TextView cardTypeValue;
    @Bind(R.id.card_num)
    TextView cardNum;
    @Bind(R.id.et_card)
    EditText etCard;
    @Bind(R.id.choose_check_num)
    RelativeLayout chooseCheckNum;
    @Bind(R.id.choose_project_rl)
    RelativeLayout chooseProjectRl;

    private String startTime;
    private String endTime;

    @OnClick(R.id.choose_check_num)
    void chooseNum() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("passenger", mPassengerInfo);
        bundle.putString("start", startTime);
        PageSwitcher.switchToTopNavPage(getActivity(), ChooseCheckNumFragment.class, bundle, getString(R.string.choose_check_num),null);

    }

    @OnClick(R.id.choose_project_rl)
    void chooseProject() {

    }
    private PassengerInfo mPassengerInfo;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_edit_passenger;
    }

    @Override
    public void initView() {

        Bundle bundle = getArguments();
        mPassengerInfo = (PassengerInfo) bundle.getSerializable("passenger");
        startTime = bundle.getString("start");
        endTime = bundle.getString("end");
        assert mPassengerInfo != null;
        passengerValue.setText(mPassengerInfo.getEmployeeName());
        cardTypeValue.setText(mPassengerInfo.getDefaultCertType());
        etCard.setText(mPassengerInfo.getDefaultCertNo());
    }
}
