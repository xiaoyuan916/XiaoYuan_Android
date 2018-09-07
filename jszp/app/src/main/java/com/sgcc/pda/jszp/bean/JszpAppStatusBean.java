package com.sgcc.pda.jszp.bean;

import com.contrarywind.interfaces.IPickerViewData;

/**
 * author:xuxiaoyuan
 * date:2018/9/6
 */
public class JszpAppStatusBean implements IPickerViewData {
    private String appStatusId;
    private String appStatusName;

    public JszpAppStatusBean(String appStatusId, String appStatusName) {
        this.appStatusId = appStatusId;
        this.appStatusName = appStatusName;
    }

    public String getAppStatusId() {
        return appStatusId;
    }

    public void setAppStatusId(String appStatusId) {
        this.appStatusId = appStatusId;
    }

    public String getAppStatusName() {
        return appStatusName;
    }

    public void setAppStatusName(String appStatusName) {
        this.appStatusName = appStatusName;
    }

    @Override
    public String getPickerViewText() {
        return appStatusName;
    }
}
