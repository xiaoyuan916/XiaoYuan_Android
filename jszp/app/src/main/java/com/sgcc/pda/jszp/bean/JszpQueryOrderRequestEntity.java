package com.sgcc.pda.jszp.bean;

/**
 * author:xuxiaoyuan
 * date:2018/9/4
 */
public class JszpQueryOrderRequestEntity extends BaseRequestEntity {
    private String appNo;//订单编号
    private String sgAppType;//订单类型
    private String orgNo;//申请单位
    private String appStatus;//订单状态
    private String beginAppDate;//开始申请日期
    private String endAppDate;//结束申请日期
    private int pageNo;//页码
    private int pageSize;//页大小

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getSgAppType() {
        return sgAppType;
    }

    public void setSgAppType(String sgAppType) {
        this.sgAppType = sgAppType;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getBeginAppDate() {
        return beginAppDate;
    }

    public void setBeginAppDate(String beginAppDate) {
        this.beginAppDate = beginAppDate;
    }

    public String getEndAppDate() {
        return endAppDate;
    }

    public void setEndAppDate(String endAppDate) {
        this.endAppDate = endAppDate;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
