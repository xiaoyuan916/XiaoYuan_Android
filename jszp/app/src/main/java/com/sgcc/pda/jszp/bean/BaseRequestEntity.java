package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

public class BaseRequestEntity implements Serializable{
    private String UID;
    private String userId;
    private String baseNo;
    private String MAC;

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

    public String getBaseNo() {
        return baseNo;
    }

    public void setBaseNo(String baseNo) {
        this.baseNo = baseNo;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
}
