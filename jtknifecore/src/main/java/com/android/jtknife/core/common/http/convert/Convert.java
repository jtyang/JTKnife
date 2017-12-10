package com.android.jtknife.core.common.http.convert;


import com.android.jtknife.core.common.http.model.HttpResponse;

import java.io.IOException;
import java.lang.reflect.Type;

public interface Convert {

    Object parse(HttpResponse response, Type type) throws IOException;

    boolean isCanParse(String contentType);

    Object parse(String content, Type type) throws IOException;
}
