package com.example.administrator.myapplication.mvvm;

import android.os.Handler;

import com.example.administrator.myapplication.mvvm.util.BaseViewModelInterface;
import com.example.administrator.myapplication.mvvm.util.Universal_Cell_Class;

/**
 * Created by Administrator on 2018/3/30.
 */
public class ShowViewModel extends BaseViewModel {
    public ShowViewModel(BaseViewModelInterface modelInterface) {
        super(modelInterface);
    }

    public void sendRequest() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 3000);
    }

    private void getData() {
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type1").set_Cell_Value("北京博锐尚格节能技术有限公司").set_Is_Select(true).set_Parameter("next"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type2").set_Cell_Value("").set_Is_Select(true).set_Parameter("next"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type2").set_Cell_Value("").set_Is_Select(true).set_Parameter("next"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type1").set_Cell_Value("北京博锐尚格节能技术有限公司").set_Is_Select(false).set_Parameter("next"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type2").set_Cell_Value("北京博锐尚格节能技术有限公司").set_Is_Select(true).set_Parameter("next"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type1").set_Cell_Value("").set_Is_Select(false).set_Parameter("next"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type2").set_Cell_Value("北京博锐尚格节能技术有限公司").set_Is_Select(true).set_Parameter("next"));
        data_Array.add(new Universal_Cell_Class().set_Cell_Type("cell_type1").set_Cell_Value("").set_Is_Select(false).set_Parameter("next"));
        my_FaSheQi.SendDircetive("RecycleView", null);
    }

    public void changedText() {
        my_FaSheQi.SendDircetive("Button", null);
    }
}
