package com.ccy.chuchaiyi.order;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.app.BaseApplication;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.db.UserInfo;
import com.ccy.chuchaiyi.flight.FlightInfo;
import com.ccy.chuchaiyi.flight.PolicyResultInfo;
import com.ccy.chuchaiyi.util.CallUtil;
import com.gjj.applibrary.util.DateUtil;
import com.gjj.applibrary.util.Util;

import butterknife.Bind;
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
    private FlightInfo mFlightInfo;
    private FlightInfo.BunksBean mBunksBean;
    private FlightInfo mReturnFlightInfo;
    private FlightInfo.BunksBean mReturnBunksBean;
    private PolicyResultInfo mSetoutResonInfo;
    private PolicyResultInfo mReturnResonInfo;

    @Override
    public void onRightBtnClick() {
        super.onRightBtnClick();
        CallUtil.askForMakeCall(getActivity(),"", "400-600-2084");
    }

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_edit_order;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        mFlightInfo = (FlightInfo) bundle.getSerializable("SetOutFlightInfo");
        mBunksBean = (FlightInfo.BunksBean) bundle.getSerializable("SetOutBunksBean");
        mReturnFlightInfo = (FlightInfo) bundle.getSerializable("ReturnFlightInfo");
        mReturnBunksBean = (FlightInfo.BunksBean) bundle.getSerializable("ReturnBunksBean");
        mSetoutResonInfo = (PolicyResultInfo) bundle.getSerializable("SetOutWarningInfoBean");
        mReturnResonInfo = (PolicyResultInfo) bundle.getSerializable("ReturnWarningInfoBean");
        if(mReturnFlightInfo == null) {
            arriveTime.setVisibility(View.GONE);
            arriveAirport.setVisibility(View.GONE);
            setDepartureTv();
            int planePrice = mBunksBean.getBunkPrice().getFactBunkPrice();
            int airportFee = mFlightInfo.getAirportFee();
            int oilFee = mFlightInfo.getOilFee();
            priceValue.setText(getString(R.string.money_no_end,String.valueOf(planePrice)));
            jiJianValue.setText(getString(R.string.money_no_end,String.valueOf(airportFee)));
            oilFeeValue.setText(getString(R.string.money_no_end,String.valueOf(oilFee)));
            StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
            stringBuilder.append(getString(R.string.money_no_end, mFlightInfo.getInsuranceFeeUnitPrice())).append(" * ").append("1份");
            safeFeeValue.setText(stringBuilder.toString());
            delayFeeValue.setText(stringBuilder.toString());
            int amount = planePrice + airportFee + oilFee;
            orderMoney.setText(getString(R.string.money_no_end, amount));
        } else {
            setDepartureTv();
            setReturnTv();
            int planePrice = mBunksBean.getBunkPrice().getFactBunkPrice() + mReturnBunksBean.getBunkPrice().getFactBunkPrice();
            priceValue.setText(getString(R.string.money_no_end,String.valueOf(planePrice)));
            int airportFee = mFlightInfo.getAirportFee()+ mReturnFlightInfo.getAirportFee();
            jiJianValue.setText(getString(R.string.money_no_end,String.valueOf(airportFee)));
            int oilFee = mFlightInfo.getOilFee()+ mReturnFlightInfo.getOilFee();
            oilFeeValue.setText(getString(R.string.money_no_end,String.valueOf(oilFee)));
            StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
            stringBuilder.append(getString(R.string.money_no_end, mFlightInfo.getInsuranceFeeUnitPrice())).append(" * ").append("2份");
            safeFeeValue.setText(stringBuilder.toString());
            delayFeeValue.setText(stringBuilder.toString());
            int amount = planePrice + airportFee + oilFee;
            orderMoney.setText(getString(R.string.money_no_end, amount));
        }
        UserInfo user = BaseApplication.getUserMgr().getUser();
        contactName.setText(user.getEmployeeName());
        contactPhone.setText(user.getMobile());
    }

    private void setDepartureTv() {
        FlightInfo.DepartureBean departureBean = mFlightInfo.getDeparture();
        String date = departureBean.getDateTime().split(" ")[0];
        StringBuilder dateRes = Util.getThreadSafeStringBuilder();
        dateRes.append(DateUtil.getDateTitle(date)).append("  ").append(mBunksBean.getBunkName());
        setOutTime.setText(DateUtil.getDateTitle(date));
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(departureBean.getAirportName()).append("-").append(mFlightInfo.getArrival().getAirportName());
        setOutAirport.setText(stringBuilder.toString());
    }

    private void setReturnTv() {
        FlightInfo.DepartureBean departureBean = mReturnFlightInfo.getDeparture();
        String date = departureBean.getDateTime().split(" ")[0];
        StringBuilder dateRes = Util.getThreadSafeStringBuilder();
        dateRes.append(DateUtil.getDateTitle(date)).append("  ").append(mReturnBunksBean.getBunkName());
        arriveTime.setText(DateUtil.getDateTitle(date));
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(departureBean.getAirportName()).append("-").append(mReturnFlightInfo.getArrival().getAirportName());
        arriveAirport.setText(stringBuilder.toString());
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
