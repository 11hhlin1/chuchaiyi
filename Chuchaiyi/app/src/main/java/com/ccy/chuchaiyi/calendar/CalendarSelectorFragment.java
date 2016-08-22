package com.ccy.chuchaiyi.calendar;

import android.os.Bundle;
import android.widget.ListView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.event.EventOfSelDate;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

/**
 * @author lvning
 * @version create time:2014-10-29_上午9:56:45
 * @Description 预订日选择
 */
public class CalendarSelectorFragment extends BaseFragment {

    /**
     * 可选天数
     */
    public static final String DAYS_OF_SELECT = "days_of_select";
    /**
     * 上次预订日
     */
    public static final String ORDER_DAY = "order_day";
    @Bind(R.id.lv_calendar)
    ListView lvCalendar;

    private int daysOfSelect;
    private String orderDay;
//    private int mIndex;
    private int mDateType;
    private ListView listView;

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.calendar_selector);
//		daysOfSelect = getIntent().getIntExtra(DAYS_OF_SELECT, 30);
//		orderDay = getIntent().getStringExtra(ORDER_DAY);
//		listView = (ListView) findViewById(R.id.lv_calendar);
//
//		CalendarListAdapter adapter = new CalendarListAdapter(this, daysOfSelect, orderDay);
//		listView.setAdapter(adapter);
//
//		adapter.setOnCalendarOrderListener(new OnCalendarOrderListener() {
//
//			@Override
//			public void onOrder(String orderInfo) {
//				Intent result = new Intent();
//				result.putExtra(ORDER_DAY, orderInfo);
//				setResult(RESULT_OK, result);
//				finish();
//			}
//		});
//	}

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_calendar_selector;
    }

    @Override
    public void initView() {

        Bundle bundle = getArguments();
        daysOfSelect = bundle.getInt(DAYS_OF_SELECT);
        orderDay = bundle.getString(ORDER_DAY);
//        mIndex = bundle.getInt("index");
        mDateType = bundle.getInt("dateType");
        CalendarListAdapter adapter = new CalendarListAdapter(getActivity(), daysOfSelect, orderDay);
        lvCalendar.setAdapter(adapter);


        adapter.setOnCalendarOrderListener(new CalendarListAdapter.OnCalendarOrderListener() {

            @Override
            public void onOrder(String orderInfo) {
                EventOfSelDate eventofSelDate = new EventOfSelDate();
                eventofSelDate.mDate = orderInfo;
                eventofSelDate.mDateType = mDateType;
//                eventofSelDate.mPage = mIndex;
                EventBus.getDefault().post(eventofSelDate);
                onBackPressed();
            }
        });
    }

}
