package com.ccy.chuchaiyi.order;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.app.BaseApplication;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.contact.ChoosePassengerFragment;
import com.ccy.chuchaiyi.contact.PassengerInfo;
import com.ccy.chuchaiyi.db.UserInfo;
import com.ccy.chuchaiyi.event.EventOfSelPassenger;
import com.ccy.chuchaiyi.flight.FlightInfo;
import com.ccy.chuchaiyi.flight.PolicyResultInfo;
import com.ccy.chuchaiyi.flight.StopInfoRsp;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.util.CallUtil;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.DateUtil;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

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
    private FlightInfoDialog mFlightInfoDialog;
    private List<Passenger> mPassengers;

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
        getStopInfo();
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
        EventBus.getDefault().register(this);
    }

    private void getStopInfo() {
        if (mFlightInfo.getStopInfo() != null) {
            requestStopInfo(mFlightInfo);
        }
        if (mReturnFlightInfo != null && mReturnFlightInfo.getStopInfo() != null) {
            requestStopInfo(mReturnFlightInfo);
        }
    }

    private void requestStopInfo(final FlightInfo flightInfo) {
        OkHttpUtils.get(ApiConstants.GET_FLIGHT_STOPS)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .params("flightNo", flightInfo.getFlightNo())
                .params("flightDate", flightInfo.getDeparture().getDateTime().split(" ")[0])
                .execute(new JsonCallback<StopInfoRsp>(StopInfoRsp.class) {
                    @Override
                    public void onResponse(boolean b, StopInfoRsp stopInfoRsp, Request request, @Nullable Response response) {
                        flightInfo.getStopInfo().setStopLocations(stopInfoRsp.getStops());
                    }
                });
    }

    private void setDepartureTv() {
        FlightInfo.DepartureBean departureBean = mFlightInfo.getDeparture();
//        String date = departureBean.getDateTime().split(" ")[0];
        String dates = DateUtil.getDateTitle(departureBean.getDateTime());
        StringBuilder dateRes = Util.getThreadSafeStringBuilder();
        dateRes.append(dates).append("  ").append(mBunksBean.getBunkName());
        setOutTime.setText(dateRes.toString());
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(departureBean.getAirportName()).append(" - ").append(mFlightInfo.getArrival().getAirportName());
        setOutAirport.setText(stringBuilder.toString());
    }

    private void setReturnTv() {
        FlightInfo.DepartureBean departureBean = mReturnFlightInfo.getDeparture();
//        String date = departureBean.getDateTime().split(" ")[0];
        String dates = DateUtil.getDateTitle(departureBean.getDateTime());
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
                showPlanDetailDialog();
                break;
            case R.id.return_change_tv:
                break;
            case R.id.add_passenger:
                Bundle bundle = new Bundle();
                bundle.putString("start", mFlightInfo.getDeparture().getDateTime());
                bundle.putString("end", mFlightInfo.getArrival().getDateTime());
                bundle.putInt("flag", ChoosePassengerFragment.IS_FROM_ORDER);
                PageSwitcher.switchToTopNavPage(getActivity(), ChoosePassengerFragment.class, bundle, getString(R.string.choose_passenger), null);
                break;
            case R.id.order_money_detail:
                showPickupWindow();
                break;
            case R.id.commit_order:
                commitOrder();
                break;
        }
    }

    private void commitOrder() {
        showLoadingDialog(R.string.commit, false);
        PlaceAskOrderRequest request = new PlaceAskOrderRequest();
        request.FirstRoute = mFlightInfo;
        request.ContactMobile = contactPhone.getText().toString();
        request.ContactName = contactName.getText().toString();
        request.FirstRoutePolicyInfo = mSetoutResonInfo;
        request.SecondRoute = mReturnFlightInfo;
        request.SecondRoutePolicyInfo = mReturnResonInfo;
        request.Passengers = mPassengers;
        OkHttpUtils.post(ApiConstants.COMMIT_ORDER)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .postJson(JSON.toJSONString(request))
                .execute(new JsonCallback<StopInfoRsp>(StopInfoRsp.class) {
                    @Override
                    public void onResponse(boolean b, StopInfoRsp stopInfoRsp, Request request, @Nullable Response response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                                ToastUtil.shortToast(R.string.commit_success);
                            }
                        });
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                            }
                        });
                    }
                });
    }

    private void showPlanDetailDialog() {
        if (mFlightInfoDialog == null) {
            List<FlightInfo> flightInfoList = new ArrayList<>();
            flightInfoList.add(mFlightInfo);
            if (mReturnFlightInfo != null) {
                flightInfoList.add(mReturnFlightInfo);
            }
            FlightInfoDialog flightInfoDialog = new FlightInfoDialog(getActivity(), flightInfoList);
            mFlightInfoDialog = flightInfoDialog;
            flightInfoDialog.setCanceledOnTouchOutside(true);
        }
        mFlightInfoDialog.show();

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setAddPassenger(EventOfSelPassenger event) {
        if (getActivity() == null) {
            return;
        }
        PassengerInfo passenger1 = event.mPassenger;
//        if(!mPassengers.contains(passenger1)) {
            PassengerViewHolder holder = inflatePassengerView(getActivity().getLayoutInflater());
            holder.passengerName.setText(passenger1.getEmployeeName());
            holder.passengerJob.setText(passenger1.getDepartmentName());
            holder.orderNum.setText(String.valueOf(event.ApprovalId));
        holder.projectNum.setText(event.ProjectName);
            holder.deletePassenger.setTag(passenger1);
            passengerLl.addView(holder.parent);


            Passenger passenger = new Passenger();
            passenger.ApprovalId = event.ApprovalId;
            passenger.BelongedDeptId = passenger1.getDepartmentId();
            passenger.CertNo = passenger1.getDefaultCertNo();
            passenger.CertType = passenger1.getDefaultCertType();
            passenger.EmployeeId = passenger1.getEmployeeId();
            passenger.InsuranceCount = 1;
            passenger.Mobile = passenger1.getMobile();
            passenger.IsEmployee = true;
            passenger.PassengerType = "Adult";
            passenger.ProjectId = event.ProjectId;
            passenger.PassengerName = passenger1.getEmployeeName();
            passenger.ReceiveFlightDynamic = true;
            mPassengers.add(passenger);
//        }
    }

    private PassengerViewHolder inflatePassengerView(LayoutInflater inflater) {
        View child = inflater.inflate(R.layout.passenger_item, null);
        PassengerViewHolder holder = new PassengerViewHolder(child);
        return holder;
    }

    class PassengerViewHolder {
        @Bind(R.id.delete_passenger)
        ImageView deletePassenger;
        @Bind(R.id.passenger_name)
        TextView passengerName;
        @Bind(R.id.order_num)
        TextView orderNum;
        @Bind(R.id.project_num)
        TextView projectNum;
        @Bind(R.id.passenger_job)
        TextView passengerJob;
        View parent;
        PassengerViewHolder(View view) {
            parent = view;
            ButterKnife.bind(this, view);
        }
    }



}
