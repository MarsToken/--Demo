package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.administrator.myapplication.mvvm.bean.ShowConstant;
import com.example.administrator.myapplication.mvvm.controller.ShowFragment;
import com.example.administrator.myapplication.mvvm.interfaces.BaseViewModelInterface;
import com.example.administrator.myapplication.mvvm.test.MyAdapater;
import com.example.administrator.myapplication.mvvm.test.MyPopupWindow;
import com.example.administrator.myapplication.mvvm.test.ShowListViewAdapter;
import com.example.administrator.myapplication.mvvm.test.ShowRecyclerViewAdapter;
import com.example.administrator.myapplication.mvvm.view.IShowView;
import com.example.administrator.myapplication.mvvm.viewmodel.ShowViewModel;
import com.example.administrator.myapplication.mvvm.widget.TopTitleView;
import com.example.administrator.myapplication.mvvm.widget.base.BaseDialog;
import com.example.administrator.myapplication.mvvm.widget.base.adapter.BaseFragmentAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * mvvm模式-test
 * 1.生命周期
 * 2.初始化UI
 * 3.交互监听
 * 4.展示数据
 *
 * @author wangmaobo
 * @date 2018/3/30
 */
public class Main2Activity extends BaseActivity
        implements BaseViewModelInterface,//发射器
        IShowView,//view层
        ShowFragment.OnFragmentInteractionListener,//fragment
        MyAdapater.ChildClickListener,//列表
        TopTitleView.TopClickListener,//标题
        View.OnClickListener,//popupWindow
        BaseDialog.AlertDialogListener {//dialog

    /**
     * view-适配器对应列表view
     **/
    @BindView(R.id.rv_list)
    RecyclerView rv;
    @BindView(R.id.btn_changedList)
    Button btn_changedList;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private BaseFragmentAdapter mAdapter_vp;
    private ShowRecyclerViewAdapter mAdapter_rv;
    @BindView(R.id.btn_changedSelf)
    Button btn_changedSelf;
    private ShowListViewAdapter mAdapter_lv;
    @BindView(R.id.lv_list)
    ListView lv_list;
    private MyPopupWindow mPopupWindow;

    /**
     * vm
     */
    private ShowViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        mViewModel = new ShowViewModel(this, this);
        mProgressHUD = new SVProgressHUD(this);
        count = 111;
        Log.e("tag", count + "个");
        initViewPager();
    }

    private void initViewPager() {
        final String[] title = new String[]{"新闻", "图片", "视频"};
        mAdapter_vp = new BaseFragmentAdapter(getSupportFragmentManager(), title) {
            @Override
            protected Fragment getFragment(int position) {
                return ShowFragment.newInstance(title[position], position + "");
            }
        };
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setAdapter(mAdapter_vp);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        mViewModel.clear();
    }

    @OnClick({R.id.btn_changedList, R.id.btn_changedSelf, R.id.btn_popup, R.id.btn_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_changedList:
                mViewModel.sendRequest_RecycleView("params");
                break;
            case R.id.btn_changedSelf:
                mViewModel.sendRequest_Button("params1");
                break;
            case R.id.btn_popup:
                showPopupWindow();
                break;
            case R.id.btn_dialog:
                showDialog();
                break;
        }
    }

    @Override
    public void SendDircetive(String name, Object params) {
        switch (name) {
            case ShowConstant.StringType.TAG_SHOW_RECYCLEVIEW:
                if (null == mAdapter_rv) {
                    rv.setLayoutManager(new LinearLayoutManager(this));
                    mAdapter_rv = new ShowRecyclerViewAdapter(this, mViewModel.data_Array);
                    rv.setAdapter(mAdapter_rv);
                } else {
                    mAdapter_rv.notifyDataSetChanged();
                }
                break;
            case ShowConstant.StringType.TAG_SHOW_BUTTON:
                btn_changedSelf.setText(params.toString());
                if (null == mAdapter_lv) {
                    mAdapter_lv = new ShowListViewAdapter(this, mViewModel.data_Array_listView,
                            R.layout.item_text);
                    lv_list.setAdapter(mAdapter_lv);
                } else {
                    mAdapter_lv.notifyDataSetChanged();
                }
                break;
            default:

                break;
        }
    }

    @Override
    public void click(int position, Object info) {
        Toast.makeText(this, "pos=" + position + info.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void show() {
        mProgressHUD.show();
    }

    @Override
    public void showWithMaskType() {
        mProgressHUD.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    @Override
    public void showWithStatus() {
        mProgressHUD.showWithStatus("loading...");
    }

    @Override
    public void showInfoWithStatus() {
        mProgressHUD.showInfoWithStatus("this is notice",
                SVProgressHUD.SVProgressHUDMaskType.Clear);
    }

    @Override
    public void showSuccessWithStatus() {
        mProgressHUD.showSuccessWithStatus("congratulation,submit successfully!");
    }

    @Override
    public void showErrorWithStatus() {
        mProgressHUD.showErrorWithStatus("May be that something is wrong!");
    }

    @Override
    public void showWithProgress() {//借助handler update progress-下载，上传
        mViewModel.updateProgress();
    }

    @Override
    public void dismiss() {
        mProgressHUD.dismissImmediately();
    }

    @Override
    public void showPopupWindow() {
        mPopupWindow = new MyPopupWindow(this, this, null);
        mPopupWindow.setListener(R.id.iv).showAtBottom(findViewById(R.id.root));
    }

    @Override
    public void showDialog() {
        new BaseDialog(this)
                .setAlertDialogListener(this)
                .show("I'm content", "I'm title", "取消", "确定");
    }

    /**
     * title
     */
    @Override
    public void onLeftClick() {
        Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRightClick() {
        Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {//popupWindow
            case R.id.iv:
                Toast.makeText(this, "确定了", Toast.LENGTH_SHORT).show();
                mPopupWindow.dismiss();
                break;
        }
    }

    /**
     * AlertDialog
     */
    @Override
    public void onCancelDialog() {
        Toast.makeText(this, "dialog is cancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCertainDialog() {
        Toast.makeText(this, "I'm certain!", Toast.LENGTH_SHORT).show();
    }

    /**
     * 与fragment之间的交互
     *
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * 本界面的入口
     *
     * @param activity
     * @param params
     */
    public static void launch(Activity activity, String... params) {
        activity.startActivity(new Intent(activity, Main2Activity.class));
    }
}
