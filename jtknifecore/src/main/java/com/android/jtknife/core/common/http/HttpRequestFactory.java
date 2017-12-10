package com.android.jtknife.core.common.http;

import com.android.jtknife.core.common.http.model.HttpMethod;
import com.android.jtknife.core.common.http.model.HttpRequest;

import java.io.IOException;
import java.net.URI;

import okhttp3.MediaType;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public interface HttpRequestFactory {

    HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException;

    HttpRequest createHttpRequest(URI uri, HttpMethod method, MediaType mediaType) throws IOException;
}
