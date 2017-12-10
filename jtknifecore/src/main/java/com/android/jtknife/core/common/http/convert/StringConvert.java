package com.android.jtknife.core.common.http.convert;

import com.android.jtknife.core.common.http.model.HttpResponse;

import java.io.InputStream;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public class StringConvert implements Converter<String> {

    @Override
    public String convertResponse(HttpResponse response) throws Throwable {
        InputStream body = response.getBody();
        if(body != null){

        }
        return null;
    }
}
