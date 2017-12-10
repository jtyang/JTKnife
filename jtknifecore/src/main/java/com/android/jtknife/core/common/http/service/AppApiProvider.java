package com.android.jtknife.core.common.http.service;


import com.android.jtknife.core.common.http.callback.StringCallback;
import com.android.jtknife.core.common.http.model.HttpMethod;

import java.util.Map;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public class AppApiProvider extends BaseApiProvider {


    public void test(String url, Map<String, String> value, StringCallback response) {
        AppRequest request = new AppRequest();
        request.setUrl(url);
        request.setMethod(HttpMethod.POST);
        request.setData(value);
//        WrapperResponse wrapperResponse = new WrapperResponse(response, sConverts);
//        request.setResponse(wrapperResponse);
        request.setResponse(response);
        mWorkStation.add(request);
    }

}
