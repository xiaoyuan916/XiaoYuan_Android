package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author:xuxiaoyuan
 * date:2018/9/4
 */
public class JszpQueryDistAppsItemEntity implements Serializable {
    private String appNo;//订单编号
    private String appSort;//订单类别
    private String sgAppType;//订单类型
    private String appStatus;//订单状态
    private String appStatusLabel;//订单状态名称
    private String distTypeCode;//配送类型
    private String handleType;//处理方式
    private String distDate;//要求配送时间
    private String remainingTime;//配送时限
    private String sgAppNo;//营销工单编号
    private String dpNo;//直配库房编号
    private String orgNo;//申请单位
    private String applicantNo;//申请人
    private String appDate;//申请时间
    private String whNo;//配送库房编号
    private String level;//订单级别
    private String msPlanNo;//里程碑计划编号
    private String isReturnSend;//是否返配送
    private String handleOrgNo;//处理单位
    private String distMode;//配送方式
    private String appReturnStatus;//订单返回状态
    private String appPrePlanDate;//预演指定计划日期
    private String appPlanDate;//指定计划日期


    /**
     * 添加单个订单的跟踪信息
     */
   private List<JszpOrderTrackEntity> tracks;//订单跟踪系信息
    private List<JszpOrderDistAppDetEntity> distDistAppDets;//正向配送订单明细数据集
    private List<JszpOrderDistAppDetEntity> retuenDistAppDets;//反向配送订单明细数据集

    public List<JszpOrderTrackEntity> getTracks() {
        return tracks;
    }

    public void setTracks(List<JszpOrderTrackEntity> tracks) {
        this.tracks = tracks;
    }

    public List<JszpOrderDistAppDetEntity> getDistDistAppDets() {
        return distDistAppDets;
    }

    public void setDistDistAppDets(List<JszpOrderDistAppDetEntity> distDistAppDets) {
        this.distDistAppDets = distDistAppDets;
    }

    public List<JszpOrderDistAppDetEntity> getRetuenDistAppDets() {
        return retuenDistAppDets;
    }

    public void setRetuenDistAppDets(List<JszpOrderDistAppDetEntity> retuenDistAppDets) {
        this.retuenDistAppDets = retuenDistAppDets;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getAppSort() {
        return appSort;
    }

    public void setAppSort(String appSort) {
        this.appSort = appSort;
    }

    public String getSgAppType() {
        return sgAppType;
    }

    public void setSgAppType(String sgAppType) {
        this.sgAppType = sgAppType;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getAppStatusLabel() {
        return appStatusLabel;
    }

    public void setAppStatusLabel(String appStatusLabel) {
        this.appStatusLabel = appStatusLabel;
    }

    public String getDistTypeCode() {
        return distTypeCode;
    }

    public void setDistTypeCode(String distTypeCode) {
        this.distTypeCode = distTypeCode;
    }

    public String getHandleType() {
        return handleType;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    public String getDistDate() {
        return distDate;
    }

    public void setDistDate(String distDate) {
        this.distDate = distDate;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getSgAppNo() {
        return sgAppNo;
    }

    public void setSgAppNo(String sgAppNo) {
        this.sgAppNo = sgAppNo;
    }

    public String getDpNo() {
        return dpNo;
    }

    public void setDpNo(String dpNo) {
        this.dpNo = dpNo;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getApplicantNo() {
        return applicantNo;
    }

    public void setApplicantNo(String applicantNo) {
        this.applicantNo = applicantNo;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getWhNo() {
        return whNo;
    }

    public void setWhNo(String whNo) {
        this.whNo = whNo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMsPlanNo() {
        return msPlanNo;
    }

    public void setMsPlanNo(String msPlanNo) {
        this.msPlanNo = msPlanNo;
    }

    public String getIsReturnSend() {
        return isReturnSend;
    }

    public void setIsReturnSend(String isReturnSend) {
        this.isReturnSend = isReturnSend;
    }

    public String getHandleOrgNo() {
        return handleOrgNo;
    }

    public void setHandleOrgNo(String handleOrgNo) {
        this.handleOrgNo = handleOrgNo;
    }

    public String getDistMode() {
        return distMode;
    }

    public void setDistMode(String distMode) {
        this.distMode = distMode;
    }

    public String getAppReturnStatus() {
        return appReturnStatus;
    }

    public void setAppReturnStatus(String appReturnStatus) {
        this.appReturnStatus = appReturnStatus;
    }

    public String getAppPrePlanDate() {
        return appPrePlanDate;
    }

    public void setAppPrePlanDate(String appPrePlanDate) {
        this.appPrePlanDate = appPrePlanDate;
    }

    public String getAppPlanDate() {
        return appPlanDate;
    }

    public void setAppPlanDate(String appPlanDate) {
        this.appPlanDate = appPlanDate;
    }
}
