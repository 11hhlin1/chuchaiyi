package com.ccy.chuchaiyi.check;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccy.chuchaiyi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chuck on 2016/9/12.
 */
public class PersonalViewHolder {
    @Bind(R.id.check_state_icon)
    ImageView checkStateIcon;
    @Bind(R.id.check_person)
    TextView checkPerson;
    @Bind(R.id.check_person_job)
    TextView checkPersonJob;
    @Bind(R.id.check_time)
    TextView checkTime;
    @Bind(R.id.check_detail_tv)
    TextView checkDetailTv;
    View parent;
    PersonalViewHolder(View view) {
        parent = view;
        ButterKnife.bind(this, view);
    }
}
