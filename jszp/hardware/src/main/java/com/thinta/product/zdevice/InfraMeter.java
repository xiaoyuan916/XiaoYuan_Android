package com.thinta.product.zdevice;

import com.sgcc.pda.hardware.SOConfig;

/**
 * Created by ywj on 2015/12/8.
 * 电表红外通讯类
 */
public class InfraMeter
{
    static {
        if(SOConfig.useOldSO) {
            System.loadLibrary("zdevice");
        }
    }

    int fd = -1;

    /**
     * 打开红外通讯
     * @return 打开成功返回 True；打开失败返回 False
     */
    public boolean open()
    {
        int ret = jniOpen();
        if(ret == 0)
            fd = jniGetCommuFd();

        return ret == 0;
    }

    /**
     * 关闭红外通讯
     * @return 关闭成功返回 True；打开失败返回 False
     */
    public boolean close()
    {
        return jniClose() == 0;
    }

    /**
     * 设置红外通讯参数
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
     * 通过红外发送数据
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
     * 通过红外接收数据
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
     * 红外安全认证命令，0x03
     * @param address 电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param dataSign 数据标识
     * @param operatorId 操作者代码
     * @param authData 待认证数据，数据块倒序，数据内容正序
     * @return 获取红外认证结果数据，不包含数据标识
     */
    public byte[] safetyCertification(String address, long dataSign, long operatorId,
                                                        byte[] authData)
    {
        return jniSafetyCertification(fd, address, dataSign, operatorId, authData);
    }

    /**
     * 红外广播校时命令，0x08
     * 该命令无返回校验
     * @param nowTime 从公元1970年1月1日0时0分0秒算起到现在所经过的秒数
     * @return 发送成功返回True，失败返回False
     */
    public boolean broadcastTiming(long nowTime)
    {
        return jniBroadcastTiming(fd, nowTime);
    }

    /**
     * 红外读数据命令，0x11
     * @param address 电表地址，正序，12位，若不足将前补AA，可为 NULL 表示广播地址
     * @param dataSign 数据标识
     * @return 红外读取数据的结果字节数组，不包含数据标识
     */
    public byte[] dataRead(String address, long dataSign)
    {
        return jniDataRead(fd, address, dataSign);
    }

    /**
     * 红外读取通信地址命令，0x13
     * @return 红外读取到的电表地址
     */
    public String addressRead()
    {
        return jniAddressRead(fd);
    }

    /**
     * 红外写数据，0x14
     * @param address 电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param dataSign 数据标识
     * @param level 密级<br>
     *      0x98: auth_data为密文+MAC设置参数，忽略密码验证<br>
     *      0x99: auth_data为明文+MAC设置参数，忽略密码验证<br>
     *      0x02、0x04: auth_data为明文设置参数，验证密码
     * @param password 密码，有效范围为 0x000000 ~ 0x999999 的BCD码<br>
     *      仅当密级为0x02或0x04时有效
     * @param operatorId 操作者代码
     * @param authData 参数设置数据
     * @return 写入成功返回True，失败返回False
     */
    public boolean dataWrite(String address, long dataSign, byte level, int password,
                             long operatorId, byte[] authData)
    {
        return jniDataWrite(fd, address, dataSign, level, password, operatorId, authData);
    }

    /**
     * 红外写通信地址，0x15
     * 该命令必须与编程键配合使用，异常无应答
     * @param addressOld 旧电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param addressNew 新电表地址，正序，12位
     * @return 写入成功返回True，失败返回False
     */
    public boolean addressWrite(String addressOld, String addressNew)
    {
        return jniAddressWrite(fd, addressOld, addressNew);
    }

    /**
     * 红外冻结命令，0x16
     * @param address 电表地址，正序，12位，若不足将前补AA，可为 NULL 表示广播地址
     * @param freezeTime 冻结时间，以32位数据表示冻结时间的月日时分 部分<br>
     *      例: 0x010A1304，表示 1月10日19时4分<br>
     *      当月为 0x99 或 0x63 时，以月为周期冻结<br>
     *      当月、日为 0x99 或 0x63 时，以日为周期冻结<br>
     *      当月、日、时为 0x99 或 0x63 时，以小时为周期冻结<br>
     *      当月、日、时、分都为 0x99 或 0x63 时，则瞬时冻结
     * @return 冻结成功返回True，失败返回False
     */
    public boolean dataFreeze(String address, long freezeTime)
    {
        return jniDataFreeze(fd, address, freezeTime);
    }

