package com.example.administrator.myapplication.testDemo.download;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.administrator.myapplication.BaseActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvvm.utils.SDCardHelper;
import com.example.administrator.myapplication.testDemo.download.utils.DownloadApi;
import com.example.administrator.myapplication.testDemo.download.utils.DownloadProgressHandler;
import com.example.administrator.myapplication.testDemo.download.utils.OpenAttachmentsUtils;
import com.example.administrator.myapplication.testDemo.download.utils.ProgressHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownLoadActivity extends BaseActivity {
    //private String url = "https://codeload.github.com/Tamicer/RetrofitClient/zip/master";
    private String url = "http://msoftdl.360.cn";
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
    }

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, DownLoadActivity.class));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                testDownLoad();
                break;
            case R.id.btn_open:
                testOpenAttachments();
                break;
        }
    }

    private void testOpenAttachments() {
        //apk,doc,gif,jpg,pdf,txt,wmv
        new OpenAttachmentsUtils().openFile(this, SDCardHelper.getFile("aaa", "test.txt"));
    }

    private void testDownLoad() {
        mProgressHUD.getProgressBar().setProgress(0);
        mProgressHUD.showWithProgress("进度" + progress + "%", SVProgressHUD.SVProgressHUDMaskType.Black);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null)
                .addInterceptor(interceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);
        DownloadApi downloadApi = retrofitBuilder.client(builder.build()).build().create(DownloadApi.class);
        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                //Log.e("是否在主线程中运行", String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
                String result = String.format("进度:%d%%", (100 * bytesRead) / contentLength);
                Log.e("onProgress", result);
                //Log.e("done", "--->" + String.valueOf(done));
                progress = (int) (100 * bytesRead / contentLength);
                mProgressHUD.getProgressBar().setProgress(progress);
                mProgressHUD.setText(result);
                if (done) {
                    progress = 0;
                    mProgressHUD.dismiss();
                }
            }
        });
        Call<ResponseBody> call = downloadApi.retrofitDownload();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    byte[] data = response.body().bytes();
                    SDCardHelper.saveFileToSDCardCustomDir(data, "aaa", "test.apk");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
