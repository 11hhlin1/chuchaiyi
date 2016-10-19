package com.ccy.chuchaiyi.main;

import android.os.Bundle;
import android.widget.ImageView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.app.BaseApplication;
import com.ccy.chuchaiyi.base.BaseMainActivity;
import com.ccy.chuchaiyi.check.CategoryData;
import com.ccy.chuchaiyi.check.CheckFragment;
import com.ccy.chuchaiyi.check.NoCheckTypeFragment;
import com.ccy.chuchaiyi.check.ViewPagerGoodListFragment;
import com.ccy.chuchaiyi.db.UserInfo;
import com.ccy.chuchaiyi.event.EventOfChangeTab;
import com.ccy.chuchaiyi.event.EventOfSelPassenger;
import com.ccy.chuchaiyi.index.IndexFragment;
import com.ccy.chuchaiyi.order.OrderFragment;
import com.ccy.chuchaiyi.person.PersonalFragment;
import com.ccy.chuchaiyi.widget.NestRadioGroup;
import com.gjj.applibrary.task.MainTaskExecutor;
import com.gjj.applibrary.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chuck on 16/7/17.
 */
public class MainActivity extends BaseMainActivity {

    private static final String SAVE_INSTANCE_STATE_KEY_TAB_ID = "tabId";

    @Bind(R.id.group_tab)
    NestRadioGroup mRadioGroup;

    @Bind(R.id.redTip)
    ImageView mRedTip;
    private boolean mIsBackPressed = false;
    private static MainActivity sMainActivity;

    public static MainActivity getMainActivity() {
        return sMainActivity;
    }

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
                    case R.id.order_tab:
                        showOrderTab();
                        break;
                    case R.id.check_tab:
                        showCheckTab();
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
            case R.id.order_tab:
                mRadioGroup.check(R.id.order_tab);
                break;
            case R.id.check_tab:
                mRadioGroup.check(R.id.check_tab);
                break;
            default:
                mRadioGroup.check(R.id.index_tab);
                break;
        }
        EventBus.getDefault().register(this);
//        Glide.with(this).load("http://jcodecraeer.com/uploads/20150327/1427445294447874.jpg")
//                .into(imageView);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeTab(EventOfChangeTab event) {
        if(isFinishing())
            return;
        switch (event.mIndex) {
            case 2:
                mRadioGroup.check(R.id.order_tab);
                break;
            case 0:
                mRadioGroup.check(R.id.index_tab);
                break;
        }
    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void changeTab(EventOfChangeTab event) {
//        if(isFinishing())
//            return;
//        mRedTip.setVisibility();
//    }
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

        Bundle bundle = new Bundle();
        ArrayList<CategoryData> dataList = new ArrayList<>();
        UserInfo userInfo = BaseApplication.getUserMgr().getUser();
        if(userInfo.getApprovalRequired()) {
            CategoryData categoryData = new CategoryData();
            categoryData.mCateId = CategoryData.MY_APPLY;
            categoryData.mCateName = getString(R.string.my_launch);
            dataList.add(categoryData);
            CategoryData categoryData1 = new CategoryData();
            categoryData1.mCateId = CategoryData.MY_UN_CHECK;
            categoryData1.mCateName = getString(R.string.un_check);
            dataList.add(categoryData1);
            CategoryData categoryData2 = new CategoryData();
            categoryData2.mCateId = CategoryData.MY_CHECKED;
            categoryData2.mCateName = getString(R.string.checked);
            dataList.add(categoryData2);
        }

        if(userInfo.getOverrunOption().equals("WarningAndAuthorize")) {
            CategoryData categoryData1 = new CategoryData();
            categoryData1.mCateId = CategoryData.MY_UN_AUDIT;
            categoryData1.mCateName = getString(R.string.un_audit);
            dataList.add(categoryData1);
            CategoryData categoryData2 = new CategoryData();
            categoryData2.mCateId = CategoryData.MY_AUDITED;
            categoryData2.mCateName = getString(R.string.audited);
            dataList.add(categoryData2);
        }
        bundle.putParcelableArrayList("data", dataList);
        if(dataList.size() > 0) {
            replaceFragment(ViewPagerGoodListFragment.class,bundle);
        }  else {
            replaceFragment(NoCheckTypeFragment.class,bundle);
        }
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
