package com.ccy.chuchaiyi.check;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.event.EventOfCancelApproval;
import com.ccy.chuchaiyi.main.ApprovalCountRsp;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.widget.NavLineView;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/9/5.
 */
public class ViewPagerGoodListFragment extends BaseFragment implements ViewPager.OnPageChangeListener{

    /**
     * viewPager
     */
    @Bind(R.id.page_vp)
    ViewPager mPageVp;

    /**
     * 分隔线
     */
    @Bind(R.id.tab_line_iv)
    NavLineView mNavLineView;

    @Bind(R.id.project_radio_group)
    LinearLayout mRadioGroup;
    @Bind(R.id.add_plan)
    TextView addPlan;
    @OnClick(R.id.add_plan)
    void addCheck() {
        PageSwitcher.switchToTopNavPage(getActivity(), AddCheckFragement.class, null, getString(R.string.trip_plan),null);
    }
    private ViewPagerFragmentAdapter mFragmentAdapter;

    private Fragment[] mFragmentCache;
    private int mRedColor;
    private int mSecondaryGrayColor;
    private int mNavItemWidth;
    private ArrayList<CategoryData> mDataArrayList;
    private TextView mTextViews[];
    @Override
    public int getContentViewLayout() {
        return R.layout.view_pager_good_list;
    }

    @Override
    public void initView() {
        Resources res = getResources();
        mRedColor = res.getColor(R.color.color_0071c4);
        mSecondaryGrayColor = res.getColor(R.color.color_222222);

        Bundle bundle = getArguments();
        mDataArrayList = bundle.getParcelableArrayList("data");
        assert mDataArrayList != null;
        int size = mDataArrayList.size();
        mFragmentCache = new Fragment[size];
        initTabLineWidth();

        ViewPager viewPager = mPageVp;
        viewPager.setCurrentItem(0);
        mTextViews = new TextView[size];
        for (int i = 0; i < size; i++) {
            TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.radio_btn,null);
            mTextViews[i] = textView;
            CategoryData categoryData = mDataArrayList.get(i);
            textView.setId(categoryData.mCateId);
            textView.setText(categoryData.mCateName);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPageVp.setCurrentItem(finalI);
                }
            });
            mRadioGroup.addView(textView);
        }
        mTextViews[0].setTextColor(mRedColor);
        //mDoneRadioBtn.setTextColor(mSecondaryGrayColor);
        viewPager.setOffscreenPageLimit(size - 1);
        viewPager.setOnPageChangeListener(this);
        mFragmentAdapter = new ViewPagerFragmentAdapter(getChildFragmentManager(), mFragmentCache, mDataArrayList);
        mPageVp.setAdapter(mFragmentAdapter);
        getApprovalCount();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        float left = mNavItemWidth * (position + positionOffset);
        NavLineView navLineView = mNavLineView;
        navLineView.setNavLeft(left);
        navLineView.setNavRight(left + mNavItemWidth);
        navLineView.invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0 ; i< mTextViews.length; i++) {
            if(i == position) {
                mTextViews[i].setTextColor(mRedColor);
            } else {
                mTextViews[i].setTextColor(mSecondaryGrayColor);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void redTip(ApprovalCountRsp event) {
        if(getActivity() == null) {
            return;
        }
        if(event.ApprovalCount > 0) {
            if(mTextViews[1] != null) {
                CategoryData categoryData = mDataArrayList.get(1);
                if(categoryData.mCateName.equals(getString(R.string.un_check))) {
                    StringBuilder name = Util.getThreadSafeStringBuilder();
                    name.append(categoryData.mCateName).append("(").append(event.ApprovalCount).append(")");
                    mTextViews[1].setText(name.toString());
                }
            }
        }
        if(event.AuthorizeCount > 0) {
            if(mTextViews.length == 3 && mTextViews[1] != null) {
                CategoryData categoryData = mDataArrayList.get(1);
                if(categoryData.mCateName.equals(getString(R.string.un_audit))) {
                    StringBuilder name = Util.getThreadSafeStringBuilder();
                    name.append(categoryData.mCateName).append("(").append(event.AuthorizeCount).append(")");
                    mTextViews[1].setText(name.toString());
                }

            }
            if(mTextViews.length == 5 && mTextViews[3] != null) {
                CategoryData categoryData = mDataArrayList.get(3);
                if(categoryData.mCateName.equals(getString(R.string.un_audit))) {
                    StringBuilder name = Util.getThreadSafeStringBuilder();
                    name.append(categoryData.mCateName).append("(").append(event.AuthorizeCount).append(")");
                    mTextViews[3].setText(name.toString());
                }
            }
        }
    }
    /**
     * 设置滑动条的宽度为屏幕的1/mFragmentList.size();(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mNavItemWidth = dm.widthPixels / mFragmentCache.length;
    }

    @Override
    public void onTitleBtnClick() {
        BaseFragment fragment = (BaseFragment) mFragmentAdapter.getItem(mPageVp.getCurrentItem());
        fragment.onTitleBtnClick();
    }
    private void getApprovalCount() {
        OkHttpUtils.get(ApiConstants.GET_APPROVAL_COUNT)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ApprovalCountRsp>(ApprovalCountRsp.class) {

                    @Override
                    public void onResponse(boolean b, final ApprovalCountRsp approvalCountRsp, Request request, @Nullable Response response) {
                        redTip(approvalCountRsp);
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                    }
                });
    }

}
