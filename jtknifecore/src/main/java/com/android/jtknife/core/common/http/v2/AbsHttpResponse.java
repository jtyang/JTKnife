package com.android.jtknife.core.common.http.v2;

import com.android.jtknife.core.common.http.model.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/16
 */
public abstract class AbsHttpResponse implements HttpResponse{

    private InputStream mGzipInputStream;

    @Override
    public void close() {
        if (mGzipInputStream != null) {
            try {
                mGzipInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        closeInternal();

    }

    @Override
    public InputStream getBody() throws IOException {

        InputStream body = getBodyInternal();
        if (isGzip()) {
            return getBodyGzip(body);
        }

        return body;
    }

    protected abstract InputStream getBodyInternal() throws IOException;

    protected abstract void closeInternal();

    private InputStream getBodyGzip(InputStream body) throws IOException {

        if (this.mGzipInputStream == null) {
            this.mGzipInputStream = new GZIPInputStream(body);
        }
        return mGzipInputStream;

    }

    private boolean isGzip() {
        String contentEncoding = getHeaders().getContentEncoding();
        return GZIP.equals(contentEncoding);
    }
}
