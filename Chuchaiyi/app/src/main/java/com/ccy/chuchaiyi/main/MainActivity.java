package com.ccy.chuchaiyi.main;

import android.os.Bundle;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseMainActivity;
import com.ccy.chuchaiyi.check.CheckFragment;
import com.ccy.chuchaiyi.index.IndexFragment;
import com.ccy.chuchaiyi.order.OrderFragment;
import com.ccy.chuchaiyi.person.PersonalFragment;
import com.ccy.chuchaiyi.widget.NestRadioGroup;
import com.gjj.applibrary.task.MainTaskExecutor;
import com.gjj.applibrary.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chuck on 16/7/17.
 */
public class MainActivity extends BaseMainActivity {

    private static final String SAVE_INSTANCE_STATE_KEY_TAB_ID = "tabId";

    @Bind(R.id.group_tab)
    NestRadioGroup mRadioGroup;


    private boolean mIsBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRadioGroup.setOnCheckedChangeListener(new NestRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(NestRadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.index_tab:
                        showIndexTab();
                        break;
                    case R.id.community_tab:
                        showCheckTab();
                        break;
                    case R.id.shopping_tab:
                        showOrderTab();
                        break;
                    case R.id.person_tab:
                        showPersonTab();
                        break;
                }
            }
        });

        int tabId = 0;
        if (null != savedInstanceState) {
            tabId = savedInstanceState.getInt(SAVE_INSTANCE_STATE_KEY_TAB_ID);
        }
        switch (tabId) {
            case R.id.person_tab:
                mRadioGroup.check(R.id.person_tab);
                break;
            case R.id.community_tab:
                mRadioGroup.check(R.id.community_tab);
                break;
            case R.id.shopping_tab:
                mRadioGroup.check(R.id.shopping_tab);
                break;
            default:
                mRadioGroup.check(R.id.index_tab);
                break;
        }
//        Glide.with(this).load("http://jcodecraeer.com/uploads/20150327/1427445294447874.jpg")
//                .into(imageView);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_INSTANCE_STATE_KEY_TAB_ID, mRadioGroup.getCheckedRadioButtonId());
    }

    private void showPersonTab() {

        replaceFragment(PersonalFragment.class);
    }

    private void showOrderTab() {

        replaceFragment(OrderFragment.class);

    }

    private void showCheckTab() {

        replaceFragment(CheckFragment.class);
    }

    private void showIndexTab() {

        replaceFragment(IndexFragment.class);
    }

    @Override
    public void onBackPressed() {
        if (mIsBackPressed) {
            mIsBackPressed = false;
            finish();
            killApp();
        } else {
            ToastUtil.shortToast(this, R.string.quit_toast);
            mIsBackPressed = true;
            MainTaskExecutor.scheduleTaskOnUiThread(2000, new Runnable() {

                @Override
                public void run() {
                    mIsBackPressed = false;
                }
            });
        }
    }


}
