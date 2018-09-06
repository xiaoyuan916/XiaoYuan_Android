package com.sgcc.pda.jszp.bean;

import java.util.List;

/**
 * author:赵锦
 * date:2018/8/23 0023 16:14
 */
public class ExpressBindRequestEntity extends BaseRequestEntity {
    private String taskNo;
    private String distAutoId;
    private String billNo;//快递单据标识
    private List<ExpressBindItem> expressPkgs;//包裹数据集

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getDistAutoId() {
        return distAutoId;
    }

    public void setDistAutoId(String distAutoId) {
        this.distAutoId = distAutoId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public List<ExpressBindItem> getExpressPkgs() {
        return expressPkgs;
    }

    public void setExpressPkgs(List<ExpressBindItem> expressPkgs) {
        this.expressPkgs = expressPkgs;
    }
}