    /**
     * 更改通信速度，0x17
     * @param address 电表地址，正序，12位，若不足将前补AA，可为 NULL 表示广播地址
     * @param rateSign 输入，通信速度特征字<br>
     *      0x02: 600bps<br>
     *      0x04: 1200bps<br>
     *      0x08: 2400bps<br>
     *      0x10: 4800bps<br>
     *      0x20: 9600bps<br>
     *      0x40: 19200bps
     * @return 更改成功返回True，失败返回False
     */
    public boolean commuRateChange(String address, byte rateSign)
    {
        return jniCommuRateChange(fd, address, rateSign);
    }

    /**
     * 红外修改密码，0x18
     * @param address 电表地址，正序，12位，若不足将前补AA，可为 NULL 表示广播地址
     * @param levelAuth 验证用密级，应不低于待修改密码的密级<br>
     *      范围为 0x00 ~ 0x09，0x00为最高权限<br>
     *      0x02 为电表清零、事件清零等级<br>
     *      0x04 为写数据、最大需量清零等级
     * @param passwordAuth 验证用密码，有效范围为 0x000000 ~ 0x999999 的BCD码
     * @param levelNew 待修改密码的密级<br>
     *      范围为 0x00 ~ 0x09，0x00为最高权限<br>
     *      0x02 为电表清零、事件清零等级<br>
     *      0x04 为写数据、最大需量清零等级
     * @param passwordNew 待修改密码，有效范围为 0x000000 ~ 0x999999 的BCD码
     * @return 修改成功返回True，失败返回False
     */
    public boolean passwordChange(String address,
                                                    byte levelAuth, int passwordAuth,
                                                    byte levelNew, int passwordNew)
    {
        return jniPasswordChange(fd, address, levelAuth, passwordAuth, levelNew, passwordNew);
    }

    /**
     * 红外最大需量清零，0x19
     * 该功能不支持 level = 0x99
     * @param address 电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param level 密级<br>
     *      0x98: 忽略密码，启用 auth_data 数据项<br>
     *      0x04: 忽略 auth_data 数据项，需配合编程键使用
     * @param password 密码，有效范围为 0x000000 ~ 0x999999 的BCD码<br>
     *      仅当密级为0x02或0x04时有效
     * @param operatorId 操作者代码
     * @param authData 最大需量清零数据密文，仅level为0x98时有效<br>
     *      解密后为8个字节，以N1~N8表示<br>
     *      N1: 0x19<br>
     *      N2: 保留，默认为0x00<br>
     *      N3~N8: 命令有效截止时间，格式为:YYMMDDhhmmss
     * @return 操作成功返回True，失败返回False
     */
    public boolean maximumDemandReset(String address, byte level, int password,
                                                        long operatorId, byte[] authData)
    {
        return jniMaximumDemandReset(fd, address, level, password, operatorId, authData);
    }

    /**
     * 红外电表清零，0x1A
     * 该功能不支持 level = 0x99
     * @param address 电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param level 密级<br>
     *      0x98: 忽略密码，启用 auth_data 数据项<br>
     *      0x04: 忽略 auth_data 数据项，需配合编程键使用
     * @param password 密码，有效范围为 0x000000 ~ 0x999999 的BCD码<br>
     *      仅当密级为0x02或0x04时有效
     * @param operatorId 操作者代码
     * @param authData 电表清零数据密文，仅level为0x98时有效<br>
     *      解密后为8个字节，以N1~N8表示<br>
     *      N1: 0x1A<br>
     *      N2: 保留，默认为0x00<br>
     *      N3~N8: 命令有效截止时间，格式为:YYMMDDhhmmss
     * @return 操作成功返回True，失败返回False
     */
    public boolean meterDataReset(String address, byte level, int password,
                                                    long operatorId, byte[] authData)
    {
        return jniMeterDataReset(fd, address, level, password, operatorId, authData);
    }

    /**
     * 红外事件清零，0x1B
     * 该功能不支持 level = 0x99
     * @param address 电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param level 密级<br>
     *      0x98: 忽略密码，启用 auth_data 数据项<br>
     *      0x04: 忽略 auth_data 数据项，需配合编程键使用
     * @param password 密码，有效范围为 0x000000 ~ 0x999999 的BCD码<br>
     *      仅当密级为0x02或0x04时有效
     * @param operatorId 操作者代码
     * @param authData 事件清零数据<br>
     *      密级为 0x02: 事件总清零: 0xFFFFFFFF<br>
     *          分项事件清零: 事件记录数据标识，其中DI0为0xFF<br>
     *      密级为 0x98: 事件清零的密文数据<br>
     *          解密后为8个字节，以N1~N8表示<br>
     *          N1: 0x1B<br>
     *          N2: 保留，默认为0x00<br>
     *          N3~N8: 命令有效截止时间，格式为:YYMMDDhhmmss
     * @return 操作成功返回True，失败返回False
     */
    public boolean eventReset(String address, byte level, int password,
                                                long operatorId, byte[] authData)
    {
        return jniEventReset(fd, address, level, password, operatorId, authData);
    }

