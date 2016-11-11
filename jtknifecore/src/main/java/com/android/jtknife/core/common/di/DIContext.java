package com.android.jtknife.core.common.di;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class DIContext {
    private static final String TAG = "DI";
    private Map<String, Object> beanMap;

    public DIContext() {
        this.beanMap = new HashMap();
    }

    public Object getBean(Class<?> cls) {
        Object obj = this.beanMap.get(cls.getName());
        if (obj != null) {
            return obj;
        }
        Log.d(TAG, "no bean in context creating " + cls);
        try {
            obj = cls.newInstance();
            this.beanMap.put(cls.getName(), obj);
            DI.inject(obj);
            return obj;
        } catch (Throwable e) {
            Log.e(TAG, " create bean error" + cls, e);
            throw new RuntimeException(e);
        }
    }

    public void addBean(Object obj) {
        this.beanMap.put(obj.getClass().getName(), obj);
    }

    public void addBean(Class cls, Object obj) {
        this.beanMap.put(cls.getName(), obj);
    }
}
