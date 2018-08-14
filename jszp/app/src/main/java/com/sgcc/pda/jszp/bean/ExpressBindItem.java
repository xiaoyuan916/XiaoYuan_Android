package com.sgcc.pda.jszp.bean;

import java.util.ArrayList;
import java.util.List;

public class ExpressBindItem {

    private String expressNo;//包号
//    private int num;//设备数
    private List<ExpressDeviceItem> deviceList;//设备列表

    public class ExpressDeviceItem{
        private String deviceNo;//设备号
        private String deviceName;//设备名称

        ExpressDeviceItem(){

        }

        public String getDeviceNo() {
            return deviceNo;
        }

        public void setDeviceNo(String deviceNo) {
            this.deviceNo = deviceNo;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }
//
//    public int getNum() {
//        return num;
//    }
//
//    public void setNum(int num) {
//        this.num = num;
//    }

    public List<ExpressDeviceItem> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<ExpressDeviceItem> deviceList) {
        this.deviceList = deviceList;
    }


    public ExpressDeviceItem addExpressDeviceItem(){
        ExpressDeviceItem expressDeviceItem = new ExpressDeviceItem();
        if(deviceList==null){
            deviceList = new ArrayList<>();
        }
        deviceList.add(expressDeviceItem);
        return  expressDeviceItem;
    }
    public ExpressDeviceItem addExpressDeviceItem(String no,String name){
        ExpressDeviceItem expressDeviceItem = new ExpressDeviceItem();
        expressDeviceItem.setDeviceName(name);
        expressDeviceItem.setDeviceNo(no);
        if(deviceList==null){
            deviceList = new ArrayList<>();
        }
        deviceList.add(expressDeviceItem);
        return  expressDeviceItem;
    }
}
