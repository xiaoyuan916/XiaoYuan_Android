package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SendCarItem implements Serializable {

    private DriverItem driver;
    private String carType;
    private List<SubSendCarItem> subSendCarItems = new ArrayList<>();

    public SendCarItem(DriverItem driver, String carType) {
        this.driver = driver;
        this.carType = carType;
    }

    public DriverItem getDriver() {
        return driver;
    }

    public void setDriver(DriverItem driver) {
        this.driver = driver;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    private void addSubSendCarItem(SubSendCarItem subSendCarItem) {
        subSendCarItems.add(subSendCarItem);
    }

    private void removeSubSendCarItem(SubSendCarItem subSendCarItem) {
        subSendCarItems.remove(subSendCarItem);
    }

    public List<SubSendCarItem> getSubSendCarItems() {
        return subSendCarItems;
    }

    public static class SubSendCarItem {
        private String address;
        private int deliverycount;
        private int returncount;
        private int state;//1是已经经过，2是当前到达，3是未到达

        public SubSendCarItem(String address, int deliverycount, int returncount, int state) {
            this.address = address;
            this.deliverycount = deliverycount;
            this.returncount = returncount;
            this.state = state;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getDeliverycount() {
            return deliverycount;
        }

        public void setDeliverycount(int deliverycount) {
            this.deliverycount = deliverycount;
        }

        public int getReturncount() {
            return returncount;
        }

        public void setReturncount(int returncount) {
            this.returncount = returncount;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }


    }
}
