package com.android.jtknife.core.common.http.v2;

import com.android.jtknife.core.common.http.model.HttpHeader;
import com.android.jtknife.core.common.http.model.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/16
 */
public class OkHttpResponseImpl extends AbsHttpResponse{
    private Response mResponse;

    private HttpHeader mHeaders;

    public OkHttpResponseImpl(Response response) {
        this.mResponse = response;
    }

    @Override
    public HttpHeader getHeaders() {
        return null;
    }

    @Override
    public HttpStatus getStatus() {
        return null;
    }

    @Override
    public String getStatusMsg() {
        return null;
    }

    @Override
    public long getContentLength() {
        return 0;
    }

    @Override
    protected InputStream getBodyInternal() throws IOException {
        return null;
    }

    @Override
    protected void closeInternal() {

    }
}
