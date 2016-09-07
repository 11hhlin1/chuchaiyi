package com.ccy.chuchaiyi.check;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.contact.ChoosePassengerFragment;
import com.ccy.chuchaiyi.index.IndexFragmentAdapter;
import com.ccy.chuchaiyi.order.EditPassengerFragment;
import com.ccy.chuchaiyi.widget.NavLineView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/8/9.
 */
public class CheckFragment extends BaseFragment implements ViewPager.OnPageChangeListener{

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.add_plan)
    TextView addPlan;
    @Bind(R.id.top_layout)
    RelativeLayout topLayout;
    @Bind(R.id.my_launch_radio_btn)
    TextView myLaunchRadioBtn;
    @Bind(R.id.un_check_radio_btn)
    TextView unCheckRadioBtn;
    @Bind(R.id.checked_radio_btn)
    TextView checkedRadioBtn;
    @Bind(R.id.project_radio_group)
    LinearLayout projectRadioGroup;
    @Bind(R.id.bottom_line)
    View bottomLine;
    @Bind(R.id.tab_line_iv)
    NavLineView mNavLineView;
    @Bind(R.id.page_vp)
    ViewPager mPageVp;
    /**
     * ViewPager适配器
     */
    private CheckFragmentAdapter mFragmentAdapter;

    private Fragment[] mFragmentCache;

    private int mBlueColor;
    private int mBlackColor;
    private int mNavItemWidth;
    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_check;
    }

    @Override
    public void initView() {
        Resources res = getResources();
        mBlueColor = res.getColor(R.color.color_0071c4);
        mBlackColor = res.getColor(R.color.color_222222);

        mFragmentCache = new Fragment[3];
        initTabLineWidth();
        ViewPager viewPager = mPageVp;
        viewPager.setCurrentItem(0);
        myLaunchRadioBtn.setTextColor(mBlueColor);
        //mTwiceRadioBtn.setTextColor(mBlackColor);
        viewPager.setOffscreenPageLimit(mFragmentCache.length);
        viewPager.setOnPageChangeListener(this);
        mFragmentAdapter = new CheckFragmentAdapter(getChildFragmentManager(), mFragmentCache);
        mPageVp.setAdapter(mFragmentAdapter);
    }


    @OnClick({R.id.my_launch_radio_btn, R.id.un_check_radio_btn, R.id.checked_radio_btn, R.id.add_plan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_launch_radio_btn:
                mPageVp.setCurrentItem(0);
                addPlan.setVisibility(View.VISIBLE);
                break;
            case R.id.un_check_radio_btn:
                mPageVp.setCurrentItem(1);
                addPlan.setVisibility(View.GONE);
                break;
            case R.id.checked_radio_btn:
                mPageVp.setCurrentItem(2);
                addPlan.setVisibility(View.GONE);
                break;
            case R.id.add_plan:
                PageSwitcher.switchToTopNavPage(getActivity(), AddCheckFragement.class, null, getString(R.string.trip_plan),null);
                break;
        }
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
        switch (position) {
            case 0:
                unCheckRadioBtn.setTextColor(mBlackColor);
                checkedRadioBtn.setTextColor(mBlackColor);
                myLaunchRadioBtn.setTextColor(mBlueColor);
                break;
            case 1:
                myLaunchRadioBtn.setTextColor(mBlackColor);
                unCheckRadioBtn.setTextColor(mBlueColor);
                checkedRadioBtn.setTextColor(mBlackColor);
                break;
            case 2:
                myLaunchRadioBtn.setTextColor(mBlackColor);
                checkedRadioBtn.setTextColor(mBlueColor);
                unCheckRadioBtn.setTextColor(mBlackColor);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 设置滑动条的宽度为屏幕的1/mFragmentList.size();(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mNavItemWidth = dm.widthPixels / mFragmentCache.length;
    }
}
