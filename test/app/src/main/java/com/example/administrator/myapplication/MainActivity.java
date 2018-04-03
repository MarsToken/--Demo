package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity implements GetDataListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new TestLongConnect().setListener(this);
        MyApi api = ((MyApp) getApplication()).getRetrofitInstance().create(MyApi.class);
        Call<ResponseBody> call = api.getData();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        count = 599999;
        TextView tv = findViewById(R.id.tv);
        tv.setText(count + "个");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("tag", count + "个");
    }

    @Override
    public void getData(Object xxx) {
        // TODO: 2018/3/2 处理数据，展示UI

    }

    public void onViewClicked(View view) {
        startActivity(new Intent(this, Main2Activity.class));
    }
}
