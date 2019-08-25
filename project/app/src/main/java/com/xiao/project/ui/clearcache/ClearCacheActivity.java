package com.xiao.project.ui.clearcache;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xiao.project.R;
import com.xiao.project.utils.ClearCacheUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClearCacheActivity extends AppCompatActivity {

    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;
    @BindView(R.id.tv_cache_clear)
    TextView tvCacheClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);
        ButterKnife.bind(this);
        initData();
        clearCache();
    }

    private void initData() {
        tvCacheSize.setText(ClearCacheUtils.getAppClearSize(this));
    }

    private void clearCache() {

    }
}
