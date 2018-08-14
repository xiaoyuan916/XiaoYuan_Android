package com.sgcc.pda.hardware.hardware;

import android.os.Build;

import com.cepri.dev.Serialport;
import com.sgcc.pda.hardware.util.NewProtocol;

import cepri.device.utils.RS485;

/**
 * @Author: GuangJie-Wang
 * @Date: 2016/12/14
 * @Time: 12:06
 */

public class A1BCom {
    /**
     * 串口初始化，包括打开电源和通信端口
     *
     * @return 0:成功 其它：错误号
     */
    public static boolean open() {
        int ret = -1;
        if (NewProtocol.isNewProtocol()){
             ret = RS485.Init();
        }else{
             ret = Serialport.init();
        }
        if (ret ==0){
            return true;
        }
        return false;
    }

    /**
     * 串口通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public static boolean close() {
        int ret = -1;
        if (NewProtocol.isNewProtocol()){
            ret = RS485.DeInit();
        }else{
            ret = Serialport.deInit();
        }
        if (ret ==0){
            return true;
        }
        return false;
    }

    /**
     * 清空串口的发送缓存
     *
     * @return 0：成功 其它：错误号
     */
    public static boolean clearSendCache() {
        int ret = -1;
        if (NewProtocol.isNewProtocol()){
            ret = RS485.ClearSendCache();
        }else{
            ret = Serialport.clearSendCache();
        }
        if (ret ==0){
            return true;
        }
        return false;
    }

    /**
     * 清空串口的接收缓存
     *
     * @return 0：成功 其它：错误号
     */
    public static boolean clearRevCache() {
        int ret = -1;
        if (NewProtocol.isNewProtocol()){
            ret = RS485.ClearRecvCache();
        }else{
            ret = Serialport.clearRevCache();
        }
        if (ret ==0){
            return true;
        }
        return false;
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
    public static boolean config(int baudrate, int databits, int parity, int stopbits) {
        return Serialport.config(baudrate, databits, parity, stopbits) == 0;
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
    public static boolean config(int baudrate, int databits, int parity, int stopbits,int blockmode) {
        return RS485.Config(baudrate, databits, parity, stopbits,blockmode) == 0;
    }
    /**
     * 发送数据
     *
     * @param data   发送数据缓冲区
     * @param offset 偏移量
     * @param length 发送数据的目标长度
     * @return 成功发送数据的字节长度
     */
    public static boolean sendData(byte[] data, int offset, int length) {
        int ret = -1;
        if (NewProtocol.isNewProtocol()){
            ret = RS485.SendData(data,offset,length);
        }else{
            if(Serialport.sendData(data,offset,length) == length){
                ret = 0;
            }else{
                ret = -1;
            }

        }
        if (ret ==0){
            return true;
        }
        return false;
    }

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public static byte[] recvData(byte[] data, int offset) {
        int ret = Serialport.recvData(data, offset);
        byte[] buffer = new byte[ret];
        System.arraycopy(data, 0, buffer, 0, ret);
        return buffer;
    }
    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public static byte[] recvData(byte[] data, int offset,int count) {
        int ret = RS485.RecvData(data, offset,count);
        if(ret == -1){
            return null;
        }
        byte[] buffer = new byte[ret];
        System.arraycopy(data, 0, buffer, 0, ret);
        return buffer;
    }

    /**
     * 设置发送和接收数据的超时时间
     *
     * @param direction 方向  发送；1,接收
     * @param timeout   超时时间 单位 ms(毫秒)
     * @return 0：成功 其它：错误号
     */
    public static boolean setTimeOut(int direction, int timeout) {
        return Serialport.setTimeOut(direction, timeout) == 0;
    }

}
