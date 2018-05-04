package com.example.administrator.myapplication.mvvm.viewmodel;

import com.example.administrator.myapplication.mvvm.bean.ShowConstant;
import com.example.administrator.myapplication.mvvm.interfaces.BaseViewModelInterface;
import com.example.administrator.myapplication.mvvm.bean.Universal_Cell_Class;
import com.example.administrator.myapplication.mvvm.interfaces.IDealDataListener;
import com.example.administrator.myapplication.mvvm.model.IShowModel;
import com.example.administrator.myapplication.mvvm.model.ShowModelImpl;
import com.example.administrator.myapplication.mvvm.view.IShowView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 负责处理view和model的逻辑-所有交互由操作由命令发射器发射命令
 * Created by Administrator on 2018/3/30.
 */
public class ShowViewModel extends BaseViewModel implements IDealDataListener {
    private IShowView mView;
    private IShowModel mModel;
    /**
     * 非主列表-零碎的view展示-如果有必要存储就用map
     */
    private HashMap<String, Universal_Cell_Class> mMap = new HashMap<>();
    public ArrayList<Universal_Cell_Class> data_Array_listView = new ArrayList<>();

    public ShowViewModel(BaseViewModelInterface modelInterface, IShowView view) {
        super(modelInterface);
        mView = view;
        mModel = new ShowModelImpl();
        getRecycleViewData();//测试fragment数据
    }

    public void sendRequest_RecycleView(String params) {
        mView.showWithStatus();
        mModel.sendRequest(ShowConstant.IntType.TAG_SHOW_RECYCLEVIEW, params, this);
    }

    public void sendRequest_Button(String params) {
        mView.showWithStatus();
        mModel.sendRequest(ShowConstant.IntType.TAG_SHOW_BUTTON, params, this);
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
    public void onSuccess(int type, Object json) {
        switch (type) {
            case ShowConstant.IntType.TAG_SHOW_RECYCLEVIEW:
                getRecycleViewData();
                my_FaSheQi.SendDircetive(ShowConstant.StringType.TAG_SHOW_RECYCLEVIEW, null);
                break;
            case ShowConstant.IntType.TAG_SHOW_BUTTON://非列表类可以考虑直接传递数值或者通过成员变量
                getListViewData();
                my_FaSheQi.SendDircetive(ShowConstant.StringType.TAG_SHOW_BUTTON, json);
                break;
        }
        mView.dismiss();
        mView.showSuccessWithStatus();
    }

    @Override
    public void onError(int type, Object json) {
        switch (type) {
            case ShowConstant.IntType.TAG_SHOW_RECYCLEVIEW:
                break;
            case ShowConstant.IntType.TAG_SHOW_BUTTON:
                break;
        }
        mView.dismiss();
        mView.showErrorWithStatus();
    }

    private void getRecycleViewData() {
        data_Array.clear();
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type1").set_Cell_Value
                ("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx").set_Is_Select(true).set_Parameter("我是文本，需要跳转吗？"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type2").set_Cell_Value("")
                .set_Is_Select(true).set_Parameter("我是图片，需要跳转吗？"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type2").set_Cell_Value("")
                .set_Is_Select(true).set_Parameter("我是图片，需要跳转吗？"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type1").set_Cell_Value
                ("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx").set_Is_Select(false));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type2").set_Cell_Value("")
                .set_Is_Select(true).set_Parameter("我是图片，需要跳转吗？"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type1").set_Cell_Value
                ("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx").set_Is_Select(false));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type2").set_Cell_Value("")
                .set_Is_Select(false).set_Parameter(""));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type1").set_Cell_Value
                ("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx").set_Is_Select(true).set_Parameter("我是文本，需要跳转吗？"));
    }

    private void getListViewData() {
        for (int i = 0; i < 18; i++) {
            data_Array_listView.add(new Universal_Cell_Class()
                    .set_Cell_Value("test test test " + "test test test" + i));
        }
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
