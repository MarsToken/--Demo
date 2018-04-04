package com.example.administrator.myapplication.mvvm.view;

/**
 * 简单的界面交互封装，设计到model层的用基类的发射器
 * 命名规范-控件
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

    /**
     * 不需要经过model拉取数据
     */
    void showPopupWindow();

    void showDialog();
}
