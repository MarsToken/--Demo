package com.example.administrator.myapplication.mvvm.view;

/**
 * 简单的界面显示封装，交互类的用基类的发射器
 * Created by wangmaobo on 2018/4/2.
 */
public interface IShowView {

    /**
     * 进度条显隐
     */
    void show();

    void showWithMaskType();

    void showWithStatus();

    void showInfoWithStatus();

    void showSuccessWithStatus();

    void showErrorWithStatus();

    void showWithProgress();

    void dismiss();
}
