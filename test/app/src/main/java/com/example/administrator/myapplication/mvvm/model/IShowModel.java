package com.example.administrator.myapplication.mvvm.model;

import com.example.administrator.myapplication.mvvm.interfaces.IDealDataListener;

/**
 * Created by Administrator on 2018/4/2.
 */
public interface IShowModel {
    void sendRequest(int type, String params, IDealDataListener listener);

    //void sendRequest_Button(int type, String params, IDealDataListener listener);
}
