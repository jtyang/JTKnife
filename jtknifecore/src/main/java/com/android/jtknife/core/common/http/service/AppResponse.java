package com.android.jtknife.core.common.http.service;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public abstract class AppResponse<T> {

    public abstract void success(AppRequest request, T data);

    public abstract void fail(int errorCode, String errorMsg);

}
