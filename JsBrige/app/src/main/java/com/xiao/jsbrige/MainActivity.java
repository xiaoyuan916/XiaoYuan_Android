package com.xiao.jsbrige;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiao.jsbrige.activity.XiaoDemoActivity;
import com.xiao.jsbrige.activity.XiaoVueProActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    private final String TAG = "MainActivity";

    @BindView(R.id.bt_demo)
    Button btDemo;
    @BindView(R.id.bt_vue_pro)
    Button btVuePro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_demo,R.id.bt_vue_pro})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_demo:
                startActivity(new Intent(MainActivity.this, XiaoDemoActivity.class));
                break;
            case R.id.bt_vue_pro:
                startActivity(new Intent(MainActivity.this, XiaoVueProActivity.class));
                break;
            default:
                break;
        }
    }

}
