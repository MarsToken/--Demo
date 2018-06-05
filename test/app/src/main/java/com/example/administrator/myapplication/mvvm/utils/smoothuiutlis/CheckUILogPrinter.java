package com.example.administrator.myapplication.mvvm.utils.smoothuiutlis;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by wangmaobo on 2018/5/7.
 */
public class CheckUILogPrinter {
    /**
     * 是否卡顿的阀值
     */
    public static final long TIME_BLOCK = 500l;
    /**
     * 打印的单例
     */
    private static CheckUILogPrinter mInstance = new CheckUILogPrinter();
    /**
     * 消息处理线程
     */
    private HandlerThread mHandlerThread = new HandlerThread("CheckUILogPrinter");
    private Handler mHandler;

    private CheckUILogPrinter() {
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    private static Runnable mLogRunnable = () -> {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] elements = Looper.getMainLooper().getThread().getStackTrace();
        for (StackTraceElement element : elements) {
            sb.append(element.toString() + "\n");
        }
        Log.e("TAG", sb.toString());
    };

    public static CheckUILogPrinter getInstance() {
        return mInstance;
    }

    /**
     * @return
     * @link #mHandler.hasCallbacks();
     * handler.hasCallbacks(Runnable r)为@hide方法
     */
    public boolean isMonitor() {
        boolean hasCall = false;
        Class<? extends Handler> aClass = mHandler.getClass();
        try {
            Method m = aClass.getMethod("hasCallbacks", Runnable.class);
            hasCall = (boolean) m.invoke(mHandler, mLogRunnable);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return hasCall;
    }

    public void startMonitor() {
        mHandler.postDelayed(mLogRunnable, TIME_BLOCK);
    }

    public void removeMonitor() {
        mHandler.removeCallbacks(mLogRunnable);
        //mHandlerThread.quit();
    }
}
