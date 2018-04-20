package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.myapplication.mvvm.utils.ScreenUtils;
import com.example.administrator.myapplication.mvvm.widget.AutoNewLineLayout;
import com.example.administrator.myapplication.mvvm.widget.TopTitleView;
import com.example.administrator.myapplication.mvvm.widget.base.BaseFreeLocationPopupWindow;
import com.example.administrator.myapplication.testlist.recyclerview.RecyclerListActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements TopTitleView.TopClickListener,
        AutoNewLineLayout.AutoViewClickListener {

    @BindView(R.id.iv_pop)
    ImageView mIvPop;
    @BindView(R.id.flow_layout)
    AutoNewLineLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFlowLayout.setItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("tag", count + "个");
    }

    private void testRetrofit() {
        MyApi api = ((MyApp) getApplication()).getRetrofitInstance().create(MyApi.class);
        Call<ResponseBody> call = api.getData();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        count = 599999;
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_pop:
                showPopup();
                break;
        }
    }

    private BaseFreeLocationPopupWindow mPopupWindow;

    private void showPopup() {
        mPopupWindow = new BaseFreeLocationPopupWindow(this) {
            @Override
            protected int getLayoutId() {
                return R.layout.layout_diy_pop;
            }
        };
        mIvPop.post(new Runnable() {
            @Override
            public void run() {
                int bottom = mIvPop.getBottom()
                        + ScreenUtils.getStatusHeight(MainActivity.this);
                mPopupWindow.showAtDIY(findViewById(R.id.iv_pop), 0, bottom);
            }
        });
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void itemClickListener(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, Main2Activity.class));
                break;
            case 1:
                RecyclerListActivity.launch(this);
                break;
            default:
                TestActivity.launch(this);
                break;
        }
    }
}
