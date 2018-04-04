package com.example.administrator.myapplication.mvvm.widget.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * function="基于ListView GridView的万能适配器"
 * Created by wangmaobo on 2018/4/3.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected List<T> mBeans;
    private Context mContext;
    private int mLayoutId;

    public BaseListAdapter(Context context, List<T> beans, int layoutId) {
        mBeans = beans;
        mContext = context;
        mLayoutId = layoutId;
    }

    /**
     * @param holder ViewHolder对象
     * @param item   mBean里的数据源
     */
    public abstract void setContent(ViewHolder holder, T item);

    @Override
    public int getCount() {
        return mBeans.size();
    }

    @Override
    public Object getItem(int position) {//源数据
        return mBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = getViewHolder(position, convertView, parent);
        setContent(holder, mBeans.get(position));
        return holder.getConvertView();
    }

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mLayoutId, position);
    }

    public static class ViewHolder {
        private SparseArray<View> mViews;
        private View mConvertView;
        private int mPosition;

        private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
            mViews = new SparseArray();
            mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            mConvertView.setTag(this);
            mPosition = position;
        }

        public static ViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int layoutId, int position) {
            if (convertView == null) {
                return new ViewHolder(context, parent, layoutId, position);
            }
            return (ViewHolder) convertView.getTag();
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (null == view) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public View getConvertView() {
            return mConvertView;
        }

        public void setText(int viewId, String text) {
            TextView tv = mConvertView.findViewById(viewId);
            tv.setText(text);
        }

        public void setImage(int viewId, Object params) {
            ImageView iv = getView(viewId);
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

    }
}
