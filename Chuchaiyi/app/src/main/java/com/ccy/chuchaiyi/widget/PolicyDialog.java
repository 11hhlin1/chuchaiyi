package com.ccy.chuchaiyi.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.flight.FlightInfo;
import com.gjj.applibrary.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/20.
 */
public class PolicyDialog extends AlertDialog {

    @Bind(R.id.seat_type)
    TextView seatType;
    @Bind(R.id.dialog_close)
    ImageView dialogClose;
    @Bind(R.id.seat_money)
    TextView seatMoney;
    @Bind(R.id.seat_detail)
    TextView seatDetail;
    @Bind(R.id.returnPolicy)
    TextView returnPolicy;
    @Bind(R.id.returnPolicyDesc)
    TextView returnPolicyDesc;
    @Bind(R.id.changePolicy)
    TextView changePolicy;
    @Bind(R.id.changePolicyDesc)
    TextView changePolicyDesc;
    @Bind(R.id.signPolicy)
    TextView signPolicy;
    @Bind(R.id.signPolicyDesc)
    TextView signPolicyDesc;
    @Bind(R.id.book_btn)
    Button bookBtn;

    public void onConfirm() {
        this.cancel();
        if (confirmClickListener != null) {
            confirmClickListener.onClick(bookBtn);
        }
    }

    public void onCancel() {
        this.cancel();
        if (cancelClickListener != null) {
            cancelClickListener.onClick(dialogClose);
        }
    }
    private View.OnClickListener confirmClickListener;
    private View.OnClickListener cancelClickListener;
    /**
     * 内容是否居中
     */
    private boolean isCenter = false;

    public PolicyDialog(Context context, int theme, boolean isCenter) {
        super(context, theme);
        this.isCenter = isCenter;
    }

    public PolicyDialog(Context context, int theme) {
        super(context, theme);
    }

    public PolicyDialog(Context context) {
        super(context, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int margin = getContext().getResources().getDimensionPixelSize(R.dimen.margin_40p);
        int screenWidth = Util.getScreenWidth(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth - margin
                - margin, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.policy_dialog, null);
        setContentView(view, params);
        ButterKnife.bind(this,view);

        bookBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onConfirm();
            }
        });
        dialogClose.setOnClickListener(new View.OnClickListener() {

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
    public void setContent(FlightInfo flightInfo, FlightInfo.BunksBean bunksBean) {
        seatType.setText(bunksBean.getBunkName());
        seatMoney.setText(getContext().getString(R.string.money_no_end, bunksBean.getBunkPrice().getBunkPrice()));
        seatDetail.setText(getContext().getString(R.string.flight_fund_detail,flightInfo.getAirportFee(),flightInfo.getOilFee()));
        returnPolicyDesc.setText(bunksBean.getReturnPolicy().getReturnPolicyDesc());
        changePolicyDesc.setText(bunksBean.getChangePolicy().getChangePolicyDesc());
        signPolicyDesc.setText(bunksBean.getSignPolicy().getSignPolicyDesc());
    }
}
