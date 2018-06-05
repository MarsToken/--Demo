package com.example.administrator.myapplication.mvvm.utils.smoothuiutlis;

import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.view.Choreographer;

/**
 * 备注，如果是死循环之类的或者循环耗时比较大的操作导致的UI卡顿：当堆栈中找到的方法不耗时的情况下，说明它被循环调用了
 * 比如recycleview没有被复用引发的ui卡顿
 * Created by wangmaobo on 2018/5/7.
 */
public class CheckUISmoothUtil {
    //Loop.loop()里的代码，核心是msg.target.dispatchMessage(msg);的前后
    private static final String START = ">>>>> Dispatching";
    private static final String END = "<<<<< Finished";

    /**
     * loop
     */
    public static void check() {
        Looper.getMainLooper().setMessageLogging(x -> {
            if (x.startsWith(START)) {
                CheckUILogPrinter.getInstance().startMonitor();
            }
            if (x.startsWith(END)) {
                CheckUILogPrinter.getInstance().removeMonitor();
            }
        });
    }

    /**
     * Choreographer-over api16
     * Android系统每隔16ms发出VSYNC信号，触发对UI进行渲染。SDK中包含了一个相关类，以及相关回调。
     * 理论上来说两次回调的时间周期应该在16ms，如果超过了16ms我们则认为发生了卡顿，我们主要就是利用两次回调间的时间周期来判断
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void check_chore() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                if (CheckUILogPrinter.getInstance().isMonitor()) {
                    CheckUILogPrinter.getInstance().removeMonitor();
                }
                CheckUILogPrinter.getInstance().startMonitor();
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }
}
