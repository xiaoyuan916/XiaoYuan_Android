package com.thinta.product.zdevice;

/**
 * Created by ywj on 2015/12/17.
 * 振中Z板命令操作
 */
public class ZCommand
{
    static {
        System.loadLibrary("zdevice");
    }

    /**
     * 红外命令总超时时间，数据内容4字节，默认3000，单位毫秒
     */
    public static final int SYSTEM_INFRA_TOTAL_TIMEOUT = 0x01;
    /**
     * 红外字节间超时时间，数据内容2字节，默认200，单位毫秒
     */
    public static final int SYSTEM_INFRA_BYTES_TIMEOUT = 0x02;
    /**
     * 蓝牙无连接自动关机时间，数据内容2字节，默认60，若为0xFFFF表示不自动关机，单位秒
     */
    public static final int SYSTEM_SHUTDOWN_NOBLUETOOTH_TIMEOUT = 0x03;
    /**
     * 系统无操作关机时间，数据内容2字节，默认300，若为0xFFFF表示不自动关机，单位秒
     */
    public static final int SYSTEM_SHUTDOWN_NOOPERATE_TIMEOUT = 0x04;
    /**
     * 红外通讯失败的重试次数，数据内容1字节，默认1
     */
    public static final int SYSTEM_INFRA_RETRY_TIMES = 0x05;
    /**
     * 外部串口通讯超时时间，数据内容4字节，默认3000，单位毫秒
     */
    public static final int SYSTEM_SERIAL_TOTAL_TIMEOUT = 0x06;
    /**
     * 扫描头读取超时时间，数据内容4字节，默认3000，单位毫秒
     */
    public static final int SYSTEM_BARCODE_TOTAL_TIMEOUT = 0x07;
    /**
     * ESAM通讯超时时间，数据内容4字节，默认3000，单位毫秒
     */
    public static final int SYSTEM_ESAM_TOTAL_TIMEOUT = 0x08;
    /**
     * 系统软件版本号，数据内容为字符串
     */
    public static final int SYSTEM_RD_APP_VERSION = 0x81;
    /**
     * 当前剩余电量百分式，数据内容1字节
     */
    public static final int SYSTEM_RD_CHARGE_PERCENT = 0x82;
    /**
     * 序列号，数据内容为字符串
     */
    public static final int SYSTEM_RD_SERIAL_NO = 0x83;
    /**
     * 生产日期，数据内容为字符串
     */
    public static final int SYSTEM_RD_PRODUCE_DATE = 0x85;
    /**
     * 硬件版本号，数据内容为16进制内容
     */
    public static final int SYSTEM_RD_HARD_VERSION = 0x86;
    /**
     * boot版本号，数据内容为字符串
     */
    public static final int SYSTEM_RD_BOOT_VERSION = 0x87;
    /**
     * 设备型号，数据内容为字符串
     */
    public static final int SYSTEM_RD_MODEL = 0x88;

    /**
     * 红外模块
     */
    public static final int DEVICE_INFRA = 0x01;
    /**
     * 扫描头模块
     */
    public static final int DEVICE_BARCODE = 0x02;
    /**
     * 串口模块
     */
    public static final int DEVICE_SERIAL = 0x03;
    /**
     * 蓝牙模块
     */
    public static final int DEVICE_BLUETOOTH = 0x04;
    /**
     * ESAM 模块
     */
    public static final int DEVICE_ESAM = 0x05;
    /**
     * 安全单元模块
     */
    public static final int DEVICE_SAFETYUNIT = 0x06;
    /**
     * TESAM 模块
     */
    public static final int DEVICE_TESAM = 0x07;

    /**
     * 升级 BOOT 版本
     */
    public static final int UPGRADE_BOOT = 0x00;
    /**
     * 升级 APP 版本
     */
    public static final int UPGRADE_APP = 0x01;

    /**
     * 打开Z板通讯
     * @return 打开成功返回 True；打开失败返回 False
     */
    public boolean open()
    {
        return jniOpen() == 0;
    }

    /**
     * 关闭Z板通讯
     * @return 关闭成功返回 True；打开失败返回 False
     */
    public boolean close()
    {
        return jniClose() == 0;
    }

