package com.android.jtknife.core.common.http.service;


import com.android.jtknife.core.common.http.model.HttpMethod;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public class AppRequest {

    private String mUrl;

    private HttpMethod mMethod;

    private byte[] mData;

    private AppResponse mResponse;

    private String mContentType;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public HttpMethod getMethod() {
        return mMethod;
    }

    public void setMethod(HttpMethod method) {
        mMethod = method;
    }

    public byte[] getData() {
        return mData;
    }

    public void setData(byte[] data) {
        mData = data;
    }

    public AppResponse getResponse() {
        return mResponse;
    }

    public void setResponse(AppResponse response) {
        mResponse = response;
    }

    public String getContentType() {
        return mContentType;
    }

    public void setContentType(String contentType) {
        mContentType = contentType;
    }
}
