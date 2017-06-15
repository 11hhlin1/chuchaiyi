package com.ccy.chuchaiyi.order;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.base.SpaceItemDecoration;
import com.ccy.chuchaiyi.check.Approval;
import com.ccy.chuchaiyi.check.Authorizes;
import com.ccy.chuchaiyi.check.AuthorizesAdapter;
import com.ccy.chuchaiyi.check.CategoryData;
import com.ccy.chuchaiyi.check.CheckTypeAdapter;
import com.ccy.chuchaiyi.city.PinyinComparator;
import com.ccy.chuchaiyi.event.EventOfCancelApproval;
import com.ccy.chuchaiyi.event.EventOfDumpOrderDetail;
import com.ccy.chuchaiyi.event.EventOfRefreshOrderList;
import com.ccy.chuchaiyi.main.MainActivity;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.widget.EditTextWithDel;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.widget.EmptyErrorViewController;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.internal.PrepareRelativeLayout;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/8/9.
 */
public class OrderFragment extends BaseFragment{
    private static final int NUM = 20;
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

    @Bind(R.id.et_search)
    EditTextWithDel etSearch;

    private OrderListAdapter mAdapter;

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
    private int mPage = 1;
    @Override
    public void initView() {
        final PullToRefreshRecyclerView recyclerView = mRecyclerView;
        recyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        recyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mRecyclerView.hasMore(true);
                Object tag = mRecyclerView.getTag(R.id.error_tv);
                mPage = 1;
                doRequest(mPage,etSearch.getText().toString());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                doRequest(mPage,etSearch.getText().toString());
            }
        });

        Context context = getActivity();
        recyclerView.getRefreshableView().setLayoutManager(new LinearLayoutManager(context));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_30p);
        mRecyclerView.getRefreshableView().addItemDecoration(new SpaceItemDecoration(spacingInPixels));

            mAdapter = new OrderListAdapter(context, new ArrayList<OrderInfo.OrdersBean>());
        OrderListAdapter adapter = mAdapter;
            recyclerView.getRefreshableView().setAdapter(adapter);
            mEmptyErrorViewController = new EmptyErrorViewController(mEmptyTextView, mErrorTextView, recyclerView,
                    new EmptyErrorViewController.AdapterWrapper(adapter));

        recyclerView.setRefreshPrepareLayoutListener(new PrepareRelativeLayout.RefreshPrepareLayoutListener() {
            @Override
            public void onPrepareLayout() {
                recyclerView.setRefreshing();
            }
        });
        //根据输入框输入值的改变来过滤搜索
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                doRequest(1,etSearch.getText().toString());
            }
        });
        setEmptyTextView();
        EventBus.getDefault().register(this);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshList(EventOfRefreshOrderList event) {
        if(getActivity() == null) {
            return;
        }
        doRequest(1,etSearch.getText().toString());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dumpDetail(EventOfDumpOrderDetail event) {
        if(getActivity() == null) {
            return;
        }
        doRequest(1,etSearch.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putInt("orderId", event.orderId);
        PageSwitcher.switchToTopNavPage(getActivity(), OrderDetailFragment.class, bundle, "订单详情", null);
    }

    private void setEmptyTextView() {
        mEmptyTextView.setText(getString(R.string.empty_no_check));
    }

    public void doRequest(final int page, String passengerName) {
        OkHttpUtils.get(ApiConstants.GET_FLIGHT_ORDER)
                .tag(this)
                .params("passengerName", passengerName)
                .params("pageNumber", String.valueOf(page))
                .params("pageSize", String.valueOf(NUM))
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<OrderInfo>(OrderInfo.class) {

                    @Override
                    public void onResponse(boolean b, final OrderInfo orderInfo, Request request, @Nullable Response response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(orderInfo == null)
                                    return;
                                if(page == 1) {
                                    mAdapter.setData(orderInfo.getOrders());
                                } else {
                                    mAdapter.addData(orderInfo.getOrders());
                                }
                                mPage = page + 1;
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
                    public void onAfter(boolean isFromCache, @Nullable final OrderInfo orderInfo, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onAfter(isFromCache, orderInfo, call, response, e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (orderInfo != null) {
                                    mRecyclerView.hasMore(orderInfo.getOrders().size() == NUM);
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
