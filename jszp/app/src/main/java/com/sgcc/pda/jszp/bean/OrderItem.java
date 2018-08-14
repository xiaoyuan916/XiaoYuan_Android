package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class OrderItem implements Serializable {
    private boolean selected;
    private String num;
    private String dscAddress;
    private int boxcount;
    private int devicecount;
    private int type;//1是工程，2是业扩，3是维护
    private int state;//0是前往，1是即将前往，2是当前位置，3是已前往
    private List<DeviceItem> devices = new LinkedList<>();

    public OrderItem(String dscAddress, int state) {
        this.dscAddress = dscAddress;
        this.state = state;
    }

    public OrderItem(String dscAddress, int devicecount, int state) {
        this.dscAddress = dscAddress;
        this.devicecount = devicecount;
        this.state = state;
    }


    public OrderItem(boolean isselected, String num, int type, String dscAddress, int boxcount, int devicecount) {
        this.selected = isselected;
        this.num = num;
        this.type = type;
        this.dscAddress = dscAddress;
        this.boxcount = boxcount;
        this.devicecount = devicecount;
    }

    public OrderItem(int type, String num, int boxcount, int devicecount) {
        this.num = num;
        this.boxcount = boxcount;
        this.devicecount = devicecount;
        this.type = type;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getDevicecount() {
        int count = 0;
        for (int i = 0; i < devices.size(); i++) {
            count += devices.get(i).getCount();
        }
        return count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<DeviceItem> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceItem> devices) {
        this.devices = devices;
    }

    public String getDscAddress() {
        return dscAddress;
    }

    public void setDscAddress(String dscAddress) {
        this.dscAddress = dscAddress;
    }

    public int getBoxcount() {
        return boxcount;
    }

    public void setBoxcount(int boxcount) {
        this.boxcount = boxcount;
    }

    public void addDevice(DeviceItem deviceItem) {
        this.devices.add(deviceItem);
    }

    public void removeDevice(DeviceItem deviceItem) {
        this.devices.remove(deviceItem);

    }

    public void removeDevice(int position) {
        this.devices.remove(position);
    }
}
