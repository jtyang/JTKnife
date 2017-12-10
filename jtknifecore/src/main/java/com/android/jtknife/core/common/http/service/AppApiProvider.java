package com.android.jtknife.core.common.http.service;


import com.android.jtknife.core.common.http.convert.Convert;
import com.android.jtknife.core.common.http.convert.JsonConvert;
import com.android.jtknife.core.common.http.model.HttpMethod;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public class AppApiProvider {

    private static final String ENCODING = "utf-8";

    private static WorkStation sWorkStation = new WorkStation();

    private static final List<Convert> sConverts = new ArrayList<>();

    static {
        sConverts.add(new JsonConvert());
    }


    public static byte[] encodeParam(Map<String, String> value) {
        if (value == null || value.size() == 0) {
            return null;
        }
        StringBuilder buffer = new StringBuilder();
        int count = 0;
        try {
            for (Map.Entry<String, String> entry : value.entrySet()) {
                buffer.append(URLEncoder.encode(entry.getKey(), ENCODING)).append("=").
                        append(URLEncoder.encode(entry.getValue(), ENCODING));
                if (count != value.size() - 1) {
                    buffer.append("&");
                }
                count++;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buffer.toString().getBytes();
    }


    public void test(String url, Map<String, String> value, AppResponse response) {
        AppRequest request = new AppRequest();
        request.setUrl(url);
        request.setMethod(HttpMethod.POST);
        request.setData(encodeParam(value));
        WrapperResponse wrapperResponse = new WrapperResponse(response, sConverts);
        request.setResponse(wrapperResponse);
        sWorkStation.add(request);
    }

}
