package com.sgcc.pda.jszp.bean;

import java.util.List;

/**
 * author:赵锦
 * date:2018/8/20 0020 10:19
 *
 * 快递确认列表
 */
public class ExpressConfirmListResultEntity extends BaseEntity {

    private List<ExpressDistAutoesItem> expressDistAutoes;

    public List<ExpressDistAutoesItem> getExpressDistAutoes() {
        return expressDistAutoes;
    }

    public void setExpressDistAutoes(List<ExpressDistAutoesItem> expressDistAutoes) {
        this.expressDistAutoes = expressDistAutoes;
    }
}
