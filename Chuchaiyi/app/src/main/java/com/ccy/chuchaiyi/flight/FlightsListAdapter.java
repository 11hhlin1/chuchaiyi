package com.ccy.chuchaiyi.flight;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.PageSwitcher;
import com.ccy.chuchaiyi.constant.Constants;
import com.ccy.chuchaiyi.event.EventOfBookBackFlight;
import com.ccy.chuchaiyi.event.EventOfRefreshOrderList;
import com.ccy.chuchaiyi.login.ForgetPswFragment;
import com.ccy.chuchaiyi.net.ApiConstants;
import com.ccy.chuchaiyi.order.ChooseChangeFliReasonFragment;
import com.ccy.chuchaiyi.order.EditOrderFragment;
import com.ccy.chuchaiyi.order.OrderInfo;
import com.ccy.chuchaiyi.order.ReturnOrderSuccessFragment;
import com.ccy.chuchaiyi.util.DiscountUtil;
import com.ccy.chuchaiyi.widget.PolicyDialog;
import com.gjj.applibrary.event.EventOfTokenError;
import com.gjj.applibrary.http.callback.CommonCallback;
import com.gjj.applibrary.http.callback.JsonCallback;
import com.gjj.applibrary.log.L;
import com.gjj.applibrary.task.MainTaskExecutor;
import com.gjj.applibrary.util.PreferencesManager;
import com.gjj.applibrary.util.SaveObjUtil;
import com.gjj.applibrary.util.ToastUtil;
import com.gjj.applibrary.util.Util;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chuck on 2016/8/17.
 */
public class FlightsListAdapter extends BaseExpandableListAdapter {
    private final int mColorOrange;
    private final ForegroundColorSpan mOrangeSpan;
    private final int mColorGray;
    private final AbsoluteSizeSpan mSmallSpan;
    private final ForegroundColorSpan mGraySpan;
    private Resources mRes;
    private LayoutInflater mInflater;
    private List<FlightInfo> mDataList;
    private Context mContext;
    private SparseArray<SpannableString> mAmountCache = new SparseArray<>();
    public static final int VIEW_HEAD = 1;
    private PolicyDialog mConfirmDialog;
//    public static final int VIEW_CONTENT = 2;
    private String mReturnDateString;
    private String mDepartureCode;
    private String mArrivalCode;
    private String mBunkType;
    private String mTitle;
    private int mAccessFlags;

    public void setmOrdersBean(OrderInfo.OrdersBean mOrdersBean) {
        this.mOrdersBean = mOrdersBean;
    }

    private OrderInfo.OrdersBean mOrdersBean;
    private String mChangeReason;
    public FlightsListAdapter(Context context, List<FlightInfo> dataList, String returnDateString, String departureCode, String arrivalCode, String bunkType, String title,int flag) {
        super();
        mContext = context;
        mRes = context.getResources();
        mInflater = LayoutInflater.from(context);
        mDataList = dataList;
        mColorOrange = mRes.getColor(R.color.orange);
        mOrangeSpan = new ForegroundColorSpan(mColorOrange);
        mColorGray = mRes.getColor(R.color.color_222222);
        mGraySpan = new ForegroundColorSpan(mColorGray);
        mSmallSpan = new AbsoluteSizeSpan(Util.px2dip(mContext, Util.sp2px(11)), true);
        mReturnDateString = returnDateString;
        mDepartureCode = departureCode;
        mArrivalCode = arrivalCode;
        mBunkType = bunkType;
        mTitle = title;
        mAccessFlags = flag;
    }


    public void setChangeReason(String reason) {
        mChangeReason = reason;
    }
    /**
     * 填充内容
     *
     * @param list
     */
    public void setData(List<FlightInfo> list) {
//        if (list != mDataList) {
//            if (mDataList != null) {
//                mDataList.clear();
//            }
            mDataList = list;
//        }
        notifyDataSetChanged();
        mAmountCache.clear();
    }

