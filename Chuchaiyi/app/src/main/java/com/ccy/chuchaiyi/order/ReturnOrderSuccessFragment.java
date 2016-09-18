package com.ccy.chuchaiyi.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.event.EventOfChangeTab;
import com.ccy.chuchaiyi.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/9/18.
 */
public class ReturnOrderSuccessFragment extends BaseFragment {
    @Bind(R.id.city_name)
    TextView cityName;
    @Bind(R.id.order_num)
    TextView orderNum;
    @Bind(R.id.back_order_list)
    Button backOrderList;
    @Bind(R.id.back_order_detail)
    Button backOrderDetail;
    @Bind(R.id.tip)
    TextView tip;
    private int orderId;

    @Override
    public boolean goBack(boolean fromKeyboard) {
        getActivity().finish();
        return super.goBack(fromKeyboard);
    }

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_return_order_success;
    }

    @Override
    public void onRightBtnClick() {
        super.onRightBtnClick();
        getActivity().finish();
        EventOfChangeTab eventOfChangeTab = new EventOfChangeTab();
        eventOfChangeTab.mIndex = 0;
        EventBus.getDefault().post(eventOfChangeTab);
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        tip.setText(bundle.getString("tip"));
        cityName.setText(bundle.getString("city"));
        orderNum.setText(getString(R.string.order_num_no_end, bundle.getString("orderNum")));
        orderId = bundle.getInt("orderId");

    }


    @OnClick({R.id.back_order_list, R.id.back_order_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_order_list:
                getActivity().finish();
                break;
            case R.id.back_order_detail:
                getActivity().finish();
                Bundle bundle = new Bundle();
                bundle.putInt("orderId", orderId);
                PageSwitcher.switchToTopNavPage(MainActivity.getMainActivity(), OrderDetailFragment.class, bundle, "订单详情", null);
                break;
        }
    }


}
