package com.ccy.chuchaiyi.contact;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.RecyclerItemOnclickListener;
import com.ccy.chuchaiyi.event.EventOfSelApproval;
import com.ccy.chuchaiyi.event.EventOfSelProject;
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

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/9/9.
 */
public class ChooseProjectFragment extends BaseFragment implements RecyclerItemOnclickListener{
    @Bind(R.id.ptr_recycler_view)
    PullToRefreshRecyclerView mRecyclerView;
    private ChooseProjectAdapter mAdapter;
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
        mAdapter = new ChooseProjectAdapter(context, new ArrayList<ProjectInfo.ProjectsBean>());
        ChooseProjectAdapter adapter = mAdapter;
        recyclerView.getRefreshableView().setAdapter(adapter);
        mAdapter.setmItemOnclickListener(this);
        doRequest();
    }
    private void doRequest() {
        showLoadingDialog(R.string.submitting, false);
        OkHttpUtils.get(ApiConstants.GET_PROJECT)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ProjectInfo>(ProjectInfo.class) {
                    @Override
                    public void onResponse(boolean isFromCache, final ProjectInfo approvalList, Request request, @Nullable Response response) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                                if(approvalList != null && !Util.isListEmpty(approvalList.getProjects())) {
                                    mAdapter.setData(approvalList.getProjects());
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
        EventOfSelProject selProject = new EventOfSelProject();
        selProject.projectsBean = mAdapter.getData(position);
        EventBus.getDefault().post(selProject);
    }
}
