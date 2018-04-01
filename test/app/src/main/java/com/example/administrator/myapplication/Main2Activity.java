package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapplication.mvvm.MyAdapater;
import com.example.administrator.myapplication.mvvm.ShowViewModel;
import com.example.administrator.myapplication.mvvm.util.BaseViewModelInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * mvvm模式-test
 *
 * @author wangmaobo
 * @date 2018/3/30
 */
public class Main2Activity extends AppCompatActivity
        implements BaseViewModelInterface,
        MyAdapater.ChildClickListener {

    /**
     * view
     **/
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.btn1)
    Button btn1;

    /**
     * vm
     */
    private ShowViewModel mViewModel;
    private MyAdapater mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        mViewModel = new ShowViewModel(this);
    }

    @OnClick({R.id.btn, R.id.btn1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                mViewModel.sendRequest();
                break;
            case R.id.btn1:
                mViewModel.changedText();
                break;
        }
    }

    @Override
    public void SendDircetive(String name, Object params) {
        switch (name) {
            case "RecycleView":
                if (null == mAdapter) {
                    rv.setLayoutManager(new LinearLayoutManager(this));
                    mAdapter = new MyAdapater(this, mViewModel.data_Array);
                    rv.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case "Button":
                btn1.setText("test");
                break;
            case "TestView":

                break;
        }
    }

    @Override
    public void click(int position, Object info) {
        Toast.makeText(this, "pos=" + position + info.toString(), Toast.LENGTH_SHORT).show();
    }
}
