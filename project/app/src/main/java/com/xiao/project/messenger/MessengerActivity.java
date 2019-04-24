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

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = MessengerActivity.class.getSimpleName();
    @BindView(R.id.bt_send_messenger_msg)
    Button btSendMessengerMsg;

    private Messenger mGetReplyMessenger = new Messenger(new MessageHandler());
    private Messenger mService;

    private class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_FROM_SERVICE:
                    Log.d(TAG, "received msg form service: msg = [" + msg.getData().getString(Constants.MSG_KEY) + "]");
                    Toast.makeText(MessengerActivity.this, "received msg form service: msg = [" + msg.getData().getString(Constants.MSG_KEY) + "]", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
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
        Intent intent = new Intent();
        intent.setAction("com.xxyuan.aidl.calc");
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
        Message msg = Message.obtain(null, Constants.MSG_FROM_CLIENT);
        Bundle data = new Bundle();
        data.putString(Constants.MSG_KEY, "Hello! This is client.");
        msg.setData(data);
        msg.replyTo = mGetReplyMessenger;
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
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
            mService = new Messenger(service);
            Log.d(TAG,"连接成功");
        }

        /**
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
}
