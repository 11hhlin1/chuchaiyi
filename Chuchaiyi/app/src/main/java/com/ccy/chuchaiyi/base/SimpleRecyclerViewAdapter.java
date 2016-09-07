package com.ccy.chuchaiyi.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Chuck on 2016/9/7.
 */
public class SimpleRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> items = null;


    public SimpleRecyclerViewAdapter(Context context, List<T> items) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vg, int viewtype) {
        return null;
    }


}

