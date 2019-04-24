package com.xxyuan.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import static com.xxyuan.service.Constants.MSG_FROM_CLIENT;
import static com.xxyuan.service.Constants.MSG_FROM_SERVICE;


public class MessengerService extends Service {
    private final static String TAG = "MessengerService";

    private Messenger mMessenger = new Messenger(new MessengerHandler());

    public MessengerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "绑定成功！");
        return mMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    Log.i(TAG,"Message from client = " + msg.getData().getString("msg"));
                    Message serviceMsg = Message.obtain();
                    serviceMsg.what = MSG_FROM_SERVICE;
                    Bundle data = new Bundle();
                    data.putString("msg","service端消息");
                    serviceMsg.setData(data);
                    if(msg.replyTo != null){
                        try {
                            msg.replyTo.send(serviceMsg);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                default:
                    //do nothing
            }
        }
    }
}
