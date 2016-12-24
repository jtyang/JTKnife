//package com.android.jtknife.core.common.logger;
//
//import android.os.SystemClock;
//import android.util.Log;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Locale;
//
///**
// * log写入到sd卡文件操作类
// * AUTHOR: yangjiantong
// * DATE: 2016/11/16
// */
//public class LogToES {
//    private static final String BAK_EXT = ".bak";
//    private static int BUFF_SIZE = 0;
//    private static final long DAY_DELAY = 864000000;
//    private static final int DEFAULT_BAK_FILE_NUM_LIMIT = 2;
//    private static final int DEFAULT_BUFF_SIZE = 32768;
//    private static final long FLUSH_INTERVAL = 5000;
//    private static final SimpleDateFormat LOG_FORMAT;
//    private static final int MAX_FILE_SIZE = 3;
//    private static int mBackFileNumLimit;
//    private static long mLastMillis;
//    private static Object mLock;
//    private static volatile String mLogPath;
//    private static String mPath;
//    private static BufferedWriter mWriter;
//    private static SimpleDateFormat simpleDateFormat;
//
//    static {
//        LOG_FORMAT = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS", Locale.getDefault());
//        simpleDateFormat = new SimpleDateFormat("-MM-dd-kk-mm-ss.SSS", Locale.getDefault());
//        mBackFileNumLimit = DEFAULT_BAK_FILE_NUM_LIMIT;
//        BUFF_SIZE = DEFAULT_BUFF_SIZE;
//        mLock = new Object();
//        mLastMillis = 0;
//    }
//
//    public static void setBackupLogLimitInMB(int i) {
//        mBackFileNumLimit = ((i + MAX_FILE_SIZE) - 1) / MAX_FILE_SIZE;
//    }
//
//    public static boolean setLogPath(String str) {
//        if (str == null || str.length() == 0) {
//            return false;
//        }
//        mLogPath = str;
//        new File(str).mkdirs();
//        return new File(str).isDirectory();
//    }
//
//    public static String getLogPath() {
//        return mLogPath;
//    }
//
//    public static void setBuffSize(int i) {
//        BUFF_SIZE = i;
//    }
//
//    public static void writeLogToFile(String str, String str2, String str3, boolean z, long j) throws IOException {
//        writeLog(str, str2, str3, z, j);
//    }
//
//    public static void writeLog(String str, String str2, String str3, boolean z, long j) throws IOException {
//        Object obj = 1;
//        if (str != null && str.length() != 0 && str2 != null && str2.length() != 0) {
//            File file = new File(str);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            Object obj2 = null;
//            file = createFile(str, str2);
//            if (!file.exists()) {
//                try {
//                    file.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return;
//                }
//            } else if ((file.length() >>> 20) > 3) {
//                deleteOldLogs();
//                String format = simpleDateFormat.format(j);
//                StringBuilder stringBuilder = new StringBuilder(str);
//                stringBuilder.append(File.separator).append(str2).append(format).append(BAK_EXT);
//                close();
//                file.renameTo(new File(stringBuilder.toString()));
//                file = createFile(str, str2);
//                int i = 1;
//                limitVolume();
//            }
//            StringBuffer stringBuffer = new StringBuffer(LOG_FORMAT.format(j));
//            stringBuffer.append(' ');
//            stringBuffer.append(str3);
//            stringBuffer.append('\n');
//            String stringBuffer2 = stringBuffer.toString();
//            synchronized (mLock) {
//                BufferedWriter bufferedWriter;
//                BufferedWriter bufferedWriter2;
//                if (mPath == null) {
//                    mPath = file.getAbsolutePath();
//                } else if (equal(mPath, file.getAbsolutePath())) {
//                    obj = obj2;
//                } else {
//                    bufferedWriter = mWriter;
//                    if (bufferedWriter != null) {
//                        bufferedWriter.close();
//                    }
//                    mWriter = null;
//                    mPath = null;
//                }
//                bufferedWriter = mWriter;
//                if (obj != null || bufferedWriter == null) {
//                    mPath = file.getAbsolutePath();
//                    bufferedWriter2 = new BufferedWriter(new FileWriter(file, true), BUFF_SIZE);
//                    mWriter = bufferedWriter2;
//                } else {
//                    bufferedWriter2 = bufferedWriter;
//                }
//                bufferedWriter2.write(stringBuffer2);
//                long elapsedRealtime = SystemClock.elapsedRealtime();
//                if (elapsedRealtime - mLastMillis >= FLUSH_INTERVAL) {
//                    bufferedWriter2.flush();
//                    mLastMillis = elapsedRealtime;
//                }
//                if (z) {
//                    bufferedWriter2.close();
//                    mPath = null;
//                    mWriter = null;
//                }
//            }
//        }
//    }
//
//    private static File createFile(String str, String str2) {
//        return new File(str.endsWith(File.separator) ? str + str2 : str + File.separator + str2);
//    }
//
//    private static boolean equal(String str, String str2) {
//        if (str == null || str2 == null) {
//            return str == null && str2 == null;
//        } else {
//            return str.equals(str2);
//        }
//    }
//
//    private static void deleteOldLogs() {
//        File file = new File(getLogPath());
//        if (file.exists()) {
//            long currentTimeMillis = System.currentTimeMillis();
//            File[] listFiles = file.listFiles();
//            if (listFiles != null) {
//                for (File file2 : listFiles) {
//                    if (isBakFile(file2.getName()) && currentTimeMillis - file2.lastModified() > DAY_DELAY) {
//                        file2.delete();
//                    }
//                }
//            }
//        }
//    }
//
//    public static boolean getLogOutputPaths(DefaultAppender.LogOutputPaths logOutputPaths, String str) {
//        int i = 0;
//        String logPath = getLogPath();
//        if (logPath == null || str == null) {
//            return false;
//        }
//        String str2;
//        logOutputPaths.dir = logPath;
//        synchronized (mLock) {
//            str2 = mPath;
//        }
//        if (str2 == null) {
//            str2 = createFile(logPath, str).getAbsolutePath();
//        }
//        logOutputPaths.currentLogFile = str2;
//        File[] listFiles = new File(logPath).listFiles();
//        if (listFiles != null) {
//            long j = 0;
//            str2 = null;
//            int length = listFiles.length;
//            while (i < length) {
//                File file = listFiles[i];
//                if (isBakFile(file.getAbsolutePath()) && file.lastModified() > 0) {
//                    j = file.lastModified();
//                    str2 = file.getAbsolutePath();
//                }
//                i++;
//            }
//            logOutputPaths.latestBackupFile = str2;
//        }
//        return true;
//    }
//
//    private static boolean isBakFile(String str) {
//        return str.endsWith(BAK_EXT);
//    }
//
//    private static void limitVolume() {
//        File file = new File(getLogPath());
//        if (file.exists()) {
//            File[] listFiles = file.listFiles();
//            if (listFiles != null && listFiles.length > Math.max(0, mBackFileNumLimit)) {
//                int i;
//                int i2;
//                int i3 = 0;
//                for (File name : listFiles) {
//                    if (isBakFile(name.getName())) {
//                        i3++;
//                    }
//                }
//                if (i3 > 0) {
//                    File[] fileArr = new File[i3];
//                    int length = listFiles.length;
//                    i2 = 0;
//                    i = 0;
//                    while (i2 < length) {
//                        File file2 = listFiles[i2];
//                        if (i >= i3) {
//                            break;
//                        }
//                        int i4;
//                        if (isBakFile(file2.getName())) {
//                            i4 = i + 1;
//                            fileArr[i] = file2;
//                        } else {
//                            i4 = i;
//                        }
//                        i2++;
//                        i = i4;
//                    }
//                    deleteIfOutOfBound(fileArr);
//                }
//            }
//        }
//    }
//
//    private static void deleteIfOutOfBound(File[] fileArr) {
//        if (fileArr.length > mBackFileNumLimit) {
//            Arrays.sort(fileArr);
//            int length = fileArr.length;
//            for (int i = mBackFileNumLimit; i < length; i++) {
//                File file = fileArr[i];
//                if (!file.delete()) {
//                    Log.e("LogToES", "LogToES failed to delete file " + file);
//                }
//            }
//        }
//    }
//
//    public static void flush() {
//        synchronized (mLock) {
//            BufferedWriter bufferedWriter = mWriter;
//            if (bufferedWriter != null) {
//                try {
//                    bufferedWriter.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public static void close() {
//        synchronized (mLock) {
//            BufferedWriter bufferedWriter = mWriter;
//            if (bufferedWriter != null) {
//                try {
//                    bufferedWriter.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            mPath = null;
//            mWriter = null;
//        }
//    }
//
//    public static boolean isOpen() {
//        boolean z;
//        synchronized (mLock) {
//            z = mWriter != null;
//        }
//        return z;
//    }
//}
