package com.android.jtknife.core.common.http.v2.api;

import com.android.jtknife.core.common.http.model.HttpMethod;
import com.android.jtknife.core.common.http.service.AppRequest;
import com.android.jtknife.core.common.http.service.WorkStation;

import java.util.Map;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/16
 */
public class HttpClientPlus {

    protected static WorkStation mWorkStation = new WorkStation();

    private HttpClientPlus() {
    }

    public static HttpClientPlus getInstance() {
        return SingleHolder.INSTANCE;
    }

    private static final class SingleHolder {
        private static final HttpClientPlus INSTANCE = new HttpClientPlus();
    }

    public void doGet(String url, HttpCallback callback) {
        AppRequest request = new AppRequest();
        request.setMethod(HttpMethod.GET);
        request.setUrl(url);
        mWorkStation.add(request);
    }

    public void doPostForm(String url, Map<String, String> params, HttpCallback callback) {

    }

    public void doPostJson(String url, String jsonString, HttpCallback callback) {

    }


}
