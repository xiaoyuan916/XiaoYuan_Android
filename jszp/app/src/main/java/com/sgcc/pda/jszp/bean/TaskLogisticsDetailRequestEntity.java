package com.sgcc.pda.jszp.bean;


//物流详情请求参数
public class TaskLogisticsDetailRequestEntity extends BaseRequestEntity {

    private String taskNo;
    private String distAutoId;


    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getDistAutoId() {
        return distAutoId;
    }

    public void setDistAutoId(String distAutoId) {
        this.distAutoId = distAutoId;
    }
}
