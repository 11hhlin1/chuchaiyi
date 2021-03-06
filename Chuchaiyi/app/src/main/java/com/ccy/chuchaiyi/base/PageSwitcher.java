package com.ccy.chuchaiyi.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.gjj.applibrary.app.AppLib;

/**
 * Created by Chuck on 2016/7/26.
 */
public class PageSwitcher {

    /**
     * 切换到带顶部导航界面
     *
     * @param act
     * @param fragment
     * @param bundle
     * @param title 顶部导航标题，空则隐藏按钮
     * @param rightTitle 右边按钮标题，空则隐藏按钮
     */
    public static void switchToTopNavPage(Activity act, Class<? extends Fragment> fragment,
                                          Bundle bundle, String title, String rightTitle) {
        if (null == bundle) {
            bundle = new Bundle();
        }
        bundle.putString(TopNavSubActivity.PARAM_TOP_TITLE, title);
        bundle.putString(TopNavSubActivity.PARAM_TOP_RIGHT, rightTitle);
        switchToTopNavPage(act, fragment, bundle);
    }

    /**
     * 切换到带顶部导航界面
     *
     * @param act
     * @param fragment
     * @param bundle
     */
    public static void switchToTopNavPage(Activity act, Class<? extends Fragment> fragment, Bundle bundle) {
        switchToTopNavPage(act, fragment.getName(), bundle);
    }
    /**
     * 切换到带顶部导航界面
     *
     * @param act
     * @param fragmentName
     * @param bundle
     */
    public static void switchToTopNavPage(Activity act, String fragmentName, Bundle bundle) {
        Intent intent = new Intent(act, TopNavSubActivity.class);
        intent.putExtra(BaseSubActivity.INTENT_EXTRA_FRAGMENT_ACTION_ADD_PAGE, 1);
        intent.putExtra(BaseSubActivity.INTENT_EXTRA_FRAGMENT_CLASS_NAME, fragmentName);

        intent.putExtra(BaseSubActivity.INTENT_EXTRA_FRAGMENT_ARGS, bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        startActivity(act, intent);
    }
    /**
     * 弹至指定页面
     * @param act
     * @param fragment
     * @param include
     */
    public static void goBackTopNavPage(Activity act, Class<? extends Fragment> fragment, Bundle bundle, boolean include) {
        Context ctx = AppLib.getContext();
        Intent intent = new Intent(ctx, TopNavSubActivity.class);
        intent.putExtra(BaseSubActivity.INTENT_EXTRA_FRAGMENT_ACTION_POP_PAGE, 1);
        intent.putExtra(BaseSubActivity.INTENT_EXTRA_FRAGMENT_CLASS_NAME, fragment.getName());
        intent.putExtra(BaseSubActivity.INTENT_EXTRA_FRAGMENT_CLASS_INCLUDE, include);
        intent.putExtra(BaseSubActivity.INTENT_EXTRA_FRAGMENT_ARGS, bundle);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        startActivity(act, intent);
    }

    private static void startActivity(Activity act, Intent intent) {
        if (act != null) {
            act.startActivity(intent);
        }
    }
}
