package com.sgcc.pda.jszp.bean;

import java.util.List;

/**
 * 装车确认  列表页面结果集
 */
public class DeliveryConfirmResultEntity extends BaseEntity {
    private List<LogisticsDistAutoesItem> logisticsDistAutoes;

    public List<LogisticsDistAutoesItem> getLogisticsDistAutoes() {
        return logisticsDistAutoes;
    }

    public void setLogisticsDistAutoes(List<LogisticsDistAutoesItem> logisticsDistAutoes) {
        this.logisticsDistAutoes = logisticsDistAutoes;
    }
}
