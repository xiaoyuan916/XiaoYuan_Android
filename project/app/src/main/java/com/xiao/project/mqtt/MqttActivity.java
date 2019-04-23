package com.xiao.project.mqtt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xiao.project.R;
import com.xiao.project.websocket.MQTTService;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MqttActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt);
        initData();
    }

    private void initData() {
        initMqttService();
    }



    /**
     * 初始化mqttservice
     */
    private void initMqttService() {
        Intent i = new Intent(this, MQTTService.class);
        startService(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(this, MQTTService.class);
        stopService(i);
    }
}
