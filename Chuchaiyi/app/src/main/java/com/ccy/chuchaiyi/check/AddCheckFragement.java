package com.ccy.chuchaiyi.check;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.app.BaseApplication;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.calendar.CalendarSelectorFragment;
import com.ccy.chuchaiyi.contact.ApprovalList;
import com.ccy.chuchaiyi.contact.ChoosePassengerFragment;
import com.ccy.chuchaiyi.contact.PassengerInfo;
import com.ccy.chuchaiyi.db.UserInfo;
import com.ccy.chuchaiyi.event.EventOfAddCheck;
import com.ccy.chuchaiyi.event.EventOfSelDate;
import com.ccy.chuchaiyi.event.EventOfSelPerson;
import com.ccy.chuchaiyi.index.IndexContentFragment;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/9/7.
 */
public class AddCheckFragement extends BaseFragment {
    @Bind(R.id.trip_reason)
    EditText tripReason;
    @Bind(R.id.trip_city)
    EditText tripCity;
    @Bind(R.id.choose_start_date_rl)
    RelativeLayout chooseStartDateRl;
    @Bind(R.id.choose_end_date_rl)
    RelativeLayout chooseEndDateRl;
    @Bind(R.id.add_passenger)
    TextView addPassenger;
    @Bind(R.id.start_date_tv)
    TextView startDateTV;
    @Bind(R.id.end_date_tv)
    TextView endDateTV;
    @Bind(R.id.person_ll)
    LinearLayout personLl;
    @Bind(R.id.btn_sure)
    Button btnSure;
    private String mSelSetOutDate;
    private String mSelReturnDate;
    LayoutInflater inflater;
    private List<PassengerInfo> passengerInfoList;
    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_add_check;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        inflater = getActivity().getLayoutInflater();
        passengerInfoList = new ArrayList<>();

    }

    @OnClick({R.id.choose_start_date_rl, R.id.choose_end_date_rl, R.id.add_passenger, R.id.btn_sure})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.choose_start_date_rl:
                bundle.putInt(CalendarSelectorFragment.DAYS_OF_SELECT, IndexContentFragment.mSelMax);
//                L.d("@@@@@@" + mSelSetOutDate);
//                bundle.putInt("index", mIndex);
                bundle.putInt("dateType", EventOfSelDate.START_DATE);
                bundle.putString(CalendarSelectorFragment.ORDER_DAY, mSelSetOutDate);
                PageSwitcher.switchToTopNavPage(getActivity(), CalendarSelectorFragment.class, bundle, getString(R.string.choose_date), null);
                break;
            case R.id.choose_end_date_rl:
                bundle.putInt(CalendarSelectorFragment.DAYS_OF_SELECT, IndexContentFragment.mSelMax);
//                L.d("@@@@@@" + mSelSetOutDate);
//                bundle.putInt("index", mIndex);
                bundle.putInt("dateType", EventOfSelDate.END_DATE);
                bundle.putString(CalendarSelectorFragment.ORDER_DAY, mSelReturnDate);
                PageSwitcher.switchToTopNavPage(getActivity(), CalendarSelectorFragment.class, bundle, getString(R.string.choose_date), null);
                break;
            case R.id.add_passenger:
                bundle.putInt("flag", ChoosePassengerFragment.IS_FROM_CHECK);
                PageSwitcher.switchToTopNavPage(getActivity(), ChoosePassengerFragment.class, bundle, getString(R.string.choose_passenger), null);
                break;
            case R.id.btn_sure:
                createCheck();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setDate(EventOfSelDate event) {
        if (getActivity() == null) {
            return;
        }
        if (event.mDateType == EventOfSelDate.START_DATE) {
            String[] dates = event.mDate.split("#");
            StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
            stringBuilder.append(dates[1]).append(getString(R.string.month)).append(dates[2]).append(getString(R.string.sunday));
            startDateTV.setText(stringBuilder.toString());
            mSelSetOutDate = event.mDate;
        } else if (event.mDateType == EventOfSelDate.END_DATE) {
            String[] dates = event.mDate.split("#");
            StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
            stringBuilder.append(dates[1]).append(getString(R.string.month)).append(dates[2]).append(getString(R.string.sunday));
            endDateTV.setText(stringBuilder.toString());
            mSelReturnDate = event.mDate;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addPerson(EventOfSelPerson event) {
        if (getActivity() == null) {
            return;
        }
        if(!passengerInfoList.contains(event.mPassengerInfo)) {
            ViewHolder holder = inflateConstructingView(inflater);
            holder.passengerName.setText(event.mPassengerInfo.getEmployeeName());
            holder.passengerJob.setText(event.mPassengerInfo.getDepartmentName());
            holder.deletePassenger.setTag(event.mPassengerInfo);
            personLl.addView(holder.parent);
            passengerInfoList.add(event.mPassengerInfo);
        }
    }

    private ViewHolder inflateConstructingView(LayoutInflater inflater) {
        View child = inflater.inflate(R.layout.check_add_person_item, null);
        ViewHolder holder = new ViewHolder(child);
        return holder;
    }

    private void createCheck() {
        CreateCheckReq createCheckReq = new CreateCheckReq();
        UserInfo userInfo = BaseApplication.getUserMgr().getUser();
        createCheckReq.CorpId = userInfo.getCorpId();
        createCheckReq.AskEmployeeId = userInfo.getEmployeeId();
        createCheckReq.TravelReason = tripReason.getText().toString();
        createCheckReq.TravelDateStart =mSelSetOutDate.replace("#","-");
        createCheckReq.TravelDateEnd = mSelReturnDate.replace("#","-");
        createCheckReq.TravelDestination = tripCity.getText().toString();
        List<Integer> ids = new ArrayList<>();
        for (PassengerInfo passengerInfo :passengerInfoList) {
            ids.add(passengerInfo.getEmployeeId());
        }
        createCheckReq.Employees = ids;
        showLoadingDialog(R.string.submitting, false);
        OkHttpUtils.post(ApiConstants.CREATE_APPROVALS)
                .tag(this)
                .postJson(JSON.toJSONString(createCheckReq))
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<String>(String.class) {
                    @Override
                    public void onResponse(boolean isFromCache,  String approvalList, Request request, @Nullable Response response) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                                ToastUtil.shortToast(R.string.success);
                                EventBus.getDefault().post(new EventOfAddCheck());
                                onBackPressed();
                            }
                        });

                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                                ToastUtil.shortToast(R.string.load_fail);
                            }
                        });

                    }
                });
    }
     class ViewHolder {
        @Bind(R.id.delete_passenger)
        ImageView deletePassenger;
        @Bind(R.id.passenger_name)
        TextView passengerName;
        @Bind(R.id.passenger_job)
        TextView passengerJob;
        View parent;

        ViewHolder(View view) {
            parent = view;
            ButterKnife.bind(this, view);
            deletePassenger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PassengerInfo passengerInfo = (PassengerInfo) deletePassenger.getTag();
                    passengerInfoList.remove(passengerInfo);
                    personLl.removeView(parent);
                }
            });
        }
    }
}
