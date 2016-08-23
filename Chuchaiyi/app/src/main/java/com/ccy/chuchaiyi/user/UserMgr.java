package com.ccy.chuchaiyi.user;

import android.text.TextUtils;

import com.ccy.chuchaiyi.constant.Constants;
import com.gjj.applibrary.http.model.BundleKey;
import com.gjj.applibrary.util.PreferencesManager;
import com.gjj.applibrary.util.SaveObjUtil;

/**
 * Created by Chuck on 2016/8/23.
 */
public class UserMgr {

    private UserInfo mUserInfo = null;
    public UserMgr() {
        String userInfoString = PreferencesManager.getInstance().get(Constants.USER_INFO);
        if(!TextUtils.isEmpty(userInfoString)) {
            mUserInfo = (UserInfo) SaveObjUtil.unSerialize(userInfoString);
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
        PreferencesManager.getInstance().put(BundleKey.TOKEN, userInfo.getToken());
        PreferencesManager.getInstance().put(Constants.USER_INFO, SaveObjUtil.serialize(userInfo));
    }

    public UserInfo getUser() {
        return mUserInfo;
    }

    public void logOut() {
        mUserInfo = null;
        PreferencesManager.getInstance().put(BundleKey.TOKEN, "");
        PreferencesManager.getInstance().put(Constants.USER_INFO, "");
    }
}
