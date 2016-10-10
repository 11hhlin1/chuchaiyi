package com.ccy.chuchaiyi.order;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.app.BaseApplication;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.base.TopNavSubActivity;
import com.ccy.chuchaiyi.constant.Constants;
import com.ccy.chuchaiyi.contact.Approval;
import com.ccy.chuchaiyi.contact.ChooseCheckNumFragment;
import com.ccy.chuchaiyi.contact.ChoosePassengerFragment;
import com.ccy.chuchaiyi.contact.ChooseProjectFragment;
import com.ccy.chuchaiyi.contact.PassengerInfo;
import com.ccy.chuchaiyi.contact.ProjectInfo;
import com.ccy.chuchaiyi.db.UserInfo;
import com.ccy.chuchaiyi.event.EventOfChooseDepart;
import com.ccy.chuchaiyi.event.EventOfSelApproval;
import com.ccy.chuchaiyi.event.EventOfSelPassenger;
import com.ccy.chuchaiyi.event.EventOfSelProject;
import com.ccy.chuchaiyi.event.EventSelPersonFromOrder;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.PreferencesManager;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/10/8.
 */
public class EditCompanyPassengerFragment extends BaseFragment{
    @Bind(R.id.passenger_tv)
    TextView passengerTv;
    @Bind(R.id.passenger_value)
    EditText passengerValue;
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

    private Approval mApproval;
    private ProjectInfo.ProjectsBean mProjectsBean;
    private PopupWindow mPickUpPopWindow;
    private ListPopupAdapter mSelectorAdapter;
    private List<String> mCertTypeList;
    private int mCertTypeIndex;
    @OnClick(R.id.choose_check_num)
    void chooseNum() {
        Bundle bundle = new Bundle();
        UserInfo userInfo = BaseApplication.getUserMgr().getUser();
        bundle.putString("EmployeeId", String.valueOf(userInfo.getEmployeeId()));
        bundle.putString("start", getArguments().getString("start"));
        bundle.putString("end", getArguments().getString("end"));
        PageSwitcher.switchToTopNavPage(getActivity(), ChooseCheckNumFragment.class, bundle, getString(R.string.choose_check_num), null);
    }

    @OnClick(R.id.choose_card_type_rl)
    void chooseCardType() {
        if(Util.isListEmpty(mCertTypeList)) {
            return;
        }
        showPickupWindow();
    }

    @OnClick(R.id.depart_rl)
    void onChooseDepart() {
        PageSwitcher.switchToTopNavPage(getActivity(), ChooseDepartmentFragment.class, null, getString(R.string.choose_department), null);
    }

