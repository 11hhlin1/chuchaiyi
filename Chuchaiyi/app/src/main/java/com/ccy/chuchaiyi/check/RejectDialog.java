package com.ccy.chuchaiyi.check;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.gjj.applibrary.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chuck on 2016/9/12.
 */
public class RejectDialog extends AlertDialog {


    @Bind(R.id.dialog_close)
    ImageView dialogClose;
    @Bind(R.id.reject_reason)
    EditText reason;
    @Bind(R.id.pay_btn_cancel)
    TextView payBtnCancel;
    @Bind(R.id.pay_btn_sure)
    TextView payBtnSure;
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

    public RejectDialog(Context context) {
        super(context, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();
        int margin = context.getResources().getDimensionPixelSize(R.dimen.margin_60p);
        int screenWidth = Util.getScreenWidth(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth - margin
                - margin, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.refuse_dialog, null);
        setContentView(view, params);
        ButterKnife.bind(this, view);
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

    public String getText() {
        return reason.getText().toString();
    }
    public void showAndKeyboard() {
        show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}

