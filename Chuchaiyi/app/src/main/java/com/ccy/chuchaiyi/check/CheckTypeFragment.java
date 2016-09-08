package com.ccy.chuchaiyi.check;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.SpaceItemDecoration;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.widget.EmptyErrorViewController;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.internal.PrepareRelativeLayout;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/8/16.
 */
public class CheckTypeFragment extends BaseFragment{
    @Bind(R.id.ptr_recycler_view)
    PullToRefreshRecyclerView mRecyclerView;
    /**
     * 空提示
     */
    @Bind(R.id.empty_tv)
    TextView mEmptyTextView;
    /**
     * 错误提示
     */
    @Bind(R.id.error_tv)
    TextView mErrorTextView;

    private CheckTypeAdapter mAdapter;

    private static final String PULL_FLAG = "isUpPullRefresh";

    public final static int NUM = 20;

    /**
     * 空状态控制器
     */
    private EmptyErrorViewController mEmptyErrorViewController;

    /**
     * 重新加载
     */
    @OnClick(R.id.error_tv)
    void errorReload() {
        mEmptyErrorViewController.onLoading();
        mRecyclerView.setTag(R.id.error_tv, true);
        mRecyclerView.setRefreshing();
    }

    /**
     * 重新加载
     */
    @OnClick(R.id.empty_tv)
    void emptyReload() {
        mEmptyErrorViewController.onLoading();
        mRecyclerView.setTag(R.id.error_tv, false);
        mRecyclerView.setRefreshing();
    }
    private int mMyApprovalPage = 1;
    private CategoryData categoryData;
    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_ptr_common;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        categoryData = bundle.getParcelable("data");

        final PullToRefreshRecyclerView recyclerView = mRecyclerView;
        recyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        recyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mRecyclerView.hasMore(true);
                Object tag = mRecyclerView.getTag(R.id.error_tv);
                mMyApprovalPage = 1;
                if (tag == null) {
                    doRequest(1);
                    return;
                }
                doRequest(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                doRequest(mMyApprovalPage);
            }
        });

        Context context = getActivity();
        recyclerView.getRefreshableView().setLayoutManager(new LinearLayoutManager(context));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_30p);
        mRecyclerView.getRefreshableView().addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        mAdapter = new CheckTypeAdapter(context, new ArrayList<Approval.ApprovalsBean>(),categoryData.mCateId);
        CheckTypeAdapter adapter = mAdapter;
        recyclerView.getRefreshableView().setAdapter(adapter);
        mEmptyErrorViewController = new EmptyErrorViewController(mEmptyTextView, mErrorTextView, recyclerView,
                new EmptyErrorViewController.AdapterWrapper(adapter));
        recyclerView.setRefreshPrepareLayoutListener(new PrepareRelativeLayout.RefreshPrepareLayoutListener() {
            @Override
            public void onPrepareLayout() {
                recyclerView.setRefreshing();
            }
        });
        setEmptyTextView();
    }

    private void setEmptyTextView() {
        mEmptyTextView.setText(getString(R.string.empty_no_data));
    }

    public void doRequest(int page) {
        switch (categoryData.mCateId) {
            case CategoryData.MY_APPLY: {
                getMyCheck(ApiConstants.GET_MY_APPROVALS,page);
                break;
            }
            case CategoryData.MY_UN_CHECK: {
                getMyCheck(ApiConstants.GET_AUDIT_FOR_ME_APPROVALS,page);
                break;
            }
            case CategoryData.MY_CHECKED: {
                getMyCheck(ApiConstants.GET_AUDITED_APPROVALS,page);
                break;
            }
        }

    }

    private void getMyCheck(String url, final int page) {
        OkHttpUtils.get(url)
                .tag(this)
                .params("pageNumber", String.valueOf(page))
                .params("pageSize", String.valueOf(NUM))
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<Approval>(Approval.class) {
                    @Override
                    public void onResponse(boolean isFromCache, final Approval approvalList, Request request, @Nullable Response response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(page == 1) {
                                    mAdapter.setData(approvalList.getApprovals());
                                } else {
                                    mAdapter.addData(approvalList.getApprovals());
                                }
                                mMyApprovalPage++;
                                mEmptyErrorViewController.onRequestFinish(mAdapter.getItemCount() > 0);
                            }
                        });

                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.shortToast(R.string.load_fail);
                            }
                        });

                    }

                    @Override
                    public void onAfter(boolean isFromCache, @Nullable final Approval approval, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onAfter(isFromCache, approval, call, response, e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (approval != null) {
                                    mRecyclerView.hasMore(approval.getApprovals().size() == NUM);
                                } else {
                                    mRecyclerView.hasMore(false);
                                }
                                mRecyclerView.onRefreshComplete();
                            }
                        });
                    }
                });
    }

}
