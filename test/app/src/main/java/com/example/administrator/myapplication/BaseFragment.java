package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangmaobo on 2018/4/4.
 */
public class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected Fragment mFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        mActivity = getActivity();
        mFragment = this;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected String getFragmentName() {
        if (mFragment == null) {
            mFragment = this;
        }
        String packName = mFragment.getClass().getName();
        return packName.substring(packName.lastIndexOf(".") + 1);
    }

    protected View findViewById(int viewId) {
        return getView().findViewById(viewId);
    }
}
