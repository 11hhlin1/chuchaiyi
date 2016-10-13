package com.ccy.chuchaiyi.order;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.base.RecyclerItemOnclickListener;
import com.ccy.chuchaiyi.event.EventOfChooseDepart;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.unnamed.b.atv.model.TreeNode;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/10/9.
 */
public class ChooseDepartmentFragment extends BaseFragment implements RecyclerItemOnclickListener {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ChooseDepartmentAdapter mAdapter;
//    @Bind(R.id.container)
//    RelativeLayout container;
//    private AndroidTreeView tView;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_choose_department;
    }

    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        // 设置布局管理器
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ChooseDepartmentAdapter(getActivity(),new ArrayList<ChoosePassengerItemData>());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setmItemOnclickListener(this);
        getDepartment();
    }

    private void getDepartment() {
        showLoadingDialog(R.string.submitting, false);
        OkHttpUtils.get(ApiConstants.GET_DEPARTMENT)
                .tag(this)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new JsonCallback<DepartmentRsp>(DepartmentRsp.class) {

                    @Override
                    public void onResponse(boolean b, DepartmentRsp departmentRsp, Request request, @Nullable Response response) {
                        dismissLoadingDialog();
//                        final TreeNode root = TreeNode.root();
                        if(departmentRsp == null)
                            return;
                        DepartmentRsp.DepartmentBean departmentBean = departmentRsp.getDepartment();
                        ArrayList<ChoosePassengerItemData> dataArrayList = new ArrayList<>();
                        ChoosePassengerItemData data = new ChoosePassengerItemData();
                        data.departmentBean = departmentBean;
                        data.mViewType = ChooseDepartmentAdapter.VIEW_CONTENT_1;
                        dataArrayList.add(data);
//                        TreeNode myProfile = new TreeNode(departmentBean).setViewHolder(new ProfileHolder(getActivity()));
                        for (DepartmentRsp.DepartmentBean departmentBean1 : departmentBean.getChildDepartments()) {
                            ChoosePassengerItemData data2 = new ChoosePassengerItemData();
                            data2.departmentBean = departmentBean1;
                            data2.mViewType = ChooseDepartmentAdapter.VIEW_CONTENT_2;
                            dataArrayList.add(data2);
//                            TreeNode childNode1 = new TreeNode(departmentBean1).setViewHolder(new ProfileHolder(getActivity()));
                            if (departmentBean1.getChildDepartments().size() > 0) {
                                for (DepartmentRsp.DepartmentBean departmentBean2 : departmentBean1.getChildDepartments()) {
                                    ChoosePassengerItemData data3 = new ChoosePassengerItemData();
                                    data3.departmentBean = departmentBean2;
                                    data3.mViewType = ChooseDepartmentAdapter.VIEW_CONTENT_3;
                                    dataArrayList.add(data3);
                                }
                            }
//                            myProfile.addChild(childNode1);
                        }
                        mAdapter.setData(dataArrayList);
//                        root.addChildren(myProfile);
//
//                        tView = new AndroidTreeView(getActivity(), root);
//                        tView.setDefaultAnimation(true);
//                        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
//                        tView.setDefaultNodeClickListener(nodeClickListener);
//                        container.addView(tView.getView());

                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        dismissLoadingDialog();
                        ToastUtil.shortToast(R.string.load_fail);
                    }
                });
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {


        }
    };


    @Override
    public void onItemClick(View view, int position) {
        ChoosePassengerItemData item =  mAdapter.getData(position);
            onBackPressed();
            EventOfChooseDepart depart = new EventOfChooseDepart();
            depart.departmentBean = item.departmentBean;
            EventBus.getDefault().post(depart);

    }
}
