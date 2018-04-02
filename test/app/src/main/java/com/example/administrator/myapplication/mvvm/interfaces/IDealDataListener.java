package com.example.administrator.myapplication.mvvm.interfaces;

/**
 * Created by Administrator on 2018/4/2.
 */
public interface IDealDataListener {
    /**
     * @param type        哪个view展示？
     * @param post_result 传递的数据
     */
    void onSuccess(int type, Object post_result);

    void onError(int type, Object post_result);

}
