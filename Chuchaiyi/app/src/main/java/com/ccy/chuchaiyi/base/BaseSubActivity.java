package com.ccy.chuchaiyi.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;

import com.ccy.chuchaiyi.R;
import com.gjj.applibrary.log.L;


/**
 * 
 * 类/接口注释
 * 
 * @author panrq
 * @createDate Dec 29, 2014
 * 
 */
public class BaseSubActivity extends BaseFragmentActivity {
    private InputMethodManager mInputMethodManager;
    public static final String BUNDLE_KEY_BUG_POPBACKSTACK_AFTER_STATESAVED = "popbackStack_exception";
    //新增页面action
    public final static String INTENT_EXTRA_FRAGMENT_ACTION_ADD_PAGE = "action_add_page";
    //弹出页面action
    public final static String INTENT_EXTRA_FRAGMENT_ACTION_POP_PAGE = "action_pop_page";

    //页面类名参数
    public final static String INTENT_EXTRA_FRAGMENT_CLASS_NAME = "class_name";
    //弹出到指定某个页面时，是否弹出该页面
    public final static String INTENT_EXTRA_FRAGMENT_CLASS_INCLUDE = "class_include";
    //弹出页面数量参数
    public final static String INTENT_EXTRA_FRAGMENT_POP_PAGE_COUNT = "pop_page_count";

    //页面参数
    public final static String INTENT_EXTRA_FRAGMENT_ARGS = "args";
    /**
     * 标识二级页面是否存在
     */
    private static boolean sIsAlive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        GjjEventBus.getInstance().register(mEventReceiver);
        sIsAlive = true;
    }
    public static boolean isSubActivityAlive() {
        return sIsAlive;
    }
