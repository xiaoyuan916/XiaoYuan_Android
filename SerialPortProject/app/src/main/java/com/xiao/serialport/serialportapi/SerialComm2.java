package com.xiao.serialport.serialportapi;


import com.blankj.utilcode.util.LogUtils;
import com.xiao.serialport.service.SerialPortService;
import com.xiao.serialport.serialportapi.bean.ComBean;

import java.io.IOException;
import java.security.InvalidParameterException;

public class SerialComm2 implements Runnable {

    // 单例模式，线程安全
    private static final SerialComm2 SERIALCOMM = new SerialComm2();

    private SerialComm2() {
    }

    ;

    /**
     * 获取实例
     *
     * @return
     * @author ShenYang
     * @date 2015-8-13
     */
    public static SerialComm2 getInstance() {
        return SERIALCOMM;
    }

    private SerialControl ComA;

    @Override
    public void run() {
    }

    public void open() {
        LogUtils.d("serial port start init ...");
        ComA = new SerialControl();
        ComA.setPort("/dev/ttyS3");
        ComA.setBaudRate(9600);
        OpenComPort(ComA);
        LogUtils.d("serial open success ...");
    }

    /**
     * 发送消息
     *
     * @param message 十六进制的消息字符串。
     */
    public void send(String message) {
        try {
            ComA.send(DigitalTrans.hex2byte(message));
            LogUtils.d("write To Serial:" + message);
        } catch (Exception e) {
            LogUtils.d("write To Serial Exception:" + e.getMessage());
        }
    }

    /**
     * 接收数据
     *
     * @author NY
     */
    private class SerialControl extends SerialHelper {

        // public SerialControl(String sPort, String sBaudRate){
        // super(sPort, sBaudRate);
        // }
        public SerialControl() {
        }

        @Override
        protected void onDataReceived(final ComBean ComRecData) {
            putMessage(ComRecData);
        }
    }

    /**
     * 打开串口
     *
     * @param ComPort
     */
    private void OpenComPort(SerialHelper ComPort) {
        try {
            ComPort.open();
            // 启动线程
            Thread thread = new Thread(this);
            thread.start();
        } catch (SecurityException e) {
            LogUtils.e("serial port open fail not write/read auth,打开串口失败:没有串口读/写权限!");
        } catch (IOException e) {
            LogUtils.e("serial port open fail: no 打开串口失败:未知错误!");
        } catch (InvalidParameterException e) {
            LogUtils.e("serial port open fail;param fail 打开串口失败:参数错误!");
        }
    }

    /**
     * 将数据放入到待处理队列
     */
    public void putMessage(ComBean dataBean) {
//        LogUtils.d("receive serial data:" + DigitalTrans.byte2hex(dataBean.bRec));
//			MessageObserver.bq.add(b);
        //将接受到的串口信息发送到队列当中
        SerialPortService.emitter2.onNext(dataBean.bRec);
    }

}
