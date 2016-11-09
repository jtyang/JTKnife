package com.android.jtknife.core.utils;

/**
 * StringUtils:source from apache
 * AUTHOR: yangjiantong
 * DATE: 16/6/29
 */
public class StringUtils {

    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        if (str != null) {
            int length = str.length();
            if (length != 0) {
                for (int i = 0; i < length; i++) {
                    if (!Character.isWhitespace(str.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String clean(String str) {
        return str == null ? EMPTY : str.trim();
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String trimToNull(String str) {
        String trim = trim(str);
        return isEmpty(trim) ? null : trim;
    }

    public static String trimToEmpty(String str) {
        return str == null ? EMPTY : str.trim();
    }

    public static String strip(String str) {
        return strip(str, null);
    }

    public static String stripToNull(String str) {
        if (str == null) {
            return null;
        }
        String strip = strip(str, null);
        if (strip.length() != 0) {
            return strip;
        }
        return null;
    }

    public static String stripToEmpty(String str) {
        return str == null ? EMPTY : strip(str, null);
    }

    public static String strip(String str, String str2) {
        return isEmpty(str) ? str : stripEnd(stripStart(str, str2), str2);
    }

    public static String stripStart(String str, String str2) {
        if (str == null) {
            return str;
        }
        int length = str.length();
        if (length == 0) {
            return str;
        }
        int i = 0;
        if (str2 == null) {
            while (i != length && Character.isWhitespace(str.charAt(i))) {
                i++;
            }
        } else if (str2.length() == 0) {
            return str;
        } else {
            while (i != length && str2.indexOf(str.charAt(i)) != INDEX_NOT_FOUND) {
                i++;
            }
        }
        return str.substring(i);
    }

    public static String stripEnd(String str, String str2) {
        if (str == null) {
            return str;
        }
        int length = str.length();
        if (length == 0) {
            return str;
        }
        if (str2 == null) {
            while (length != 0 && Character.isWhitespace(str.charAt(length + INDEX_NOT_FOUND))) {
                length += INDEX_NOT_FOUND;
            }
        } else if (str2.length() == 0) {
            return str;
        } else {
            while (length != 0 && str2.indexOf(str.charAt(length + INDEX_NOT_FOUND)) != INDEX_NOT_FOUND) {
                length += INDEX_NOT_FOUND;
            }
        }
        return str.substring(0, length);
    }

    public static String[] stripAll(String[] strArr) {
        return stripAll(strArr, null);
    }

    public static String[] stripAll(String[] strArr, String str) {
        if (strArr == null) {
            return strArr;
        }
        int length = strArr.length;
        if (length == 0) {
            return strArr;
        }
        String[] strArr2 = new String[length];
        for (int i = 0; i < length; i++) {
            strArr2[i] = strip(strArr[i], str);
        }
        return strArr2;
    }

    public static boolean equals(String str, String str2) {
        if (str == null) {
            return str2 == null;
        } else {
            return str.equals(str2);
        }
    }

    public static boolean equalsIgnoreCase(String str, String str2) {
        if (str == null) {
            return str2 == null;
        } else {
            return str.equalsIgnoreCase(str2);
        }
    }

    public static int indexOf(String str, char c) {
        if (isEmpty(str)) {
            return INDEX_NOT_FOUND;
        }
        return str.indexOf(c);
    }

    public static int indexOf(String str, char c, int i) {
        if (isEmpty(str)) {
            return INDEX_NOT_FOUND;
        }
        return str.indexOf(c, i);
    }

    public static int indexOf(String str, String str2) {
        if (str == null || str2 == null) {
            return INDEX_NOT_FOUND;
        }
        return str.indexOf(str2);
    }

    public static int ordinalIndexOf(String str, String str2, int i) {
        int i2 = 0;
        int i3 = INDEX_NOT_FOUND;
        if (str == null || str2 == null || i <= 0) {
            return INDEX_NOT_FOUND;
        }
        if (str2.length() == 0) {
            return 0;
        }
        do {
            i3 = str.indexOf(str2, i3 + 1);
            if (i3 < 0) {
                return i3;
            }
            i2++;
        } while (i2 < i);
        return i3;
    }

    public static int indexOf(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return INDEX_NOT_FOUND;
        }
        if (str2.length() != 0 || i < str.length()) {
            return str.indexOf(str2, i);
        }
        return str.length();
    }

    public static int lastIndexOf(String str, char c) {
        if (isEmpty(str)) {
            return INDEX_NOT_FOUND;
        }
        return str.lastIndexOf(c);
    }

    public static int lastIndexOf(String str, char c, int i) {
        if (isEmpty(str)) {
            return INDEX_NOT_FOUND;
        }
        return str.lastIndexOf(c, i);
    }

    public static int lastIndexOf(String str, String str2) {
        if (str == null || str2 == null) {
            return INDEX_NOT_FOUND;
        }
        return str.lastIndexOf(str2);
    }

    public static int lastIndexOf(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return INDEX_NOT_FOUND;
        }
        return str.lastIndexOf(str2, i);
    }

    public static boolean contains(String str, char c) {
        if (!isEmpty(str) && str.indexOf(c) >= 0) {
            return true;
        }
        return false;
    }

    public static boolean contains(String str, String str2) {
        if (str == null || str2 == null || str.indexOf(str2) < 0) {
            return false;
        }
        return true;
    }

    public static boolean containsIgnoreCase(String str, String str2) {
        if (str == null || str2 == null) {
            return false;
        }
        return contains(str.toUpperCase(), str2.toUpperCase());
    }

    public static int indexOfAnyBut(String str, String str2) {
        if (isEmpty(str) || isEmpty(str2)) {
            return INDEX_NOT_FOUND;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str2.indexOf(str.charAt(i)) < 0) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean containsNone(String str, char[] cArr) {
        if (str == null || cArr == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            for (char c : cArr) {
                if (c == charAt) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean containsNone(String str, String str2) {
        if (str == null || str2 == null) {
            return true;
        }
        return containsNone(str, str2.toCharArray());
    }

    public static String substringBefore(String str, String str2) {
        if (isEmpty(str) || str2 == null) {
            return str;
        }
        if (str2.length() == 0) {
            return EMPTY;
        }
        int indexOf = str.indexOf(str2);
        return indexOf != INDEX_NOT_FOUND ? str.substring(0, indexOf) : str;
    }

    public static String substringAfter(String str, String str2) {
        if (isEmpty(str)) {
            return str;
        }
        if (str2 == null) {
            return EMPTY;
        }
        int indexOf = str.indexOf(str2);
        if (indexOf == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str.substring(indexOf + str2.length());
    }

    public static String substringBeforeLast(String str, String str2) {
        if (isEmpty(str) || isEmpty(str2)) {
            return str;
        }
        int lastIndexOf = str.lastIndexOf(str2);
        return lastIndexOf != INDEX_NOT_FOUND ? str.substring(0, lastIndexOf) : str;
    }

    public static String substringAfterLast(String str, String str2) {
        if (isEmpty(str)) {
            return str;
        }
        if (isEmpty(str2)) {
            return EMPTY;
        }
        int lastIndexOf = str.lastIndexOf(str2);
        if (lastIndexOf == INDEX_NOT_FOUND || lastIndexOf == str.length() - str2.length()) {
            return EMPTY;
        }
        return str.substring(lastIndexOf + str2.length());
    }

    public static String substringBetween(String str, String str2) {
        return substringBetween(str, str2, str2);
    }

    public static String substringBetween(String str, String str2, String str3) {
        if (str == null || str2 == null || str3 == null) {
            return null;
        }
        int indexOf = str.indexOf(str2);
        if (indexOf == INDEX_NOT_FOUND) {
            return null;
        }
        int indexOf2 = str.indexOf(str3, str2.length() + indexOf);
        if (indexOf2 != INDEX_NOT_FOUND) {
            return str.substring(str2.length() + indexOf, indexOf2);
        }
        return null;
    }

    public static String concatenate(Object[] objArr) {
        return join(objArr, null);
    }

    public static String join(Object[] objArr) {
        return join(objArr, null);
    }

    public static String join(Object[] objArr, char c) {
        if (objArr == null) {
            return null;
        }
        return join(objArr, c, 0, objArr.length);
    }

    public static String join(Object[] objArr, char c, int i, int i2) {
        if (objArr == null) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return EMPTY;
        }
        int i4;
        if (objArr[i] == null) {
            i4 = 16;
        } else {
            i4 = objArr[i].toString().length();
        }
        StringBuffer stringBuffer = new StringBuffer((i4 + 1) * i3);
        for (i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                stringBuffer.append(c);
            }
            if (objArr[i4] != null) {
                stringBuffer.append(objArr[i4]);
            }
        }
        return stringBuffer.toString();
    }

    public static String join(Object[] objArr, String str) {
        if (objArr == null) {
            return null;
        }
        return join(objArr, str, 0, objArr.length);
    }

    public static String join(Object[] objArr, String str, int i, int i2) {
        if (objArr == null) {
            return null;
        }
        if (str == null) {
            str = EMPTY;
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return EMPTY;
        }
        int i4;
        if (objArr[i] == null) {
            i4 = 16;
        } else {
            i4 = objArr[i].toString().length();
        }
        StringBuffer stringBuffer = new StringBuffer((i4 + str.length()) * i3);
        for (i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                stringBuffer.append(str);
            }
            if (objArr[i4] != null) {
                stringBuffer.append(objArr[i4]);
            }
        }
        return stringBuffer.toString();
    }

    public static String deleteWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int length = str.length();
        char[] cArr = new char[length];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3;
            if (Character.isWhitespace(str.charAt(i))) {
                i3 = i2;
            } else {
                i3 = i2 + 1;
                cArr[i2] = str.charAt(i);
            }
            i++;
            i2 = i3;
        }
        return i2 != length ? new String(cArr, 0, i2) : str;
    }

    public static String removeStart(String str, String str2) {
        if (isEmpty(str) || isEmpty(str2) || !str.startsWith(str2)) {
            return str;
        }
        return str.substring(str2.length());
    }

    public static String removeEnd(String str, String str2) {
        if (isEmpty(str) || isEmpty(str2) || !str.endsWith(str2)) {
            return str;
        }
        return str.substring(0, str.length() - str2.length());
    }

    public static String remove(String str, String str2) {
        return (isEmpty(str) || isEmpty(str2)) ? str : replace(str, str2, EMPTY, INDEX_NOT_FOUND);
    }

    public static String remove(String str, char c) {
        if (isEmpty(str) || str.indexOf(c) == INDEX_NOT_FOUND) {
            return str;
        }
        char[] toCharArray = str.toCharArray();
        int i = 0;
        for (int i2 = 0; i2 < toCharArray.length; i2++) {
            if (toCharArray[i2] != c) {
                int i3 = i + 1;
                toCharArray[i] = toCharArray[i2];
                i = i3;
            }
        }
        return new String(toCharArray, 0, i);
    }

    public static String replaceOnce(String str, String str2, String str3) {
        return replace(str, str2, str3, 1);
    }

    public static String replace(String str, String str2, String str3) {
        return replace(str, str2, str3, INDEX_NOT_FOUND);
    }

    public static String replace(String str, String str2, String str3, int i) {
        int i2 = 64;
        if (isEmpty(str) || isEmpty(str2) || str3 == null || i == 0) {
            return str;
        }
        int indexOf = str.indexOf(str2, 0);
        if (indexOf == INDEX_NOT_FOUND) {
            return str;
        }
        int length = str2.length();
        int length2 = str3.length() - length;
        if (length2 < 0) {
            length2 = 0;
        }
        if (i < 0) {
            i2 = 16;
        } else if (i <= 64) {
            i2 = i;
        }
        StringBuffer stringBuffer = new StringBuffer((i2 * length2) + str.length());
        i2 = 0;
        while (indexOf != INDEX_NOT_FOUND) {
            stringBuffer.append(str.substring(i2, indexOf)).append(str3);
            i2 = indexOf + length;
            i += INDEX_NOT_FOUND;
            if (i == 0) {
                break;
            }
            indexOf = str.indexOf(str2, i2);
        }
        stringBuffer.append(str.substring(i2));
        return stringBuffer.toString();
    }

    public static String replaceChars(String str, char c, char c2) {
        if (str == null) {
            return null;
        }
        return str.replace(c, c2);
    }

    public static String replaceChars(String str, String str2, String str3) {
        Object obj = null;
        if (isEmpty(str) || isEmpty(str2)) {
            return str;
        }
        if (str3 == null) {
            str3 = EMPTY;
        }
        int length = str3.length();
        int length2 = str.length();
        StringBuffer stringBuffer = new StringBuffer(length2);
        for (int i = 0; i < length2; i++) {
            char charAt = str.charAt(i);
            int indexOf = str2.indexOf(charAt);
            if (indexOf >= 0) {
                obj = 1;
                if (indexOf < length) {
                    stringBuffer.append(str3.charAt(indexOf));
                }
            } else {
                stringBuffer.append(charAt);
            }
        }
        if (obj != null) {
            return stringBuffer.toString();
        }
        return str;
    }

    public static String overlayString(String str, String str2, int i, int i2) {
        return new StringBuffer((((str2.length() + i) + str.length()) - i2) + 1).append(str.substring(0, i)).append(str2).append(str.substring(i2)).toString();
    }

    public static String overlay(String str, String str2, int i, int i2) {
        if (str == null) {
            return null;
        }
        int i3;
        int i4;
        if (str2 == null) {
            str2 = EMPTY;
        }
        int length = str.length();
        if (i < 0) {
            i3 = 0;
        } else {
            i3 = i;
        }
        if (i3 > length) {
            i3 = length;
        }
        if (i2 < 0) {
            i4 = 0;
        } else {
            i4 = i2;
        }
        if (i4 > length) {
            i4 = length;
        }
        if (i3 <= i4) {
            int i5 = i4;
            i4 = i3;
            i3 = i5;
        }
        return new StringBuffer((((length + i4) - i3) + str2.length()) + 1).append(str.substring(0, i4)).append(str2).append(str.substring(i3)).toString();
    }

}
