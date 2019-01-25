package com.xiao.project.mvp.impl;

import android.util.SparseArray;

import com.xiao.project.bean.UserBean;
import com.xiao.project.mvp.IUserModel;

/**
 * author:xuxiaoyuan
 * date:2019/1/25
 */
public class UserModel implements IUserModel {

    private String mFristName;
    private String mLastName;
    private int mID;
    private SparseArray<UserBean> mUsererArray = new SparseArray<UserBean>();

    @Override
    public void setID(int id) {
        mID = id;
    }

    @Override
    public void setFirstName(String firstName) {
        mFristName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        mLastName = lastName;
        UserBean UserBean = new UserBean(mFristName, mLastName);
        mUsererArray.append(mID, UserBean);
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public UserBean load(int id) {
        mID = id;
        UserBean userBean = mUsererArray.get(mID, new UserBean("not found",
                "not found"));
        return userBean;

    }

}