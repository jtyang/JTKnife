package com.android.jtknife.core.app.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.jtknife.core.app.BaseAppCompatActivity;

/**
 * 文件描述: MVP基类
 * 泛型v主要是为了参数2中的BasePresenter<V>
 *
 * @author yangjiantong
 * @date 2018/1/7
 */
public abstract class BaseMvpActivity<V, T extends BasePresenter<V>> extends BaseAppCompatActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected abstract T createPresenter();

}
