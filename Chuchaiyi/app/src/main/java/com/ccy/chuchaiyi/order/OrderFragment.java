package com.ccy.chuchaiyi.order;

import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.check.AuthorizesAdapter;
import com.ccy.chuchaiyi.check.CheckTypeAdapter;
import com.gjj.applibrary.widget.EmptyErrorViewController;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Chuck on 2016/8/9.
 */
public class OrderFragment extends BaseFragment{
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
    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_order;
    }

    @Override
    public void initView() {

    }
}
