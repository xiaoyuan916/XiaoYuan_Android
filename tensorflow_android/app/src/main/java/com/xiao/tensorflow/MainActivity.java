package com.xiao.tensorflow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiao.tensorflow.activity.TensorFlowPhotoDistinguishActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.bt_photo_distinguish)
    Button btPhotoDistinguish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_photo_distinguish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_photo_distinguish:
                startActivity(new Intent(MainActivity.this, TensorFlowPhotoDistinguishActivity.class));
                break;
        }
    }
}
