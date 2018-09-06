package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

/**
 * author:xuxiaoyuan
 * date:2018/9/5
 */
public class JszpOrderDistAppDetEntity implements Serializable {
    private String appDetNo;//订单明细编号
    private String appNo;//订单明细编号
    private String equipCateg;//设备类别
    private String equipCode;//设备码
    private String equipDesc;//设备码描述
    private int qty;//数量
    private int boxQty;//周转箱数量
    private String appDetStatus;//订单明细状态
    private String appDetStatusLabel;//订单明细状态名称
    private String isReturnSend;//是否返配送
    private String returnType;//返回类型

    public String getAppDetNo() {
        return appDetNo;
    }

    public void setAppDetNo(String appDetNo) {
        this.appDetNo = appDetNo;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getEquipCateg() {
        return equipCateg;
    }

    public void setEquipCateg(String equipCateg) {
        this.equipCateg = equipCateg;
    }

    public String getEquipCode() {
        return equipCode;
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }

    public String getEquipDesc() {
        return equipDesc;
    }

    public void setEquipDesc(String equipDesc) {
        this.equipDesc = equipDesc;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getBoxQty() {
        return boxQty;
    }

    public void setBoxQty(int boxQty) {
        this.boxQty = boxQty;
    }

    public String getAppDetStatus() {
        return appDetStatus;
    }

    public void setAppDetStatus(String appDetStatus) {
        this.appDetStatus = appDetStatus;
    }

    public String getAppDetStatusLabel() {
        return appDetStatusLabel;
    }

    public void setAppDetStatusLabel(String appDetStatusLabel) {
        this.appDetStatusLabel = appDetStatusLabel;
    }

    public String getIsReturnSend() {
        return isReturnSend;
    }

    public void setIsReturnSend(String isReturnSend) {
        this.isReturnSend = isReturnSend;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
