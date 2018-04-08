package com.example.administrator.myapplication.mvvm.widget.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * function="基于单个view的viewpager适配器"
 * Created by wangmaobo on 2018/4/8.
 */
public abstract class BaseSingleViewAdapter<T> extends PagerAdapter {
    private List<T> mList;
    private Context mContext;
    private View mCurrentView;

    public BaseSingleViewAdapter(Context context, List<T> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        if (null == mList) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext)
                .inflate(getContentLayout(position), container, false);
        container.addView(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        onBindViewData(view, position);
        return view;
    }

    /**
     * 绑定数据-数据源为mList
     *
     * @param view
     * @param position
     */
    protected abstract void onBindViewData(View view, int position);

    /**
     * 获取对应postion的viewId
     *
     * @param position
     * @return
     */
    protected abstract int getContentLayout(int position);

    private <T extends View> T getView(int viewId, View parent) {
        return parent.findViewById(viewId);
    }

    public void setText(int viewId, View parent, String text) {
        TextView tv = getView(viewId, parent);
        tv.setText(text);
    }

    public void setImage(int viewId, View parent, Object params) {
        ImageView iv = getView(viewId, parent);
        if (params instanceof String) {
            //自己写加载图片的逻辑
        } else if (params instanceof Integer) {
            iv.setImageResource((Integer) params);
        } else if (params instanceof Bitmap) {
            iv.setImageBitmap((Bitmap) params);
        } else if (params instanceof Drawable) {
            iv.setImageDrawable((Drawable) params);
        } else {
            try {
                throw new Exception("params is wrong!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentView = (View) object;
    }

    public View getCurrentView() {
        return mCurrentView;
    }
}
