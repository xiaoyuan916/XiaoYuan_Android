package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

/**
 *物流派车  选择 车辆信息
 */
public class AutoDocsItem implements Serializable {

    private String autoNo;//车辆编号
    private String companyId;//物流公司编号
    private String companyName;//物流公司名称
    private String autoBrandNo;//车牌号
    private String autoType;//车辆类型  //01大型车  02中型车  03小型车
    private String loaded;//吨数
    private String whNo;//配送库房编号
    private String whName;//配送库房名称
    private String staffName;//司机姓名
    private String phoneNo;//联系电话



    public String getAutoNo() {
        return autoNo;
    }

    public void setAutoNo(String autoNo) {
        this.autoNo = autoNo;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAutoBrandNo() {
        return autoBrandNo;
    }

    public void setAutoBrandNo(String autoBrandNo) {
        this.autoBrandNo = autoBrandNo;
    }

    public String getAutoType() {
        return autoType;
    }

    public void setAutoType(String autoType) {
        this.autoType = autoType;
    }

    public String getWhNo() {
        return whNo;
    }

    public void setWhNo(String whNo) {
        this.whNo = whNo;
    }

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getLoaded() {
        return loaded;
    }

    public void setLoaded(String loaded) {
        this.loaded = loaded;
    }
}
