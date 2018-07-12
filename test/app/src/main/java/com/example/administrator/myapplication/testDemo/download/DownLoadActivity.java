package com.example.administrator.myapplication.testDemo.download;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
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
    //    private String key_url = "timg?image&quality=100&size=b4000_4000&sec=1530522035&di=0915dfd1b3572a778b20e811a00f4faa&src=http://imgsrc
    // .baidu" +
    //            ".com/imgad/pic/item/bf096b63f6246b60553a62a0e1f81a4c510fa22a.jpg";
    private int progress;
    private ProgressBar progressBar;
    private Call<ResponseBody> call;
    private boolean isFinished = false;
    /**
     * 断点-已下载-总长度
     */
    private long breakPoints, totalBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        progressBar = findViewById(R.id.progressBar);
        initRetrofit();
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
                    if (null == call || call.isCanceled()) {
                        breakPoints = 0;
                        testDownLoad(0);
                    }
                } else {
                    if (isFinished) {//可以新建个文件，在里面做个finished记录，如果下载完了，同步此标记
                        Toast.makeText(this, "附件已下载完毕，请直接打开", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_pause:
                if (null != call && call.isExecuted()) {
                    call.cancel();
                }
                break;
            case R.id.btn_reStart:
                if (null != call && call.isCanceled()) {
                    testDownLoad(breakPoints);
                }
                break;
            case R.id.btn_open:
                if (isFinished) {
                    testOpenAttachments();
                }
                break;
        }
    }

    private void testOpenAttachments() {
        //apk,doc,gif,jpg,pdf,txt,wmv
        new OpenAttachmentsUtils().openFile(this, SDCardHelper.getFile("aaa", getFileName(key_url)));
    }

    private void testDownLoad(long startsPoint) {
        Log.e("tag", "startsPoint=" + startsPoint);
        //mProgressHUD.getProgressBar().setProgress(0);
        //mProgressHUD.showWithProgress("进度" + progress + "%", SVProgressHUD.SVProgressHUDMaskType.Black);
        DownloadApi downloadApi = mRetrofit.create(DownloadApi.class);
        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done, boolean isError) {

                //String result = String.format("进度:%d%%", (100 * bytesRead) / contentLength);
                //Log.e("onProgress", result);
                progress = (int) (100 * bytesRead / contentLength);
                //mProgressHUD.getProgressBar().setProgress(progress);
                //mProgressHUD.setText(result);
                if (progressBar.getMax() == 100) {
                    Log.e("tag", "progressBar init progress!");
                    progressBar.setMax((int) (contentLength / 1024));
                }
                progressBar.setProgress((int) (bytesRead + breakPoints) / 1024);
                if (done) {
                    progress = 0;
                    //mProgressHUD.dismiss();
                    isFinished = true;
                    Toast.makeText(DownLoadActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();
                }
                if (isError) {
                    Log.e("tag", "isError=true");
                    if (done) {
                        progress = 0;
                        //mProgressHUD.dismiss();
                        Toast.makeText(DownLoadActivity.this, "下载失败！", Toast.LENGTH_SHORT).show();
                    } else {
                        // 注意加上断点的长度-可能会导致调用多次，累加多次
                        totalBytes = bytesRead + breakPoints;
                        breakPoints = totalBytes;
                        Log.e("tag=暂停", "breakPoints=" + breakPoints);
                        Toast.makeText(DownLoadActivity.this, "暂停下载！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        call = downloadApi.retrofitDownload("bytes=" + startsPoint + "-", key_url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("tag", response.body().contentType().toString());
                new Thread(() -> {
                    InputStream is = response.body().byteStream();
                    //TODO: 2018/7/2 需要和后台沟通
                    SDCardHelper.saveFileToSDCardCustomDir(is, "aaa", getFileName(key_url), startsPoint, response.body().contentLength());
                }).start();
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

    private Retrofit mRetrofit;

    private void initRetrofit() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url);
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null)//已添加拦截器
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);
        mRetrofit = retrofitBuilder.client(builder.build()).build();
    }
}
