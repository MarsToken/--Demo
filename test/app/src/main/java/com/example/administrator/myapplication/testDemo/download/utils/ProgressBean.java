package com.example.administrator.myapplication.testDemo.download.utils;

/**
 * Created by Administrator on 2018/6/28.
 */
public class ProgressBean {
    private long bytesRead;
    private long contentLength;
    private boolean done;
    private boolean isError;

    public long getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }
}
