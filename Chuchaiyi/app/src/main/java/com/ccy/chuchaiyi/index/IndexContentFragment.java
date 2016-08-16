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
import com.ccy.chuchaiyi.city.SaveObjUtil;
import com.ccy.chuchaiyi.event.EventOfSelCity;
import com.ccy.chuchaiyi.event.EventOfSelDate;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.util.PreferencesManager;
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
    @Bind(R.id.set_out_date)
    RelativeLayout setOutDate;
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
    private List<String> mItemList;
    private String mSelDate;
    private int mSelMax = 30;
    private CitySort mArriveCity;
    private CitySort mSetOutCity;


    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_index_content;
    }

    @Override
    public void initView() {
        mItemList = new ArrayList<>();
        mItemList.add( "不限机舱");
        mItemList.add("经济舱");
        mItemList.add("公务舱/头等舱");
        seatTv.setText(mItemList.get(mSeatIndex));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M月d日");
        setOutDateTv.setText(simpleDateFormat.format(calendar.getTime()));

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy#MM#dd");
        mSelDate = simpleDateFormat1.format(calendar.getTime());
        EventBus.getDefault().register(this);

        mSetOutCity = SaveObjUtil.unSerialize(PreferencesManager.getInstance().get(SaveObjUtil.SET_OUT_CITY));
        mArriveCity = SaveObjUtil.unSerialize(PreferencesManager.getInstance().get(SaveObjUtil.ARRIVE_CITY));
        if(mArriveCity != null) {
            arriveCity.setText(mArriveCity.getName());
        }
        if(mSetOutCity != null) {
            chufaCity.setText(mSetOutCity.getName());
        }
    }


    @OnClick({R.id.chufa_city_ll, R.id.exchange, R.id.arrive_city_ll, R.id.set_out_date, R.id.seat_wei, R.id.search_btn})
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
                    PreferencesManager.getInstance().put(SaveObjUtil.ARRIVE_CITY, SaveObjUtil.serialize(mArriveCity));
                    PreferencesManager.getInstance().put(SaveObjUtil.SET_OUT_CITY, SaveObjUtil.serialize(mSetOutCity));
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
                L.d("@@@@@@" + mSelDate);
                bundle.putString(CalendarSelectorFragment.ORDER_DAY, mSelDate);
                PageSwitcher.switchToTopNavPage(getActivity(), CalendarSelectorFragment.class, bundle, getString(R.string.choose_set_out_date),null);
                break;
            case R.id.seat_wei:
                showPickupWindow();
                break;
            case R.id.search_btn:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setDate(EventOfSelDate event) {
        String [] dates = event.mDate.split("#");
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(dates[1]).append(getString(R.string.month)).append(dates[2]).append(getString(R.string.sunday));
        setOutDateTv.setText(stringBuilder.toString());
        mSelDate = event.mDate;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCity(EventOfSelCity event) {
        if(event.mType == ChooseCityFragment.ARRIVE) {
            arriveCity.setText(event.mCity.getName());
            mArriveCity = event.mCity;
            PreferencesManager.getInstance().put(SaveObjUtil.ARRIVE_CITY, SaveObjUtil.serialize(mArriveCity));
        } else {
            chufaCity.setText(event.mCity.getName());
            mSetOutCity = event.mCity;
            PreferencesManager.getInstance().put(SaveObjUtil.SET_OUT_CITY, SaveObjUtil.serialize(mSetOutCity));
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
        public String getItem(int position) {
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
            vh.typeName.setText(mItemList.get(position));
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
                        seatTv.setText(mItemList.get(mSeatIndex));
                    }
                });
            }
        }
    }
}
