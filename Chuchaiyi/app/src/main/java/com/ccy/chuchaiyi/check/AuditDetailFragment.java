package com.ccy.chuchaiyi.check;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.event.EventOfAgreeCheck;
import com.ccy.chuchaiyi.event.EventOfCheckedAudit;
import com.ccy.chuchaiyi.flight.FlightInfo;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.util.DiscountUtil;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.DateUtil;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by user on 16/9/11.
 */
public class AuditDetailFragment extends BaseFragment {
    @Bind(R.id.check_state_tv)
    TextView checkStateTv;
    @Bind(R.id.state_icon)
    ImageView stateIcon;
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
    @Bind(R.id.agree_btn)
    Button agreeBtn;
    @Bind(R.id.refuse_btn)
    Button refuseBtn;
    @Bind(R.id.bottom_rl)
    RelativeLayout bottomRl;
    private LayoutInflater inflater;
    int authorizeId;
    private RejectDialog mConfirmDialog;

    @OnClick(R.id.agree_btn)
    void agreeBtn() {
        checkUrl(ApiConstants.PASS_AUTHORIZE_DETAIL,"");
    }
    @OnClick(R.id.refuse_btn)
    void rejectBtn() {
        final RejectDialog rejectDialog = new RejectDialog(getActivity());
        mConfirmDialog = rejectDialog;
        rejectDialog.setCanceledOnTouchOutside(false);
        rejectDialog.setCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissConfirmDialog();
            }
        });
        rejectDialog.setConfirmClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUrl(ApiConstants.REJECT_AUTHORIZE_DETAIL,rejectDialog.getText());
            }
        });
        mConfirmDialog.showAndKeyboard();
    }

    private void checkUrl(String url,String opinion) {
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(url).append("?").append("authorizeId=").append(authorizeId)
                .append("&opinion=").append(opinion);
        OkHttpUtils.post(stringBuilder.toString())
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<String>(String.class) {
                    @Override
                    public void onResponse(boolean b, String s, Request request, @Nullable Response response) {
                        EventBus.getDefault().post(new EventOfCheckedAudit());
                        ToastUtil.shortToast(R.string.success);
                        onBackPressed();
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        ToastUtil.shortToast(R.string.load_fail);
                    }
                });
    }
    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_audit_detail;
    }

    @Override
    public void initView() {
        inflater = getActivity().getLayoutInflater();

        authorizeId = getArguments().getInt("authorizeId");
        showLoadingDialog(R.string.submitting, false);
        OkHttpUtils.get(ApiConstants.GET_AUTHORIZE_DETAIL)
                .tag(this)
                .params("authorizeId", String.valueOf(authorizeId))
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<AuthorizeDetailRsp>(AuthorizeDetailRsp.class) {
                    @Override
                    public void onResponse(final boolean b, final AuthorizeDetailRsp authorizeDetailRsp, Request request, @Nullable Response response) {

                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       if(authorizeDetailRsp != null) {
                                           dismissLoadingDialog();

                                           AuthorizeDetailRsp.AuthorizeDetailBean detail = authorizeDetailRsp.getAuthorizeDetail();
                                           String status = detail.getStatus();
                                           checkStateTv.setText(status);
                                           if(detail.getStatus().equals("待授权")) {
                                               bottomRl.setVisibility(View.VISIBLE);
                                           } else {
                                               bottomRl.setVisibility(View.GONE);
                                           }
                                           if(status.equals("授权通过")) {
                                               stateIcon.setImageResource(R.mipmap.icon_approve_agree);
                                           } else if(status.equals("授权拒绝")) {
                                               stateIcon.setImageResource(R.mipmap.icon_approve_refuse);
                                           } else if(status.equals("待授权")) {
                                               stateIcon.setImageResource(R.mipmap.icon_approve_ing);
                                           } else if(status.equals("已撤消")) {
                                               stateIcon.setImageResource(R.mipmap.icon_approve_cannel);
                                           }
                                           AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean order = detail.getFlightOrder();
                                           AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean.PassengerBean.TravelPolicyInfoBean policy = order.getPassenger().getTravelPolicyInfo();
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
                                           AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean.RouteBean route = order.getRoute();
                                           AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean.RouteBean.DepartureBean departure = route.getDeparture();
                                           AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean.RouteBean.ArrivalBean arrival = route.getArrival();
                                           AuthorizeDetailRsp.AuthorizeDetailBean.FlightOrderBean.FeeInfoBean feeInfo = order.getFeeInfo();
                                           city.append(departure.getCityName()).append(" - ").append(arrival.getCityName());
                                           flightCity.setText(city.toString());
                                           StringBuilder bunk = Util.getThreadSafeStringBuilder();
                                           bunk.append(DiscountUtil.getDis(route.getDiscount())).append(route.getBunkName());
                                           flightDiscount.setText(bunk.toString());
                                           flightMoney.setText(getString(R.string.money_no_end, order.getFeeInfo().getPaymentAmount()));


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

                                           PersonalViewHolder viewHolder = inflatePersonView(inflater);
                                           viewHolder.checkStateIcon.setImageResource(R.mipmap.all_img_dot_pr);
                                           String hisName = detail.getAuditEmployeeName();
                                           if(TextUtils.isEmpty(hisName)) {
                                               String names = detail.getAuditPositionEmployeeNames();
                                               if(names.contains(",")) {
                                                   String [] nameArray = names.split(",");
                                                   viewHolder.checkPerson.setText(nameArray[0]);
                                               } else {
                                                   viewHolder.checkPerson.setText(names);
                                               }
                                           } else {
                                               viewHolder.checkPerson.setText(hisName);
                                           }
                                           viewHolder.checkPersonJob.setText(detail.getAuditPositionName());
                                           String hiStatus = detail.getStatus();

                                           if(hiStatus.equals("授权通过")) {
                                               viewHolder.checkStateIcon.setImageResource(R.mipmap.icon_order_approve1);
                                               viewHolder.checkDetailTv.setTextColor(getResources().getColor(R.color.color_80c41c));
                                               viewHolder.checkDetailTv.setText(detail.getStatus());
                                           } else if(hiStatus.equals("授权拒绝")) {
                                               viewHolder.checkStateIcon.setImageResource(R.mipmap.icon_order_approve3);
                                               viewHolder.checkDetailTv.setTextColor(getResources().getColor(R.color.color_aaaaaa));
                                               StringBuilder stringBuilder1 = Util.getThreadSafeStringBuilder();
                                               stringBuilder1.append(hiStatus);
                                               if(!TextUtils.isEmpty(detail.getAuditOpinion())) {
                                                   stringBuilder1.append("(").append(detail.getAuditOpinion()).append(")");
                                               }
                                               viewHolder.checkDetailTv.setText(stringBuilder1.toString());
                                           } else if(hiStatus.equals("待授权")) {
                                               viewHolder.checkStateIcon.setImageResource(R.mipmap.icon_order_approve2);
                                               viewHolder.checkDetailTv.setTextColor(getResources().getColor(R.color.color_aaaaaa));
                                               viewHolder.checkDetailTv.setText(detail.getStatus());
                                           } else {
                                               viewHolder.checkStateIcon.setImageResource(R.mipmap.icon_order_approve2);
                                               viewHolder.checkDetailTv.setTextColor(getResources().getColor(R.color.color_80c41c));
                                               viewHolder.checkDetailTv.setText(detail.getStatus());
                                           }
//                                           viewHolder.checkDetailTv.setText(detail.getStatus());
                                           viewHolder.checkTime.setText(DateUtil.getYYYYMMDDHHMMDate(detail.getAuditDate()));
                                           checkStateLl.addView(viewHolder.parent);
                                       }
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
    private PersonalViewHolder inflatePersonView(LayoutInflater inflater) {
        View child = inflater.inflate(R.layout.check_person_item, null);
        PersonalViewHolder holder = new PersonalViewHolder(child);
        return holder;
    }

    /**
     * dismiss确认对话框
     */
    private void dismissConfirmDialog() {
        RejectDialog confirmDialog = mConfirmDialog;
        if (null != confirmDialog && confirmDialog.isShowing()) {
            confirmDialog.dismiss();
            mConfirmDialog = null;
        }
    }
}
