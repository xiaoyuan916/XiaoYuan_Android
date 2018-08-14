package com.thinta.product.zdevice;

import com.sgcc.pda.hardware.SOConfig;

/**
 * Created by ywj on 2015/12/2.
 * 串口操作类
 */
public class SerialPort {
    static {
        if (SOConfig.useOldSO) {
            System.loadLibrary("zdevice");
        }
    }

    private int portFd = -1;

    /**
     * 打开外部串口
     *
     * @param device 串口设备文件名
     * @return 打开成功返回 True；打开失败返回 False
     */
    public boolean open(String device) {
        portFd = jniOpen(device);
        return portFd == 0;
    }

    /**
     * 关闭串口通讯
     *
     * @return 关闭成功返回 True；打开失败返回 False
     */
    public boolean close() {
        int ret = jniClose(portFd);
        if (ret == 0) {
            portFd = -1;
            return true;
        }

        return false;
    }

    /**
     * 查询串口通讯状态
     *
     * @return 串口未启用返回0，已启用返回1，查询失败返回-1
     */
    public int status() {
        if (portFd >= 0)
            return 1;
        return 0;
    }

    /**
     * 设置串口通讯参数
     *
     * @param baudRate 波特率，支持 300、600、1200、2400、4800、9600、19200、38400、57600、115200 等
     * @param parity   校验位，0:无校验，1:奇校验，2:偶校验
     * @param dataBits 数据位，5:5个数据位，6:6个数据位，7:7个数据位，8:8个数据位
     * @param stopBits 停止位，1:一个停止位，2:两个停止位
     * @return 关闭成功返回 True，失败返回 False
     */
    public boolean set(int baudRate, int parity, int dataBits, int stopBits) {
        return jniSet(portFd, baudRate, parity, dataBits, stopBits) == 0;
    }

    /**
     * 通过串口发送数据
     *
     * @param sendFrame 发送的数据字节数组
     * @param offset    发送数据在字节数组中的起始位置
     * @param length    发送数据的长度
     * @return 实际发送的字节数
     */
    public int send(byte[] sendFrame, int offset, int length) {
        return jniComSend(portFd, sendFrame, offset, length);
    }

    /**
     * 通过串口接收数据
     *
     * @param maxLength    预计返回数据的最大长度
     * @param timeout      接收超时时间，单位毫秒
     * @param timeoutBlock 数据块接收超时，该值不应大于timeout，单位毫秒，设置为0表示使用默认值，默认为100
     * @return 接收到的数据内容
     */
    public byte[] receive(int maxLength, int timeout, int timeoutBlock) {
        return jniComReceive(portFd, maxLength, timeout, timeoutBlock);
    }

    /**
     * 打开外部串口
     *
     * @param device 串口设备文件名
     * @return 打开成功返回值 >= 0；打开失败返回 -1
     */
    private native static int jniOpen(String device);

    /**
     * 关闭串口通讯
     *
     * @param fd 已打开的串口设备文件标识
     * @return 关闭成功返回 0，失败返回 其它值
     */
    private native static int jniClose(int fd);

    /**
     * 通过串口发送数据
     *
     * @param fd        已打开的串口设备文件标识
     * @param sendFrame 发送的数据字节数组
     * @param offset    发送数据在字节数组中的起始位置
     * @param length    发送数据的长度
     * @return 实际发送的字节数
     */
    private native static int jniComSend(int fd, byte[] sendFrame, int offset, int length);

    /**
     * 通过串口接收数据
     *
     * @param fd           已打开的串口设备文件标识
     * @param maxLength    预计返回数据的最大长度
     * @param timeout      接收超时时间，单位毫秒
     * @param timeoutBlock 数据块接收超时，该值不应大于timeout，单位毫秒，设置为0表示使用默认值，默认为100
     * @return 接收到的数据内容
     */
    private native static byte[] jniComReceive(int fd, int maxLength, int timeout, int timeoutBlock);

    /**
     * 设置串口通讯参数
     *
     * @param fd       已打开的串口设备文件标识
     * @param baudRate 波特率，支持 300、600、1200、2400、4800、9600、19200、38400、57600、115200 等
     * @param parity   校验位，0:无校验，1:奇校验，2:偶校验
     * @param dataBits 数据位，5:5个数据位，6:6个数据位，7:7个数据位，8:8个数据位
     * @param stopBits 停止位，1:一个停止位，2:两个停止位
     * @return 关闭成功返回 0，失败返回 其它值
     */
    private native static int jniSet(int fd, int baudRate, int parity, int dataBits, int stopBits);

    /**
     * 获取串口设备名称
     *
     * @return 返回的设备名称列表
     */
    public native static String[] getSerialPorts();
}
