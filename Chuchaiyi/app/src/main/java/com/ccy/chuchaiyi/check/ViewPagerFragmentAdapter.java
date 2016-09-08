package com.ccy.chuchaiyi.check;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;


import java.util.ArrayList;

/**
 * Created by Chuck on 2016/9/5.
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private Fragment[] mCache;
    private ArrayList<CategoryData> dataArrayList;
    public ViewPagerFragmentAdapter(FragmentManager fm, Fragment[] cache, ArrayList<CategoryData> datas) {
        super(fm);
        mCache = cache;
        dataArrayList = datas;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mCache[position];
        if (fragment != null) {
            return fragment;
        }
        fragment = new CheckTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", dataArrayList.get(position));
        fragment.setArguments(bundle);
        mCache[position] = fragment;
        return fragment;
    }

    @Override
    public int getCount() {
        return mCache.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}

