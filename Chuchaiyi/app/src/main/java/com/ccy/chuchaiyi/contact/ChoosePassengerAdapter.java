package com.ccy.chuchaiyi.contact;

import android.content.Context;

import com.ccy.chuchaiyi.base.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Chuck on 2016/8/24.
 */
public class ChoosePassengerAdapter extends BaseRecyclerViewAdapter<PassengerData>{
    public ChoosePassengerAdapter(Context context, List<PassengerData> items) {
        super(context, items);
    }
}
