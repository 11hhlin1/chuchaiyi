package com.ccy.chuchaiyi.city;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.event.EventOfSelCity;
import com.ccy.chuchaiyi.event.EventOfSelDate;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.widget.EditTextWithDel;
import com.ccy.chuchaiyi.widget.UnScrollableGridView;
import com.gjj.applibrary.event.EventOfTokenError;
import com.gjj.applibrary.http.callback.CommonCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.request.BaseRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/8/15.
 */
public class ChooseCityFragment extends BaseFragment {
    @Bind(R.id.et_search)
    EditTextWithDel etSearch;
    @Bind(R.id.country_list)
    ListView countryList;
    @Bind(R.id.dialog)
    TextView dialog;
//    @Bind(R.id.sidrbar)
//    SideBar sidrbar;
    private SortAdapter adapter;
    CityAdapter mHotAdapter;
    private List<CitySort> allCitySorts;
    private List<CitySort> filterCitySorts;
    public static final int SET_OUT = 0;
    public static final int ARRIVE = 1;

    private int mType;
    private String mSelCity;
    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_choose_city;
    }

    @Override
    public void initView() {
//        initDatas();
        Bundle bundle = getArguments();
        mType = bundle.getInt("type");
        mSelCity = bundle.getString("selCity");
        initEvents();
        setAdapter();
        OkHttpUtils.get(ApiConstants.GET_FLIGHT_LOCATION)
                .tag(this)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new CommonCallback<CitySortList>() {
                    @Override
                    public CitySortList parseNetworkResponse(Response response) throws Exception {
                        String responseData = response.body().string();
                        JSONObject jsonObject = JSON.parseObject(responseData);
                        final String msg = jsonObject.getString("Message");
                        final int code = jsonObject.getIntValue("Code");
                        switch (code) {
                            case 0: {
                                CitySortList citySortList = new CitySortList();
                                citySortList.mAllList = JSON.parseArray(jsonObject.getString("All"), CitySort.class);
                                citySortList.mHotList = JSON.parseArray(jsonObject.getString("Hot"), CitySort.class);
                                return citySortList;
                            }
                            case 401: {
                                EventBus.getDefault().post(new EventOfTokenError());
                                throw new IllegalStateException("用户授权信息无效");
                            }
                            default:
                                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + msg);
                        }
                    }

                    @Override
                    public void onResponse(boolean isFromCache, final CitySortList citySortList, Request request, @Nullable Response response) {
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                     if(!Util.isListEmpty(citySortList.mHotList)) {
                                         mHotAdapter.setData(citySortList.mHotList);
                                     }
                                   if(!Util.isListEmpty(citySortList.mAllList)) {
                                       filterCitySorts = citySortList.mAllList;
                                       allCitySorts = citySortList.mAllList;
                                       Collections.sort(allCitySorts, new PinyinComparator());
                                       adapter.setData(allCitySorts);
                                   }
                               }
                           });
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onAfter(boolean isFromCache, @Nullable CitySortList citySortList, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onAfter(isFromCache, citySortList, call, response, e);
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        showLoadingDialog(R.string.submitting, true);
                    }
                });
    }


    private void setAdapter() {
        allCitySorts = new ArrayList<>();
        Collections.sort(allCitySorts, new PinyinComparator());
        adapter = new SortAdapter(getActivity(), allCitySorts, mSelCity);
        countryList.addHeaderView(initHeadView());
        countryList.setAdapter(adapter);
    }

    private void initEvents() {
//        //设置右侧触摸监听
//        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
//            @Override
//            public void onTouchingLetterChanged(String s) {
//                //该字母首次出现的位置
//                int position = adapter.getPositionForSection(s.charAt(0));
//                if (position != -1) {
//                    countryList.setSelection(position + 1);
//                }
//            }
//        });

        //ListView的点击事件
        countryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ToastUtil.shortToast(getActivity(), adapter.getItem(position - 1).getName());
                onBackPressed();
                EventOfSelCity eventOfSelCity = new EventOfSelCity();
                eventOfSelCity.mCity = adapter.getItem(position - 1);
                eventOfSelCity.mType = mType;
                EventBus.getDefault().post(eventOfSelCity);
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
                if(s.toString().length() > 0) {
                    filterData(s.toString());
                } else {
                    Collections.sort(allCitySorts, new PinyinComparator());
                    adapter.setData(allCitySorts);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

//    private void initDatas() {
//        sidrbar.setTextView(dialog);
//    }

    private View initHeadView() {
        View headView = getActivity().getLayoutInflater().inflate(R.layout.headview, null);
        UnScrollableGridView mGvCity = (UnScrollableGridView) headView.findViewById(R.id.gv_hot_city);
//        String[] datas = getResources().getStringArray(R.array.city);
//        ArrayList<CitySort> cityList = new ArrayList<>();
//        for (int i = 0; i < datas.length; i++) {
//            CitySort sort = new CitySort();
//            sort.setName(datas[i]);
//            cityList.add(sort);
//        }
        mHotAdapter = new CityAdapter(getActivity(), new ArrayList<CitySort>());
        mGvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onBackPressed();
                EventOfSelCity eventOfSelCity = new EventOfSelCity();
                eventOfSelCity.mCity = mHotAdapter.getItem(position);
                eventOfSelCity.mType = mType;
                EventBus.getDefault().post(eventOfSelCity);
            }
        });
        mGvCity.setAdapter(mHotAdapter);
        return headView;
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<CitySort> mSortList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            mSortList = filterCitySorts;
        } else {
            mSortList.clear();
            for (CitySort sortModel : filterCitySorts) {
                String name = sortModel.getPinyin();
                String shortPin = sortModel.getPinyinShort();
                if (shortPin.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString().toUpperCase())) {
                    mSortList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(mSortList, new PinyinComparator());
        adapter.setData(mSortList);
    }
//
//    private List<CitySortModel> filledData(String[] date) {
//        List<CitySortModel> mSortList = new ArrayList<>();
//        ArrayList<String> indexString = new ArrayList<>();
//
//        for (int i = 0; i < date.length; i++) {
//            CitySortModel sortModel = new CitySortModel();
//            sortModel.setName(date[i]);
//            String pinyin = PinyinUtils.getPingYin(date[i]);
//            String sortString = pinyin.substring(0, 1).toUpperCase();
//            if (sortString.matches("[A-Z]")) {
//                sortModel.setSortLetters(sortString.toUpperCase());
//                if (!indexString.contains(sortString)) {
//                    indexString.add(sortString);
//                }
//            }
//            mSortList.add(sortModel);
//        }
//        Collections.sort(indexString);
////        sidrbar.setIndexText(indexString);
//        return mSortList;
//    }
}
