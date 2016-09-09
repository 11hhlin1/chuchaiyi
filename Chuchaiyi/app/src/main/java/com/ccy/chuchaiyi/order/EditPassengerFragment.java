package com.ccy.chuchaiyi.order;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.base.TopNavSubActivity;
import com.ccy.chuchaiyi.check.CategoryData;
import com.ccy.chuchaiyi.contact.Approval;
import com.ccy.chuchaiyi.contact.ChooseCheckNumFragment;
import com.ccy.chuchaiyi.contact.ChooseProjectFragment;
import com.ccy.chuchaiyi.contact.PassengerInfo;
import com.ccy.chuchaiyi.contact.ProjectInfo;
import com.ccy.chuchaiyi.event.EventOfCancelApproval;
import com.ccy.chuchaiyi.event.EventOfSelApproval;
import com.ccy.chuchaiyi.event.EventOfSelPassenger;
import com.ccy.chuchaiyi.event.EventOfSelProject;
import com.ccy.chuchaiyi.net.ApiConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/21.
 */
public class EditPassengerFragment extends BaseFragment {
    @Bind(R.id.passenger_tv)
    TextView passengerTv;
    @Bind(R.id.passenger_value)
    TextView passengerValue;
    @Bind(R.id.card_type_tv)
    TextView cardTypeTv;
    @Bind(R.id.card_type_value)
    TextView cardTypeValue;
    @Bind(R.id.card_num)
    TextView cardNum;
    @Bind(R.id.check_num_tv)
    TextView numTv;
    @Bind(R.id.check_project_tv)
    TextView projectTv;
    @Bind(R.id.et_card)
    EditText etCard;
    @Bind(R.id.choose_check_num)
    RelativeLayout chooseCheckNum;
    @Bind(R.id.choose_project_rl)
    RelativeLayout chooseProjectRl;

    private String startTime;
    private String endTime;
    Approval mApproval;
    ProjectInfo.ProjectsBean mProjectsBean;
    @OnClick(R.id.choose_check_num)
    void chooseNum() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("passenger", mPassengerInfo);
        bundle.putString("start", startTime);
        PageSwitcher.switchToTopNavPage(getActivity(), ChooseCheckNumFragment.class, bundle, getString(R.string.choose_check_num),null);

    }

    @OnClick(R.id.choose_project_rl)
    void chooseProject() {
        PageSwitcher.switchToTopNavPage(getActivity(), ChooseProjectFragment.class, null, getString(R.string.choose_project),null);
    }

    @Override
    public void onRightBtnClick() {
        super.onRightBtnClick();
        TopNavSubActivity activity = (TopNavSubActivity) getActivity();
        PageSwitcher.goBackTopNavPage(activity, EditOrderFragment.class, false);

        EventOfSelPassenger eventOfSelPassenger = new EventOfSelPassenger();
        eventOfSelPassenger.mPassenger = mPassengerInfo;
        eventOfSelPassenger.ApprovalId = mApproval.getApprovalId();
        eventOfSelPassenger.ProjectId = mProjectsBean.getProjectId();
        eventOfSelPassenger.ProjectName = mProjectsBean.getProjectName();
        EventBus.getDefault().post(eventOfSelPassenger);
    }

    private PassengerInfo mPassengerInfo;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_edit_passenger;
    }

    @Override
    public void initView() {

        Bundle bundle = getArguments();
        mPassengerInfo = (PassengerInfo) bundle.getSerializable("passenger");
        startTime = bundle.getString("start");
        endTime = bundle.getString("end");
        assert mPassengerInfo != null;
        passengerValue.setText(mPassengerInfo.getEmployeeName());
        cardTypeValue.setText(mPassengerInfo.getDefaultCertType());
        etCard.setText(mPassengerInfo.getDefaultCertNo());
        EventBus.getDefault().register(this);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSelApproval(EventOfSelApproval event) {
        if(getActivity() == null) {
            return;
        }
        mApproval = event.mApproval;
        numTv.setText(mApproval.getApprovalNo());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSelApproval(EventOfSelProject event) {
        if(getActivity() == null) {
            return;
        }
        mProjectsBean = event.projectsBean;
        projectTv.setText(mProjectsBean.getProjectName());
    }
}
