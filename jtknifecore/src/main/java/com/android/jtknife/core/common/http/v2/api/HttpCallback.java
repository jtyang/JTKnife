package com.android.jtknife.core.common.http.v2.api;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/16
 */
public abstract class HttpCallback {
    public abstract void onSuccess(String response);

    public abstract void onError(int errorCode, String errorMessage);
}
