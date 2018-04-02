package com.example.administrator.myapplication.mvvm.viewmodel;

import com.example.administrator.myapplication.mvvm.interfaces.BaseViewModelInterface;
import com.example.administrator.myapplication.mvvm.interfaces.NetEvent;
import com.example.administrator.myapplication.mvvm.bean.Universal_Cell_Class;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/30.
 */
public class BaseViewModel implements NetEvent {
    public int netMobile;
    /**
     * 命令发射器
     */
    public BaseViewModelInterface my_FaSheQi;
    /**
     * UI对应的列表显示数据源
     */
    public ArrayList<Universal_Cell_Class> data_Array = new ArrayList<>();

    public BaseViewModel(BaseViewModelInterface modelInterface) {
        my_FaSheQi = modelInterface;
    }

    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
    }

    public boolean isHaveInternet() {
        return true;
    }

    public void submitOrderFunction() {//model delegate

    }
}
