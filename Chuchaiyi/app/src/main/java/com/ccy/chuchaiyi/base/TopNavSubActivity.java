package com.ccy.chuchaiyi.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.ccy.chuchaiyi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/7/26.
 */
public class TopNavSubActivity extends BaseSubActivity {
    public static final String PARAM_TOP_TITLE = "top_title";
    public static final String PARAM_TOP_RIGHT = "right_title";

    @Bind(R.id.back_icon)
    ImageView mBackIV;

    @Bind(R.id.tv_title)
    TextView mTopTitleTV;
    @Bind(R.id.right_btn)
    TextView mRightTitleTV;

    @OnClick(R.id.back_icon)
    void back() {
        doBackPress(false);
    }

    @OnClick(R.id.right_btn)
    void rightBtnClick() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (null != fragment && fragment instanceof BaseFragment) {
            ((BaseFragment) fragment).onRightBtnClick();
        }
    }

    @OnClick(R.id.tv_title)
    void topTitleBtnClick() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (null != fragment && fragment instanceof BaseFragment) {
            ((BaseFragment) fragment).onTitleBtnClick();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        ButterKnife.bind(this);
        init(savedInstanceState);
//        GjjEventBus.getInstance().register(mEventReceiver);
    }

    @Override
    protected void handleArgs(Bundle bundle) {
        super.handleArgs(bundle);
        if (null != bundle) {
            String tp = bundle.getString(PARAM_TOP_TITLE);
            setTopTitleTV(tp);
            String tr = bundle.getString(PARAM_TOP_RIGHT);
            setRightBtnText(tr);
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
            if (null != fragment && fragment instanceof BaseFragment) {
                ((BaseFragment) fragment).handleArgs(bundle);
            }
        }
    }


    public void setTopTitleTV(String text) {
        if (!TextUtils.isEmpty(text)) {
            mTopTitleTV.setText(text);
            mTopTitleTV.setVisibility(View.VISIBLE);
        } else {
            mTopTitleTV.setVisibility(View.GONE);
        }
    }

    public void setRightBtnText(String text) {
        if (!TextUtils.isEmpty(text)) {
            mRightTitleTV.setText(text);
            mRightTitleTV.setVisibility(View.VISIBLE);
        } else {
            mRightTitleTV.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


}
