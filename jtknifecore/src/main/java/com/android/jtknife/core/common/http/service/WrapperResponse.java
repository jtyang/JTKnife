package com.android.jtknife.core.common.http.service;


import com.android.jtknife.core.common.http.convert.Convert;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author nate
 */

public class WrapperResponse extends AppResponse<String> {

    private AppResponse mAppResponse;

    private List<Convert> mConvert;

    public WrapperResponse(AppResponse moocResponse, List<Convert> converts) {
        this.mAppResponse = moocResponse;
        this.mConvert = converts;
    }

    @Override
    public void success(AppRequest request, String data) {
        for (Convert convert : mConvert) {
            if (convert.isCanParse(request.getContentType())) {
                try {
                    Object object = convert.parse(data, getType());
                    mAppResponse.success(request, object);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    public Type getType() {
        Type type = mAppResponse.getClass().getGenericSuperclass();
        Type[] paramType = ((ParameterizedType) type).getActualTypeArguments();
        return paramType[0];
    }

    @Override
    public void fail(int errorCode, String errorMsg) {

    }
}
