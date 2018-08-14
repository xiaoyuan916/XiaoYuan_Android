package com.sgcc.pda.jszp.bean;

public class ReturnItem {

    private boolean selected;
    private String tasknum;
    private String devicename;
    private int devicetype;
    private int plancount;
    private int realcount=-1;


    public ReturnItem(boolean selected, String tasknum, int devicetype, String devicename, int plancount, int realcount) {
        this.tasknum = tasknum;
        this.devicetype = devicetype;
        this.devicename = devicename;
        this.plancount = plancount;
        this.realcount = realcount;
        this.selected = selected;
    }

    public String getTasknum() {
        return tasknum;
    }

    public void setTasknum(String tasknum) {
        this.tasknum = tasknum;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public int getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(int devicetype) {
        this.devicetype = devicetype;
    }

    public int getPlancount() {
        return plancount;
    }

    public void setPlancount(int plancount) {
        this.plancount = plancount;
    }

    public int getRealcount() {
        return realcount;
    }

    public void setRealcount(int realcount) {
        this.realcount = realcount;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
