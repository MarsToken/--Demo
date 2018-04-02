package com.example.administrator.myapplication.mvvm.model;

import android.os.Handler;
import android.text.TextUtils;

import com.example.administrator.myapplication.mvvm.bean.ShowConstant;
import com.example.administrator.myapplication.mvvm.interfaces.IDealDataListener;

/**
 * 数据相关操作-发送网络请求，io流读写
 * Created by Administrator on 2018/4/2.
 */
public class ShowModelImpl implements IShowModel {

    @Override
    public void sendRequest(final int type, final String params, final IDealDataListener listener) {
        switch (type) {
            case ShowConstant.TAG_SHOW_RECYCLEVIEW:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(params)) {
                            listener.onSuccess(type, "json");
                        } else {
                            listener.onError(type, "json");
                        }
                    }
                }, 1000);
                break;
            case ShowConstant.TAG_SHOW_BUTTON:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(params)) {
                            listener.onSuccess(type, "json");
                        } else {
                            listener.onError(type, "json");
                        }
                    }
                }, 1000);
                break;
        }
    }

//    @Override
//    public void sendRequest_Button(final int type, final String params, final IDealDataListener
// listener) {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (!TextUtils.isEmpty(params)) {
//                    listener.onSuccess(type);
//                } else {
//                    listener.onError(type);
//                }
//            }
//        }, 1000);
//    }
}
