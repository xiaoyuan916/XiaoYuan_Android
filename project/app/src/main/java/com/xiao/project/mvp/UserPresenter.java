package com.xiao.project.mvp;

import com.xiao.project.bean.UserBean;
import com.xiao.project.mvp.impl.UserModel;

/**
 * author:xuxiaoyuan
 * date:2019/1/25
 */
public class UserPresenter {
    private IUserView mUserView;
    private IUserModel mUserModel;

    public UserPresenter(IUserView view) {
        mUserView = view;
        mUserModel = new UserModel();
    }

    public void saveUser(int id, String firstName, String lastName) {
        mUserModel.setID(id);
        mUserModel.setFirstName(firstName);
        mUserModel.setLastName(lastName);
    }

    public void loadUser(int id) {
        UserBean user = mUserModel.load(id);
        mUserView.setFirstName(user.getFirstName());//通过调用IUserView的方法来更新显示
        mUserView.setLastName(user.getLastName());
    }
}

