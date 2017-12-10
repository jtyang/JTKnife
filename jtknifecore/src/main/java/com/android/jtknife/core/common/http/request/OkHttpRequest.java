package com.android.jtknife.core.common.http.request;

import com.android.jtknife.core.common.http.model.HttpHeader;
import com.android.jtknife.core.common.http.model.HttpMethod;
import com.android.jtknife.core.common.http.model.HttpParams;
import com.android.jtknife.core.common.http.model.HttpResponse;
import com.android.jtknife.core.common.http.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 文件描述:
 * get post-form post-json  upload-file download-file
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public class OkHttpRequest extends AbstractHttpRequest {

    private OkHttpClient mClient;

    private HttpMethod mMethod;

    private String mUrl;
    private Object mTag;

    protected MediaType mediaType;        //上传的MIME类型
    protected String content;             //上传的文本内容
    protected byte[] bs;                  //上传的字节数据
    protected File file;                  //单纯的上传一个文件

    protected boolean isMultipart = false;  //是否强制使用 multipart/form-data 表单上传
    protected boolean isSpliceUrl = false;  //是否拼接url参数
    protected okhttp3.Request mRequest;

    public OkHttpRequest(OkHttpClient client, HttpMethod method, String url) {
        this.mClient = client;
        this.mMethod = method;
        this.mUrl = url;
    }

    @Override
    protected HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException {
        boolean isBody = mMethod == HttpMethod.POST;
        RequestBody requestBody = null;
        if (isBody) {
            requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), data);
        }
        Request.Builder builder = new Request.Builder().url(mUrl).method(mMethod.name(), requestBody);

        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        Response response = mClient.newCall(builder.build()).execute();
//        Response response = getRawCall(header, data).execute();
        return new OkHttpResponse(response);
    }


    /**
     * 获取okhttp的同步call对象
     */
    public okhttp3.Call getRawCall(HttpHeader header, byte[] data) {
        //构建请求体，返回call对象
        RequestBody requestBody = generateRequestBody();
        if (requestBody != null) {
//            ProgressRequestBody<T> progressRequestBody = new ProgressRequestBody<>(requestBody, callback);
//            progressRequestBody.setInterceptor(uploadInterceptor);
//            mRequest = generateRequest(progressRequestBody);
            mRequest = generateRequest(header, requestBody);
        } else {
            mRequest = generateRequest(header, null);
        }
        return mClient.newCall(mRequest);
    }

    public okhttp3.Request generateRequest(HttpHeader header, RequestBody requestBody) {
        Request.Builder requestBuilder = generateRequestBuilder(header, requestBody);
        return requestBuilder.method(getMethod().name(), requestBody).url(mUrl).tag(mTag).build();
    }

    protected okhttp3.Request.Builder generateRequestBuilder(HttpHeader headers, RequestBody requestBody) {
        try {
            headers.setContentLength(String.valueOf(requestBody.contentLength()));
//            headers(HttpHeader.CONTENT_LENGTH, String.valueOf(requestBody.contentLength()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
        return HttpUtils.appendHeaders(requestBuilder, headers);
    }

    public RequestBody generateRequestBody() {
        if (getMethod().hasBody()) {
//            if (isSpliceUrl) url = HttpUtils.createUrlFromParams(baseUrl, params.urlParamsMap);
//            if (requestBody != null) return requestBody;        //自定义的请求体
            if (content != null && mediaType != null)
                return RequestBody.create(mediaType, content);    //上传字符串数据
            if (bs != null && mediaType != null)
                return RequestBody.create(mediaType, bs);         //上传字节数组
            if (file != null && mediaType != null)
                return RequestBody.create(mediaType, file);       //上传一个文件
            return HttpUtils.generateMultipartRequestBody(mParams, isMultipart);
        }
        return null;
    }

    @Override
    public HttpMethod getMethod() {
        return mMethod;
    }

    @Override
    public URI getUri() {
        return URI.create(mUrl);
    }


    //===================

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    @SuppressWarnings("unchecked")
    public OkHttpRequest upString(String string) {
        this.content = string;
        this.mediaType = HttpParams.MEDIA_TYPE_PLAIN;
        return this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     * 该方法用于定制请求content-type
     */
    @SuppressWarnings("unchecked")
    public OkHttpRequest upString(String string, MediaType mediaType) {
        this.content = string;
        this.mediaType = mediaType;
        return this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    @SuppressWarnings("unchecked")
    public OkHttpRequest upJson(String json) {
        this.content = json;
        this.mediaType = HttpParams.MEDIA_TYPE_JSON;
        return this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    @SuppressWarnings("unchecked")
    public OkHttpRequest upJson(JSONObject jsonObject) {
        this.content = jsonObject.toString();
        this.mediaType = HttpParams.MEDIA_TYPE_JSON;
        return this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    @SuppressWarnings("unchecked")
    public OkHttpRequest upJson(JSONArray jsonArray) {
        this.content = jsonArray.toString();
        this.mediaType = HttpParams.MEDIA_TYPE_JSON;
        return this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    @SuppressWarnings("unchecked")
    public OkHttpRequest upBytes(byte[] bs) {
        this.bs = bs;
        this.mediaType = HttpParams.MEDIA_TYPE_STREAM;
        return this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    @SuppressWarnings("unchecked")
    public OkHttpRequest upBytes(byte[] bs, MediaType mediaType) {
        this.bs = bs;
        this.mediaType = mediaType;
        return this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    @SuppressWarnings("unchecked")
    public OkHttpRequest upFile(File file) {
        this.file = file;
        this.mediaType = HttpUtils.guessMimeType(file.getName());
        return this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    @SuppressWarnings("unchecked")
    public OkHttpRequest upFile(File file, MediaType mediaType) {
        this.file = file;
        this.mediaType = mediaType;
        return this;
    }

}
