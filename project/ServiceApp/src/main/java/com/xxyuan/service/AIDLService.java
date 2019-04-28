package com.xxyuan.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.xxyuan.service.aidl.IServiceAidlInterface;

public class AIDLService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }


    public AIDLService() {
    }

    private final IServiceAidlInterface.Stub aidlInterface = new IServiceAidlInterface.Stub() {
        @Override
        public String getString(String str) throws RemoteException {
            return "调用成功";
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        Log.d(getClass().getSimpleName(), "绑定成功");
        return aidlInterface;
    }
}
