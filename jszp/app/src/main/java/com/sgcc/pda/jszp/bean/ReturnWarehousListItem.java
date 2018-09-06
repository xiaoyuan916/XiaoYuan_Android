package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ReturnWarehousListItem implements Serializable {


    private String order;
    private String sendCompany;



    private List<ReturnWarehousListItem> ReturnWarehousListItems = new LinkedList<>();

    public ReturnWarehousListItem(String order, String sendCompany) {

        this.order = order;
        this.sendCompany = sendCompany;
    }


    public void setOrder(String order) {
        this.order = order;
    }

    public String getSendCompany() {
        return sendCompany;
    }

    public void setSendCompany(String sendCompany) {
        this.sendCompany = sendCompany;
    }


    public String getOrder() {
        return order;
    }


    public List<ReturnWarehousListItem> getReturnWarehousListItems() {
        return ReturnWarehousListItems;
    }

    public void setReturnWarehousListItems(List<ReturnWarehousListItem> returnWarehousListItems) {
        ReturnWarehousListItems = returnWarehousListItems;
    }
}
