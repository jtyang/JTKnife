package com.android.jtknife.modules.mvp.model;

import com.android.jtknife.modules.mvp.bean.UserInfo;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2018/1/7
 */
public interface ILoginModel {

    void login(String userName,String password,LoginCallback callback);

    interface LoginCallback{
        void onSuccess(UserInfo userInfo);
        void onFailed(String msg);
    }
}
