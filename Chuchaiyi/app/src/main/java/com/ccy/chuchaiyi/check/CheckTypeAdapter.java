package com.ccy.chuchaiyi.check;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.base.RecyclerItemOnclickListener;
import com.ccy.chuchaiyi.base.SimpleRecyclerViewAdapter;
import com.ccy.chuchaiyi.calendar.CalendarSelectorFragment;
import com.ccy.chuchaiyi.event.EventOfAgreeCheck;
import com.ccy.chuchaiyi.event.EventOfCancelApproval;
import com.ccy.chuchaiyi.event.EventOfChangeTab;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.order.PayDialog;
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
 * Created by Chuck on 2016/9/8.
 */
public class CheckTypeAdapter extends SimpleRecyclerViewAdapter<Approval.ApprovalsBean> {
    private int mType;
    private RejectDialog mConfirmDialog;

    public CheckTypeAdapter(Context context, List<Approval.ApprovalsBean> items,int type) {
        super(context, items);
        mType = type;
    }

//    public void setmItemOnclickListener(RecyclerItemOnclickListener mItemOnclickListener) {
//        this.mItemOnclickListener = mItemOnclickListener;
//    }
//
//    RecyclerItemOnclickListener mItemOnclickListener;

    public void addData(List<Approval.ApprovalsBean> list) {
        if (list != items) {
            int size = getItemCount();
            items.addAll(list);
            notifyItemRangeInserted(size > 0 ? size : 0, list.size());
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderHeader viewHolderHeader = (ViewHolderHeader) holder;
        Approval.ApprovalsBean approvalsBean = getData(position);
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(approvalsBean.getEmployeeName()).append("的审批");
        viewHolderHeader.checkTitle.setText(stringBuilder.toString());
        viewHolderHeader.checkTripArriveCity.setText(approvalsBean.getTravelDestination());

        String startTime = DateUtil.getDate(approvalsBean.getTravelDateStart());
        String endTime = DateUtil.getDate(approvalsBean.getTravelDateEnd());
        StringBuilder time = Util.getThreadSafeStringBuilder();
        time.append(startTime).append(" - ").append(endTime);
        viewHolderHeader.checkTripTime.setText(time.toString());
        viewHolderHeader.checkTripState.setText(approvalsBean.getStatus());
        viewHolderHeader.checkItemLl.setTag(position);
        if(mType == CategoryData.MY_APPLY) {
            if(approvalsBean.getStatus().equals("待审批")) {
                viewHolderHeader.handleBtn.setVisibility(View.VISIBLE);
                viewHolderHeader.handleBtn.setText(mContext.getString(R.string.checkcancle));
            } else {
                viewHolderHeader.handleBtn.setVisibility(View.GONE);
            }
            viewHolderHeader.handleLeftBtn.setVisibility(View.GONE);
        } else if(mType == CategoryData.MY_UN_CHECK) {
            viewHolderHeader.handleBtn.setVisibility(View.VISIBLE);
            viewHolderHeader.handleLeftBtn.setVisibility(View.VISIBLE);
            viewHolderHeader.handleBtn.setText(mContext.getString(R.string.agree));
            viewHolderHeader.handleLeftBtn.setText(mContext.getString(R.string.refuse));
        } else if(mType == CategoryData.MY_CHECKED) {
            viewHolderHeader.handleBtn.setVisibility(View.GONE);
            viewHolderHeader.handleLeftBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.check_list_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHolderHeader(view);
        return viewHolder;
    }


    class ViewHolderHeader extends RecyclerView.ViewHolder {
        @Bind(R.id.check_title)
        TextView checkTitle;
        @Bind(R.id.check_trip_arrive_city)
        TextView checkTripArriveCity;
        @Bind(R.id.check_trip_time)
        TextView checkTripTime;
        @Bind(R.id.check_state)
        TextView checkTripState;
        @Bind(R.id.handle_btn)
        Button handleBtn;
        @Bind(R.id.handle_btn_left)
        Button handleLeftBtn;
        @Bind(R.id.check_item_ll)
        RelativeLayout checkItemLl;

        @OnClick(R.id.handle_btn_left)
        void setHandleLeftBtn() {
            int pos = (int) checkItemLl.getTag();
            final Approval.ApprovalsBean approvalsBean = getData(pos);
            RejectDialog rejectDialog = new RejectDialog(mContext);
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
                    checkApproval(ApiConstants.AUDIT_REJECT_APPROVAL, approvalsBean.getApprovalId());
                }
            });
            mConfirmDialog.showAndKeyboard();
        }
        @OnClick(R.id.handle_btn)
        void setHandleBtn() {
            int pos = (int) checkItemLl.getTag();
            Approval.ApprovalsBean approvalsBean = getData(pos);
            if(mType == CategoryData.MY_APPLY) {
                StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
                stringBuilder.append(ApiConstants.CANCEL_APPROVAL).append("?").append("approvalId=").append(approvalsBean.getApprovalId());
                OkHttpUtils.post(stringBuilder.toString())
                        .tag(mContext)
//                    .params("approvalId",String.valueOf(approvalsBean.getApprovalId()))
                        .cacheMode(CacheMode.NO_CACHE)
                        .execute(new JsonCallback<String>(String.class) {

                            @Override
                            public void onResponse(boolean b, String s, Request request, @Nullable Response response) {
                                EventOfCancelApproval eventOfCancelApproval = new EventOfCancelApproval();
                                eventOfCancelApproval.mType = CategoryData.MY_APPLY;
                                EventBus.getDefault().post(new EventOfCancelApproval());
                            }

                            @Override
                            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                                super.onError(isFromCache, call, response, e);
                            }

                        });
            } else if(mType == CategoryData.MY_UN_CHECK) {
                checkApproval(ApiConstants.AUDIT_PASS_APPROVAL,approvalsBean.getApprovalId());
            }
        }
        private void checkApproval(String url, int approvalId) {
            StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
            stringBuilder.append(url).append("?").append("approvalId=").append(approvalId)
                    .append("&opinion=").append(" ");
            OkHttpUtils.post(stringBuilder.toString())
                    .tag(mContext)
                    .cacheMode(CacheMode.NO_CACHE)
                    .execute(new JsonCallback<String>(String.class) {
                        @Override
                        public void onResponse(boolean b, String s, Request request, @Nullable Response response) {
                            EventBus.getDefault().post(new EventOfAgreeCheck());
                            ToastUtil.shortToast(R.string.success);
                        }

                        @Override
                        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                            super.onError(isFromCache, call, response, e);
                            ToastUtil.shortToast(R.string.load_fail);
                        }
                    });
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


        ViewHolderHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
            checkItemLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) checkItemLl.getTag();
                    Approval.ApprovalsBean approvalsBean = getData(pos);
                    Bundle bundle = new Bundle();
                    bundle.putInt("approvalId",approvalsBean.getApprovalId());
                    StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
                    stringBuilder.append(approvalsBean.getAskEmployeeName()).append("的出差申请");
                    PageSwitcher.switchToTopNavPage((Activity) mContext, CheckDetailFragment.class, bundle, stringBuilder.toString(), null);

//                    mItemOnclickListener.onItemClick(v, pos);
                }
            });
        }
    }



}
