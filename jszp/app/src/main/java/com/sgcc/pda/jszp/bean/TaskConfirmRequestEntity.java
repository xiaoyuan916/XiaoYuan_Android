package com.sgcc.pda.jszp.bean;


/**
 * 确认出发请求
 */
public class TaskConfirmRequestEntity extends BaseRequestEntity {
    private String taskNos;

    public String getTaskNos() {
        return taskNos;
    }

    public void setTaskNos(String taskNos) {
        this.taskNos = taskNos;
    }
}
