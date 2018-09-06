package com.sgcc.pda.jszp.bean;

/**
 * 物流派车  详情结果集
 */
public class LogisticsSendDetailResultEntity extends BaseEntity{
    private LogisticsDistAutoesItem logisticsDistAuto;

    public LogisticsDistAutoesItem getLogisticsDistAuto() {
        return logisticsDistAuto;
    }

    public void setLogisticsDistAuto(LogisticsDistAutoesItem logisticsDistAuto) {
        this.logisticsDistAuto = logisticsDistAuto;
    }
}
