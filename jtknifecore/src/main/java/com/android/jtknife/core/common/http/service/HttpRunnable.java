package com.android.jtknife.core.common.http.service;


import com.android.jtknife.core.common.http.model.HttpRequest;
import com.android.jtknife.core.common.http.model.HttpResponse;

import java.io.IOException;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public class HttpRunnable implements Runnable {

    private HttpRequest mHttpRequest;

    private AppRequest mRequest;

    private WorkStation mWorkStation;

    public HttpRunnable(HttpRequest httpRequest, AppRequest request, WorkStation workStation) {
        this.mHttpRequest = httpRequest;
        this.mRequest = request;
        this.mWorkStation = workStation;
    }

    @Override
    public void run() {
        try {
            byte[] data = mRequest.getData();
            if(data != null){
                mHttpRequest.getBody().write(data);
            }
            HttpResponse response = mHttpRequest.execute();
            String contentType = response.getHeaders().getContentType();
            mRequest.setContentType(contentType);
            if (response.getStatus().isSuccess()) {
                if (mRequest.getResponse() != null) {
//                    mRequest.getResponse().success(mRequest, new String(getData(response)));
                    mRequest.getResponse().onSuccess(mRequest, response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (mRequest.getResponse() != null) {
                mRequest.getResponse().onError(-1, "IOException");
            }
        } finally {
            mWorkStation.finish(mRequest);
        }

    }

}