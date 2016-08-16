package com.ccy.chuchaiyi.check;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ccy.chuchaiyi.index.IndexContentFragment;

/**
 * Created by Chuck on 2016/8/16.
 */
public class CheckFragmentAdapter extends FragmentPagerAdapter {
    private Fragment[] mCache;

    public CheckFragmentAdapter(FragmentManager fm, Fragment[] cache) {
        super(fm);
        mCache = cache;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mCache[position];
        if (fragment != null) {
            return fragment;
        }
        if (position == 0) {
            fragment = new CheckTypeFragment();
        } else if (position == 1) {
            fragment = new CheckTypeFragment();
        } else {
            fragment = new CheckTypeFragment();
        }
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
