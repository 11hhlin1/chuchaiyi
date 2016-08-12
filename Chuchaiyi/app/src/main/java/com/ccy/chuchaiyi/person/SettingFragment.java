package com.ccy.chuchaiyi.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/8/12.
 */
public class SettingFragment extends BaseFragment {
    @Bind(R.id.new_item)
    RelativeLayout newItem;
    @Bind(R.id.version_tv)
    TextView versionTv;
    @Bind(R.id.version_item)
    RelativeLayout versionItem;


    @OnClick(R.id.new_item)
    void goNew() {
        PageSwitcher.switchToTopNavPage(getActivity(), NewSettingFragment.class, null, getString(R.string.news),null);
    }

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initView() {

    }
}
