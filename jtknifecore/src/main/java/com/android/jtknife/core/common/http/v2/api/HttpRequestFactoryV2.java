package com.android.jtknife.core.common.http.v2.api;

import com.android.jtknife.core.common.http.model.HttpMethod;
import com.android.jtknife.core.common.http.model.HttpRequest;

import java.io.IOException;
import java.net.URI;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/16
 */
public interface HttpRequestFactoryV2 {

    HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException;
}
