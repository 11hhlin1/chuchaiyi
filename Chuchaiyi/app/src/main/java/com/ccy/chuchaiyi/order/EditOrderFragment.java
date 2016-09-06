package com.ccy.chuchaiyi.order;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.app.BaseApplication;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.contact.ChoosePassengerFragment;
import com.ccy.chuchaiyi.db.UserInfo;
import com.ccy.chuchaiyi.flight.FlightInfo;
import com.ccy.chuchaiyi.flight.PolicyResultInfo;
import com.ccy.chuchaiyi.util.CallUtil;
import com.gjj.applibrary.util.DateUtil;
import com.gjj.applibrary.util.Util;

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
    @Bind(R.id.safe_fee_value)
    TextView safeFeeValue;
    @Bind(R.id.safe_fee_check_icon)
    CheckBox safeFeeCheckIcon;
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
    private PopupWindow mPickUpPopWindow;
    private int mPassengerNum = 1;
    private int planePrice;
    private int airportFee;
    private int oilFee;
    private int safeFeeMoney;
    private int delayFeeMoney;
    @Override
    public void onRightBtnClick() {
        super.onRightBtnClick();
        CallUtil.askForMakeCall(getActivity(), "", "400-600-2084");
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
        if (mReturnFlightInfo == null) {
            arriveTime.setVisibility(View.GONE);
            arriveAirport.setVisibility(View.GONE);
            setDepartureTv();
            planePrice = mBunksBean.getBunkPrice().getFactBunkPrice();
            airportFee = mFlightInfo.getAirportFee();
            oilFee = mFlightInfo.getOilFee();
            priceValue.setText(getString(R.string.money_no_end, String.valueOf(planePrice)));
            jiJianValue.setText(getString(R.string.money_no_end, String.valueOf(airportFee)));
            oilFeeValue.setText(getString(R.string.money_no_end, String.valueOf(oilFee)));
            StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
            delayFeeMoney = mFlightInfo.getInsuranceFeeUnitPrice();
            stringBuilder.append(getString(R.string.money_no_end, delayFeeMoney)).append(" * ").append("1份");
            safeFeeValue.setText(stringBuilder.toString());
            delayFeeValue.setText(stringBuilder.toString());
            int amount = planePrice + airportFee + oilFee;
            orderMoney.setText(getString(R.string.money_no_end, amount));
        } else {
            setDepartureTv();
            setReturnTv();
            planePrice = mBunksBean.getBunkPrice().getFactBunkPrice() + mReturnBunksBean.getBunkPrice().getFactBunkPrice();
            priceValue.setText(getString(R.string.money_no_end, String.valueOf(planePrice)));
            airportFee = mFlightInfo.getAirportFee() + mReturnFlightInfo.getAirportFee();
            jiJianValue.setText(getString(R.string.money_no_end, String.valueOf(airportFee)));
            oilFee = mFlightInfo.getOilFee() + mReturnFlightInfo.getOilFee();
            oilFeeValue.setText(getString(R.string.money_no_end, String.valueOf(oilFee)));
            StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
            delayFeeMoney = mFlightInfo.getInsuranceFeeUnitPrice();
            stringBuilder.append(getString(R.string.money_no_end, delayFeeMoney)).append(" * ").append("2份");
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
        String dates  = DateUtil.getDateTitle(date);
        StringBuilder dateRes = Util.getThreadSafeStringBuilder();
        dateRes.append(dates).append("  ").append(mBunksBean.getBunkName());
        setOutTime.setText(dateRes.toString());
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(departureBean.getAirportName()).append(" - ").append(mFlightInfo.getArrival().getAirportName());
        setOutAirport.setText(stringBuilder.toString());
    }

    private void setReturnTv() {
        FlightInfo.DepartureBean departureBean = mReturnFlightInfo.getDeparture();
        String date = departureBean.getDateTime().split(" ")[0];
        String dates  = DateUtil.getDateTitle(date);
        StringBuilder dateRes = Util.getThreadSafeStringBuilder();
        dateRes.append(dates).append("  ").append(mReturnBunksBean.getBunkName());
        arriveTime.setText(dateRes);
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(departureBean.getAirportName()).append(" - ").append(mReturnFlightInfo.getArrival().getAirportName());
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
                Bundle bundle = new Bundle();
                bundle.putString("start", mFlightInfo.getDeparture().getDateTime());
                bundle.putString("end", mFlightInfo.getArrival().getDateTime());
                PageSwitcher.switchToTopNavPage(getActivity(), ChoosePassengerFragment.class, bundle, getString(R.string.choose_passenger),null);
                break;
            case R.id.order_money_detail:
                showPickupWindow();
                break;
            case R.id.commit_order:
                break;
        }
    }


    /**
     * 显示选择框
     */
    @SuppressWarnings("unused")
    private void showPickupWindow() {
        // dismissConstructNoticeWindow();
        View contentView;
        PopupWindow popupWindow = mPickUpPopWindow;
        if (popupWindow == null) {
            contentView = LayoutInflater.from(getActivity()).inflate(
                    R.layout.order_pop_layout, null);
            ViewHolder viewHolder = new ViewHolder(contentView);
            StringBuilder planePriceStr = Util.getThreadSafeStringBuilder();
            planePriceStr.append(getString(R.string.money_no_end, planePrice)).append(" * 1人");
            viewHolder.ticketValue.setText(planePriceStr);
            StringBuilder airportFeeStr = Util.getThreadSafeStringBuilder();
            planePriceStr.append(getString(R.string.money_no_end, airportFee)).append(" * 1人");
            viewHolder.airportFee.setText(planePriceStr);
            StringBuilder oilFeeStr = Util.getThreadSafeStringBuilder();
            planePriceStr.append(getString(R.string.money_no_end, oilFee)).append(" * 1人");
            viewHolder.oilFee.setText(planePriceStr);
            StringBuilder delayStr = Util.getThreadSafeStringBuilder();
            planePriceStr.append(getString(R.string.money_no_end, delayFeeMoney)).append(" * 1人");
            viewHolder.delayFee.setText(planePriceStr);
            StringBuilder safeStr = Util.getThreadSafeStringBuilder();
            planePriceStr.append(getString(R.string.money_no_end, safeFeeMoney)).append(" * 1人");
            viewHolder.safeFee.setText(planePriceStr);
            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissConstructNoticeWindow();
                }
            });
            Rect r = new Rect();
            mRootView.getWindowVisibleDisplayFrame(r);
            final int[] location = new int[2];
            mRootView.getLocationOnScreen(location);
            int height = getResources().getDimensionPixelSize(R.dimen.margin_88p);
            popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, r.bottom - height, false);
            mPickUpPopWindow = popupWindow;
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setAnimationStyle(R.style.popwin_anim_style);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                }
            });
        } else {
            contentView = popupWindow.getContentView();
        }
        //判读window是否显示，消失了就执行动画
        if (!popupWindow.isShowing()) {
            Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.effect_bg_show);
            contentView.startAnimation(animation2);
        }
        popupWindow.showAtLocation(mRootView, Gravity.TOP, 0, 0);

    }

    /**
     * 取消工程消息弹出框
     *
     * @return
     */
    private void dismissConstructNoticeWindow() {
        PopupWindow pickUpPopWindow = mPickUpPopWindow;
        if (null != pickUpPopWindow && pickUpPopWindow.isShowing()) {
            pickUpPopWindow.dismiss();
        }
    }

    static class ViewHolder {
        @Bind(R.id.pop_ticket_value)
        TextView ticketValue;
        @Bind(R.id.pop_airport_fee)
        TextView airportFee;
        @Bind(R.id.pop_oil_fee)
        TextView oilFee;
        @Bind(R.id.pop_safe_fee)
        TextView safeFee;
        @Bind(R.id.pop_delay_fee)
        TextView delayFee;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
