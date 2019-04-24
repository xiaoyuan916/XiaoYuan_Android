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
import android.util.Log;

import static com.xiao.project.conf.Constants.MSG_FROM_CLIENT;

/**
 * author:xuxiaoyuan
 * date:2019/4/24
 */
public class MessengerController {
    private static final String TAG = MessengerController.class.getSimpleName();

    private Messenger mClientMessenger;
    private Messenger mServiceMessenger;


    public MessengerController(Handler handler) {
        mClientMessenger = new Messenger(handler);
    }

    /**
     * 绑定service
     *
     * @param context   上下文
     * @param pageName  包名
     * @param className 类名
     */
    public void bindService(Context context, String pageName, String className) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(pageName, className));
        intent.setPackage(context.getPackageName());    //兼容Android 5.0
        context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 发送消息
     *
     * @param msg 消息
     */
    public void sendMessage(String msg) {
        Message clientMsg = Message.obtain();
        clientMsg.what = MSG_FROM_CLIENT;
        clientMsg.replyTo = mClientMessenger;
        Bundle data = new Bundle();
        data.putString("msg", msg);
        clientMsg.setData(data);
        if (mServiceMessenger != null) {
            try {
                mServiceMessenger.send(clientMsg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接定义
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        /**
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "绑定成功 name = " + name.toString());
            mServiceMessenger = new Messenger(service);
        }

        /**
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "绑定失败 name = " + name.toString());
            mServiceMessenger = null;
        }
    };

    /**
     * 解除绑定
     *
     * @param context
     */
    public void unbindService(Context context) {
        context.unbindService(mServiceConnection);
    }
}