    /**
     * 向Z板发送数据
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
     * 从Z板接收数据
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
     * 重启并进入BOOT模式，0x0E
     * @return 成功返回 true，失败返回 false
     */
    public boolean systemGotoBoot()
    {
        return jniSystemGotoBoot();
    }

    /**
     * 设置系统参数，0x31
     * @param type 输入，参数类型<br>
     *      SYSTEM_INFRA_TOTAL_TIMEOUT: 红外命令总超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_INFRA_BYTES_TIMEOUT: 红外字节间超时时间，默认200，单位毫秒<br>
     *      SYSTEM_SHUTDOWN_NOBLUETOOTH_TIMEOUT: 蓝牙无连接自动关机时间，默认60，若为0xFFFF表示不自动关机，单位秒<br>
     *      SYSTEM_SHUTDOWN_NOOPERATE_TIMEOUT: 系统无操作关机时间，默认300，若为0xFFFF表示不自动关机，单位秒<br>
     *      SYSTEM_INFRA_RETRY_TIMES: 红外通讯失败的重试次数，默认1<br>
     *      SYSTEM_SERIAL_TOTAL_TIMEOUT: 外部串口通讯超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_BARCODE_TOTAL_TIMEOUT: 扫描头读取超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_ESAM_TOTAL_TIMEOUT: ESAM通讯超时时间，默认3000，单位毫秒
     * @param data 输入，与参数类型对应的数据内容<br>
     *      数据内容长度依不同参数类型而不同
     * @return 成功返回 true，失败返回 false
     */
    public boolean systemSet(int type, String data)
    {
        return jniSystemSet(type, data);
    }

    /**
     * 读取系统参数，0x32
     * @param type 输入，参数类型<br>
     *      SYSTEM_INFRA_TOTAL_TIMEOUT: 红外命令总超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_INFRA_BYTES_TIMEOUT: 红外字节间超时时间，默认200，单位毫秒<br>
     *      SYSTEM_SHUTDOWN_NOBLUETOOTH_TIMEOUT: 蓝牙无连接自动关机时间，默认60，若为0xFFFF表示不自动关机，单位秒<br>
     *      SYSTEM_SHUTDOWN_NOOPERATE_TIMEOUT: 系统无操作关机时间，默认300，若为0xFFFF表示不自动关机，单位秒<br>
     *      SYSTEM_INFRA_RETRY_TIMES: 红外通讯失败的重试次数，默认1<br>
     *      SYSTEM_SERIAL_TOTAL_TIMEOUT: 外部串口通讯超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_BARCODE_TOTAL_TIMEOUT: 扫描头读取超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_ESAM_TOTAL_TIMEOUT: ESAM通讯超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_RD_APP_VERSION: 系统软件版本号，数据内容为字符串<br>
     *      SYSTEM_RD_CHARGE_PERCENT: 当前剩余电量百分式<br>
     *      SYSTEM_RD_SERIAL_NO: 序列号，数据内容为字符串<br>
     *      SYSTEM_RD_PRODUCE_DATE: 生产日期，数据内容为字符串<br>
     *      SYSTEM_RD_HARD_VERSION: 硬件版本号，数据内容为16进制内容<br>
     *      SYSTEM_RD_BOOT_VERSION: boot版本号，数据内容为字符串<br>
     *      SYSTEM_RD_MODEL: 设备型号，数据内容为字符串
     * @return 读取失败返回 null，读取成功返回相应的内容
     */
    public String systemGet(int type)
    {
        return jniSystemGet(type);
    }

    /**
     * 设置模块通讯参数，0x37
     * @param type 输入，参数类型<br>
     *      DEVICE_INFRA: 红外<br>
     *      DEVICE_BARCODE: 扫描头<br>
     *      DEVICE_SERIAL: 串口<br>
     *      DEVICE_BLUETOOTH: 蓝牙<br>
     *      DEVICE_ESAM: ESAM<br>
     *      DEVICE_SAFETYUNIT: 安全单元<br>
     *      DEVICE_TESAM: T-ESAM
     * @param baud_rate 输入，波特率，支持 300、600、1200、2400、4800、9600、19200、38400、57600、115200 等
     * @param parity 输入，校验位<br>
     *      0: 无校验<br>
     *      1: 奇校验<br>
     *      2: 偶校验
     * @param data_bits 输入，数据位<br>
     *      5: 5个数据位<br>
     *      6: 6个数据位<br>
     *      7: 7个数据位<br>
     *      8: 8个数据位
     * @param stop_bits 输入，停止位<br>
     *      1: 一个停止位<br>
     *      2: 两个停止位
     * @return 成功返回 true，失败返回 false
     */
    public boolean modelArgsSet(int type, int baud_rate, int parity, int data_bits,
                                int stop_bits)
    {
        return jniModelArgsSet(type, baud_rate, parity, data_bits, stop_bits);
    }

