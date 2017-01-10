package com.android.jtknife.core.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.android.jtknife.core.common.imageloader.ScrollPerfExecutorSupplier;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.ClassicFlattener;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.facebook.drawee.backends.pipeline.DraweeConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/6/29
 */
public class BaseApplication extends Application {

    public static final int NUMBER_OF_PROCESSORS = Runtime.getRuntime().availableProcessors();

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
        initFresco();
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

    private void initFresco() {
        ImagePipelineConfig.Builder imagePipelineConfigBuilder = ImagePipelineConfig.newBuilder(this)
                .setResizeAndRotateEnabledForNetwork(false)
                .setDownsampleEnabled(true);
        //Downsampling，要不要向下采样,它处理图片的速度比常规的裁剪scaling更快，
        // 并且同时支持PNG，JPG以及WEP格式的图片，非常强大,与ResizeOptions配合使用

        //如果不是重量级图片应用,就用这个省点内存吧.默认是RGB_888
        imagePipelineConfigBuilder.setBitmapsConfig(Bitmap.Config.RGB_565);

        imagePipelineConfigBuilder.experiment().setWebpSupportEnabled(true);

        imagePipelineConfigBuilder.setExecutorSupplier(
                new ScrollPerfExecutorSupplier(NUMBER_OF_PROCESSORS, 2));
        imagePipelineConfigBuilder.experiment().setDecodeCancellationEnabled(true);
        DraweeConfig draweeConfig = DraweeConfig.newBuilder()
                .setDrawDebugOverlay(true)
                .build();
        Fresco.initialize(this, imagePipelineConfigBuilder.build(), draweeConfig);
    }

}
