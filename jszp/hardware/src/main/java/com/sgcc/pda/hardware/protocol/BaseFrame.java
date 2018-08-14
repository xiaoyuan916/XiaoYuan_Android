package com.sgcc.pda.hardware.protocol;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/21
 * 通讯帧基类
 */
public abstract class BaseFrame {
    // 控制字符
    private String control;
    // 数据域长度
    private String dataLength;
    // 数据域
    private String data;
    // 异常情况
    private int exception;
    // 校验值
    private String check;
    // 通讯帧起始位
    private String frameHead;
    // 通讯帧结束位
    private String frameTail;

    public String getControl() { return control; }
    public void setControl(String control) { this.control = control; }

    public String getDataLength() { return dataLength; }
    public void setDataLength(String dataLength) { this.dataLength = dataLength; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public int getException() { return exception; }
    public void setException(int exception) { this.exception = exception; }

    public String getCheck() { return check; }
    public void setCheck(String check) { this.check = check; }

    public String getFrameHead() { return frameHead; }
    protected void setFrameHead(String frameHead) { this.frameHead = frameHead; }

    public String getFrameTail() { return frameTail; }
    protected void setFrameTail(String frameTail) { this.frameTail = frameTail; }

    public abstract String getString();
}
