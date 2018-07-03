package com.example.administrator.myapplication.testDemo.download;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.myapplication.BaseActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.testDemo.download.utils.test.DownLoadController;
import com.example.administrator.myapplication.testDemo.download.utils.test.ProgressResponseBody;

import java.io.File;

public class DownLoadActivity2 extends BaseActivity implements ProgressResponseBody.ProgressListener {
    public static final String TAG = "MainActivity";
    public static final String PACKAGE_URL = "http://gdown.baidu.com/data/wisegame/df65a597122796a4/weixin_821.apk";

    private ProgressBar progressBar;
    private long breakPoints;
    private DownLoadController downloader;
    private File file;
    private long totalBytes;
    private long contentLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load2);
        progressBar = findViewById(R.id.progressBar);
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, DownLoadActivity2.class);
        activity.startActivity(intent);
    }

    public void downloadButtons(View view) {

        // 新下载前清空断点信息
        breakPoints = 0L;
        // 下载的位置
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "sample.apk");
        downloader = new DownLoadController(PACKAGE_URL, file, this);
        downloader.download(0L);
    }

    public void cancel_buttons(View view) {
        downloader.pause();
        Toast.makeText(this, "下载暂停", Toast.LENGTH_SHORT).show();
        // 存储此时的totalBytes，即断点位置。
        breakPoints = totalBytes;
    }

    public void continue_buttons(View view) {
        downloader.download(breakPoints);
    }

    @Override
    public void onPreExecute(long contentLength) {
        // 文件总长只需记录一次，要注意断点续传后的contentLength只是剩余部分的长度
        if (this.contentLength == 0L) {
            this.contentLength = contentLength;
            progressBar.setMax((int) (contentLength / 1024));
        }
    }

    @Override
    public void update(long totalBytes, boolean done) {
        // 注意加上断点的长度
        this.totalBytes = totalBytes + breakPoints;
        progressBar.setProgress((int) (totalBytes + breakPoints) / 1024);
//        if (done) {
//            // 切换到主线程
//            Observable.empty()
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .doOnCompleted(new Action0() {
//                        @Override
//                        public void call() {
//                            Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .subscribe();
//        }
    }
}
