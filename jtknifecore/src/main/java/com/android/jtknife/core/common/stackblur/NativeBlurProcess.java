package com.android.jtknife.core.common.stackblur;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.elvishew.xlog.XLog;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/1/13
 */
public class NativeBlurProcess implements BlurProcess {

    private static AtomicBoolean isLoadLibraryOk;

    static {
        isLoadLibraryOk = new AtomicBoolean(false);
        try {
            System.loadLibrary("stackblur");
            isLoadLibraryOk.set(true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static native void functionToBlur(Bitmap bitmap, int radius, int threadCount, int threadIndex, int round);

    @Override
    public Bitmap blur(Bitmap bitmap, float radius) {
        if (!isLoadLibraryOk.get() || bitmap == null) {
            String str;
            String str2 = "NativeBlurProcess";
            if (bitmap == null) {
                str = "original null!";
            } else {
                str = "isLoadLibraryOk false!";
            }
            XLog.e(str2 + str);
            return bitmap;
        }
        long currentTimeMillis = System.currentTimeMillis();
        bitmap = bitmap.copy(Config.ARGB_8888, true);
        functionToBlur(bitmap, (int) radius, 1, 0, 1);
        functionToBlur(bitmap, (int) radius, 1, 0, 2);
        XLog.e("blur radius:" + radius + " end, cast time  = " + (System.currentTimeMillis() - currentTimeMillis));
        return bitmap;
    }
}
