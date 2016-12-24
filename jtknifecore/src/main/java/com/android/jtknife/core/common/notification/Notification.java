package com.android.jtknife.core.common.notification;

import android.os.Handler;
import android.util.Log;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/1
 */
public class Notification<T> implements InvocationHandler {
    private Class<T> callback;
    private Handler mainHandler;
    private T observerProxy;
    private final Map<Object, Boolean> observers;

    /* renamed from: com.yy.androidlib.util.notification.Notification.1 */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ Object[] val$args;
        final /* synthetic */ Method val$method;

        AnonymousClass1(Method method, Object[] objArr) {
            this.val$method = method;
            this.val$args = objArr;
        }

        public void run() {
            Notification.this.doInvoke(this.val$method, this.val$args);
        }
    }

    public Notification(Class<T> cls, Handler handler, Map<Object, Boolean> map) {
        this.observerProxy = null;
        this.callback = cls;
        this.mainHandler = handler;
        this.observers = map;
    }

    public Object invoke(Object obj, Method method, Object[] objArr) {
        this.mainHandler.post(new AnonymousClass1(method, objArr));
        return null;
    }

    private void doInvoke(Method method, Object[] objArr) {
        for (Object next : this.observers.keySet()) {
            if (this.callback.isInstance(next)) {
                try {
                    method.invoke(next, objArr);
                } catch (Throwable e) {
                    Log.e(NotificationCenter.TAG, "invoke error, method: " + method.getName(), e);
                }
            }
        }
    }

    public T getObserver() {
        if (this.observerProxy == null) {
            this.observerProxy = (T) Proxy.newProxyInstance(this.callback.getClassLoader(), new Class[]{this.callback}, this);
        }
        return this.observerProxy;
    }
}
