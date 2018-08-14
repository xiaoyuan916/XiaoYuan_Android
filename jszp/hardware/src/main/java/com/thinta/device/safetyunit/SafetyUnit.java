package com.thinta.device.safetyunit;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/4/12.
 * A1-B安全单元操作接口
 */
public class SafetyUnit {
    static {
        System.loadLibrary("safetyunit");
    }

    public native static boolean open();

    public native static boolean close();

    public native static int status();

    public native static boolean set(int baudRate, int parity, int dataBits, int stopBits);

    public native static int send(byte[] buffer, int offset, int length);

    public native static byte[] receive(int timeout, int partTime);
}
