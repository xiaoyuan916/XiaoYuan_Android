package com.sgcc.pda.jszp.bean;

/**
 * author:xuxiaoyuan
 * date:2018/9/10
 */
public class JszpDpRequestEntity extends BaseRequestEntity{
    private String orgNo;
    private String orgType;

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }
}
