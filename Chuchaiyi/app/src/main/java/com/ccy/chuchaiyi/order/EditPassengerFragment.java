package com.ccy.chuchaiyi.order;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
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
import com.gjj.applibrary.event.EventOfTokenError;
import com.gjj.applibrary.http.callback.CommonCallback;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.util.ToastUtil;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

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
    private Approval mApproval;
    private ProjectInfo.ProjectsBean mProjectsBean;
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

        EventOfSelPassenger eventOfSelPassenger = new EventOfSelPassenger();
        eventOfSelPassenger.mPassenger = mPassengerInfo;
        if(mApproval == null) {
            ToastUtil.shortToast(R.string.choose_check_num);
            return;
        }
        if(mProjectsBean == null) {
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
        bundle.putString(TopNavSubActivity.PARAM_TOP_TITLE, getArguments().getString("title"));
        bundle.putString(TopNavSubActivity.PARAM_TOP_RIGHT, getString(R.string.reason_private));
        PageSwitcher.goBackTopNavPage(activity, EditOrderFragment.class,bundle, false);

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
        if(TextUtils.isEmpty(mPassengerInfo.getDepartmentName())) {
            getEmployeeInfo();
        } else {
            passengerValue.setText(mPassengerInfo.getEmployeeName());
            cardTypeValue.setText(mPassengerInfo.getDefaultCertType());
            etCard.setText(mPassengerInfo.getDefaultCertNo());
        }

        EventBus.getDefault().register(this);

    }

    private void getEmployeeInfo() {
        OkHttpUtils.get(ApiConstants.GET_EMPLOYEE)
                .tag(this)
                .params("employeeId", String.valueOf(mPassengerInfo.getEmployeeId()))
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new CommonCallback<PassengerInfo>() {
                    @Override
                    public PassengerInfo parseNetworkResponse(Response response) throws Exception {
                        String responseData = response.body().string();
                        if (TextUtils.isEmpty(responseData)) return null;

                        /**
                         * 一般来说，服务器返回的响应码都包含 code，msg，data 三部分，在此根据自己的业务需要完成相应的逻辑判断
                         * 以下只是一个示例，具体业务具体实现
                         */
                        JSONObject jsonObject = new JSONObject(responseData);
                        final String msg = jsonObject.optString("Message", "");
                        final int code = jsonObject.optInt("Code", -1);
                        String data = jsonObject.optString("Employee");
                        switch (code) {
                            case 0:
                                PassengerInfo object = JSON.parseObject(data, PassengerInfo.class);
                                L.d("@@@@", object);
                                return object;
                            case 401:
                                //比如：用户授权信息无效，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                                EventBus.getDefault().post(new EventOfTokenError());
                                throw new IllegalStateException("用户授权信息无效");
                            case 105:
                                //比如：用户收取信息已过期，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                                throw new IllegalStateException("用户收取信息已过期");
                            case 106:
                                //比如：用户账户被禁用，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                                throw new IllegalStateException("用户账户被禁用");
                            case 300:
                                //比如：其他乱七八糟的等，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                                throw new IllegalStateException("其他乱七八糟的等");
                            default:
                                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + msg);
                        }
                    }

                    @Override
                    public void onResponse(boolean isFromCache, PassengerInfo passengerInfo, Request request, @Nullable Response response) {
//                                        dismissLoadingDialog();
//                        mCallBack.selPerson(passengerInfo);
                          mPassengerInfo = passengerInfo;
                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  passengerValue.setText(mPassengerInfo.getEmployeeName());
                                  cardTypeValue.setText(mPassengerInfo.getDefaultCertType());
                                  etCard.setText(mPassengerInfo.getDefaultCertNo());
                              }
                          });
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
//                                        dismissLoadingDialog();
                        ToastUtil.shortToast(R.string.load_fail);
                    }
                });
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
