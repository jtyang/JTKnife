package com.android.jtknife.core.common.http.v2;

import com.android.jtknife.core.common.http.model.HttpHeader;
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
 * @date 2017/12/16
 */
public abstract class AbsHttpRequest implements HttpRequest{

    protected HttpHeader mHeader = new HttpHeader();
    private ByteArrayOutputStream mBodyByteStream = new ByteArrayOutputStream();

    private ZipOutputStream mZip;
    private boolean executed;

    protected ByteArrayOutputStream getBodyOutputStream() {
        return mBodyByteStream;
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

    @Override
    public HttpResponse execute() throws IOException {
        if (mZip != null) {
            mZip.close();
        }
        HttpResponse response = executeInternal();
        executed = true;
        return response;
    }

    protected abstract HttpResponse executeInternal() throws IOException;

    protected abstract AbsHttpRequest makeGetRequest() throws IOException;

    protected abstract AbsHttpRequest makePostFormRequest(byte[] data) throws IOException;

    protected abstract AbsHttpRequest makePostJsonRequest(byte[] data) throws IOException;

}
