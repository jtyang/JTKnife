package com.android.jtknife.core.common.http.v2.api;

import com.android.jtknife.core.common.http.model.HttpMethod;
import com.android.jtknife.core.common.http.model.HttpRequest;
import com.android.jtknife.core.common.http.v2.OkHttpRequestImpl;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/16
 */
public class OkHttpRequestFactoryV2  implements HttpRequestFactoryV2 {

    private OkHttpClient mClient;

    public OkHttpRequestFactoryV2() {
        this.mClient = new OkHttpClient();
    }

    public OkHttpRequestFactoryV2(OkHttpClient client) {
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
        return new OkHttpRequestImpl(mClient, method, uri.toString());
    }

}
