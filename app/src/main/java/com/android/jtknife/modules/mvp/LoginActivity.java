package com.android.jtknife.modules.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.jtknife.R;
import com.android.jtknife.core.app.mvp.BaseMvpActivity;
import com.android.jtknife.modules.mvp.bean.UserInfo;

/**
 * 文件描述:mvp测试activity
 * <p>
 * activity：view显示
 * presenter：业务调用
 * model：获取数据来源--网络、本地db、本地文件等
 * 一个activity对应一个presenter，一个presenter可调用多个model，这样可灵活解耦UI、业务调用和数据来源三者之间的关系
 *
 * @author yangjiantong
 * @date 2018/1/7
 */
public class LoginActivity extends BaseMvpActivity<ILoginView, LoginPresenter> implements ILoginView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.fetch();
    }

    @Override
    public void showUserInfo(UserInfo userInfo) {

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {

    }
}
