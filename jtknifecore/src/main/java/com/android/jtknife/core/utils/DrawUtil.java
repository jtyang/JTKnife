package com.android.jtknife.core.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/1/11
 */
public class DrawUtil {
    private static float x = 0.0f;
    private static int y;
    private static int z;

    public static int z(Context context, float f) {
        if (Math.abs(x) < 1.0E-5f) {
            x = context.getResources().getDisplayMetrics().density;
        }
        return (int) ((x * f) + 0.5f);
    }

    public static int z(Context context) {
        if (z == 0 || y == 0) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            z = displayMetrics.widthPixels;
            y = displayMetrics.heightPixels;
        }
        return z;
    }

    public static int y(Context context) {
        if (z == 0 || y == 0) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            z = displayMetrics.widthPixels;
            y = displayMetrics.heightPixels;
        }
        return y;
    }
}
