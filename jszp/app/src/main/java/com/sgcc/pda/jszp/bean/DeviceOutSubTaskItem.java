package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

/**
 * 平库出库列表中的子列表  三級
 * 配送任务 --》接收单位-->出库任务
 */
public class DeviceOutSubTaskItem implements Serializable {
    private String planDetNo;//出库计划明细编号;
    private String planNo;//出库计划编号
    private String splitTaskNo;//拆分任务编号
    private String mdsTaskNo;//mds出入库任务编号
    private String  nvType;//出库方式
     private String equipCode;//设备码
    private String  equipDesc;//设备码描述
    private int qty;//设备数量
    private int finishQty;//最终设备数量
    private int finishBoxQty;//最终箱子数量
    private String status;//00代签收  01新建  02已下发  03已完成
    private String statusLabel;
    private String equipCateg;//设备类别  参考3.2.6
    private String equipCateLabel;
    private String taskId;

    public String getPlanDetNo() {
        return planDetNo;
    }

    public void setPlanDetNo(String planDetNo) {
        this.planDetNo = planDetNo;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getSplitTaskNo() {
        return splitTaskNo;
    }

    public void setSplitTaskNo(String splitTaskNo) {
        this.splitTaskNo = splitTaskNo;
    }

    public String getMdsTaskNo() {
        return mdsTaskNo;
    }

    public void setMdsTaskNo(String mdsTaskNo) {
        this.mdsTaskNo = mdsTaskNo;
    }

    public String getNvType() {
        return nvType;
    }

    public void setNvType(String nvType) {
        this.nvType = nvType;
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

    public int getFinishQty() {
        return finishQty;
    }

    public void setFinishQty(int finishQty) {
        this.finishQty = finishQty;
    }

    public int getFinishBoxQty() {
        return finishBoxQty;
    }

    public void setFinishBoxQty(int finishBoxQty) {
        this.finishBoxQty = finishBoxQty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public String getEquipCateg() {
        return equipCateg;
    }

    public void setEquipCateg(String equipCateg) {
        this.equipCateg = equipCateg;
    }

    public String getEquipCateLabel() {
        return equipCateLabel;
    }

    public void setEquipCateLabel(String equipCateLabel) {
        this.equipCateLabel = equipCateLabel;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
