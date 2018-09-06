package com.sgcc.pda.jszp.bean;

/**
 * 物流派车列表请求实体
 */
public class LogisticsSendRequestEntity extends BaseRequestEntity{
    private String effectDate;//计划日期
    private String autoType;//车辆类型
    private String status;//派车状态
    private int pageNo;//页码
    private int pageSize;//页大小

    public String getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(String effectDate) {
        effectDate = effectDate.replace("年","");
        effectDate = effectDate.replace("月","");
        effectDate = effectDate.replace("日","");
        this.effectDate = effectDate;
    }

    public String getAutoType() {
        return autoType;
    }

    public void setAutoType(String autoType) {
        this.autoType = autoType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
