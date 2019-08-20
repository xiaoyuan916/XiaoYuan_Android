package com.xiao.project.ui.chart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.xiao.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChartActivity extends AppCompatActivity {

    @BindView(R.id.wv_chart)
    WebView wvChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        WebSettings settings = wvChart.getSettings();
        settings.setJavaScriptEnabled(true);
        wvChart.loadUrl("file:///android_asset/chart/chart.html");
    }
}
