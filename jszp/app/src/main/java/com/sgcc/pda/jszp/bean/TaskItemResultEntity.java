package com.sgcc.pda.jszp.bean;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * 获取物流出发数据实体类
 */
public class TaskItemResultEntity extends BaseEntity {

    private ArrayList<LogisticsDistAutoes> logisticsDistAutoes;

    public ArrayList<LogisticsDistAutoes> getLogisticsDistAutoes() {
        return logisticsDistAutoes;
    }

    public void setLogisticsDistAutoes(ArrayList<LogisticsDistAutoes> logisticsDistAutoes) {
        this.logisticsDistAutoes = logisticsDistAutoes;
    }

    public class LogisticsDistAutoes implements Serializable {
        private String userId;
        private int pageNo;
        private int pageSize;
        private String distAutoId;
        private String taskNo;
        private String loadRate;
        private String seq;
        private String mileage;
        private String whNo;
        private int qty;
        private int boxQty;
        private String finishQty;
        private String finishBoxQty;
        private String whName;
        private String dpNo;
        private String dpName;
        private String autoType;
        private String autoBrandNo;
        private String staffName;
        private String phoneNo;
        private String planPlaceTime;
        private String status;
        private String statusLabel;
        private String logisticsDistAutoDets;
        private String billNo;
        private String expressPkgs;
        private String loaded;
        private String taskStatus;
        private String taskStatusLabel;
        private String statuses;
        private String companyId;
        private String companyName;
        private String taker;
        private String dpAddress;
        private String address;
        private String mobile;
        private String uid;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getLoadRate() {
            return loadRate;
        }

        public void setLoadRate(String loadRate) {
            this.loadRate = loadRate;
        }

        public String getSeq() {
            return seq;
        }

        public void setSeq(String seq) {
            this.seq = seq;
        }

        public String getMileage() {
            return mileage;
        }

        public void setMileage(String mileage) {
            this.mileage = mileage;
        }

        public String getWhNo() {
            return whNo;
        }

        public void setWhNo(String whNo) {
            this.whNo = whNo;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public int getBoxQty() {
            return boxQty;
        }

        public void setBoxQty(int boxQty) {
            this.boxQty = boxQty;
        }

        public String getFinishQty() {
            return finishQty;
        }

        public void setFinishQty(String finishQty) {
            this.finishQty = finishQty;
        }

        public String getFinishBoxQty() {
            return finishBoxQty;
        }

        public void setFinishBoxQty(String finishBoxQty) {
            this.finishBoxQty = finishBoxQty;
        }

        public String getWhName() {
            return whName;
        }

        public void setWhName(String whName) {
            this.whName = whName;
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

        public String getAutoType() {
            return autoType;
        }

        public void setAutoType(String autoType) {
            this.autoType = autoType;
        }

        public String getAutoBrandNo() {
            return autoBrandNo;
        }

        public void setAutoBrandNo(String autoBrandNo) {
            this.autoBrandNo = autoBrandNo;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getPlanPlaceTime() {
            return planPlaceTime;
        }

        public void setPlanPlaceTime(String planPlaceTime) {
            this.planPlaceTime = planPlaceTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusLabel() {
            return statusLabel;
        }

        public void setStatusLabel(String statusLabel) {
            this.statusLabel = statusLabel;
        }

        public String getLogisticsDistAutoDets() {
            return logisticsDistAutoDets;
        }

        public void setLogisticsDistAutoDets(String logisticsDistAutoDets) {
            this.logisticsDistAutoDets = logisticsDistAutoDets;
        }

        public String getBillNo() {
            return billNo;
        }

        public void setBillNo(String billNo) {
            this.billNo = billNo;
        }

        public String getExpressPkgs() {
            return expressPkgs;
        }

        public void setExpressPkgs(String expressPkgs) {
            this.expressPkgs = expressPkgs;
        }

        public String getLoaded() {
            return loaded;
        }

        public void setLoaded(String loaded) {
            this.loaded = loaded;
        }

        public String getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(String taskStatus) {
            this.taskStatus = taskStatus;
        }

        public String getTaskStatusLabel() {
            return taskStatusLabel;
        }

        public void setTaskStatusLabel(String taskStatusLabel) {
            this.taskStatusLabel = taskStatusLabel;
        }

        public String getStatuses() {
            return statuses;
        }

        public void setStatuses(String statuses) {
            this.statuses = statuses;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getTaker() {
            return taker;
        }

        public void setTaker(String taker) {
            this.taker = taker;
        }

        public String getDpAddress() {
            return dpAddress;
        }

        public void setDpAddress(String dpAddress) {
            this.dpAddress = dpAddress;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }


}
