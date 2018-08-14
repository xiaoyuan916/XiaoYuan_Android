package com.thinta.device.infra;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/4/11.
 * A1-B红外设备操作接口实现
 */
public class Infra {
    static {
        System.loadLibrary("infra");
    }

    public native static boolean open();

    public native static boolean close();

    public native static int status();

    public native static boolean set(int baudRate, int parity, int dataBits, int stopBits);

    public native static int send(byte[] buffer, int offset, int length);

    public native static byte[] receive(int timeout, int partTime);
}
