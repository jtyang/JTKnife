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
//    public static int BarrageItemModel(Context context) {
//        WindowManager windowManager = (WindowManager) context.getSystemService("window");
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        return displayMetrics.heightPixels;
//    }

    public static int getScreenHeight(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getScreenWidth(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
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
//        boolean BarrageHolder = true;
//        Resources resources = context.getResources();
//        int identifier = resources.getIdentifier("config_showNavigationBar", "bool", "android");
//        if (identifier != 0) {
//            boolean z2 = resources.getBoolean(identifier);
//            String BarrageItemModel = BarrageItemModel();
//            if (AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(BarrageItemModel)) {
//                return false;
//            }
//            if (AppEventsConstants.EVENT_PARAM_VALUE_NO.equals(BarrageItemModel)) {
//                return true;
//            }
//            return z2;
//        }
//        if (ViewConfiguration.get(context).hasPermanentMenuKey()) {
//            BarrageHolder = false;
//        }
//        return BarrageHolder;
//    }

    /**
     * 获取状态栏高度
     * @param activity
     * @return
     */
    public static int getWindowVisibleDisplayFrameTop(Activity activity) {
        Rect rect = new Rect();
        //getWindowVisibleDisplayFrame(rect)可以获取到程序显示的区域，包括标题栏，但不包括状态栏,获取后的区域坐标会保存在rect(Rect类型)中
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    /**
     * 获取程序显示区域高度，不包括状态栏
     * @param activity
     * @return
     */
    public static int getWindowVisibleDisplayFrameHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }
}
