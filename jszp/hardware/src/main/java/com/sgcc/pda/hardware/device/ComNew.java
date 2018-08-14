package com.sgcc.pda.hardware.device;

import com.sgcc.pda.hardware.SOConfig;
import com.sgcc.pda.hardware.shell.Shell;
import com.thinta.device.UsbSerialPort;


/**
 * Created by Nicky on 2015/11/19.
 */
public class ComNew {

    /**
     * open 打开串口 参数: 无 返回: 成功返回 0
     */
    public static boolean open() {
        if (SOConfig.useOldSO) {
            return UsbSerialPort.open() == 0;
        } else {
            if (0 == Shell.Serialport_init()) {
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * close 关闭串口 参数: 无 返回: 成功返回 true，失败返回false
     */
    public static boolean close() {
        if (SOConfig.useOldSO) {
            return UsbSerialPort.close() == 0;
        } else {
            if (0 == Shell.Serialport_deInit()) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * set 设置串口通讯参数 参数: baudRate 波特率，支持
     * 300、600、1200、2400、4800、9600、19200、38400、57600、115200 等 parity 校验位 0:无校验
     * 1:奇校验 2:偶校验 dataBits 数据位 5:5个数据位 6:6个数据位 7:7个数据位 8:8个数据位 stopBits 停止位
     * 1:一个停止位 2:两个停止位 返回: 成功返回 true，失败返回 true
     */
    public static boolean set(int baudRate, int parity, int dataBits,
                              int stopBits) {
        if (SOConfig.useOldSO) {
            return UsbSerialPort.set(baudRate, parity, dataBits, stopBits) == 0;
        } else {
            if (0 == Shell.Serialport_config(baudRate, dataBits, parity, stopBits)) {
                return true;
            } else {
                return  false;
            }
        }

    }
    /**
     * set 设置串口通讯参数 参数: baudRate 波特率，支持
     * 300、600、1200、2400、4800、9600、19200、38400、57600、115200 等 parity 校验位 0:无校验
     * 1:奇校验 2:偶校验 dataBits 数据位 5:5个数据位 6:6个数据位 7:7个数据位 8:8个数据位 stopBits 停止位
     * 1:一个停止位 2:两个停止位 返回: 成功返回 true，失败返回 true
     */
    public static boolean set(int baudRate, int parity, int dataBits,
                              int stopBits,int blockmode) {
        if (SOConfig.useOldSO) {
            return UsbSerialPort.set(baudRate, parity, dataBits, stopBits) == 0;
        } else {
            if (0 == Shell.Serialport_config(baudRate, dataBits, parity, stopBits,blockmode)) {
                return true;
            } else {
                return  false;
            }
        }

    }


    public static int send(byte[] buffer, int offset, int length) {
        if (SOConfig.useOldSO) {
            return UsbSerialPort.send(buffer, length);
        } else {
            Shell.Serialport_clearSendCache();
            return Shell.Serialport_sendData(buffer, offset, length);
        }
    }

    public static byte[] receive(int timeout) {
        if (SOConfig.useOldSO) {
            try {
                byte[] temp = new byte[1024];
                UsbSerialPort.clearReceiveBuffer();
                int len = UsbSerialPort.receive(temp, temp.length, timeout);
                if (len < 1) {
                    return null;
                } else {
                    byte ret[] = new byte[len];
                    System.arraycopy(temp, 0, ret, 0, len);
                    return ret;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try {
                byte[] temp = new byte[1024];
                Shell.Serialport_clearRevCache();
                Shell.Serialport_setTimeOut(1, timeout);
                int len = Shell.SecurityUnit_recvData(temp, temp.length);
                if (len < 1) {
                    return null;
                } else {
                    byte ret[] = new byte[len];
                    System.arraycopy(temp, 0, ret, 0, len);
                    return ret;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}