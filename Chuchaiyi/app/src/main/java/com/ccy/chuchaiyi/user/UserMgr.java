package com.ccy.chuchaiyi.user;

import android.text.TextUtils;

import com.ccy.chuchaiyi.app.BaseApplication;
import com.ccy.chuchaiyi.constant.Constants;
import com.ccy.chuchaiyi.db.UserInfo;
import com.gjj.applibrary.app.AppLib;
import com.gjj.applibrary.http.model.BundleKey;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.util.PreferencesManager;
import com.gjj.applibrary.util.SaveObjUtil;
import com.gjj.applibrary.util.Util;

import java.util.List;

/**
 * Created by Chuck on 2016/8/23.
 */
public class UserMgr {

    private UserInfo mUserInfo = null;
    public UserMgr() {
//        String userInfoString = PreferencesManager.getInstance().get(Constants.USER_INFO);
        List<UserInfo> userInfos = BaseApplication.getDaoSession(AppLib.getContext()).getUserInfoDao().loadAll();
        if(!Util.isListEmpty(userInfos)) {
            mUserInfo = userInfos.get(0);
        }
    }

    public boolean isLogin() {
        UserInfo info = mUserInfo;
        if (null == info) {
            return false;
        }

        if (TextUtils.isEmpty(info.getToken())) {
            return false;
        }
        return true;
    }

    public void saveUserInfo(UserInfo userInfo) {
        mUserInfo = userInfo;
        PreferencesManager.getInstance().put(BundleKey.TOKEN, userInfo.getToken());
        BaseApplication.getDaoSession(AppLib.getContext()).getUserInfoDao().deleteAll();
//        PreferencesManager.getInstance().put(Constants.USER_INFO, SaveObjUtil.serialize(userInfo));
        BaseApplication.getDaoSession(AppLib.getContext()).getUserInfoDao().insert(userInfo);
    }

    public UserInfo getUser() {
        return mUserInfo;
    }

    public void logOut() {
        mUserInfo = null;
        PreferencesManager.getInstance().put(BundleKey.TOKEN, "");
        BaseApplication.getDaoSession(AppLib.getContext()).getUserInfoDao().deleteAll();
//        PreferencesManager.getInstance().put(Constants.USER_INFO, "");
    }
}
