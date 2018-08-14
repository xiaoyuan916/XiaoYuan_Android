package com.sgcc.pda.hardware.resam.beans;

/**
 * Created by 王自盈 on 2017/4/24.
 */

public class Frame {
    //帧长度
    private int dataLen;
    //命令类别
    private byte cla;
    //命令类别 指令代码
    private byte ins;
    //完成指令代码的参考符号
    private byte p1;
    //完成指令代码的参考符号
    private byte p2;
    //数据域data
    private byte[] data;
    //发送数据的校验值
    private byte lrc1;
    //接收数据返回的状态字
    private byte sw1;
    //接收数据返回的状态字
    private byte sw2;
    //接收数据的校验值
    private byte lrc2;
    //下行帧
    private byte[] downBytes;
    //下行帧数据域
    private byte[] downData;
    //响应数据字符串
    private String upValue;

    public String getUpValue() {
        return upValue;
    }

    public void setUpValue(String upValue) {
        this.upValue = upValue;
    }

    public byte[] getDownData() {
        return downData;
    }

    public void setDownData(byte[] downData) {
        this.downData = downData;
    }

    public byte getCla() {
        return cla;
    }

    public void setCla(byte cla) {
        this.cla = cla;
    }

    public byte getIns() {
        return ins;
    }

    public void setIns(byte ins) {
        this.ins = ins;
    }

    public byte getP1() {
        return p1;
    }

    public void setP1(byte p1) {
        this.p1 = p1;
    }

    public byte getP2() {
        return p2;
    }

    public void setP2(byte p2) {
        this.p2 = p2;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte getLrc1() {
        return lrc1;
    }

    public void setLrc1(byte lrc1) {
        this.lrc1 = lrc1;
    }

    public byte getSw1() {
        return sw1;
    }

    public void setSw1(byte sw1) {
        this.sw1 = sw1;
    }

    public byte getSw2() {
        return sw2;
    }

    public void setSw2(byte sw2) {
        this.sw2 = sw2;
    }

    public byte getLrc2() {
        return lrc2;
    }

    public void setLrc2(byte lrc2) {
        this.lrc2 = lrc2;
    }

    public byte[] getDownBytes() {
        return downBytes;
    }

    public void setDownBytes(byte[] downBytes) {
        this.downBytes = downBytes;
    }

    public int getDataLen() {
        return dataLen;
    }

    public void setDataLen(int dataLen) {
        this.dataLen = dataLen;
    }
}
