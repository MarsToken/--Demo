package com.example.administrator.myapplication.mvvm.interfaces;

/**
 * Created by Administrator on 2018/3/30.
 */
public interface BaseViewModelInterface {
    /**
     * 在ViewModel初始化的过程中需要传入改接口的实现用于ViewModel向View层发布命令
     *
     * @param name   命名-格式：success(or failure)_view(具体view名字)
     * @param params 附带的参数,一般情况下为Null，比如传递给下个界面的信息
     */
    void SendDircetive(String name, Object params);
}
