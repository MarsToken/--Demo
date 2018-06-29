package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bigkoo.svprogresshud.SVProgressHUD;

public class BaseActivity extends AppCompatActivity{
    protected int count = 1;
    protected SVProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mProgressHUD = new SVProgressHUD(this);
        //String str = "1sdf2sdf3sdf4sdf5sdf6dsf7fsdf7";
        //int index = str.indexOf("6", 25);//如果要查第二个需要对应索引+1
        //Log.e("tag", "index=" + index);
    }
}
