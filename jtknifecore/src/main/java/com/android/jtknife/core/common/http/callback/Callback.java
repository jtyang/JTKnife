package com.android.jtknife.core.common.http.callback;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public interface Callback<T> {

    void onSuccess();

    void onError();
}
