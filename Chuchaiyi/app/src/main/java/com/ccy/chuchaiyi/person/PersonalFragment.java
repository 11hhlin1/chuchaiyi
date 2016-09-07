package com.ccy.chuchaiyi.person;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.app.BaseApplication;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.login.LoginActivity;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/8/9.
 */
public class PersonalFragment extends BaseFragment {

    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.username)
    TextView username;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.apartment_tv)
    TextView apartmentTv;
    @Bind(R.id.personal_item)
    RelativeLayout personalItem;
    @Bind(R.id.company_tv)
    TextView companyTv;
    @Bind(R.id.change_psw_item)
    RelativeLayout changePswLayout;
    @Bind(R.id.setting_item)
    RelativeLayout settingLayout;
    @Bind(R.id.logout_btn)
    Button logoutBtn;


    @OnClick(R.id.setting_item)
    void goNew() {
        PageSwitcher.switchToTopNavPage(getActivity(), SettingFragment.class, null, getString(R.string.setting),null);
    }
    @OnClick(R.id.change_psw_item)
    void changePsw() {
        PageSwitcher.switchToTopNavPage(getActivity(), ChangePswFragment.class, null, getString(R.string.set_psw),null);
    }
    @OnClick(R.id.logout_btn)
    void logout() {
        OkHttpUtils.post(ApiConstants.LOGOUT)
                .tag(getActivity())
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<String>(String.class) {
                    @Override
                    public void onResponse(boolean b, String s, Request request, @Nullable Response response) {
                        BaseApplication.getUserMgr().logOut();
                        Intent intent = new Intent();
                        Activity activity = getActivity();
                        intent.setClass(activity, LoginActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                });
    }
    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_person;
    }

    @Override
    public void initView() {

    }
}
