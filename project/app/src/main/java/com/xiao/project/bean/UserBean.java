package com.xiao.project.bean;

/**
 * author:xuxiaoyuan
 * date:2019/1/25
 */
public class UserBean {
    private String mFirstName;
    private String mLastName;

    public UserBean(String firstName, String lastName) {
        this.mFirstName = firstName;
        this.mLastName = lastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }
}
