package com.yjt.java;

public class MyClass {

    public static void main(String[] args) {
        log("========start==========");
        long start = 0L;


//        String s1 = " aa                        cbb ";
//        start = System.currentTimeMillis();
//        log("=====" + mergeMultiWhiteSpaceToOne(s1) + "=====");
//        log("time = " + (System.currentTimeMillis() - start));

        String s2 = " aa cbb\rx\ny m \n d\t= k";
        start = System.currentTimeMillis();
        log("=====" + mergeMultiWhiteSpaceToOne(s2) + "=====");
//        log("replaceAll===" + s2.replaceAll("\\s+", " ") + "=====");
        log("time = " + (System.currentTimeMillis() - start));
//        log("replaceAll==="+s2.replaceAll(" +", " ")+"=====");

        log("========end==========");
    }

    private static void log(String s) {
        System.out.println(s);
    }

    public static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0;
    }

    public static String mergeMultiWhiteSpaceToOne(String str) {
        if (isEmpty(str)) {
            return str;
        }
        str = str.trim();
        final int sz = str.length();
        if (sz == 0) {
            return " ";
        }
        final char[] chs = new char[sz];
        int count = 0;
        boolean lastCharIsSpace = false;
        for (int i = 0; i < sz; i++) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                chs[count++] = c;
                lastCharIsSpace = false;
            } else {
                if (!lastCharIsSpace && c != '\n' && c != '\r' && c != '\t') {
                    chs[count++] = c;
                }
                lastCharIsSpace = true;
            }
        }
        if (count == sz) {
            return str;
        }
        return new String(chs, 0, count);
    }
}
