package com.android.jtknife.core.common.di;

import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class InjectBeanCommand implements FieldCommand {
    private static final String TAG = "DI";

    public void inject(DIContext dIContext, Field field, Annotation annotation, Object obj) {
        Log.d(TAG, "inject bean type " + field.getType());
        try {
            field.set(obj, dIContext.getBean(field.getType()));
        } catch (Throwable e) {
            Log.e(TAG, "inject bean failed " + field.getType(), e);
            throw new RuntimeException(e);
        }
    }
}
