package com.sgcc.pda.hardware.hardware;


import com.sgcc.pda.hardware.SOConfig;
import com.sgcc.pda.hardware.shell.Shell;
import com.sgcc.pda.hardware.util.CommonFunc;
import com.sgcc.pda.hardware.util.Convert;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.ErrorManager;
import com.sgcc.pda.hardware.util.NewProtocol;
import com.sgcc.pda.sdk.utils.LogUtil;
import com.thinta.product.zdevice.InfraMeter;


/**
 * 创建者 田汉鑫
 * 创建时间 2016/4/21
 * 新A1-B设备红外接口
 */
public class A1BInfra implements ICommunicate {
    private static InfraMeter infraMeter = new InfraMeter();
    private static final int BUFFER_LENGTH = 1024;
    private boolean isOpen = false;

    /**
     * 打开通讯端口
     *
     * @return 0-成功 InfraError-红外设备操作失败
     */
    synchronized public int open() {
        if (isOpen) {
            return 0;
        }

        if (SOConfig.useOldSO) {
            if (infraMeter.open()) {
                isOpen = true;
                return 0;
            }
        } else {
            if (0 == Shell.IRDA_init()) {
                Shell.IRDA_setTimeOut(0, 5000);
                int i = Shell.IRDA_setTimeOut(1, 8000);
                LogUtil.e("TL","设置超时时间结果----"+i);
                if (NewProtocol.isNewProtocol()){
                    Shell.IRDA_config(1200,8,2,1,0);
                }else {
                    Shell.IRDA_config(1200,8,2,1);
                }
                isOpen = true;
                return 0;
            }
        }
        return ErrorManager.ErrorType.InfraError.getValue();
    }

    /**
     * 关闭通讯端口
     *
     * @return 0-成功 InfraError-红外设备操作失败
     */
    synchronized public int close() {
        if (SOConfig.useOldSO) {
            if (infraMeter.close()) {
                isOpen = false;
                return 0;
            }
        } else {
            if (0 == Shell.IRDA_deInit()) {
                isOpen = false;
                return 0;
            }
        }
        return ErrorManager.ErrorType.InfraError.getValue();
    }

    /**
     * 获取通讯端口状态
     *
     * @return 0-成功 其它-错误码
     * @deprecated 新A1-B接口中没有提供红外状态接口
     */
    public int getStatus() {
        return 0;
    }

    /**
     * 从通讯端口中读取数据
     *
     * @param data    返回-数据内容
     * @param timeout 读取超时
     * @return 0-成功
     * InfraError-红外设备操作失败
     * CommonBufferError-返回值缓冲区为空
     * CommonReadError-读取数据为空
     */
    synchronized public int read(StringBuffer data, long timeout) {
        if (data == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        int ret = open();
        if (ret != 0) {
            return ret;
        }

        if (SOConfig.useOldSO) {
            // 在操作前打开红外设备
            data.delete(0, data.length());
            byte[] buffer = null;
            do {
                buffer = infraMeter.receive(BUFFER_LENGTH, (int) timeout, 500);
                data.append(Convert.toHexString(buffer));
            } while (buffer != null && buffer.length >= BUFFER_LENGTH);
            // 将接收到的数据写入日志
            CommonFunc.log(getClass().getName() + " RECEIVE : " + data);
            if (data.length() == 0) {
                return ErrorManager.ErrorType.CommonReadError.getValue();
            }
            return 0;
        } else {
            //清除读取缓存
            Shell.IRDA_clearRevCache();
            //开始读取
            byte[] buffer = new byte[BUFFER_LENGTH];
            int offset = 0;
            byte buf[];
            int len ;
            do {
                if (NewProtocol.isNewProtocol()){
                    len = Shell.IRDA_recvData(buffer, offset,1024);
                }else{
                    len = Shell.IRDA_recvData(buffer, offset);
                }
                if (len > 0&&len<=BUFFER_LENGTH) {
                    buf = new byte[len];
                    System.arraycopy(buffer, 0, buf, 0, len);
                    offset += len;
                    data.append(Convert.toHexString(buf));
                } else {
                    LogUtil.d("XianChangBuChaoASyncTask"," read len= "+len);
                    return ErrorManager.ErrorType.CommonReadError.getValue();
                }

            } while (buf != null && buf.length >= BUFFER_LENGTH);
            // 将接收到的数据写入日志
            CommonFunc.log(getClass().getName() + " RECEIVE : " + Convert.toHexString(buffer));
            return 0;
        }
    }

    /**
     * 向通讯端口中写入数据
     *
     * @param message 待写入的数据
     * @return 0-成功 CommonParamError-待写入数据错误 InfraError-红外设备操作失败
     * CommonWriteError-数据发送失败
     */
    synchronized public int write(String message) {
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
        if (SOConfig.useOldSO) {
            ret = infraMeter.send(sendData, 0, sendData.length);
        } else {
            ret = Shell.IRDA_sendData(sendData, 0, sendData.length);
        }
        if (NewProtocol.isNewProtocol()){
            if (ret == 0){
                ret = 0;
            }else{
                ret = ErrorManager.ErrorType.CommonWriteError.getValue();
            }

        }else {
            if (ret == sendData.length) {
                ret = 0;
            } else {
                ret = ErrorManager.ErrorType.CommonWriteError.getValue();
            }
        }
        return ret;
    }
}