package com.ccy.chuchaiyi.check;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.base.SimpleRecyclerViewAdapter;
import com.ccy.chuchaiyi.event.EventOfAgreeCheck;
import com.ccy.chuchaiyi.event.EventOfCancelApproval;
import com.ccy.chuchaiyi.event.EventOfCheckedAudit;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.task.MainTaskExecutor;
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
 * Created by Chuck on 2016/9/9.
 */
public class AuthorizesAdapter extends SimpleRecyclerViewAdapter<Authorizes.AuthorizesBean> {
    private int mType;
    private RejectDialog mConfirmDialog;

    public AuthorizesAdapter(Context context, List<Authorizes.AuthorizesBean> items, int type) {
        super(context, items);
        mType = type;
    }

//    public void setmItemOnclickListener(RecyclerItemOnclickListener mItemOnclickListener) {
//        this.mItemOnclickListener = mItemOnclickListener;
//    }
//
//    RecyclerItemOnclickListener mItemOnclickListener;

    public void addData(List<Authorizes.AuthorizesBean> list) {
        if (list != items) {
            int size = getItemCount();
            items.addAll(list);
            notifyItemRangeInserted(size > 0 ? size : 0, list.size());
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderHeader viewHolderHeader = (ViewHolderHeader) holder;
        Authorizes.AuthorizesBean authorizesBean = getData(position);
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(authorizesBean.getTravellerName()).append("的订单需要您授权");
        viewHolderHeader.checkTitle.setText(stringBuilder.toString());
        viewHolderHeader.checkTripArriveCity.setText(authorizesBean.getOrderDesc().split(" ")[1]);
        String startTime = DateUtil.getDate(authorizesBean.getTravelDate());
        String endTime = DateUtil.getDate(authorizesBean.getTravelDate());
        StringBuilder time = Util.getThreadSafeStringBuilder();
        time.append(startTime).append(" - ").append(endTime);
        viewHolderHeader.checkTripTime.setText(time.toString());
        viewHolderHeader.checkTripState.setText(authorizesBean.getStatus());
        viewHolderHeader.checkItemLl.setTag(position);
        if(mType == CategoryData.MY_UN_AUDIT) {
            viewHolderHeader.handleBtn.setVisibility(View.VISIBLE);
            viewHolderHeader.handleLeftBtn.setVisibility(View.VISIBLE);
            viewHolderHeader.handleBtn.setText(mContext.getString(R.string.agree));
            viewHolderHeader.handleLeftBtn.setText(mContext.getString(R.string.refuse));
        } else if(mType == CategoryData.MY_AUDITED) {
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
            final Authorizes.AuthorizesBean authorizesBean = getData(pos);
            final RejectDialog rejectDialog = new RejectDialog(mContext);
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
                    checkUrl(ApiConstants.REJECT_AUTHORIZE_DETAIL, authorizesBean.getAuthorizeId(), rejectDialog.getText());
                }
            });
            mConfirmDialog.showAndKeyboard();

        }
        @OnClick(R.id.handle_btn)
        void setHandleBtn() {
            int pos = (int) checkItemLl.getTag();
            final Authorizes.AuthorizesBean authorizesBean = getData(pos);
            checkUrl(ApiConstants.PASS_AUTHORIZE_DETAIL,authorizesBean.getAuthorizeId(),"");
        }

        ViewHolderHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
            checkItemLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) checkItemLl.getTag();
                    Authorizes.AuthorizesBean authorizesBean = getData(pos);
                    Bundle bundle = new Bundle();
                    bundle.putInt("authorizeId",authorizesBean.getAuthorizeId());
                    StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
                    stringBuilder.append(authorizesBean.getTravellerName()).append("的订单授权");
                    PageSwitcher.switchToTopNavPage((Activity) mContext, AuditDetailFragment.class, bundle, stringBuilder.toString(), null);

//                    mItemOnclickListener.onItemClick(v, pos);
                }
            });
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
    private void checkUrl(String url,int authorizeId, String opinion) {
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
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        ToastUtil.shortToast(R.string.load_fail);
                    }
                });
    }
}

