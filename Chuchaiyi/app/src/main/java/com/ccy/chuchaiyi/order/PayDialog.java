package com.ccy.chuchaiyi.order;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.gjj.applibrary.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by user on 16/9/11.
 */
public class PayDialog extends AlertDialog {

    @Bind(R.id.dialog_close)
    ImageView dialogClose;
    @Bind(R.id.passengerTv)
    TextView passengerTv;
    @Bind(R.id.travel_city)
    TextView travelCity;
    @Bind(R.id.travel_time)
    TextView travelTime;
    @Bind(R.id.pay_amount)
    TextView payAmount;
    @Bind(R.id.pay_btn_cancel)
    TextView payBtnCancel;
    @Bind(R.id.pay_btn_sure)
    TextView payBtnSure;

    PayDialogData mPayDialogData;
    private View.OnClickListener confirmClickListener;
    private View.OnClickListener cancelClickListener;
    public void onConfirm() {
        this.cancel();
        if (confirmClickListener != null) {
            confirmClickListener.onClick(payBtnSure);
        }
    }

    public void onCancel() {
        this.cancel();
        if (cancelClickListener != null) {
            cancelClickListener.onClick(payBtnCancel);
        }
    }

    public PayDialog(Context context, PayDialogData payDialogData) {
        super(context, 0);
        mPayDialogData = payDialogData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();
        int margin = context.getResources().getDimensionPixelSize(R.dimen.margin_60p);
        int screenWidth = Util.getScreenWidth(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth - margin
                - margin, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.pay_dialog, null);
        setContentView(view, params);
        ButterKnife.bind(this, view);

        passengerTv.setText(mPayDialogData.passenger);
        payAmount.setText(context.getString(R.string.money_no_end,mPayDialogData.amount));
        travelCity.setText(mPayDialogData.travelCity);
        travelTime.setText(mPayDialogData.travelTime);
        dialogClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        payBtnSure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onConfirm();
            }
        });
        payBtnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCancel();
            }
        });
    }

    public void setConfirmClickListener(View.OnClickListener confirmClickListener) {
        this.confirmClickListener = confirmClickListener;
    }

    public void setCancelClickListener(View.OnClickListener cancelClickListener) {
        this.cancelClickListener = cancelClickListener;
    }
}
