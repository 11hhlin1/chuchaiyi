package com.ccy.chuchaiyi.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/9/18.
 */
public class NetCheckInFragment extends BaseFragment {
    @Bind(R.id.time_dateline)
    TextView timeDateline;
    @Bind(R.id.passenger_name)
    TextView passengerName;
    @Bind(R.id.seat_list_rl)
    LinearLayout seatListRl;
    @Bind(R.id.btn_sure)
    Button btnSure;
    private String[] checkInArrary;
    ArrayList<String> mSelSeat;
    private OrderInfo.OrdersBean ordersBean;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_net_check_in;
    }

    @Override
    public void initView() {
        ordersBean = (OrderInfo.OrdersBean) getArguments().getSerializable("order");
        assert ordersBean != null;
        passengerName.setText(ordersBean.getPassengerName());

        checkInArrary = getResources().getStringArray(R.array.netCheckIn);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mSelSeat = new ArrayList<>();
        for (int i = 0; i < checkInArrary.length; i++) {
            ViewHolder holder = inflateConstructingView(inflater);
            holder.seatName.setText(checkInArrary[i]);
            holder.checkIcon.setTag(checkInArrary[i]);
            seatListRl.addView(holder.parent);
        }

    }


    private ViewHolder inflateConstructingView(LayoutInflater inflater) {
        View child = inflater.inflate(R.layout.net_check_item, null);
        ViewHolder holder = new ViewHolder(child);
        return holder;
    }


    @OnClick(R.id.btn_sure)
    public void onClick() {
        StringBuilder seat = Util.getThreadSafeStringBuilder();
        for (String s : mSelSeat) {
            seat.append(s).append(",");
        }
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(ApiConstants.NET_CHECK_IN).append("?").append("orderId=")
                .append(ordersBean.getOrderId())
                .append("&seatRequirement=").append(seat.toString());
        OkHttpUtils.post(stringBuilder.toString())
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<String>(String.class) {

                    @Override
                    public void onResponse(boolean b, String s, Request request, @Nullable Response response) {
//                        EventOfRefreshOrderList eventOfRefreshOrderList = new EventOfRefreshOrderList();
//                        EventBus.getDefault().post(eventOfRefreshOrderList);
                        ToastUtil.shortToast(R.string.success);
                        Bundle bundle = new Bundle();
                        StringBuilder city = Util.getThreadSafeStringBuilder();
                        city.append(ordersBean.getDepartureCityName()).append("-").append(ordersBean.getArrivalCityName());
                        bundle.putString("city", city.toString());
                        bundle.putString("orderNum", ordersBean.getOrderNo());
                        bundle.putInt("orderId", ordersBean.getOrderId());
                        bundle.putString("tip", getString(R.string.net_check_success_tip));
                        PageSwitcher.switchToTopNavPage(getActivity(), ReturnOrderSuccessFragment.class, bundle, getString(R.string.dai_ban_zhi_ji), getString(R.string.index));

                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                });
    }


    class ViewHolder {
        @Bind(R.id.check_icon)
        CheckBox checkIcon;
        @Bind(R.id.seat_name)
        TextView seatName;
        View parent;

        ViewHolder(View view) {
            parent = view;
            ButterKnife.bind(this, view);
            checkIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String res = (String) checkIcon.getTag();
                    if (isChecked) {
                        mSelSeat.add(res);
                    } else {
                        mSelSeat.remove(res);
                    }
                }
            });
        }
    }
}
