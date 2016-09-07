package com.ccy.chuchaiyi.index;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Created by Chuck on 2015/10/12.
 */
public class IndexFragmentAdapter extends FragmentPagerAdapter {
    private Fragment[] mCache;

    public IndexFragmentAdapter(FragmentManager fm, Fragment[] cache) {
        super(fm);
        mCache = cache;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mCache[position];
        if (fragment != null) {
            return fragment;
        }
//        if (position == 0) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", position);
        fragment = new IndexContentFragment();
        fragment.setArguments(bundle);
//        } else if (position == 1) {
//            fragment = new IndexContentFragment();
//        }
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
