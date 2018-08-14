package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

public class DeviceOutSubTaskItem implements Serializable {
    private String num;
    private String company;
    private int taskcount;
    private int outcount;
    private String describe;
    //0是电表，1是采集器，2是集中器，3是通讯模块，4是互感器
    private int devicetype;
    private int state;//0是新建，1是出库中，2是已下发，3是已完成
    private  boolean ishead=true;

    public DeviceOutSubTaskItem(String num, String company, int taskcount, int outcount, String describe, int devicetype, int state, boolean ishead) {
        this.num = num;
        this.company = company;
        this.taskcount = taskcount;
        this.outcount = outcount;
        this.describe = describe;
        this.devicetype = devicetype;
        this.state = state;
        this.ishead = ishead;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getTaskcount() {
        return taskcount;
    }

    public void setTaskcount(int taskcount) {
        this.taskcount = taskcount;
    }

    public int getOutcount() {
        return outcount;
    }

    public void setOutcount(int outcount) {
        this.outcount = outcount;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(int devicetype) {
        this.devicetype = devicetype;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isIshead() {
        return ishead;
    }

    public void setIshead(boolean ishead) {
        this.ishead = ishead;
    }
}
