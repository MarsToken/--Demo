package com.example.administrator.myapplication.mvvm;

import android.content.Context;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvvm.base.BaseListAdapter;
import com.example.administrator.myapplication.mvvm.bean.Universal_Cell_Class;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */
public class ListViewAdapterSimple extends BaseListAdapter<Universal_Cell_Class> {

    public ListViewAdapterSimple(Context context, List beans, int layoutId) {
        super(context, beans, layoutId);
    }

    @Override
    public void setContent(ViewHolder holder, Universal_Cell_Class item) {
        holder.setText(R.id.tv, item.cell_Value.toString());
    }
}
