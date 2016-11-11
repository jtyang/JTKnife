package com.android.jtknife.core.common.di;

import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DI {
    private static final String TAG = "DI";
    private static DIContext context;

    static {
        context = new DIContext();
    }

    public static void inject(Object obj) {
        try {
            Method[] declaredMethods = obj.getClass().getDeclaredMethods();
            int length = declaredMethods.length;
            int i = 0;
            Method method = null;
            while (i < length) {
                Method method2 = declaredMethods[i];
                Method method3 = method;
                MethodCommand methodCommand = null;
                for (Annotation annotation : method2.getDeclaredAnnotations()) {
                    if (annotation.annotationType().equals(Bean.class)) {
                        methodCommand = MethodCommand.bean;
                    } else if (annotation.annotationType().equals(InjectOnClick.class)) {
                        methodCommand = MethodCommand.injectOnClick;
                    } else if (annotation.annotationType().equals(AfterInject.class)) {
                        method3 = method2;
                    }
                    if (methodCommand != null) {
                        method2.setAccessible(true);
                        methodCommand.inject(context, method2, annotation, obj);
                        break;
                    }
                }
                i++;
                method = method3;
            }
            for (Field field : ReflectionUtil.getFieldsIncludingParents(obj.getClass())) {
                FieldCommand fieldCommand = null;
                for (Annotation annotation2 : field.getDeclaredAnnotations()) {
                    if (annotation2.annotationType().equals(InjectView.class)) {
                        fieldCommand = FieldCommand.injectView;
                    } else if (annotation2.annotationType().equals(InjectBean.class)) {
                        fieldCommand = FieldCommand.injectBean;
                    }
                    if (fieldCommand != null) {
                        field.setAccessible(true);
                        fieldCommand.inject(context, field, annotation2, obj);
                        break;
                    }
                }
            }
            if (method != null) {
                method.setAccessible(true);
                method.invoke(obj, new Object[0]);
            }
        } catch (Throwable e) {
            Log.e(TAG, "dependency injection failed", e);
            throw new RuntimeException(e);
        }
    }
}
