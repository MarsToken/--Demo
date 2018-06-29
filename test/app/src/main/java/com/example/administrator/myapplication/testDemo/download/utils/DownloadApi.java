package com.example.administrator.myapplication.testDemo.download.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2018/6/28.
 */
public interface DownloadApi {
    @GET("/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk")
    Call<ResponseBody> retrofitDownload();
}
