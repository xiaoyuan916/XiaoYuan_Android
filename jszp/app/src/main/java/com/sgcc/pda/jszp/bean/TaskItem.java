package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TaskItem implements Serializable {

    private boolean selected = false;
    private String num;
    private String address;
    private int boxcount;
    private int devicecount;
    private DriverItem driver;

    private int state;//0是未发车，1是已经发车
    private List<OrderItem> orderItems = new LinkedList<>();

    public TaskItem(String num, String address, int state) {
        this.num = num;
        this.address = address;
        this.state = state;
    }

    public TaskItem(String num, String address, int state, DriverItem driver) {
        this.num = num;
        this.address = address;
        this.state = state;
        this.driver = driver;
    }

    public String getDscAddress() {
        if (orderItems.size() > 0) return orderItems.get(orderItems.size() - 1).getDscAddress();
        else return "";
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addOrder(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public void removeOrder(OrderItem orderItem) {
        orderItems.remove(orderItem);
    }

    public int getBoxcount() {
        int count = 0;
        for (int i = 0; i < orderItems.size(); i++) {
            count += orderItems.get(i).getBoxcount();
        }
        return count;
    }


    public int getDevicecount() {
        int count = 0;
        for (int i = 0; i < orderItems.size(); i++) {
            count += orderItems.get(i).getDevicecount();
        }
        return count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public String getTime() {
        if (driver == null) return "";
        return driver.getTime();
    }

    public String getCarInfo() {
        if (driver == null) return "";
        return driver.getName() + " " + driver.getCarnum() + " " + driver.getPhone();


    }
}
