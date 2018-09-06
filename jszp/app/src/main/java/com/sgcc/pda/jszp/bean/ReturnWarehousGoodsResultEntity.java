package com.sgcc.pda.jszp.bean;


import java.util.ArrayList;

//返程入库商品
public class ReturnWarehousGoodsResultEntity extends BaseEntity {
private SplitTasks splitTask;

    public SplitTasks getSplitTask() {
        return splitTask;
    }

    public void setSplitTask(SplitTasks splitTask) {
        this.splitTask = splitTask;
    }

    public class SplitTasks {
        private String splitTaskNo;//拆分任务编号
        private String taskNo;//配送任务编号
        private String dpNo;//直配点编号
        private String dpName;//直配点名称
        private boolean isEnableIssued;//是否可以下发
        private String statusCode;//状态
        private ArrayList<IoTaskDets> ioTaskDets;

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

        public ArrayList<IoTaskDets> getIoTaskDets() {
            return ioTaskDets;
        }

        public void setIoTaskDets(ArrayList<IoTaskDets> ioTaskDets) {
            this.ioTaskDets = ioTaskDets;
        }

        public SplitTasks(ArrayList<IoTaskDets> ioTaskDets) {
            this.ioTaskDets = ioTaskDets;
        }

//        public class IoTaskDets {
//                    private int boxQty;
//                    private String dpName;
//                    private String dpNo;
//                    private String equipCateg;
//                    private String equipCategLabel;
//                    private String equipCode;
//                    private String equipDesc;
//                    private int finishBoxQty;
//                    private int finishQty;
//                    private String nvType;
//                    private int pageNo;
//                    private int pageSize;
//                    private String planDetNo;
//                    private String planNo;
//                    private int qty;
//                    private String returnType;
//                    private String splitTaskNo;
//                    private String status;
//                    private String statusLabel;
//                    private String taskId;
//                    private String taskNo;
//
//            public int getBoxQty() {
//                return boxQty;
//            }
//
//            public void setBoxQty(int boxQty) {
//                this.boxQty = boxQty;
//            }
//
//            public String getDpName() {
//                return dpName;
//            }
//
//            public void setDpName(String dpName) {
//                this.dpName = dpName;
//            }
//
//            public String getDpNo() {
//                return dpNo;
//            }
//
//            public void setDpNo(String dpNo) {
//                this.dpNo = dpNo;
//            }
//
//            public String getEquipCateg() {
//                return equipCateg;
//            }
//
//            public void setEquipCateg(String equipCateg) {
//                this.equipCateg = equipCateg;
//            }
//
//            public String getEquipCategLabel() {
//                return equipCategLabel;
//            }
//
//            public void setEquipCategLabel(String equipCategLabel) {
//                this.equipCategLabel = equipCategLabel;
//            }
//
//            public String getEquipCode() {
//                return equipCode;
//            }
//
//            public void setEquipCode(String equipCode) {
//                this.equipCode = equipCode;
//            }
//
//            public String getEquipDesc() {
//                return equipDesc;
//            }
//
//            public void setEquipDesc(String equipDesc) {
//                this.equipDesc = equipDesc;
//            }
//
//            public int getFinishBoxQty() {
//                return finishBoxQty;
//            }
//
//            public void setFinishBoxQty(int finishBoxQty) {
//                this.finishBoxQty = finishBoxQty;
//            }
//
//            public int getFinishQty() {
//                return finishQty;
//            }
//
//            public void setFinishQty(int finishQty) {
//                this.finishQty = finishQty;
//            }
//
//            public String getNvType() {
//                return nvType;
//            }
//
//            public void setNvType(String nvType) {
//                this.nvType = nvType;
//            }
//
//            public int getPageNo() {
//                return pageNo;
//            }
//
//            public void setPageNo(int pageNo) {
//                this.pageNo = pageNo;
//            }
//
//            public int getPageSize() {
//                return pageSize;
//            }
//
//            public void setPageSize(int pageSize) {
//                this.pageSize = pageSize;
//            }
//
//            public String getPlanDetNo() {
//                return planDetNo;
//            }
//
//            public void setPlanDetNo(String planDetNo) {
//                this.planDetNo = planDetNo;
//            }
//
//            public String getPlanNo() {
//                return planNo;
//            }
//
//            public void setPlanNo(String planNo) {
//                this.planNo = planNo;
//            }
//
//            public int getQty() {
//                return qty;
//            }
//
//            public void setQty(int qty) {
//                this.qty = qty;
//            }
//
//            public String getReturnType() {
//                return returnType;
//            }
//
//            public void setReturnType(String returnType) {
//                this.returnType = returnType;
//            }
//
//            public String getSplitTaskNo() {
//                return splitTaskNo;
//            }
//
//            public void setSplitTaskNo(String splitTaskNo) {
//                this.splitTaskNo = splitTaskNo;
//            }
//
//            public String getStatus() {
//                return status;
//            }
//
//            public void setStatus(String status) {
//                this.status = status;
//            }
//
//            public String getStatusLabel() {
//                return statusLabel;
//            }
//
//            public void setStatusLabel(String statusLabel) {
//                this.statusLabel = statusLabel;
//            }
//
//            public String getTaskId() {
//                return taskId;
//            }
//
//            public void setTaskId(String taskId) {
//                this.taskId = taskId;
//            }
//
//            public String getTaskNo() {
//                return taskNo;
//            }
//
//            public void setTaskNo(String taskNo) {
//                this.taskNo = taskNo;
//            }
//        }
    }

}
