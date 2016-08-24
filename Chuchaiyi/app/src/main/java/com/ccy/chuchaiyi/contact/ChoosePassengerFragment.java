package com.ccy.chuchaiyi.contact;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.widget.EditTextWithDel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chuck on 2016/8/24.
 */
public class ChoosePassengerFragment extends BaseFragment {

    @Bind(R.id.et_search)
    EditTextWithDel etSearch;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_choose_passenger;
    }

    @Override
    public void initView() {

    }

}
