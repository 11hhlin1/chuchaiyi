package com.ccy.chuchaiyi.contact;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.city.CitySort;
import com.ccy.chuchaiyi.city.PinyinComparator;
import com.ccy.chuchaiyi.city.PinyinUtils;
import com.ccy.chuchaiyi.event.EventOfSelPersonFromCheck;
import com.ccy.chuchaiyi.event.EventSelPersonFromOrder;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.order.EditPassengerFragment;
import com.ccy.chuchaiyi.widget.EditTextWithDel;
import com.ccy.chuchaiyi.widget.SideBar;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
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
    @Bind(R.id.sidrbar)
    SideBar mSideBar;
    @Bind(R.id.dialog)
    TextView dialog;
    ChoosePassengerAdapter mAdapter;
    final List<PassengerData> dataList = new ArrayList<>();

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
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        mBundle = getArguments();
        mAdapter = new ChoosePassengerAdapter(context, new ArrayList<PassengerData>());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setmCallBack(this);
        doRequest();
        mSideBar.setTextView(dialog);
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    linearLayoutManager.scrollToPositionWithOffset(position + 1, 10);
                }
            }
        });

        //根据输入框输入值的改变来过滤搜索
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<PassengerData> mSortList = new ArrayList<>();
        List<PassengerData> mList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            mSortList = dataList;
        } else {
            mSortList.clear();
            for (PassengerData sortModel : dataList) {
                if(sortModel.mStoreUser == null) {
                    String name = sortModel.name;
                    if (sortModel.mViewType != ChoosePassengerAdapter.VIEW_TYPE_ITEM || name.contains(filterStr) || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString().toUpperCase())||filterStr.toString().toUpperCase().startsWith(sortModel.sortLetters.toUpperCase())) {
                        mList.add(sortModel);
                    }
                } else {
                    mSortList.add(sortModel);
                }

            }
            // 根据a-z进行排序
            Collections.sort(mList, new PassengerListComparator());
            mSortList.addAll(mList);
        }

        mAdapter.setData(mSortList);
    }
    private void doRequest() {
        showLoadingDialog(R.string.submitting, false);
        OkHttpUtils.get(ApiConstants.GET_STORED_USER)
                .tag(this)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new JsonCallback<StoreUserList>(StoreUserList.class) {
                    @Override
                    public void onResponse(boolean isFromCache, StoreUserList list, Request request, @Nullable Response response) {
                        if(list != null && !Util.isListEmpty(list.AppMyStoredUsers)) {
                            PassengerData dataHeader = new PassengerData();
                            dataHeader.mViewType = ChoosePassengerAdapter.VIEW_TYPE_COMMON_TITLE;
                            dataHeader.mStoreUser = new StoreUser();
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
                                            ArrayList<String> indexString = new ArrayList<>();
                                            for (PassengerInfo info : passengerInfoList.Employees) {
                                                String firstChar = String.valueOf(PinyinUtils.getAlpha(info.getEmployeeName()).charAt(0));
                                                if (firstChar.toUpperCase().matches("[A-Z]")) {
                                                    if (!indexString.contains(firstChar)) {
                                                        indexString.add(firstChar);
                                                    }
                                                }
                                                mSideBar.setIndexText(indexString);
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
//            editPassenger(passengerInfo);
            onBackPressed();
            EventSelPersonFromOrder eventSelPersonFromOrder = new EventSelPersonFromOrder();
            eventSelPersonFromOrder.mPassengerInfo = passengerInfo;
            EventBus.getDefault().post(eventSelPersonFromOrder);
        } else if (flag == ChoosePassengerFragment.IS_FROM_CHECK) {
            onBackPressed();
            EventOfSelPersonFromCheck eventOfSelPersonFromCheck = new EventOfSelPersonFromCheck();
            eventOfSelPersonFromCheck.mPassengerInfo = passengerInfo;
            EventBus.getDefault().post(eventOfSelPersonFromCheck);
        }

    }
    void editPassenger(PassengerInfo passengerInfo) {
        Bundle bundle = new Bundle();
        bundle.putInt("flag", mBundle.getInt("flag"));
        bundle.putString("start", mBundle.getString("start"));
        bundle.putString("end", mBundle.getString("end"));
        bundle.putString("title", mBundle.getString("title"));
        bundle.putSerializable("passenger", passengerInfo);
        PageSwitcher.switchToTopNavPage(getActivity(), EditPassengerFragment.class, bundle, getString(R.string.edit),getString(R.string.sure));
    }
}
