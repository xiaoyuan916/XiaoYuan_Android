package com.xiao.serialport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiao.serialport.service.SerialPortService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSerialPortService();
    }

    private void initSerialPortService() {
        startService(new Intent(this, SerialPortService.class));
    }
}
