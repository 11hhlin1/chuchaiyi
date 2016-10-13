package com.ccy.chuchaiyi.index;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.calendar.CalendarSelectorFragment;
import com.ccy.chuchaiyi.city.ChooseCityFragment;
import com.ccy.chuchaiyi.city.CitySort;
import com.ccy.chuchaiyi.flight.FlightInfo;
import com.gjj.applibrary.util.DateUtil;
import com.gjj.applibrary.util.SaveObjUtil;
import com.ccy.chuchaiyi.constant.Constants;
import com.ccy.chuchaiyi.event.EventOfSelCity;
import com.ccy.chuchaiyi.event.EventOfSelDate;
import com.ccy.chuchaiyi.flight.FlightsListFragment;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.util.PreferencesManager;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/8/9.
 */
public class IndexContentFragment extends BaseFragment {
    public int mIndex = 0;
    @Bind(R.id.chufa_city)
    TextView chufaCity;
    @Bind(R.id.chufa_city_ll)
    LinearLayout chufaCityLl;
    @Bind(R.id.exchange)
    ImageView exchange;
    @Bind(R.id.arrive_city)
    TextView arriveCity;
    @Bind(R.id.arrive_city_ll)
    LinearLayout arriveCityLl;
    @Bind(R.id.set_out_date_tv)
    TextView setOutDateTv;
    @Bind(R.id.return_date_tv)
    TextView returnDateTv;
    @Bind(R.id.set_out_date)
    RelativeLayout setOutDate;
    @Bind(R.id.return_date)
    RelativeLayout returnDate;
    @Bind(R.id.seat_tv)
    TextView seatTv;
    @Bind(R.id.seat_wei)
    RelativeLayout seatWei;
    @Bind(R.id.search_btn)
    Button searchBtn;
    /**
     * 弹出框
     */
    private PopupWindow mPickUpPopWindow;
    private ListPopupAdapter mSelectorAdapter;
    private int mSeatIndex = 0;
    private List<SeatType> mItemList;
    private String mSeatCode;
    private String mSelSetOutDate;
    private String mSelReturnDate;
    public  static int mSelMax = 130;
    private CitySort mArriveCity;
    private CitySort mSetOutCity;



    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_index_content;
    }

    @Override
    public void initView() {
        this.mIndex = getArguments().getInt("index");
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

//        mItemList.add("不限机舱");
//        mItemList.add("经济舱");
//        mItemList.add("公务舱/头等舱");
        seatTv.setText(mItemList.get(mSeatIndex).mName);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy#MM#dd");
        mSelSetOutDate = simpleDateFormat1.format(calendar.getTime());
        setOutDateTv.setText(DateUtil.getDateTitleByCalendar(calendar));
        if(mIndex == 0) {
            returnDate.setVisibility(View.GONE);
        } else {
            returnDate.setVisibility(View.VISIBLE);
            calendar.add(Calendar.DATE, 1);
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy#MM#dd");
            mSelReturnDate = simpleDateFormat2.format(calendar.getTime());
            returnDateTv.setText(DateUtil.getDateTitleByCalendar(calendar));
        }
        EventBus.getDefault().register(this);

        mSetOutCity = (CitySort) SaveObjUtil.unSerialize(PreferencesManager.getInstance().get(Constants.SET_OUT_CITY));
        mArriveCity = (CitySort) SaveObjUtil.unSerialize(PreferencesManager.getInstance().get(Constants.ARRIVE_CITY));
        if(mArriveCity != null) {
            arriveCity.setText(mArriveCity.getName());
        } else {
            mArriveCity = new CitySort();
            mArriveCity.setCode("BJS");
            mArriveCity.setName("北京");
            arriveCity.setText(mArriveCity.getName());
        }
        if(mSetOutCity != null) {
            chufaCity.setText(mSetOutCity.getName());
        } else {
            mSetOutCity = new CitySort();
            mSetOutCity.setCode("SZX");
            mSetOutCity.setName("深圳");
            chufaCity.setText(mSetOutCity.getName());
        }
    }


    @OnClick({R.id.chufa_city_ll, R.id.exchange, R.id.arrive_city_ll, R.id.set_out_date,R.id.return_date, R.id.seat_wei, R.id.search_btn})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.chufa_city_ll:
                bundle.putInt("type", ChooseCityFragment.SET_OUT);
                if(mSetOutCity != null)
                    bundle.putString("selCity", mSetOutCity.getName());
                PageSwitcher.switchToTopNavPage(getActivity(), ChooseCityFragment.class, bundle, getString(R.string.choose_set_out_city),null);
                break;
            case R.id.exchange:
                if(mSetOutCity != null && mArriveCity != null) {
                    CitySort temp = mArriveCity;
                    mArriveCity = mSetOutCity;
                    mSetOutCity = temp;
                    arriveCity.setText(mArriveCity.getName());
                    chufaCity.setText(mSetOutCity.getName());
                    PreferencesManager.getInstance().put(Constants.ARRIVE_CITY, SaveObjUtil.serialize(mArriveCity));
                    PreferencesManager.getInstance().put(Constants.SET_OUT_CITY, SaveObjUtil.serialize(mSetOutCity));
                }
                break;
            case R.id.arrive_city_ll:
                bundle.putInt("type", ChooseCityFragment.ARRIVE);
                if(mArriveCity != null)
                    bundle.putString("selCity", mArriveCity.getName());
                PageSwitcher.switchToTopNavPage(getActivity(), ChooseCityFragment.class, bundle, getString(R.string.choose_arrive_city),null);
                break;
            case R.id.set_out_date:
                bundle.putInt(CalendarSelectorFragment.DAYS_OF_SELECT, mSelMax);
                L.d("@@@@@@" + mSelSetOutDate);
