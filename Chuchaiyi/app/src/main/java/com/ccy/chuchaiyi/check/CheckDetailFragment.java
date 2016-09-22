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
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.util.DiscountUtil;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.DateUtil;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    int approvalId;
    private RejectDialog mConfirmDialog;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_check_detail;
    }

    @Override
    public void initView() {
        inflater = getActivity().getLayoutInflater();

        approvalId = getArguments().getInt("approvalId");
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
                                String status = detail.getStatus();
                                if (status.equals("待审批")) {
                                    bottomRl.setVisibility(View.VISIBLE);
                                } else {
                                    bottomRl.setVisibility(View.GONE);
                                }
                                if(status.equals("审批通过")) {
                                    stateIcon.setImageResource(R.mipmap.icon_approve_agree);
                                } else if(status.equals("审批拒绝")) {
                                    stateIcon.setImageResource(R.mipmap.icon_approve_refuse);
                                } else if(status.equals("待审批")) {
                                    stateIcon.setImageResource(R.mipmap.icon_approve_ing);
                                } else if(status.equals("已撤消")) {
                                    stateIcon.setImageResource(R.mipmap.icon_approve_cannel);
                                }
                                checkStateTv.setText(detail.getStatus());
                                tripReason.setText(detail.getTravelReason());
                                travelCity.setText(detail.getTravelDestination());
                                travelPerson.setText(detail.getEmployeeName());
                                String travelStartTime = DateUtil.getYYYYMMDDDate(detail.getTravelDateStart());
                                String travelEndTime = DateUtil.getYYYYMMDDDate(detail.getTravelDateEnd());
                                StringBuilder travelTimeStr = Util.getThreadSafeStringBuilder();
                                travelTimeStr.append(travelStartTime).append(" - ").append(travelEndTime);
                                travelTime.setText(travelTimeStr);
                                String applyTimeStr = DateUtil.getYYYYMMDDHHMMDate(detail.getCreateTime());
                                applyTime.setText(applyTimeStr);
                                checkNum.setText(detail.getApprovalNo());
                                if(TextUtils.isEmpty(detail.getTransport())) {
                                    travelWay.setText(R.string.plane);
                                } else {
                                    travelWay.setText(detail.getTransport());
                                }
                                if(detail.getFlightOrders().size() <= 0) {
                                    orderDetailTitle.setVisibility(View.GONE);
                                }
                                for (CheckDetailRsp.ApprovalDetailBean.FlightOrdersBean flightOrdersBean : detail.getFlightOrders()) {
                                    FlightViewHolder viewHolder = inflateFlightView(inflater);
                                    viewHolder.detailIcon.setImageResource(R.mipmap.icon_order_flight);
                                    StringBuilder title = Util.getThreadSafeStringBuilder();
                                    title.append(flightOrdersBean.getDepartureCityName()).append(" - ").append(flightOrdersBean.getArrivalCityName()).append(" ").append(DiscountUtil.getDis(flightOrdersBean.getDiscount())).append(flightOrdersBean.getBunkName());
                                    viewHolder.detailTitle.setText(title.toString());
                                    String flightTime = DateUtil.getDate(flightOrdersBean.getFlightDate());
                                    StringBuilder checkDetail = Util.getThreadSafeStringBuilder();
                                    checkDetail.append(flightOrdersBean.getAirlineName()).append(flightOrdersBean.getFlightNo()).append("   ").append(flightTime);
                                    viewHolder.detailTv.setText(checkDetail.toString());
                                    viewHolder.amountTv.setText(getString(R.string.money_no_end, flightOrdersBean.getAmount()));
                                    orderDetailLl.addView(viewHolder.parent);

                                }
                                for (CheckDetailRsp.ApprovalDetailBean.HotelOrdersBean hotelOrdersBean : detail.getHotelOrders()) {
                                    FlightViewHolder viewHolder = inflateFlightView(inflater);
                                    viewHolder.detailIcon.setImageResource(R.mipmap.icon_order_hotel);
                                    viewHolder.detailTitle.setText(hotelOrdersBean.getHotelName());
                                    StringBuilder checkDetail = Util.getThreadSafeStringBuilder();
                                    checkDetail.append(hotelOrdersBean.getRoomTypeName()).append("  ").append(DateUtil.getDate(hotelOrdersBean.getCheckInDate())).append(" - ").append(DateUtil.getDate(hotelOrdersBean.getCheckOutDate()));
                                    viewHolder.detailTv.setText(checkDetail.toString());
                                    viewHolder.amountTv.setText(getString(R.string.money_no_end, hotelOrdersBean.getAmount()));
                                    orderDetailLl.addView(viewHolder.parent);
                                }
                                for (CheckDetailRsp.ApprovalDetailBean.ApprovalHisBean approvalHisBean : detail.getApprovalHis()) {
                                    PersonalViewHolder viewHolder = inflatePersonView(inflater);
                                    String hisName = approvalHisBean.getAuditEmployeeName();
                                    if(TextUtils.isEmpty(hisName)) {
                                        String names = approvalHisBean.getAuditPositionEmployeeNames();
                                        if(names.contains(",")) {
                                            String [] nameArray = names.split(",");
                                            viewHolder.checkPerson.setText(nameArray[0]);
                                        } else {
                                            viewHolder.checkPerson.setText(names);
                                        }
                                    } else {
                                        viewHolder.checkPerson.setText(hisName);
                                    }
                                    viewHolder.checkPersonJob.setText(approvalHisBean.getAuditPositionName());
                                    String hiStatus = approvalHisBean.getStatus();
                                    if(hiStatus.equals("审批通过")) {
                                        viewHolder.checkStateIcon.setImageResource(R.mipmap.icon_order_approve1);
                                    } else if(hiStatus.equals("审批拒绝")) {
                                        viewHolder.checkStateIcon.setImageResource(R.mipmap.icon_order_approve3);
                                    } else if(hiStatus.equals("待审批")) {
                                        viewHolder.checkStateIcon.setImageResource(R.mipmap.icon_order_approve2);
                                    } else {
                                        viewHolder.checkStateIcon.setImageResource(R.mipmap.icon_order_approve2);
                                    }
                                    viewHolder.checkDetailTv.setText(hiStatus);
                                    viewHolder.checkTime.setText(DateUtil.getYYYYMMDDHHMMDate(approvalHisBean.getAuditDate()));
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

    @OnClick({R.id.agree_btn, R.id.refuse_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.agree_btn:
                checkApproval(ApiConstants.AUDIT_PASS_APPROVAL,"");
                break;
            case R.id.refuse_btn:
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
                        checkApproval(ApiConstants.AUDIT_REJECT_APPROVAL, rejectDialog.getText());
                    }
                });
                mConfirmDialog.showAndKeyboard();
                break;
        }
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

    private void checkApproval(String url,String opinion) {
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(url).append("?").append("approvalId=").append(approvalId)
                .append("&opinion=").append(opinion);
        OkHttpUtils.post(stringBuilder.toString())
                .tag(getActivity())
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<String>(String.class) {
                    @Override
                    public void onResponse(boolean b, String s, Request request, @Nullable Response response) {
                        EventBus.getDefault().post(new EventOfAgreeCheck());
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

}
