package com.sgcc.pda.jszp.bean;

public class JSZPConfirmationDetailsRequestEntity extends BaseRequestEntity{
    private String distAutoId;
    private String taskNo;

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
}
