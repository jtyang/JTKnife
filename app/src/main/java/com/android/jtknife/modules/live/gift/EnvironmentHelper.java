package com.android.jtknife.modules.live.gift;

import android.os.Environment;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/1
 */
public class EnvironmentHelper {
    public static final String a = (Environment.getDataDirectory().getPath() + "/data/" + "com.kitty.android");
    private static final String t = ("mounted".equals(Environment.getExternalStorageState()) ? Environment.getExternalStorageDirectory().getPath() : a);
    public static final String b = (a + "/databases/");
    public static final String c = (a + "/shared_prefs/");
    public static final String d = (a + "/image/");
    public static final String e = (t + "/KittyLive");
    public static final String f = (e + "/cache/");
    public static final String g = (e + "/error/");
    public static final String h = (e + "/log/");
    public static final String i = (h + "1.8.9");
    public static final String j = (e + "/effect/");
    public static final String k = (e + "/sticker/");
    public static final String l = (e + "/image/");
    public static final String m = (l + "screenshot/");
    public static final String n = (e + "/gift/image/");
    public static final String o = (e + "/gift/icon/");
    public static final String p = (e + "/gift/mini_icon/");
    public static final String q = (e + "/Kitty_Live/");
    public static final String r = (t + "/LiveLog");
    public static final String s = (a + "/resource/");

}