//    private Object mEventReceiver = new Object() {
//        @SuppressWarnings("unused")
//        public void onEventBackgroundThread(EventOfLaunchMainAct event) {
//            FragmentManager fm = getSupportFragmentManager();
//            if (fm != null && !fm.isDestroyed()) {
//                int backStackCount = fm.getBackStackEntryCount();
//                for (int i = 0; i < backStackCount; i++) {
//                    fm.popBackStack();
//                }
//            }
//        }
//    };

    @Override
    protected void onNewIntent(Intent intent) {
        L.d("BaseFragment onNewIntent %s", intent);
        if (null == intent) {
            return;
        }
        if (intent.hasExtra(INTENT_EXTRA_FRAGMENT_CLASS_NAME)) {
            // 修正快速点击，可能导致打开多个相同fragment问题
            String className = intent.getStringExtra(INTENT_EXTRA_FRAGMENT_CLASS_NAME);
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.content);
            if (null != f && f.getClass().getName().equals(className)) {
                return;
            }
        }

        handleIntent(intent);
        super.onNewIntent(intent);
    }

    protected void init(Bundle savedInstanceState) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.content);
        // 避免后台销毁，重新进入时，重复加载Fragment
        if (f == null) {
            handleIntent(getIntent());
        } else {
            handleArgs(f.getArguments());
        }
    }

    @Override
    public void onBackPressed() {
        doBackPress(true);
    }

    protected void doBackPress(boolean fromKeyboard) {
        if (isFinishing()) {
            return;
        }
        hideKeyboardForCurrentFocus();
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.content);
        if (f != null && f instanceof BaseFragment) {
            BaseFragment fragment = (BaseFragment) f;
            if (!fragment.goBack(fromKeyboard)) {

                if (fm.getBackStackEntryCount() <= 1) {
                    finish();
                } else {
                    try {
                        fm.popBackStackImmediate();
                    } catch (IllegalStateException e) {
                        L.e(e);
                        f.getArguments().putBoolean(BUNDLE_KEY_BUG_POPBACKSTACK_AFTER_STATESAVED, true);
                    }
                    f = fm.findFragmentById(R.id.content);
                    if (f != null) {
                        handleArgs(f.getArguments());
                    }
                }
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        sIsAlive = false;
        super.finish();
    }

    /**
     * 弹至指定页面
     * @param fragmentName 页面类名
     * @param include 标识指定页面是否弹出
     */
    protected void goBackToFragment(String fragmentName, Bundle bundle, boolean include) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(fragmentName, include ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        if (fm.getBackStackEntryCount() <= 0) {
            finish();
        } else {
            Fragment f = fm.findFragmentById(R.id.content);
            if (f != null) {
                handleArgs(bundle);
            }
        }
    }

    /**
     * 弹出指定数量的页面
     * @param count
     * @return
     */
    protected boolean goBackForCount(int count) {
        FragmentManager fm = getSupportFragmentManager();
        if (count > 0 && fm.getBackStackEntryCount() >= count) {
            while (count > 0) {
                fm.popBackStackImmediate();
                count--;
            }
            if (fm.getBackStackEntryCount() <= 0) {
                finish();
            } else {
                Fragment f = fm.findFragmentById(R.id.content);
                if (f != null) {
                    handleArgs(f.getArguments());
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
//        GjjEventBus.getInstance().unregister(mEventReceiver);
        super.onDestroy();
    }

    public void handleIntent(Intent intent) {
        hideKeyboardForCurrentFocus();
        if (intent == null || (intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0) {
            return;
        }
        if (isFinishing()) {
            return;
        }
        if (intent.hasExtra(INTENT_EXTRA_FRAGMENT_ACTION_ADD_PAGE)) {
            String className = intent.getStringExtra(INTENT_EXTRA_FRAGMENT_CLASS_NAME);
            Bundle args = intent.getParcelableExtra(INTENT_EXTRA_FRAGMENT_ARGS);
            try {
                @SuppressWarnings("unchecked")
                Class<BaseFragment> clazz = (Class<BaseFragment>) Class.forName(className);
                addFragment(clazz, args);
            } catch (ClassNotFoundException e) {
                L.e(e);
            }
        } else if (intent.hasExtra(INTENT_EXTRA_FRAGMENT_ACTION_POP_PAGE)) {
            Bundle args = intent.getParcelableExtra(INTENT_EXTRA_FRAGMENT_ARGS);
            if (intent.hasExtra(INTENT_EXTRA_FRAGMENT_CLASS_NAME)) {
                String className = intent.getStringExtra(INTENT_EXTRA_FRAGMENT_CLASS_NAME);
                goBackToFragment(className, args, intent.getBooleanExtra(INTENT_EXTRA_FRAGMENT_CLASS_INCLUDE, false));
            } else if (intent.hasExtra(INTENT_EXTRA_FRAGMENT_POP_PAGE_COUNT)) {
                goBackForCount(intent.getIntExtra(INTENT_EXTRA_FRAGMENT_POP_PAGE_COUNT, 0));
            }
        }
        setIntent(intent);
    }

    /**
     * 添加fragment
     *
     * @param clazz
     * @param bundle
     */
    private <T extends BaseFragment> void addFragment(Class<T> clazz, Bundle bundle) {

        BaseFragment fragment = FragmentFactory.getFragmentFactory().getFragment(clazz, false);
        if (fragment == null) {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        // set bundle
        if (bundle != null) {
            fragment.setArguments(bundle);
            if (bundle.getBoolean(BaseFragment.FLAT_CLEAR_TOP_FRAGMENT)) {
                while (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStackImmediate();
                }
            }
        }

        FragmentTransaction fT = fm.beginTransaction();
//        fT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Fragment preFragment = fm.findFragmentById(R.id.content);
        if (null != preFragment) {
            fT.hide(preFragment);
            fT.setCustomAnimations(R.anim.right_side_in, 0, 0, R.anim.right_side_out);
        }

        fT.add(R.id.content, fragment);
        fT.addToBackStack(clazz.getName());
        fT.commitAllowingStateLoss();
        fm.executePendingTransactions();
        handleArgs(bundle);
    }

    public BaseFragment getCurrentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        return (BaseFragment)fm.findFragmentById(R.id.content);
    }

    protected void handleArgs(Bundle bundle) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (null != fragment) {
            fragment.onActivityResult(requestCode & '\uffff', resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void hideKeyboardForCurrentFocus() {
        if (getCurrentFocus() != null) {
            if (mInputMethodManager == null) {
                mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
