package com.sgcc.pda.jszp.bean;

/**
 * 删除扫描请求实体
 */
public class ScanDeleteRequest extends BaseRequestEntity {
   private String barCode;
   private Long    taskId;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
