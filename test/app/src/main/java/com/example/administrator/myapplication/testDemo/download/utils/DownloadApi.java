package com.example.administrator.myapplication.testDemo.download.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2018/6/28.
 */
public interface DownloadApi {
    //    @GET("/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk")
//    Call<ResponseBody> retrofitDownload();
    @Streaming//大文件需要，否则可能会oom，边下载边保存（异步）
    @GET
    Call<ResponseBody> retrofitDownload(@Header("RANGE") String start, @Url String url);
}
