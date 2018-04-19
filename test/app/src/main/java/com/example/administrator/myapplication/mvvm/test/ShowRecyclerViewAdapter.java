package com.example.administrator.myapplication.mvvm.test;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvvm.widget.base.adapter.BaseRecyclerViewAdapter;
import com.example.administrator.myapplication.mvvm.bean.Universal_Cell_Class;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */
public class ShowRecyclerViewAdapter extends BaseRecyclerViewAdapter<Universal_Cell_Class> {

    public ShowRecyclerViewAdapter(Context context, List<Universal_Cell_Class> beans) {
        super(context, beans);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.item_text;
        } else {
            return R.layout.item_image;
        }
    }

    @Override
    protected void onBindDataToView(BaseRecyclerViewAdapter<Universal_Cell_Class>.ViewHolder
                                            holder, Universal_Cell_Class bean) {
        if (bean.cell_Type.equals("cell_type1")) {
            holder.setText(R.id.tv, bean.cell_Value.toString());
            if (bean.is_Select) {//注册监听
                holder.setClickListener(R.id.tv);
            }
        } else if (bean.cell_Type.equals("cell_type2")) {
            holder.setImage(R.id.iv, R.mipmap.ic_launcher);
            if (bean.is_Select) {
                holder.setClickListener(R.id.iv);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mBeans.get(position).cell_Type.equals("cell_type1")) {
            return 0;
        } else if (mBeans.get(position).cell_Type.equals("cell_type2")) {
            return 1;
        }
        return -1;
    }

    @Override
    protected void onItemClick(int position) {
        Toast.makeText(mContext, "!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSingleViewClick(View v,int position) {
        if (mBeans.get(position).is_Select) {
            Toast.makeText(mContext, "pos=" + position + mBeans.get(position).parameter,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
