package com.example.administrator.myapplication.mvvm.widget.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * function="基于Recycleview的万能适配器"
 * Created by wangmaobo on 2018/4/3.
 */
public abstract class BaseRecyclerViewAdapter<T>
        extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder> {
    protected List<T> mBeans;
    protected Context mContext;
    protected boolean mAnimateItems = false;
    protected int mLastAnimatedPosition = -1;

    public BaseRecyclerViewAdapter(Context context, List<T> beans) {
        mContext = context;
        mBeans = beans;
    }

    @Override
    public BaseRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(getItemLayoutId(viewType), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.ViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        onBindDataToView(holder, mBeans.get(position));
    }

    @Override
    public int getItemCount() {
        return mBeans.size();
    }

    public void add(T bean) {
        mBeans.add(bean);
        notifyDataSetChanged();
    }

    public void addAll(List<T> beans) {
        mBeans.addAll(beans);
        notifyDataSetChanged();
    }

    public void clear() {
        mBeans.clear();
        notifyDataSetChanged();
    }

    /**
     * item动画
     *
     * @param itemView
     * @param position
     */
    protected void runEnterAnimation(View itemView, int position) {
        if (!mAnimateItems) return;
        if (position > mLastAnimatedPosition) {
            mLastAnimatedPosition = position;
            itemView.setTranslationY(getScreenHeight());
            itemView.animate()
                    .translationY(50)
                    .setStartDelay(100)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(300)
                    .start();
        }
    }

    private int getScreenHeight() {
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final SparseArray<View> mViews;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
            this.itemView = itemView;
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(getAdapterPosition());
                }
            });
            setTextChangedListener(this,itemView);
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public void setText(int viewId, String text) {
            TextView tv = getView(viewId);
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

        public void setClickListener(int viewId) {
            View v = getView(viewId);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSingleViewClick(v, getAdapterPosition());
                }
            });
        }

        /**
         * 获取当前的位置
         *
         * @return
         */
        public int getCurrentPosition() {
            return getAdapterPosition();
        }
    }

    /**
     * 绑定布局id
     *
     * @param viewType
     * @return
     */
    protected abstract int getItemLayoutId(int viewType);

    /**
     * 绑定数据
     *
     * @param holder
     * @param t
     */
    protected abstract void onBindDataToView(ViewHolder holder, T t);

    /**
     * 支持多种ItemType
     *
     * @param position
     * @return
     */
    @Override
    public abstract int getItemViewType(int position);

    /**
     * ItemView里的某个子控件的单击事件(如果需要，重写此方法就行)
     * 只有注册了setClickListener才有效果
     *
     * @param position
     */
    protected void onSingleViewClick(View v, int position) {

    }

    /**
     * ItemView的单击事件(如果需要，重写此方法就行)
     *
     * @param position
     */
    protected void onItemClick(int position) {

    }
    /**
     * 为edittext添加文本监听(如果需要，重写此方法就行)
     * @param viewHolder
     * @param itemView
     */
    protected void setTextChangedListener(ViewHolder viewHolder, View itemView){

    }

}
