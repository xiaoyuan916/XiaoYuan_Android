package com.sgcc.pda.jszp.bean;


/**
 * 返程入库确认
 */
public class ReturnWarehouseGoodsConfirmRequestEntity extends BaseRequestEntity {
    private String planDetNo;//出库计划明细编号

    public String getPlanDetNo() {
        return planDetNo;
    }

    public void setPlanDetNo(String planDetNo) {
        this.planDetNo = planDetNo;
    }
}
