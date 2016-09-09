package com.ccy.chuchaiyi.contact;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.city.PinyinComparator;
import com.ccy.chuchaiyi.city.PinyinUtils;
import com.ccy.chuchaiyi.event.EventOfSelPerson;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.order.EditPassengerFragment;
import com.ccy.chuchaiyi.widget.EditTextWithDel;
import com.gjj.applibrary.http.callback.CommonCallback;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.http.callback.ListCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/8/24.
 */
public class ChoosePassengerFragment extends BaseFragment implements ChoosePassengerAdapter.CallBack{

    @Bind(R.id.et_search)
    EditTextWithDel etSearch;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    ChoosePassengerAdapter mAdapter;

    public final static int IS_FROM_ORDER = 0;
    public final static int IS_FROM_CHECK = 1;

    Bundle mBundle;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_choose_passenger;
    }

    @Override
    public void initView() {
        Context context = getActivity();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mBundle = getArguments();
        mAdapter = new ChoosePassengerAdapter(context, new ArrayList<PassengerData>());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setmCallBack(this);
        doRequest();
    }

    private void doRequest() {
        showLoadingDialog(R.string.submitting,false);
        final List<PassengerData> dataList = new ArrayList<>();
        OkHttpUtils.get(ApiConstants.GET_STORED_USER)
                .tag(this)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new JsonCallback<StoreUserList>(StoreUserList.class) {
                    @Override
                    public void onResponse(boolean isFromCache, StoreUserList list, Request request, @Nullable Response response) {
                        if(list != null && !Util.isListEmpty(list.AppMyStoredUsers)) {
                            PassengerData dataHeader = new PassengerData();
                            dataHeader.mViewType = ChoosePassengerAdapter.VIEW_TYPE_COMMON_TITLE;
                            dataList.add(dataHeader);
                            for (StoreUser info : list.AppMyStoredUsers) {
                                PassengerData data = new PassengerData();
                                data.name = info.getName();
                                data.mViewType = ChoosePassengerAdapter.VIEW_TYPE_ITEM;
                                data.sortLetters = PinyinUtils.getPingYin(info.getName());
                                data.mStoreUser = info;
                                dataList.add(data);
                            }
                        }
                        OkHttpUtils.get(ApiConstants.GET_EMPLOYEES)
                                .tag(this)
                                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                                .execute(new JsonCallback<PassengerInfoList>(PassengerInfoList.class) {
                                    @Override
                                    public void onResponse(boolean isFromCache, PassengerInfoList passengerInfoList, Request request, @Nullable Response response) {
                                        dismissLoadingDialog();
                                        if(passengerInfoList != null && !Util.isListEmpty(passengerInfoList.Employees)) {
                                            Collections.sort(passengerInfoList.Employees, new PassengerPinyinComparator());
                                            String mLastChar = null;
                                            for (PassengerInfo info : passengerInfoList.Employees) {
                                                String firstChar = String.valueOf(PinyinUtils.getAlpha(info.getEmployeeName()).charAt(0));
                                                if(TextUtils.isEmpty(mLastChar) || !firstChar.equals(mLastChar)) {
                                                    PassengerData data = new PassengerData();
                                                    data.mViewType = ChoosePassengerAdapter.VIEW_TYPE_PINYIN_ITEM;
                                                    data.sortLetters = firstChar;
                                                    dataList.add(data);
                                                }
                                                PassengerData data = new PassengerData();
                                                data.name = info.getEmployeeName();
                                                data.mViewType = ChoosePassengerAdapter.VIEW_TYPE_ITEM;
                                                data.mPassengerInfo = info;
                                                data.sortLetters = firstChar;
                                                mLastChar = firstChar;
                                                dataList.add(data);
                                            }
                                            mAdapter.setData(dataList);
                                        }

                                    }

                                    @Override
                                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                                        super.onError(isFromCache, call, response, e);
                                        dismissLoadingDialog();
                                        ToastUtil.shortToast(R.string.load_fail);
                                    }
                                });
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        dismissLoadingDialog();
                        ToastUtil.shortToast(R.string.load_fail);
                    }
                });

    }

    @Override
    public void selPerson(PassengerInfo passengerInfo) {
        int flag = mBundle.getInt("flag");
        if (flag == ChoosePassengerFragment.IS_FROM_ORDER) {
            editPassenger(passengerInfo);
        } else if (flag == ChoosePassengerFragment.IS_FROM_CHECK) {
            onBackPressed();
        }
        EventOfSelPerson eventOfSelPerson = new EventOfSelPerson();
        eventOfSelPerson.mPassengerInfo = passengerInfo;
        EventBus.getDefault().post(eventOfSelPerson);
    }
    void editPassenger(PassengerInfo passengerInfo) {
//        Bundle bundle = new Bundle();
        mBundle.putSerializable("passenger", passengerInfo);
        PageSwitcher.switchToTopNavPage(getActivity(), EditPassengerFragment.class, mBundle, getString(R.string.edit),getString(R.string.sure));
    }
}
