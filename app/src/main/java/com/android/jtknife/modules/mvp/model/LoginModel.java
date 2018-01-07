package com.android.jtknife.modules.mvp.model;

import com.android.jtknife.modules.mvp.bean.UserInfo;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2018/1/7
 */
public class LoginModel implements ILoginModel {

    @Override
    public void login(final String userName, final String password, final LoginCallback callback) {
        //login ...
        new Thread(){
            @Override
            public void run() {
                if(callback != null){
                    UserInfo userInfo = new UserInfo();
                    userInfo.userName = userName;
                    userInfo.password = password;
                    userInfo.age = 18;
                    userInfo.avatar = "http://www.baidu.com";
                    callback.onSuccess(userInfo);
                }
            }
        }.start();
    }
}
