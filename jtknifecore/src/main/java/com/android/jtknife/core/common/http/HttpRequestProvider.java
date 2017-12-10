package com.android.jtknife.core.common.http;

import com.android.jtknife.core.common.http.model.HttpMethod;
import com.android.jtknife.core.common.http.model.HttpRequest;
import com.android.jtknife.core.common.http.utils.Utills;

import java.io.IOException;
import java.net.URI;

import okhttp3.MediaType;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public class HttpRequestProvider {

    private static boolean OKHTTP_REQUEST = Utills.isExist("okhttp3.OkHttpClient", HttpRequestProvider.class.getClassLoader());

    private HttpRequestFactory mHttpRequestFactory;

    public HttpRequestProvider() {
        if (OKHTTP_REQUEST) {
            mHttpRequestFactory = new OkHttpRequestFactory();
        } else {
//            mHttpRequestFactory = new OriginHttpRequestFactory();
        }
    }

    public HttpRequest getHttpRequest(URI uri, HttpMethod httpMethod) throws IOException {
        return mHttpRequestFactory.createHttpRequest(uri, httpMethod);
    }

    public HttpRequest getHttpRequest(URI uri, HttpMethod httpMethod, MediaType mediaType) throws IOException {
        return mHttpRequestFactory.createHttpRequest(uri, httpMethod, mediaType);
    }

    public HttpRequestFactory getHttpRequestFactory() {
        return mHttpRequestFactory;
    }

    public void setHttpRequestFactory(HttpRequestFactory httpRequestFactory) {
        mHttpRequestFactory = httpRequestFactory;
    }

}

