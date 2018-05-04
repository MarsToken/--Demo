package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bigkoo.svprogresshud.SVProgressHUD;

public class BaseActivity extends AppCompatActivity{
    protected int count = 1;
    protected SVProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
