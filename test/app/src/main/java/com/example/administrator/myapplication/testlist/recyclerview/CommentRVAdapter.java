package com.example.administrator.myapplication.testlist.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvvm.bean.Universal_Cell_Class;
import com.example.administrator.myapplication.mvvm.widget.base.adapter.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by wmb on 2018/4/19.
 */
public class CommentRVAdapter extends BaseRecyclerViewAdapter<Universal_Cell_Class> {
    public CommentRVAdapter(Context context, List<Universal_Cell_Class> beans) {
        super(context, beans);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.activity_list_comment_item;
        } else {
            return R.layout.activity_list_comment_item1;
        }
    }

    @Override
    protected void onBindDataToView(final ViewHolder holder, final Universal_Cell_Class bean) {
        if (bean.cell_Type.equals(Universal_Cell_Class.TYPE1)) {
            setListener(holder, bean);
        }
        initUI(holder, bean);
    }

    @SuppressLint("RestrictedApi")
    private void initUI(ViewHolder holder, Universal_Cell_Class bean) {
        CommentBean commentBean = (CommentBean) bean.cell_Value;
        holder.setText(R.id.ui_tv_name, commentBean.name);
        RatingBar bar = holder.getView(R.id.ui_ratingBar);
        LayerDrawable layerDrawable = null;
        if (bar.getProgressDrawable() instanceof LayerDrawable) {
            layerDrawable = (LayerDrawable) bar.getProgressDrawable();
        } else if (bar.getProgressDrawable() instanceof DrawableWrapper) {
            DrawableWrapper wrapper = (DrawableWrapper) bar.getProgressDrawable();
            if (wrapper.getWrappedDrawable() instanceof LayerDrawable) {
                layerDrawable = (LayerDrawable) wrapper.getWrappedDrawable();
            }
        }
        if (layerDrawable != null) {
            layerDrawable.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);//选中
            //layerDrawable.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);//默认
            //layerDrawable.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        }
        bar.setRating(commentBean.score);
        if (bean.cell_Type.equals(Universal_Cell_Class.TYPE1)) {
            holder.setText(R.id.ui_et_comment, commentBean.comment);
            bar.setIsIndicator(false);
            holder.getView(R.id.rl_comment).setVisibility(View.VISIBLE);
            holder.getView(R.id.rl_commit).setVisibility(View.VISIBLE);
        } else {
            holder.setText(R.id.ui_tv_comment, commentBean.comment);
            bar.setIsIndicator(true);
        }
//        if (commentBean.isEdit) {
//
//        } else {
//            holder.getView(R.id.rl_comment).setVisibility(View.GONE);
//            holder.getView(R.id.rl_commit).setVisibility(View.GONE);
//            holder.setText(R.id.ui_tv_comment, commentBean.comment);
//            bar.setIsIndicator(true);
//        }
        Log.e("tag", "init finished!");
    }

    private void setListener(final ViewHolder holder, final Universal_Cell_Class bean) {
        final CommentBean commentBean = (CommentBean) bean.cell_Value;
        final RatingBar rb = holder.getView(R.id.ui_ratingBar);
        holder.getView(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.cell_Type = Universal_Cell_Class.TYPE2;
                //initUI(holder, bean);
                notifyDataSetChanged();
            }
        });
        holder.getView(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentBean.score = 0;
                commentBean.comment = "";
                Log.e("tag" + holder.getCurrentPosition(), "false clear all");
                //initUI(holder, bean);
                notifyDataSetChanged();
            }
        });
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                commentBean.score = rating;
                Log.e("tag" + holder.getCurrentPosition(), commentBean.name + "score=" + rating);
            }
        });
//        final EditText et = holder.getView(R.id.ui_et_comment);
//        et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Log.e("tag" + holder.getCurrentPosition(), "afterTextChanged="+s);
//                int end = et.getText().toString().length();
//                int max = 200;
//                if (s.length() > max) {
//                    s.delete(max, end);
//                    et.setText(s);
//                    et.setSelection(max);
//                    Toast.makeText(mContext, "最多输入200个字符", Toast.LENGTH_SHORT).show();
//                }
//                bean.comment = s.toString();
//                holder.setText(R.id.ui_tv_limit, s.length() + "/" + max);
//                Log.e("tag" + holder.getCurrentPosition(), s.toString() + "来自EditText");
//            }
//        });
    }

    @Override
    public int getItemViewType(int position) {
        if (mBeans.get(position).cell_Type.equals(Universal_Cell_Class.TYPE1)) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    protected void setTextChangedListener(final ViewHolder holder, View itemView) {
        final EditText et = itemView.findViewById(R.id.ui_et_comment);
        if (null != et) {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.e("tag" + holder.getCurrentPosition(), "afterTextChanged=" + s);
                    int end = et.getText().toString().length();
                    int max = 200;
                    if (s.length() > max) {
                        s.delete(max, end);
                        et.setText(s);
                        et.setSelection(max);
                        Toast.makeText(mContext, "最多输入200个字符", Toast.LENGTH_SHORT).show();
                    }
                    ((CommentBean) mBeans.get(holder.getCurrentPosition()).cell_Value).comment = s.toString();
                    holder.setText(R.id.ui_tv_limit, s.length() + "/" + max);
                    Log.e("tag" + holder.getCurrentPosition(), s.toString() + "来自EditText");
                }
            });
        }
    }
}
