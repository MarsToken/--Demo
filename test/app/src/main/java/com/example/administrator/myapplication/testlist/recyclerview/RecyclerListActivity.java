package com.example.administrator.myapplication.testlist.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.example.administrator.myapplication.BaseActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvvm.widget.TopTitleView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerListActivity extends BaseActivity implements TopTitleView.TopClickListener {

    @BindView(R.id.ui_rv_comment)
    RecyclerView ui_Rv_Comment;
    @BindView(R.id.ui_rv_header)
    RecyclerViewHeader ui_RvHeader;

    private CommentRVAdapter mAdapter_Comment;
    private ArrayList<CommentBean> mList_Comment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        earlyInitUI();
        initData();
        lateInitUI();
    }

    private void lateInitUI() {
        mAdapter_Comment.notifyDataSetChanged();
    }

    private void earlyInitUI() {
        ButterKnife.bind(this);
        ui_Rv_Comment.setLayoutManager(new LinearLayoutManager(this));
        mAdapter_Comment = new CommentRVAdapter(this, mList_Comment);
        ui_RvHeader.attachTo(ui_Rv_Comment);
        ui_Rv_Comment.setAdapter(mAdapter_Comment);
    }

    private void initData() {
        mList_Comment.add(new CommentBean("鲁班七号"));
        mList_Comment.add(new CommentBean("兰陵王"));
        mList_Comment.add(new CommentBean("阿珂"));
        mList_Comment.add(new CommentBean("嬴政"));
        mList_Comment.add(new CommentBean("黄忠"));
        mList_Comment.add(new CommentBean("刘备"));
        mList_Comment.add(new CommentBean("甄姬"));
    }

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, RecyclerListActivity.class));
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}
