package com.sgcc.pda.jszp.bean;

public class JSZPReplenishmentLibraryRequestEntity extends BaseRequestEntity {
    private String planDetNo;//出库计划明细编号

    public String getPlanDetNo() {
        return planDetNo;
    }

    public void setPlanDetNo(String planDetNo) {
        this.planDetNo = planDetNo;
    }
}
