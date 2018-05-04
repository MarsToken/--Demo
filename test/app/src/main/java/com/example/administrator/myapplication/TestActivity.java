package com.example.administrator.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RatingBar;

import com.example.administrator.myapplication.mvvm.controller.ShowFragment;
import com.example.administrator.myapplication.mvvm.widget.RichTextView;

import java.util.Timer;
import java.util.TimerTask;

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

    @SuppressLint("WrongViewCast")
    @OnClick(R.id.tv_popSoftInput)
    public void onViewClicked() {
        Log.e("tag", "click!");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

}
