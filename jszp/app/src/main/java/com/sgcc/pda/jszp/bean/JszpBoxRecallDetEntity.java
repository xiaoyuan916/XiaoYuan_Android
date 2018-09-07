package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

/**
 * author:xuxiaoyuan
 * date:2018/9/7
 */
public class JszpBoxRecallDetEntity implements Serializable{
    private String recallDetId;
    private String sortCode;
    private String barCode;
    private int sumQty;

    public int getSumQty() {
        return sumQty;
    }

    public void setSumQty(int sumQty) {
        this.sumQty = sumQty;
    }

    public String getRecallDetId() {
        return recallDetId;
    }

    public void setRecallDetId(String recallDetId) {
        this.recallDetId = recallDetId;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