    @OnClick(R.id.choose_project_rl)
    void chooseProject() {
        PageSwitcher.switchToTopNavPage(getActivity(), ChooseProjectFragment.class, null, getString(R.string.choose_project), null);
    }
    @Override
    public void onRightBtnClick() {
        super.onRightBtnClick();

        if(!Util.isMobileNO(etPhone.getText().toString())) {
            ToastUtil.shortToast(R.string.hint_check_login_phone);
            return;
        }
        if(TextUtils.isEmpty(cardTypeValue.getText().toString())) {
            ToastUtil.shortToast(R.string.choose_certType);
            return;
        }
        mPassengerInfo.setDefaultCertNo(etCard.getText().toString());
        mPassengerInfo.setDefaultCertType(cardTypeValue.getText().toString());
        mPassengerInfo.setMobile(etPhone.getText().toString());
        mPassengerInfo.setEmployeeName(passengerValue.getText().toString());
        EventOfSelPassenger eventOfSelPassenger = new EventOfSelPassenger();
        eventOfSelPassenger.mPassenger = mPassengerInfo;
        if (mApproval == null) {
            ToastUtil.shortToast(R.string.choose_check_num);
            return;
        }
        if (mProjectsBean == null) {
            ToastUtil.shortToast(R.string.choose_project);
            return;
        }
        eventOfSelPassenger.ApprovalId = mApproval.getApprovalId();
        eventOfSelPassenger.ProjectId = mProjectsBean.getProjectId();
        eventOfSelPassenger.ProjectName = mProjectsBean.getProjectName();
        eventOfSelPassenger.ApprovalNo = mApproval.getApprovalNo();
        EventBus.getDefault().post(eventOfSelPassenger);

        TopNavSubActivity activity = (TopNavSubActivity) getActivity();
        Bundle bundle = new Bundle();
        String title = PreferencesManager.getInstance().get(Constants.EDIT_ORDER_TITLE);
        bundle.putString(TopNavSubActivity.PARAM_TOP_TITLE, title);
        bundle.putString(TopNavSubActivity.PARAM_TOP_RIGHT, getString(R.string.reason_private));
        PageSwitcher.goBackTopNavPage(activity, EditOrderFragment.class, bundle, false);

    }
    private PassengerInfo mPassengerInfo;

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
        Bundle bundle = getArguments();
        EventOfSelPassenger eventOfSelPassenger = (EventOfSelPassenger) bundle.getSerializable("passenger");
        if(eventOfSelPassenger == null) {
            mPassengerInfo = new PassengerInfo();
        } else {
            mPassengerInfo = eventOfSelPassenger.mPassenger;
            if(mApproval == null) {
                mApproval = new Approval();
                mApproval.setApprovalId(eventOfSelPassenger.ApprovalId);
                mApproval.setApprovalNo(eventOfSelPassenger.ApprovalNo);
            }
            if(mProjectsBean == null) {
                mProjectsBean = new ProjectInfo.ProjectsBean();
                mProjectsBean.setProjectId(eventOfSelPassenger.ProjectId);
                mProjectsBean.setProjectName(eventOfSelPassenger.ProjectName);
            }
            numTv.setText(mApproval.getApprovalNo());
            projectTv.setText(mProjectsBean.getProjectName());
            setPassengerInfo();
        }
        getCardType();
        if (userInfo.getIsProjectRequired()) {
            chooseProjectRl.setVisibility(View.VISIBLE);
        } else {
            chooseProjectRl.setVisibility(View.GONE);
        }

        EventBus.getDefault().register(this);

    }
    private void getCardType() {
        OkHttpUtils.get(ApiConstants.GET_CERT_TYPE)
                .tag(this)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new JsonCallback<CertTypes>(CertTypes.class) {

                    @Override
                    public void onResponse(boolean b, CertTypes certTypes, Request request, @Nullable Response response) {
                        mCertTypeList = certTypes.CertTypes;
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        ToastUtil.shortToast(R.string.load_fail);
                    }
                });
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSelDepart(EventOfChooseDepart event) {
        if (getActivity() == null) {
            return;
        }
        mPassengerInfo.setDepartmentId(event.departmentBean.getId());
        mPassengerInfo.setDepartmentName(event.departmentBean.getName());
        departValue.setText(event.departmentBean.getName());
    }

    private void setPassengerInfo() {
        passengerValue.setText(mPassengerInfo.getEmployeeName());
        cardTypeValue.setText(mPassengerInfo.getDefaultCertType());
        etCard.setText(mPassengerInfo.getDefaultCertNo());
        etPhone.setText(mPassengerInfo.getMobile());
        departValue.setText(mPassengerInfo.getDepartmentName());
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

        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        // mPopWindow.showAsDropDown(view, 0, 0);
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
            return mCertTypeList.size();
        }

        @Override
        public String getItem(int position) {
            return mCertTypeList.get(position);
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
            vh.typeName.setText(mCertTypeList.get(position));
            vh.typeName.setTag(position);
            if (position == mCertTypeIndex) {
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
                        mCertTypeIndex = (int) typeName.getTag();
                        dismissConstructNoticeWindow();
                        cardTypeValue.setText(getItem(mCertTypeIndex));
                        mPassengerInfo.setDefaultCertType(getItem(mCertTypeIndex));
                    }
                });
            }
        }
    }
}
