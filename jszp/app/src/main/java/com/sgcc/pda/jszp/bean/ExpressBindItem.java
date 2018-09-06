package com.sgcc.pda.jszp.bean;

import java.util.ArrayList;
import java.util.List;

public class ExpressBindItem {

    private String pkgNo;//包号
//    private String billNo;//快递单据号
    private String taskNo;//配送任务编号
    private List<ExpressDeviceItem> expressDevs;//设备列表

    public ExpressBindItem(String pkgNo,String taskNo){
        this.pkgNo = pkgNo;
        this.taskNo = taskNo;
    }

    public class ExpressDeviceItem{
        private String barCode;//设备条码
        private String deviceName;//设备名称
//        private String pkgNo;//包裹号
        private String taskNo;//配送任务编号
        private String equipCateg;//设备类别
        private String equipCode;//设备码

     public ExpressDeviceItem(String barCode,String deviceName,String taskNo,String equipCateg,String equipCode){
            this.barCode = barCode;
            this.deviceName = deviceName;
            this.taskNo = taskNo;
            this.equipCateg = equipCateg;
            this.equipCode = equipCode;
        }

        ExpressDeviceItem(){

        }

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getTaskNo() {
            return taskNo;
        }

        public void setTaskNo(String taskNo) {
            this.taskNo = taskNo;
        }

        public String getEquipCateg() {
            return equipCateg;
        }

        public void setEquipCateg(String equipCateg) {
            this.equipCateg = equipCateg;
        }

        public String getEquipCode() {
            return equipCode;
        }

        public void setEquipCode(String equipCode) {
            this.equipCode = equipCode;
        }
    }

    public String getPkgNo() {
        return pkgNo;
    }

    public void setPkgNo(String pkgNo) {
        this.pkgNo = pkgNo;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public List<ExpressDeviceItem> getExpressDevs() {
        return expressDevs;
    }

    public void setExpressDevs(List<ExpressDeviceItem> expressDevs) {
        this.expressDevs = expressDevs;
    }

    public ExpressDeviceItem addExpressDeviceItem(String barCode,String deviceName,String taskNo,String equipCateg,String equipCode){
        ExpressDeviceItem expressDeviceItem = new ExpressDeviceItem(barCode,deviceName,taskNo,equipCateg,equipCode);
        if(expressDevs==null){
            expressDevs = new ArrayList<>();
        }
        expressDevs.add(expressDeviceItem);
        return  expressDeviceItem;
    }
}
