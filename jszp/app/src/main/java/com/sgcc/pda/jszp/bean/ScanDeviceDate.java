package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

/**
 * author:赵锦
 * date:2018/8/28 0028 14:38
 */
public class ScanDeviceDate implements Serializable {
    private String barCode;//条码
    private String equipCateg;//设备类别
    private String equipCategLable;//设备类别
    private String equipCode;//设备码
    private String equipDesc;//设备码描述
    private String statusCode;//设备状态  查看3.2.16
    private String excepDesc;//异常描述
    private String statusCodeLabel;//设备状态

    public ScanDeviceDate( String barCode){
        this.barCode = barCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
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

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getExcepDesc() {
        return excepDesc;
    }

    public void setExcepDesc(String excepDesc) {
        this.excepDesc = excepDesc;
    }

    public String getStatusCodeLabel() {
        return statusCodeLabel;
    }

    public void setStatusCodeLabel(String statusCodeLabel) {
        this.statusCodeLabel = statusCodeLabel;
    }

    public String getEquipCategLable() {
        return equipCategLable;
    }

    public void setEquipCategLable(String equipCategLable) {
        this.equipCategLable = equipCategLable;
    }
}
