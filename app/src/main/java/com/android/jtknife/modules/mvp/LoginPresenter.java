package com.android.jtknife.modules.mvp;

import com.android.jtknife.core.app.mvp.BasePresenter;
import com.android.jtknife.modules.mvp.bean.UserInfo;
import com.android.jtknife.modules.mvp.model.ILoginModel;
import com.android.jtknife.modules.mvp.model.LoginModel;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2018/1/7
 */
public class LoginPresenter extends BasePresenter<ILoginView> {


    private ILoginModel loginModel = new LoginModel();

    public void fetch(){
        if(getView() != null){
            loginModel.login("zhangsan", "123456", new ILoginModel.LoginCallback() {
                @Override
                public void onSuccess(UserInfo userInfo) {
                    if(getView() != null){
                        getView().showUserInfo(userInfo);
                    }
                }

                @Override
                public void onFailed(String msg) {

                }
            });
        }
    }
}
