package com.sgcc.pda.jszp.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;

/**
 * author:xuxiaoyuan
 * date:2018/9/6
 */
public class JszpOrgListEntity implements IPickerViewData, Serializable {
    private String orgNo;//单位编号
    private String orgName;//单位名称
    private String nodeId;//单位节点id
    private String pNodeId;//单位父节点id

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getpNodeId() {
        return pNodeId;
    }

    public void setpNodeId(String pNodeId) {
        this.pNodeId = pNodeId;
    }

    @Override
    public String getPickerViewText() {
        return orgName;
    }
}
