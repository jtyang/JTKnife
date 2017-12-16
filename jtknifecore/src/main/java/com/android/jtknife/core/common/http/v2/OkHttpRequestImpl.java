package com.android.jtknife.core.common.http.v2;

import com.android.jtknife.core.common.http.model.HttpHeader;
import com.android.jtknife.core.common.http.model.HttpMethod;
import com.android.jtknife.core.common.http.model.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/16
 */
public class OkHttpRequestImpl extends AbsHttpRequest {

    private OkHttpClient mClient;
    private HttpMethod mMethod;
    private String mUrl;

    private Request.Builder mRequestBuilder;

    public OkHttpRequestImpl(OkHttpClient client, HttpMethod method, String url) {
        this.mClient = client;
        this.mMethod = method;
        this.mUrl = url;
    }

    @Override
    protected HttpResponse executeInternal() throws IOException {
        //OKHttp执行同步请求
        Response response = mClient.newCall(mRequestBuilder.build()).execute();
        return new OkHttpResponseImpl(response);
    }

    @Override
    protected AbsHttpRequest makeGetRequest() throws IOException {
        genericRequestBuilder(null);
        return this;
    }

    @Override
    protected AbsHttpRequest makePostFormRequest(byte[] data) throws IOException {
        getBodyOutputStream().write(data);
        genericRequestBuilder(RequestBody.create(HttpHeader.MEDIA_TYPE_FORM, getBodyOutputStream().toByteArray()));
        return this;
    }

    @Override
    protected AbsHttpRequest makePostJsonRequest(byte[] data) throws IOException {
        genericRequestBuilder(RequestBody.create(HttpHeader.MEDIA_TYPE_JSON, getBodyOutputStream().toByteArray()));
        return this;
    }

    @Override
    public HttpMethod getMethod() {
        return mMethod;
    }

    @Override
    public URI getUri() {
        return URI.create(mUrl);
    }

    public AbsHttpRequest makeDownloadFileRequest() throws IOException {
        genericRequestBuilder(null);
        return this;
    }

    public AbsHttpRequest makeUploadFileRequest(File file) throws IOException {
        return this;
    }

    private void genericRequestBuilder(RequestBody requestBody) throws IOException {
        Request.Builder builder = new Request.Builder().url(mUrl).method(mMethod.name(), requestBody);
        for (Map.Entry<String, String> entry : mHeader.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        mRequestBuilder = builder;
    }

}
