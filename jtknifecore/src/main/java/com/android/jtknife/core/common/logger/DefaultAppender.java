//package com.android.jtknife.core.common.logger;
//
//import android.os.Environment;
//import android.text.TextUtils;
//import android.util.Log;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.io.Writer;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * log输出的默认实现类
// * AUTHOR: yangjiantong
// * DATE: 2016/11/16
// */
//public class DefaultAppender implements Logger.IPrinter {
//    private static final String TAG = "DefaultAppender";
//    public static final String DIVIDER = ":";
//    public static final int DEFAULT_BUFF_SIZE = 32768;
//
//    private final ExecutorService THREAD;
//    private int callerStackTraceIndex;
//    private volatile LogOptions sOptions;
//
//    public static class LogOptions {
//        public static final int LEVEL_VERBOSE = 1;
//        public static final int LEVEL_DEBUG = 2;
//        public static final int LEVEL_INFO = 3;
//        public static final int LEVEL_WARN = 4;
//        public static final int LEVEL_ERROR = 5;
//        public static final int LEVEL_NONE = 6;
//        public int backUpLogLimitInMB;
//        public int buffSizeInBytes;
//        public boolean honorVerbose;
//        public String logFileName;
//        public int logLevel;
//        public String stackTraceFilterKeyword;
//        public String uniformTag;
//
//        public LogOptions() {
//            this.logLevel = LEVEL_DEBUG;
//            this.honorVerbose = false;
//            this.backUpLogLimitInMB = LEVEL_WARN;
//            this.buffSizeInBytes = DEFAULT_BUFF_SIZE;
//            this.logFileName = "logs.txt";
//        }
//    }
//
//    public static class LogOutputPaths {
//        public String currentLogFile;
//        public String dir;
//        public String latestBackupFile;
//    }
//
//    public DefaultAppender() {
//        this(null);
//    }
//
//    public DefaultAppender(String logFilePath) {
//        this(logFilePath, null);
//    }
//
//    public DefaultAppender(String logFilePath, LogOptions logOptions) {
//        this.sOptions = new LogOptions();
//        this.THREAD = Executors.newSingleThreadExecutor();
//        this.callerStackTraceIndex = 7;
//        setOptions(logOptions);
//        if (!TextUtils.isEmpty(logFilePath)) {
//            LogToES.setLogPath(logFilePath);
//        }
//    }
//
//    public LogOutputPaths getLogOutputPaths() {
//        LogOutputPaths logOutputPaths = new LogOutputPaths();
//        if (!getLogOutputPaths(logOutputPaths)) {
//            Log.e(TAG, "failed to get log output paths.");
//        }
//        return logOutputPaths;
//    }
//
//    public boolean getLogOutputPaths(LogOutputPaths logOutputPaths) {
//        return LogToES.getLogOutputPaths(logOutputPaths, this.sOptions.logFileName);
//    }
//
//    public void setUniformTag(String str) {
//        if (str != null && str.length() != 0) {
//            this.sOptions.uniformTag = str;
//        }
//    }
//
//    public String getLogPath() {
//        return LogToES.getLogPath();
//    }
//
//    public LogOptions getOptions() {
//        return this.sOptions;
//    }
//
//    private boolean setOptions(LogOptions logOptions) {
//        if (logOptions == null) {
//            logOptions = new LogOptions();
//        }
//        this.sOptions = logOptions;
//        LogToES.setBackupLogLimitInMB(logOptions.backUpLogLimitInMB);
//        LogToES.setBuffSize(logOptions.buffSizeInBytes);
//        return logOptions.buffSizeInBytes > 0 && !isNullOrEmpty(logOptions.logFileName);
//    }
//
//    @Override
//    public void write(Logger.Level level, Object obj, String str) {
//        switch (level) {
//            case VERBOSE:
//                verbose(obj, str, null);
//                break;
//            case DEBUG:
//                debug(obj, str, null);
//                break;
//            case INFO:
//                info(obj, str, null);
//                break;
//            case WARN:
//                warn(obj, str, null);
//                break;
//            case ERROR:
//                error(obj, str, null);
//                break;
//            default:
//                Log.w(TAG, String.format("Unknown level to write log, level: %s, message: %s", new Object[]{level, str}));
//                break;
//        }
//    }
//
//    public void verbose(Object obj, String str, Object... objArr) {
//        boolean shouldOutputVerboseToDDMS = shouldOutputVerboseToDDMS();
//        boolean shouldOutputVerboseToFile = shouldOutputVerboseToFile();
//        if (shouldOutputVerboseToDDMS || shouldOutputVerboseToFile) {
//            try {
//                outputVerbose(obj, getCallerLineNumber(), getCallerFilename(), str, shouldOutputVerboseToDDMS, shouldOutputVerboseToFile, objArr);
//            } catch (Throwable e) {
//                Log.e(TAG, "write log error !", e);
//            }
//        }
//    }
//
//    public void debug(Object obj, String str, Object... objArr) {
//        if (shouldWriteDebug()) {
//            outputDebug(obj, str, getCallerLineNumber(), getCallerFilename(), objArr);
//        }
//    }
//
//    public void info(Object obj, String str, Object... objArr) {
//        if (shouldWriteInfo()) {
//            try {
//                outputInfo(obj, str, getCallerLineNumber(), getCallerFilename(), objArr);
//            } catch (Throwable e) {
//                Log.e(TAG, "write log error !", e);
//            }
//        }
//    }
//
//    public void warn(Object obj, String str, Object... objArr) {
//        if (shouldWriteWarn()) {
//            try {
//                outputWarning(obj, str, getCallerLineNumber(), getCallerFilename(), objArr);
//            } catch (Throwable e) {
//                Log.e(TAG, "write log error !", e);
//            }
//        }
//    }
//
//    public void error(Object obj, String str, Object... objArr) {
//        if (shouldWriteError()) {
//            try {
//                outputError(obj, str, getCallerLineNumber(), getCallerFilename(), objArr);
//            } catch (Throwable e) {
//                Log.e(TAG, "write log error !", e);
//            }
//        }
//    }
//
//    public void error(Object obj, Throwable th) {
//        if (shouldWriteError()) {
//            outputError(obj, th, getCallerLineNumber(), getCallerFilename(), getCallerMethodName());
//        }
//    }
//
//    public void close() {
//        executeCommand(new Runnable() {
//            public void run() {
//                if (externalStorageExist()) {
//                    LogToES.close();
//                }
//            }
//        });
//    }
//
//    private void executeCommand(Runnable runnable) {
//        this.THREAD.execute(runnable);
//    }
//
//    private String objClassName(Object obj) {
//        if (obj instanceof String) {
//            return (String) obj;
//        }
//        return obj.getClass().getSimpleName();
//    }
//
//    private void writeLog2File(final String str) {
//        executeCommand(new Runnable() {
//            @Override
//            public void run() {
//                long currentTimeMillis = System.currentTimeMillis();
//                if (externalStorageExist()) {
//                    try {
//                        LogToES.writeLogToFile(LogToES.getLogPath(), sOptions.logFileName, str, false, currentTimeMillis);
//                    } catch (Throwable th) {
//                        Log.e("MLog", "writeToLog fail, " + th);
//                    }
//                }
//            }
//        });
//
//    }
//
//    private void logToFile(String str, Throwable th) {
//        Writer stringWriter = new StringWriter();
//        try {
//            stringWriter.write(str);
//            stringWriter.write("\n");
//            th.printStackTrace(new PrintWriter(stringWriter));
//            writeLog2File(stringWriter.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String msgForException(Object obj, String str, String str2, int i) {
//        StringBuilder stringBuilder = new StringBuilder();
//        if (obj instanceof String) {
//            stringBuilder.append((String) obj);
//        } else {
//            stringBuilder.append(obj.getClass().getSimpleName());
//        }
//        stringBuilder.append(" Exception occurs at ");
//        stringBuilder.append("(T:");
//        stringBuilder.append(Thread.currentThread().getName());
//        stringBuilder.append(")");
//        stringBuilder.append(" at (");
//        stringBuilder.append(str2);
//        stringBuilder.append(DIVIDER);
//        stringBuilder.append(i);
//        stringBuilder.append(")");
//        return stringBuilder.toString();
//    }
//
//    private String msgForTextLog(Object obj, String str, int i, String str2) {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(str2);
//        stringBuilder.append(")");
//        stringBuilder.append("(T:");
//        stringBuilder.append(Thread.currentThread().getName());
//        stringBuilder.append(")");
//        stringBuilder.append(" at (");
//        stringBuilder.append(str);
//        stringBuilder.append(DIVIDER);
//        stringBuilder.append(i);
//        stringBuilder.append(")");
//        return stringBuilder.toString();
//    }
//
//    public void setCallerStackTraceIndex(int i) {
//        this.callerStackTraceIndex = i;
//    }
//
//    private int getCallerLineNumber() {
//        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//        int i = this.callerStackTraceIndex;
//        if (i >= stackTrace.length) {
//            i = stackTrace.length - 1;
//        }
//        return stackTrace[i].getLineNumber();
//    }
//
//    private String getCallerFilename() {
//        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//        int i = this.callerStackTraceIndex;
//        if (i >= stackTrace.length) {
//            i = stackTrace.length - 1;
//        }
//        return stackTrace[i].getFileName();
//    }
//
//    private String getCallerMethodName() {
//        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//        int i = this.callerStackTraceIndex;
//        if (i >= stackTrace.length) {
//            i = stackTrace.length - 1;
//        }
//        return stackTrace[i].getMethodName();
//    }
//
//    private String getThreadStacksKeyword() {
//        return this.sOptions.stackTraceFilterKeyword;
//    }
//
//    public void printThreadStacks() {
//        printThreadStacks(tagOfStack(), getThreadStacksKeyword(), false, false);
//    }
//
//    public void printThreadStacks(String str) {
//        printThreadStacks(str, getThreadStacksKeyword(), isNullOrEmpty(getThreadStacksKeyword()), false);
//    }
//
//    public void printThreadStacks(Throwable th, String str) {
//        printStackTraces(th.getStackTrace(), str);
//    }
//
//    public void printThreadStacks(String str, String str2) {
//        printThreadStacks(str, str2, false, false);
//    }
//
//    public void printThreadStacks(String str, String str2, boolean z, boolean z2) {
//        printStackTraces(Thread.currentThread().getStackTrace(), str, str2, z, z2);
//    }
//
//    public void printStackTraces(StackTraceElement[] stackTraceElementArr, String str) {
//        printStackTraces(stackTraceElementArr, str, getThreadStacksKeyword(), isNullOrEmpty(this.sOptions.stackTraceFilterKeyword), false);
//    }
//
//    private void printStackTraces(StackTraceElement[] stackTraceElementArr, String str, String str2, boolean z, boolean z2) {
//        printLog(str, "------------------------------------", z2);
//        for (StackTraceElement stackTraceElement : stackTraceElementArr) {
//            String stackTraceElement2 = stackTraceElement.toString();
//            if (z || (!isNullOrEmpty(str2) && stackTraceElement2.contains(str2))) {
//                printLog(str, stackTraceElement2, z2);
//            }
//        }
//        printLog(str, "------------------------------------", z2);
//    }
//
//    private void printLog(String str, String str2, boolean z) {
//        if (z) {
//            info(str, str2, new Object[0]);
//        } else {
//            debug(str, str2, new Object[0]);
//        }
//    }
//
//    private String tag(Object obj) {
//        LogOptions logOptions = this.sOptions;
//        if (logOptions.uniformTag == null) {
//            return obj instanceof String ? (String) obj : obj.getClass().getSimpleName();
//        } else {
//            return logOptions.uniformTag;
//        }
//    }
//
//    private String tagOfStack() {
//        return this.sOptions.uniformTag == null ? "CallStack" : this.sOptions.uniformTag;
//    }
//
//    private boolean shouldOutputVerboseToDDMS() {
//        return this.sOptions.logLevel <= 1;
//    }
//
//    private boolean shouldOutputVerboseToFile() {
//        return this.sOptions.logLevel <= 1 && this.sOptions.honorVerbose;
//    }
//
//    private boolean shouldWriteDebug() {
//        return this.sOptions.logLevel <= 2;
//    }
//
//    private boolean shouldWriteInfo() {
//        return this.sOptions.logLevel <= 3;
//    }
//
//    private boolean shouldWriteWarn() {
//        return this.sOptions.logLevel <= 4;
//    }
//
//    private boolean shouldWriteError() {
//        return this.sOptions.logLevel <= 5;
//    }
//
//    private boolean externalStorageExist() {
//        return Environment.getExternalStorageState().equalsIgnoreCase("mounted");
//    }
//
//    private boolean isNullOrEmpty(String str) {
//        return str == null || str.length() == 0;
//    }
//
//    private void outputVerbose(Object obj, int i, String str, String str2, boolean isPrintLog, boolean isSaveLog, Object... objArr) {
//        if (objArr != null) {
//            try {
//                if (objArr.length != 0) {
//                    str2 = String.format(str2, objArr);
//                }
//            } catch (Throwable e) {
//                Log.e(TAG, "write log error !", e);
//                return;
//            }
//        }
//        String msgForTextLog = msgForTextLog(obj, str, i, str2);
//        if (isPrintLog) {
//            Log.v(tag(obj), msgForTextLog);
//        }
//        if (isSaveLog) {
//            writeLog2File(msgForTextLog);
//        }
//    }
//
//    private void outputDebug(Object obj, String str, int i, String str2, Object... objArr) {
//        if (objArr != null) {
//            try {
//                if (objArr.length != 0) {
//                    str = String.format(str, objArr);
//                }
//            } catch (Throwable e) {
//                Log.e(TAG, "write log error !", e);
//                return;
//            }
//        }
//        String msgForTextLog = msgForTextLog(obj, str2, i, str);
//        Log.d(tag(obj), msgForTextLog);
//        writeLog2File(msgForTextLog);
//    }
//
//    private void outputInfo(Object obj, String str, int i, String str2, Object... objArr) {
//        if (objArr != null) {
//            try {
//                if (objArr.length != 0) {
//                    str = String.format(str, objArr);
//                }
//            } catch (Throwable e) {
//                Log.e(TAG, "write log error !", e);
//                return;
//            }
//        }
//        String msgForTextLog = msgForTextLog(obj, str2, i, str);
//        Log.i(tag(obj), msgForTextLog);
//        writeLog2File(msgForTextLog);
//    }
//
//    private void outputWarning(Object obj, String str, int i, String str2, Object... objArr) {
//        if (objArr != null) {
//            try {
//                if (objArr.length != 0) {
//                    str = String.format(str, objArr);
//                }
//            } catch (Throwable e) {
//                Log.e(TAG, "write log error !", e);
//                return;
//            }
//        }
//        String msgForTextLog = msgForTextLog(obj, str2, i, str);
//        Log.w(tag(obj), msgForTextLog);
//        writeLog2File(msgForTextLog);
//    }
//
//    private void outputError(Object obj, String str, int i, String str2, Object... objArr) {
//        if (objArr != null) {
//            try {
//                if (objArr.length != 0) {
//                    str = String.format(str, objArr);
//                }
//            } catch (Throwable e) {
//                Log.e(TAG, "write log error !", e);
//                return;
//            }
//        }
//        String msgForTextLog = msgForTextLog(obj, str2, i, str);
//        if (objArr == null || objArr.length <= 0 || !(objArr[objArr.length - 1] instanceof Throwable)) {
//            Log.e(tag(obj), msgForTextLog);
//            writeLog2File(msgForTextLog);
//            return;
//        }
//        Throwable e2 = (Throwable) objArr[objArr.length - 1];
//        Log.e(tag(obj), msgForTextLog, e2);
//        logToFile(msgForTextLog, e2);
//    }
//
//    private void outputError(Object obj, Throwable th, int i, String str, String str2) {
//        try {
//            logToFile(msgForException(obj, str2, str, i), th);
//        } catch (Throwable e) {
//            Log.e(TAG, "write log error !", e);
//        }
//    }
//}
