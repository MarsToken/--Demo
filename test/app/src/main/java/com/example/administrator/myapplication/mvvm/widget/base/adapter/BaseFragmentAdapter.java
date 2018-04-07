package com.example.administrator.myapplication.mvvm.widget.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * fragment-baseAdapter
 * Created by wangmaobo on 2018/4/7.
 */
public abstract class BaseFragmentAdapter<T> extends FragmentPagerAdapter {
    private T[] mTitle;

    public BaseFragmentAdapter(FragmentManager fm, T[] title) {
        super(fm);
        mTitle = title;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position].toString();
    }

    @Override
    public Fragment getItem(int position) {
        return getFragment(position);
    }

    protected abstract Fragment getFragment(int position);

    @Override
    public int getCount() {
        return mTitle.length;
    }
}
