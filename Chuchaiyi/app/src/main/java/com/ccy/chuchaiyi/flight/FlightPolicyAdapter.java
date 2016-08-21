package com.ccy.chuchaiyi.flight;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    @Bind(R.id.reason_title)
    TextView reasonTitle;

    public FlightPolicyAdapter(Context context, List<PolicyReason> items) {
        super(context, items);
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


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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
                viewHolderTitle.mCheckIcon.setTag(reason);
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
        @Bind(R.id.reason_check_icon)
        CheckBox mCheckIcon;

        public ViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mCheckIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    PolicyReason reason = (PolicyReason) mCheckIcon.getTag();
                    reason.isSel = b;
                }
            });
        }
    }
}
