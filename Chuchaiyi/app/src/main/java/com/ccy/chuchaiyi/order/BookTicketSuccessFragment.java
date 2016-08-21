package com.ccy.chuchaiyi.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/21.
 */
public class BookTicketSuccessFragment extends BaseFragment {
    @Bind(R.id.city_name)
    TextView cityName;
    @Bind(R.id.order_num)
    TextView orderNum;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.dai_ban_btn)
    Button daiBanBtn;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_book_success;
    }

    @Override
    public void initView() {

    }

}
