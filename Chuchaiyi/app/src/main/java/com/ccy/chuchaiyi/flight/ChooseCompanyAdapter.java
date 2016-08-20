package com.ccy.chuchaiyi.flight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.index.SeatType;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/20.
 */
public class ChooseCompanyAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Company> mCompanyList;

    public ChooseCompanyAdapter(Context context, List<Company> companies) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mCompanyList = companies;
    }

    @Override
    public int getCount() {
        return mCompanyList.size();
    }

    @Override
    public Company getItem(int position) {
        return mCompanyList.get(position);
    }

    public List<Company> getmCompanyList() {
        return mCompanyList;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.choose_company_list_item, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Company company = getItem(position);
        if(company.mIsSel) {
            vh.companyName.setTextColor(mContext.getResources().getColor(R.color.orange));
        } else {
            vh.companyName.setTextColor(mContext.getResources().getColor(R.color.color_222222));
        }
        vh.companyName.setText(company.mAirlineName);
        vh.checkIcon.setTag(position);
        vh.checkIcon.setOnCheckedChangeListener(null);
        vh.checkIcon.setChecked(company.mIsSel);
        vh.checkIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (position == 0 && b) {
                    int len = mCompanyList.size();
                    for (int i = 0; i<len;i++){
                        Company company = mCompanyList.get(i);
                        if(i==0){
                            company.mIsSel  = b;
                        } else {
                            company.mIsSel = false;
                        }
                    }

                } else {
                    mCompanyList.get(position).mIsSel = b;
                    mCompanyList.get(0).mIsSel = false;
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.company_name)
        TextView companyName;
        @Bind(R.id.check_icon)
        CheckBox checkIcon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }
    }
}
