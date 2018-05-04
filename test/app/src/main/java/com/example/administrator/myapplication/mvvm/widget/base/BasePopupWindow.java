package com.example.administrator.myapplication.mvvm.widget.base;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.administrator.myapplication.mvvm.utils.ScreenUtils;

import java.util.List;

/**
 * function="自定义PopupWindow：如果有必要自定义列表-T代表model返回来的数据
 * 此时列表在子类的{@link #setData()}写逻辑"
 * Created by wangmaobo on 2018/4/4.
 */
public abstract class BasePopupWindow<T> extends PopupWindow {
    private View mPopupLayout;
    private View.OnClickListener mListener;

    protected List<T> mBeans;

    public BasePopupWindow(Context context, View.OnClickListener listener, List<T> beans) {
        super(context);
        initView(context);
        mBeans = beans;
        mListener = listener;
    }

    private void initView(Context context) {
        mPopupLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getLayoutId(), null);
        setContentView(mPopupLayout);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(dw);
        mPopupLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                mPopupLayout.findViewById(getContentViewId()).post(new Runnable() {
                    @Override
                    public void run() {
                        int height = mPopupLayout.findViewById(getContentViewId()).getTop();
                        int y = (int) event.getY();
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (y < height) {
                                dismiss();
                            }
                        }
                    }
                });

                return true;
            }
        });
        setData();
    }

    private View getView(int viewId) {
        return mPopupLayout.findViewById(viewId);
    }

    /**
     * 如果有必要-子类根据model返回的数据写逻辑
     *
     * @return
     */
    protected BasePopupWindow setData() {
        return this;
    }

    public BasePopupWindow setListener(int viewId) {
        getView(viewId).setOnClickListener(mListener);
        return this;
    }

    /**
     * 位置-底部
     *
     * @param parent
     */
    public void showAtBottom(View parent) {
        View view = mPopupLayout.findViewById(getContentViewId());
        setAnimation(view);
        showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    protected void setAnimation(View target) {

    }

    /**
     * 点击不消失的content view
     *
     * @return
     */
    protected abstract int getContentViewId();

    protected abstract int getLayoutId();

}
