package com.android.jtknife.core.common.http.request;

import com.android.jtknife.core.common.http.model.HttpHeader;
import com.android.jtknife.core.common.http.model.HttpParams;
import com.android.jtknife.core.common.http.model.HttpRequest;
import com.android.jtknife.core.common.http.model.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public abstract class AbstractHttpRequest implements HttpRequest {

    protected int mRetryCount;

    protected HttpHeader mHeader = new HttpHeader();
    protected HttpParams mParams = new HttpParams();

    private ZipOutputStream mZip;
    private boolean executed;

    public AbstractHttpRequest() {

//        //默认添加 Accept-Language
//        String acceptLanguage = HttpHeader.getAcceptLanguage();
//        mHeader.setAccept();
//        headers(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, acceptLanguage);
//        //默认添加 User-Agent
//        String userAgent = HttpHeaders.getUserAgent();
//        if (!TextUtils.isEmpty(userAgent)) headers(HttpHeaders.HEAD_KEY_USER_AGENT, userAgent);


    }

    public void headers(String key, String value) {
        mHeader.put(key, value);
    }

    public void remove(Object key) {
        mHeader.remove(key);
    }

    public void removeAllHeaders() {
        mHeader.clear();
    }


    @Override
    public HttpHeader getHeaders() {
        return mHeader;
    }


    @Override
    public OutputStream getBody() {
        OutputStream body = getBodyOutputStream();
        if (isGzip()) {
            return getGzipOutStream(body);
        }
        return body;
    }

    private OutputStream getGzipOutStream(OutputStream body) {
        if (this.mZip == null) {
            this.mZip = new ZipOutputStream(body);
        }
        return mZip;
    }

    private boolean isGzip() {
        String contentEncoding = getHeaders().getContentEncoding();
        return GZIP.equals(contentEncoding);
    }

    @Override
    public HttpResponse execute() throws IOException {
        if (mZip != null) {
            mZip.close();
        }
        HttpResponse response = executeInternal(mHeader);
        executed = true;
        return response;
    }

//    protected abstract HttpResponse executeInternal(HttpHeader mHeader) throws IOException;

//    protected abstract OutputStream getBodyOutputStream();

    private ByteArrayOutputStream mByteArray = new ByteArrayOutputStream();

    protected OutputStream getBodyOutputStream() {
        return mByteArray;
    }

    protected HttpResponse executeInternal(HttpHeader header) throws IOException {
        byte[] data = mByteArray.toByteArray();
        return executeInternal(header, data);
    }

    protected abstract HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException;

}
