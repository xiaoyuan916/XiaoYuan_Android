package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

/**
 * author:xuxiaoyuan
 * date:2018/9/5
 */
public class JszpOrderTrackEntity implements Serializable {
    private int trackId;//跟踪记录标识
    private String appDetNo;//订单明细编号
    private String appNoAPP_NO;//订单编号
    private String appDetStatus;//订单明细状态
    private String handleActSort;//处理环节分类
    private String handleAct;//处理环节
    private String handleDesc;//处理说明
    private String handleTime;//处理时间

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getAppDetNo() {
        return appDetNo;
    }

    public void setAppDetNo(String appDetNo) {
        this.appDetNo = appDetNo;
    }

    public String getAppNoAPP_NO() {
        return appNoAPP_NO;
    }

    public void setAppNoAPP_NO(String appNoAPP_NO) {
        this.appNoAPP_NO = appNoAPP_NO;
    }

    public String getAppDetStatus() {
        return appDetStatus;
    }

    public void setAppDetStatus(String appDetStatus) {
        this.appDetStatus = appDetStatus;
    }

    public String getHandleActSort() {
        return handleActSort;
    }

    public void setHandleActSort(String handleActSort) {
        this.handleActSort = handleActSort;
    }

    public String getHandleAct() {
        return handleAct;
    }

    public void setHandleAct(String handleAct) {
        this.handleAct = handleAct;
    }

    public String getHandleDesc() {
        return handleDesc;
    }

    public void setHandleDesc(String handleDesc) {
        this.handleDesc = handleDesc;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }
}
