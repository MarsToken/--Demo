package com.example.administrator.myapplication.mvvm.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * 优化-构建者模式
 * Created by wangmaobo on 2018/4/18.
 */
public class RichTextView extends AppCompatTextView {
    private SpannableString mSpannable;
    private Context mContext;

    public RichTextView(Context context) {
        this(context, null, -1);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public RichTextView setText(String character) {
        super.setText(character, TextView.BufferType.SPANNABLE);
        mSpannable = new SpannableString(character.toString().trim());
        return this;
    }

    public RichTextView setSpan(int start, int end, int styleId) {
        mSpannable.setSpan(new TextAppearanceSpan(mContext, styleId), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public void show() {
        setText(mSpannable, TextView.BufferType.SPANNABLE);
    }
}
