package com.example.administrator.myapplication.mvvm.widget.base;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2018/4/17.
 */
public abstract class BaseFreeLocationPopupWindow extends PopupWindow {
    private View mPopupLayout;

    public BaseFreeLocationPopupWindow(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        mPopupLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getLayoutId(), null);
        setContentView(mPopupLayout);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(context.getResources().getDrawable(R.color.transparent));
        setOutsideTouchable(true);
    }

    protected abstract int getLayoutId();

    public void showAtDIY(View view, int offX, int offY) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        //相对于屏幕的
        showAtLocation(view, Gravity.NO_GRAVITY, offX, offY);
    }
}
