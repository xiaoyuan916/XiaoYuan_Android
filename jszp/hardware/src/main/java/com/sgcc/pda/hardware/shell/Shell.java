package com.sgcc.pda.hardware.shell;

import com.cepri.dev.IRDA;
import com.cepri.dev.LaserIRDA;
import com.cepri.dev.LibInfo;
import com.cepri.dev.RESAM;
import com.cepri.dev.Scanner;
import com.cepri.dev.SecurityUnit;
import com.cepri.dev.Serialport;
import com.sgcc.pda.hardware.util.NewProtocol;

import cepri.device.utils.RS485;

/**
 * Created by xuzl on 2016/10/31.
 */
public class Shell {
    /**
     * 安全单元初始化，包括打开电源和通信
     *
     * @return 0:成功 其它：错误号
     *  -1  SO库加载失败
     */
    public static int SecurityUnit_init() {
        int ret = 0;
        try {
            if (NewProtocol.isNewProtocol()) {
                ret = cepri.device.utils.SecurityUnit.Init();
            } else {
                ret = SecurityUnit.init();
            }
        }catch (Throwable e){
            return -1;
        }

        return ret;
    }

    /**
     * 安全单元通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public static int SecurityUnit_deInit() {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.SecurityUnit.DeInit();
        } else {
            ret = SecurityUnit.deInit();
        }
        return ret;
    }

    /**
     * 清空安全单元的发送缓存
     *
     * @return 0：成功 其它：错误号
     */
    public static int SecurityUnit_clearSendCache() {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.SecurityUnit.ClearSendCache();
        } else {
            ret = SecurityUnit.clearSendCache();
        }
        return ret;
    }

    /**
     * 清空安全单元的接收缓存
     *
     * @return 0：成功 其它：错误号
     */
    public static int SecurityUnit_clearRevCache() {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.SecurityUnit.ClearRecvCache();
        } else {
            ret = SecurityUnit.clearRevCache();
        }
        return ret;
    }

    /**
     * 设置通讯参数
     *
     * @param baudrate 通讯波特率
     * @param databits 数据位
     * @param parity   校验位 0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits 停止位 0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     * @return 0:成功 其它：错误号
     */
    public static int SecurityUnit_config(int baudrate, int databits, int parity, int stopbits) {
        return SecurityUnit.config(baudrate, databits, parity, stopbits);
    }

    /**
     * 设置通讯参数
     *
     * @param baudrate  通讯波特率
     * @param databits  数据位
     * @param parity    校验位 0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits  停止位 0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     * @param blockmode 0为无阻塞，1为阻塞
     * @return 0:成功 其它：错误号
     */
    public static int SecurityUnit_config(int baudrate, int databits, int parity, int stopbits, int blockmode) {
        return cepri.device.utils.SecurityUnit.Config(baudrate, databits, parity, stopbits, blockmode);

    }

    /**
     * 发送数据
     *
     * @param data   发送数据缓冲区
     * @param offset 偏移量
     * @param length 发送数据的目标长度
     * @return 成功发送数据的字节长度
     */
    public static int SecurityUnit_sendData(byte[] data, int offset, int length) {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.SecurityUnit.SendData(data, offset, length);
        } else  {
            ret = SecurityUnit.sendData(data, offset, length);
        }
        return ret;
    }

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public static int SecurityUnit_recvData(byte[] data, int offset) {
        return SecurityUnit.recvData(data, offset);
    }

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @param count  数据数量
     * @return 成功接收的字节数
     */
    public static int SecurityUnit_recvData(byte[] data, int offset, int count) {
        return cepri.device.utils.SecurityUnit.RecvData(data, offset, count);
    }

    /**
     * 设置发送和接收数据的超时时间
     *
     * @param direction 方向  发送；1,接收
     * @param timeout   超时时间 单位 ms(毫秒)
     * @return 0：成功 其它：错误号
     */
    public static int SecurityUnit_setTimeOut(int direction, int timeout) {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.SecurityUnit.SetTimeOut(direction, timeout);
        }
        else {
            ret = SecurityUnit.setTimeOut(direction, timeout);
        }
        return ret;
    }

    /**
     * R-ESAM初始化，包括打开电源和通信端口
     *
     * @return 0:成功  其它：错误号
     */
    public static int RESAM_init() {
        return RESAM.init();
    }

    /**
     * R-ESAM通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public static int RESAM_deInit() {
        return RESAM.deInit();
    }

    /**
     * 清空R-ESAM的发送缓存
     *
     * @return 0：成功 其它：错误号
     */
    public static int RESAM_clearSendCache() {
        return RESAM.clearSendCache();
    }

    /**
     * 清空R-ESAM的接收缓存
     *
     * @return 0：成功 其它：错误号
     */
    public static int RESAM_clearRevCache() {
        return RESAM.clearRevCache();
    }

    /**
     * 设置通讯参数
     *
     * @param baudrate 通讯波特率
     * @param databits 数据位
     * @param parity   校验位 0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits 停止位 0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     * @return 0:成功 其它：错误号
     */
    public static int RESAM_config(int baudrate, int databits, int parity, int stopbits) {
        return RESAM.config(baudrate, databits, parity, stopbits);
    }

    /**
     * 发送数据
     *
     * @param data   发送数据缓冲区
     * @param offset 偏移量
     * @param length 发送数据的目标长度
     * @return 成功发送数据的字节长度
     */
    public static int RESAM_sendData(byte[] data, int offset, int length) {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.RESAM.SendData(data, offset, length);
        } else  {
            ret = RESAM.sendData(data, offset, length);
        }
        return ret;
    }

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public static int RESAM_recvData(byte[] data, int offset, int count) {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.RESAM.RecvData(data, offset, count);
        } else {
            ret = RESAM.recvData(data, offset);
        }
        return ret;
    }

    /**
     * 设置发送和接收数据的超时时间
     *
     * @param direction 方向  发送；1,接收
     * @param timeout   超时时间 单位 ms(毫秒)
     * @return 0：成功 其它：错误号
     */
    public static int RESAM_setTimeOut(int direction, int timeout) {
        return RESAM.setTimeOut(direction, timeout);
    }

    /**
     * 红外初始化，包括打开电源和通信端口
     *
     * @return 0:成功 其它：错误号
     */
    public static int IRDA_init() {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.IRDA.Init();
        } else  {
            ret = IRDA.init();
        }
        return ret;
    }

    /**
     * 红外通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public static int IRDA_deInit() {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.IRDA.DeInit();
        } else  {
            ret = IRDA.deInit();
        }
        return ret;
    }

    /**
     * 清空红外的发送缓存
     *
     * @return 0：成功 其它：错误号
     */
    public static int IRDA_clearSendCache() {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.IRDA.ClearSendCache();
        } else {
            ret = IRDA.clearSendCache();
        }
        return ret;
    }

    /**
     * 清空红外的接收缓存
     *
     * @return 0：成功 其它：错误号
     */
    public static int IRDA_clearRevCache() {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.IRDA.ClearRecvCache();
        }else{
            ret = IRDA.clearRevCache();
        }
        return ret;
    }

    /**
     * 设置通讯参数
     *
     * @param baudrate 通讯波特率
     * @param databits 数据位
     * @param parity   校验位  0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits 停止位  0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     * @return 0:成功 其它：错误号
     */
    public static int IRDA_config(int baudrate, int databits, int parity, int stopbits) {
        return IRDA.config(baudrate, databits, parity, stopbits);
    }

    /**
     * 设置通讯参数
     *
     * @param baudrate  通讯波特率
     * @param databits  数据位
     * @param parity    校验位  0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits  停止位  0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     * @param blockmode 阻塞模式。0为无阻塞，1为阻塞
     * @return 0:成功 其它：错误号
     */
    public static int IRDA_config(int baudrate, int databits, int parity, int stopbits, int blockmode) {
        return cepri.device.utils.IRDA.Config(baudrate, databits, parity, stopbits, blockmode);
    }

    /**
     * 发送数据
     *
     * @param data   发送数据缓冲区
     * @param offset 偏移量
     * @param length 发送数据的目标长度
     * @return 成功发送数据的字节长度
     */
    public static int IRDA_sendData(byte[] data, int offset, int length) {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.IRDA.SendData(data, offset, length);
        }else{
            ret = IRDA.sendData(data, offset, length);
        }
        return ret;
    }

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public static int IRDA_recvData(byte[] data, int offset) {
        return IRDA.recvData(data, offset);
    }

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @param count  数据数量
     * @return 成功接收的字节数
     */
    public static int IRDA_recvData(byte[] data, int offset, int count) {
        return cepri.device.utils.IRDA.RecvData(data, offset, count);
    }

    /**
     * 设置发送和接收数据的超时时间
     *
     * @param direction 方向  发送；1,接收
     * @param timeout   超时时间 单位 ms(毫秒)
     * @return 0：成功 其它：错误号
     */
    public static int IRDA_setTimeOut(int direction, int timeout) {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.IRDA.SetTimeOut(direction,timeout);
        }else{
            ret = IRDA.setTimeOut(direction, timeout);
        }
        return ret;
    }

    /**
     * 激光红外初始化，包括打开电源和通信端口
     *
     * @return 0:成功 其它：错误号
     */
    public static int LaserIRDA_init() {
        return LaserIRDA.init();
    }

    /**
     * 激光红外通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public static int LaserIRDA_deInit() {
        return LaserIRDA.deInit();
    }

    /**
     * 清空激光红外的发送缓存
     *
     * @return 0：成功 其它：错误号
     */
    public static int LaserIRDA_clearSendCache() {
        return LaserIRDA.clearSendCache();
    }

    /**
     * 清空激光红外的接收缓存
     *
     * @return 0：成功 其它：错误号
     */
    public static int LaserIRDA_clearRevCache() {
        return LaserIRDA.clearRevCache();
    }

    /**
     * 设置通讯参数
     *
     * @param baudrate 通讯波特率
     * @param databits 数据位
     * @param parity   校验位  0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits 停止位  0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     * @return 0:成功 其它：错误号
     */
    public static int LaserIRDA_config(int baudrate, int databits, int parity, int stopbits) {
        return LaserIRDA.config(baudrate, databits, parity, stopbits);
    }

    /**
     * 发送数据
     *
     * @param data   发送数据缓冲区
     * @param offset 偏移量
     * @param length 发送数据的目标长度
     * @return 成功发送数据的字节长度
     */
    public static int LaserIRDA_sendData(byte[] data, int offset, int length) {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.LaserIRDA.SendData(data, offset, length);
        }else {
            ret = LaserIRDA.sendData(data, offset, length);
        }
        return ret;
    }

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public static int LaserIRDA_recvData(byte[] data, int offset) {
        return LaserIRDA.recvData(data, offset);
    }

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public static int LaserIRDA_recvData(byte[] data, int offset, int length) {
        return cepri.device.utils.LaserIRDA.RecvData(data, offset, length);
    }

    /**
     * 设置发送和接收数据的超时时间
     *
     * @param direction 方向  发送；1,接收
     * @param timeout   超时时间 单位 ms(毫秒)
     * @return 0：成功 其它：错误号
     */
    public static int LaserIRDA_setTimeOut(int direction, int timeout) {
        return LaserIRDA.setTimeOut(direction, timeout);
    }

    /**
     * 扫描头初始化，包括打开电源和通信端口
     *
     * @return 0:成功  其它：错误号
     */
    public static int Scanner_init() {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.Scanner.Init();
        }else {
            ret = Scanner.init();
        }
        return ret;
    }

    /**
     * 扫描头通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public static int Scanner_deInit() {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.Scanner.DeInit();
        }else{
            ret = Scanner.deInit();
        }
        return ret;
    }

    /**
     * 读取条码或者二维码
     *
     * @param timeout 超时时间
     * @param code    读取到的数据
     * @param offset  偏移量
     * @return 读取到的条码或者二维码的字节数据长度
     */
    public static int Scanner_decode(int timeout, byte[] code, int offset) {
        int ret = 0;
        if (NewProtocol.isNewProtocol()) {
            ret = cepri.device.utils.Scanner.Decode(timeout, code, offset);
        }else {
            ret = Scanner.decode(timeout, code, offset);
        }
        return ret;
    }

    /**
     * 串口初始化，包括打开电源和通信端口
     *
     * @return 0:成功 其它：错误号
     */
    public static int Serialport_init() {
        int ret = 0;
        if (NewProtocol.isNewProtocol()){
            ret = RS485.Init();
        }else {
            ret =  Serialport.init();
        }
        return ret;
    }

    /**
     * 串口通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public static int Serialport_deInit() {
        int ret = 0;
        if (NewProtocol.isNewProtocol()){
            ret = RS485.DeInit();
        }else {
             ret = Serialport.deInit();
        }
        return ret;


    }

    /**
     * 清空串口的发送缓存
     *
     * @return 0：成功 其它：错误号
     */
    public static int Serialport_clearSendCache() {
        int ret = 0;
        if (NewProtocol.isNewProtocol()){
            ret = RS485.ClearSendCache();
        }else {
            ret = Serialport.clearSendCache();
        }
        return ret;
    }

    /**
     * 清空串口的接收缓存
     *
     * @return 0：成功 其它：错误号
     */
    public static int Serialport_clearRevCache() {
        int ret = 0;
        if (NewProtocol.isNewProtocol()){
            ret = RS485.ClearRecvCache();
        }else {
            ret = Serialport.clearRevCache();
        }
        return ret;
    }

    /**
     * 设置通讯参数
     *
     * @param baudrate 通讯波特率
     * @param databits 数据位
     * @param parity   校验位  0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits 停止位  0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     * @return 0:成功 其它：错误号
     */
    public static int Serialport_config(int baudrate, int databits, int parity, int stopbits) {
        return Serialport.config(baudrate, databits, parity, stopbits);
    }

    /**
     * 设置通讯参数
     *
     * @param baudrate 通讯波特率
     * @param databits 数据位
     * @param parity   校验位  0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits 停止位  0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     * @return 0:成功 其它：错误号
     */
    public static int Serialport_config(int baudrate, int databits, int parity, int stopbits,int blockmode) {
        return RS485.Config(baudrate, databits, parity, stopbits,blockmode);
    }

    /**
     * 发送数据
     *
     * @param data   发送数据缓冲区
     * @param offset 偏移量
     * @param length 发送数据的目标长度
     * @return 成功发送数据的字节长度
     */
    public static int Serialport_sendData(byte[] data, int offset, int length) {
        int ret = 0;
        if (NewProtocol.isNewProtocol()){
            ret = RS485.SendData(data,offset,length);
        }else {
            ret = Serialport.sendData(data, offset, length);
        }
        return ret;
    }

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public static int Serialport_recvData(byte[] data, int offset) {
        return Serialport.recvData(data, offset);
    }
    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public static int Serialport_recvData(byte[] data, int offset,int count) {
        return RS485.RecvData(data, offset,count);
    }

    /**
     * 设置发送和接收数据的超时时间
     *
     * @param direction 方向  发送；1,接收
     * @param timeout   超时时间 单位 ms(毫秒)
     * @return 0：成功 其它：错误号
     */
    public static int Serialport_setTimeOut(int direction, int timeout) {
        int ret = 0;
        if (NewProtocol.isNewProtocol()){
            ret = RS485.SetTimeOut(direction, timeout);
        }else {
            ret = Serialport.setTimeOut(direction, timeout);
        }
        return ret;
    }

    /**
     * 获取so库的版本序号
     *
     * @return So库的版本序号
     */
    public static int LibInfo_getVersion() {
        return LibInfo.getVersion();
    }

    /**
     * 获取so库的厂家编码
     *
     * @return So库的厂家编码
     */
    public static int LibInfo_getCompany() {
        return LibInfo.getCompany();
    }
}
