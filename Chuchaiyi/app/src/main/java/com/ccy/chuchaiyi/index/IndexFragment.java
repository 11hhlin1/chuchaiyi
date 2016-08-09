package com.ccy.chuchaiyi.index;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.widget.NavLineView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/8/9.
 */
public class IndexFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    /**
     * viewPager
     */
    @Bind(R.id.page_vp)
    ViewPager mPageVp;


    @Bind(R.id.single_radio_btn)
    TextView mSingleRadioBtn;

    @Bind(R.id.twice_radio_btn)
    TextView mTwiceRadioBtn;

    /**
     * 分隔线
     */
    @Bind(R.id.tab_line_iv)
    NavLineView mNavLineView;

    @OnClick(R.id.single_radio_btn)
    void clickPlan() {
        mPageVp.setCurrentItem(0);
    }

    @OnClick(R.id.twice_radio_btn)
    void clickProgress() {
        mPageVp.setCurrentItem(1);
    }

    /**
     * ViewPager适配器
     */
    private IndexFragmentAdapter mFragmentAdapter;

    private Fragment[] mFragmentCache;

    private int mBlueColor;
    private int mBlackColor;
    private int mNavItemWidth;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_index;
    }

    @Override
    public void initView() {
        Resources res = getResources();
        mBlueColor = res.getColor(R.color.color_0071c4);
        mBlackColor = res.getColor(R.color.color_222222);

        mFragmentCache = new Fragment[2];
        initTabLineWidth();
        ViewPager viewPager = mPageVp;
        viewPager.setCurrentItem(0);
        mSingleRadioBtn.setTextColor(mBlueColor);
        //mTwiceRadioBtn.setTextColor(mBlackColor);
        viewPager.setOffscreenPageLimit(mFragmentCache.length);
        viewPager.setOnPageChangeListener(this);
        mFragmentAdapter = new IndexFragmentAdapter(getChildFragmentManager(), mFragmentCache);
        mPageVp.setAdapter(mFragmentAdapter);
    }


    /**
     * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
     */
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    /**
     * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比 offsetPixels:当前页面偏移的像素位置
     */
    @Override
    public void onPageScrolled(int position, float offset, int offsetPixels) {
        float left = mNavItemWidth * (position + offset);
        NavLineView navLineView = mNavLineView;
        navLineView.setNavLeft(left);
        navLineView.setNavRight(left + mNavItemWidth);
        navLineView.invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mTwiceRadioBtn.setTextColor(mBlackColor);
                mSingleRadioBtn.setTextColor(mBlueColor);
                break;
            case 1:
                mSingleRadioBtn.setTextColor(mBlackColor);
                mTwiceRadioBtn.setTextColor(mBlueColor);
                break;
        }
    }

    /**
     * 设置滑动条的宽度为屏幕的1/mFragmentList.size();(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mNavItemWidth = dm.widthPixels / mFragmentCache.length;
    }
}
