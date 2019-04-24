package com.xiao.project.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xiao.project.R;
import com.xiao.project.conf.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xiao.project.conf.Constants.MSG_FROM_CLIENT;
import static com.xiao.project.conf.Constants.MSG_FROM_SERVICE;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = MessengerActivity.class.getSimpleName();

    @BindView(R.id.bt_send_messenger_msg)
    Button btSendMessengerMsg;
    private MessengerController mController;


    private class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_FROM_SERVICE:
                    Log.i(TAG, "Message from service = " + msg.getData().getString("msg"));
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        ButterKnife.bind(this);
        bindService();
    }

    public void bindService() {
        mController = new MessengerController(new MessengerHandler());
        String pageName="com.xxyuan.service";
        String className="com.xxyuan.service.MessengerService";
        mController.bindService(this,pageName,className);
    }

    @OnClick({R.id.bt_send_messenger_msg})
    public void onClickViewed(View v) {
        switch (v.getId()) {
            case R.id.bt_send_messenger_msg:
                mController.sendMessage("客户端消息");
                break;
        }
    }



    @Override
    protected void onDestroy() {
        mController.unbindService(this);
        super.onDestroy();
    }

}
