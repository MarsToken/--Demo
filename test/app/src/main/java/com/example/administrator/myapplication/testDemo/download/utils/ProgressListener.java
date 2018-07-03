package com.example.administrator.myapplication.testDemo.download.utils;

/**
 * Created by wangmaobo on 2018/6/28.
 */
public interface ProgressListener {
    /**
     * @param progress
     * @param total
     * @param isFinished
     */
    void onProgress(long progress, long total, boolean isFinished);

    void onError(boolean isError);
}
