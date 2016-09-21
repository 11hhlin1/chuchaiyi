package com.ccy.chuchaiyi.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.flight.ChangeFlightReq;
import com.ccy.chuchaiyi.flight.FlightInfo;
import com.ccy.chuchaiyi.flight.FlightPolicyAdapter;
import com.ccy.chuchaiyi.flight.PolicyReason;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/9/21.
 */
public class ChooseChangeFliReasonFragment extends BaseFragment{
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
    private OrderInfo.OrdersBean ordersBean;
    private String[] reasonArray;
    private FlightInfo mFlightInfo;
    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_return_order;
    }

    @Override
    public void initView() {
        returnOrderTitle.setVisibility(View.GONE);
        returnOrderDesc.setText(getString(R.string.change_order_desc));
        Bundle bundle = getArguments();
        ordersBean = (OrderInfo.OrdersBean) bundle.getSerializable("order");
        mFlightInfo = (FlightInfo) bundle.getSerializable("flight");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        List<PolicyReason> reasonList = new ArrayList<>();
        PolicyReason reason = new PolicyReason();
        reason.mViewType = FlightPolicyAdapter.VIEW_TITLE;
        reason.mTitle = getString(R.string.return_order_title);
        reasonList.add(reason);
        reasonArray = getResources().getStringArray(R.array.changeOrderReason);

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
        ChangeFlightReq changeFlightReq = new ChangeFlightReq();
        changeFlightReq.ChangeReason = reasonArray[mAdapter.getFirstReasonPos() - 1];
        changeFlightReq.SrcOrderId = ordersBean.getOrderId();
        FlightInfo.BunksBean bunks = mFlightInfo.getBunks().get(0);
        bunks.getBunkPrice().getFactBunkPrice();
        ordersBean.getPaymentAmount();
        changeFlightReq.ChangeDifferencePrice = 0;

        OkHttpUtils.post(ApiConstants.CHANGE_ORDER)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .postJson(JSON.toJSONString(changeFlightReq))
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
                        bundle.putString("tip", getString(R.string.change_order_success_tip));
                        PageSwitcher.switchToTopNavPage(getActivity(), ReturnOrderSuccessFragment.class, bundle, getString(R.string.returnPolicy),getString(R.string.index));

                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                });
    }
}
