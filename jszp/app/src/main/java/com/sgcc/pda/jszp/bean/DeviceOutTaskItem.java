package com.sgcc.pda.jszp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 平库出库列表
 */
public class DeviceOutTaskItem implements Serializable {
    private String taskNo;//配送任务编号
    @SerializedName("splitTasks")
    private List<DeviceOutSecondSubTaskItem> subTaskItems;


    public DeviceOutTaskItem(List<DeviceOutSecondSubTaskItem> subTaskItems) {
        this.taskNo = "201805221234567890";
        this.subTaskItems = subTaskItems;
    }

    public void addSubTask(DeviceOutSecondSubTaskItem deviceOutSubTaskItem) {
        subTaskItems.add(deviceOutSubTaskItem);
    }

    public void removeSubTask(DeviceOutSecondSubTaskItem deviceOutSubTaskItem) {
        subTaskItems.remove(deviceOutSubTaskItem);
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public List<DeviceOutSecondSubTaskItem> getSubTaskItems() {
        return subTaskItems;
    }

    public void setSubTaskItems(List<DeviceOutSecondSubTaskItem> subTaskItems) {
        this.subTaskItems = subTaskItems;
    }
}
