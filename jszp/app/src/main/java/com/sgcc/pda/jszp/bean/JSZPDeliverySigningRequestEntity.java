package com.sgcc.pda.jszp.bean;

public class JSZPDeliverySigningRequestEntity {
    private String UID;//UID
    private String userId;//用户编号
    private String splitTaskNo;//拆分任务编号
    private String randomCode;//动态口令
    private String MAC;//传输MAC

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSplitTaskNo() {
        return splitTaskNo;
    }

    public void setSplitTaskNo(String splitTaskNo) {
        this.splitTaskNo = splitTaskNo;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
}
