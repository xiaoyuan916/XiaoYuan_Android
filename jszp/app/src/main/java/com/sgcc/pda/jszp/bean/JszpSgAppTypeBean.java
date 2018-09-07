package com.sgcc.pda.jszp.bean;

import com.contrarywind.interfaces.IPickerViewData;

/**
 * author:xuxiaoyuan
 * date:2018/9/6
 * 订单类型
 */
public class JszpSgAppTypeBean implements IPickerViewData {
    private String sgAppTypeId;
    private String sgAppTypeName;

    public JszpSgAppTypeBean(String sgAppTypeId, String sgAppTypeName) {
        this.sgAppTypeId = sgAppTypeId;
        this.sgAppTypeName = sgAppTypeName;
    }

    public String getSgAppTypeId() {
        return sgAppTypeId;
    }

    public void setSgAppTypeId(String sgAppTypeId) {
        this.sgAppTypeId = sgAppTypeId;
    }

    public String getSgAppTypeName() {
        return sgAppTypeName;
    }

    public void setSgAppTypeName(String sgAppTypeName) {
        this.sgAppTypeName = sgAppTypeName;
    }

    @Override
    public String getPickerViewText() {
        return sgAppTypeName;
    }
}
