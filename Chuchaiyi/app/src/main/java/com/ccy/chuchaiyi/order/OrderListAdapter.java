package com.ccy.chuchaiyi.order;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.base.SimpleRecyclerViewAdapter;
import com.ccy.chuchaiyi.util.DiscountUtil;
import com.gjj.applibrary.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        ViewHolderHeader viewHolderHeader = (ViewHolderHeader) holder;
        OrderInfo.OrdersBean ordersBean = getData(position);
        StringBuilder city = Util.getThreadSafeStringBuilder();
        city.append(ordersBean.getDepartureCityName()).append(" - ").append(ordersBean.getArrivalCityName());
        viewHolderHeader.flightCity.setText(city.toString());
        viewHolderHeader.flightStatus.setText(ordersBean.getStatus());
        viewHolderHeader.airCompany.setText(ordersBean.getAirlineName());
        viewHolderHeader.flightCode.setText(ordersBean.getFlightNo());
        StringBuilder discount = Util.getThreadSafeStringBuilder();
        discount.append(DiscountUtil.getDis(ordersBean.getDiscount())).append(ordersBean.getBunkName());
        viewHolderHeader.flightDiscount.setText(discount.toString());
        viewHolderHeader.flightMoney.setText(mContext.getString(R.string.money_no_end,ordersBean.getPaymentAmount()));
        StringBuilder time = Util.getThreadSafeStringBuilder();
        //TODO
        viewHolderHeader.flightTime.setText(ordersBean.getDepartureDateTime());
        viewHolderHeader.passenger.setText(ordersBean.getPassengerName());
        viewHolderHeader.passenger.setTag(ordersBean);
        setBtnText(viewHolderHeader);
//        if(ordersBean.isCanCancel()) {
//            viewHolderHeader.handleBtn1.setText(mContext.getString(R.string.dialog_default_cancel_title));
//        } else if(ordersBean.isCanPayment()){
//            String text = viewHolderHeader.handleBtn1.getText().toString();
//            if(TextUtils.isEmpty(text)) {
//                viewHolderHeader.handleBtn1.setText(mContext.getString(R.string.pay));
//            } else {
//                viewHolderHeader.handleBtn2.setText(mContext.getString(R.string.pay));
//            }
//        } else if(ordersBean.isCanReturn()) {
//            setBtnText(viewHolderHeader,R.string.returnPolicy);
//        } else if(ordersBean.isCanChange()) {
//            setBtnText(viewHolderHeader,R.string.changePolicy);
//        } else if(ordersBean.isCanNetCheckIn()) {
//            setBtnText(viewHolderHeader,R.string.dai_ban_zhi_ji);
//        }
        if(ordersBean.isCanCancel()) {
            setBtnText(viewHolderHeader,mContext.getString(R.string.dialog_default_cancel_title));
        } else {
            setBtnText(viewHolderHeader,null);
        }
        if(ordersBean.isCanPayment()) {
            setBtnText(viewHolderHeader,mContext.getString(R.string.pay));
        } else {
            setBtnText(viewHolderHeader,null);
        }
        if(ordersBean.isCanReturn()) {
            setBtnText(viewHolderHeader,mContext.getString(R.string.returnPolicy));
        } else {
            setBtnText(viewHolderHeader,null);
        }
        if(ordersBean.isCanChange()) {
            setBtnText(viewHolderHeader,mContext.getString(R.string.changePolicy));
        } else {
            setBtnText(viewHolderHeader,null);
        }
        if(ordersBean.isCanNetCheckIn()) {
            setBtnText(viewHolderHeader,mContext.getString(R.string.dai_ban_zhi_ji));
        } else {
            setBtnText(viewHolderHeader,null);
        }
        setBtnVisibility(viewHolderHeader);
    }
    private void setBtnText(ViewHolderHeader viewHolderHeader) {

        viewHolderHeader.handleBtn1.setText(null);
        viewHolderHeader.handleBtn2.setText(null);
        viewHolderHeader.handleBtn3.setText(null);

    }
    private void setBtnText(ViewHolderHeader viewHolderHeader, String res) {
        String text = viewHolderHeader.handleBtn1.getText().toString();
        String text2 = viewHolderHeader.handleBtn2.getText().toString();
        if(TextUtils.isEmpty(text)) {
            viewHolderHeader.handleBtn1.setText(res);
        } else if(TextUtils.isEmpty(text2)) {
            viewHolderHeader.handleBtn2.setText(res);
        } else {
            viewHolderHeader.handleBtn3.setText(res);
        }
    }

    private void setBtnVisibility(ViewHolderHeader viewHolderHeader) {
        String text3 = viewHolderHeader.handleBtn3.getText().toString();
        String text2 = viewHolderHeader.handleBtn2.getText().toString();
        String text1 = viewHolderHeader.handleBtn1.getText().toString();
        if(TextUtils.isEmpty(text3)) {
            viewHolderHeader.handleBtn3.setVisibility(View.GONE);
        } else {
            viewHolderHeader.handleBtn3.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(text2)) {
            viewHolderHeader.handleBtn2.setVisibility(View.GONE);
        } else {
            viewHolderHeader.handleBtn2.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(text1)){
            viewHolderHeader.handleBtn1.setVisibility(View.GONE);
        } else {
            viewHolderHeader.handleBtn1.setVisibility(View.VISIBLE);
        }

        if(TextUtils.isEmpty(text3) && TextUtils.isEmpty(text2) && TextUtils.isEmpty(text1)) {
            viewHolderHeader.bottomBtnRl.setVisibility(View.GONE);
        } else {
            viewHolderHeader.bottomBtnRl.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.order_list_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHolderHeader(view);
        return viewHolder;
    }

    class ViewHolderHeader extends RecyclerView.ViewHolder{
        @Bind(R.id.flight_city)
        TextView flightCity;
        @Bind(R.id.flight_status)
        TextView flightStatus;
        @Bind(R.id.air_company)
        TextView airCompany;
        @Bind(R.id.flight_code)
        TextView flightCode;
        @Bind(R.id.flight_discount)
        TextView flightDiscount;
        @Bind(R.id.flight_money)
        TextView flightMoney;
        @Bind(R.id.flight_time)
        TextView flightTime;
        @Bind(R.id.passenger)
        TextView passenger;
        @Bind(R.id.handle_btn_1)
        Button handleBtn1;
        @Bind(R.id.handle_btn_2)
        Button handleBtn2;
        @Bind(R.id.handle_btn_3)
        Button handleBtn3;
//        @Bind(R.id.handle_btn_4)
//        Button handleBtn4;
//        @Bind(R.id.handle_btn_5)
//        Button handleBtn5;
        @Bind(R.id.bottom_btn_rl)
        RelativeLayout bottomBtnRl;


        ViewHolderHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderInfo.OrdersBean ordersBean = (OrderInfo.OrdersBean) passenger.getTag();
                    Bundle bundle = new Bundle();
                    bundle.putInt("orderId", ordersBean.getOrderId());
                    PageSwitcher.switchToTopNavPage((Activity) mContext, OrderDetailFragment.class, bundle, "订单详情",null);

                }
            });
        }
    }
}
