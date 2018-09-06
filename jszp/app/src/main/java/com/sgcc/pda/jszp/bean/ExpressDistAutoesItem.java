package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

/**
 * author:赵锦
 * date:2018/8/20 0020 10:20
 *
 * 快递运输记录
 */
public class ExpressDistAutoesItem implements Serializable{

    private String distAutoId;//运输记录标识
    private String taskNo;//配送任务编号
    private String whNo;//配送库房编号
    private String whName;//配送库房名称
    private String dpNo;//直配点编号
    private String dpName;//直配点名称
    private int qty;//设备数
    private int boxQty;//设备箱数
    private int finishQty;//最终设备数
    private int finishBoxQty;//最终设备箱数
    private String companyId;//快递公司标识
    private String companyName;//快递公司名称
    private String taskStatus;//配送任务状态
    private String taskStatusLabel;//配送任务状态名称
    private String rcver;//收件人
    private String mobile;//联系方式
    private String dpAddress;//接收地址

    public String getDistAutoId() {
        return distAutoId;
    }

    public void setDistAutoId(String distAutoId) {
        this.distAutoId = distAutoId;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getWhNo() {
        return whNo;
    }

    public void setWhNo(String whNo) {
        this.whNo = whNo;
    }

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public String getDpNo() {
        return dpNo;
    }

    public void setDpNo(String dpNo) {
        this.dpNo = dpNo;
    }

    public String getDpName() {
        return dpName;
    }

    public void setDpName(String dpName) {
        this.dpName = dpName;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatusLabel() {
        return taskStatusLabel;
    }

    public void setTaskStatusLabel(String taskStatusLabel) {
        this.taskStatusLabel = taskStatusLabel;
    }

    public String getRcver() {
        return rcver;
    }

    public void setRcver(String rcver) {
        this.rcver = rcver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDpAddress() {
        return dpAddress;
    }

    public void setDpAddress(String dpAddress) {
        this.dpAddress = dpAddress;
    }
}
