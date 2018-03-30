package com.example.administrator.myapplication;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/3/28.
 */
public class MyApp extends Application {
    private static Retrofit retrofitInstance;
    //private static final String BASEURL = "http://140.143.138.233:8080/WebContent/";
    private static final String BASEURL = "http://news.baidu.com/";

    @Override
    public void onCreate() {
        super.onCreate();
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
