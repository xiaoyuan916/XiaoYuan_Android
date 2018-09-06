package com.sgcc.pda.hardware.frame.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.sgcc.pda.hardware.event.BlueConnectedEvent;
import com.sgcc.pda.hardware.event.BlueEntityEvent;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.EventManager;
import com.sgcc.pda.hardware.util.ToastManager;
import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.sdk.utils.SharepreferenceUtil;
import com.sgcc.pda.sdk.utils.StringUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BlueToothManager {

    private static final String TAG = "BlueToothManager";
    private Handler mHandler;
    private BluetoothAdapter bluetooth_adapter = null;// 蓝牙适配器
    private String deviceName;
    private BluetoothDevice mConnectedDevice = null;
    ConnectThread m_connect_thread = null;

    public static int open_result = 0;

    //    private BlueToothReceiver mBlueToothReceiver;
    private BlueToothConnextReceiver blueToothConnextReceiver;
    private BlueToothSendReceiver blueToothSendReceiver;

    private Context mContext;
    private String name;
    private BlueDeviceEntity blueDeviceEntity;

    public BlueToothManager(Context context) {
        this.mContext = context;
    }

    private FindBlueToothDeviceReceiver bReceiver;

    public void setUiHandler(Handler handler) {
        mHandler = handler;
    }

    public void setBluetoothAdapter(BluetoothAdapter handler) {
        bluetooth_adapter = handler;
    }

    /**
     * 蓝牙读数报文回调
     *
     * @param data 返回蓝牙读数报文
     */
    public void receiveIRData(Map<String, byte[]> data) {

        if (mHandler != null) {
            Message msg = mHandler.obtainMessage(0x5001, data);
            mHandler.sendMessage(msg);
        }
    }

    /**
     * 蓝牙读数报文回调
     *
     * @param data 返回蓝牙读数报文
     */
    public void bluetoothLine(String data) {

        if (mHandler != null) {
            Message msg = mHandler.obtainMessage(0x5006, data);
            mHandler.sendMessage(msg);
        }
    }


    /**
     * 接收条码回调
     *
     * @param barcode
     */
    public void receiveBarCode(String barcode) {
        if (mHandler != null) {
            Message msg = mHandler.obtainMessage(0x5002, barcode);
            mHandler.sendMessage(msg);
        }
    }

    /**
     * 蓝牙读数超时后回调
     *
     * @param msg
     */
    public void overTimeMessage(String msg) {// 蓝牙通讯接收超时

        if (mHandler != null) {
            Message omsg = mHandler.obtainMessage(0x5003, msg);
            mHandler.sendMessage(omsg);
        }
    }

    /**
     * 打开蓝牙
     *
     * @return
     */
    public int open()// 打开蓝牙并连接
    {
        if (BlueToothUtil.LINK_STATE == 1) {
            return 1;
        }
        // 打开蓝牙设备
        openBluetooth();

//        bluetoothReceiver = new BluetoothConnectActivityReceiver();
//        mBlueToothReceiver = new BlueToothReceiver();
//        IntentFilter intentRequestPaird = new IntentFilter();
//        intentRequestPaird
//                .addAction("android.bluetooth.device.action.PAIRING_REQUEST");
//        intentRequestPaird.addAction(BluetoothDevice.ACTION_FOUND);
//        intentRequestPaird.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//        intentRequestPaird.addAction(BluetoothDevice.ACTION_FOUND);
//        intentRequestPaird.addAction(BluetoothDevice.ACTION_FOUND);
////        mContext.registerReceiver(bluetoothReceiver,
////                intentRequestPaird);
//        mContext.registerReceiver(mBlueToothReceiver,
//                intentRequestPaird);
        bReceiver = new FindBlueToothDeviceReceiver();
        IntentFilter intentFilter = new IntentFilter(
                BluetoothDevice.ACTION_FOUND);
        mContext.registerReceiver(bReceiver, intentFilter);


        blueToothConnextReceiver = new BlueToothConnextReceiver();
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("blueconnect");
        mContext.registerReceiver(blueToothConnextReceiver, intentFilter1);

        blueToothSendReceiver = new BlueToothSendReceiver();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("bluesend");
        mContext.registerReceiver(blueToothSendReceiver, intentFilter2);


        BluetoothChatService.GetInstance().addListener(this, 0);

        boolean isPaired = false;
        Set<BluetoothDevice> pairedDevices = bluetooth_adapter
                .getBondedDevices();
        // 判断已配对的设备中是否有deviceName
        if (pairedDevices.size() > 0) {
//            isPaired = true;
            for (BluetoothDevice device : pairedDevices) {
                if (!StringUtil.isBlank(deviceName) && device.getName().contains(deviceName)) {
                    this.mConnectedDevice = device;
                    name = device.getName();
                    LogUtil.d("TL", "name====" + name);
                    isPaired = true;
                    break;
                }
            }
        }
        if (isPaired) {
            // 如果已经配对则直接连接
            // 启动线程连接的设备
            m_connect_thread = new ConnectThread(mConnectedDevice);
            m_connect_thread.start();
            try {
                m_connect_thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            SharepreferenceUtil.setBlueConnectFlag(mContext, false);
            scanDevice t_scan = new scanDevice();
            t_scan.start();

            try {
                t_scan.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return open_result;
    }


    /**
     * 关闭蓝牙
     *
     * @return 0:失败 1：成功
     */
    public void close()// 断开连接
    {
        BlueToothUtil.LINK_STATE = 0;

        if (bReceiver != null) {
            mContext.unregisterReceiver(bReceiver);
            bReceiver = null;
        }

        if (blueToothConnextReceiver != null) {
            mContext.unregisterReceiver(blueToothConnextReceiver);
            blueToothConnextReceiver = null;
        }

        if (blueToothSendReceiver != null) {
            mContext.unregisterReceiver(blueToothSendReceiver);
            blueToothSendReceiver = null;
        }

        BluetoothChatService.GetInstance().Stop();

        if (m_connect_thread != null) {
            m_connect_thread.cancel();
            m_connect_thread = null;
        }

        // return result;
    }


    /**
     * 发送蓝牙指令
     *
     * @param data
     * @return
     */
    public int sendData(Map<String, byte[]> data) {
        // 清除蓝牙接收结果的列表
        BlueToothUtil.INFRA_REC.clear();

        int result = 0;
        byte[] frame = null;

        BlueToothUtil.REC_CMD_NUM = 0;

        BluetoothChatService.GetInstance().addListener(this, data.size());

        Iterator<String> it = data.keySet().iterator();
        int sendTime = 0;
        while (it.hasNext()) {
            String key = (String) it.next();
            frame = data.get(key);
            BluetoothChatService.GetInstance().write(frame);
            sendTime++;
        }

        if (sendTime == data.size()) {
            result = 1;
        } else {
            result = 0;
        }
        LogUtil.d("TL", "result=" + result);
        return result;
    }

    /**
     * 发送蓝牙指令
     *
     * @return
     */
    public int sendData(String frameStr) {
        // 清除蓝牙接收结果的列表
        BlueToothUtil.INFRA_REC.clear();

        int result = 0;
        byte[] frame = DataConvert.toBytes(frameStr);

        BlueToothUtil.REC_CMD_NUM = 0;

        BluetoothChatService.GetInstance().addListener(this, 1);

        BluetoothChatService.GetInstance().write(frame);
        return 1;
    }

    // ===============================================================
    // 打开蓝牙设备
    private void openBluetooth() {
        // 检查设备是否支持蓝牙
        bluetooth_adapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetooth_adapter == null) {
            Toast.makeText(this.mContext, "此设备不支持蓝牙！", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        // 打开蓝牙

        if (!bluetooth_adapter.isEnabled())// isEnabled()蓝牙功能是否启用
        {
            bluetooth_adapter.enable();
        }
    }

    List<BlueDeviceEntity> infos = new ArrayList<>();

    class FindBlueToothDeviceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                    BluetoothDevice temdevice = intent
                            .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    boolean isHas = false;
                    String state;
                    if (temdevice.getBondState() != BluetoothDevice.BOND_BONDED) {
                        state = "未配对";
                    } else {
                        state = "已配对";
                    }

                    for (int i = 0; i < infos.size(); i++) {
                        if (infos.get(i).mac.equals(temdevice.getAddress())) {
                            isHas = true;
                            break;
                        }
                    }

                    if (!isHas) {
                        BlueDeviceEntity info = new BlueDeviceEntity();
                        info.name = temdevice.getName();
                        info.mac = temdevice.getAddress();
                        info.state = state;
                        info.device = temdevice;
                        infos.add(info);

                        EventManager.getDefault().post(new BlueEntityEvent(infos));

                        Log.e(TAG, "搜索到设备===>设备名称为:====>"
                                + temdevice.getName() + " " + temdevice.getAddress());
                    }


//                    if (temdevice.getName() != null
//                            && temdevice.getName().trim().contains(deviceName)) {
//                        mConnectedDevice = temdevice;
//                        if (mConnectedDevice != null) {
//                            if (connection(mConnectedDevice)) {
//                                // while (true) {
//                                Log.i(TAG, "connecting...");
//                                if (mConnectedDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
//                                    ToastManager.getInstance().displayToast("该设备已绑定其它设备");
//                                }
//                                // 启动线程连接的设备
//                                m_connect_thread = new ConnectThread(
//                                        mConnectedDevice);
//                                m_connect_thread.start();
//                                try {
//                                    m_connect_thread.join();
//                                    return;
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                // }
//                                // }
//                            }
//                        }
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void connectFail() {
        ToastManager.getInstance().displayToast("连接蓝牙失败");
        // 无法扫描到蓝牙设备Lianway
        setState("无法扫描到蓝牙设备" + getDeviceName());
        open_result = 0;
        BlueToothUtil.LINK_STATE = 0;
    }

    private class scanDevice extends Thread {
        @Override
        public void run() {
            mConnectedDevice = null;
            bluetooth_adapter.startDiscovery();
        }
    }

    private void setState(String state) {
        Message msg = mHandler.obtainMessage(0x5004, state);
        mHandler.sendMessage(msg);
    }

    public class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            try {
                Method m = device.getClass().getMethod("createRfcommSocket",
                        new Class[]{int.class});
                tmp = (BluetoothSocket) m.invoke(device, 1);
//                if (("WLT2564M EDR").equals(name)){
//                    mmSocket = mConnectedDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
//                }else {
//                    mmSocket = tmp;
//                }
                mmSocket = mConnectedDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void run() {
            new Thread(new Runnable() {
                public void run() {
                    bluetooth_adapter.cancelDiscovery();
                    try {
                        mmSocket.connect();
                        Log.e(TAG, "--------蓝牙连接成功");
//                        bluetoothLine("蓝牙连接成功");
                        EventManager.getDefault().post(new BlueConnectedEvent());
                        BluetoothChatService.GetInstance().Start(mmSocket);
                        SharepreferenceUtil.setBlueConnectFlag(mContext, true);
                        open_result = 1;
                        BlueToothUtil.LINK_STATE = 1;
                        if (blueDeviceEntity != null && !StringUtil.isBlank(blueDeviceEntity.name)) {
                            Log.e(TAG, "----blueDeviceEntity.name----" + blueDeviceEntity.name);
                            Log.e(TAG, "----getWSType----" + SharepreferenceUtil.getMyWsType(mContext));
                            SharepreferenceUtil.setBlueName(mContext, blueDeviceEntity.name, SharepreferenceUtil.getMyWsType(mContext));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        SharepreferenceUtil.setBlueConnectFlag(mContext, false);
                        LogUtil.d("TL", e.getMessage());
                        open_result = 0;
                        // 关闭这个socket
                        try {
                            mmSocket.close();
                        } catch (IOException e2) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 自动配对设备
     *
     * @param device 需要配对的设备
     * @return
     */
    public boolean connection(BluetoothDevice device) {
        boolean result = false;
        int pairNum = 0;
        if (device != null) {
            try {
                Log.i(TAG, "currentBondState=====>"
                        + device.getBondState());
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {// 判断是否已配对
                    try {
                        boolean setFlag = BlueToothUtil.setPin(device.getClass(),
                                device, BlueToothUtil.PIN_ID);
                        if (setFlag) {
                            Log.i(TAG, "自动配对设置成功");
                        } else {
                            Log.i(TAG, "自动配对设置失败");
                            // return false;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(TAG, "自动配对设置失败");
                        return false;
                    }// 设置pin值
                    boolean returnValue = false;
                    while (!returnValue && pairNum < 50) {
                        try {
                            returnValue = BlueToothUtil.createBond(
                                    device.getClass(), device);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.i(TAG, "配对失败");
                        }
                        pairNum++;
                    }
                    if (pairNum == 50) {
                        result = false;
                    } else {
                        result = true;
                    }
                } else {
                    return true;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            }
        }

        return result;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public class BlueToothConnextReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("tag", "---------111---------");
            if (intent.getAction().equals("blueconnect")) {
                Log.e("tag", "---------222---------");
                blueDeviceEntity = intent.getParcelableExtra("name");
                mConnectedDevice = blueDeviceEntity.device;

                if (null != m_connect_thread) {
                    m_connect_thread.cancel();
                    m_connect_thread = null;
                }

                m_connect_thread = new ConnectThread(mConnectedDevice);
                m_connect_thread.start();
                try {
                    m_connect_thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class BlueToothSendReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("tag", "---------333---------");
            if (intent.getAction().equals("bluesend")) {
                Log.e("tag", "---------444---------");

                String frameStr = intent.getStringExtra("name");
                sendData(frameStr);
            }
        }
    }


//    public class BlueToothReceiver extends BroadcastReceiver {
//
//        String strPsw = BlueToothUtil.PIN_ID;// "8260"
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(
//                    "android.bluetooth.device.action.PAIRING_REQUEST")) {
//                BluetoothDevice btDevice = intent
//                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//
//                Log.i("tag11111", "ddd");
//                try {
//                    BlueToothUtil.setPin(btDevice.getClass(), btDevice, strPsw); // 手机和蓝牙采集器配对
//                    BlueToothUtil.createBond(btDevice.getClass(), btDevice);
//                    BlueToothUtil.cancelPairingUserInput(btDevice.getClass(), btDevice);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//    }
}


