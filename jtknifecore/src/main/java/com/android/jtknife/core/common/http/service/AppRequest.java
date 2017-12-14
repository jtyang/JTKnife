package com.android.jtknife.core.common.http.service;


import android.text.TextUtils;

import com.android.jtknife.core.common.http.model.HttpMethod;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public class AppRequest {

    private static final String ENCODING = "utf-8";

    private String mUrl;

    private HttpMethod mMethod;

    private byte[] mData;

    private Object extraObj;

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

    public AppRequest setMethod(HttpMethod method) {
        mMethod = method;
        return this;
    }

    public byte[] getData() {
        return mData;
    }

    public AppRequest setData(byte[] data) {
        mData = data;
        return this;
    }

    public AppRequest setData(Map<String, String> values) {
        setData(encodeParam(values));
        return this;
    }

    public AppRequest setData(String content) {
        if (!TextUtils.isDigitsOnly(content)) {
            setData(content.getBytes());
        }
        return this;
    }

    public void setExtraObj(Object o){
        this.extraObj = o;
    }

    public Object getExtraObj(){
        return extraObj;
    }

    private byte[] encodeParam(Map<String, String> value) {
        if (value == null || value.size() == 0) {
            return null;
        }
        StringBuilder buffer = new StringBuilder();
        int count = 0;
        try {
            for (Map.Entry<String, String> entry : value.entrySet()) {
                buffer.append(URLEncoder.encode(entry.getKey(), ENCODING)).append("=").
                        append(URLEncoder.encode(entry.getValue(), ENCODING));
                if (count != value.size() - 1) {
                    buffer.append("&");
                }
                count++;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buffer.toString().getBytes();
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
