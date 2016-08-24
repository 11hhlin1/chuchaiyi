package com.ccy.chuchaiyi.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.gjj.applibrary.util.Util;

/**
 * Created by Chuck on 2016/8/24.
 */
public class CallDialog extends AlertDialog {
    /**
     * 提交
     */
    TextView confirmBtn;
    /**
     * 取消
     */
    TextView cancelBtn;
    /**
     * 内容
     */
    TextView content;

    public void onConfirm() {
        this.cancel();
        if (confirmClickListener != null) {
            confirmClickListener.onClick(confirmBtn);
        }
    }

    public void onCancel() {
        this.cancel();
        if (cancelClickListener != null) {
            cancelClickListener.onClick(cancelBtn);
        }
    }

    private android.view.View.OnClickListener confirmClickListener;
    private android.view.View.OnClickListener cancelClickListener;
    private int contentResId;
    private String contentStr;
    private int confirmResId;
    private String confirmStr;
    private int cancelResId;
    private String cancelStr;
    /**
     * 内容是否居中
     */
    private boolean isCenter = false;

    public CallDialog(Context context, int theme, boolean isCenter) {
        super(context, theme);
        this.isCenter = isCenter;
    }

    public CallDialog(Context context, int theme) {
        super(context, theme);
    }

    public CallDialog(Context context) {
        super(context, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int margin = getContext().getResources().getDimensionPixelSize(R.dimen.margin_120px);
        int screenWidth = Util.getScreenWidth(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth - margin
                - margin, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.call_dialog, null);
        setContentView(view, params);
        // ButterKnife.bind(this);
        confirmBtn = (TextView) findViewById(R.id.btn_confirm);
        cancelBtn = (TextView) findViewById(R.id.btn_cancel);
        content = (TextView) findViewById(R.id.dialog_content);
        if (isCenter) {
            content.setGravity(Gravity.CENTER);
        }

        confirmBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onConfirm();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCancel();
            }
        });

        if (contentStr != null) {
            this.content.setText(contentStr);
        } else if (contentResId != 0) {
            this.content.setText(contentResId);
        }
        if (confirmStr != null) {
            this.confirmBtn.setText(confirmStr);
        } else if (confirmResId != 0) {
            this.confirmBtn.setText(confirmResId);
        }
        if (cancelStr != null) {
            this.cancelBtn.setText(cancelStr);
        } else if (cancelResId != 0) {
            this.cancelBtn.setText(cancelResId);
        }
    }

    public void setContent(int contentResId) {
        if (null != this.content) {
            this.content.setText(contentResId);
        }
        this.contentResId = contentResId;
    }

    public void setContent(String content) {
        if (null != this.content) {
            this.content.setText(content);
        }
        contentStr = content;
    }

    public void setConfirm(String confirmStr) {
        if (null != this.content) {
            this.confirmBtn.setText(confirmStr);
        }
        this.confirmStr = confirmStr;
    }

    public void setConfirm(int confirmResId) {
        if (null != this.content) {
            this.confirmBtn.setText(confirmResId);
        }
        this.confirmResId = confirmResId;
    }

    public void setCancel(String cancelStr) {
        if (null != this.content) {
            this.cancelBtn.setText(cancelStr);
        }
        this.cancelStr = cancelStr;
    }

    public void setCancel(int cancelResId) {
        if (null != this.content) {
            this.cancelBtn.setText(cancelResId);
        }
        this.cancelResId = cancelResId;
    }

    public void setContentAndBtn(int contentResId, int confirmResId, int cancelResId) {
        if (null != this.content) {
            this.content.setText(contentResId);
        }
        this.contentResId = contentResId;
        if (null != this.confirmBtn) {
            this.confirmBtn.setText(confirmResId);
        }
        this.confirmResId = confirmResId;
        if (null != this.cancelBtn) {
            this.cancelBtn.setText(cancelResId);
        }
        this.cancelResId = cancelResId;
    }

    public void setConfirmClickListener(android.view.View.OnClickListener confirmClickListener) {
        this.confirmClickListener = confirmClickListener;
    }

    public void setCancelClickListener(android.view.View.OnClickListener cancelClickListener) {
        this.cancelClickListener = cancelClickListener;
    }

}
