package com.issac.indoor_navigation.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Issac on 2017/7/14.
 */

public class FragAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public FragAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        mFragments=fragments;
    }


    @Override
    public Fragment getItem(int arg0) {
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
