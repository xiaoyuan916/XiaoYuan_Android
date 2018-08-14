package com.sgcc.pda.hardware.protocol.safetyunit;


import com.sgcc.pda.hardware.protocol.BaseFrame;
import com.sgcc.pda.hardware.protocol.IBaseProtocol;
import com.sgcc.pda.hardware.util.ErrorManager;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/21
 * 安全单元通讯帧
 */
public class SafetyUnitFrame extends BaseFrame {
    private String statusCode; // 状态码 1字节 只有下行帧有

    /**
     * 获取状态码
     * @return 状态码
     */
    public String getStatusCode() { return statusCode; }

    /**
     * 设置状态码
     * @param statusCode 状态码
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        super.setDataLength(protocol.computeDataLength(this));
    }

    private String mainSign; // 主功能标识 1字节

    /**
     * 获取主功能标识
     * @return 主功能标识
     */
    public String getMainSign() { return mainSign; }

    /**
     * 设置主功能标识
     * @param mainSign 主功能标识
     */
    public void setMainSign(String mainSign) {
        this.mainSign = mainSign;
        super.setDataLength(protocol.computeDataLength(this));
    }

    /**
     * 该通迅帧对应的协议接口
     */
    IBaseProtocol<SafetyUnitFrame> protocol;

    /**
     * 默认构造函数
     */
    public SafetyUnitFrame() {
        this(new SafetyUnitProtocol());
    }

    /**
     * 构造函数
     * @param protocol 协议接口实例
     */
    public SafetyUnitFrame(IBaseProtocol<SafetyUnitFrame> protocol) {
        this.protocol = protocol;
        setFrameHead(protocol.getFrameHead());
        setFrameTail(protocol.getFrameTail());
        setException(0);
        setStatusCode("");
    }

    /**
     * 重写设置命令码（响应码）方法
     * 进行数据设置后，重新计算帧长度
     * @param control 命令码
     */
    @Override
    public void setControl(String control) {
        super.setControl(control);
        super.setDataLength(protocol.computeDataLength(this));
    }

    /**
     * 重写设置数据域方法
     * 设置数据后，重新计算帧长度
     * @param data 数据域数据
     */
    @Override
    public void setData(String data) {
        super.setData(data);
        super.setDataLength(protocol.computeDataLength(this));
    }

    /**
     * 获取发送HEX字符串
     * @return HEX字符串-获取成功 null-失败（帧异常情况可以通过getException()获取）
     */
    @Override
    public String getString() {
        // 如果已经存在异常信息，则不进行数据的重新判断，直接输出结果
        if (getException() != 0) {
            return null;
        }

        // 根据规约规定的规则检测帧数据是否合法
        int error = protocol.checkFrameError(this);
        if (error != 0) {
            this.setException(error);
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(getFrameHead());
        sb.append(getDataLength());
        sb.append(getMainSign());
        sb.append(getControl());
        sb.append(getStatusCode() == null ? "" : getStatusCode());
        sb.append(getData() == null ? "" : getData());

        // 每次进行数据组帧时，进行校验值的计算
        String check = protocol.computeCheckValue(this);
        if (check == null) { // 如果没有计算出校验值，则是数据帧有错误
            setException(ErrorManager.ErrorType.SafetyFrameError.getValue());
            return null;
        }
        this.setCheck(check);
        sb.append(getCheck());
        sb.append(getFrameTail());

        return sb.toString();
    }
}
