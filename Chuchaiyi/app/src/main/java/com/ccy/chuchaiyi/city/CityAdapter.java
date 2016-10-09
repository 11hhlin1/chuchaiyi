package com.ccy.chuchaiyi.city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;

import java.util.List;

/**
 * @author: chuck
 * @description:
 * @date: 2016-03-01
 * @time: 17:25
 */
public class CityAdapter extends BaseAdapter {
    /**
     * 需要渲染的item布局文件
     */
    private List<CitySort> mHotCity;
    private Context mContext;

    public CityAdapter(Context context, List<CitySort> citySorts) {
        mHotCity = citySorts;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mHotCity.size();
    }
    public void setData(List<CitySort> msg) {
        if (msg != mHotCity) {
            if (mHotCity != null) {
                mHotCity.clear();
            }
            this.mHotCity = msg;
        }
        notifyDataSetChanged();
    }
    @Override
    public CitySort getItem(int position) {
        return mHotCity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            layout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.gridview_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        TextView name = (TextView) layout.findViewById(R.id.tv_city);
        CitySort citySort = mHotCity.get(position);
        name.setText(citySort.getName());
        return layout;
    }
}
