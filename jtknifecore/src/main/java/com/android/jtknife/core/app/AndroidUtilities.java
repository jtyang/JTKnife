package com.android.jtknife.core.app;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/9/2
 */
public class AndroidUtilities {

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            BaseApplication.applicationHandler.post(runnable);
        } else {
            BaseApplication.applicationHandler.postDelayed(runnable, delay);
        }
    }

    public static void cancelRunOnUIThread(Runnable runnable) {
        BaseApplication.applicationHandler.removeCallbacks(runnable);
    }
}
