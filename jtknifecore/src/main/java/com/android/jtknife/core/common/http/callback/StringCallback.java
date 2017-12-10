package com.android.jtknife.core.common.http.callback;

import com.android.jtknife.core.common.http.model.HttpResponse;
import com.android.jtknife.core.common.http.service.AppRequest;
import com.android.jtknife.core.common.http.service.AppResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public abstract class StringCallback extends AppResponse {

    @Override
    public void onSuccess(AppRequest request, HttpResponse response) {
        onSuccess(new String(getData(response)));
    }

    private byte[] getData(HttpResponse response) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream((int) response.getContentLength());
        int len;
        byte[] data = new byte[512];
        try {
            while ((len = response.getBody().read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public abstract void onSuccess(String result);
}
