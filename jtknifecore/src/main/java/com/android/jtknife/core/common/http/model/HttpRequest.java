package com.android.jtknife.core.common.http.model;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public interface HttpRequest extends IHeader {

    HttpMethod getMethod();

    URI getUri();

    OutputStream getBody();

    HttpResponse execute() throws IOException;

}