    /**
     * 模块参数恢复出厂设置，0x39
     * @param type 输入，参数类型，当前仅支持扫描头<br>
     *      DEVICE_BARCODE: 扫描头
     * @return 成功返回 true，失败返回 false
     */
    public boolean modelDefault(int type)
    {
        return jniModelDefault(type);
    }

    /**
     * 升级Z板内软件版本，0x3C、0x3D、0x3E、0x3F
     * @param type 输入，待升级的软件类型，目前支持 app 及 boot 升级
     *      UPGRADE_BOOT: boot
     *      UPGRADE_APP: app
     * @param softDatas 输入，待升级的Z板软件数据
     * @return 成功返回 true，失败返回 false
     */
    public boolean upgrade(int type, byte[] softDatas)
    {
        return jniUpgrade(type, softDatas);
    }

    /**
     * @deprecated
     * 读取软件版本号
     * @return 成功返回软件版本号信息字符串
     */
    public String softVersion()
    {
        String softVersion = jniSystemGet(SYSTEM_RD_APP_VERSION);
        if(softVersion == null)
            softVersion = "";

        return softVersion;
    }

    /**
     * @deprecated
     * Z板版本升级
     * @return 成功返回 true，失败返回 false
     */
    public boolean upgrade(byte[] softDatas)
    {
        return jniUpgrade(UPGRADE_APP, softDatas);
    }

    /**
     * 打开Z板通讯
     * @return 打开成功返回 0；打开失败返回 其它值
     */
    private native static int jniOpen();

    /**
     * 关闭EZ板通讯
     * @return 关闭成功返回 0，失败返回 其它值
     */
    private native static int jniClose();

    /**
     * 向Z板发送命令
     * @param sendFrame 发送的数据字节数组
     * @param offset 发送数据在字节数组中的起始位置
     * @param length 发送数据的长度
     * @return 实际发送的字节数
     */
    private native static int jniComSend(byte[] sendFrame, int offset, int length);

    /**
     * 接Z板返回的命令
     * @param maxLength 预计返回数据的最大长度
     * @param timeout 接收超时时间，单位毫秒
     * @param timeoutBlock 数据块接收超时，该值不应大于timeout，单位毫秒，设置为0表示使用默认值，默认为100
     * @return 接收到的数据内容
     */
    private native static byte[] jniComReceive(int maxLength, int timeout, int timeoutBlock);

    /**
     * 重启并进入BOOT模式，0x0E
     * @return 成功返回 true，失败返回 false
     */
    private native static boolean jniSystemGotoBoot();

    /**
     * 设置系统参数，0x31
     * @param type 输入，参数类型<br>
     *      SYSTEM_INFRA_TOTAL_TIMEOUT: 红外命令总超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_INFRA_BYTES_TIMEOUT: 红外字节间超时时间，默认200，单位毫秒<br>
     *      SYSTEM_SHUTDOWN_NOBLUETOOTH_TIMEOUT: 蓝牙无连接自动关机时间，默认60，若为0xFFFF表示不自动关机，单位秒<br>
     *      SYSTEM_SHUTDOWN_NOOPERATE_TIMEOUT: 系统无操作关机时间，默认300，若为0xFFFF表示不自动关机，单位秒<br>
     *      SYSTEM_INFRA_RETRY_TIMES: 红外通讯失败的重试次数，默认1<br>
     *      SYSTEM_SERIAL_TOTAL_TIMEOUT: 外部串口通讯超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_BARCODE_TOTAL_TIMEOUT: 扫描头读取超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_ESAM_TOTAL_TIMEOUT: ESAM通讯超时时间，默认3000，单位毫秒
     * @param data 输入，与参数类型对应的数据内容<br>
     *      数据内容长度依不同参数类型而不同
     * @return 成功返回 true，失败返回 false
     */
    private native static boolean jniSystemSet(int type, String data);

