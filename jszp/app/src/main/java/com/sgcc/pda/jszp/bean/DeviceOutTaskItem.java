package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeviceOutTaskItem implements Serializable {


    private String num;
    private List<DeviceOutSubTaskItem> subTaskItems = new ArrayList<>();


    public DeviceOutTaskItem(String num) {
        this.num = num;
    }

    public void addSubTask(DeviceOutSubTaskItem deviceOutSubTaskItem) {
        subTaskItems.add(deviceOutSubTaskItem);
    }

    public void removeSubTask(DeviceOutSubTaskItem deviceOutSubTaskItem) {
        subTaskItems.remove(deviceOutSubTaskItem);
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<DeviceOutSubTaskItem> getSubTaskItems() {
        return subTaskItems;
    }

    public void setSubTaskItems(List<DeviceOutSubTaskItem> subTaskItems) {
        this.subTaskItems = subTaskItems;
    }
}
