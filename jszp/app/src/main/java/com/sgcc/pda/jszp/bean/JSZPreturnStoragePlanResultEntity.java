package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class JSZPreturnStoragePlanResultEntity extends BaseEntity {
    private ArrayList<JSZPreturnStoragePlanSplitTask> splitTasks;

    public ArrayList<JSZPreturnStoragePlanSplitTask> getSplitTasks() {
        return splitTasks;
    }

    public void setSplitTasks(ArrayList<JSZPreturnStoragePlanSplitTask> splitTasks) {
        this.splitTasks = splitTasks;
    }

    public class JSZPreturnStoragePlanSplitTask implements Serializable{
        private String splitTaskNo;//拆分任务编号
        private String taskNo;//配送任务编号
        private String dpNo;//直配点编号
        private String dpName;//直配点名称
        private boolean isEnableIssued;//是否可以下发
        private String status;//状态

        public String getSplitTaskNo() {
            return splitTaskNo;
        }

        public void setSplitTaskNo(String splitTaskNo) {
            this.splitTaskNo = splitTaskNo;
        }

        public String getTaskNo() {
            return taskNo;
        }

        public void setTaskNo(String taskNo) {
            this.taskNo = taskNo;
        }

        public String getDpNo() {
            return dpNo;
        }

        public void setDpNo(String dpNo) {
            this.dpNo = dpNo;
        }

        public String getDpName() {
            return dpName;
        }

        public void setDpName(String dpName) {
            this.dpName = dpName;
        }

        public boolean isEnableIssued() {
            return isEnableIssued;
        }

        public void setEnableIssued(boolean enableIssued) {
            isEnableIssued = enableIssued;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
