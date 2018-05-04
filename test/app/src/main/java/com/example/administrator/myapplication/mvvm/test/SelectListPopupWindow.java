package com.example.administrator.myapplication.mvvm.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cunwen on 16/3/25.
 */

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvvm.utils.AnimateUtil;

public class SelectListPopupWindow extends PopupWindow {
    DisplayMetrics dm;

    private TextView title;
    private Button btn1, btn_cancel;
    private RecyclerView recyclerview;
    private View mMenuView;


    private Context context;
    private static final long DISMISSDELAYED = 2000;

    private Animation inAnim;
    private Animation outAnim;

    public ArrayList<String> list = new ArrayList<String>();
    public int position = -1;

    public SelectListPopupWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        this.context = context;

        dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        initAnimation();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_popup, null);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.content).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


        mMenuView.startAnimation(inAnim);

    }

    public void setBtn(Boolean flag,String textBtn1) {
        if (flag) {
            btn1.setVisibility(View.VISIBLE);
            btn1.setText(textBtn1);
        }else{
            btn1.setVisibility(View.GONE);
        }
    }
    public void setBtnbottom(Boolean flag,String textBtn1) {
        if (flag) {
            btn_cancel.setVisibility(View.VISIBLE);
            btn_cancel.setText(textBtn1);
        }else{
            btn_cancel.setVisibility(View.GONE);
        }
    }

    public void setTitle(Boolean flag,String text) {
        if (flag) {
            title.setVisibility(View.VISIBLE);
            title.setText(text);
        }else{
            title.setVisibility(View.GONE);
        }
    }





    private int gravity = Gravity.BOTTOM;


    protected void initAnimation() {
        if (inAnim == null)
            inAnim = getInAnimation();
        if (outAnim == null)
            outAnim = getOutAnimation();
    }


    public Animation getInAnimation() {
        int res = AnimateUtil.getAnimationResource(this.gravity, true);
        return AnimationUtils.loadAnimation(context, res);
    }

    public Animation getOutAnimation() {
        int res = AnimateUtil.getAnimationResource(this.gravity, false);
        return AnimationUtils.loadAnimation(context, res);
    }

    Animation.AnimationListener outAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            dismissImmediately();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public void dismissImmediately() {
        dismiss();
        context = null;
    }


    private SelectListPopupWindow.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(SelectListPopupWindow.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

}

