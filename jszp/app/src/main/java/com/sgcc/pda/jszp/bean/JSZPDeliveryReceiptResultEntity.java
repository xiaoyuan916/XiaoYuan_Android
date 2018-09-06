package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
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
        private ArrayList<IoTaskDets>  ioTaskDets;//出库计划明细数据集

        public ArrayList<IoTaskDets> getIoTaskDets() {
            return ioTaskDets;
        }

        public void setIoTaskDets(ArrayList<IoTaskDets> ioTaskDets) {
            this.ioTaskDets = ioTaskDets;
        }

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


//        public class JSZPDeliveryReceiptResultIoPlanDetsEntity implements Serializable{
//            private long planDetNo;//出库计划明细编号
//            private String planNo;//出库计划编号
//            private String splitTaskNo;//拆分任务编号
//            private String nvType;//出库方式
//            private String equipCode;//设备码
//            private String equipDesc;//设备码描述
//            private String equipCateg;//设备类别
//            private String equipCategLabel;//设备类别名称
//            private int qty;//设备数
//            private int boxQty;//设备箱数
//            private int finishQty;//最终设备数量
//            private int finishBoxQty;//最终设备箱数
//            private String isReturnSend;//是否返配送
//            private long taskId;//mds出入库任务编号
//            private String  taskNo;//任务编号
//            /**
//             * 00    待签收
//             01   新建
//             02   已下发
//             03   已完成
//             */
//            private String status;//状态
//            private String statusLabel;//状态名称
//
//            private int realCount=-1;//实际数量
//            /**
//             * 是否选中
//             */
//            private boolean selected;
//            /**
//             * 核对的实际数量
//             */
//            private int realcount=-1;
//
//            private String return_type;//返回类型  超期表、分拣表、周转箱、其他
//
//            public String getReturn_type() {
//                return return_type;
//            }
//
//            public void setReturn_type(String return_type) {
//                this.return_type = return_type;
//            }
//
//            public long getTaskId() {
//                return taskId;
//            }
//
//            public void setTaskId(long taskId) {
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
//
//            public boolean isSelected() {
//                return selected;
//            }
//
//            public void setSelected(boolean selected) {
//                this.selected = selected;
//            }
//
//            public int getRealcount() {
//                return realcount;
//            }
//
//            public void setRealcount(int realcount) {
//                this.realcount = realcount;
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
//            public String getStatusLabel() {
//                return statusLabel;
//            }
//
//            public void setStatusLabel(String statusLabel) {
//                this.statusLabel = statusLabel;
//            }
//
//            public long getPlanDetNo() {
//                return planDetNo;
//            }
//
//            public void setPlanDetNo(int planDetNo) {
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
//            public String getSplitTaskNo() {
//                return splitTaskNo;
//            }
//
//            public void setSplitTaskNo(String splitTaskNo) {
//                this.splitTaskNo = splitTaskNo;
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
//            public int getQty() {
//                return qty;
//            }
//
//            public void setQty(int qty) {
//                this.qty = qty;
//            }
//
//            public int getBoxQty() {
//                return boxQty;
//            }
//
//            public void setBoxQty(int boxQty) {
//                this.boxQty = boxQty;
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
//            public int getFinishBoxQty() {
//                return finishBoxQty;
//            }
//
//            public void setFinishBoxQty(int finishBoxQty) {
//                this.finishBoxQty = finishBoxQty;
//            }
//
//            public String getIsReturnSend() {
//                return isReturnSend;
//            }
//
//            public void setIsReturnSend(String isReturnSend) {
//                this.isReturnSend = isReturnSend;
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
//            public void setPlanDetNo(long planDetNo) {
//                this.planDetNo = planDetNo;
//            }
//
//            public int getRealCount() {
//                return realCount;
//            }
//
//            public void setRealCount(int realCount) {
//                this.realCount = realCount;
//            }
//        }
    }
}
