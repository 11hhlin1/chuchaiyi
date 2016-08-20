package com.ccy.chuchaiyi.index;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.widget.NestRadioGroup;
import com.gjj.applibrary.event.EventOfTokenError;
import com.gjj.applibrary.http.callback.CommonCallback;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.gjj.applibrary.widget.EmptyErrorViewController;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.internal.PrepareRelativeLayout;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/8/17.
 */
public class FlightsListFragment extends BaseFragment implements ExpandableListView.OnGroupClickListener {

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
    @Bind(R.id.top_date)
    RelativeLayout topDate;
    @Bind(R.id.time_tab)
    RadioButton timeTab;
    @Bind(R.id.price_tab)
    RadioButton priceTab;
    @Bind(R.id.seat_tab)
    RadioButton seatTab;
    @Bind(R.id.company_tab)
    RadioButton companyTab;
    @Bind(R.id.redTip)
    ImageView redTip;
    @Bind(R.id.company_fl)
    FrameLayout companyFl;
    private String mDepartureCode;
    private String mArrivalCode;
    private String mBunkType;
    private String mSelSeatType;
    private String mSelAirlineCode;

    private static final int mLowToHigh = 1;
    private static final int mHighToLow = 2;
    private int mPriceType;

    private int mTimeType;
    private static final int EARLY_TO_LATE = -1;
    private static final int LATE_TO_EARLY = -2;
    /**
     * 弹出框
     */
    private PopupWindow mPickUpPopWindow;
    private ListPopupAdapter mSelectorAdapter;
    private int mSeatIndex = 0;
    private List<SeatType> mItemList;

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

    @Bind(R.id.group_tab)
    NestRadioGroup mRadioGroup;

    private List<FlightInfo> mFlightInfoList;

    private EmptyErrorViewController mEmptyErrorViewController;
    private FlightsListAdapter mAdapter;
    private String mCurrentDateString;
    private Date mCurrentDate;

    @OnClick({R.id.pre_day, R.id.next_day, R.id.time_tab,R.id.price_tab, R.id.seat_tab, R.id.company_fl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pre_day:
                Date date = new Date();
                if (date.getTime() >= mCurrentDate.getTime()) {
                    return;
                }
                calendar.add(Calendar.DAY_OF_WEEK, -1);
                setTodayTv();
                mListView.setRefreshing();
                if (date.getTime() == mCurrentDate.getTime()) {
                    preDay.setEnabled(false);
                    preDay.setTextColor(getResources().getColor(R.color.secondary_gray));
                    preDay.setClickable(false);
                } else {
                    preDay.setEnabled(true);
                    preDay.setClickable(true);
                    preDay.setTextColor(getResources().getColor(R.color.color_aaaaaa));
                }
                break;
            case R.id.next_day:
                calendar.add(Calendar.DAY_OF_WEEK, 1);
                setTodayTv();
                mListView.setRefreshing();
                break;
            case R.id.time_tab:
                if (mFlightInfoList == null)
                    return;
                mPriceType = 0;
                if(mTimeType == 0) {
                    Collections.sort(mFlightInfoList, new TimeComparator());
                    timeTab.setText(getText(R.string.time_tab));
                    mTimeType = EARLY_TO_LATE;
                } else if(mTimeType == EARLY_TO_LATE){
                    timeTab.setText(getText(R.string.late_to_early_tab));
                    mTimeType = LATE_TO_EARLY;
                    Collections.reverse(mFlightInfoList);
                } else {
                    mTimeType = EARLY_TO_LATE;
                    timeTab.setText(getText(R.string.time_tab));
                    Collections.reverse(mFlightInfoList);
                }
                mAdapter.setData(mFlightInfoList);
                break;
            case R.id.price_tab:
                if (mFlightInfoList == null)
                    return;
                 mTimeType = 0;
                if(mPriceType == 0) {
                    priceTab.setText(getText(R.string.low_to_high));
                    mPriceType = mLowToHigh;
                    Collections.sort(mFlightInfoList, new PriceComparator());
                } else if(mPriceType == mLowToHigh){
                    priceTab.setText(getText(R.string.high_to_low));
                    mPriceType = mHighToLow;
                    Collections.reverse(mFlightInfoList);
                } else {
                    priceTab.setText(getText(R.string.low_to_high));
                    mPriceType = mLowToHigh;
                    Collections.reverse(mFlightInfoList);
                }
                mAdapter.setData(mFlightInfoList);
                break;
            case R.id.seat_tab:
                showPickupWindow();
                break;
        }
    }

