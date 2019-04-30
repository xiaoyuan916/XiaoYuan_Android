package com.xiao.project.ui.mqtt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xiao.project.R;

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
        //注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initMqttService();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MqttMessage message) {
        try {
            String msg = new String(message.getPayload());
            Log.d(getClass().getSimpleName(), msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化mqttservice
     */
    private void initMqttService() {
        MqttService.actionStart(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MqttService.actionStop(this);
        //解除注册
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
