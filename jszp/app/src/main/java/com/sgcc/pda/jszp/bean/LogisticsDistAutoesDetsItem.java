package com.sgcc.pda.jszp.bean;

/**
 * 物流运输明细数据集
 * <p>
 * 物流派车
 */
public class LogisticsDistAutoesDetsItem extends BaseEntity {

    private String distAutoDetId;//派车记录明细标识
    private String dpNo;//直配点编号
    private String dpName;//直配点名称
    private String whName;//配送库房名称
    private String taskNo;//配送任务编号
    private int qty;//设备数
    private int boxQty;//设备箱数
    private int finishQty;//最终设备数
    private int finishBoxQty;//最终设备箱数
    private int returnQty;//返回设备数
    private int returnBoxQty;//返回设备箱数
    private int seq;//途径直配点先后顺序
    private String statusCode;//3.2.14配送任务状态//状态大于0501（正向已签收）标识已经到达到该直配点

    private boolean isConfirmed;//装车确认的时候使用

    /**
     * 装车确认模块的两个值
     */
    private String realcount;
    private boolean selected=false;

    public String getRealcount() {
        return realcount;
    }

    public void setRealcount(String realcount) {
        this.realcount = realcount;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getDistAutoDetId() {
        return distAutoDetId;
    }

    public void setDistAutoDetId(String distAutoDetId) {
        this.distAutoDetId = distAutoDetId;
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

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
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

    public int getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(int returnQty) {
        this.returnQty = returnQty;
    }

    public int getReturnBoxQty() {
        return returnBoxQty;
    }

    public void setReturnBoxQty(int returnBoxQty) {
        this.returnBoxQty = returnBoxQty;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    //是否已到达该直配点
    public boolean isArrived() {
        if(statusCode ==null){
            return false;
        }
        switch (statusCode) {
            case "00":
            case "01":
            case "02":
            case "03":
            case "04":
            case "0401":
            case "0402":
            case "0403":
            case "05":
                return false;
            default:
                return true;
        }
    }
}
