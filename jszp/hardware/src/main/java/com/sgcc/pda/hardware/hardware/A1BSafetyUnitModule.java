package com.sgcc.pda.hardware.hardware;

import android.util.Log;
import com.sgcc.pda.hardware.SOConfig;
import com.sgcc.pda.hardware.event.HardwareErroeEvent;
import com.sgcc.pda.hardware.shell.Shell;
import com.sgcc.pda.hardware.util.CommonFunc;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.ErrorManager;
import com.sgcc.pda.hardware.util.EventManager;
import com.sgcc.pda.hardware.util.NewProtocol;
import com.thinta.product.zdevice.SafetyUnit;


/**
 * 创建者 田汉鑫
 * 创建时间 2016/4/22
 * 新A1-B设备安全单元接口
 */
public class A1BSafetyUnitModule implements ICommunicate {
    private static SafetyUnit safetyUnit = new SafetyUnit();
    private static final int BUFFER_LENGTH = 1024*1024;
    private boolean isOpen = false;

    /**
     * 打开通讯端口
     *
     * @return 0-成功 SafetyUnitError-安全单元操作设备操作失败
     */
    synchronized public int open() {
        int ret = -1;
        if (isOpen) {
            return 0;
        }
        if (SOConfig.useOldSO) {
            if (safetyUnit.open()) {
                isOpen = true;
                return 0;
            }
            EventManager.getDefault().post(new HardwareErroeEvent("打开通讯端口失败,出现在A1BSafetyUnitModule类 open()方法中", "isOpen = " + isOpen));
            return ErrorManager.ErrorType.SafetyUnitError.getValue();
        } else {
            Log.d("upgrade","  open : ");
            int op = Shell.SecurityUnit_init();
            if (op == 0) {
                if (NewProtocol.isNewProtocol()) {
                    Shell.SecurityUnit_config(115200, 8, 0, 1, 1);
                } else {
                    Shell.SecurityUnit_config(115200, 8, 0, 1);
                }
                isOpen = true;
                ret = 0;

            } else if (op != 0) {
                EventManager.getDefault().post(new HardwareErroeEvent("打开通讯端口失败,出现在A1BSafetyUnitModule类 open()方法中", "isOpen = " + isOpen));
                ret = ErrorManager.ErrorType.SafetyUnitError.getValue();
            }
            return ret;
        }
    }

    /**
     * 关闭通讯端口
     *
     * @return 0-成功 SafetyUnitError-安全单元操作设备操作失败
     */
    synchronized public int close() {
        if (SOConfig.useOldSO) {
            isOpen = false;
            if (safetyUnit.close()) {
                return 0;
            }
//            EventManager.getDefault().post();
            EventManager.getDefault().post(new HardwareErroeEvent("关闭通讯端口失败,发生在A1BSafetyUnitModule类 close()方法中",
                    "isOpen = " + isOpen));
            return ErrorManager.ErrorType.SafetyUnitError.getValue();
        } else {
            int cl = Shell.SecurityUnit_deInit();
            if (cl == 0) {
                return 0;
            }
            EventManager.getDefault().post(new HardwareErroeEvent("关闭通讯端口失败,发生在A1BSafetyUnitModule类 close()方法中",
                    "isOpen = " + isOpen + " cl = " + cl));
            return ErrorManager.ErrorType.SafetyUnitError.getValue();
        }
    }

    /**
     * 获取通讯端口状态
     *
     * @return 0-成功 其它-错误码
     * @deprecated 新A1-B没有状态获取接口
     */
    public int getStatus() {
        return 0;
    }

