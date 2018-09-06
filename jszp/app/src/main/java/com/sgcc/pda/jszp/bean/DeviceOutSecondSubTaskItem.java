package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 平库出库列表中的子列表
 * 配送任务 --》接收单位
 */
public class DeviceOutSecondSubTaskItem implements Serializable {
    private String splitTaskNo;//拆分任务编号
    private String taskNo;//配送任务编号
    private String dpNo;//直配点编号
    private String dpName;//直配点名称
    private List<DeviceOutSubTaskItem> ioTaskDets;

    public DeviceOutSecondSubTaskItem(List<DeviceOutSubTaskItem> ioTaskDets) {
        this.dpName = "徐州供电局";
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

    public List<DeviceOutSubTaskItem> getIoTaskDets() {
        return ioTaskDets;
    }

    public void setIoTaskDets(List<DeviceOutSubTaskItem> ioTaskDets) {
        this.ioTaskDets = ioTaskDets;
    }
}
