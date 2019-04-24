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

    private Messenger mClientMessenger;
    private Messenger mServiceMessenger;

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_FROM_SERVICE:
                    Log.i(TAG,"Message from service = " + msg.getData().getString("msg"));
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        ButterKnife.bind(this);
        mClientMessenger = new Messenger(new MessengerHandler());
        bindService();
    }

    public void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.xxyuan.service", "com.xxyuan.service.MessengerService"));
        intent.setPackage(this.getPackageName());    //兼容Android 5.0
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @OnClick({R.id.bt_send_messenger_msg})
    public void onClickViewed(View v) {
        switch (v.getId()) {
            case R.id.bt_send_messenger_msg:
                sendMessage();
                break;
        }
    }

    public void sendMessage() {
        Message clientMsg = Message.obtain();
        clientMsg.what = MSG_FROM_CLIENT;
        clientMsg.replyTo = mClientMessenger;
        Bundle data = new Bundle();
        data.putString("msg","客户端消息");
        clientMsg.setData(data);
        if(mServiceMessenger != null) {
            try {
                mServiceMessenger.send(clientMsg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        /**
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"绑定成功 name = " + name.toString());
            mServiceMessenger = new Messenger(service);
        }

        /**
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG,"绑定失败 name = " + name.toString());
            mServiceMessenger = null;
        }
    };
}
