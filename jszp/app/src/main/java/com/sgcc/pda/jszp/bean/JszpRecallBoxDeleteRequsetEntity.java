package com.sgcc.pda.jszp.bean;

/**
 * author:xuxiaoyuan
 * date:2018/9/7
 */
public class JszpRecallBoxDeleteRequsetEntity extends BaseRequestEntity {
    private String recallId;//周转箱召回id
    private String barCode;//周转箱条码

    public String getRecallId() {
        return recallId;
    }

    public void setRecallId(String recallId) {
        this.recallId = recallId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
