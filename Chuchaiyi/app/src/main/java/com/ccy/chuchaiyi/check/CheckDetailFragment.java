package com.ccy.chuchaiyi.check;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.util.DiscountUtil;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by user on 16/9/11.
 */
public class CheckDetailFragment extends BaseFragment {
    @Bind(R.id.check_state_tv)
    TextView checkStateTv;
    @Bind(R.id.state_icon)
    ImageView stateIcon;
    @Bind(R.id.trip_reason)
    TextView tripReason;
    @Bind(R.id.check_num)
    TextView checkNum;
    @Bind(R.id.apply_time)
    TextView applyTime;
    @Bind(R.id.travel_person)
    TextView travelPerson;
    @Bind(R.id.travel_time)
    TextView travelTime;
    @Bind(R.id.travel_city)
    TextView travelCity;
    @Bind(R.id.travel_way)
    TextView travelWay;
    @Bind(R.id.order_detail_title)
    TextView orderDetailTitle;
    @Bind(R.id.order_detail_ll)
    LinearLayout orderDetailLl;
    @Bind(R.id.check_state_ll)
    LinearLayout checkStateLl;

    @Bind(R.id.refuse_btn)
    Button refuseBtn;
    @Bind(R.id.agree_btn)
    Button agreeBtn;
    @Bind(R.id.bottom_rl)
    RelativeLayout bottomRl;
    private LayoutInflater inflater;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_check_detail;
    }

    @Override
    public void initView() {
        inflater = getActivity().getLayoutInflater();

        int approvalId = getArguments().getInt("approvalId");
        showLoadingDialog(R.string.submitting, false);
        OkHttpUtils.get(ApiConstants.GET_APPROVAL_DETAIL)
                .tag(this)
                .params("approvalId", String.valueOf(approvalId))
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<CheckDetailRsp>(CheckDetailRsp.class) {
                    @Override
                    public void onResponse(boolean b, final CheckDetailRsp checkDetailRsp, Request request, @Nullable Response response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                                CheckDetailRsp.ApprovalDetailBean detail = checkDetailRsp.getApprovalDetail();
                                if(detail.getStatus().equals("审批通过")||detail.getStatus().equals("审批拒绝")) {
                                    bottomRl.setVisibility(View.GONE);
                                } else {
                                    bottomRl.setVisibility(View.VISIBLE);
                                }
                                checkStateTv.setText(detail.getStatus());
                                tripReason.setText(detail.getTravelReason());
                                travelCity.setText(detail.getTravelDestination());
                                travelPerson.setText(detail.getEmployeeName());
                                StringBuilder travelTimeStr = Util.getThreadSafeStringBuilder();
                                travelTimeStr.append(detail.getTravelDateStart()).append("-").append(detail.getTravelDateEnd());
                                travelTime.setText(travelTimeStr);
                                applyTime.setText(detail.getCreateTime());
                                checkNum.setText(detail.getApprovalNo());
                                travelWay.setText(detail.getTransport());
                                for (CheckDetailRsp.ApprovalDetailBean.FlightOrdersBean flightOrdersBean : detail.getFlightOrders()) {
                                    FlightViewHolder viewHolder = inflateFlightView(inflater);
                                    viewHolder.detailIcon.setImageResource(R.mipmap.all_img_dot_pr);
                                    StringBuilder title = Util.getThreadSafeStringBuilder();
                                    title.append(flightOrdersBean.getDepartureCityName()).append("-").append(flightOrdersBean.getArrivalCityName()).append(" ").append(DiscountUtil.getDis(flightOrdersBean.getDiscount())).append(flightOrdersBean.getBunkName());
                                    viewHolder.detailTitle.setText(title.toString());
                                    StringBuilder checkDetail = Util.getThreadSafeStringBuilder();
                                    checkDetail.append(flightOrdersBean.getAirlineName()).append(flightOrdersBean.getFlightNo()).append(flightOrdersBean.getFlightDate());
                                    viewHolder.detailTv.setText(checkDetail.toString());
                                    viewHolder.amountTv.setText(getString(R.string.money_no_end, flightOrdersBean.getAmount()));
                                    orderDetailLl.addView(viewHolder.parent);
                                    View line = inflater.inflate(R.layout.bottom_line, null);
                                    orderDetailLl.addView(line);
                                }
                                for (CheckDetailRsp.ApprovalDetailBean.HotelOrdersBean hotelOrdersBean : detail.getHotelOrders()) {
                                    FlightViewHolder viewHolder = inflateFlightView(inflater);
                                    viewHolder.detailIcon.setImageResource(R.mipmap.all_img_dot_pr);
                                    viewHolder.detailTitle.setText(hotelOrdersBean.getHotelName());
                                    StringBuilder checkDetail = Util.getThreadSafeStringBuilder();
                                    checkDetail.append(hotelOrdersBean.getRoomTypeName()).append("  ").append(hotelOrdersBean.getCheckInDate()).append(hotelOrdersBean.getCheckOutDate());
                                    viewHolder.detailTv.setText(checkDetail.toString());
                                    viewHolder.amountTv.setText(getString(R.string.money_no_end, hotelOrdersBean.getAmount()));
                                    orderDetailLl.addView(viewHolder.parent);
                                    View line = inflater.inflate(R.layout.bottom_line, null);
                                    orderDetailLl.addView(line);
                                }
                                for (CheckDetailRsp.ApprovalDetailBean.ApprovalHisBean approvalHisBean : detail.getApprovalHis()) {
                                    PersonalViewHolder viewHolder = inflatePersonView(inflater);
                                    viewHolder.checkStateIcon.setImageResource(R.mipmap.all_img_dot_pr);
                                    viewHolder.checkPerson.setText(approvalHisBean.getAuditEmployeeName());
                                    viewHolder.checkPersonJob.setText(approvalHisBean.getAuditPositionName());
                                    viewHolder.checkDetailTv.setText(approvalHisBean.getStatus());
                                    viewHolder.checkTime.setText(approvalHisBean.getAuditDate());
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

    private FlightViewHolder inflateFlightView(LayoutInflater inflater) {
        View child = inflater.inflate(R.layout.check_order_ll_item, null);
        FlightViewHolder holder = new FlightViewHolder(child);
        return holder;
    }

    class FlightViewHolder {
        @Bind(R.id.detail_icon)
        ImageView detailIcon;
        @Bind(R.id.detail_title)
        TextView detailTitle;
        @Bind(R.id.amount_tv)
        TextView amountTv;
        @Bind(R.id.detail_tv)
        TextView detailTv;
        View parent;

        FlightViewHolder(View view) {
            parent = view;
            ButterKnife.bind(this, view);
        }
    }

    private PersonalViewHolder inflatePersonView(LayoutInflater inflater) {
        View child = inflater.inflate(R.layout.check_person_item, null);
        PersonalViewHolder holder = new PersonalViewHolder(child);
        return holder;
    }

    static class PersonalViewHolder {
        @Bind(R.id.check_state_icon)
        ImageView checkStateIcon;
        @Bind(R.id.check_person)
        TextView checkPerson;
        @Bind(R.id.check_person_job)
        TextView checkPersonJob;
        @Bind(R.id.check_time)
        TextView checkTime;
        @Bind(R.id.check_detail_tv)
        TextView checkDetailTv;
        View parent;
        PersonalViewHolder(View view) {
            parent = view;
            ButterKnife.bind(this, view);
        }
    }
}
