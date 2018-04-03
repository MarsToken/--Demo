package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.administrator.myapplication.mvvm.MyAdapater;
import com.example.administrator.myapplication.mvvm.ShowRecyclerViewAdapter;
import com.example.administrator.myapplication.mvvm.bean.ShowConstant;
import com.example.administrator.myapplication.mvvm.view.IShowView;
import com.example.administrator.myapplication.mvvm.viewmodel.ShowViewModel;
import com.example.administrator.myapplication.mvvm.interfaces.BaseViewModelInterface;

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
public class Main2Activity extends BaseActivity implements BaseViewModelInterface, IShowView,
        MyAdapater.ChildClickListener {

    /**
     * view
     **/
    @BindView(R.id.rv_list)
    RecyclerView rv;
    @BindView(R.id.btn_changedList)
    Button btn_changedList;
    @BindView(R.id.btn_changedSelf)
    Button btn_changedSelf;
    private SVProgressHUD mProgressHUD;
    /**
     * vm
     */
    private ShowViewModel mViewModel;
    private ShowRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        mViewModel = new ShowViewModel(this, this);
        mProgressHUD = new SVProgressHUD(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.clear();
    }

    @OnClick({R.id.btn_changedList, R.id.btn_changedSelf})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_changedList:
                mViewModel.sendRequest_RecycleView("params");
                break;
            case R.id.btn_changedSelf:
                mViewModel.sendRequest_Button("params1");
                break;
        }
    }

    @Override
    public void SendDircetive(String name, Object params) {
        switch (name) {
            case ShowConstant.StringType.TAG_SHOW_RECYCLEVIEW:
                if (null == mAdapter) {
                    rv.setLayoutManager(new LinearLayoutManager(this));
                    mAdapter = new ShowRecyclerViewAdapter(this, mViewModel.data_Array);
                    rv.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case ShowConstant.StringType.TAG_SHOW_BUTTON:
                btn_changedSelf.setText(params.toString());
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
        mProgressHUD.showInfoWithStatus("this is notice", SVProgressHUD.SVProgressHUDMaskType
                .Clear);
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
}
