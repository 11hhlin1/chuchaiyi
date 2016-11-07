package com.ccy.chuchaiyi.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ccy.chuchaiyi.R;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.task.ForegroundTaskExecutor;

import java.util.ArrayList;

/**
 *
 * 类/接口注释
 *
 * @author panrq
 * @createDate Dec 29, 2014
 *
 */
public class BaseMainActivity extends BaseFragmentActivity {

    private ArrayList<Fragment> mHistoryList = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public <T extends BaseFragment> Fragment replaceFragment(Class<T> clazz) {
        return replaceFragment(clazz, new Bundle());
    }


    public boolean isFragmentInCache(BaseFragment f) {
        if (FragmentFactory.getFragmentFactory() != null) {
            return FragmentFactory.getFragmentFactory().getFragmentFromCache(f.getClass()) != null;
        }
        return false;
    }


    /**
     * @param clazz
     * @param args
     */
    public <T extends BaseFragment> Fragment replaceFragment(Class<T> clazz, Bundle args) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fT = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(clazz.getName());
        if (fragment == null) {
            fragment = FragmentFactory.getFragmentFactory().getFragment(clazz, false);
            fragment.setArguments(args);
            fT.add(R.id.content, fragment, clazz.getName());
        } else {
            fT.show(fragment);
        }
        ArrayList<Fragment> historyList = mHistoryList;
        if (!historyList.contains(fragment)) {
            historyList.add(fragment);
        }

        for (Fragment other : historyList) {
            if (other != fragment) {
                fT.hide(other);
            }
        }
        fT.commitAllowingStateLoss();

        return fragment;
    }

    protected <T extends BaseFragment> BaseFragment findFragment(Class<T> clazz) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (BaseFragment) fragmentManager.findFragmentByTag(clazz.getName());
    }

    public BaseFragment getCurrentFragment() {
        for (Fragment fragment : mHistoryList) {
            if (!fragment.isHidden()) {
                return (BaseFragment)fragment;
            }
        }
        return null;
    }

    protected void init() {
    }

    @Override
    public void onBackPressed() {
        BaseFragment fragment = getCurrentFragment();
        if (fragment == null || !fragment.goBack(true)) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public <T extends BaseFragment> void removeCacheFragment(Class<T> clazz) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(clazz.getName());
        if (fragment != null) {
            L.d("BaseMainActivity removeCacheFragment: %s", clazz.getName());
            mHistoryList.remove(fragment);
            FragmentTransaction fT = fragmentManager.beginTransaction();
            fT.remove(fragment);
            fT.commitAllowingStateLoss();
        }
    }

    public static void killApp() {
        ForegroundTaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }
}
