package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

/**
 * author:赵锦
 * date:2018/8/27 0027 11:01
 *
 * 出库计划明细数据集
 */
public class IoTaskDets implements Serializable {
    private long planDetNo;//出库计划明细编号
    private String planNo;//出库计划编号
    private String splitTaskNo;//拆分任务编号
    private String nvType;//出库方式
    private String equipCode;//设备码
    private String equipDesc;//设备码描述
    private String equipCateg;//设备类别
    private String equipCategLabel;//设备类别名称
    private int qty;//设备数
    private int boxQty;//设备箱数
    private int finishQty;//最终设备数量
    private int finishBoxQty;//最终设备箱数
    private String isReturnSend;//是否返配送
    /**
     * 00    待签收
     01   新建
     02   已下发
     03   已完成
     */
    private String status;//状态
    private String statusLabel;//状态名称
    private String returnType;//返回类型  超期表、分拣表、周转箱、其他

    private int realCount;//实际数量

    /**
     * 返程入库第二界面三级数据
     */
            private String dpName;
            private String dpNo;
            private long taskId;
            private String taskNo;

    public String getDpName() {
        return dpName;
    }

    public void setDpName(String dpName) {
        this.dpName = dpName;
    }

    public String getDpNo() {
        return dpNo;
    }

    public void setDpNo(String dpNo) {
        this.dpNo = dpNo;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getEquipCateg() {
        return equipCateg;
    }

    public void setEquipCateg(String equipCateg) {
        this.equipCateg = equipCateg;
    }

    public String getEquipCategLabel() {
        return equipCategLabel;
    }

    public void setEquipCategLabel(String equipCategLabel) {
        this.equipCategLabel = equipCategLabel;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public long getPlanDetNo() {
        return planDetNo;
    }

    public void setPlanDetNo(long planDetNo) {
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

    public int getBoxQty() {
        return boxQty;
    }

    public void setBoxQty(int boxQty) {
        this.boxQty = boxQty;
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

    public String getIsReturnSend() {
        return isReturnSend;
    }

    public void setIsReturnSend(String isReturnSend) {
        this.isReturnSend = isReturnSend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRealCount() {
        return realCount;
    }

    public void setRealCount(int realCount) {
        this.realCount = realCount;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
