package com.ccy.chuchaiyi.contact;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.order.EditPassengerFragment;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import java.util.ArrayList;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/9/2.
 */
public class ChooseCheckNumFragment extends BaseFragment{
    @Bind(R.id.ptr_recycler_view)
    PullToRefreshRecyclerView mRecyclerView;
    ChooseCheckNumAdapter mAdapter;
    private PassengerInfo mPassengerInfo;

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
        mPassengerInfo = (PassengerInfo) bundle.getSerializable("passenger");
    }

    private void doRequest() {
        showLoadingDialog(R.string.submitting, false);
        OkHttpUtils.get(ApiConstants.GET_APPROVALS)
                .tag(this)
                .params("travelEmployeeId", String.valueOf(mPassengerInfo.getEmployeeId()))
                .params("start","")
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
}
