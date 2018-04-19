package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangmaobo on 2018/4/4.
 */
public class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected Fragment mFragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (null != getActivity()) {
            Log.e("tagonAttach", "不是null");
        }
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);//
        Log.e("tag onInflate", "一直没有被调用=================================");
        if (null != getActivity()) {
            Log.e("tagonInflate", "不是null");
        } else {
            Log.e("tag onInflate", "是null");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        if (null != getActivity()) {
            Log.e("tagononCreate", "不是null");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        mFragment = this;
        if (null != getActivity()) {
            Log.e("tagononCreateView", "不是null");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();//状态
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
