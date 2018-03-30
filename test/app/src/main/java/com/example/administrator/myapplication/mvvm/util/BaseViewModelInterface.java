package com.example.administrator.myapplication.mvvm.util;

/**
 * Created by Administrator on 2018/3/30.
 */
public interface BaseViewModelInterface {
    /**
     * 在ViewModel初始化的过程中需要传入改接口的实现用于ViewModel向View层发布命令
     *
     * @param name   命令
     * @param params 附带的参数,一般情况下为Null
     */
    void SendDircetive(String name, Object params);
}
