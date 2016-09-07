package com.ccy.chuchaiyi.flight;

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
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.order.DialogListAdapter;
import com.gjj.applibrary.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chuck on 2016/9/7.
 */
public class EmployeePolicyDialog extends AlertDialog {
    @Bind(R.id.policy_detail)
    TextView policyDetail;
    String mDetails;
    @Bind(R.id.dialog_close)
    ImageView dialogClose;
    public EmployeePolicyDialog(Context context, String text) {
        super(context, 0);
        mDetails = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();
        int margin = context.getResources().getDimensionPixelSize(R.dimen.margin_40p);
        int screenWidth = Util.getScreenWidth(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth - margin
                - margin, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.employee_policy_dialog, null);
        setContentView(view, params);
        ButterKnife.bind(this, view);

        policyDetail.setText(mDetails);
        dialogClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}

