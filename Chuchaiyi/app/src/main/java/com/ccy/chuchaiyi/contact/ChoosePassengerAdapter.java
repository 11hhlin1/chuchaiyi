package com.ccy.chuchaiyi.contact;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseRecyclerViewAdapter;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.city.PinyinUtils;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.order.EditPassengerFragment;
import com.gjj.applibrary.event.EventOfTokenError;
import com.gjj.applibrary.http.callback.CommonCallback;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/8/24.
 */
public class ChoosePassengerAdapter extends BaseRecyclerViewAdapter<PassengerData> {
    public static final int VIEW_TYPE_COMMON_TITLE = 1; // 标题
    public static final int VIEW_TYPE_ITEM = 2; // 没数据

    public static final int VIEW_TYPE_PINYIN_ITEM = 3; // 顶部空view

    public void setmCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    private CallBack mCallBack;
    public ChoosePassengerAdapter(Context context, List<PassengerData> items) {
        super(context, items);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        PassengerData passengerData = getData(position);
        switch (type) {
            case VIEW_TYPE_PINYIN_ITEM: {
                PinYinViewHolder pinYinViewHolder = (PinYinViewHolder)holder;
                pinYinViewHolder.pinyinTitle.setText(passengerData.sortLetters);
                break;
            }
            case VIEW_TYPE_ITEM: {
                ViewHolderItemStaff viewHolderItemStaff = (ViewHolderItemStaff)holder;
                viewHolderItemStaff.passengerName.setText(passengerData.name);
                viewHolderItemStaff.passengerName.setTag(position);
//                viewHolderItemStaff.job.setText(staff.mJob);
//                viewHolderItemStaff.icon.setTag(staff);
//                if (staff.mIsSel) {
//                    viewHolderItemStaff.icon.setVisibility(View.VISIBLE);
//                } else {
//                    viewHolderItemStaff.icon.setVisibility(View.INVISIBLE);
//                }
                break;
            }
            default:
                super.onBindViewHolder(holder, position);
                break;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_COMMON_TITLE: {
                View view = mInflater.inflate(R.layout.passenger_first_item, parent, false);
                RecyclerView.ViewHolder viewHolder = new ViewHolderHeader(view);
                return viewHolder;
            }
            case VIEW_TYPE_ITEM: {
                View view = mInflater.inflate(R.layout.passenger_second_item, parent, false);
                RecyclerView.ViewHolder viewHolder = new ViewHolderItemStaff(view);
                return viewHolder;
            }
            case VIEW_TYPE_PINYIN_ITEM: {
                View view = mInflater.inflate(R.layout.passenger_pinyin_item, parent, false);
                RecyclerView.ViewHolder viewHolder = new PinYinViewHolder(view);
                return viewHolder;
            }
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    class ViewHolderHeader extends RecyclerView.ViewHolder {
        @Bind(R.id.common_title)
        TextView commonTitle;

        ViewHolderHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderItemStaff extends RecyclerView.ViewHolder {
        @Bind(R.id.passenger_name)
        TextView passengerName;
        @Bind(R.id.passenger_type)
        TextView passengerType;

        ViewHolderItemStaff(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) passengerName.getTag();
                    PassengerData passengerData = getData(pos);
                    if(passengerData.mStoreUser != null) {
                        OkHttpUtils.get(ApiConstants.GET_EMPLOYEE)
                                .tag(this)
                                .params("employeeId", String.valueOf(passengerData.mStoreUser.getBelongedEmployeeId()))
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
                                        mCallBack.selPerson(passengerInfo);

                                    }

                                    @Override
                                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                                        super.onError(isFromCache, call, response, e);
//                                        dismissLoadingDialog();
                                        ToastUtil.shortToast(R.string.load_fail);
                                    }
                                });
                    } else {
                        mCallBack.selPerson(passengerData.mPassengerInfo);
                    }

                }
            });
        }
    }



    public interface CallBack {
        void selPerson(PassengerInfo passengerInfo);
    }
    class PinYinViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.pinyin_title)
        TextView pinyinTitle;

        PinYinViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
