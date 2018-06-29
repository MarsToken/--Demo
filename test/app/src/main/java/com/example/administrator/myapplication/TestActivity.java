package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.mvvm.controller.ShowFragment;
import com.example.administrator.myapplication.mvvm.widget.RichTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity implements ShowFragment.OnFragmentInteractionListener {

    @BindView(R.id.tv_title)
    RichTextView tvTitle;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, TestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
//        LayerDrawable drawable = (LayerDrawable) ratingBar.getProgressDrawable();
//        drawable.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.e("tag", "rating=" + rating);
                ratingBar.setRating(rating);
            }
        });
        Log.e("tag", "22小时后结束".split("小")[0]);

        TextView orderTypeNameLabel1 = findViewById(R.id.orderTypeNameLabel1);
        orderTypeNameLabel1.setClickable(true);
        orderTypeNameLabel1.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString sp = new SpannableString("这句话中有百度超链接,有高亮显示，这样，或者这样，还有斜体.");
        View.OnClickListener l = new View.OnClickListener() {
            //如下定义自己的动作
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "Click Success", Toast.LENGTH_SHORT).show();
                //在这里就可以做跳转到activity或者弹出对话框的操作了
                Log.e("tag", "success!");
            }
        };
        View.OnLongClickListener ll = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("tag", "success!");
                return false;
            }
        };

        int start = 0;
        int end = 3;
        sp.setSpan(new ClickableL(ll), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        orderTypeNameLabel1.setText(sp);
        Log.e("tag", "123456".substring(0, 2));
    }

    class Clickable extends ClickableSpan implements View.OnClickListener {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }
    }

    class ClickableL extends ClickableSpan  implements View.OnLongClickListener{
        private final View.OnLongClickListener mListener;

        public ClickableL(View.OnLongClickListener l) {
            mListener = l;
        }


        @Override
        public void onClick(View widget) {
            mListener.onLongClick(widget);
        }

        @Override
        public boolean onLongClick(View v) {

            return false;
        }
    }

    private void testRichText() {
        //example1
        tvTitle.setText("你好啊，小美");
        for (int i = 0; i < 2; i++) {
            if (i == 0) tvTitle.setSpan(0, 3, R.style.style0);
            if (i == 1) tvTitle.setSpan(4, 6, R.style.style1);
        }
        tvTitle.show();
        //example2
        tvTitle.setText("你好啊，小美").setSpan(0, 3, R.style.style0).setSpan(4, 6, R.style.style1).show();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick({R.id.tv_popSoftInput, R.id.btn_ui_smooth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_popSoftInput:
                Log.e("tag", "click!");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.btn_ui_smooth:

                try {
                    Thread.sleep(20);
                    Thread.sleep(980);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
