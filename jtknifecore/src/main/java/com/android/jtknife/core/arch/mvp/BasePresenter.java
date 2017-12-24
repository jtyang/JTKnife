package com.android.jtknife.core.arch.mvp;

import android.arch.lifecycle.LifecycleObserver;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/24
 */
public interface BasePresenter<V> extends LifecycleObserver {
    void onDetach();
}
