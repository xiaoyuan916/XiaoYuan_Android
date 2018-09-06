package com.sgcc.pda.hardware.protocol.p645;

import com.sgcc.pda.hardware.hardware.ICommunicate;
import com.sgcc.pda.hardware.protocol.IBaseProtocol;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/21
 * 红外基本功能接口
 */
public abstract class IP645EssentialMethod<T extends P645Frame> {
    // 基本操作使用的通讯协议
    protected IBaseProtocol<P645Frame> protocol = null;
    // 基本操作使用的通讯接口
    protected ICommunicate communicate = null;
    // 读取超时
    protected long readTimeout;

    /**
     * 设置通讯协议
     *
     * @param protocol 通讯协议类型
     */
    public void setProtocol(IBaseProtocol<P645Frame> protocol) {
        this.protocol = protocol;
    }

    /**
     * 获取当前通讯协议
     *
     * @return 通讯协议实例
     */
    public IBaseProtocol<P645Frame> getProtocol() {
        return protocol;
    }

    /**
     * 设置当前使用的硬件通讯接口
     *
     * @param communicate 硬件通讯接口实例
     */
    public void setCommunicate(ICommunicate communicate) {
        this.communicate = communicate;
    }

    /**
     * 获取当前使用的硬件通讯接口
     *
     * @return 当前通讯接口实例
     */
    public ICommunicate getCommunicate() {
        return communicate;
    }

    /**
     * 设置从设备读取数据超时
     *
     * @param timeout 超时（毫秒）
     */
    public void setReadTimeout(long timeout) {
        this.readTimeout = timeout;
    }

    /**
     * 获取从设备读取数据超时
     *
     * @return 超时（毫秒）
     */
    public long getReadTimeout() {
        return readTimeout;
    }


    /**
     * 获取与基础通讯匹配的协议帧新实例
     *
     * @return 协议帧实例
     */
    public abstract T getFrameNewInstance();

    /**
     * 根据自定义数据标识读取数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param retData      读取到的数据
     * @return 0-成功 其它-错误码
     */
    public abstract int readData(String meterAddress, String dataSign, StringBuffer retData);


    /**
     * 读取电能表数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param retData      读取到的数据
     * @param isMeter97    电表类型
     * @return 0-成功 其它-错误码
     */
    public abstract int readData(String meterAddress, String dataSign, StringBuffer retData, boolean isMeter97);

    /**
     * 根据自定义数据标识读取数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param retControl   电表返回的控制码
     * @param retData      读取到的数据
     * @return 0-成功 其它-错误码
     */
    public abstract int readData(String meterAddress, String dataSign, StringBuffer retControl, StringBuffer retData);

    /**
     * 根据自定义数据标识读取数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param dataNum      负荷记录块数
     * @param dateTime     时间 mmhhDDMMYY
     * @param retControl   电表返回的控制码
     * @param retData      读取到的数据
     * @return 0-成功 其它-错误码
     */
    public abstract int readData(String meterAddress, String dataSign, String dataNum, String dateTime, StringBuffer retControl, StringBuffer retData);
    /**
     * 根据自定义数据标识读取数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param retControl   电表返回的控制码
     * @param retData      读取到的数据
     * @return 0-成功 其它-错误码
     */
    public abstract int readDataContinue(String meterAddress, String dataSign, StringBuffer retControl, StringBuffer retData);

    /**
     * 向电表写入数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param keyLevel     密级
     * @param password     密码
     * @param operator     操作员编号
     * @param data         写入数据内容
     * @param mac          安全单元计算MAC
     * @param retData      返回-写入数据后电表的响应数据
     * @return 0-成功 其它-错误码
     */
    public abstract int writeData(String meterAddress, String dataSign, String keyLevel,
                                  String password, String operator, String data, String mac, StringBuffer retData);
    /**
     * 向电表写入数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param keyLevel     密级
     * @param password     密码
     * @param operator     操作员编号
     * @param data         写入数据内容
     * @param mac          安全单元计算MAC
     * @param retControl    返回-写入数据后电表的响应数据的控制码
     * @param retData      返回-写入数据后电表的响应数据
     * @return 0-成功 其它-错误码
     */
    public abstract int writeData(String meterAddress, String dataSign, String keyLevel,
                                  String password, String operator, String data, String mac, StringBuffer retControl, StringBuffer retData);

    /**
     * 电表进行远程控制
     *
     * @param meterAddress      表通讯地址
     * @param keyLevel          密级
     * @param operator          操作员编号
     * @param password          操作员密码
     * @param cipherOperateData 安全单元返回的控制命令密文
     * @param retData           返回-远程控制后电表返回的数据
     * @return 0-成功 其它-错误码
     */
    public abstract int powerOperate(String meterAddress, String keyLevel, String operator, String password, String cipherOperateData, StringBuffer retData);

    /**
     * 电表安全认证指令
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param operator     操作员编号
     * @param data         认证数据
     * @param retData      返回-安全认证后电表的返回数据
     * @return 0-成功 其它-错误码
     */
    public abstract int safetyCertification(String meterAddress, String dataSign, String operator, String data, StringBuffer retData);

    /**
     * 广播发送命令
     *
     * @param data 要广播的数据
     * @return 0-成功 其它-错误码
     */
    public abstract int broadcast(String data);

    /**
     * 进行一次通讯交互
     *
     * @param meterAddress 目标电表表地址或表地址掩码
     * @param control      控制字
     * @param data         待发送数据
     * @param retData      返回-通讯返回的数据域
     * @return 0-成功 其它-错误码
     */
    public abstract int commonCommunicate(String meterAddress, String control, String data, StringBuffer retData);

    /**
     * 判断返回的帧是否有异常出现
     *
     * @param upFrame   上行帧实例（请求帧）
     * @param downFrame 下行帧实例（响应帧）
     * @return 0-无异常 其它-异常值
     */
    public abstract int checkDownFrameException(T upFrame, T downFrame);

}
