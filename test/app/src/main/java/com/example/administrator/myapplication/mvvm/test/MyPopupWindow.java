package com.example.administrator.myapplication.mvvm.test;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvvm.utils.ScreenUtils;
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
    protected BasePopupWindow setData() {

        return super.setData();
    }

    @Override
    protected void setAnimation(final View view) {
        final int final_location;
        if (view.getContext().getApplicationInfo().targetSdkVersion
                < Build.VERSION_CODES.LOLLIPOP_MR1) {
            final_location = -ScreenUtils.getNavBarHeight(view.getContext());
        } else {
            final_location = 0;
        }
        view.post(new Runnable() {
            @Override
            public void run() {
                int height = view.getHeight();
                ValueAnimator animator = ObjectAnimator
                        .ofFloat(view, "translationY", height,final_location );
                animator.setDuration(200).start();
            }
        });
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
