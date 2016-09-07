package com.ccy.chuchaiyi.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.SimpleRecyclerViewAdapter;
import com.ccy.chuchaiyi.flight.FlightInfo;
import com.gjj.applibrary.util.DateUtil;
import com.gjj.applibrary.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chuck on 2016/9/7.
 */
public class DialogListAdapter extends SimpleRecyclerViewAdapter<FlightInfo> {


    public DialogListAdapter(Context context, List<FlightInfo> items) {
        super(context, items);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderItem viewHolderItem = (ViewHolderItem) holder;
        FlightInfo flightInfo = getData(position);
        FlightInfo.DepartureBean departureBean = flightInfo.getDeparture();
        FlightInfo.ArrivalBean arrivalBean = flightInfo.getArrival();
        String arrivalTime = arrivalBean.getDateTime();
        String date = departureBean.getDateTime();
        viewHolderItem.setOutDate.setText(DateUtil.getDateTitle(date));
        viewHolderItem.setOutTime.setText(date.split(" ")[1]);
        viewHolderItem.setOutAirport.setText(departureBean.getAirportName());
        viewHolderItem.arriveDate.setText(DateUtil.getDateTitle(arrivalTime));
        viewHolderItem.arriveTime.setText(arrivalTime.split(" ")[1]);
        viewHolderItem.arriveAirport.setText(arrivalBean.getAirportName());
        FlightInfo.StopInfoBean stopInfoBean = flightInfo.getStopInfo();
        if (stopInfoBean != null) {
            List<FlightInfo.StopInfoBean.StopLocationsBean> stopLocationsBeens = stopInfoBean.getStopLocations();
            StringBuilder stopInfoString = Util.getThreadSafeStringBuilder();
            stopInfoString.append(mContext.getString(R.string.stop_tip));
            for (FlightInfo.StopInfoBean.StopLocationsBean stopLocationsBean : stopLocationsBeens) {
                stopInfoString.append("  ").append(stopLocationsBean.getCity());
            }
            viewHolderItem.stopInfoCity.setText(stopInfoString.toString());
        } else {
            viewHolderItem.stopInfoCity.setText("");
        }

        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(flightInfo.getAirlineName()).append(flightInfo.getFlightNo())
                .append("   |   ").append(flightInfo.getPlanType())
                .append("   |   ").append(flightInfo.getMeal()).append("餐食");
        viewHolderItem.planeDetail.setText(stringBuilder.toString());
        if(position == (getItemCount() - 1)) {
            viewHolderItem.bottomLine.setVisibility(View.GONE);
        } else {
            viewHolderItem.bottomLine.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.dialog_flight_info_list_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHolderItem(view);
        return viewHolder;
    }


     class ViewHolderItem extends RecyclerView.ViewHolder{
        @Bind(R.id.set_out_date)
        TextView setOutDate;
        @Bind(R.id.set_out_time)
        TextView setOutTime;
        @Bind(R.id.set_out_airport)
        TextView setOutAirport;
        @Bind(R.id.stop_info_city)
        TextView stopInfoCity;
        @Bind(R.id.arrive_date)
        TextView arriveDate;
        @Bind(R.id.arrive_time)
        TextView arriveTime;
        @Bind(R.id.arrive_airport)
        TextView arriveAirport;
        @Bind(R.id.plane_detail)
        TextView planeDetail;
        @Bind(R.id.bottom_line)
        View bottomLine;

         ViewHolderItem(View view) {
             super(view);
             ButterKnife.bind(this, view);
        }
    }
}