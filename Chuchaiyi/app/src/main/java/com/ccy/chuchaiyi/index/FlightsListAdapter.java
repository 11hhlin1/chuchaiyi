package com.ccy.chuchaiyi.index;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.gjj.applibrary.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chuck on 2016/8/17.
 */
public class FlightsListAdapter extends BaseExpandableListAdapter {
    private Resources mRes;
    private LayoutInflater mInflater;
    private ArrayList<FlightInfo> mDataList;
    private Context mContext;

    public FlightsListAdapter(Context context, ArrayList<FlightInfo> dataList) {
        super();
        mContext = context;
        mRes = context.getResources();
        mInflater = LayoutInflater.from(context);
        mDataList = dataList;
    }

    /**
     * 填充内容
     *
     * @param list
     */
    public void setData(ArrayList<FlightInfo> list) {
        if (list != mDataList) {
            if (mDataList != null) {
                mDataList.clear();
            }
            mDataList = list;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mDataList == null || mDataList.size() == 0) {
            return 0;
        }
        List<FlightInfo.BunksBean> list = mDataList.get(groupPosition).getBunks();
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public FlightInfo getGroup(int groupPosition) {
        if (mDataList == null) {
            return null;
        }
        return mDataList.get(groupPosition);
    }

    @Override
    public FlightInfo.BunksBean getChild(int groupPosition, int childPosition) {
        if (mDataList == null) {
            return null;
        }
        return mDataList.get(groupPosition).getBunks().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.flights_list_group_item, parent,
                    false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FlightInfo flightInfo = getGroup(groupPosition);
        String arrival = flightInfo.getArrival().getDateTime();
        int len = arrival.length();
        holder.arriveTime.setText(arrival.substring(len - 5, len));
        String setOutTime = flightInfo.getDeparture().getDateTime();
        int len1 = arrival.length();
        holder.setOutTime.setText(setOutTime.substring(len1 - 5, len1));
        holder.setOutAirport.setText(flightInfo.getDeparture().getAirportName());
        holder.arriveAirport.setText(flightInfo.getArrival().getAirportName());
        holder.money.setText(mContext.getString(R.string.money, flightInfo.getYBunkPrice()));
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(flightInfo.getAirlineName()).append(flightInfo.getFlightNo()).append(" | ").append(flightInfo.getPlanType());
        holder.planeMsg.setText(stringBuilder.toString());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild childHolder = null;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.flights_list_child_item, parent, false);
            childHolder = new ViewHolderChild(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ViewHolderChild) convertView.getTag();
        }
        FlightInfo.BunksBean bunksBean = getChild(groupPosition,childPosition);
        childHolder.planeType.setText(bunksBean.getBunkName());
        childHolder.discount.setText(mContext.getString(R.string.discount,bunksBean.getBunkPrice().getDiscount()));
        childHolder.detailMoney.setText((mContext.getString(R.string.money_no_end,bunksBean.getBunkPrice().getBunkPrice())));
        if(bunksBean.getRemainNum() < 5){
            childHolder.remainNum.setVisibility(View.VISIBLE);
            childHolder.remainNum.setText(mContext.getString(R.string.num,bunksBean.getRemainNum()));
        } else {
            childHolder.remainNum.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    static class ViewHolder {
        @Bind(R.id.set_out_time)
        TextView setOutTime;
        @Bind(R.id.arrive_time)
        TextView arriveTime;
        @Bind(R.id.money)
        TextView money;
        @Bind(R.id.set_out_airport)
        TextView setOutAirport;
        @Bind(R.id.arrive_airport)
        TextView arriveAirport;
        @Bind(R.id.plane_msg)
        TextView planeMsg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderChild {
        @Bind(R.id.plane_type)
        TextView planeType;
        @Bind(R.id.discount)
        TextView discount;
        @Bind(R.id.remainNum)
        TextView remainNum;
        @Bind(R.id.book_btn)
        Button bookBtn;
        @Bind(R.id.detail_money)
        TextView detailMoney;

        ViewHolderChild(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
