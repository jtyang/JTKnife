package com.android.jtknife.core.common.http.service;

import com.android.jtknife.core.common.http.model.HttpResponse;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public abstract class AppResponse {

    public abstract void onSuccess(AppRequest request, HttpResponse response);

    public abstract void onError(int errorCode, String errorMsg);

}
