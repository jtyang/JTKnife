package com.android.jtknife.core.common.logger;

import android.util.Log;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/7/17
 */
public class Logger {

    public static final String TAG = "JTKnife";

    public static void i(String message) {
        Log.i(TAG, "============>"+message);
    }
}
