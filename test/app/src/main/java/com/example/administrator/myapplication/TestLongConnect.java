package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2018/3/2.
 */
public class TestLongConnect {
    private GetDataListener mListener;

    public void setListener(GetDataListener listener) {
        mListener = listener;
    }

    /**
     * @param response 网络返回的数据
     */
    private void onSuccess(Object response) {
        //模拟长连接请求成功后后台返回的接口回调
        //...源码用的retrofit
        if (null != mListener) {
            mListener.getData(response);
        }
    }
}