    /**
     * 跳合闸、报警、保电，0x1C
     * @param address 电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param level 密级<br>
     *      0x98: 忽略密码，启用 auth_data 数据项<br>
     *      0x04: 忽略 auth_data 数据项
     * @param password 密码，有效范围为 0x000000 ~ 0x999999 的BCD码<br>
     *      仅当密级为0x02或0x04时有效
     * @param operatorId 操作者代码
     * @param authData 控制数据<br>
     *      密级为 0x02: auth_data为8个字节，以N1~N8表示<br>
     *          N1: 控制命令类型，0x1A:跳闸，0x1B:合闸允许，0x1C:直接合闸，0x2A:报警，0x2B:报警解除，0x3A:保电，0x3B:保电解除<br>
     *          N2: 保留，默认为0x00<br>
     *          N3~N8: 命令有效截止时间，格式为:ssmmhhDDMMYY<br>
     *      密级为 0x98: 控制的密文数据，解密后为8个字节，以N1~N8表示<br>
     *          N1: 控制命令类型，0x1A:跳闸，0x1B:合闸允许，0x1C:直接合闸，0x2A:报警，0x2B:报警解除，0x3A:保电，0x3B:保电解除<br>
     *          N2: 保留，默认为0x00<br>
     *          N3~N8: 命令有效截止时间，格式为:YYMMDDhhmmss
     * @return 操作成功返回True，失败返回False
     */
    public boolean meterControl(String address, byte level, int password,
                                                  long operatorId, byte[] authData)
    {
        return jniMeterControl(fd, address, level, password, operatorId, authData);
    }

    /**
     * 打开红外
     * @return 打开成功返回 0；打开失败返回 其它值
     */
    private native static int jniOpen();

    /**
     * 关闭红外
     * @return 关闭成功返回 0，失败返回 其它值
     */
    private native static int jniClose();

    /**
     * 获取红外接口通讯描述符
     * @return 通讯描述符值
     */
    private native static int jniGetCommuFd();

    /**
     * 通过红外发送数据
     * @param sendFrame 发送的数据字节数组
     * @param offset 发送数据在字节数组中的起始位置
     * @param length 发送数据的长度
     * @return 实际发送的字节数
     */
    private native static int jniComSend(byte[] sendFrame, int offset, int length);

    /**
     * 通过红外接收数据
     * @param maxLength 预计返回数据的最大长度
     * @param timeout 接收超时时间，单位毫秒
     * @param timeoutBlock 数据块接收超时，该值不应大于timeout，单位毫秒，设置为0表示使用默认值，默认为100
     * @return 接收到的数据内容
     */
    private native static byte[] jniComReceive(int maxLength, int timeout, int timeoutBlock);

    /**
     * 设置红外通讯参数
     * @param baudRate 波特率，支持 300、600、1200、2400、4800、9600、19200、38400、57600、115200 等
     * @param parity 校验位，0:无校验，1:奇校验，2:偶校验
     * @param dataBits 数据位，5:5个数据位，6:6个数据位，7:7个数据位，8:8个数据位
     * @param stopBits 停止位，1:一个停止位，2:两个停止位
     * @return 关闭成功返回 0，失败返回 其它值
     */
    private native static int jniSet(int baudRate, int parity, int dataBits, int stopBits);

    /**
     * 红外安全认证命令，0x03
     * @param fd 已打开的DL/T645报文通讯设备
     * @param address 电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param dataSign 数据标识
     * @param operatorId 操作者代码
     * @param authData 待认证数据，数据块倒序，数据内容正序
     * @return 获取红外认证结果数据，不包含数据标识
     */
    private native static byte[] jniSafetyCertification(int fd, String address, long dataSign, long operatorId,
                                                     byte[] authData);

    /**
     * 红外广播校时命令，0x08<br>
     * 该命令无返回校验
     * @param fd 已打开的DL/T645报文通讯设备
     * @param nowTime 从公元1970年1月1日0时0分0秒算起到现在所经过的秒数
     * @return 发送成功返回True，失败返回False
     */
    private native static boolean jniBroadcastTiming(int fd, long nowTime);

