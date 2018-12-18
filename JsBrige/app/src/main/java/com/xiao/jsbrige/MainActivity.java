package com.xiao.jsbrige;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiao.jsbrige.activity.XiaoDemoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    private final String TAG = "MainActivity";

    @BindView(R.id.bt_demo)
    Button btDemo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_demo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_demo:
                startActivity(new Intent(MainActivity.this, XiaoDemoActivity.class));
                break;
            default:
                break;
        }
    }

}
