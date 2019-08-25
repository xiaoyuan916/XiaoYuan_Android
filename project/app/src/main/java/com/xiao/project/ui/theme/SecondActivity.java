package com.xiao.project.ui.theme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xiao.project.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
