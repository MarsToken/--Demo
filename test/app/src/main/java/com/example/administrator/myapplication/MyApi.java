package com.example.administrator.myapplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2018/3/21.
 */
public interface MyApi {
    @GET("index.jsp")
    Call<ResponseBody> getData();
}

