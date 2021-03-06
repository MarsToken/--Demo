package com.example.administrator.myapplication.testDemo.download.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by wangmaobo on 2018/6/28.
 */
public class ProgressHelper {
    private static ProgressHandler mProgressHandler;
    private static ProgressBean progressBean = new ProgressBean();

    public static OkHttpClient.Builder addProgress(OkHttpClient.Builder builder) {
        if (builder == null) {
            builder = new OkHttpClient.Builder();
        }
        final ProgressListener progressListener = new ProgressListener() {
            //该方法在子线程中运行
            @Override
            public void onProgress(long progress, long total, boolean done) {
                //Log.e("helper:progress:", String.format("%d%% done\n", (100 * progress) / total));
                if (mProgressHandler == null) {
                    return;
                }
                progressBean.setBytesRead(progress);
                progressBean.setContentLength(total);
                progressBean.setDone(done);
                progressBean.setError(false);
                mProgressHandler.sendMessage(progressBean);

            }

            @Override
            public void onError(boolean isError) {
                progressBean.setError(isError);
                mProgressHandler.sendMessage(progressBean);
            }
        };

        //添加拦截器，自定义ResponseBody，添加下载进度
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new DownLoadResponseBody(originalResponse.body(), progressListener))
                        .build();

            }
        });

        return builder;
    }

    public static void setProgressHandler(ProgressHandler progressHandler) {
        mProgressHandler = progressHandler;
    }
}
