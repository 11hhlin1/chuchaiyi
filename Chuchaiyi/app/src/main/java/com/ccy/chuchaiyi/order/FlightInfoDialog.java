package com.ccy.chuchaiyi.order;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.flight.FlightInfo;
import com.gjj.applibrary.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by user on 16/9/6.
 */
public class FlightInfoDialog extends AlertDialog {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    List<FlightInfo> mFlightList;
    @Bind(R.id.dialog_close)
    ImageView dialogClose;
    public FlightInfoDialog(Context context, List<FlightInfo> flightList) {
        super(context, 0);
        mFlightList = flightList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();
        int margin = context.getResources().getDimensionPixelSize(R.dimen.margin_60p);
        int screenWidth = Util.getScreenWidth(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth - margin
                - margin, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.flight_info_dialog, null);
        setContentView(view, params);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(new DialogListAdapter(context,mFlightList));
        dialogClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
