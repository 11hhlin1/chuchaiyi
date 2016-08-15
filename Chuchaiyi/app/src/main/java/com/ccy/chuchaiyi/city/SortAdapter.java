package com.ccy.chuchaiyi.city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;


import com.ccy.chuchaiyi.R;

import java.util.List;

public class SortAdapter extends BaseAdapter implements SectionIndexer {
    private List<CitySort> list = null;
    private Context mContext;

    public SortAdapter(Context mContext, List<CitySort> list) {
        this.mContext = mContext;
        this.list = list;
    }

//    /**
//     * 当ListView数据发生变化时,调用此方法来更新ListView
//     *
//     * @param list
//     */
//    public void updateListView(List<CitySort> list) {
//        this.list = list;
//        notifyDataSetChanged();
//    }
    public void setData(List<CitySort> msg) {
//            if (list != null) {
//                list.clear();
//            }
            this.list = msg;
        notifyDataSetChanged();
    }
    public int getCount() {
        return this.list.size();
    }

    public CitySort getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final CitySort mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_select_city, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.tv_city_name);
            view.setTag(viewHolder);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.tv_category);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        int section = getSectionForPosition(position);

        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            if(mContent.getPinyinShort().length() > 0) {
                viewHolder.tvLetter.setText(String.valueOf(mContent.getPinyinShort().charAt(0)));
            }
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(this.list.get(position).getName());

        return view;

    }


    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
    }

    public int getSectionForPosition(int position) {
        return list.get(position).getPinyinShort().charAt(0);
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getPinyinShort();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}