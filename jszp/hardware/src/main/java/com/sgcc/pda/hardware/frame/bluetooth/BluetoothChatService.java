package com.sgcc.pda.hardware.frame.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;
import com.sgcc.pda.hardware.util.ByteUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class BluetoothChatService {

    private static final String TAG = "BluetoothChatService";

    private ConnectedThread mConnectedThread = null;
    private CustomBuffer custom_buffer = null;
    private ReadThread mReadThread = null;
    private Object obj = null;
    private int cmd_num = 0;

    private static BluetoothChatService bt_service = null;

    public static BluetoothChatService GetInstance() {
        synchronized (BluetoothChatService.class) {
            if (bt_service == null) {
                bt_service = new BluetoothChatService();
            }
        }
        return bt_service;
    }

    private BluetoothChatService() {
        custom_buffer = new CustomBuffer();
    }

    public void addListener(Object obj, int cmd_num) {
        this.obj = obj;
        this.cmd_num = cmd_num;
    }

    public void Start(BluetoothSocket socket) {
        Log.i(TAG, "BluetoothChatService=======Start");
        if (mConnectedThread == null) {
            // 启动线程管理连接和传输
            mConnectedThread = new ConnectedThread(socket);
            mConnectedThread.start();
            Log.i(TAG, "mConnectedThread=======start");
        }

        if (mReadThread == null) {
            mReadThread = new ReadThread();
            mReadThread.start();
            Log.i(TAG, "mReadThread=======start");
        }
    }

    public void Stop() {

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        if (mReadThread != null) {
            mReadThread.cancel();
            mReadThread = null;
        }
    }

    public void write(byte[] out) {
        // 创建临时对象
        ConnectedThread r;

        if (BlueToothUtil.LINK_STATE != 1)
            return;

        if (mConnectedThread != null) {
            r = mConnectedThread;

            // 执行写同步
            r.write(out);
        }
    }

    // 本线程负责处理所有的传入和传出（连接之后的管理线程）
    private class ConnectedThread extends Thread {
        private BluetoothSocket mmSocket;
        private InputStream mInStream;
        private OutputStream mOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
                Log.i(TAG, "BluetoothChatService===========getOutputStream===========");
            } catch (IOException e) {
                e.printStackTrace();
            }

            mInStream = tmpIn;
            mOutStream = tmpOut;
        }

        @Override
        public void run() {
            byte[] rBuffer = new byte[1024];// 1024
            int readLen;

            while (true) {
                synchronized (BluetoothChatService.this) {
                    try {
                        // 读入输入流，用于接收远程信息
                        readLen = mInStream.read(rBuffer);
                        byte[] tempBuffer = new byte[readLen];
                        System.arraycopy(rBuffer, 0, tempBuffer, 0, readLen);
                        Log.i(TAG, "readBytes------" + BcdUtils.binArrayToString(tempBuffer));
                        custom_buffer.InData(tempBuffer, readLen);

                        Log.i(TAG, "ConnectedThread notify");
                        BluetoothChatService.this.notify();
                        Log.i(TAG, "ConnectedThread wait");
                        BluetoothChatService.this.wait();

                    } catch (Exception e) {
                        Log.i(TAG, "BluetoothChatService Lost ");
                        // 装置连接丢失
                        BlueToothUtil.LINK_STATE = 0;
                        break;
                    }
                }

            }
        }

        public void write(byte[] buffer) {
            try {
                // 得到输出流，用于发送给远程的信息
                mOutStream.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReadThread extends Thread {
        boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                synchronized (BluetoothChatService.this) {
                    if (custom_buffer.getEffectiveLen() >= 8) {
                        byte[] read_buffer = new byte[1024];
                        int[] len = new int[]{0};
                        int flag = custom_buffer.getDataMessage(read_buffer,
                                len);
                        if (flag == 1) {
                            if (obj != null) {
                                byte[] tmp = new byte[len[0]];
                                System.arraycopy(read_buffer, 0, tmp, 0, len[0]);
                                Log.i(TAG, "返回数据：" + ByteUtils.byteToHexString(tmp));
                                BlueToothUtil.INFRA_REC.put("返回报文" + BlueToothUtil.REC_CMD_NUM, tmp);
                                ((BlueToothManager) obj).receiveIRData(BlueToothUtil.INFRA_REC);
                            }
                        } else {
                            Log.i(TAG, "run: ===================" + flag);
                        }
                    } else {
                        Log.i(TAG, "run: +++++++++++++++");
                    }
                    Log.i(TAG, "ReadThread notify");
                    BluetoothChatService.this.notify();
                    try {
                        Log.i(TAG, "ReadThread wait");
                        BluetoothChatService.this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

        public void cancel() {
            flag = false;
        }
    }

}
