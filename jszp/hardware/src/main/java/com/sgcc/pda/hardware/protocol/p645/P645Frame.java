package com.sgcc.pda.hardware.protocol.p645;

import com.sgcc.pda.hardware.protocol.BaseFrame;
import com.sgcc.pda.hardware.protocol.IBaseProtocol;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.ErrorManager;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/21
 * 645协议通讯帧
 */
public class P645Frame extends BaseFrame {
    private String meterAddress; // 通信地址域 6字节
    private IBaseProtocol<P645Frame> protocol;

    /**
     * 默认构造函数
     * 根据协议确认帧起始符和结束符
     */
    public P645Frame() {
        this(new P645Protocol());
    }

    /**
     * 构造函数
     * 指定协议类型
     * 并根据协议类型设置起始符和结束符
     * @param protocol 协议实例
     */
    public P645Frame(IBaseProtocol<P645Frame> protocol) {
        this.protocol = protocol;
        setFrameHead(protocol.getFrameHead());
        setFrameTail(protocol.getFrameTail());
        setException(0);
    }

    /**
     * 获取通讯地址
     * @return 通讯地址字符串
     */
    public String getMeterAddress() { return meterAddress; }

    /**
     * 设置帧通讯地址
     * @param meterAddress 通讯地址字符串
     */
    public void setMeterAddress(String meterAddress) { this.meterAddress = meterAddress; }

    /**
     * 重载方法
     * 设置帧数据域，同时设置数据长度
     * @param data 数据域数据
     */
    @Override
    public void setData(String data){
        if (data == null) {
            data = "";
        }
        super.setData(data);
        super.setDataLength(protocol.computeDataLength(this));
    }

    /**
     * 获取通讯帧HEX字符串
     * @return 通讯帧字符串
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
        // P645规约规定电表表地址为倒序
        // sb.append(getMeterAddress());
        sb.append(DataConvert.strReverse(getMeterAddress(), 0, getMeterAddress().length()));
        sb.append(getFrameHead());
        sb.append(getControl());
        sb.append(getDataLength());
        // 根据规约规定，数据域中的每个值需要加33H发送
        setData(DataConvert.stringHexAddEach(getData(), (byte)0x33));
        sb.append(getData());
        // 每次进行数据组帧时，进行校验值的计算
        String check = protocol.computeCheckValue(this);
        if (check == null) {
            setException(ErrorManager.ErrorType.P645FrameError.getValue());
            return null;
        }
        this.setCheck(check);
        sb.append(getCheck());
        sb.append(getFrameTail());

        return sb.toString();
    }
}
