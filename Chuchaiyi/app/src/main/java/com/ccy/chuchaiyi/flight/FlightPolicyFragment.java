package com.ccy.chuchaiyi.flight;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.gjj.applibrary.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by user on 16/8/20.
 */
public class FlightPolicyFragment extends BaseFragment {
    @Bind(R.id.low_price)
    TextView lowPrice;
    @Bind(R.id.pre_n_day)
    TextView preNDay;
    @Bind(R.id.discount_limit)
    TextView discountLimit;
    @Bind(R.id.cabin)
    TextView cabin;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private FlightPolicyAdapter mAdapter;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_flight_policy;
    }

    @Override
    public void initView() {
        BookValidateInfo.WarningInfoBean warningInfo = (BookValidateInfo.WarningInfoBean) getArguments().getSerializable("warningInfoBean");
        if (warningInfo == null) {
            return;
        }
        if(TextUtils.isEmpty(warningInfo.getLowPriceWarningMsg())) {
            lowPrice.setVisibility(View.GONE);
        } else {
            lowPrice.setVisibility(View.VISIBLE);
            lowPrice.setText(warningInfo.getLowPriceWarningMsg());
        }
        if(TextUtils.isEmpty(warningInfo.getPreNDaysWarningMsg())) {
            preNDay.setVisibility(View.GONE);
        } else {
            preNDay.setVisibility(View.VISIBLE);
            preNDay.setText(warningInfo.getPreNDaysWarningMsg());
        }
        if(TextUtils.isEmpty(warningInfo.getDiscountLimitWarningMsg())) {
            discountLimit.setVisibility(View.GONE);
        } else {
            discountLimit.setVisibility(View.VISIBLE);
            discountLimit.setText(warningInfo.getDiscountLimitWarningMsg());
        }
        if(TextUtils.isEmpty(warningInfo.getTwoCabinWarningMsg())) {
            cabin.setVisibility(View.GONE);
        } else {
            cabin.setVisibility(View.VISIBLE);
            cabin.setText(warningInfo.getTwoCabinWarningMsg());
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        List<PolicyReason> reasonList = new ArrayList<>();
        if(!Util.isListEmpty(warningInfo.getLowPriceReasons())) {
            PolicyReason reason = new PolicyReason();
            reason.mViewType = FlightPolicyAdapter.VIEW_TITLE;
            reason.mTitle = getString(R.string.low_price_title);
            reasonList.add(reason);
            for (String s : warningInfo.getLowPriceReasons()) {
                PolicyReason reason1 = new PolicyReason();
                reason1.mViewType = FlightPolicyAdapter.VIEW_CONTENT;
                reason1.mTitle = s;
                reasonList.add(reason1);
            }
        }
        if(!Util.isListEmpty(warningInfo.getLowPriceReasons())) {
            PolicyReason reason2 = new PolicyReason();
            reason2.mViewType = FlightPolicyAdapter.VIEW_TITLE;
            reason2.mTitle = getString(R.string.pre_day_title);
            reasonList.add(reason2);
            for (String s : warningInfo.getPreNDaysReasons()) {
                PolicyReason reasonDay = new PolicyReason();
                reasonDay.mViewType = FlightPolicyAdapter.VIEW_CONTENT;
                reasonDay.mTitle = s;
                reasonList.add(reasonDay);
            }
        }


        // 设置布局管理器
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new FlightPolicyAdapter(getActivity(), reasonList);
        mRecyclerView.setAdapter(mAdapter);
    }

}
