package com.ccy.chuchaiyi.order;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.app.BaseApplication;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.contact.Approval;
import com.ccy.chuchaiyi.contact.ChooseCheckNumFragment;
import com.ccy.chuchaiyi.contact.ChoosePassengerFragment;
import com.ccy.chuchaiyi.contact.ChooseProjectFragment;
import com.ccy.chuchaiyi.contact.ProjectInfo;
import com.ccy.chuchaiyi.db.UserInfo;
import com.ccy.chuchaiyi.event.EventOfSelApproval;
import com.ccy.chuchaiyi.event.EventOfSelProject;
import com.ccy.chuchaiyi.event.EventSelPersonFromOrder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/10/8.
 */
public class EditCompanyPassengerFragment extends BaseFragment{
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

    @Bind(R.id.depart_tv)
    TextView departTv;
    @Bind(R.id.depart_value)
    TextView departValue;
    @Bind(R.id.depart_rl)
    RelativeLayout departRl;
    @Bind(R.id.phone_num)
    TextView phoneNum;
    @Bind(R.id.et_phone)
    EditText etPhone;

    private String startTime;
    private String endTime;
    private Approval mApproval;
    private ProjectInfo.ProjectsBean mProjectsBean;

    @OnClick(R.id.choose_check_num)
    void chooseNum() {
        Bundle bundle = new Bundle();
        UserInfo userInfo = BaseApplication.getUserMgr().getUser();
        bundle.putString("EmployeeId", String.valueOf(userInfo.getEmployeeId()));
        bundle.putString("start", startTime);
        PageSwitcher.switchToTopNavPage(getActivity(), ChooseCheckNumFragment.class, bundle, getString(R.string.choose_check_num), null);

    }


    @OnClick(R.id.depart_rl)
    void onChooseDepart() {

    }

    @OnClick(R.id.choose_project_rl)
    void chooseProject() {
        PageSwitcher.switchToTopNavPage(getActivity(), ChooseProjectFragment.class, null, getString(R.string.choose_project), null);
    }

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_edit_company_passenger;
    }

    @Override
    public void initView() {
        UserInfo userInfo = BaseApplication.getUserMgr().getUser();

        if (userInfo.getApprovalRequired()) {
            chooseCheckNum.setVisibility(View.VISIBLE);
        } else {
            chooseCheckNum.setVisibility(View.GONE);
        }

        if (userInfo.getIsProjectRequired()) {
            chooseProjectRl.setVisibility(View.VISIBLE);
        } else {
            chooseProjectRl.setVisibility(View.GONE);
        }

        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSelApproval(EventOfSelApproval event) {
        if (getActivity() == null) {
            return;
        }
        mApproval = event.mApproval;
        numTv.setText(mApproval.getApprovalNo());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSelApproval(EventOfSelProject event) {
        if (getActivity() == null) {
            return;
        }
        mProjectsBean = event.projectsBean;
        projectTv.setText(mProjectsBean.getProjectName());
    }

}
