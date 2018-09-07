package com.sgcc.pda.jszp.bean;

/**
 * author:xuxiaoyuan
 * date:2018/9/7
 */
public class JszpBoxRequstEntity extends JszpBoxScanRequestEntity {
    private int pageNo;
    private int pageSize;

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
