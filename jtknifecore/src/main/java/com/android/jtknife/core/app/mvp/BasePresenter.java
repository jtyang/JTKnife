package com.android.jtknife.core.app.mvp;

import android.support.annotation.Nullable;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/11/14
 */
public abstract class BasePresenter<V extends BaseView> {

    @Nullable
    private V view;

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }


    @Nullable
    public V getView() {
        return view;
    }

//    public abstract Bundle getStatus();
//
//    public abstract void loadStatus(Bundle bundle);


}