    /**
     * 从通讯端口中读取数据
     *
     * @param data    返回-数据内容
     * @param timeout 读取超时
     * @return 0-成功 SafetyUnitError-安全单元操作设备操作失败 CommonBufferError-返回值缓冲区为空
     */
    synchronized public int read(StringBuffer data, long timeout) {
        if (data == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("从通讯端口中读取数据失败,发生在A1BSafetyUnitModule类 read()方法中",
                    "data = " + data + " timeout = " + timeout));
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        //打开
        int ret = open();
        if (ret != 0) {
            return ret;
        }
        if (SOConfig.useOldSO) {
            byte[] buffer;
            do {
                buffer = safetyUnit.receive(BUFFER_LENGTH, (int) timeout, 5000);
                data.append(DataConvert.toHexString(buffer));
            } while (buffer != null && buffer.length >= BUFFER_LENGTH);

            // 将接收到的数据写入日志
            CommonFunc.log(getClass().getName() + " RECEIVE : " + data);
            return 0;
        } else {
            //清除读取缓存
             Shell.SecurityUnit_clearRevCache();
            Shell.SecurityUnit_setTimeOut(1, (int) timeout);

            //开始读取s
            byte[] buffer = new byte[BUFFER_LENGTH];
            int offset = 0;
            int rcount;
            byte r[];
            do {
                if (NewProtocol.isNewProtocol()){
                    rcount = Shell.SecurityUnit_recvData(buffer, offset,BUFFER_LENGTH);
//                    Shell.SecurityUnit_clearRevCache();
                    Log.d("upgrade"," read: New Protocol  返回字节数: "+ rcount);
                }else {
                    rcount = Shell.SecurityUnit_recvData(buffer, offset);
                    Log.d("upgrade"," read: old Protocol  返回字节数: "+ rcount);
                }
                if (rcount > 0) {
                    r = new byte[rcount];
                    Log.i("TAG","r.length...." + r.length);
                    System.arraycopy(buffer, 0, r, 0, rcount);
                    offset += rcount;
                    data.append(DataConvert.toHexString(r));
                } else {
                    /*EventManager.getDefault().post(new HardwareErroeEvent("从通讯端口中读取数据失败,发生在A1BSafetyUnitModule类 read()方法中",
                            "data = "+data+" rcount = "+rcount));*/
                    return ErrorManager.ErrorType.CommonReadError.getValue();
                }
            } while (r != null && r.length >= BUFFER_LENGTH);
            // 将接收到的数据写入日志
          //  LogUtil.e("TL","给爸爸显示E6----"+data.toString().substring(data.toString().length()-2,data.toString().length()));
            CommonFunc.log(getClass().getName() + " RECEIVE: " + data);

            return 0;
        }
    }

    /**
     * 向通讯端口中写入数据
     *
     * @param message 待写入的数据
     * @return 0-成功 SafetyUnitError-安全单元操作设备操作失败 CommonParamError-输入参数不合法
     * CommonWriteError-数据写入错误
     */
    synchronized public int write(String message) {
        byte[] sendData = DataConvert.toBytes(message);
        if (sendData == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("向通讯端口中写入数据失败,发生在A1BSafetyUnitModule类 write()方法中",
                    "message = " + message));
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        int ret = open();
        if (ret != 0) {
            return ret;
        }
        // 将发送内容写入日志
        CommonFunc.log(getClass().getName() + " SEND : " + message);
        if (SOConfig.useOldSO) {
            ret = safetyUnit.send(sendData, 0, sendData.length);
        } else {
            ret = Shell.SecurityUnit_sendData(sendData, 0, sendData.length);

        }
        Log.d("upgrade","  write：  ret : "+ ret);

        if (NewProtocol.isNewProtocol()){
            if (ret == 0){
                ret = 0;
            }else{
                EventManager.getDefault().post(new HardwareErroeEvent("向通讯端口中写入数据失败,发生在A1BSafetyUnitModule类 write()方法中",
                        "ret = " + ret + " sendData.length = " + sendData.length));
                ret = ErrorManager.ErrorType.CommonWriteError.getValue();
            }
        }else  {
            if (ret == sendData.length) {
                ret = 0;
            } else {
                EventManager.getDefault().post(new HardwareErroeEvent("向通讯端口中写入数据失败,发生在A1BSafetyUnitModule类 write()方法中",
                        "ret = " + ret + " sendData.length = " + sendData.length));
                ret = ErrorManager.ErrorType.CommonWriteError.getValue();
            }
        }
        Log.d("upgrade","  write：  ret : "+ ret);
        return ret;
    }
}