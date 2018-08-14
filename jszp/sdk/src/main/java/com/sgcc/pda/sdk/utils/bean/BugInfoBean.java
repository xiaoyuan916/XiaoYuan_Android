package com.sgcc.pda.sdk.utils.bean;

/**
 * Created by Yang on 2017/1/23.
 */
public class BugInfoBean {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //硬件版本号
    private String dev_info;
    //当前操作系统版本号
    private String os_version;
    //软件版本号
    private String app_version;
    //操作员id
    private String userid;
    //异常产生时间
    private String ctime;
    //异常详细信息
    private String detail;
    //元数据
    private String data;
    //上传状态
    private String Status;
    //异常类型
    private String etype;

    public String getDev_info() {
        return dev_info;
    }

    public void setDev_info(String dev_info) {
        this.dev_info = dev_info;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEtype() {
        return etype;
    }

    public void setEtype(String etype) {
        this.etype = etype;
    }

}
