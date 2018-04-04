package com.example.administrator.myapplication.mvvm.test;

import android.content.Context;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvvm.widget.base.BasePopupWindow;

import java.util.List;

/**
 * Created by Administrator on 2018/4/4.
 */
public class MyPopupWindow extends BasePopupWindow<String> {

    public MyPopupWindow(Context context, View.OnClickListener listener, List<String> beans) {
        super(context, listener, beans);
    }

    @Override
    protected int getContentViewId() {
        return R.id.content;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_popup;
    }
}
