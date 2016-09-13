package com.ccy.chuchaiyi.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.SimpleRecyclerViewAdapter;
import com.ccy.chuchaiyi.check.Approval;
import com.ccy.chuchaiyi.check.CategoryData;
import com.gjj.applibrary.util.DateUtil;
import com.gjj.applibrary.util.Util;

import java.util.List;

/**
 * Created by Chuck on 2016/9/13.
 */
public class OrderListAdapter extends SimpleRecyclerViewAdapter<OrderInfo.OrdersBean> {
    public OrderListAdapter(Context context, List<OrderInfo.OrdersBean> items) {
        super(context, items);
    }


//    public void setmItemOnclickListener(RecyclerItemOnclickListener mItemOnclickListener) {
//        this.mItemOnclickListener = mItemOnclickListener;
//    }
//
//    RecyclerItemOnclickListener mItemOnclickListener;

    public void addData(List<OrderInfo.OrdersBean> list) {
        if (list != items) {
            int size = getItemCount();
            items.addAll(list);
            notifyItemRangeInserted(size > 0 ? size : 0, list.size());
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.check_list_item, parent, false);
//        RecyclerView.ViewHolder viewHolder = new ViewHolderHeader(view);
//        return viewHolder;
//    }

}
