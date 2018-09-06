package com.sgcc.pda.jszp.bean;

public class JSZPReplenishmentLibraryRequestEntity extends BaseRequestEntity {
    private long planDetNo;//出库计划明细编号

    public long getPlanDetNo() {
        return planDetNo;
    }

    public void setPlanDetNo(long planDetNo) {
        this.planDetNo = planDetNo;
    }
}
