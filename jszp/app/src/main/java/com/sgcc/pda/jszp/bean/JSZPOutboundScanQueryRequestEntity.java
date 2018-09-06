package com.sgcc.pda.jszp.bean;

public class JSZPOutboundScanQueryRequestEntity extends BaseRequestEntity {
    private String relaNo;//关联单号

    private int pageNo;//页码

    private int pageSize;//页大小

    public String getRelaNo() {
        return relaNo;
    }

    public void setRelaNo(String relaNo) {
        this.relaNo = relaNo;
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
