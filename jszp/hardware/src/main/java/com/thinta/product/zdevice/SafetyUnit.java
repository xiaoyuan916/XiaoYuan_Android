package com.thinta.product.zdevice;

import com.sgcc.pda.hardware.SOConfig;

/**
 * Created by ywj on 2016/03/18.
 * 安全单元操作类
 */
public class SafetyUnit
{
    static {
        if(SOConfig.useOldSO) {
            System.loadLibrary("zdevice");
        }
    }

    /**
     * 打开条码扫描功能
     * @return 打开成功返回 True；打开失败返回 False
     */
    public boolean open()
    {
        return jniOpen() == 0;
    }

    /**
     * 关闭条码扫描功能
     * @return 关闭成功返回 True；打开失败返回 False
     */
    public boolean close()
    {
        return jniClose() == 0;
    }

    /**
     * 设置与条码扫描模块的通讯参数
     * @param baudRate 波特率，支持 300、600、1200、2400、4800、9600、19200、38400、57600、115200 等
     * @param parity 校验位，0:无校验，1:奇校验，2:偶校验
     * @param dataBits 数据位，5:5个数据位，6:6个数据位，7:7个数据位，8:8个数据位
     * @param stopBits 停止位，1:一个停止位，2:两个停止位
     * @return 关闭成功返回 True，失败返回 False
     */
    public boolean set(int baudRate, int parity, int dataBits, int stopBits)
    {
        return jniSet(baudRate, parity, dataBits, stopBits) == 0;
    }

    /**
     * 向条码扫描模块发送命令
     * @param sendFrame 发送的数据字节数组
     * @param offset 发送数据在字节数组中的起始位置
     * @param length 发送数据的长度
     * @return 实际发送的字节数
     */
    public int send(byte[] sendFrame, int offset, int length)
    {
        return jniComSend(sendFrame, offset, length);
    }

    /**
     * 接收条码扫描模块返回的命令
     * @param maxLength 预计返回数据的最大长度
     * @param timeout 接收超时时间，单位毫秒
     * @param timeoutBlock 数据块接收超时，该值不应大于timeout，单位毫秒，设置为0表示使用默认值，默认为100
     * @return 接收到的数据内容
     */
    public byte[] receive(int maxLength, int timeout, int timeoutBlock)
    {
        return jniComReceive(maxLength, timeout, timeoutBlock);
    }

    /**
     * 安全单元与业务卡同步，0x15
     * @return 成功返回True，失败返回False
     */
    public boolean sync()
    {
        return jniSync(5000);
    }

    /**
     * 打开安全单元
     * @return 打开成功返回 0；打开失败返回 其它值
     */
    private native static int jniOpen();

    /**
     * 关闭安全单元
     * @return 关闭成功返回 0，失败返回 其它值
     */
    private native static int jniClose();

    /**
     * 向安全单元模块发送命令
     * @param sendFrame 发送的数据字节数组
     * @param offset 发送数据在字节数组中的起始位置
     * @param length 发送数据的长度
     * @return 实际发送的字节数
     */
    private native static int jniComSend(byte[] sendFrame, int offset, int length);

    /**
     * 接收安全单元模块返回的命令
     * @param maxLength 预计返回数据的最大长度
     * @param timeout 接收超时时间，单位毫秒
     * @param timeoutBlock 数据块接收超时，该值不应大于timeout，单位毫秒，设置为0表示使用默认值，默认为100
     * @return 接收到的数据内容
     */
    private native static byte[] jniComReceive(int maxLength, int timeout, int timeoutBlock);

    /**
     * 设置与安全单元模块的通讯参数
     * @param baudRate 波特率，支持 300、600、1200、2400、4800、9600、19200、38400、57600、115200 等
     * @param parity 校验位，0:无校验，1:奇校验，2:偶校验
     * @param dataBits 数据位，5:5个数据位，6:6个数据位，7:7个数据位，8:8个数据位
     * @param stopBits 停止位，1:一个停止位，2:两个停止位
     * @return 关闭成功返回 0，失败返回 其它值
     */
    private native static int jniSet(int baudRate, int parity, int dataBits, int stopBits);

    /**
     * 安全单元与业务卡同步，0x15
     * @param timeout 输入，同步操作的超时时间，单位毫秒
     * @return 成功返回True，失败返回False
     */
    private native static boolean jniSync(int timeout);

}
