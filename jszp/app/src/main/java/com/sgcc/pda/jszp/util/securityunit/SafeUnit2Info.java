package com.sgcc.pda.jszp.util.securityunit;


import android.os.Parcel;
import android.os.Parcelable;

import com.sgcc.pda.jszp.bean.BaseEntity;

/**
 * Created by qinling on 2018/5/15 10:47
 * Description:
 */
public class SafeUnit2Info extends StatusBean {

    /**
     * 获取安全单元信息
     *
     * @param status                        安全单元状态字
     * @param appVersion                    软件版本号
     * @param version                       硬件版本号
     * @param cEsamNum                      C-ESAM序列号
     * @param operator                      操作者代码
     * @param operatorPower                 权限
     * @param cover                         权限掩码
     * @param operatorInfo                  操作者信息
     * @param yEsamNum                      Y-ESAM序列号
     * @param yEsamVersion                  Y-ESAM对称密钥密钥版本
     * @param mainStationCertificateVersion 主站证书版本号
     * @param terminalCertificateVersion    终端证书版本号
     * @param mainStationCertificateNum     主站证书序列号
     * @param terminalCertificateNum        终端证书序列号
     * @param counter                       当前计数器
     * @param encryptNum                    转加密剩余次数
     * @param labelSecretVersion            标签密钥版本
     * @param mainStationCertificate        主站证书
     * @param terminalCertificate           终端证书
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     *


    /*****************************/

    private String status;                 //安全单元状态字
    private String appVersion;                 //软件版本号
    private String version;//                     硬件版本号
    private String cEsamNum;//                C-ESAM序列号
    private String operator;//                操作者代码
    private String operatorPower;//                权限
    private String cover;//               权限掩码
    private String operatorInfo;//              操作者信息
    private String yEsamNum;//              Y-ESAM序列号
    private String yEsamVersion;//              Y-ESAM对称密钥密钥版本
    private String mainStationCertificateVersion;// 主站证书版本号
    private String terminalCertificateVersion;//   终端证书版本号
    private String mainStationCertificateNum;//    主站证书序列号
    private String terminalCertificateNum;//     终端证书序列号
    private String counter;//      当前计数器


    private String encryptNum;//       转加密剩余次数
    private String labelSecretVersion;//        标签密钥版本
    private String mainStationCertificate;//      主站证书
    private String terminalCertificate;//       终端证书

    private String systemVersion;//       操作系统版本号
    private String userName;//      用户名
    private Integer remainCount;//     剩余密码尝试次数   null 则是正常 ， -1 是字符串转数字失败  >=0 则是密码错误
    private String yEsamcounter;//      Yesam计数器

    public String getYEsamcounter() {
        return yEsamcounter;
    }

    public void setYEsamcounter(String yEsamcounter) {
        this.yEsamcounter = yEsamcounter;
    }

    public Integer getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(Integer remainCount) {
        this.remainCount = remainCount;
    }

    public String getCEsamVersion() {
        return cEsamVersion;
    }

    public void setCEsamVersion(String cEsamVersion) {
        this.cEsamVersion = cEsamVersion;
    }

    private String cEsamVersion;//      用户名
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }





    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSoftVersion() {
        return appVersion;
    }

    public void setSoftVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getHardVersion() {
        return version;
    }

    public void setHardVersion(String version) {
        this.version = version;
    }

    public String getCEsamNum() {
        return cEsamNum;
    }

    public void setCEsamNum(String cEsamNum) {
        this.cEsamNum = cEsamNum;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorPower() {
        return operatorPower;
    }

    public void setOperatorPower(String operatorPower) {
        this.operatorPower = operatorPower;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getOperatorInfo() {
        return operatorInfo;
    }

    public void setOperatorInfo(String operatorInfo) {
        this.operatorInfo = operatorInfo;
    }

    public String getYEsamNum() {
        return yEsamNum;
    }

    public void setYEsamNum(String yEsamNum) {
        this.yEsamNum = yEsamNum;
    }

    public String getYEsamVersion() {
        return yEsamVersion;
    }

    public void setYEsamVersion(String yEsamVersion) {
        this.yEsamVersion = yEsamVersion;
    }

    public String getMainStationCertificateVersion() {
        return mainStationCertificateVersion;
    }

    public void setMainStationCertificateVersion(String mainStationCertificateVersion) {
        this.mainStationCertificateVersion = mainStationCertificateVersion;
    }

    public String getTerminalCertificateVersion() {
        return terminalCertificateVersion;
    }

    public void setTerminalCertificateVersion(String terminalCertificateVersion) {
        this.terminalCertificateVersion = terminalCertificateVersion;
    }

    public String getMainStationCertificateNum() {
        return mainStationCertificateNum;
    }

    public void setMainStationCertificateNum(String mainStationCertificateNum) {
        this.mainStationCertificateNum = mainStationCertificateNum;
    }

    public String getTerminalCertificateNum() {
        return terminalCertificateNum;
    }

    public void setTerminalCertificateNum(String terminalCertificateNum) {
        this.terminalCertificateNum = terminalCertificateNum;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getEncryptNum() {
        return encryptNum;
    }

    public void setEncryptNum(String encryptNum) {
        this.encryptNum = encryptNum;
    }

    public String getLabelSecretVersion() {
        return labelSecretVersion;
    }

    public void setLabelSecretVersion(String labelSecretVersion) {
        this.labelSecretVersion = labelSecretVersion;
    }

    public String getMainStationCertificate() {
        return mainStationCertificate;
    }

    public void setMainStationCertificate(String mainStationCertificate) {
        this.mainStationCertificate = mainStationCertificate;
    }

    public String getTerminalCertificate() {
        return terminalCertificate;
    }

    public void setTerminalCertificate(String terminalCertificate) {
        this.terminalCertificate = terminalCertificate;
    }


    @Override
    public String toString() {
        return "SafeUnit2Info{" +
                "status='" + status + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", version='" + version + '\'' +
                ", cEsamNum='" + cEsamNum + '\'' +
                ", operator='" + operator + '\'' +
                ", operatorPower='" + operatorPower + '\'' +
                ", cover='" + cover + '\'' +
                ", operatorInfo='" + operatorInfo + '\'' +
                ", yEsamNum='" + yEsamNum + '\'' +
                ", yEsamVersion='" + yEsamVersion + '\'' +
                ", mainStationCertificateVersion='" + mainStationCertificateVersion + '\'' +
                ", terminalCertificateVersion='" + terminalCertificateVersion + '\'' +
                ", mainStationCertificateNum='" + mainStationCertificateNum + '\'' +
                ", terminalCertificateNum='" + terminalCertificateNum + '\'' +
                ", counter='" + counter + '\'' +
                ", encryptNum='" + encryptNum + '\'' +
                ", labelSecretVersion='" + labelSecretVersion + '\'' +
                ", mainStationCertificate='" + mainStationCertificate + '\'' +
                ", terminalCertificate='" + terminalCertificate + '\'' +
                '}';
    }

}
