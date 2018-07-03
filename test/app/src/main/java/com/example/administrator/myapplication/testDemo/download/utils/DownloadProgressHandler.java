package com.example.administrator.myapplication.testDemo.download.utils;

import android.os.Looper;
import android.os.Message;

/**
 * Created by Administrator on 2018/6/29.
 */
public abstract class DownloadProgressHandler extends ProgressHandler{
    private static final int DOWNLOAD_PROGRESS = 1;
    protected ResponseHandler mHandler = new ResponseHandler(this, Looper.getMainLooper());

    @Override
    protected void sendMessage(ProgressBean progressBean) {
        mHandler.obtainMessage(DOWNLOAD_PROGRESS,progressBean).sendToTarget();

    }

    @Override
    protected void handleMessage(Message message){
        switch (message.what){
            case DOWNLOAD_PROGRESS:
                ProgressBean progressBean = (ProgressBean)message.obj;
                onProgress(progressBean.getBytesRead(), progressBean.getContentLength(), progressBean.isDone(), progressBean.isError());
        }
    }
}
