package com.android.jtknife.core.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.ClassicFlattener;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;

import java.io.File;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/6/29
 */
public class BaseApplication extends Application {

    private static Context mContext;

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initConfig();
    }

    private void initConfig() {
        initLog();
    }

    private void initLog() {
//        XLog.init(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE);
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(LogLevel.ALL)
                .tag("JTKnife")                                     // Specify TAG, default: "X-LOG"
                .t()                                                // Enable thread info, disabled by default
                .st(2)                                              // Enable stack trace info with depth 2, disabled by default
                .b()                                                // Enable border, disabled by default
                .build();

//        Printer androidPrinter = new AndroidPrinter();                // Printer that print the log using android.util.Log
        Printer filePrinter = new FilePrinter                           // Printer that print the log to the file system
                .Builder(new File(Environment.getExternalStorageDirectory(), "JTKnife").getPath())       // Specify the path to save log file
                .fileNameGenerator(new DateFileNameGenerator())        // Default: ChangelessFileNameGenerator("log")
                // .backupStrategy(new MyBackupStrategy())             // Default: FileSizeBackupStrategy(1024 * 1024)
                .logFlattener(new ClassicFlattener())                  // Default: DefaultFlattener
                .build();

//        XLog.init(                                                     // Initialize XLog
//                config,                                                // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
//                androidPrinter,                                        // Specify printers, if no printer is specified, AndroidPrinter(for Android)/ConsolePrinter(for java) will be used.
//                filePrinter);
        XLog.init(config);
    }


}
