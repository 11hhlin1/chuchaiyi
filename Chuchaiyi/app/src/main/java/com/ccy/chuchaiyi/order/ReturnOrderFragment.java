package com.ccy.chuchaiyi.order;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.event.EventOfRefreshOrderList;
import com.ccy.chuchaiyi.flight.FlightPolicyAdapter;
import com.ccy.chuchaiyi.flight.PolicyReason;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/9/18.
 */
public class ReturnOrderFragment extends BaseFragment {
    @Bind(R.id.return_order_title)
    TextView returnOrderTitle;
    @Bind(R.id.return_order_desc)
    TextView returnOrderDesc;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.policy_btn_sure)
    Button policyBtnSure;
    private FlightPolicyAdapter mAdapter;
    private int dividePos;
    OrderInfo.OrdersBean ordersBean;
    String[] reasonArray;
    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_return_order;
    }

    @Override
    public void initView() {
        ordersBean = (OrderInfo.OrdersBean) getArguments().getSerializable("order");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        List<PolicyReason> reasonList = new ArrayList<>();
        PolicyReason reason = new PolicyReason();
        reason.mViewType = FlightPolicyAdapter.VIEW_TITLE;
        reason.mTitle = getString(R.string.return_order_title);
        reasonList.add(reason);
        reasonArray = getResources().getStringArray(R.array.returnOrderReason);

        for (int i = 0; i < reasonArray.length; i++) {
            PolicyReason reason1 = new PolicyReason();
            reason1.mViewType = FlightPolicyAdapter.VIEW_CONTENT;
            reason1.mTitle = reasonArray[i];
            reasonList.add(reason1);
        }

        dividePos = reasonList.size();


        // 设置布局管理器
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new FlightPolicyAdapter(getActivity(), reasonList, dividePos);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.policy_btn_sure)
    public void onClick() {
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(ApiConstants.RETURN_ORDER).append("?").append("orderId=")
                .append(ordersBean.getOrderId())
                .append("&returnReason=").append(reasonArray[mAdapter.getFirstReasonPos() - 1]);
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
                        bundle.putString("orderNum",ordersBean.getOrderNo());
                        bundle.putInt("orderId", ordersBean.getOrderId());
                        bundle.putString("tip", getString(R.string.return_order_success_tip));
                        EventOfRefreshOrderList eventOfRefreshOrderList = new EventOfRefreshOrderList();
                        EventBus.getDefault().post(eventOfRefreshOrderList);
                        PageSwitcher.switchToTopNavPage(getActivity(), ReturnOrderSuccessFragment.class, bundle, getString(R.string.returnPolicy),getString(R.string.index));

                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                });
    }
}
