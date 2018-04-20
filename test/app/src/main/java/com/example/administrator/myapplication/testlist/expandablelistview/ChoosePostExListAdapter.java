package com.example.administrator.myapplication.testlist.expandablelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */
public class ChoosePostExListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<GroupBean> mGroupBeans;
    private List<List<ChildBean>> mChildBeans;

    public ChoosePostExListAdapter(Context context,
                                   List<GroupBean> groupBeans,
                                   List<List<ChildBean>> childBeans) {
        mContext = context;
        mGroupBeans = groupBeans;
        mChildBeans = childBeans;
    }

    @Override
    public int getGroupCount() {
        //null == mGroupBeans ? 0 : mGroupBeans.size()
        return mGroupBeans.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //null == mChildBeans ? 0 : null == mChildBeans.get(groupPosition) ? 0 : mChildBeans.get(groupPosition).size()
        return mChildBeans.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupBeans.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildBeans.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup
                                     parent) {
        GroupViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout
                    .activity_choosepost_exlist_group, parent, false);
            viewHolder = new GroupViewHolder();
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.btn_skip = convertView.findViewById(R.id.btn_skip);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(mGroupBeans.get(groupPosition).name);
        viewHolder.btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "pos=" + groupPosition, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean
            isLastChild, View
            convertView, ViewGroup parent) {
        ChildViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout
                    .activity_choosepost_exlist_child, parent, false);
            viewHolder = new ChildViewHolder();
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.btn_delete = convertView.findViewById(R.id.btn_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(mChildBeans.get(groupPosition).get(childPosition).name);
        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, groupPosition + "" + childPosition, Toast.LENGTH_SHORT).show();
                mChildBeans.get(groupPosition).remove(childPosition);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    //        分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //        指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        TextView tv_name;
        Button btn_skip;

    }

    static class ChildViewHolder {
        TextView tv_name;
        ImageButton btn_delete;
    }
}
