package com.android.jtknife.core.common.http.v2.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/16
 */
public class AppApiProviderTest {

    public void testHelloWorld() {
        HttpClientPlus.getInstance().doGet("http://www.baidu.com", new HttpCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }
        });
    }

    public void testPostForm() {
        Map<String, String> params = new HashMap<>();
        HttpClientPlus.getInstance().doPostForm("http://", params, new HttpCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }
        });
    }

}
