package com.sgcc.pda.jszp.bean;

import com.sgcc.pda.jszp.R;

import java.io.Serializable;

public class DeviceItem implements Serializable {
    //0是电表，1是采集器，2是集中器，3是通讯模块，4是互感器
    private int type;

    private String num;

    private String order_num;

    private String name;

    private int count;

    private int state = 0;//0是合格在库,1是存库超期

    public DeviceItem(String order_num) {
        this.order_num = order_num;
    }

    public DeviceItem(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public DeviceItem(int type, String name, int count) {
        this.type = type;
        this.name = name;
        this.count = count;
    }

    public DeviceItem(String num, int state) {
        this.num = num;
        this.state = state;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public static int getImgSourceId(int type, boolean selected) {
        int srcid = R.drawable.dianneng;
        switch (type) {
            case 0:
                srcid = selected ? R.drawable.diannengf : R.drawable.dianneng;
                break;
            case 1:
                srcid = selected ? R.drawable.caijiqif : R.drawable.caijiqi;
                break;
            case 2:
                srcid = selected ? R.drawable.jizhongqif : R.drawable.jizhongqi;
                break;
            case 3:
                srcid = selected ? R.drawable.tongxunf : R.drawable.tongxun;
                break;
            case 4:
                srcid = selected ? R.drawable.huganqif : R.drawable.huganqi;
                break;


        }
        return srcid;

    }

}
