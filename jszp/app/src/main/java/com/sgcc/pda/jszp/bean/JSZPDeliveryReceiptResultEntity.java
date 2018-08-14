package com.sgcc.pda.jszp.bean;

import java.util.ArrayList;

public class JSZPDeliveryReceiptResultEntity extends BaseEntity {
    private JSZPDeliveryReceiptResultSplitTaskEntity splitTask; //拆分任务数据集

    public JSZPDeliveryReceiptResultSplitTaskEntity getSplitTask() {
        return splitTask;
    }

    public void setSplitTask(JSZPDeliveryReceiptResultSplitTaskEntity splitTask) {
        this.splitTask = splitTask;
    }

    public class JSZPDeliveryReceiptResultSplitTaskEntity {
        private String splitTaskNo;//拆分任务编号
        private String taskNo;//配送任务编号
        private String dpNo;//直配点编号
        private String dpName;//直配点名称
        private boolean isEnableIssued;//是否可以下发
        private String status;//状态
        private ArrayList<JSZPDeliveryReceiptResultIoPlanDetsEntity> ioPlanDets;//出库计划明细数据集

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

        public ArrayList<JSZPDeliveryReceiptResultIoPlanDetsEntity> getIoPlanDets() {
            return ioPlanDets;
        }

        public void setIoPlanDets(ArrayList<JSZPDeliveryReceiptResultIoPlanDetsEntity> ioPlanDets) {
            this.ioPlanDets = ioPlanDets;
        }

        public class JSZPDeliveryReceiptResultIoPlanDetsEntity {
            private int planDetNo;//出库计划明细编号
            private String planNo;//出库计划编号
            private String splitTaskNo;//拆分任务编号
            private String nvType;//出库方式
            private String equipCode;//设备码
            private String equipDesc;//设备码描述
            private int qty;//设备数
            private int boxQty;//设备箱数
            private int finishQty;//最终设备数量
            private int finishBoxQty;//最终设备箱数
            private String isReturnSend;//是否返配送
            private String status;//状态

            public int getPlanDetNo() {
                return planDetNo;
            }

            public void setPlanDetNo(int planDetNo) {
                this.planDetNo = planDetNo;
            }

            public String getPlanNo() {
                return planNo;
            }

            public void setPlanNo(String planNo) {
                this.planNo = planNo;
            }

            public String getSplitTaskNo() {
                return splitTaskNo;
            }

            public void setSplitTaskNo(String splitTaskNo) {
                this.splitTaskNo = splitTaskNo;
            }

            public String getNvType() {
                return nvType;
            }

            public void setNvType(String nvType) {
                this.nvType = nvType;
            }

            public String getEquipCode() {
                return equipCode;
            }

            public void setEquipCode(String equipCode) {
                this.equipCode = equipCode;
            }

            public String getEquipDesc() {
                return equipDesc;
            }

            public void setEquipDesc(String equipDesc) {
                this.equipDesc = equipDesc;
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

            public int getFinishQty() {
                return finishQty;
            }

            public void setFinishQty(int finishQty) {
                this.finishQty = finishQty;
            }

            public int getFinishBoxQty() {
                return finishBoxQty;
            }

            public void setFinishBoxQty(int finishBoxQty) {
                this.finishBoxQty = finishBoxQty;
            }

            public String getIsReturnSend() {
                return isReturnSend;
            }

            public void setIsReturnSend(String isReturnSend) {
                this.isReturnSend = isReturnSend;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
