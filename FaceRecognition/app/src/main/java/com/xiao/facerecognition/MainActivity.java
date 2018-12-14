package com.xiao.facerecognition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        startActivity(new Intent(MainActivity.this,
                com.xiao.facerecognition.facedetector.activity.MainActivity.class));
    }
}
