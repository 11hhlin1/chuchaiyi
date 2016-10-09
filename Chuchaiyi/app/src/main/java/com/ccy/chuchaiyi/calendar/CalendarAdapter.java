package com.ccy.chuchaiyi.calendar;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;


/**
 * @author chuck
 * @Description 日历表格适配器
 */
public class CalendarAdapter extends BaseAdapter {

    private ArrayList<Day> days;
    private LayoutInflater mInflater;
    private Calendar c;
    private Context context;
    private String orderDay;

    public CalendarAdapter(Context context, Calendar c, int passDays, String orderDay) {
        this.c = c;
        this.context = context;
        this.orderDay = orderDay;
        days = CalendarUtils.getDaysOfMonth(this.c, passDays, orderDay);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Day getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        View v = convertView;
        ViewHolder holder = null;
        if (v == null) {
            v = mInflater.inflate(R.layout.calendar_item, arg2, false);
            holder = new ViewHolder();
            holder.tv = (TextView) v.findViewById(R.id.tv_calendar_item);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        Day d = getItem(position);
        switch (d.getType()) {
            case TODAY:
                if(d.isOrdered()) {
                    holder.tv.setText(d.getName());
                    holder.tv.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tv.setBackgroundResource(R.drawable.circle_orange);
                } else {
                    holder.tv.setText(context.getString(R.string.today));
                    holder.tv.setTextColor(context.getResources().getColor(R.color.color_222222));
                    holder.tv.setBackgroundResource(R.drawable.normal_calendar_order_item_bg);
                }
                holder.tv.setTextSize(14);
                holder.tv.setEnabled(true);
                break;
            case TOMORROW:
            case T_D_A_T:
                if(d.isOrdered()) {
                    holder.tv.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tv.setBackgroundResource(R.drawable.circle_orange);
                } else {
                    holder.tv.setTextColor(context.getResources().getColor(R.color.color_222222));
                    holder.tv.setBackgroundResource(R.drawable.normal_calendar_order_item_bg);
                }
                holder.tv.setText(d.getName());
                holder.tv.setEnabled(true);
//                holder.tv.setBackgroundResource(R.drawable.circle_orange);
//                holder.tv.setTextColor(context.getResources().getColor(R.color.color_222222));
                holder.tv.setTextSize(14);
                break;
            case ENABLE:
                holder.tv.setText(d.getName());
                holder.tv.setEnabled(true);
                holder.tv.setTextColor(d.isOrdered() ? Color.WHITE : context.getResources().getColor(
                        R.color.color_222222));
                holder.tv.setBackgroundResource(d.isOrdered() ? R.drawable.circle_orange
                        : R.drawable.normal_calendar_order_item_bg);
                holder.tv.setTextSize(14);
                break;
            case NOT_ENABLE:
                holder.tv.setText(d.getName());
                holder.tv.setEnabled(false);
                holder.tv.setTextColor(context.getResources().getColor(R.color.color_979797));
                holder.tv.setBackgroundColor(Color.WHITE);
                holder.tv.setTextSize(12);
                break;
        }

        return v;
    }

//	private void setOrderThreeDayStyle(TextView tv, boolean ordered, String dayStr) {
//		tv.setText(ordered ? dayStr + context.getString(R.string.order_day) : dayStr);
//		tv.setEnabled(true);
//		tv.setTextColor(ordered ? Color.WHITE : context.getResources().getColor(R.color.calendar_threeday_color));
//		tv.setBackgroundResource(ordered ? R.drawable.calendar_order_item_bg : R.drawable.normal_calendar_order_item_bg);
//		tv.setTextSize(ordered ? context.getResources().getDimension(R.dimen.calendar_item_order_day_size) : context
//				.getResources().getDimension(R.dimen.calendar_item_nonorder_day_size));
//	}

    static class ViewHolder {
        TextView tv;
    }

    public void previous() {
        if (c.get(Calendar.MONTH) == c.getActualMinimum(Calendar.MONTH)) {
            c.set((c.get(Calendar.YEAR) - 1), c.getActualMaximum(Calendar.MONTH), 1);
        } else {
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        }
        days = CalendarUtils.getDaysOfMonth(c, 0, orderDay);
        notifyDataSetChanged();
    }

    public void next() {
        if (c.get(Calendar.MONTH) == c.getActualMaximum(Calendar.MONTH)) {
            c.set((c.get(Calendar.YEAR) + 1), c.getActualMinimum(Calendar.MONTH), 1);
        } else {
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        }
        days = CalendarUtils.getDaysOfMonth(c, 0, orderDay);
        notifyDataSetChanged();
    }

}
