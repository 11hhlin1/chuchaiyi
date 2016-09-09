package com.ccy.chuchaiyi.contact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.base.RecyclerItemOnclickListener;
import com.ccy.chuchaiyi.base.SimpleRecyclerViewAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chuck on 2016/9/9.
 */
public class ChooseProjectAdapter extends SimpleRecyclerViewAdapter<ProjectInfo.ProjectsBean> {

    public void setmItemOnclickListener(RecyclerItemOnclickListener mItemOnclickListener) {
        this.mItemOnclickListener = mItemOnclickListener;
    }

    RecyclerItemOnclickListener mItemOnclickListener;

    public ChooseProjectAdapter(Context context, List<ProjectInfo.ProjectsBean> items) {
        super(context, items);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderHeader viewHolderHeader = (ViewHolderHeader) holder;
        ProjectInfo.ProjectsBean projectsBean = getData(position);
        viewHolderHeader.projectName.setText(projectsBean.getProjectName());
        viewHolderHeader.projectName.setTag(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.choose_project_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHolderHeader(view);
        return viewHolder;
    }


    class ViewHolderHeader extends RecyclerView.ViewHolder {
        @Bind(R.id.project_name)
        TextView projectName;
        @Bind(R.id.check_icon)
        CheckBox checkIcon;

        ViewHolderHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) projectName.getTag();
                    mItemOnclickListener.onItemClick(v, pos);
                }
            });
        }
    }



}