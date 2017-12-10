package com.android.jtknife.core.common.http.model;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public interface HttpResponse extends IHeader, Closeable {

    HttpStatus getStatus();

    String getStatusMsg();

    InputStream getBody() throws IOException;

    void close();

    long getContentLength();
}
