package com.sgcc.pda.jszp.bean;


//快递请求参数
public class TaskExpressRequestEntity extends BaseRequestEntity {

    private String taskNos;
    private String dpName;
    private int pageNo;
    private int pageSize;

    public String getTaskNos() {
        return taskNos;
    }

    public void setTaskNos(String taskNos) {
        this.taskNos = taskNos;
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
