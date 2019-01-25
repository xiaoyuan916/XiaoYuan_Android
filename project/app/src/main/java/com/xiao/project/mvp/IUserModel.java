package com.xiao.project.mvp;

import com.xiao.project.bean.UserBean;

/**
 * author:xuxiaoyuan
 * date:2019/1/25
 */
public interface IUserModel {
    void setID(int id);

    void setFirstName(String firstName);

    void setLastName(String lastName);

    int getID();

    UserBean load(int id);//通过id读取user信息,返回一个UserBean
}