    /**
     * 红外读数据命令，0x11
     * @param fd 已打开的DL/T645报文通讯设备
     * @param address 电表地址，正序，12位，若不足将前补AA，可为 NULL 表示广播地址
     * @param dataSign 数据标识
     * @return 红外读取数据的结果字节数组，不包含数据标识
     */
    private native static byte[] jniDataRead(int fd, String address, long dataSign);

    /**
     * 红外读取通信地址命令，0x13
     * @param fd 已打开的DL/T645报文通讯设备
     * @return 红外读取到的电表地址
     */
    private native static String jniAddressRead(int fd);

    /**
     * 红外写数据，0x14
     * @param fd 已打开的DL/T645报文通讯设备
     * @param address 电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param dataSign 数据标识
     * @param level 密级<br>
     *      0x98: auth_data为密文+MAC设置参数，忽略密码验证<br>
     *      0x99: auth_data为明文+MAC设置参数，忽略密码验证<br>
     *      0x02、0x04: auth_data为明文设置参数，验证密码
     * @param password 密码，有效范围为 0x000000 ~ 0x999999 的BCD码<br>
     *      仅当密级为0x02或0x04时有效
     * @param operatorId 操作者代码
     * @param authData 参数设置数据
     * @return 写入成功返回True，失败返回False
     */
    private native static boolean jniDataWrite(int fd, String address, long dataSign,
                                               byte level, int password,
                                               long operatorId, byte[] authData);

    /**
     * 红外写通信地址，0x15<br>
     * 该命令必须与编程键配合使用，异常无应答
     * @param fd 已打开的DL/T645报文通讯设备
     * @param addressOld 旧电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param addressNew 新电表地址，正序，12位
     * @return 写入成功返回True，失败返回False
     */
    private native static boolean jniAddressWrite(int fd, String addressOld, String addressNew);

    /**
     * 红外冻结命令，0x16
     * @param fd 已打开的DL/T645报文通讯设备
     * @param address 电表地址，正序，12位，若不足将前补AA，可为 NULL 表示广播地址
     * @param freezeTime 冻结时间，以32位数据表示冻结时间的月日时分 部分<br>
     *      例: 0x010A1304，表示 1月10日19时4分<br>
     *      当月为 0x99 或 0x63 时，以月为周期冻结<br>
     *      当月、日为 0x99 或 0x63 时，以日为周期冻结<br>
     *      当月、日、时为 0x99 或 0x63 时，以小时为周期冻结<br>
     *      当月、日、时、分都为 0x99 或 0x63 时，则瞬时冻结
     * @return 冻结成功返回True，失败返回False
     */
    private native static boolean jniDataFreeze(int fd, String address, long freezeTime);

    /**
     * 更改通信速度，0x17
     * @param fd 已打开的DL/T645报文通讯设备
     * @param address 电表地址，正序，12位，若不足将前补AA，可为 NULL 表示广播地址
     * @param rateSign 通信速度特征字<br>
     *      0x02: 600bps<br>
     *      0x04: 1200bps<br>
     *      0x08: 2400bps<br>
     *      0x10: 4800bps<br>
     *      0x20: 9600bps<br>
     *      0x40: 19200bps
     * @return 更改成功返回True，失败返回False
     */
    private native static boolean jniCommuRateChange(int fd, String address, byte rateSign);

    /**
     * 红外修改密码，0x18
     * @param fd 已打开的DL/T645报文通讯设备
     * @param address 电表地址，正序，12位，若不足将前补AA，可为 NULL 表示广播地址
     * @param levelAuth 验证用密级，应不低于待修改密码的密级<br>
     *      范围为 0x00 ~ 0x09，0x00为最高权限<br>
     *      0x02 为电表清零、事件清零等级<br>
     *      0x04 为写数据、最大需量清零等级
     * @param passwordAuth 验证用密码，有效范围为 0x000000 ~ 0x999999 的BCD码
     * @param levelNew 待修改密码的密级<br>
     *      范围为 0x00 ~ 0x09，0x00为最高权限<br>
     *      0x02 为电表清零、事件清零等级<br>
     *      0x04 为写数据、最大需量清零等级
     * @param passwordNew 待修改密码，有效范围为 0x000000 ~ 0x999999 的BCD码
     * @return 修改成功返回True，失败返回False
     */
    private native static boolean jniPasswordChange(int fd, String address,
                                                    byte levelAuth, int passwordAuth,
                                                    byte levelNew, int passwordNew);

