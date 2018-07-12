package com.example.administrator.myapplication.testDemo.highcharts;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.myapplication.BaseActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.testDemo.highcharts.bean.JsData;
import com.google.gson.Gson;

import java.util.Random;

public class HighChartsActivity extends BaseActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_charts);
        webView = findViewById(R.id.webView);
        // 启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheMaxSize(8 * 1024 * 1024);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!webView.getSettings().getLoadsImagesAutomatically()) {
                    webView.getSettings().setLoadsImagesAutomatically(true);
                }
            }
        });
        loadUrl("file:///android_asset/index.html");
    }

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, HighChartsActivity.class));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                loadUrl("javascript:refresh('" + getJsData() + "')");
                break;
        }
    }

    private void loadUrl(String url) {
        runOnUiThread(() -> webView.loadUrl(url));
    }

    private String getJsData() {
        JsData data = new JsData();
        data.categories = new String[]{"春", "夏", "秋", "冬"};
        data.series = new JsData.YData[2];
        Random random = new Random();
        for (int i = 0; i < data.series.length; i++) {
            data.series[i] = new JsData.YData();
            data.series[i].name = "Name" + (i + 1);
            int j = i;
            data.series[i].data = new int[]{100 * j + random.nextInt(100), 100 * j + random.nextInt(100), 100 * j + random.nextInt(100), 100 * j + random.nextInt(100)};
        }
        String json = new Gson().toJson(data);
        System.out.println("json = " + json);
        return json;
    }
}
