package com.sgcc.pda.jszp.bean;

/**
 * author:赵锦
 * date:2018/8/20 0020 10:19
 *
 * 快递确认详情
 */
public class ExpressConfirmResultEntity extends BaseEntity {

    private ExpressDistAutoesItem expressDistAuto;

    public ExpressDistAutoesItem getExpressDistAuto() {
        return expressDistAuto;
    }

    public void setExpressDistAuto(ExpressDistAutoesItem expressDistAuto) {
        this.expressDistAuto = expressDistAuto;
    }
}
