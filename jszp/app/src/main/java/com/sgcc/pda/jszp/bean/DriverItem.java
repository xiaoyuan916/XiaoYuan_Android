package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

public class DriverItem implements Serializable {

    private String name;
    private String carnum;
    private String phone;
    private String time="";
    public DriverItem(String name, String carnum, String phone) {
        this.name = name;
        this.carnum = carnum;
        this.phone = phone;
    }

    public DriverItem(String name, String carnum, String phone, String time) {
        this.name = name;
        this.carnum = carnum;
        this.phone = phone;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
