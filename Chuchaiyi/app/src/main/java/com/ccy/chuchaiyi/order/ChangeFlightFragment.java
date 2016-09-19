package com.ccy.chuchaiyi.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.gjj.applibrary.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/9/19.
 */
public class ChangeFlightFragment extends BaseFragment {
    @Bind(R.id.flight_city)
    TextView flightCity;
    @Bind(R.id.flight_time)
    TextView flightTime;
    @Bind(R.id.flight_company)
    TextView flightCompany;
    @Bind(R.id.set_out_date_tv)
    TextView setOutDateTv;
    @Bind(R.id.set_out_date)
    RelativeLayout setOutDate;
    @Bind(R.id.search_btn)
    Button searchBtn;
    private OrderInfo.OrdersBean ordersBean;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_change_flight;
    }

    @Override
    public void initView() {
        ordersBean = (OrderInfo.OrdersBean) getArguments().getSerializable("order");
        StringBuilder city = Util.getThreadSafeStringBuilder();
        city.append(ordersBean.getDepartureCityName()).append("-").append(ordersBean.getArrivalCityName());
        flightCity.setText(city.toString());
    }


    @OnClick({R.id.set_out_date, R.id.search_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_out_date:
                break;
            case R.id.search_btn:
                break;
        }
    }
}