    @Override
    public int getGroupCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    @Override
    public int getChildTypeCount() {
        return 2;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        if (childPosition == 0) {
            return VIEW_HEAD;
        } else {
            return super.getChildType(groupPosition, childPosition);
        }
//
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mDataList == null || mDataList.size() == 0) {
            return 0;
        }
        List<FlightInfo.BunksBean> list = mDataList.get(groupPosition).getBunks();
        if (list == null) {
            return 0;
        }
        return list.size() + 1;
    }

    @Override
    public FlightInfo getGroup(int groupPosition) {
        if (mDataList == null) {
            return null;
        }
        return mDataList.get(groupPosition);
    }

    @Override
    public FlightInfo.BunksBean getChild(int groupPosition, int childPosition) {
        if (mDataList == null) {
            return null;
        }
        if(childPosition == 0) {
            return null;
        }
        return mDataList.get(groupPosition).getBunks().get(childPosition - 1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.flights_list_group_item, parent,
                    false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FlightInfo flightInfo = getGroup(groupPosition);
        String arrival = flightInfo.getArrival().getDateTime();
        int len = arrival.length();
        holder.arriveTime.setText(arrival.substring(len - 5, len));
        String setOutTime = flightInfo.getDeparture().getDateTime();
        int len1 = arrival.length();
        holder.setOutTime.setText(setOutTime.substring(len1 - 5, len1));
        StringBuilder setoutAir = Util.getThreadSafeStringBuilder();
        setoutAir.append(flightInfo.getDeparture().getAirportName()).append(" ").append(flightInfo.getDeparture().getTerminal());
        holder.setOutAirport.setText(setoutAir);
        StringBuilder arriveAir = Util.getThreadSafeStringBuilder();
        setoutAir.append(flightInfo.getArrival().getAirportName()).append(" ").append(flightInfo.getArrival().getTerminal());
        holder.arriveAirport.setText(arriveAir.toString());
        if(flightInfo.getStopInfo() == null) {
            holder.stopTip.setVisibility(View.INVISIBLE);
        } else {
            holder.stopTip.setVisibility(View.VISIBLE);
        }
        SpannableString spannableAmount = mAmountCache.get(groupPosition);
        if (spannableAmount == null) {
            int price = getMinPrice(flightInfo.getBunks());
            spannableAmount = new SpannableString(mRes.getString(R.string.money, price));
            int length = spannableAmount.length();
            spannableAmount.setSpan(mSmallSpan, length - 1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableAmount.setSpan(mGraySpan, length - 1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mAmountCache.put(groupPosition, spannableAmount);
        }
        holder.money.setText(spannableAmount);
//        holder.money.setText(mContext.getString(R.string.money, flightInfo.getYBunkPrice()));
        StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
        stringBuilder.append(flightInfo.getAirlineName()).append(flightInfo.getFlightNo()).append(" | ").append(flightInfo.getPlanType());
        holder.planeMsg.setText(stringBuilder.toString());
        return convertView;
    }

    public static int getMinPrice(List<FlightInfo.BunksBean> bunksBeen) {
        int size = bunksBeen.size();
        int price = 0;
        for (int i = 0; i < size; i++) {
            FlightInfo.BunksBean bunksBean = bunksBeen.get(i);
            FlightInfo.BunksBean.BunkPriceBean bunkPriceBean = bunksBean.getBunkPrice();
            int temp = bunkPriceBean.getFactBunkPrice();
            if (i == 0) {
                price = temp;
            } else if (price > temp) {
                price = temp;
            }
        }

        return price;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        int type = getChildType(groupPosition, childPosition);

        if (type == VIEW_HEAD) {
            ViewHolderChildHead childHolder = null;
            if (null == convertView) {
                convertView = mInflater.inflate(R.layout.flight_list_child_head_item, parent, false);
                childHolder = new ViewHolderChildHead(convertView);
                convertView.setTag(childHolder);
            } else {
                childHolder = (ViewHolderChildHead) convertView.getTag();
            }
            return convertView;
        } else {
            ViewHolderChild childHolder = null;
            if (null == convertView) {
                convertView = mInflater.inflate(R.layout.flights_list_child_item, parent, false);
                childHolder = new ViewHolderChild(convertView);
                convertView.setTag(childHolder);
            } else {
                childHolder = (ViewHolderChild) convertView.getTag();
            }
            FlightInfo.BunksBean bunksBean = getChild(groupPosition, childPosition);
            childHolder.planeType.setText(bunksBean.getBunkName());
            childHolder.changeMsg.setTag(R.id.left_ll,groupPosition);
            childHolder.changeMsg.setTag(R.id.change_msg,childPosition);
            childHolder.bookBtn.setTag(R.id.left_ll,groupPosition);
            childHolder.bookBtn.setTag(R.id.book_btn,childPosition);
            StringBuilder stringBuilder = Util.getThreadSafeStringBuilder();
            stringBuilder.append(DiscountUtil.getDis(bunksBean.getBunkPrice().getFactDiscount())).append("/").append(bunksBean.getBunkCode());
            childHolder.discount.setText(stringBuilder.toString());
            childHolder.detailMoney.setText((mContext.getString(R.string.money_no_end, bunksBean.getBunkPrice().getFactBunkPrice())));
            if (bunksBean.getRemainNum() < 5) {
                childHolder.remainNum.setVisibility(View.VISIBLE);
                childHolder.remainNum.setText(mContext.getString(R.string.num, bunksBean.getRemainNum()));
            } else {
                childHolder.remainNum.setVisibility(View.GONE);
            }
            return convertView;
        }

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    static class ViewHolder {
        @Bind(R.id.set_out_time)
        TextView setOutTime;
        @Bind(R.id.arrive_time)
        TextView arriveTime;
        @Bind(R.id.stop_info_tv)
        TextView stopTip;
        @Bind(R.id.money)
        TextView money;
        @Bind(R.id.set_out_airport)
        TextView setOutAirport;
        @Bind(R.id.arrive_airport)
        TextView arriveAirport;
        @Bind(R.id.plane_msg)
        TextView planeMsg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderChild {
        @Bind(R.id.plane_type)
        TextView planeType;
        @Bind(R.id.change_msg)
        TextView changeMsg;
        @Bind(R.id.discount)
        TextView discount;
        @Bind(R.id.remainNum)
        TextView remainNum;
        @Bind(R.id.book_btn)
        Button bookBtn;
        @Bind(R.id.detail_money)
        TextView detailMoney;

        @OnClick(R.id.book_btn)
        void setBookBtn() {
            int groupPos = (int) bookBtn.getTag(R.id.left_ll);
            int childPos = (int) bookBtn.getTag(R.id.book_btn);
            FlightInfo flight = getGroup(groupPos);
            FlightInfo.BunksBean bunks = getChild(groupPos,childPos);
            book(flight,bunks);
        }
        @OnClick(R.id.left_ll)
        void showDialog() {
            int groupPos = (int) changeMsg.getTag(R.id.left_ll);
            final int childPos = (int) changeMsg.getTag(R.id.change_msg);
            final FlightInfo flight = getGroup(groupPos);
            final FlightInfo.BunksBean bunks = getChild(groupPos,childPos);
            OkHttpUtils.get(ApiConstants.GET_FLIGHT_POLICY)
                    .tag(mContext)
                    .cacheMode(CacheMode.NO_CACHE)
                    .params("airlineCode",flight.getAirline())
                    .params("bunkCode",bunks.getBunkCode())
                    .params("departureDate",flight.getDeparture().getDateTime().split(" ")[0])
                    .params("departureAirportCode",flight.getDeparture().getAirportCode())
                    .params("arrivalAirportCode",flight.getArrival().getAirportCode())
                    .execute(new JsonCallback<ReturnFlightPolicy>(ReturnFlightPolicy.class) {
                        @Override
                        public void onResponse(boolean isFromCache, ReturnFlightPolicy returnFlightPolicy, Request request, @Nullable Response response) {
                            if(mConfirmDialog == null) {
                                PolicyDialog policyDialog = new PolicyDialog(mContext);
                                mConfirmDialog = policyDialog;
                                policyDialog.setCanceledOnTouchOutside(true);
                                policyDialog.setCancelClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dismissConfirmDialog();
                                    }
                                });
                                policyDialog.setConfirmClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        book(flight,bunks);
                                    }
                                });
                            }

                            mConfirmDialog.show();
                            mConfirmDialog.setContent(flight,bunks);
                            mConfirmDialog.setContent(returnFlightPolicy.getReturnPolicyDesc(), returnFlightPolicy.getChangePolicyDesc(),returnFlightPolicy.getSignPolicyDesc());
                        }

                        @Override
                        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                            super.onError(isFromCache, call, response, e);
                            ToastUtil.shortToast(R.string.load_fail);
                        }
                    });


        }
        ViewHolderChild(View view) {
            ButterKnife.bind(this, view);
        }
    }


    private void book(final FlightInfo flight, final FlightInfo.BunksBean bunks) {
        if(mAccessFlags == FlightsListFragment.FROM_INDEX) {
            GetBookValidateRequest bookValidateRequest = new GetBookValidateRequest();
            bookValidateRequest.setArrivalCode(flight.getArrival().getCityCode());
            bookValidateRequest.setDepartureCode(flight.getDeparture().getCityCode());
            bookValidateRequest.setBunkCode(bunks.getBunkCode());
            bookValidateRequest.setFlightDate(flight.getDeparture().getDateTime().split(" ")[0]);
            bookValidateRequest.setFlightNo(flight.getFlightNo());
            bookValidateRequest.setFactBunkPrice(bunks.getBunkPrice().getFactBunkPrice());
            OkHttpUtils.post(ApiConstants.GET_BOOK_VALIDATE)
                    .tag(mContext)
                    .cacheMode(CacheMode.NO_CACHE)
                    .postJson(JSON.toJSONString(bookValidateRequest))
                    .execute(new CommonCallback<BookValidateInfo>() {
                        @Override
                        public BookValidateInfo parseNetworkResponse(Response response) throws Exception {
                            String responseData = response.body().string();
                            if (TextUtils.isEmpty(responseData)) return null;
                            JSONObject jsonObject = new JSONObject(responseData);
                            final String msg = jsonObject.optString("Message", "");
                            final int code = jsonObject.optInt("Code", -1);
                            switch (code) {
                                case 0:
                                case 1:
                                    BookValidateInfo object = JSON.parseObject(responseData, BookValidateInfo.class);
                                    return object;
                                case 2:
                                    MainTaskExecutor.runTaskOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.shortToast(R.string.price_fail);
                                        }
                                    });
                                    throw new IllegalStateException("价格已过期需重新查询");
                                case 401:
                                    EventBus.getDefault().post(new EventOfTokenError());
                                    throw new IllegalStateException("用户授权信息无效");
                                default:
                                    throw new IllegalStateException("错误代码：" + code + "，错误信息：" + msg);
                            }
                        }

                        @Override
                        public void onResponse(boolean isFromCache, final BookValidateInfo bookValidateInfo, Request request, @Nullable Response response) {

                            BookValidateInfo.WarningInfoBean warningInfoBean = bookValidateInfo.getWarningInfo();
                            if(warningInfoBean == null) {
                                if(TextUtils.isEmpty(mReturnDateString)) {
                                    String  SetOutWarningInfoBean = PreferencesManager.getInstance().get("SetOutWarningInfoBean");
                                    Bundle bundle = new Bundle();
                                    if(!TextUtils.isEmpty(SetOutWarningInfoBean)) {
                                        PolicyResultInfo resultInfo = (PolicyResultInfo) SaveObjUtil.unSerialize(SetOutWarningInfoBean);
                                        bundle.putSerializable("SetOutWarningInfoBean", resultInfo);
                                    }
                                    if(!TextUtils.isEmpty(PreferencesManager.getInstance().get("SetOutFlightInfo"))) {
                                        bundle.putSerializable("SetOutFlightInfo", (FlightInfo)SaveObjUtil.unSerialize(PreferencesManager.getInstance().get("SetOutFlightInfo")));
                                        bundle.putSerializable("SetOutBunksBean", (FlightInfo.BunksBean)SaveObjUtil.unSerialize(PreferencesManager.getInstance().get("SetOutBunksBean")));
                                        FlightInfo flightInfo = bookValidateInfo.Flight;
                                        List<FlightInfo.BunksBean> bunksBeanList = flightInfo.getBunks();
                                        bundle.putSerializable("ReturnFlightInfo", flightInfo);
                                        bundle.putSerializable("ReturnBunksBean", bunksBeanList.get(0));
                                    } else {
                                        FlightInfo flightInfo = bookValidateInfo.Flight;
                                        List<FlightInfo.BunksBean> bunksBeanList = flightInfo.getBunks();
                                        bundle.putSerializable("SetOutFlightInfo", flightInfo);
                                        bundle.putSerializable("SetOutBunksBean", bunksBeanList.get(0));
                                    }

                                    StringBuilder title = Util.getThreadSafeStringBuilder();
                                    title.append(flight.getDeparture().getCityName()).append("-").append(flight.getArrival().getCityName()).append(mContext.getString(R.string.reason_common));
                                    PreferencesManager.getInstance().put(Constants.EDIT_ORDER_TITLE,title.toString());
                                    PageSwitcher.switchToTopNavPage((Activity) mContext,EditOrderFragment.class,bundle,title.toString(),mContext.getString(R.string.reason_private));
                                } else {
//                                    Bundle bundle = new Bundle();
                                    PreferencesManager.getInstance().put("SetOutFlightInfo", SaveObjUtil.serialize(bookValidateInfo.Flight));
                                    PreferencesManager.getInstance().put("SetOutBunksBean", SaveObjUtil.serialize(bookValidateInfo.Flight.getBunks().get(0)));

                                    PreferencesManager.getInstance().put("SetOutWarningInfoBean", "");
//                                    bundle.putString("DepartureCode", mArrivalCode);
//                                    bundle.putString("ArrivalCode", mDepartureCode);
//                                    bundle.putString("SetOutDate", mReturnDateString);
//                                    bundle.putString("BunkType",mBunkType);
                                    mReturnDateString = "";
                                    EventOfBookBackFlight eventOfBookBackFlight = new EventOfBookBackFlight();
//                                    eventOfBookBackFlight.ArrivalCode = mDepartureCode;
//                                    eventOfBookBackFlight.DepartureCode = mArrivalCode;
//                                    eventOfBookBackFlight.SetOutDate = mReturnDateString;
                                    EventBus.getDefault().post(eventOfBookBackFlight);
//                                    PageSwitcher.switchToTopNavPage((Activity) mContext, FlightsListFragment.class, bundle, mTitle ,mContext.getString(R.string.policy));
                                }

                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("warningInfoBean", warningInfoBean);
                                FlightInfo flightInfo = bookValidateInfo.Flight;
                                List<FlightInfo.BunksBean> bunksBeanList = flightInfo.getBunks();
                                bundle.putSerializable("FlightInfo", flightInfo);
                                bundle.putSerializable("BunksBean", bunksBeanList.get(0));
                                bundle.putString("returnDate", mReturnDateString);
                                bundle.putString("mDepartureCode", mDepartureCode);
                                bundle.putString("mArrivalCode", mArrivalCode);
                                bundle.putString("mBunkType", mBunkType);
                                bundle.putString("mTitle", mTitle);
                                PageSwitcher.switchToTopNavPage((Activity) mContext,FlightPolicyFragment.class,bundle,mContext.getString(R.string.policy),null);

                            }


                        }

                        @Override
                        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                            super.onError(isFromCache, call, response, e);
                            ToastUtil.shortToast(R.string.load_fail);
                        }
                    });
        } else if(mAccessFlags == FlightsListFragment.FROM_CHANGE_FLIGHT) {
            ChangeFlightReq changeFlightReq = new ChangeFlightReq();
            changeFlightReq.ChangeReason = mChangeReason;
            changeFlightReq.SrcOrderId = mOrdersBean.getOrderId();
            List<FlightInfo.BunksBean> bunksBeanList = new ArrayList<>();
            bunksBeanList.add(bunks);
            flight.setBunks(bunksBeanList);
            changeFlightReq.ChangeRoute = flight;
            int price = bunks.getBunkPrice().getFactBunkPrice() - mOrdersBean.getFactTicketPrice();
            changeFlightReq.ChangeDifferencePrice = price > 0 ? price : 0;
            OkHttpUtils.post(ApiConstants.CHANGE_ORDER)
                    .tag(this)
                    .cacheMode(CacheMode.NO_CACHE)
                    .postJson(JSON.toJSONString(changeFlightReq))
                    .execute(new JsonCallback<String>(String.class) {

                        @Override
                        public void onResponse(boolean b, String s, Request request, @Nullable Response response) {
                            ToastUtil.shortToast(R.string.success);
                            Bundle bundle = new Bundle();
                            StringBuilder city = Util.getThreadSafeStringBuilder();
                            city.append(mOrdersBean.getDepartureCityName()).append("-").append(mOrdersBean.getArrivalCityName());
                            bundle.putString("city", city.toString());
                            bundle.putString("orderNum", mOrdersBean.getOrderNo());
                            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(s);
                            bundle.putInt("orderId", jsonObject.getIntValue("ChangeOrderId"));
                            bundle.putString("tip", mContext.getString(R.string.change_order_success_tip));
                            EventOfRefreshOrderList eventOfRefreshOrderList = new EventOfRefreshOrderList();
                            EventBus.getDefault().post(eventOfRefreshOrderList);
                            PageSwitcher.switchToTopNavPage((Activity) mContext, ReturnOrderSuccessFragment.class, bundle, mContext.getString(R.string.changePolicy),mContext.getString(R.string.index));

                        }

                        @Override
                        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                            super.onError(isFromCache, call, response, e);
                        }

                    });


        }
    }
    /**
     * dismiss确认对话框
     */
    private void dismissConfirmDialog() {
        PolicyDialog confirmDialog = mConfirmDialog;
        if (null != confirmDialog && confirmDialog.isShowing()) {
            confirmDialog.dismiss();
            mConfirmDialog = null;
        }
    }
    class ViewHolderChildHead {
        @Bind(R.id.hide_tv)
        TextView hideTv;
        @Bind(R.id.hide_iv)
        ImageView hideIv;
        ViewHolderChildHead(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
