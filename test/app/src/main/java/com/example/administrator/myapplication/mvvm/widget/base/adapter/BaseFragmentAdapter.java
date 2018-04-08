package com.example.administrator.myapplication.mvvm.widget.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * function="基于fragment的viewpager的适配器"
 * Created by wangmaobo on 2018/4/7.
 */
public abstract class BaseFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitle;

    public BaseFragmentAdapter(FragmentManager fm, String[] title) {
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
