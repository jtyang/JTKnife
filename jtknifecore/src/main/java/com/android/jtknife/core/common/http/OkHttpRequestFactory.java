package com.android.jtknife.core.common.http;

import com.android.jtknife.core.common.http.model.HttpMethod;
import com.android.jtknife.core.common.http.model.HttpRequest;
import com.android.jtknife.core.common.http.request.OkHttpRequest;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public class OkHttpRequestFactory implements HttpRequestFactory {

    private OkHttpClient mClient;

    public OkHttpRequestFactory() {
        this.mClient = new OkHttpClient();
    }

    public OkHttpRequestFactory(OkHttpClient client) {
        this.mClient = client;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.mClient = mClient.newBuilder().
                readTimeout(readTimeOut, TimeUnit.MILLISECONDS).
                build();
    }

    public void setWriteTimeOut(int writeTimeOut) {
        this.mClient = mClient.newBuilder().
                writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS).
                build();
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.mClient = mClient.newBuilder().
                connectTimeout(connectionTimeOut, TimeUnit.MILLISECONDS).
                build();
    }

    @Override
    public HttpRequest createHttpRequest(URI uri, HttpMethod method) {
        return new OkHttpRequest(mClient, method, uri.toString());
    }

    @Override
    public HttpRequest createHttpRequest(URI uri, HttpMethod method, MediaType mediaType) throws IOException {
        return new OkHttpRequest(mClient, method, uri.toString(), mediaType);
    }
}

