package com.android.jtknife.core.utils;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/7/8
 */
public class FileUtils {

    /**
     * 新建文件.
     *
     * @param path 文件路径
     */
    public static boolean createFile(String path) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        boolean ret = false;
        try {
            if (path.endsWith(File.separator)) {
                //目标文件不能为目录
                return ret;
            }
            // 获得文件对象
            File f = new File(path);
            if (f.exists()) {
                deleteFile(f);
            }
            // 如果路径不存在,则创建
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            ret = f.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static boolean createDir(String destDirName) {
        if (StringUtils.isEmpty(destDirName)) {
            return false;
        }
//        if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
//            destDirName = destDirName + File.separator;
//        }
        File dir = new File(destDirName);
        if (dir.exists() && dir.isDirectory()) {// 判断目录是否存在
            deleteDir(destDirName);
        }
        return dir.mkdirs();
    }

    public static void deleteDir(String f) {
        if (f != null && f.length() > 0) {
            deleteDir(new File(f));
        }
    }

    public static boolean deleteFile(String path) {
        if (!TextUtils.isEmpty(path)) {
//            return deleteFile(new File(path));
            deleteFile(new File(path));
            return true;
        }
        return false;
    }

//    public static boolean deleteFile(File f) {
//        if (f != null && f.exists() && !f.isDirectory()) {
//            return f.delete();
//        }
//        return false;
//    }

    public static void deleteDir(File f) {
        if (f != null && f.exists() && f.isDirectory()) {
//            for (File file : f.listFiles()) {
//                if (file.isDirectory())
//                    deleteDir(file);
//                file.delete();
//            }
//            f.delete();
            deleteFile(f);
        }
    }

    public static void deleteFile(File file) {
        if (file == null || !file.exists()) return;
        if (file.isFile()) {
            deleteFileSafely(file);
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                deleteFileSafely(file);
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            deleteFileSafely(file);
        }
    }

    /**
     * 安全删除文件
     * 修复file delete时有些机器上可能bug：open failed: EBUSY (Device or resource busy)
     *
     * @param file
     * @return
     */
    private static boolean deleteFileSafely(File file) {
        if (file != null) {
            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
            File tmp = new File(tmpPath);
            file.renameTo(tmp);
            return tmp.delete();
        }
        return false;
    }

    /**
     * 清除所有文件
     *
     * @param filePathList
     */
    public static void clearFiles(List<String> filePathList) {
        for (int i = 0; i < filePathList.size(); i++) {
            File file = new File(filePathList.get(i));
            if (file.exists()) {
                file.delete();
            }
        }
    }

    // 写入指定的文本文件，append为true表示追加，false表示重头开始写，
    //text是要写入的文本字符串，text为null时直接返回
    public static boolean write(String filePath, boolean append, String text) {
        if (TextUtils.isEmpty(text))
            return false;
        try {
            File f = new File(filePath);
            if (!f.getParentFile().exists()) {
                if (!f.getParentFile().mkdirs()) {
                    return false;
                }
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath,
                    append));
            try {
                out.write(text);
            } finally {
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 读取指定路径文本文件
    public static String read(String filePath) {
        StringBuilder str = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filePath));
            String s;
            try {
                while ((s = in.readLine()) != null)
                    str.append(s + '\n');
            } finally {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    /**
     * 判断指定文件是否存在
     *
     * @param file 文件对象
     * @return 存在返回true，否则返回false
     */
    public static boolean exists(File file) {
        if (file == null) return false;
        return file.exists();
    }

    /**
     * 判断指定文件是否存在
     *
     * @param fileName 文件对象
     * @return 存在返回true，否则返回false
     */
    public static boolean exists(String fileName) {
        return !TextUtils.isEmpty(fileName) && new File(fileName).exists();
    }

    /**
     * 判断指定文件是否存在
     *
     * @param fileName 文件对象
     * @return 存在返回true，否则返回false
     */
    public static boolean isFileExists(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            File file = new File(fileName);
            if (file.exists() && file.isFile()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 拷贝文件
     *
     * @param source 源文件
     * @param dest   目标文件
     * @return
     */
    public static boolean copyFileUsingFileChannels(File source, File dest) {
        if (source == null || dest == null) return false;

        try {
            File parentFile = dest.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean ret = false;
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputChannel != null) inputChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outputChannel != null) outputChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static long getFileSize(String filePath) {
        if (TextUtils.isEmpty(filePath)) return 0L;
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                return file.length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
