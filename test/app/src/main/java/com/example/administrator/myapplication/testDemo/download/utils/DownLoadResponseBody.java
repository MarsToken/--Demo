package com.example.administrator.myapplication.testDemo.download.utils;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by wangmaobo on 2018/6/28.
 */
public class DownLoadResponseBody extends ResponseBody {
    private ResponseBody mResponseBody;
    private ProgressListener mProgressListener;
    private BufferedSource mBuffer;

    public DownLoadResponseBody(ResponseBody responseBody, ProgressListener listener) {
        mProgressListener = listener;
        mResponseBody = responseBody;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBuffer == null) {
            mBuffer = Okio.buffer(source(mResponseBody.source()));
        }
        return mBuffer;
    }

    private Source source(BufferedSource source) {
        return new ForwardingSource(source) {
            long totalBytes = 0;

            @Override
            public long read(Buffer sink, long byteCount) {
                // TODO: 2018/7/2 可能会报超时异常，需要异常捕捉下
                long eachBytes = 0;
                try {
                    eachBytes = super.read(sink, byteCount);
                    totalBytes += eachBytes != -1 ? eachBytes : 0;
                    mProgressListener.onProgress(totalBytes, mResponseBody.contentLength(), eachBytes == -1);
                    return eachBytes;
                } catch (IOException e) {
                    e.printStackTrace();
                    mProgressListener.onError(true);
                }
                return -1;
            }
        };
    }
}
