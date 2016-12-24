package com.android.jtknife.model.entity;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/25
 */
public class UserInfo {
    public long userId;
    public String userName;
    public int age;
    public int sex;

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
