package com.android.jtknife.core.app.mvp;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/11/14
 */
public abstract class BasePresenter<V extends IBaseView> {

    private WeakReference<V> viewRef;

    public void attachView(@NonNull V view) {
        this.viewRef = new WeakReference<V>(view);
    }

    public void detachView() {
        if (this.viewRef != null) {
            this.viewRef.clear();
        }
    }

    public V getView() {
        if (this.viewRef != null) {
            return this.viewRef.get();
        }
        return null;
    }

//    public abstract Bundle getStatus();
//
//    public abstract void loadStatus(Bundle bundle);


}
