package com.xiao.project.ui.theme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xiao.project.R;

public class ThemeSwitchingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_switching);
        findViewById(R.id.tv_light).setOnClickListener(this);
        findViewById(R.id.tv_dark).setOnClickListener(this);
        findViewById(R.id.tv_intent).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_light:
                PrefsUtils.write(this,Config.THEME_CONFIG,true);
                ThemeUtil.reCreate(this);
                break;
            case R.id.tv_dark:
                PrefsUtils.write(this,Config.THEME_CONFIG,false);
                ThemeUtil.reCreate(this);
                break;
            case R.id.tv_intent:
                Intent intent=new Intent(this,SecondActivity.class);
                startActivity(intent);
                break;

        }
    }
}
