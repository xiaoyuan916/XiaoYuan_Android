package com.sgcc.pda.hardware.hardware;

import android.os.Build;

import com.sgcc.pda.hardware.util.CommonFunc;
import com.sgcc.pda.hardware.util.Convert;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.ErrorManager;
import com.sgcc.pda.hardware.util.NewProtocol;

/**
 * @Author: GuangJie-Wang
 * @Date: 2016/12/14
 * @Time: 11:54
 */

public class A1BSerial implements ICommunicate {
    private static final int BUFFER_LENGTH = 1024;
    private boolean isOpen = false;

    @Override
    public int open() {
        if (isOpen) {
            return 0;
        }
//        int ret;
        if (A1BCom.open()) {
            isOpen = true;
            if (NewProtocol.isNewProtocol()){
                if (A1BCom.config(2400,8,2,1,1024)){
//                    ret = 0;
                    return 0;
                }else{
//                    ret = -1;
                    return -1;
                }
            }else{
                if (A1BCom.config(2400, 8, 2, 1)) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    ret = 0;
                    return 0;
                } else {
//                    ret = -1;
                    return -1;
                }
            }
        }
        return ErrorManager.ErrorType.InfraError.getValue();
    }

    @Override
    public int close() {
        isOpen = false;
        if (A1BCom.close()) {
            return 0;
        }
        return ErrorManager.ErrorType.InfraError.getValue();
    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public int read(StringBuffer data, long timeout) {
        byte buf[] = new byte[BUFFER_LENGTH];

        if (data == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        int ret = open();
        if (ret != 0) {
            return ret;
        }
        // 在操作前打开红外设备
        data.delete(0, data.length());
        byte[] buffer = null;
        do {
            if (NewProtocol.isNewProtocol()){
                buffer = A1BCom.recvData(buf,0,1024);
            }else{
                buffer = A1BCom.recvData(buf, 0);
            }
            data.append(Convert.toHexString(buffer));
        } while (buffer != null && buffer.length >= BUFFER_LENGTH);
        // 将接收到的数据写入日志
        CommonFunc.log(getClass().getName() + " RECEIVE : " + data);
        if (data.length() == 0) {
            return ErrorManager.ErrorType.CommonReadError.getValue();
        }
        return 0;
    }

    @Override
    public int write(String message) {
        byte[] sendData = DataConvert.toBytes(message);
        if (sendData == null) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        int ret = open();
        if (ret != 0) {
            return ret;
        }
        // 将发送内容写入日志
        CommonFunc.log(getClass().getName() + " SEND : " + message);
        // 向红外设备发送数据
        if (A1BCom.sendData(sendData, 0, sendData.length)) {
            return 0;
        } else {
            return ErrorManager.ErrorType.CommonWriteError.getValue();
        }
    }
}
