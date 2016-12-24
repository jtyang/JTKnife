package com.android.jtknife.core.common.logger;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Log打印类
 * 功能：
 * 1.解决系统默认Log打印超过4000长度会被截断bug
 * 2.增加开关控制
 * 3.扩展支持打印json和exception
 * 4.格式化log
 * 5.支持输出log到文件---文件大小控制，自动清除
 * <p>
 * <p>
 * AUTHOR: yangjiantong
 * DATE: 16/7/17
 */
public class Logger {

    private static final String TAG = "JTKnife";

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public interface IPrinter {
        void write(Level level, Object obj, String str);
    }

    public enum Level {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }


    private static String stackTraceOf(Throwable th) {
        Writer stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private static String stackTrace() {
        return TextUtils.join("\n", Thread.currentThread().getStackTrace());
    }

    private static void writeLog(Level level, Object obj, String str, Object... objArr) {
        String format = null;
        if (objArr != null && objArr.length > 0) {
            format = String.format(str, objArr);
            if (objArr.length > 0 && (objArr[objArr.length - 1] instanceof Throwable)) {
                format = getExceptionLogText(format, (Throwable) objArr[objArr.length - 1]);
            }
        }
//        if (currPrinter != null)
//            currPrinter.write(level, obj, format);
    }

    private static String getExceptionLogText(String str, Throwable th) {
        Writer stringWriter = new StringWriter();
        try {
            stringWriter.write(str);
            stringWriter.write("\n");
            th.printStackTrace(new PrintWriter(stringWriter));
            return stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
