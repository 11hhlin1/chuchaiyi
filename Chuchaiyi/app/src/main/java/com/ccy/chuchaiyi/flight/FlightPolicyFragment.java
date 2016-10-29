package com.ccy.chuchaiyi.flight;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.constant.Constants;
import com.gjj.applibrary.util.SaveObjUtil;
import com.ccy.chuchaiyi.order.EditOrderFragment;
import com.gjj.applibrary.util.PreferencesManager;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

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
    private BookValidateInfo.WarningInfoBean warningInfo;
    private FlightInfo mFlightInfo;
    private FlightInfo.BunksBean mBunksBean;
    private String mReturnDateString;
    private String mDepartureCode;
    private String mArrivalCode;
    private String mBunkType;
    private String mTitle;

    private int dividePos;
    @OnClick(R.id.policy_btn_sure)
    void goBook() {

        if(mAdapter.getFirstReasonPos() == 0 && !TextUtils.isEmpty(warningInfo.getLowPriceWarningMsg())) {
            ToastUtil.shortToast(R.string.choose_reason);
            return;
        }
        if(!TextUtils.isEmpty(warningInfo.getPreNDaysWarningMsg()) && mAdapter.getSecondReasonPos() == 0) {
            ToastUtil.shortToast(R.string.choose_reason);
            return;
        }
        if(TextUtils.isEmpty(mReturnDateString)) {
            Bundle bundle = new Bundle();
            if(TextUtils.isEmpty(PreferencesManager.getInstance().get("SetOutWarningInfoBean"))) {
                PolicyResultInfo resultInfo = new PolicyResultInfo();
                setPolicyResultInfo(resultInfo);
                bundle.putSerializable("SetOutWarningInfoBean", resultInfo);
                bundle.putSerializable("SetOutFlightInfo", mFlightInfo);
                bundle.putSerializable("SetOutBunksBean", mBunksBean);
                StringBuilder title = Util.getThreadSafeStringBuilder();
                title.append(mFlightInfo.getDeparture().getCityName()).append("-").append(mFlightInfo.getArrival().getCityName()).append(getString(R.string.reason_common));
                PreferencesManager.getInstance().put(Constants.EDIT_ORDER_TITLE,title.toString());
                PageSwitcher.switchToTopNavPage(getActivity(), EditOrderFragment.class, bundle, title.toString(),getString(R.string.reason_private));
            } else {
                PolicyResultInfo resultInfo = (PolicyResultInfo) SaveObjUtil.unSerialize(PreferencesManager.getInstance().get("SetOutWarningInfoBean"));
                bundle.putSerializable("SetOutWarningInfoBean", resultInfo);
                PolicyResultInfo resultInfoLast = new PolicyResultInfo();
                setPolicyResultInfo(resultInfoLast);
                bundle.putSerializable("ReturnWarningInfoBean", resultInfoLast);
                bundle.putSerializable("SetOutFlightInfo", (FlightInfo)SaveObjUtil.unSerialize(PreferencesManager.getInstance().get("SetOutFlightInfo")));
                bundle.putSerializable("SetOutBunksBean", (FlightInfo.BunksBean)SaveObjUtil.unSerialize(PreferencesManager.getInstance().get("SetOutBunksBean")));
                bundle.putSerializable("ReturnFlightInfo", mFlightInfo);
                bundle.putSerializable("ReturnBunksBean", mBunksBean);
                StringBuilder title = Util.getThreadSafeStringBuilder();
                title.append(mFlightInfo.getDeparture().getCityName()).append("-").append(mFlightInfo.getArrival().getCityName()).append(getString(R.string.reason_common));
                PreferencesManager.getInstance().put(Constants.EDIT_ORDER_TITLE,title.toString());

                PageSwitcher.switchToTopNavPage(getActivity(), EditOrderFragment.class, bundle, title.toString(),getString(R.string.reason_private));
            }

        } else {
            Bundle bundle = new Bundle();
            PreferencesManager.getInstance().put("SetOutFlightInfo", SaveObjUtil.serialize(mFlightInfo));
            PreferencesManager.getInstance().put("SetOutBunksBean", SaveObjUtil.serialize(mBunksBean));
            PolicyResultInfo resultInfo = new PolicyResultInfo();
            setPolicyResultInfo(resultInfo);
            PreferencesManager.getInstance().put("SetOutWarningInfoBean", SaveObjUtil.serialize(resultInfo));
            bundle.putString("DepartureCode", mArrivalCode);
            bundle.putString("ArrivalCode", mDepartureCode);
            bundle.putString("SetOutDate", mReturnDateString);
            bundle.putString("BunkType",mBunkType);
            PageSwitcher.switchToTopNavPage(getActivity(), FlightsListFragment.class, bundle, mTitle ,getString(R.string.policy));
        }

    }

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_flight_policy;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        warningInfo = (BookValidateInfo.WarningInfoBean) bundle.getSerializable("warningInfoBean");
        mFlightInfo = (FlightInfo) bundle.getSerializable("FlightInfo");
        mBunksBean = (FlightInfo.BunksBean) bundle.getSerializable("BunksBean");
        mReturnDateString = bundle.getString("returnDate","");
        mDepartureCode = bundle.getString("mDepartureCode");
        mArrivalCode = bundle.getString("mArrivalCode");
        mBunkType = bundle.getString("mBunkType");
        mTitle = bundle.getString("mTitle");
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
        if(!TextUtils.isEmpty(warningInfo.getLowPriceWarningMsg()) && !Util.isListEmpty(warningInfo.getLowPriceReasons())) {
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
        dividePos = reasonList.size();
        if(!TextUtils.isEmpty(warningInfo.getPreNDaysWarningMsg()) && !Util.isListEmpty(warningInfo.getPreNDaysReasons())) {
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
        mAdapter = new FlightPolicyAdapter(getActivity(), reasonList,dividePos);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void setPolicyResultInfo(PolicyResultInfo resultInfo) {
        resultInfo.setDiscountLimitWarningMsg(warningInfo.getDiscountLimitWarningMsg());
        resultInfo.setLowPriceWarningMsg(warningInfo.getLowPriceWarningMsg());
        if(!TextUtils.isEmpty(warningInfo.getLowPriceWarningMsg())) {
            resultInfo.setNotLowPriceReason(warningInfo.getLowPriceReasons().get(mAdapter.getFirstReasonPos()-1));
        }
        resultInfo.setPreNDaysWarningMsg(warningInfo.getPreNDaysWarningMsg());
        resultInfo.setTwoCabinWarningMsg(warningInfo.getTwoCabinWarningMsg());
        if(!TextUtils.isEmpty(warningInfo.getPreNDaysWarningMsg())) {
            resultInfo.setNotPreNDaysReason(warningInfo.getPreNDaysReasons().get(mAdapter.getSecondReasonPos() - dividePos - 1));
        }
    }
}
