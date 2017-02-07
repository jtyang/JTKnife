package com.android.jtknife.modules.live;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/6
 */
public class DisplayUtils {
//    public static int a(Context context) {
//        WindowManager windowManager = (WindowManager) context.getSystemService("window");
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        return displayMetrics.widthPixels;
//    }
//
//    public static int b(Context context) {
//        WindowManager windowManager = (WindowManager) context.getSystemService("window");
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        return displayMetrics.heightPixels;
//    }

    public static int a(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
//    public static int c(Context context) {
//        if (!d(context)) {
//            return 0;
//        }
//        Resources resources = context.getResources();
//        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
//        if (identifier > 0) {
//            return resources.getDimensionPixelSize(identifier);
//        }
//        return 0;
//    }
//
//    public static boolean d(Context context) {
//        boolean z = true;
//        Resources resources = context.getResources();
//        int identifier = resources.getIdentifier("config_showNavigationBar", "bool", "android");
//        if (identifier != 0) {
//            boolean z2 = resources.getBoolean(identifier);
//            String b = b();
//            if (AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(b)) {
//                return false;
//            }
//            if (AppEventsConstants.EVENT_PARAM_VALUE_NO.equals(b)) {
//                return true;
//            }
//            return z2;
//        }
//        if (ViewConfiguration.get(context).hasPermanentMenuKey()) {
//            z = false;
//        }
//        return z;
//    }

    public static int b(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    public static int c(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }
}
