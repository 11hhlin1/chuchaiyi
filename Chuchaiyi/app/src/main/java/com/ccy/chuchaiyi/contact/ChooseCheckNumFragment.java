package com.ccy.chuchaiyi.contact;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.RecyclerItemOnclickListener;
import com.ccy.chuchaiyi.event.EventOfSelApproval;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/9/2.
 */
public class ChooseCheckNumFragment extends BaseFragment implements RecyclerItemOnclickListener{
    @Bind(R.id.ptr_recycler_view)
    PullToRefreshRecyclerView mRecyclerView;
    private ChooseCheckNumAdapter mAdapter;
    private String mEmployeeId;
    private String startTime;
    private String endTime;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_ptr_common;
    }

    @Override
    public void initView() {

        final PullToRefreshRecyclerView recyclerView = mRecyclerView;
        recyclerView.setMode(PullToRefreshBase.Mode.DISABLED);

        Context context = getActivity();
        recyclerView.getRefreshableView().setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new ChooseCheckNumAdapter(context, new ArrayList<Approval>());
        ChooseCheckNumAdapter adapter = mAdapter;
        recyclerView.getRefreshableView().setAdapter(adapter);
        Bundle bundle = getArguments();
        mEmployeeId =bundle.getString("EmployeeId");
        startTime = bundle.getString("start");
        String end = bundle.getString("end");
        if(!TextUtils.isEmpty(end)) {
            endTime = end.split(" ")[0];
        }
        mAdapter.setmItemOnclickListener(this);
        doRequest();
    }

    private void doRequest() {
        showLoadingDialog(R.string.submitting, false);
        OkHttpUtils.get(ApiConstants.GET_APPROVALS)
                .tag(this)
                .params("travelEmployeeId", mEmployeeId)
                .params("start",startTime.split(" ")[0])
                .params("end",endTime)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ApprovalList>(ApprovalList.class) {
                    @Override
                    public void onResponse(boolean isFromCache, final ApprovalList approvalList, Request request, @Nullable Response response) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                                if(approvalList != null && !Util.isListEmpty(approvalList.Approvals)) {
                                    mAdapter.setData(approvalList.Approvals);
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
                                dismissLoadingDialog();
                                ToastUtil.shortToast(R.string.load_fail);
                            }
                        });

                    }
                });
    }

    @Override
    public void onItemClick(View view, int position) {
        onBackPressed();
        EventOfSelApproval selApproval = new EventOfSelApproval();
        selApproval.mApproval = mAdapter.getData(position);
        EventBus.getDefault().post(selApproval);
    }
}
