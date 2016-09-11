package com.ccy.chuchaiyi.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.event.EventOfChangeTab;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/21.
 */
public class BookTicketSuccessFragment extends BaseFragment {


    @Bind(R.id.city_name)
    TextView cityName;
    @Bind(R.id.order_num)
    TextView orderNum;
    @Bind(R.id.passenger_list_tv)
    TextView passengerListTv;
    @Bind(R.id.back_index_btn)
    Button backIndexBtn;
    @Bind(R.id.back_order_btn)
    Button backOrderBtn;

    @OnClick(R.id.back_order_btn)
    void setBackOrderBtn() {
        backOrder();
    }

    @OnClick(R.id.back_index_btn)
    void setBackIndexBtn() {
        getActivity().finish();
    }

    @Override
    public void onRightBtnClick() {
        super.onRightBtnClick();
        getActivity().finish();
    }

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_book_success;
    }

    @Override
    public boolean goBack(boolean fromKeyboard) {
        backOrder();
        return super.goBack(fromKeyboard);
    }


    void backOrder() {
        getActivity().finish();
        EventOfChangeTab eventOfChangeTab = new EventOfChangeTab();
        eventOfChangeTab.mIndex = 2;
        EventBus.getDefault().post(eventOfChangeTab);
    }
    @Override
    public void initView() {
       Bundle bundle = getArguments();
        passengerListTv.setText(bundle.getString("passengers"));
        cityName.setText(bundle.getString("city"));
        orderNum.setText(bundle.getString("flight"));
    }


}
