package com.ccy.chuchaiyi.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.BaseRecyclerViewAdapter;
import com.ccy.chuchaiyi.base.RecyclerItemOnclickListener;
import com.ccy.chuchaiyi.contact.Approval;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chuck on 2016/10/13.
 */

public class ChooseDepartmentAdapter extends BaseRecyclerViewAdapter<ChoosePassengerItemData> {
    public static final int VIEW_CONTENT_1 = 1;
    public static final int VIEW_CONTENT_2 = 2;
    public static final int VIEW_CONTENT_3 = 3;


    public void setmItemOnclickListener(RecyclerItemOnclickListener mItemOnclickListener) {
        this.mItemOnclickListener = mItemOnclickListener;
    }

    RecyclerItemOnclickListener mItemOnclickListener;
    public ChooseDepartmentAdapter(Context context, List<ChoosePassengerItemData> items) {
        super(context, items);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view;
        switch (viewType) {
            case VIEW_CONTENT_1: {
                view = mInflater.inflate(R.layout.choose_passenger_item_1, parent, false);
                viewHolder = new ViewHolderContent(view);
                break;
            }
            case VIEW_CONTENT_2: {
                view = mInflater.inflate(R.layout.choose_passenger_item_2, parent, false);
                viewHolder = new ViewHolderContent(view);
                break;
            }
            case VIEW_CONTENT_3: {
                view = mInflater.inflate(R.layout.choose_passenger_item_3, parent, false);
                viewHolder = new ViewHolderContent(view);
                break;
            }
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        int type = getItemViewType(position);
        ChoosePassengerItemData data = getData(position);
        ViewHolderContent viewHolderTitle = (ViewHolderContent)holder;
        viewHolderTitle.mName.setText(data.departmentBean.getName());
        viewHolderTitle.mName.setTag(position);
    }


    public class ViewHolderContent extends RecyclerView.ViewHolder {

        @Bind(R.id.passenger_name)
        TextView mName;

        public ViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) mName.getTag();
                    mItemOnclickListener.onItemClick(v,pos);
                }
            });
        }
    }

//
//    public class ViewHolderContent2 extends RecyclerView.ViewHolder {
//
//        @Bind(R.id.passenger_name)
//        TextView mName;
//
//        public ViewHolderContent2(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//
//        }
//    }
//
//
//    public class ViewHolderContent3 extends RecyclerView.ViewHolder {
//
//        @Bind(R.id.passenger_name)
//        TextView mName;
//
//        public ViewHolderContent3(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//
//        }
//    }
}

