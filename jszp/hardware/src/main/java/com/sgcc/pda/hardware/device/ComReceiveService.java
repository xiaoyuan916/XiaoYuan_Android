package com.sgcc.pda.hardware.device;

import android.os.Handler;
import android.os.Message;

import com.sgcc.pda.hardware.frame.bluetooth.CustomBuffer;
import com.sgcc.pda.sdk.utils.LogUtil;


/**
 * CreateTime: 2015-11-25下午1:44
 * Author: wjkjinke00@126.com
 * Description:
 */
public class ComReceiveService {
    private static final String TAG = "ComReceiveService";

    private CustomBuffer mCustomBuffer;
    private ReceiveThread mReceiveThread;
    private Handler handler;

    public ComReceiveService(Handler handler) {
        mCustomBuffer = new CustomBuffer();
        this.handler = handler;
    }

    public void start() {
        if (mReceiveThread == null) {
            mReceiveThread = new ReceiveThread();
            mReceiveThread.start();
        }
    }

    public void stop() {
        if (mReceiveThread != null) {
            mReceiveThread.cancel();
            mReceiveThread = null;
        }
    }

    private class ReceiveThread extends Thread {
        boolean flag = true;

        @Override
        public void run() {
            byte[] rBuffer = new byte[1024];// 1024
            int readLen;

            while (flag) {
                LogUtil.i(TAG,"ReceiveThread start");
                synchronized (ComReceiveService.this) {
                    // 读入输入流，用于接收远程信息
                    try {
                        byte[] receive = ComNew.receive(30000);
                        if (null != receive && receive.length > 0) {

                            boolean frameOk = CustomBuffer.isFrameOk(receive);

                            if (!frameOk) {
                                continue;
                            }

                            Message message = Message.obtain();
                            message.what = 0x5002;
                            message.obj = receive;
                            handler.sendMessage(message);
                        }
                    } catch (Exception e) {
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
