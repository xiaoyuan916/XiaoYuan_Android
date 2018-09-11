package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author:赵锦
 * date:2018/9/7 0007 09:11
 *
 * 设备组箱  周转箱信息
 */
public class TurnoverBoxInfo implements Serializable{

    private String sortCode;//周转箱类别
    private String sortCodeLabel;
    private int cap;//周转箱容量
    private int equipQty;//已绑定设备数量
    private String barCode;//周转箱条码
    private String equipCateg;//设备类别
    private List<ScanDeviceDate> assets;//周转箱绑定设备信息

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public int getEquipQty() {
        return equipQty;
    }

    public void setEquipQty(int equipQty) {
        this.equipQty = equipQty;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public List<ScanDeviceDate> getAssets() {
        return assets;
    }

    public void setAssets(List<ScanDeviceDate> assets) {
        this.assets = assets;
    }

    public String getSortCodeLabel() {
        return sortCodeLabel;
    }

    public void setSortCodeLabel(String sortCodeLabel) {
        this.sortCodeLabel = sortCodeLabel;
    }

    public String getEquipCateg() {
        return equipCateg;
    }

    public void setEquipCateg(String equipCateg) {
        this.equipCateg = equipCateg;
    }
}
