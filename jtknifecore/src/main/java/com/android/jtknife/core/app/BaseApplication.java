package com.android.jtknife.core.app;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;

import com.android.jtknife.core.common.imageloader.config.ImagePipelineConfigFactory;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.ClassicFlattener;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.drawee.backends.pipeline.DraweeConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/6/29
 */
public class BaseApplication extends Application {

    public static final int NUMBER_OF_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static volatile Context mContext;

    public static Context getAppContext() {
        return mContext;
    }

    public static volatile Handler applicationHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        applicationHandler = new Handler(mContext.getMainLooper());
        initConfig();
    }


    private void initConfig() {
        initLog();
        initHttp();
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

    private void initHttp() {

    }

    private MyMemoryTrimmableRegistry mMemoryTrimmableRegistry = new MyMemoryTrimmableRegistry();

    private void initFresco() {
        FLog.setMinimumLoggingLevel(FLog.DEBUG);
//        ImagePipelineConfig.Builder imagePipelineConfigBuilder = ImagePipelineConfig.newBuilder(this)
//                .setResizeAndRotateEnabledForNetwork(false)
//                .setDownsampleEnabled(true);//Downsample：向下采样,它处理图片的速度比常规的裁剪scaling更快,且同时支持PNG，JPG以及WEP格式的图片，非常强大,与ResizeOptions配合使用
//        imagePipelineConfigBuilder.setBitmapsConfig(Bitmap.Config.RGB_565);//如果不是重量级图片应用,就用这个省点内存吧.默认是RGB_888
//        imagePipelineConfigBuilder.experiment().setWebpSupportEnabled(true);
//        imagePipelineConfigBuilder.setExecutorSupplier(
//                new ScrollPerfExecutorSupplier(NUMBER_OF_PROCESSORS, 2));
//        imagePipelineConfigBuilder.experiment().setDecodeCancellationEnabled(true);
//        imagePipelineConfigBuilder.setMemoryTrimmableRegistry(mMemoryTrimmableRegistry);//内存策略
//        DraweeConfig draweeConfig = DraweeConfig.newBuilder()
//                .setDrawDebugOverlay(false)
//                .build();
//        Fresco.initialize(this, imagePipelineConfigBuilder.build(), draweeConfig);
        Fresco.initialize(mContext, ImagePipelineConfigFactory.getImagePipelineConfig(mContext, mMemoryTrimmableRegistry));
        XLog.i("init fresco success");
    }

    private void initFresco_V2() {
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
        Set<RequestListener> listeners = new HashSet<>();
        listeners.add(new RequestLoggingListener());
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setRequestListeners(listeners)
                .build();
        DraweeConfig draweeConfig = DraweeConfig.newBuilder()
                .setDrawDebugOverlay(false)
                .build();
        Fresco.initialize(mContext, config, draweeConfig);
        XLog.i("init fresco2 success");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (Build.VERSION.SDK_INT >= 16) {
            mMemoryTrimmableRegistry.trim(level);
        }
    }

    private class MyMemoryTrimmableRegistry implements MemoryTrimmableRegistry {

        ArrayList<MemoryTrimmable> memoryTrimmables = new ArrayList<>();

        @Override
        public void registerMemoryTrimmable(MemoryTrimmable trimmable) {
            memoryTrimmables.add(trimmable);
        }

        @Override
        public void unregisterMemoryTrimmable(MemoryTrimmable trimmable) {
            memoryTrimmables.remove(trimmable);
        }

        public void trim(int level) {
            MemoryTrimType memoryTrimType;
            if (level >= 40) {
                memoryTrimType = MemoryTrimType.OnAppBackgrounded;
            } else if (level >= 10) {
                memoryTrimType = MemoryTrimType.OnSystemLowMemoryWhileAppInForeground;
            } else {
                memoryTrimType = null;
            }
            if (memoryTrimType != null) {
                Iterator it = this.memoryTrimmables.iterator();
                while (it.hasNext()) {
                    ((MemoryTrimmable) it.next()).trim(memoryTrimType);
                }
            }
        }
    }
}
