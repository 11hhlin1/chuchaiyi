package com.ccy.chuchaiyi.contact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseRecyclerViewAdapter;
//import com.ccy.chuchaiyi.base.RecyclerItemOnclickListener;
import com.gjj.applibrary.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chuck on 2016/9/2.
 */
public class ChooseCheckNumAdapter extends BaseRecyclerViewAdapter<Approval> {

//    public void setmItemOnclickListener(RecyclerItemOnclickListener mItemOnclickListener) {
//        this.mItemOnclickListener = mItemOnclickListener;
//    }
//
//    RecyclerItemOnclickListener mItemOnclickListener;
    public ChooseCheckNumAdapter(Context context, List<Approval> items) {
        super(context, items);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderHeader viewHolderHeader =  (ViewHolderHeader)holder;
        Approval approval = getData(position);
        viewHolderHeader.checkCity.setText(approval.getTravelDestination());
        viewHolderHeader.checkNum.setText(approval.getApprovalNo());
        viewHolderHeader.checkDate.setText(mContext.getString(R.string.date_format, approval.getTravelDateStart(),approval.getTravelDateEnd()));
        viewHolderHeader.checkNum.setTag(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.choose_check_num_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHolderHeader(view);
        return viewHolder;
    }


    class ViewHolderHeader extends RecyclerView.ViewHolder{
        @Bind(R.id.check_num)
        TextView checkNum;
        @Bind(R.id.check_city)
        TextView checkCity;
        @Bind(R.id.check_date)
        TextView checkDate;
        @Bind(R.id.check_icon)
        CheckBox checkIcon;

        ViewHolderHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) checkNum.getTag();
//                    mItemOnclickListener.onItemClick(v,pos);
                }
            });
        }
    }


}