    /**
     * 读取系统参数，0x32
     * @param type 输入，参数类型<br>
     *      SYSTEM_INFRA_TOTAL_TIMEOUT: 红外命令总超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_INFRA_BYTES_TIMEOUT: 红外字节间超时时间，默认200，单位毫秒<br>
     *      SYSTEM_SHUTDOWN_NOBLUETOOTH_TIMEOUT: 蓝牙无连接自动关机时间，默认60，若为0xFFFF表示不自动关机，单位秒<br>
     *      SYSTEM_SHUTDOWN_NOOPERATE_TIMEOUT: 系统无操作关机时间，默认300，若为0xFFFF表示不自动关机，单位秒<br>
     *      SYSTEM_INFRA_RETRY_TIMES: 红外通讯失败的重试次数，默认1<br>
     *      SYSTEM_SERIAL_TOTAL_TIMEOUT: 外部串口通讯超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_BARCODE_TOTAL_TIMEOUT: 扫描头读取超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_ESAM_TOTAL_TIMEOUT: ESAM通讯超时时间，默认3000，单位毫秒<br>
     *      SYSTEM_RD_APP_VERSION: 系统软件版本号，数据内容为字符串<br>
     *      SYSTEM_RD_CHARGE_PERCENT: 当前剩余电量百分式<br>
     *      SYSTEM_RD_SERIAL_NO: 序列号，数据内容为字符串<br>
     *      SYSTEM_RD_PRODUCE_DATE: 生产日期，数据内容为字符串<br>
     *      SYSTEM_RD_HARD_VERSION: 硬件版本号，数据内容为16进制内容<br>
     *      SYSTEM_RD_BOOT_VERSION: boot版本号，数据内容为字符串<br>
     *      SYSTEM_RD_MODEL: 设备型号，数据内容为字符串
     * @return 读取失败返回 null，读取成功返回相应的内容
     */
    private native static String jniSystemGet(int type);

    /**
     * 设置模块通讯参数，0x37
     * @param type 输入，参数类型<br>
     *      DEVICE_INFRA: 红外<br>
     *      DEVICE_BARCODE: 扫描头<br>
     *      DEVICE_SERIAL: 串口<br>
     *      DEVICE_BLUETOOTH: 蓝牙<br>
     *      DEVICE_ESAM: ESAM<br>
     *      DEVICE_SAFETYUNIT: 安全单元<br>
     *      DEVICE_TESAM: T-ESAM
     * @param baud_rate 输入，波特率，支持 300、600、1200、2400、4800、9600、19200、38400、57600、115200 等
     * @param parity 输入，校验位<br>
     *      0: 无校验<br>
     *      1: 奇校验<br>
     *      2: 偶校验
     * @param data_bits 输入，数据位<br>
     *      5: 5个数据位<br>
     *      6: 6个数据位<br>
     *      7: 7个数据位<br>
     *      8: 8个数据位
     * @param stop_bits 输入，停止位<br>
     *      1: 一个停止位<br>
     *      2: 两个停止位
     * @return 成功返回 true，失败返回 false
     */
    private native static boolean jniModelArgsSet(int type, int baud_rate, int parity,
                                                  int data_bits, int stop_bits);

    /**
     * 模块参数恢复出厂设置，0x39
     * @param type 输入，参数类型，当前仅支持扫描头<br>
     *      DEVICE_BARCODE: 扫描头
     * @return 成功返回 true，失败返回 false
     */
    private native static boolean jniModelDefault(int type);

    /**
     * 升级Z板内软件版本，0x3C、0x3D、0x3E、0x3F
     * @param type 输入，待升级的软件类型，目前支持 app 及 boot 升级
     *      UPGRADE_BOOT: boot
     *      UPGRADE_APP: app
     * @param softDatas 输入，待升级的Z板软件数据
     * @return 成功返回 true，失败返回 false
     */
    private native static boolean jniUpgrade(int type, byte[] softDatas);

}
