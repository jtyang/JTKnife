package com.android.jtknife.core.common.http.convert;

import com.android.jtknife.core.common.http.model.HttpResponse;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public interface Converter<T> {

    T convertResponse(HttpResponse response) throws Throwable;
}
