package com.android.jtknife.core.utils;

import android.content.Context;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/6/29
 */
public class DeviceUtils {
    /**
     * 获取手机型号
     * @return
     */
    public static String getMobileModel(){
        String model = android.os.Build.MODEL;
        if(StringUtils.isEmpty(model)){
            model = "android";
        }
        return model;
    }

    /**
     * 获取手机版本号
     * @return
     */
    public static String getMobileVersion(){
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
}
