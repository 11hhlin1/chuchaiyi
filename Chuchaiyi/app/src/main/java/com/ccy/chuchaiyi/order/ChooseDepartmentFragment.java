package com.ccy.chuchaiyi.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseFragment;
import com.ccy.chuchaiyi.event.EventOfChooseDepart;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.util.ToastUtil;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/10/9.
 */
public class ChooseDepartmentFragment extends BaseFragment {
    @Bind(R.id.container)
    RelativeLayout container;
    private AndroidTreeView tView;

    @Override
    public int getContentViewLayout() {
        return R.layout.fragment_choose_department;
    }

    @Override
    public void initView() {

        getDepartment();
    }

    private void getDepartment() {
        showLoadingDialog(R.string.submitting,false);
        OkHttpUtils.get(ApiConstants.GET_DEPARTMENT)
                .tag(this)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new JsonCallback<DepartmentRsp>(DepartmentRsp.class) {

                    @Override
                    public void onResponse(boolean b, DepartmentRsp departmentRsp, Request request, @Nullable Response response) {
                        dismissLoadingDialog();
                        final TreeNode root = TreeNode.root();
                        DepartmentRsp.DepartmentBean departmentBean = departmentRsp.getDepartment();
                        TreeNode myProfile = new TreeNode(departmentBean).setViewHolder(new ProfileHolder(getActivity()));
                        for (DepartmentRsp.DepartmentBean departmentBean1 : departmentBean.getChildDepartments()) {
                            TreeNode childNode1 = new TreeNode(departmentBean1).setViewHolder(new ProfileHolder(getActivity()));
                            if(departmentBean1.getChildDepartments().size() > 0) {
                                for (DepartmentRsp.DepartmentBean departmentBean2 : departmentBean1.getChildDepartments()) {
                                    TreeNode childNode2 = new TreeNode(departmentBean2).setViewHolder(new ProfileHolder(getActivity()));
                                    childNode1.addChild(childNode2);
                                }
                            }
                            myProfile.addChild(childNode1);
                        }
                        root.addChildren(myProfile);

                        tView = new AndroidTreeView(getActivity(), root);
                        tView.setDefaultAnimation(true);
                        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
                        tView.setDefaultNodeClickListener(nodeClickListener);
                        container.addView(tView.getView());

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
            DepartmentRsp.DepartmentBean item = (DepartmentRsp.DepartmentBean) value;
            if(item.getChildDepartments().size() == 0) {
                onBackPressed();
                EventOfChooseDepart depart = new EventOfChooseDepart();
                depart.departmentBean = item;
                EventBus.getDefault().post(depart);
            }

        }
    };
}
