package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
import java.util.ArrayList;

//返程入库列表
public class ReturnWarehouseResultEntity extends BaseEntity {
    private ArrayList<SplitTasks> splitTasks;

    public ArrayList<SplitTasks> getSplitTasks() {
        return splitTasks;
    }

    public void setSplitTasks(ArrayList<SplitTasks> splitTasks) {
        this.splitTasks = splitTasks;
    }

    public class SplitTasks implements Serializable{

        private String splitTaskNo;//拆分任务编号
        private String taskNo;//配送任务编号
        private String dpNo;//直配点编号
        private String dpName;//直配点名称
        private boolean isEnableIssued;//是否可以下发
        private String statusCode;//状态
        private int pageNo;
        private int pageSize;

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

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
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
}
