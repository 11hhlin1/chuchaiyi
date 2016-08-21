package com.ccy.chuchaiyi.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/21.
 */
public class EditOrderFragment extends BaseFragment {
    @Bind(R.id.set_out_time)
    TextView setOutTime;
    @Bind(R.id.set_out_airport)
    TextView setOutAirport;
    @Bind(R.id.arrive_time)
    TextView arriveTime;
    @Bind(R.id.arrive_airport)
    TextView arriveAirport;
    @Bind(R.id.plane_detail_rl)
    RelativeLayout planeDetailRl;
    @Bind(R.id.return_change_tv)
    TextView returnChangeTv;
    @Bind(R.id.price_tv)
    TextView priceTv;
    @Bind(R.id.price_value)
    TextView priceValue;
    @Bind(R.id.ji_jian_tv)
    TextView jiJianTv;
    @Bind(R.id.ji_jian_value)
    TextView jiJianValue;
    @Bind(R.id.oil_fee_tv)
    TextView oilFeeTv;
    @Bind(R.id.oil_fee_value)
    TextView oilFeeValue;
    @Bind(R.id.add_passenger)
    TextView addPassenger;
    @Bind(R.id.passenger_ll)
    LinearLayout passengerLl;
    @Bind(R.id.safe_fee)
    TextView safeFee;
    @Bind(R.id.safe_fee_value)
    TextView safeFeeValue;
    @Bind(R.id.safe_fee_check_icon)
    CheckBox safeFeeCheckIcon;
    @Bind(R.id.delay_fee)
    TextView delayFee;
    @Bind(R.id.delay_fee_value)
    TextView delayFeeValue;
    @Bind(R.id.delay_fee_check_icon)
    CheckBox delayFeeCheckIcon;
    @Bind(R.id.contact_name)
    TextView contactName;
    @Bind(R.id.contact_phone)
    TextView contactPhone;
    @Bind(R.id.pay_type)
    TextView payType;
    @Bind(R.id.order_money)
    TextView orderMoney;
    @Bind(R.id.order_money_detail)
    TextView orderMoneyDetail;
    @Bind(R.id.commit_order)
    Button commitOrder;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_edit_order;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.plane_detail_rl, R.id.return_change_tv, R.id.add_passenger, R.id.order_money_detail, R.id.commit_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plane_detail_rl:
                break;
            case R.id.return_change_tv:
                break;
            case R.id.add_passenger:
                break;
            case R.id.order_money_detail:
                break;
            case R.id.commit_order:
                break;
        }
    }
}
