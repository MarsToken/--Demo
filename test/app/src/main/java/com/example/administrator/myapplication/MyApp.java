package com.example.administrator.myapplication;

import android.app.Application;
import android.os.Build;

import com.example.administrator.myapplication.mvvm.utils.smoothuiutlis.CheckUISmoothUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/3/28.
 */
public class MyApp extends Application {
    public static Retrofit retrofitInstance;
    //private static final String BASEURL = "http://140.143.138.233:8080/WebContent/";
    //private static final String BASEURL = "http://news.baidu.com/";
    private static final String BASEURL = "https://codeload.github.com/Tamicer/RetrofitClient/zip/master";

    @Override
    public void onCreate() {
        super.onCreate();
        //检测ui是否卡顿-阀值自定义
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            CheckUISmoothUtil.check_chore();
        }
    }

    public Retrofit getRetrofitInstance() {
        if (null == retrofitInstance) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .addInterceptor(interceptor);
            OkHttpClient client = builder.build();
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }
}
