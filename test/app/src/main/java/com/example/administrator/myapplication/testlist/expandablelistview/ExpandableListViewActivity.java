package com.example.administrator.myapplication.testlist.expandablelistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.administrator.myapplication.BaseActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvvm.widget.TopTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableListViewActivity extends BaseActivity
        implements TopTitleView.TopClickListener {
    @BindView(R.id.ui_common_title)
    TopTitleView ui_CommonTitle;
    @BindView(R.id.ui_exList)
    ExpandableListView ui_ExList;
    private ChoosePostExListAdapter mAdapter;
    private List<GroupBean> mGroupBeans = new ArrayList<>();
    private List<List<ChildBean>> mChildBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);
        ButterKnife.bind(this);
        earlyInit();
        getData();
        lateInit();
        int a=9;
        int b=100;
        System.out.println(a + "+" + b + "=" + a + b);
    }

    private void lateInit() {
        mAdapter.notifyDataSetChanged();
        for (int i = 0; i < mGroupBeans.size(); i++) {
            ui_ExList.expandGroup(i, false);
        }
    }

    private void getData() {
        mGroupBeans.clear();
        mChildBeans.clear();
        mGroupBeans.add(new GroupBean("岗位1"));
        mGroupBeans.add(new GroupBean("岗位2"));
        mGroupBeans.add(new GroupBean("岗位3"));
        for (int i = 0; i < mGroupBeans.size(); i++) {
            List<ChildBean> beans = new ArrayList<>();
            int random = (int) (Math.random() * 10);
            for (int j = 0; j < random; j++) {
                beans.add(new ChildBean("技能" + j));
            }
            mChildBeans.add(beans);
        }
    }

    private void earlyInit() {
        mAdapter = new ChoosePostExListAdapter(this, mGroupBeans, mChildBeans);
        View header = LayoutInflater.from(this).inflate(R.layout.activity_choosepost_exlist_header, null);
        ui_ExList.addHeaderView(header);
        ui_ExList.setGroupIndicator(null);
        ui_ExList.setAdapter(mAdapter);
        ui_ExList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //Toast.makeText(ExpandableListViewActivity.this, mGroupBeans.get(groupPosition).name, Toast.LENGTH_SHORT).show();
                if (!parent.isGroupExpanded(groupPosition)) {
                    parent.expandGroup(groupPosition);
                    getData();
                    mAdapter.notifyDataSetChanged();
                } else {
                    parent.collapseGroup(groupPosition);
                }
                return true;
            }
        });
        ui_ExList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int
                    childPosition, long id) {
                Toast.makeText(ExpandableListViewActivity.this, groupPosition + "" + childPosition, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, ExpandableListViewActivity.class));
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}
