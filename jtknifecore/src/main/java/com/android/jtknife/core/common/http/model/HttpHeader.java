package com.android.jtknife.core.common.http.model;

import android.text.TextUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public class HttpHeader {

    public static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_FORM = MediaType.parse("application/x-www-form-urlencoded");
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");


    public final static String ACCEPT = "Accept";
    public final static String PRAGMA = "Pragma";
    public final static String PROXY_CONNECTION = "Proxy-Connection";
    public final static String USER_AGENT = "User-Agent";
    public final static String ACCEPT_ENCODING = "Accept-Encoding";
    public final static String CACHE_CONTROL = "Cache-Control";
    public final static String CONTENT_ENCODING = "Content-Encoding";
    public final static String CONNECTION = "Connection";
    public final static String CONTENT_LENGTH = "Content-length";
    public static final String CONTENT_TYPE = "Content-Type";

    private Map<String, String> mMap = new LinkedHashMap<>();
    private static String acceptLanguage;
    private static String userAgent;

    public String getAccept() {
        return get(ACCEPT);
    }

    public void setAccept(String value) {
        set(ACCEPT, value);
    }

    public String getPragma() {
        return get(PRAGMA);
    }

    public void setPragma(String value) {
        set(PRAGMA, value);
    }

    public String getUserAgent() {
        return get(USER_AGENT);
    }

    public void setUserAgent(String value) {
        set(USER_AGENT, value);
    }

    public String getProxyConnection() {
        return get(PROXY_CONNECTION);
    }

    public void setProxyConnection(String value) {
        set(PROXY_CONNECTION, value);
    }

    public String getAcceptEncoding() {
        return get(ACCEPT_ENCODING);
    }

    public void setAcceptEncoding(String value) {
        set(ACCEPT_ENCODING, value);
    }

    public String getCacheControl() {
        return get(CACHE_CONTROL);
    }

    public void setCacheControl(String value) {
        set(CACHE_CONTROL, value);
    }

    public String getContentEncoding() {
        return get(CONTENT_ENCODING);
    }

    public void setContentEncoding(String value) {
        set(CONTENT_ENCODING, value);
    }

    public String getConnection() {
        return get(CONNECTION);
    }

    public void setConnection(String value) {
        set(CONNECTION, value);
    }

    public String getContentLength() {
        return get(CONTENT_LENGTH);
    }

    public void setContentLength(String value) {
        set(CONTENT_LENGTH, value);
    }

    public String getContentType() {
        return get(CONTENT_TYPE);
    }

    public void setContentType(String value) {
        set(CONTENT_TYPE, value);
    }

    public String get(String name) {
        return mMap.get(name);
    }

    public void set(String name, String value) {
        if (name != null && value != null) {
            mMap.put(name, value);
        }
    }

    public void setAll(Map<String, String> map) {
        mMap.putAll(map);
    }

    public int size() {
        return mMap.size();
    }

    public boolean isEmpty() {
        return mMap.isEmpty();
    }

    public boolean containsKey(Object o) {
        return mMap.containsKey(o);
    }

    public boolean containsValue(Object value) {
        return mMap.containsValue(value);
    }

    public String get(Object o) {
        return mMap.get(o);
    }

    public String put(String key, String value) {
        return mMap.put(key, value);
    }

    public String remove(Object key) {
        return mMap.remove(key);
    }

    public void putAll(Map<? extends String, ? extends String> map) {
        mMap.putAll(map);

    }

    public void clear() {
        mMap.clear();

    }

    public Set<String> keySet() {
        return mMap.keySet();
    }

    public Collection<String> values() {
        return mMap.values();
    }

    public Set<Map.Entry<String, String>> entrySet() {
        return mMap.entrySet();
    }

    public Map<String,String> getHeadersMap(){
        return mMap;
    }

    /**
     * Accept-Language: zh-CN,zh;q=0.8
     */
    public static String getAcceptLanguage() {
        if (TextUtils.isEmpty(acceptLanguage)) {
            Locale locale = Locale.getDefault();
            String language = locale.getLanguage();
            String country = locale.getCountry();
            StringBuilder acceptLanguageBuilder = new StringBuilder(language);
            if (!TextUtils.isEmpty(country)) acceptLanguageBuilder.append('-').append(country).append(',').append(language).append(";q=0.8");
            acceptLanguage = acceptLanguageBuilder.toString();
            return acceptLanguage;
        }
        return acceptLanguage;
    }


}
