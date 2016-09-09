package com.ccy.chuchaiyi.check;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.SimpleRecyclerViewAdapter;
import com.gjj.applibrary.util.DateUtil;
import com.gjj.applibrary.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/9/9.
 */
public class AuthorizesAdapter extends SimpleRecyclerViewAdapter<Authorizes.AuthorizesBean> {
    private int mType;
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

        @OnClick(R.id.handle_btn)
        void setHandleBtn() {

        }

        ViewHolderHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
            checkItemLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) checkItemLl.getTag();
//                    mItemOnclickListener.onItemClick(v, pos);
                }
            });
        }
    }


}

