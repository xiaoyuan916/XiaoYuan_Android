package com.sgcc.pda.hardware.event;

/**
 * 照片事件
 */
public class HardwareErroeEvent {

    private String type;
    private String detail;

    public HardwareErroeEvent(){}

    public HardwareErroeEvent(String type,String detail){
        this.type = type;
        this.detail = detail;
    }

    public String getDetail(){
        return detail;
    }

    public String getType(){
        return type;
    }
}
