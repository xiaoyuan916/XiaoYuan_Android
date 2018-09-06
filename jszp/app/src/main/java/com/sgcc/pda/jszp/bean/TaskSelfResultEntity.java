package com.sgcc.pda.jszp.bean;


import java.util.ArrayList;

/**
 *自提出发请求结果实体类
 */
public class TaskSelfResultEntity extends BaseEntity {

        private ArrayList<TakeDistAutoes> takeDistAutoes;

    public ArrayList<TakeDistAutoes> getTakeDistAutoes() {
        return takeDistAutoes;
    }

    public void setTakeDistAutoes(ArrayList<TakeDistAutoes> takeDistAutoes) {
        this.takeDistAutoes = takeDistAutoes;
    }

    public class TakeDistAutoes {

            private String userId;//用户编号
            private String distAutoId;//运输记录标识
            private String taskNo;//配送任务编号
            private String whNo;//配送库房编号
            private String whName;//配送库房名称
            private String dpNo;//直配点编号
            private String dpNmae;//直配点名称
            private int qty;//设备数
            private int boxQty;//设备箱数
            private int finishQty;//最终设备数
            private int finishBoxQty;//最终设备箱数
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

            public String getWhNo() {
                return whNo;
            }

            public void setWhNo(String whNo) {
                this.whNo = whNo;
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

            public String getDpNmae() {
                return dpNmae;
            }

            public void setDpNmae(String dpNmae) {
                this.dpNmae = dpNmae;
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
        }


}
