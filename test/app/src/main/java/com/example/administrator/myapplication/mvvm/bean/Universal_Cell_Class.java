package com.example.administrator.myapplication.mvvm.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/5.
 */
public class Universal_Cell_Class implements Serializable {

    public String cell_Type;//样式

    public Object cell_Value="";//值

    public Boolean is_Select=false;//是否可以点击


    public Object parameter;//用于保存的临时参数,用于向下一级传递

    public Universal_Cell_Class set_Cell_Type(String type)
    {
        cell_Type=type;
        return this;
    }

    public Universal_Cell_Class set_Cell_Value(Object value)
    {
        cell_Value=value;
        return this;
    }

    public Universal_Cell_Class set_Is_Select(Boolean select)
    {
        is_Select=select;
        return this;
    }

    public Universal_Cell_Class set_Parameter(Object canShu)
    {
        parameter=canShu;
        return this;
    }
}
