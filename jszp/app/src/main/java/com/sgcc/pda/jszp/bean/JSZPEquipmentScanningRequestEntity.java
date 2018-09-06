package com.sgcc.pda.jszp.bean;

public class JSZPEquipmentScanningRequestEntity extends BaseRequestEntity {
    private String barCodes;//条码
    private String beginBarCode;//设备开始条码
    private String endBarCode;//设备结束条码
    private boolean ioFlag;//是否出入库
    private String relaNo;//关联单号
    private String equipCode;//设备码
    private boolean resultFlag;//是否返回扫描结果

    public String getBarCodes() {
        return barCodes;
    }

    public void setBarCodes(String barCodes) {
        this.barCodes = barCodes;
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


    public String getRelaNo() {
        return relaNo;
    }

    public void setRelaNo(String relaNo) {
        this.relaNo = relaNo;
    }

    public String getEquipCode() {
        return equipCode;
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }

    public boolean isIoFlag() {
        return ioFlag;
    }

    public void setIoFlag(boolean ioFlag) {
        this.ioFlag = ioFlag;
    }

    public boolean isResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(boolean resultFlag) {
        this.resultFlag = resultFlag;
    }
}
