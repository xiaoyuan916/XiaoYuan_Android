package com.thinta.device;


public class UsbSerialPort {

    public UsbSerialPort() {
    }

    public static native int open();

    public static native int close();

    public static native int set(int var0, int var1, int var2, int var3);

    public static native int send(byte[] var0, int var1);

    public static native int receive(byte[] var0, int var1, int var2);

    public static native int clearAllBuffer();

    public static native int clearSendBuffer();

    public static native int clearReceiveBuffer();

    static {
        System.loadLibrary("ThintaPlatform");
    }
}
