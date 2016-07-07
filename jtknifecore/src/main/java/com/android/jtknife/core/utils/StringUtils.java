package com.android.jtknife.core.utils;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/6/29
 */
public class StringUtils {

    public static boolean isNotEmpty(CharSequence text) {
        return text != null && text.length() > 0;
    }

    public static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0;
    }


}
