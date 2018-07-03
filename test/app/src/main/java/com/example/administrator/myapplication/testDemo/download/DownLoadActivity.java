package com.example.administrator.myapplication.testDemo.download;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.administrator.myapplication.BaseActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvvm.utils.CommonUtils;
import com.example.administrator.myapplication.mvvm.utils.SDCardHelper;
import com.example.administrator.myapplication.testDemo.download.utils.DownloadApi;
import com.example.administrator.myapplication.testDemo.download.utils.DownloadProgressHandler;
import com.example.administrator.myapplication.testDemo.download.utils.OpenAttachmentsUtils;
import com.example.administrator.myapplication.testDemo.download.utils.ProgressHelper;

import java.io.File;
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
    private static String url = "http://msoftdl.360.cn";
    private String key_url = "/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk";
    //    private static String url = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/";
//    private String key_url = "timg?image&quality=100&size=b4000_4000&sec=1530522035&di=0915dfd1b3572a778b20e811a00f4faa&src=http://imgsrc.baidu" +
//            ".com/imgad/pic/item/bf096b63f6246b60553a62a0e1f81a4c510fa22a.jpg";
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
                //File file = new File("aaa", getFileName(key_url));
                File file = SDCardHelper.getFile("aaa", getFileName(key_url));
                if (!file.exists()) {
                    testDownLoad();
                } else {
                    Toast.makeText(this, "文件已存在", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_open:
                testOpenAttachments();
                break;
        }
    }

    private void testOpenAttachments() {
        //apk,doc,gif,jpg,pdf,txt,wmv
        new OpenAttachmentsUtils().openFile(this, SDCardHelper.getFile("aaa", getFileName(key_url)));
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
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS);
        DownloadApi downloadApi = retrofitBuilder.client(builder.build()).build().create(DownloadApi.class);
        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done, boolean isError) {
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
                if (isError) {
                    progress = 0;
                    mProgressHUD.dismiss();
                    Toast.makeText(DownLoadActivity.this, "下载失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Call<ResponseBody> call = downloadApi.retrofitDownload(key_url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    byte[] data = response.body().bytes();
                    SDCardHelper.saveFileToSDCardCustomDir(data, "aaa", getFileName(key_url));
                    Toast.makeText(DownLoadActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();
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

    private String getFileName(String pre) {
        // TODO: 2018/7/2 需要和后台沟通
        String changedUrl = "";
        if (pre.contains("/")) {
            changedUrl = pre.split("/")[pre.split("/").length - 1];
        } else {
            changedUrl = pre;
        }
        String result = CommonUtils._MD5(changedUrl) + "." + changedUrl.split("\\.")[changedUrl.split("\\.").length - 1];
        Log.e("tag", result);
        return result;
    }
}
