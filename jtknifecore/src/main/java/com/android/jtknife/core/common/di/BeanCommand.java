package com.android.jtknife.core.common.di;

import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class BeanCommand implements MethodCommand {
    private static final String TAG = "DI";

    public void inject(DIContext dIContext, Method method, Annotation annotation, Object obj) {
        try {
            dIContext.addBean(method.getReturnType(), method.invoke(obj, new Object[0]));
        } catch (Throwable e) {
            Log.e(TAG, "create bean failed " + method, e);
            throw new RuntimeException(e);
        }
    }
}
