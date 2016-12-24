package com.android.jtknife.core.common.notification;

import android.os.Handler;
import android.util.Log;

import java.util.Map;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/1
 */
public enum NotificationCenter {
    INSTANCE;

    public static final String TAG = "notification";
    private Handler handler;
    private long mainThreadId;
    private Map<Class<?>, Notification> notificationMap;
    private Map<Object, Boolean> observers;

    /* renamed from: com.yy.androidlib.util.notification.NotificationCenter.1 */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ Object val$observer;

        AnonymousClass1(Object obj) {
            this.val$observer = obj;
        }

        public void run() {
            NotificationCenter.this.doAddObserver(this.val$observer);
        }
    }

    /* renamed from: com.yy.androidlib.util.notification.NotificationCenter.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ Object val$observer;

        AnonymousClass2(Object obj) {
            this.val$observer = obj;
        }

        public void run() {
            NotificationCenter.this.doRemoveObserver(this.val$observer);
        }
    }

    public void addObserver(Object obj) {
        if (isMainThread()) {
            doAddObserver(obj);
            return;
        }
        Log.w(TAG, String.format("trying to add observer in non main thread: " + obj.getClass(), new Object[0]));
        this.handler.post(new AnonymousClass1(obj));
    }

    private boolean isMainThread() {
        return Thread.currentThread().getId() == this.mainThreadId;
    }

    private void doAddObserver(Object obj) {
        this.observers.put(obj, Boolean.valueOf(true));
    }

    public void removeObserver(Object obj) {
        if (isMainThread()) {
            doRemoveObserver(obj);
            return;
        }
        Log.w(TAG, String.format("trying to remove observer in non main thread: " + obj.getClass(), new Object[0]));
        removeObserverLater(obj);
    }

    private void removeObserverLater(Object obj) {
        this.handler.post(new AnonymousClass2(obj));
    }

    private void doRemoveObserver(Object obj) {
        this.observers.remove(obj);
    }

    private <T> Notification<T> getNotification(Class<T> cls) {
        Notification<T> notification = (Notification) this.notificationMap.get(cls);
        if (notification == null) {
            return addNotification(cls);
        }
        return notification;
    }

    private <T> Notification<T> addNotification(Class<T> cls) {
        Notification<T> notification = (Notification) this.notificationMap.get(cls);
        if (notification != null) {
            return notification;
        }
        notification = new Notification(cls, this.handler, this.observers);
        this.notificationMap.put(cls, notification);
        return notification;
    }

    public <T> T getObserver(Class<T> cls) {
        return getNotification(cls).getObserver();
    }

    public void removeAll() {
        this.observers.clear();
    }

    @Deprecated
    public void addCallbacks(Class cls) {
    }
}

