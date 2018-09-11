package com.sgcc.pda.jszp.bean;

/**
 * author:赵锦
 * date:2018/9/7 0007 09:20
 */
public class DeviceBoxScanBoxRequestEntity extends BaseRequestEntity {
    private String barCode;
    private boolean returnFlag;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public boolean isReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(boolean returnFlag) {
        this.returnFlag = returnFlag;
    }
}
