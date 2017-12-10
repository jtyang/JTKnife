package com.android.jtknife.core.common.http.model;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public enum HttpMethod {

    GET,
    POST,
    HEAD,
    PATCH,
    TRACE,
    PUT,
    DELETE,
    OPTIONS;

    public boolean hasBody() {
        switch (this) {
            case POST:
            case PUT:
            case PATCH:
            case DELETE:
            case OPTIONS:
                return true;
            default:
                return false;
        }
    }

}
