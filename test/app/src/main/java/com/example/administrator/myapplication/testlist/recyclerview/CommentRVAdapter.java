package com.example.administrator.myapplication.testlist.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvvm.widget.base.adapter.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by wmb on 2018/4/19.
 */
public class CommentRVAdapter extends BaseRecyclerViewAdapter<CommentBean> {
    public CommentRVAdapter(Context context, List<CommentBean> beans) {
        super(context, beans);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.activity_list_comment_item;
    }

    @Override
    protected void onBindDataToView(final ViewHolder holder, final CommentBean bean) {
        setListener(holder, bean);
        initUI(holder, bean);
    }

    private void initUI(ViewHolder holder, CommentBean bean) {
        holder.setText(R.id.ui_tv_name, bean.name);
        RatingBar bar = holder.getView(R.id.ui_ratingBar);
        LayerDrawable drawable = (LayerDrawable) bar.getProgressDrawable();
        drawable.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        bar.setRating(bean.score);
        if (bean.isEdit) {
            holder.setText(R.id.ui_et_comment, bean.comment);
            bar.setIsIndicator(false);
            holder.getView(R.id.ll_comment_tv).setVisibility(View.GONE);
            holder.getView(R.id.rl_comment).setVisibility(View.VISIBLE);
            holder.getView(R.id.rl_commit).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.ll_comment_tv).setVisibility(View.VISIBLE);
            holder.getView(R.id.rl_comment).setVisibility(View.GONE);
            holder.getView(R.id.rl_commit).setVisibility(View.GONE);
            holder.setText(R.id.ui_tv_comment, bean.comment);
            bar.setIsIndicator(true);
        }
        Log.e("tag", "init finished!");
    }

    private void setListener(final ViewHolder holder, final CommentBean bean) {
        final EditText et = holder.getView(R.id.ui_et_comment);
        //et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        final RatingBar rb = holder.getView(R.id.ui_ratingBar);
        holder.getView(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.isEdit = false;
                initUI(holder, bean);
                //notifyDataSetChanged();
            }
        });
        holder.getView(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.score = 0;
                bean.comment = "";
                Log.e("tag" + holder.getCurrentPosition(), "false clear all");
                initUI(holder, bean);
                //notifyDataSetChanged();
            }
        });
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                bean.score = rating;
                Log.e("tag" + holder.getCurrentPosition(), bean.name + "score=" + rating);
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("tag" + holder.getCurrentPosition(), "afterTextChanged="+s);
                int end = et.getText().toString().length();
                int max = 200;
                if (s.length() > max) {
                    s.delete(max, end);
                    et.setText(s);
                    et.setSelection(max);
                    Toast.makeText(mContext, "最多输入200个字符", Toast.LENGTH_SHORT).show();
                }
                bean.comment = s.toString();
                holder.setText(R.id.ui_tv_limit, s.length() + "/" + max);
                Log.e("tag" + holder.getCurrentPosition(), s.toString() + "来自EditText");
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
