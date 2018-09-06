package com.sgcc.pda.jszp.bean;


import java.util.ArrayList;

/**
 * 快递请求结果实体
 */
public class TaskExpressResultEntity extends BaseEntity {

    private ArrayList<ExpressDistAutoes> expressDistAutoes;

    public ArrayList<ExpressDistAutoes> getExpressDistAutos() {
        return expressDistAutoes;
    }

    public void setExpressDistAutoes(ArrayList<ExpressDistAutoes> expressDistAutoes) {
        this.expressDistAutoes = expressDistAutoes;
    }

    public class ExpressDistAutoes {

        private int boxQty;
        private String companyName;
        private String distAutoId;
        private String dpName;
        private String dpNo;
        private int pageNo;
        private int pageSize;
        private int qty;
        private String taskNo;
        private String taskStatus;
        private String whName;
        private String whNo;


        private boolean isSelected;//是否选中

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getBoxQty() {
            return boxQty;
        }

        public void setBoxQty(int boxQty) {
            this.boxQty = boxQty;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getDistAutoId() {
            return distAutoId;
        }

        public void setDistAutoId(String distAutoId) {
            this.distAutoId = distAutoId;
        }

        public String getDpName() {
            return dpName;
        }

        public void setDpName(String dpName) {
            this.dpName = dpName;
        }

        public String getDpNo() {
            return dpNo;
        }

        public void setDpNo(String dpNo) {
            this.dpNo = dpNo;
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

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getTaskNo() {
            return taskNo;
        }

        public void setTaskNo(String taskNo) {
            this.taskNo = taskNo;
        }

        public String getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(String taskStatus) {
            this.taskStatus = taskStatus;
        }

        public String getWhName() {
            return whName;
        }

        public void setWhName(String whName) {
            this.whName = whName;
        }

        public String getWhNo() {
            return whNo;
        }

        public void setWhNo(String whNo) {
            this.whNo = whNo;
        }
    }


}
