package com.ccy.chuchaiyi.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.check.AuthorizeDetailRsp;
import com.ccy.chuchaiyi.check.CheckDetailRsp;
import com.ccy.chuchaiyi.check.PersonalViewHolder;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.util.DiscountUtil;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.DateUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by user on 16/9/15.
 */
public class OrderDetailFragment extends BaseFragment {
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
    @Bind(R.id.order_audit)
    TextView orderAudit;
    @Bind(R.id.more_than_audit)
    TextView moreThanAudit;
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
    @Bind(R.id.order_amount)
    TextView orderAmount;
    @Bind(R.id.order_num)
    TextView orderNum;
    @Bind(R.id.order_type)
    TextView orderType;
    @Bind(R.id.order_status)
    TextView orderStatus;
    @Bind(R.id.passenger_name)
    TextView passengerName;
    @Bind(R.id.passenger_job)
    TextView passengerJob;
    @Bind(R.id.contact_name)
    TextView contactName;
    @Bind(R.id.safe_fee_tv)
    TextView safeFeeTv;
    @Bind(R.id.handle_btn_1)
    Button handleBtn1;
    @Bind(R.id.handle_btn_2)
    Button handleBtn2;
    @Bind(R.id.handle_btn_3)
    Button handleBtn3;
    @Bind(R.id.bottom_rl)
    RelativeLayout bottomRl;
    int orderId;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_order_detail;
    }

    @Override
    public void initView() {
        orderId = getArguments().getInt("orderId");
        showLoadingDialog(R.string.submitting, false);
        OkHttpUtils.get(ApiConstants.GET_ORDER_DETAIL)
                .tag(this)
                .params("orderId", String.valueOf(orderId))
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<OrderDetailRsp>(OrderDetailRsp.class) {
                    @Override
                    public void onResponse(boolean b, final OrderDetailRsp orderDetailRsp, Request request, @Nullable Response response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                                AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean orderBean = orderDetailRsp.Order;
                                checkStateTv.setText(orderBean.getStatus());
                                AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean.PassengerBean.TravelPolicyInfoBean policy = orderBean.getPassenger().getTravelPolicyInfo();
                                StringBuilder lowPriceMsg = Util.getThreadSafeStringBuilder();
                                lowPriceMsg.append(policy.getLowPriceWarningMsg()).append("\n").append(policy.getNotLowPriceReason());
                                if(TextUtils.isEmpty(policy.getLowPriceWarningMsg())) {
                                    lowPrice.setVisibility(View.GONE);
                                } else {
                                    lowPrice.setVisibility(View.VISIBLE);
                                    lowPrice.setText(lowPriceMsg.toString());
                                }

                                StringBuilder preNDayMsg = Util.getThreadSafeStringBuilder();
                                lowPriceMsg.append(policy.getPreNDaysWarningMsg()).append("\n").append(policy.getNotPreNDaysReason());
                                if(TextUtils.isEmpty(policy.getPreNDaysWarningMsg())) {
                                    preNDay.setVisibility(View.GONE);
                                } else {
                                    preNDay.setVisibility(View.VISIBLE);
                                    preNDay.setText(preNDayMsg.toString());
                                }

                                if(TextUtils.isEmpty(policy.getDiscountLimitWarningMsg())) {
                                    discountLimit.setVisibility(View.GONE);
                                } else {
                                    discountLimit.setVisibility(View.VISIBLE);
                                    discountLimit.setText(policy.getDiscountLimitWarningMsg());
                                }
                                if(TextUtils.isEmpty(policy.getTwoCabinWarningMsg())) {
                                    cabin.setVisibility(View.GONE);
                                } else {
                                    cabin.setVisibility(View.VISIBLE);
                                    cabin.setText(policy.getTwoCabinWarningMsg());
                                }
                                StringBuilder city = Util.getThreadSafeStringBuilder();
                                AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean.RouteBean route = orderBean.getRoute();
                                AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean.RouteBean.DepartureBean departure = route.getDeparture();
                                AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean.RouteBean.ArrivalBean arrival = route.getArrival();
                                AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean.FeeInfoBean feeInfo = orderBean.getFeeInfo();
                                city.append(departure.getCityName()).append("-").append(arrival.getCityName());
                                flightCity.setText(city.toString());
                                StringBuilder bunk = Util.getThreadSafeStringBuilder();
                                bunk.append(DiscountUtil.getDis(route.getDiscount())).append(route.getBunkName());
                                flightDiscount.setText(bunk.toString());
                                flightMoney.setText(getString(R.string.money_no_end, orderBean.getFeeInfo().getPaymentAmount()));


                                String arrivalTime = arrival.getDateTime();
                                String date = departure.getDateTime();
                                setOutDate.setText(DateUtil.getDateTitle(date));
                                setOutTime.setText(date.split(" ")[1]);
                                setOutAirport.setText(departure.getAirportName());
                                arriveDate.setText(DateUtil.getDateTitle(arrivalTime));
                                arriveTime.setText(arrivalTime.split(" ")[1]);
                                arriveAirport.setText(arrival.getAirportName());


                                StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
                                stringBuilder.append(route.getAirlineName()).append(route.getFlightNo())
                                        .append("   |   ").append(route.getPlanTypeCode());
                                planeDetail.setText(stringBuilder.toString());
                                stopInfoCity.setText(route.getStopCity());

                                priceValue.setText(getString(R.string.money_no_end, String.valueOf(feeInfo.getTicketFee())));
                                jiJianValue.setText(getString(R.string.money_no_end, String.valueOf(feeInfo.getAirportFee())));
                                oilFeeValue.setText(getString(R.string.money_no_end, String.valueOf(feeInfo.getOilFee())));
                                safeFeeValue.setText(getString(R.string.money_no_end, String.valueOf(feeInfo.getInsuranceFee())));

                                orderAmount.setText(getString(R.string.money_no_end, orderBean.getFeeInfo().getPaymentAmount()));
                                orderNum.setText(orderBean.getOrderNo());
                                orderType.setText(orderBean.getPayMode());
                                orderStatus.setText(orderBean.getStatus());
                                AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean.PassengerBean passengerBean = orderBean.getPassenger();
                                passengerName.setText(passengerBean.getPassengerName());
                                passengerJob.setText(passengerBean.getBelongedDeptName());
                                contactName.setText(orderBean.getContactName() + orderBean.getContactMobile());
                                StringBuilder safeFee = Util.getThreadSafeStringBuilder();
                                safeFee.append("航意险").append(getString(R.string.money_no_end,orderBean.getFeeInfo().getInsuranceFee())).append("份 *").append(passengerBean.getInsuranceCount());
                                safeFeeTv.setText(safeFee.toString());
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


    @OnClick({R.id.handle_btn_1, R.id.handle_btn_2, R.id.handle_btn_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.handle_btn_1:
                break;
            case R.id.handle_btn_2:
                break;
            case R.id.handle_btn_3:
                break;
        }
    }
}