//                bundle.putInt("index", mIndex);
                bundle.putInt("dateType", EventOfSelDate.SET_OUT_DATE);
                bundle.putString(CalendarSelectorFragment.ORDER_DAY, mSelSetOutDate);
                PageSwitcher.switchToTopNavPage(getActivity(), CalendarSelectorFragment.class, bundle, getString(R.string.choose_set_out_date),null);
                break;
            case R.id.return_date:
                bundle.putInt(CalendarSelectorFragment.DAYS_OF_SELECT, mSelMax);
//                bundle.putInt("index", mIndex);
                bundle.putInt("dateType", EventOfSelDate.RETURN_DATE);
                L.d("@@@@@@" + mSelReturnDate);
                bundle.putString(CalendarSelectorFragment.ORDER_DAY, mSelReturnDate);
                PageSwitcher.switchToTopNavPage(getActivity(), CalendarSelectorFragment.class, bundle, getString(R.string.choose_return_date),null);
                break;
            case R.id.seat_wei:
                showPickupWindow();
                break;
            case R.id.search_btn:
                if(mSetOutCity == null || mArriveCity == null) {
                    ToastUtil.shortToast(R.string.choose_city);
                    return;
                }
                bundle.putString("DepartureCode", mSetOutCity.getCode());
                bundle.putString("ArrivalCode", mArriveCity.getCode());
                if(mIndex == 0) {
                    bundle.putString("SetOutDate", mSelSetOutDate.replace("#","-"));
                } else {
                    bundle.putString("SetOutDate", mSelSetOutDate.replace("#","-"));
                    if(mSelReturnDate == null) {
                        ToastUtil.shortToast(R.string.choose_time);
                        return;
                    }
                    bundle.putString("ReturnDate", mSelReturnDate.replace("#","-"));
                }
                bundle.putString("BunkType",mItemList.get(mSeatIndex).mCode);
                bundle.putInt("accessFlag", FlightsListFragment.FROM_INDEX);
                PreferencesManager.getInstance().put("SetOutFlightInfo", "");
                PreferencesManager.getInstance().put("SetOutBunksBean", "");
                PreferencesManager.getInstance().put("SetOutWarningInfoBean","");
                StringBuilder title = Util.getThreadSafeStringBuilder();
                title.append(mSetOutCity.getName()).append("-").append(mArriveCity.getName());
                PageSwitcher.switchToTopNavPage(getActivity(), FlightsListFragment.class, bundle, title.toString() ,getString(R.string.policy));

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setDate(EventOfSelDate event) {
        if(getActivity() == null) {
            return;
        }
            if(event.mDateType == EventOfSelDate.SET_OUT_DATE) {
//                String [] dates = event.mDate.split("#");
//                StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
//                stringBuilder.append(dates[1]).append(getString(R.string.month)).append(dates[2]).append(getString(R.string.sunday));
                String date = event.mDate.replace("#", "-");
                setOutDateTv.setText(DateUtil.getDateTitleWithoutEnd(date));
                mSelSetOutDate = event.mDate;
            } else if(event.mDateType == EventOfSelDate.RETURN_DATE){
                if(1 == mIndex) {
//                    String[] dates = event.mDate.split("#");
//                    StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
//                    stringBuilder.append(dates[1]).append(getString(R.string.month)).append(dates[2]).append(getString(R.string.sunday));
//                    returnDateTv.setText(stringBuilder.toString());
                    String date = event.mDate.replace("#", "-");
                    returnDateTv.setText(DateUtil.getDateTitleWithoutEnd(date));
                    mSelReturnDate = event.mDate;
                }
            }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCity(EventOfSelCity event) {
        if(event.mType == ChooseCityFragment.ARRIVE) {
            arriveCity.setText(event.mCity.getName());
            mArriveCity = event.mCity;
            PreferencesManager.getInstance().put(Constants.ARRIVE_CITY, SaveObjUtil.serialize(mArriveCity));
        } else {
            chufaCity.setText(event.mCity.getName());
            mSetOutCity = event.mCity;
            PreferencesManager.getInstance().put(Constants.SET_OUT_CITY, SaveObjUtil.serialize(mSetOutCity));
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
                         seatTv.setText(getItem(mSeatIndex).mName);
                    }
                });
            }
        }
    }
}
