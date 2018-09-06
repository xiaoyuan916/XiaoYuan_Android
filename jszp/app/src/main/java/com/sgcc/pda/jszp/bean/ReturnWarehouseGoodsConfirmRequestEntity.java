package com.sgcc.pda.jszp.bean;


/**
 * 返程入库确认
 */
public class ReturnWarehouseGoodsConfirmRequestEntity extends BaseRequestEntity {
    private long planDetNo;//出库计划明细编号

    public long getPlanDetNo() {
        return planDetNo;
    }

    public void setPlanDetNo(long planDetNo) {
        this.planDetNo = planDetNo;
    }
}
