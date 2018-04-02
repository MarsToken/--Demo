package com.example.administrator.myapplication.mvvm.bean;

/**
 * Created by wangmaobo on 2018/4/2.
 */
public class ShowConstant {
    public class IntType {
        /**
         * 网络请求
         */
        public static final int TAG_SHOW_RECYCLEVIEW = 0;
        public static final int TAG_SHOW_BUTTON = 1;
    }

    public class StringType {
        /**
         * 网络请求后更新UI 与上面一一对应
         */
        public static final String TAG_SHOW_RECYCLEVIEW = "success_recycleView";
        public static final String TAG_SHOW_BUTTON = "success_button";
    }
}
