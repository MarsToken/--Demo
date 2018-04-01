package com.example.administrator.myapplication.mvvm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvvm.util.Universal_Cell_Class;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/3/30.
 */
public class MyAdapater extends RecyclerView.Adapter<MyAdapater.ViewHolder> implements View
        .OnClickListener {
    private ArrayList<Universal_Cell_Class> mList;
    private RecyclerView mRecycleView;
    private Context mContext;
    private ChildClickListener mListener;

    public MyAdapater(Context context, ArrayList<Universal_Cell_Class> list) {
        mList = list;
        mContext = context;
        if (mContext instanceof ChildClickListener) {
            mListener = (ChildClickListener) mContext;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecycleView = recyclerView;
    }

    @Override
    public MyAdapater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_text, parent, false);
            view.findViewById(R.id.tv).setOnClickListener(this);
            view.setTag("cell_type1");
        } else if (viewType == 1) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
            view.findViewById(R.id.iv).setOnClickListener(this);
            view.setTag("cell_type2");//子控件的点击事件
        }
        return new ViewHolder(view, mList.get(viewType).cell_Type);
    }

    @Override
    public void onBindViewHolder(MyAdapater.ViewHolder holder, int position) {
        Universal_Cell_Class bean = mList.get(position);
        if (bean.cell_Type.equals("cell_type1")) {
            holder.textView.setText(bean.cell_Value.toString());
        } else if (bean.cell_Type.equals("cell_type2")) {
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Universal_Cell_Class bean = mList.get(position);
        if (bean.cell_Type.equals("cell_type1")) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        //有风险，切记不要再添加tag
        int position = 0;
        while (null == v.getTag()) {
            v = (View) v.getParent();
        }
        //补丁
        if (v.getTag().equals("cell_type1")
                || v.getTag().equals("cell_type2")) {
            position = mRecycleView.getChildAdapterPosition(v);
        }
        if (null != mListener && mList.get(position).is_Select) {
            mListener.click(position, mList.get(position).parameter);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        private ImageView imageView;

        public ViewHolder(View itemView, String viewType) {
            super(itemView);
            if (viewType.equals("cell_type1")) {
                textView = itemView.findViewById(R.id.tv);
            } else if (viewType.equals("cell_type2")) {
                imageView = itemView.findViewById(R.id.iv);
            }
        }
    }

    public interface ChildClickListener {
        void click(int position, Object info);
    }
}
