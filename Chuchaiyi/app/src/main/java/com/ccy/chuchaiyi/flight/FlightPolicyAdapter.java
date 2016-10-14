package com.ccy.chuchaiyi.flight;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseRecyclerViewAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/21.
 */
public class FlightPolicyAdapter extends BaseRecyclerViewAdapter<PolicyReason> {
    public static final int VIEW_TITLE = 1;
    public static final int VIEW_CONTENT = 2;
    private int mDividePos;
    private int mFirstReasonPos;
    private int mSecondReasonPos;
    public FlightPolicyAdapter(Context context, List<PolicyReason> items, int firstPos) {
        super(context, items);
        mDividePos = firstPos;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view;
        switch (viewType) {
            case VIEW_TITLE: {
                view = mInflater.inflate(R.layout.policy_title_item, parent, false);
                viewHolder = new ViewHolderTitle(view);
                break;
            }
            case VIEW_CONTENT: {
                view = mInflater.inflate(R.layout.policy_content_item, parent, false);
                viewHolder = new ViewHolderContent(view);
                break;
            }
        }

        return viewHolder;
    }

    public int getFirstReasonPos() {
        return mFirstReasonPos;
    }
    public int getSecondReasonPos() {
        return mSecondReasonPos;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        int type = getItemViewType(position);
        PolicyReason reason = getData(position);
        switch (type) {
            case VIEW_TITLE:{
                ViewHolderTitle viewHolderTitle = (ViewHolderTitle)holder;
                viewHolderTitle.mTitle.setText(reason.mTitle);
                break;
            }
            case VIEW_CONTENT:{
                ViewHolderContent viewHolderTitle = (ViewHolderContent)holder;
                viewHolderTitle.mName.setText(reason.mTitle);
                viewHolderTitle.mCheckIcon.setTag(position);
//                viewHolderTitle.mCheckIcon.setOnCheckedChangeListener(null);
                if(reason.isSel) {
                    viewHolderTitle.mName.setTextColor(mContext.getResources().getColor(R.color.orange));
                    viewHolderTitle.mCheckIcon.setImageResource(R.mipmap.icon_radio_pr);
                } else {
                    viewHolderTitle.mName.setTextColor(mContext.getResources().getColor(R.color.color_aaaaaa));
                    viewHolderTitle.mCheckIcon.setImageResource(R.mipmap.icon_radio_un);
                }
//                viewHolderTitle.mCheckIcon.setChecked(reason.isSel);
//                viewHolderTitle.mCheckIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        if(b) {
//                            if(position < mDividePos) {
//                                for (int i = 0; i < mDividePos; i++) {
//                                    items.get(i).isSel = false;
//                                }
//                                mFirstReasonPos = position;
//                            } else {
//                                int size = items.size();
//                                for (int i = mDividePos + 1; i < size; i++) {
//                                    items.get(i).isSel = false;
//                                }
//                                mSecondReasonPos = position;
//                            }
//
//                        }
//                        PolicyReason reason = items.get(position);
//                        reason.isSel = b;
//                        notifyDataSetChanged();
//
//                    }
//                });
                break;
            }
        }
    }

    public class ViewHolderTitle extends RecyclerView.ViewHolder {

        @Bind(R.id.reason_title)
        TextView mTitle;

        public ViewHolderTitle(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public class ViewHolderContent extends RecyclerView.ViewHolder {

        @Bind(R.id.reason_name)
        TextView mName;
//        @Bind(R.id.reason_check_icon)
//        CheckBox mCheckIcon;
        @Bind(R.id.reason_check_icon)
        ImageView mCheckIcon;
        public ViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) mCheckIcon.getTag();
                    PolicyReason reason = items.get(position);
                    if(!reason.isSel) {
                        if(position < mDividePos) {
                            for (int i = 0; i < mDividePos; i++) {
                                items.get(i).isSel = false;
                            }
                            mFirstReasonPos = position;
                        } else {
                            int size = items.size();
                            for (int i = mDividePos + 1; i < size; i++) {
                                items.get(i).isSel = false;
                            }
                            mSecondReasonPos = position;
                        }
                        reason.isSel = true;
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
}