    /**
     * 红外最大需量清零，0x19<br>
     * 该功能不支持 level = 0x99
     * @param fd 已打开的DL/T645报文通讯设备
     * @param address 电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param level 密级<br>
     *      0x98: 忽略密码，启用 auth_data 数据项<br>
     *      0x04: 忽略 auth_data 数据项，需配合编程键使用
     * @param password 密码，有效范围为 0x000000 ~ 0x999999 的BCD码<br>
     *      仅当密级为0x02或0x04时有效
     * @param operatorId 操作者代码
     * @param authData 最大需量清零数据密文，仅level为0x98时有效<br>
     *      解密后为8个字节，以N1~N8表示<br>
     *      N1: 0x19<br>
     *      N2: 保留，默认为0x00<br>
     *      N3~N8: 命令有效截止时间，格式为:YYMMDDhhmmss
     * @return 操作成功返回True，失败返回False
     */
    private native static boolean jniMaximumDemandReset(int fd, String address, byte level, int password,
                                                       long operatorId, byte[] authData);

    /**
     * 红外电表清零，0x1A<br>
     * 该功能不支持 level = 0x99
     * @param fd 已打开的DL/T645报文通讯设备
     * @param address 电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param level 密级<br>
     *      0x98: 忽略密码，启用 auth_data 数据项<br>
     *      0x04: 忽略 auth_data 数据项，需配合编程键使用
     * @param password 密码，有效范围为 0x000000 ~ 0x999999 的BCD码<br>
     *      仅当密级为0x02或0x04时有效
     * @param operatorId 操作者代码
     * @param authData 电表清零数据密文，仅level为0x98时有效<br>
     *      解密后为8个字节，以N1~N8表示<br>
     *      N1: 0x1A<br>
     *      N2: 保留，默认为0x00<br>
     *      N3~N8: 命令有效截止时间，格式为:YYMMDDhhmmss
     * @return 操作成功返回True，失败返回False
     */
    private native static boolean jniMeterDataReset(int fd, String address, byte level, int password,
                                                        long operatorId, byte[] authData);

    /**
     * 红外事件清零，0x1B<br>
     * 该功能不支持 level = 0x99
     * @param fd 已打开的DL/T645报文通讯设备
     * @param address 电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param level 密级<br>
     *      0x98: 忽略密码，启用 auth_data 数据项<br>
     *      0x04: 忽略 auth_data 数据项，需配合编程键使用
     * @param password 密码，有效范围为 0x000000 ~ 0x999999 的BCD码<br>
     *      仅当密级为0x02或0x04时有效
     * @param operatorId 操作者代码
     * @param authData 事件清零数据<br>
     *      密级为 0x02: 事件总清零: 0xFFFFFFFF<br>
     *          分项事件清零: 事件记录数据标识，其中DI0为0xFF<br>
     *      密级为 0x98: 事件清零的密文数据<br>
     *          解密后为8个字节，以N1~N8表示<br>
     *          N1: 0x1B<br>
     *          N2: 保留，默认为0x00<br>
     *          N3~N8: 命令有效截止时间，格式为:YYMMDDhhmmss
     * @return 操作成功返回True，失败返回False
     */
    private native static boolean jniEventReset(int fd, String address, byte level, int password,
                                                    long operatorId, byte[] authData);

    /**
     * 跳合闸、报警、保电，0x1C
     * @param fd 已打开的DL/T645报文通讯设备
     * @param address 电表地址，正序，12位，若不足将前初AA，可为 NULL 表示广播地址
     * @param level 密级<br>
     *      0x98: 忽略密码，启用 auth_data 数据项<br>
     *      0x04: 忽略 auth_data 数据项
     * @param password 密码，有效范围为 0x000000 ~ 0x999999 的BCD码<br>
     *      仅当密级为0x02或0x04时有效
     * @param operatorId 操作者代码
     * @param authData 控制数据<br>
     *      密级为 0x02: auth_data为8个字节，以N1~N8表示<br>
     *          N1: 控制命令类型，0x1A:跳闸，0x1B:合闸允许，0x1C:直接合闸，0x2A:报警，0x2B:报警解除，0x3A:保电，0x3B:保电解除<br>
     *          N2: 保留，默认为0x00<br>
     *          N3~N8: 命令有效截止时间，格式为:ssmmhhDDMMYY<br>
     *      密级为 0x98: 控制的密文数据，解密后为8个字节，以N1~N8表示<br>
     *          N1: 控制命令类型，0x1A:跳闸，0x1B:合闸允许，0x1C:直接合闸，0x2A:报警，0x2B:报警解除，0x3A:保电，0x3B:保电解除<br>
     *          N2: 保留，默认为0x00<br>
     *          N3~N8: 命令有效截止时间，格式为:YYMMDDhhmmss
     * @return 操作成功返回True，失败返回False
     */
    private native static boolean jniMeterControl(int fd, String address, byte level, int password,
                                                long operatorId, byte[] authData);
}
