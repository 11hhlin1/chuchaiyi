package com.ccy.chuchaiyi.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/30.
 */
public class BaseRecyclerViewAdapter <T extends Serializable> extends RecyclerView.Adapter<ViewHolder>{
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> items = null;
    public BaseRecyclerViewAdapter(Context context, List<T> items) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.items = items;
    }
    @Override
    public int getItemCount() {
        if (null == items) {
            return 0;
        }
        return items.size();

    }

    public T getData(int position) {
        if (null == items) {
            return null;
        }
        return items.get(position);
    }

    public List<T> getDataList() {
        return items;
    }

    public void setData(List<T> msg) {
        if (msg != items) {
            if (items != null) {
                items.clear();
            }
            this.items = msg;
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }


}
