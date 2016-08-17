package com.ccy.chuchaiyi.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.city.CitySort;
import com.ccy.chuchaiyi.city.CitySortList;
import com.ccy.chuchaiyi.city.PinyinComparator;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.gjj.applibrary.event.EventOfTokenError;
import com.gjj.applibrary.http.callback.CommonCallback;
import com.gjj.applibrary.util.Util;
import com.gjj.applibrary.widget.EmptyErrorViewController;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.internal.PrepareRelativeLayout;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/8/17.
 */
public class FlightsListFragment extends BaseFragment implements ExpandableListView.OnGroupClickListener{

    @Bind(R.id.pre_day)
    TextView preDay;
    @Bind(R.id.today_tv)
    TextView todayTv;
    @Bind(R.id.next_day)
    TextView nextDay;
    @Bind(R.id.elistview)
    PullToRefreshExpandableListView mListView;
    /**
     * 空提示
     */
    @Bind(R.id.empty_tv)
    TextView mEmptyTextView;
    /**
     * 错误提示
     */
    @Bind(R.id.error_tv)
    TextView mErrorTextView;
    /**
     * 重新加载
     */
    @OnClick(R.id.error_tv)
    void errorReload() {
        mListView.setTag(R.id.error_tv, true);
        mListView.setRefreshing();
    }

    /**
     * 重新加载
     */
    @OnClick(R.id.empty_tv)
    void emptyReload() {
        mListView.setRefreshing();
    }
    private EmptyErrorViewController mEmptyErrorViewController;
    private FlightsListAdapter mAdapter;
    @OnClick({R.id.pre_day, R.id.next_day})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pre_day:
                break;
            case R.id.next_day:
                break;
        }
    }
    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_flight_list;
    }

    @Override
    public void initView() {

        final PullToRefreshExpandableListView listView = mListView;
        mAdapter = new FlightsListAdapter(getActivity(), new ArrayList<FlightInfo>());
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                mEmptyErrorViewController.onLoading();
                Object tag = listView.getTag(R.id.error_tv);
                if (tag instanceof Boolean && (Boolean) tag) {
                    listView.setTag(R.id.error_tv, false);
                    doRequest();
                } else {
                    doRequest();
                }
            }
        });
        ExpandableListView expandableListView = listView.getRefreshableView();
        expandableListView.setAdapter(mAdapter);
//        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupClickListener(this);
        listView.setRefreshPrepareLayoutListener(new PrepareRelativeLayout.RefreshPrepareLayoutListener() {
            @Override
            public void onPrepareLayout() {
                mListView.setTag(R.id.error_tv, true);
                mListView.setRefreshing();
            }
        });
        mEmptyErrorViewController = new EmptyErrorViewController(mEmptyTextView, mErrorTextView, listView, new EmptyErrorViewController.AdapterWrapper(mAdapter));
    }

    void doRequest() {
        Bundle bundle =getArguments();
        GetFlightsRequest request = new GetFlightsRequest();
        request.setDepartureCode(bundle.getString("DepartureCode"));
        request.setArrivalCode(bundle.getString("ArrivalCode"));
        request.setFlightDate(bundle.getString("FlightDate"));
        request.setBunkType(bundle.getString("BunkType"));
        request.setDepartureCodeIsCity(true);
        request.setArrivalCodeIsCity(true);
        request.setAirlines("");
        request.setFlightNo("");
        request.setFactBunkPriceLowestLimit(0);
        OkHttpUtils.post(ApiConstants.GET_FLIGHT_LIST)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .postJson(JSON.toJSONString(request))
                .execute(new CommonCallback<FlightInfoList>() {
                    @Override
                    public FlightInfoList parseNetworkResponse(Response response) throws Exception {
                        String responseData = response.body().string();
                        JSONObject jsonObject = JSON.parseObject(responseData);
                        final String msg = jsonObject.getString("Message");
                        final int code = jsonObject.getIntValue("Code");
                        switch (code) {
                            case 0: {
                                FlightInfoList list = new FlightInfoList();
                                list.mFlightInfoList = (ArrayList<FlightInfo>) JSON.parseArray(jsonObject.getString("Flights"), FlightInfo.class);
                                return list;
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
                    public void onResponse(boolean isFromCache, final FlightInfoList flightInfoList, Request request, @Nullable Response response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mListView.onRefreshComplete();
                                if(!Util.isListEmpty(flightInfoList.mFlightInfoList)) {
                                    mAdapter.setData(flightInfoList.mFlightInfoList);
                                }
                            }
                        });
                    }
                });
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }
}
