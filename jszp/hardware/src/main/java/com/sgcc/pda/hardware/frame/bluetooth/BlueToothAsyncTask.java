package com.sgcc.pda.hardware.frame.bluetooth;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.sgcc.pda.hardware.util.PeripheralParamUtil;

/**
 * @Author: GuangJie-Wang
 * @Date: 2016/9/22
 * @Time: 19:43
 */
public class BlueToothAsyncTask extends AsyncTask<Object, Void, byte[]> {

    private Context mContext;
    private Handler mHandler;
    public BlueToothManager blueTooth;
    private String name;

    public BlueToothAsyncTask(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
        name = PeripheralParamUtil.getPeripheralName(context);
    }

    @Override
    protected byte[] doInBackground(Object... params) {
        try {
            blueTooth = new BlueToothManager(mContext);
            blueTooth.setUiHandler(mHandler);
            blueTooth.setDeviceName(name);
            blueTooth.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
