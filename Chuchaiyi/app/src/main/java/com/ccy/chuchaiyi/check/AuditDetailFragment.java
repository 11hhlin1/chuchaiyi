package com.ccy.chuchaiyi.check;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by user on 16/9/11.
 */
public class AuditDetailFragment extends BaseFragment {
    @Bind(R.id.check_state_tv)
    TextView checkStateTv;
    @Bind(R.id.low_price)
    TextView lowPrice;
    @Bind(R.id.pre_n_day)
    TextView preNDay;
    @Bind(R.id.discount_limit)
    TextView discountLimit;
    @Bind(R.id.cabin)
    TextView cabin;
    @Bind(R.id.order_detail_title)
    TextView orderDetailTitle;
    @Bind(R.id.flight_city)
    TextView flightCity;
    @Bind(R.id.flight_discount)
    TextView flightDiscount;
    @Bind(R.id.flight_money)
    TextView flightMoney;
    @Bind(R.id.set_out_date)
    TextView setOutDate;
    @Bind(R.id.set_out_time)
    TextView setOutTime;
    @Bind(R.id.set_out_airport)
    TextView setOutAirport;
    @Bind(R.id.stop_info_city)
    TextView stopInfoCity;
    @Bind(R.id.arrive_date)
    TextView arriveDate;
    @Bind(R.id.arrive_time)
    TextView arriveTime;
    @Bind(R.id.arrive_airport)
    TextView arriveAirport;
    @Bind(R.id.plane_detail)
    TextView planeDetail;
    @Bind(R.id.bottom_line)
    View bottomLine;
    @Bind(R.id.price_value)
    TextView priceValue;
    @Bind(R.id.ji_jian_value)
    TextView jiJianValue;
    @Bind(R.id.oil_fee_value)
    TextView oilFeeValue;
    @Bind(R.id.safe_fee_value)
    TextView safeFeeValue;
    @Bind(R.id.check_state_ll)
    LinearLayout checkStateLl;
    @Bind(R.id.check_state)
    TextView checkState;
    @Bind(R.id.handle_btn)
    Button handleBtn;
    @Bind(R.id.handle_btn_left)
    Button handleBtnLeft;
    @Bind(R.id.bottom_rl)
    RelativeLayout bottomRl;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_audit_detail;
    }

    @Override
    public void initView() {

    }

}
