package com.android.jtknife.core.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * 设备信息工具类
 * AUTHOR: yangjiantong
 * DATE: 16/6/29
 */
public class DeviceUtils {

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getMobileModel() {
        String model = android.os.Build.MODEL;
        if (StringUtils.isEmpty(model)) {
            model = "android";
        }
        return model;
    }

    /**
     * 获取手机版本号
     *
     * @return
     */
    public static String getMobileVersion() {
        String version = android.os.Build.VERSION.RELEASE;
        if (StringUtils.isEmpty(version)) {
            version = "android";
        }
        return version;
    }

    /**
     * 获取设备宽度（px）
     *
     * @param context
     * @return
     */
    public static int deviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备高度（px）
     *
     * @param context
     * @return
     */
    public static int deviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dp2px(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static int px2dp(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        return getScreenSize(context, null).x;
    }

    public static int getScreenHeight(Context context) {
        return getScreenSize(context, null).y;
    }

    @TargetApi(13)
    public static Point getScreenSize(Context context, Point point) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (point == null) {
            point = new Point();
        }
        Display defaultDisplay = windowManager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 13) {
            defaultDisplay.getSize(point);
        } else {
            point.x = defaultDisplay.getWidth();
            point.y = defaultDisplay.getHeight();
        }
        return point;
    }

    public static int getDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static int getDensity(int i) {
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }

    public static long getMemoryTotal(Context context) {
        if (Build.VERSION.SDK_INT >= 16) {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(memoryInfo);
            if (memoryInfo != null) {
                return memoryInfo.totalMem;
            }
        }
        return -1;
    }
}
