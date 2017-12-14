package com.android.jtknife.core.common.http;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 文件描述:OkHttpSamples
 * <p>
 * ref:http://blog.csdn.net/jiangguangchao/article/details/78478326
 *
 * @author yangjiantong
 * @date 2017/12/14
 */
public class OkHttpSamples {

    private static final String TAG = "OkHttpSamples";

    public void syncRequest() {
        String url = "http://www.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            //TODO 解析response
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void asyncRequest() {
        String url = "http://www.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //我在子线程中
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //我在子线程中
            }
        });
    }

    public void getAsync() {
        String url = "http://www.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //我在子线程中
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //我在子线程中
            }
        });
    }

    /**
     * 下载文件使用普通的GET请求即可，只需在返回结果中根据获取的流保存成文件即可
     */
    public void downloadFile(){
        String url = "http://www.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //我在子线程中
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //TODO 解析流并保存成文件
            }
        });
    }

    /**
     * POST
     * 使用OKHttp发起POST的请求和发起GET请求的流程是一样的，只不过在创建Request对象时需要传入一个RequestBody对象，RequestBody对象保存需要提交给客户端的表单数据。

       RequestBody需要指定Content-Type，常见的数据格式有三种：
       application/x-www-form-urlencoded  数据是个普通表单
       multipart/form-data  数据里有文件
       application/json  数据是个json串
     */

    //FormBody继承自RequestBody并指定了数据类型为application/x-www-form-urlencoded
    public void postForm(){
        String url = "http://192.168.1.105/user/login";
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("login_username", "jgc")
                .add("login_password", "123456").build();
        Request request = new Request.Builder().url(url).post(body).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "success");
            }
        });
    }

    /**
     * 创建RequestBody时指定数据类型为application/json即可
     */
    public void postJson(){
        String url = "http://192.168.1.105/user/login";
        OkHttpClient okHttpClient = new OkHttpClient();

        String jsonString = "{\"code\":0,\"data\":{}}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Request request = new Request.Builder().url(url).post(body).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "success");
            }
        });
    }

    /**
     * 使用MultipartBody.Builder来创建RequestBody对象，需要指定数据类型并且设置表单数据部分
     */
    public void uploadFile(File file){
        String url = "http://192.168.1.105/user/upload";
        OkHttpClient okHttpClient = new OkHttpClient();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM); //设置类型
        builder.addFormDataPart("UploadForm", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        RequestBody body = builder.build();

        Request request = new Request.Builder().url(url).post(body).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "success");
            }
        });
    }


}
