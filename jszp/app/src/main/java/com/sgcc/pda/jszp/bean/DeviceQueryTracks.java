package com.sgcc.pda.jszp.bean;

/**
 * author:赵锦
 * date:2018/8/31 0031 15:45
 *
 * 设备详情  跟踪信息
 */
public class DeviceQueryTracks {
    private String busi_id;//业务id
    private String busi_type;//业务类型
    private String date;//发生时间

   public DeviceQueryTracks(String busi_id,String busi_type,String date){
        this.busi_id = busi_id;
        this.busi_type = busi_type;
        this.date = date;
    }

    public String getBusi_id() {
        return busi_id;
    }

    public void setBusi_id(String busi_id) {
        this.busi_id = busi_id;
    }

    public String getBusi_type() {
        return busi_type;
    }

    public void setBusi_type(String busi_type) {
        this.busi_type = busi_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
