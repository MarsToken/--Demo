package com.example.administrator.myapplication.mvvm.viewmodel;

import com.example.administrator.myapplication.mvvm.bean.ShowConstant;
import com.example.administrator.myapplication.mvvm.interfaces.BaseViewModelInterface;
import com.example.administrator.myapplication.mvvm.bean.Universal_Cell_Class;
import com.example.administrator.myapplication.mvvm.interfaces.IDealDataListener;
import com.example.administrator.myapplication.mvvm.model.IShowModel;
import com.example.administrator.myapplication.mvvm.model.ShowModelImpl;
import com.example.administrator.myapplication.mvvm.view.IShowView;

/**
 * 负责处理view和model的逻辑-所有交互由操作由命令发射器发射命令
 * Created by Administrator on 2018/3/30.
 */
public class ShowViewModel extends BaseViewModel implements IDealDataListener {
    private IShowView mView;
    private IShowModel mModel;

    public ShowViewModel(BaseViewModelInterface modelInterface, IShowView view) {
        super(modelInterface);
        mView = view;
        mModel = new ShowModelImpl();
    }

    public void sendRequest_RecycleView(String params) {
        mView.showWithStatus();
        mModel.sendRequest(ShowConstant.TAG_SHOW_RECYCLEVIEW, params, this);
    }

    public void sendRequest_Button(String params) {
        mView.showWithStatus();
        mModel.sendRequest(ShowConstant.TAG_SHOW_BUTTON, params, this);
    }

    /**
     * 模拟更新进度
     */
    public void updateProgress() {

    }

    /**
     * model返回的数据处理
     */
    @Override
    public void onSuccess(int type,Object json) {
        switch (type) {
            case ShowConstant.TAG_SHOW_RECYCLEVIEW:
                getData();
                my_FaSheQi.SendDircetive("success_recycleView", null);
                break;
            case ShowConstant.TAG_SHOW_BUTTON://非列表类可以考虑直接传递数值或者通过成员变量
                my_FaSheQi.SendDircetive("success_button", json);
                break;
        }
        mView.dismiss();
        mView.showSuccessWithStatus();
    }

    @Override
    public void onError(int type,Object json) {
        switch (type) {
            case ShowConstant.TAG_SHOW_RECYCLEVIEW:
                break;
            case ShowConstant.TAG_SHOW_BUTTON:
                break;
        }
        mView.dismiss();
        mView.showErrorWithStatus();
    }

    private void getData() {
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type1").set_Cell_Value
                ("北京博锐尚格节能技术有限公司").set_Is_Select(true).set_Parameter("我是文本，需要跳转吗？"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type2").set_Cell_Value("")
                .set_Is_Select(true).set_Parameter("我是图片，需要跳转吗？"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type2").set_Cell_Value("")
                .set_Is_Select(true).set_Parameter("我是图片，需要跳转吗？"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type1").set_Cell_Value
                ("北京博锐尚格节能技术有限公司").set_Is_Select(false));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type2").set_Cell_Value("")
                .set_Is_Select(true).set_Parameter("我是图片，需要跳转吗？"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type1").set_Cell_Value
                ("北京博锐尚格节能技术有限公司").set_Is_Select(false));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type2").set_Cell_Value("")
                .set_Is_Select(false).set_Parameter(""));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type1").set_Cell_Value
                ("北京博锐尚格节能技术有限公司").set_Is_Select(true).set_Parameter("我是文本，需要跳转吗？"));
    }

    public void clear() {
        if (null != mView) {
            mView = null;
        }
        if (null != mModel) {
            mModel = null;
        }
    }
}