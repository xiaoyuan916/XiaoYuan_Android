package com.sgcc.pda.jszp.bean;

import java.util.ArrayList;

/**
 * author:赵锦
 * date:2018/8/31 0031 15:44
 *
 * 设备详情
 */
public class DeviceDetail {
    private String barCode;//设备条码
    private String equipCateg;//设备类别
    private String equipCategLable;
    private String equipCode;//设备码
    private String equipDesc;//设备码描述
    private String boxBarCode;//周转箱条码
    private String orgNo;//所属单位
    private String orgName;//所属单位名称
    private String arriveBatchNo;//到货批次号
    private String bidBachNo;//招标批次号
    private String manufacturer;//生产厂家
    private String manufacturerLabel;//生产厂家名称
    private String statusCode;//设备状态
    private String statusLabel;//设备状态名称
    private ArrayList<DeviceQueryTracks> tracks;//跟踪信息

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

    public String getBoxBarCode() {
        return boxBarCode;
    }

    public void setBoxBarCode(String boxBarCode) {
        this.boxBarCode = boxBarCode;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getArriveBatchNo() {
        return arriveBatchNo;
    }

    public void setArriveBatchNo(String arriveBatchNo) {
        this.arriveBatchNo = arriveBatchNo;
    }

    public String getBidBachNo() {
        return bidBachNo;
    }

    public void setBidBachNo(String bidBachNo) {
        this.bidBachNo = bidBachNo;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturerLabel() {
        return manufacturerLabel;
    }

    public void setManufacturerLabel(String manufacturerLabel) {
        this.manufacturerLabel = manufacturerLabel;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public ArrayList<DeviceQueryTracks> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<DeviceQueryTracks> tracks) {
        this.tracks = tracks;
    }

    public String getEquipCategLable() {
        return equipCategLable;
    }

    public void setEquipCategLable(String equipCategLable) {
        this.equipCategLable = equipCategLable;
    }
}
