package com.sgcc.pda.jszp.bean;

/**
 * author:xuxiaoyuan
 * date:2018/9/6
 */
public class JszpBoxScanRequestEntity extends BaseRequestEntity {
    private String recallId;
    private String barCodes;

    public String getRecallId() {
        return recallId;
    }

    public void setRecallId(String recallId) {
        this.recallId = recallId;
    }

    public String getBarCodes() {
        return barCodes;
    }

    public void setBarCodes(String barCodes) {
        this.barCodes = barCodes;
    }
}
