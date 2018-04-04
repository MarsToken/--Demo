package com.example.administrator.myapplication.mvvm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

/**
 * Created by wangmaobo on 2018/4/4.
 */
public class TopTitleView extends RelativeLayout {
    /**
     * View
     */
    private ImageButton mLeftButton, mRightButton;
    private TextView mCenterTitle, mBottomLine;
    /**
     * attrs
     */
    private int mLeftTextColor, mRightTextColor, mTitleColor;
    private Drawable mLeftBackground, mRightBackground;
    private String mLeftText, mRightText, mTitleText;
    private float mTitleTextSize;
    private LayoutParams mLeftParams, mTitleParams, mRightParams, mBottomParams;

    private TopClickListener mListener;

    public TopTitleView(Context context) {
        this(context, null, -1);
    }

    public TopTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TopTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TopTitleView);
        mLeftTextColor = array.getColor(R.styleable.TopTitleView_leftTextColor, 0);
        mLeftBackground = array.getDrawable(R.styleable.TopTitleView_leftBackground);
        mLeftText = array.getString(R.styleable.TopTitleView_leftText);

        mRightTextColor = array.getColor(R.styleable.TopTitleView_rightTextColor, 0);
        mRightBackground = array.getDrawable(R.styleable.TopTitleView_rightBackground);
        mRightText = array.getString(R.styleable.TopTitleView_rightText);

        mTitleColor = array.getColor(R.styleable.TopTitleView_titleColor, Color.BLACK);
        mTitleText = array.getString(R.styleable.TopTitleView_titleText);
        mTitleTextSize = array.getDimension(R.styleable.TopTitleView_titleTextSize, 16);

        array.recycle();

        mLeftButton = new ImageButton(context);
        mRightButton = new ImageButton(context);
        mCenterTitle = new TextView(context);
        mBottomLine = new TextView(context);

        mLeftButton.setBackgroundDrawable(mLeftBackground);
        mRightButton.setBackgroundDrawable(mRightBackground);

        mCenterTitle.setText(mTitleText);
        mCenterTitle.setTextColor(mTitleColor);
        mCenterTitle.setTextSize(mTitleTextSize);
        mCenterTitle.setGravity(Gravity.CENTER);
        //mCenterTitle.setTypeface();
        mCenterTitle.setSingleLine(true);
        //mCenterTitle.setMaxWidth();
        mCenterTitle.setEllipsize(TextUtils.TruncateAt.END);

        mLeftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        mLeftParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        addView(mLeftButton, mLeftParams);

        mRightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        mRightParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        addView(mRightButton, mRightParams);

        mTitleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mTitleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(mCenterTitle, mTitleParams);

        mBottomParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mBottomParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, TRUE);
        mBottomLine.setBackgroundColor(Color.GRAY);
        addView(mBottomLine, mBottomParams);

        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener)
                    mListener.onLeftClick();
            }
        });
        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener)
                    mListener.onRightClick();
            }
        });
        if (context instanceof TopClickListener)
            mListener = (TopClickListener) context;

    }

    public interface TopClickListener {
        void onLeftClick();

        void onRightClick();
    }
}
