package com.ccy.chuchaiyi.order;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ccy.chuchaiyi.R;
import com.gjj.applibrary.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by user on 16/9/6.
 */
public class FlightInfoDialog extends AlertDialog {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    public FlightInfoDialog(Context context) {
        super(context, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int margin = getContext().getResources().getDimensionPixelSize(R.dimen.margin_60p);
        int screenWidth = Util.getScreenWidth(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth - margin
                - margin, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.flight_info_dialog, null);
        setContentView(view, params);
        ButterKnife.bind(this, view);


    }
}
