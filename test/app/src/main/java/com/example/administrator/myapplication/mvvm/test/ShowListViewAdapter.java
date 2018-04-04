package com.example.administrator.myapplication.mvvm.test;

import android.content.Context;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvvm.widget.base.adapter.BaseListAdapter;
import com.example.administrator.myapplication.mvvm.bean.Universal_Cell_Class;

import java.util.List;

/**
 * Created by wangmaobo on 2018/4/4.
 */
public class ShowListViewAdapter extends BaseListAdapter<Universal_Cell_Class> {
    public ShowListViewAdapter(Context context, List<Universal_Cell_Class> beans, int layoutId) {
        super(context, beans, layoutId);
    }

    @Override
    public void setContent(ViewHolder holder, Universal_Cell_Class item) {
        holder.setText(R.id.tv, item.cell_Value.toString());
    }
}
