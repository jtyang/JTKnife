package com.android.jtknife.core.app.mvp;

import android.support.annotation.NonNull;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/11/14
 */
public abstract class BasePresenter<V> {

    private Reference<V> mViewRef;

    public void attachView(@NonNull V view) {
//        this.viewRef = new WeakReference<V>(view);
        this.mViewRef = new SoftReference<V>(view);
    }

    public void detachView() {
        if (this.mViewRef != null) {
            this.mViewRef.clear();
        }
    }

    public V getView() {
        if (this.mViewRef != null) {
            return this.mViewRef.get();
        }
        return null;
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

//    public abstract Bundle getStatus();
//
//    public abstract void loadStatus(Bundle bundle);


}
