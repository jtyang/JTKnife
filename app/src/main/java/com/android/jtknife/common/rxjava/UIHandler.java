package com.android.jtknife.common.rxjava;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/9/20
 */
public class UIHandler {
    private static Handler handler;

    private static synchronized void prepare() {
        if (handler == null) {
            reset();
        }

    }

    private static void reset() {
        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                UIHandler.handleMessage(msg);
                return false;
            }
        });
    }

    private static void handleMessage(Message msg) {
        UIHandler.InnerObj io = (UIHandler.InnerObj) msg.obj;
        Message inner = io.msg;
        Handler.Callback callback = io.callback;
        if (callback != null) {
            callback.handleMessage(inner);
        }

    }

    private static Message getShellMessage(Message msg, Handler.Callback callback) {
        Message shell = new Message();
        shell.obj = new UIHandler.InnerObj(msg, callback);
        return shell;
    }

    private static Message getShellMessage(int what, Handler.Callback callback) {
        Message msg = new Message();
        msg.what = what;
        return getShellMessage(msg, callback);
    }

    public static boolean sendMessage(Message msg, Handler.Callback callback) {
        prepare();
        return handler.sendMessage(getShellMessage(msg, callback));
    }

    public static boolean sendMessageDelayed(Message msg, long delayMillis, Handler.Callback callback) {
        prepare();
        return handler.sendMessageDelayed(getShellMessage(msg, callback), delayMillis);
    }

    public static boolean sendMessageAtTime(Message msg, long uptimeMillis, Handler.Callback callback) {
        prepare();
        return handler.sendMessageAtTime(getShellMessage(msg, callback), uptimeMillis);
    }

    public static boolean sendMessageAtFrontOfQueue(Message msg, Handler.Callback callback) {
        prepare();
        return handler.sendMessageAtFrontOfQueue(getShellMessage(msg, callback));
    }

    public static boolean sendEmptyMessage(int what, Handler.Callback callback) {
        prepare();
        return handler.sendMessage(getShellMessage(what, callback));
    }

    public static boolean sendEmptyMessageAtTime(int what, long uptimeMillis, Handler.Callback callback) {
        prepare();
        return handler.sendMessageAtTime(getShellMessage(what, callback), uptimeMillis);
    }

    public static boolean sendEmptyMessageDelayed(int what, long delayMillis, Handler.Callback callback) {
        prepare();
        return handler.sendMessageDelayed(getShellMessage(what, callback), delayMillis);
    }

    private static final class InnerObj {
        public final Message msg;
        public final Handler.Callback callback;

        public InnerObj(Message msg, Handler.Callback callback) {
            this.msg = msg;
            this.callback = callback;
        }
    }
}
