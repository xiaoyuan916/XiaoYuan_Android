package com.sgcc.pda.jszp.bean;


/**
 * 设备拣选请求实体
 */
public class DevicePickFinishRequestEntity extends BaseRequestEntity {

    private String barCode;
    private String beginBarCode;
    private String endBarCode;
    private String arriveBatchNo;
    private String statusCode;
    private String equipCateg;
    private String typeCode;
    private String sortCode;
    private String wiringMode;
    private String voltCode;
    private String ratedCurrent;
    private String apPreLevelCode;
    private String equipRate;
    private String voltRatioCode;
    private String rcRatioCode;
    private String taPreCode;
    private String modelCode;
    private String specCode;
    private String commMode;
    private String carrierWaveCenterFre;
    private String commPortCode;
    private String protocolCode;
    private String collMode;
    private int pageNo;
    private int pageSize;

    public String getCollMode() {
        return collMode;
    }

    public void setCollMode(String collMode) {
        this.collMode = collMode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBeginBarCode() {
        return beginBarCode;
    }

    public void setBeginBarCode(String beginBarCode) {
        this.beginBarCode = beginBarCode;
    }

    public String getEndBarCode() {
        return endBarCode;
    }

    public void setEndBarCode(String endBarCode) {
        this.endBarCode = endBarCode;
    }

    public String getArriveBatchNo() {
        return arriveBatchNo;
    }

    public void setArriveBatchNo(String arriveBatchNo) {
        this.arriveBatchNo = arriveBatchNo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getEquipCateg() {
        return equipCateg;
    }

    public void setEquipCateg(String equipCateg) {
        this.equipCateg = equipCateg;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getWiringMode() {
        return wiringMode;
    }

    public void setWiringMode(String wiringMode) {
        this.wiringMode = wiringMode;
    }

    public String getVoltCode() {
        return voltCode;
    }

    public void setVoltCode(String voltCode) {
        this.voltCode = voltCode;
    }

    public String getRatedCurrent() {
        return ratedCurrent;
    }

    public void setRatedCurrent(String ratedCurrent) {
        this.ratedCurrent = ratedCurrent;
    }

    public String getApPreLevelCode() {
        return apPreLevelCode;
    }

    public void setApPreLevelCode(String apPreLevelCode) {
        this.apPreLevelCode = apPreLevelCode;
    }

    public String getEquipRate() {
        return equipRate;
    }

    public void setEquipRate(String equipRate) {
        this.equipRate = equipRate;
    }

    public String getVoltRatioCode() {
        return voltRatioCode;
    }

    public void setVoltRatioCode(String voltRatioCode) {
        this.voltRatioCode = voltRatioCode;
    }

    public String getRcRatioCode() {
        return rcRatioCode;
    }

    public void setRcRatioCode(String rcRatioCode) {
        this.rcRatioCode = rcRatioCode;
    }

    public String getTaPreCode() {
        return taPreCode;
    }

    public void setTaPreCode(String taPreCode) {
        this.taPreCode = taPreCode;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public String getCommMode() {
        return commMode;
    }

    public void setCommMode(String commMode) {
        this.commMode = commMode;
    }

    public String getCarrierWaveCenterFre() {
        return carrierWaveCenterFre;
    }

    public void setCarrierWaveCenterFre(String carrierWaveCenterFre) {
        this.carrierWaveCenterFre = carrierWaveCenterFre;
    }

    public String getCommPortCode() {
        return commPortCode;
    }

    public void setCommPortCode(String commPortCode) {
        this.commPortCode = commPortCode;
    }

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
