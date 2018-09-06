package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
import java.util.List;
//物流派车  明细
public class LogisticsDistAutoesItem implements Serializable {

    private String distAutoId;//运输记录标识
    private String taskNo;//配送任务编号
    private String whNo;//配送库房编号
    private String whName;//配送库房名称
    private String dpNo;//直配点编号
    private String dpName;//直配点名称
    private String autoType;//车辆类型  01大型车  02中型车 03小型车
    private String loaded;//吨数
    private String autoBrandNo;//车牌号
    private String staffName;//司机姓名
    private String phoneNo;//联系电话

    private String planPlaceTime;//预计到位时间
    private String status;//派车状态  01未排产  02已派车
    private String statusLabel;//派车状态
    /**
     *00    临时
     01    新建
     02    生效
     03   配送出库
     04   已出库
     0401   用户确认
     0402   已绑定
     0403   中心确认
     05   配送中
     0501   正向已签收
     0502   反向配送中
     0503   反向已签收
     06   结束
     08   归档
     */
    private String taskStatus;
    private String taskStatusLabel;

    private int qty;//设备数
    private int boxQty;//设备箱数
    private int finishQty;//最终设备数
    private int finishBoxQty;//最终设备箱数


    private List<LogisticsDistAutoesDetsItem> logisticsDistAutoDets;//物流运输明细数据集

    public String getDistAutoId() {
        return distAutoId;
    }

    public void setDistAutoId(String distAutoId) {
        this.distAutoId = distAutoId;
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

    public List<LogisticsDistAutoesDetsItem> getLogisticsDistAutoDets() {
        return logisticsDistAutoDets;
    }

    public void setLogisticsDistAutoDets(List<LogisticsDistAutoesDetsItem> logisticsDistAutoDets) {
        this.logisticsDistAutoDets = logisticsDistAutoDets;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
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

    public String getLoaded() {
        return loaded;
    }

    public void setLoaded(String loaded) {
        this.loaded = loaded;
    }
}
