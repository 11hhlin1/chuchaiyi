package com.ccy.chuchaiyi.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.calendar.CalendarSelectorFragment;
import com.ccy.chuchaiyi.event.EventOfSelDate;
import com.ccy.chuchaiyi.flight.FlightsListFragment;
import com.ccy.chuchaiyi.index.IndexContentFragment;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.util.PreferencesManager;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/9/19.
 */
public class ChangeFlightFragment extends BaseFragment {
    @Bind(R.id.flight_city)
    TextView flightCity;
    @Bind(R.id.flight_time)
    TextView flightTime;
    @Bind(R.id.flight_company)
    TextView flightCompany;
    @Bind(R.id.set_out_date_tv)
    TextView setOutDateTv;
    @Bind(R.id.set_out_date)
    RelativeLayout setOutDate;
    @Bind(R.id.search_btn)
    Button searchBtn;
    private OrderInfo.OrdersBean ordersBean;
    private String mSelSetOutDate;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_change_flight;
    }

    @Override
    public void initView() {
        ordersBean = (OrderInfo.OrdersBean) getArguments().getSerializable("order");
        StringBuilder city = Util.getThreadSafeStringBuilder();
        city.append(ordersBean.getDepartureCityName()).append("-").append(ordersBean.getArrivalCityName());
        flightCity.setText(city.toString());
        flightTime.setText(ordersBean.getDepartureDateTime().split(" ")[0]);
        flightCompany.setText(ordersBean.getAirlineName());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy#MM#dd");
        mSelSetOutDate = simpleDateFormat1.format(calendar.getTime());
    }


    @OnClick({R.id.set_out_date, R.id.search_btn})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.set_out_date:
                bundle.putInt(CalendarSelectorFragment.DAYS_OF_SELECT, IndexContentFragment.mSelMax);
                L.d("@@@@@@" + mSelSetOutDate);
//                bundle.putInt("index", mIndex);
                bundle.putInt("dateType", EventOfSelDate.CHANGE_SET_OUT_DATE);
                bundle.putString(CalendarSelectorFragment.ORDER_DAY, mSelSetOutDate);
                PageSwitcher.switchToTopNavPage(getActivity(), CalendarSelectorFragment.class, bundle, getString(R.string.choose_set_out_date),null);
                break;
            case R.id.search_btn:
                bundle.putString("DepartureCode", ordersBean.getDepartureCityCode());
                bundle.putString("ArrivalCode", ordersBean.getArrivalCityCode());
                bundle.putString("SetOutDate", mSelSetOutDate.replace("#","-"));
                bundle.putInt("accessFlag", FlightsListFragment.FROM_CHANGE_FLIGHT);
                bundle.putSerializable("order", ordersBean);
//                bundle.putString("BunkType",mItemList.get(mSeatIndex).mCode);
                StringBuilder title = Util.getThreadSafeStringBuilder();
                title.append(ordersBean.getDepartureCityName()).append("-").append(ordersBean.getArrivalCityName());
                PageSwitcher.switchToTopNavPage(getActivity(), FlightsListFragment.class, bundle, title.toString() ,getString(R.string.policy));

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setDate(EventOfSelDate event) {
        if(getActivity() == null) {
            return;
        }
        if(event.mDateType == EventOfSelDate.CHANGE_SET_OUT_DATE) {
            String [] dates = event.mDate.split("#");
            StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
            stringBuilder.append(dates[1]).append(getString(R.string.month)).append(dates[2]).append(getString(R.string.sunday));
            setOutDateTv.setText(stringBuilder.toString());
            mSelSetOutDate = event.mDate;
        }

    }
}
