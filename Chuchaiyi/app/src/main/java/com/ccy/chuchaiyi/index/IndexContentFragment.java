package com.ccy.chuchaiyi.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/8/9.
 */
public class IndexContentFragment extends BaseFragment {
    @Bind(R.id.chufa_city)
    TextView chufaCity;
    @Bind(R.id.chufa_city_ll)
    LinearLayout chufaCityLl;
    @Bind(R.id.exchange)
    ImageView exchange;
    @Bind(R.id.arrive_city)
    TextView arriveCity;
    @Bind(R.id.arrive_city_ll)
    LinearLayout arriveCityLl;
    @Bind(R.id.set_out_date_tv)
    TextView setOutDateTv;
    @Bind(R.id.set_out_date)
    LinearLayout setOutDate;
    @Bind(R.id.seat_tv)
    TextView seatTv;
    @Bind(R.id.seat_wei)
    LinearLayout seatWei;
    @Bind(R.id.search_btn)
    Button searchBtn;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_index_content;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.chufa_city_ll, R.id.exchange, R.id.arrive_city_ll, R.id.set_out_date, R.id.seat_wei, R.id.search_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chufa_city_ll:
                break;
            case R.id.exchange:
                break;
            case R.id.arrive_city_ll:
                break;
            case R.id.set_out_date:
                break;
            case R.id.seat_wei:
                break;
            case R.id.search_btn:
                break;
        }
    }
}
