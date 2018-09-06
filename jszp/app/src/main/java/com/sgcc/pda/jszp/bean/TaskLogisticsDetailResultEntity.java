package com.sgcc.pda.jszp.bean;


/**
 * 获取物流详情数据实体类
 */
public class TaskLogisticsDetailResultEntity extends BaseEntity {

    private LogisticsDistAutoesItem logisticsDistAuto;

    public LogisticsDistAutoesItem getLogisticsDistAuto() {
        return logisticsDistAuto;
    }

    public void setLogisticsDistAuto(LogisticsDistAutoesItem logisticsDistAuto) {
        this.logisticsDistAuto = logisticsDistAuto;
    }
}
