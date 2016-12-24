package com.android.jtknife.model;

import com.android.jtknife.model.entity.UserInfo;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/25
 */
public class UserModel {

    public UserInfo login(String userName, String password) {
        UserInfo userInfo = new UserInfo();
        userInfo.userId = 10086;
        userInfo.userName = "zhangsan";
        userInfo.age = 108;
        userInfo.sex = 1;
        return userInfo;
    }
}