    private Calendar calendar = Calendar.getInstance();

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_flight_list;
    }

    @Override
    public void initView() {
        mPriceType = 0;
        mTimeType = EARLY_TO_LATE;
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
        Bundle bundle = getArguments();
        mDepartureCode = bundle.getString("DepartureCode");
        mArrivalCode = bundle.getString("ArrivalCode");
        mCurrentDateString = bundle.getString("FlightDate");
        mBunkType = bundle.getString("BunkType");
        String[] dates = mCurrentDateString.split("-");
        L.d("@@@@" + mCurrentDateString);
        calendar.set(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]) - 1, Integer.valueOf(dates[2]));

        setTodayTv();
        mEmptyErrorViewController = new EmptyErrorViewController(mEmptyTextView, mErrorTextView, listView, new EmptyErrorViewController.AdapterWrapper(mAdapter));
//        mRadioGroup.setOnCheckedChangeListener(new NestRadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(NestRadioGroup group, int checkedId) {
//                switch (group.getCheckedRadioButtonId()) {
//                    case R.id.time_tab:
//
////                        showIndexTab();
//                        break;
//                    case R.id.price_tab:
//                          isPriceLowToHigh = !isPriceLowToHigh;
////                        showOrderTab();
//                        break;
//                    case R.id.seat_tab:
////                        showCheckTab();
//                        break;
//                    case R.id.company_tab:
////                        showPersonTab();
//                        break;
//                }
//            }
//        });
        mRadioGroup.check(R.id.time_tab);
        mItemList = new ArrayList<>();
        String[] names = getResources().getStringArray(R.array.seatName);
        String[] codes = getResources().getStringArray(R.array.seatCode);
        int len = names.length;
        for (int i=0; i< len; i++) {
            SeatType seatType = new SeatType();
            seatType.mCode = codes[i];
            seatType.mName = names[i];
            mItemList.add(seatType);
        }
    }
    /**
     * 显示选择框
     */
    @SuppressWarnings("unused")
    private void showPickupWindow() {
        // dismissConstructNoticeWindow();
        View contentView;
        PopupWindow popupWindow = mPickUpPopWindow;
        ListView listView;
        if (popupWindow == null) {
            contentView = LayoutInflater.from(getActivity()).inflate(
                    R.layout.choose_c_wei_pop, null);
            listView = (ListView) contentView.findViewById(R.id.listView);
            mSelectorAdapter = new ListPopupAdapter(getActivity());
            listView.setAdapter(mSelectorAdapter);
            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissConstructNoticeWindow();
                }
            });
            popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);
            mPickUpPopWindow = popupWindow;
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setAnimationStyle(R.style.popwin_anim_style);
            mPickUpPopWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);

        } else {
            contentView = popupWindow.getContentView();
            listView = (ListView) contentView.findViewById(R.id.listView);
        }
        //判读window是否显示，消失了就执行动画
        if (!popupWindow.isShowing()) {
            Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.effect_bg_show);
            contentView.startAnimation(animation2);
        }

        setAdapterData();
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        // mPopWindow.showAsDropDown(view, 0, 0);
    }

    /**
     * 为适配器设置数据
     */
    private void setAdapterData() {

    }
    /**
     * 取消工程消息弹出框
     *
     * @return
     */
    private void dismissConstructNoticeWindow() {
        PopupWindow pickUpPopWindow = mPickUpPopWindow;
        if (null != pickUpPopWindow && pickUpPopWindow.isShowing()) {
            pickUpPopWindow.dismiss();
        }
    }

    //=============================================================adapter
    class ListPopupAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Context mContext;

        public ListPopupAdapter(Context context) {
            mContext = context;
            mInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mItemList.size();
        }

        @Override
        public SeatType getItem(int position) {
            return mItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.chuang_wei_item, parent, false);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.typeName.setText(mItemList.get(position).mName);
            vh.typeName.setTag(position);
            if (position == mSeatIndex) {
                vh.selIcon.setVisibility(View.VISIBLE);
                vh.typeName.setTextColor(getResources().getColor(R.color.orange));
            } else {
                vh.typeName.setTextColor(getResources().getColor(R.color.color_222222));
                vh.selIcon.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }


        class ViewHolder {

            @Bind(R.id.type_name)
            TextView typeName;


            @Bind(R.id.sel_icon)
            ImageView selIcon;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSeatIndex = (int) typeName.getTag();
                        dismissConstructNoticeWindow();
                        int len = mFlightInfoList.size();
                        List<FlightInfo> flightInfos = new ArrayList<FlightInfo>();
                        for (int i=0;i<len;i++){
                            FlightInfo flightInfo = mFlightInfoList.get(i);
                            List<FlightInfo.BunksBean> bunksBeen = flightInfo.getBunks();
                            List<FlightInfo.BunksBean> bunksBeenTemp = new ArrayList<FlightInfo.BunksBean>();

                            int size = bunksBeen.size();
                            for (FlightInfo.BunksBean bean :bunksBeen){

                                if(!mItemList.get(mSeatIndex).mCode.equals(bean.getBunkCode())) {
                                    continue;
                                }
                                bunksBeenTemp.add(bean);
                            }
//                            bunksBeen = bunksBeenTemp;
                            flightInfo.setBunks(bunksBeenTemp);
                            if(bunksBeenTemp.size() > 0) {
                                flightInfos.add(flightInfo);
                            }

                        }
                        mFlightInfoList = flightInfos;
                        mAdapter.setData(mFlightInfoList);
                    }
                });
            }
        }
    }
    void setTodayTv() {
        mCurrentDate = calendar.getTime();
        StringBuilder dateTitle = Util.getThreadSafeStringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        dateTitle.append(simpleDateFormat.format(mCurrentDate));
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekDay == Calendar.MONDAY) {
            dateTitle.append(" 周一");
        } else if (weekDay == Calendar.TUESDAY) {
            dateTitle.append(" 周二");
        } else if (weekDay == Calendar.WEDNESDAY) {
            dateTitle.append(" 周三");
        } else if (weekDay == Calendar.THURSDAY) {
            dateTitle.append(" 周四");
        } else if (weekDay == Calendar.FRIDAY) {
            dateTitle.append(" 周五");
        } else if (weekDay == Calendar.SATURDAY) {
            dateTitle.append(" 周六");
        } else if (weekDay == Calendar.SUNDAY) {
            dateTitle.append(" 周日");
        }
        todayTv.setText(dateTitle.toString());
    }

    void doRequest() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mCurrentDateString = dateFormat.format(mCurrentDate);
        GetFlightsRequest request = new GetFlightsRequest();
        request.setDepartureCode(mDepartureCode);
        request.setArrivalCode(mArrivalCode);
        request.setFlightDate(mCurrentDateString);
        request.setBunkType(mBunkType);
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
                                list.mFlightInfoList = JSON.parseArray(jsonObject.getString("Flights"), FlightInfo.class);
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
                                if (!Util.isListEmpty(flightInfoList.mFlightInfoList)) {
                                    mFlightInfoList = flightInfoList.mFlightInfoList;
                                    if(mTimeType == 0 && mPriceType == 0) {
                                        mAdapter.setData(flightInfoList.mFlightInfoList);
                                    } else if(mPriceType == 0){
                                        if(mTimeType == EARLY_TO_LATE) {
//                                            Collections.sort(flightInfoList.mFlightInfoList,new TimeComparator());
//                                            Collections.reverse(flightInfoList.mFlightInfoList);
                                            mAdapter.setData(flightInfoList.mFlightInfoList);
                                        } else {
                                            Collections.sort(flightInfoList.mFlightInfoList,new TimeComparator());
                                            Collections.reverse(flightInfoList.mFlightInfoList);
                                            mAdapter.setData(flightInfoList.mFlightInfoList);
                                        }

                                    } else if(mTimeType == 0) {
                                        if(mPriceType == mLowToHigh) {
                                            Collections.sort(flightInfoList.mFlightInfoList,new PriceComparator());
//                                            Collections.reverse(flightInfoList.mFlightInfoList);
                                            mAdapter.setData(flightInfoList.mFlightInfoList);
                                        } else {
                                            Collections.sort(flightInfoList.mFlightInfoList,new PriceComparator());
                                            Collections.reverse(flightInfoList.mFlightInfoList);
                                            mAdapter.setData(flightInfoList.mFlightInfoList);
                                        }

                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.shortToast(R.string.load_fail);
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
