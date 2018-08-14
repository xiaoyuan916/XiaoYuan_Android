package com.sgcc.pda.hardware.protocol.safetyunit;

import com.sgcc.pda.hardware.hardware.ICommunicate;
import com.sgcc.pda.hardware.protocol.IBaseProtocol;



/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/29.
 * 安全单元通讯基础类
 */
public abstract class ISafetyUnitEssentialMethod<T extends SafetyUnitFrame> {
    // 基本操作使用的通讯协议
    protected IBaseProtocol<SafetyUnitFrame> protocol = null;
    // 基本操作使用的通讯接口
    protected ICommunicate communicate = null;
    // 读取超时
    protected long readTimeout;

    /**
     * 设置通讯协议
     * @param protocol 通讯协议类型
     */
    public void setProtocol(IBaseProtocol<SafetyUnitFrame> protocol) {
        this.protocol = protocol;
    }

    /**
     * 获取当前通讯协议
     * @return 通讯协议实例
     */
    public IBaseProtocol<SafetyUnitFrame> getProtocol() {
        return protocol;
    }

    /**
     * 设置当前使用的硬件通讯接口
     * @param communicate 硬件通讯接口实例
     */
    public void setCommunicate(ICommunicate communicate) {
        this.communicate = communicate;
    }

    /**
     * 获取当前使用的硬件通讯接口
     * @return 当前通讯接口实例
     */
    public ICommunicate getCommunicate() {
        return communicate;
    }

    /**
     * 设置从设备读取数据超时
     * @param timeout 超时（毫秒）
     */
    public void setReadTimeout(long timeout) {
        this.readTimeout = timeout;
    }

    /**
     * 获取从设备读取数据超时
     * @return 超时（毫秒）
     */
    public long getReadTimeout() {
        return readTimeout;
    }


    /**
     * 获取与基础通讯匹配的协议帧新实例
     * @return 协议帧实例
     */
    public abstract T getFrameNewInstance();

    /**
     * 向安全单元发送命令并获取安全单元返回
     * @param mainSign 主功能标识
     * @param control 命令码
     * @param data 发送命令
     * @param retData 读取到的数据
     * @return 0-成功 其它-错误码
     */
    public abstract int commitDown(String mainSign, String control, String data, StringBuffer retData);
    /**
     * 从安全单元获取一次通讯帧
     * 默认的主功能标识为01否则会返回错误
     * @param mainSign 返回-主功能标识
     * @param control 返回-请求命令码
     * @param status 返回-状态码
     * @param data 返回-请求数据域
     * @return 0-成功 其它-错误码
     */
    public abstract int getFrame(StringBuffer mainSign, StringBuffer control, StringBuffer status, StringBuffer data);

    /**
     * 向安全单元发送一次通讯帧
     * @param mainSign 主功能标识
     * @param control 发送的命令码
     * @param status 状态码
     * @param data 发送数据
     * @return 0-成功 其它-错误码
     */
    public abstract int sendFrame(String mainSign, String control, String status, String data);
}
