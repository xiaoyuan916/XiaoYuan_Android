package com.sgcc.pda.jszp.bean;


/**
 * 自提出发请求
 */
public class TaskSelfRequestEntity extends BaseRequestEntity {
    private String taskNo;
    private String dpName;
    private int pageNo;
    private int pageSize;

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getDpName() {
        return dpName;
    }

    public void setDpName(String dpName) {
        this.dpName = dpName;
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
