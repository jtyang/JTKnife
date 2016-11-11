package com.android.jtknife.core.common.di;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class InjectViewCommand implements FieldCommand {
    private static final String TAG = "DI";

    public void inject(DIContext dIContext, Field field, Annotation annotation, Object obj) {
        Object findViewById;
        InjectView injectView = (InjectView) annotation;
        Log.d(TAG, "inject view id " + injectView.id());
        if (obj instanceof Activity) {
            findViewById = ((Activity) obj).findViewById(injectView.id());
        } else if (obj instanceof View) {
            findViewById = ((View) obj).findViewById(injectView.id());
        } else {
            throw new IllegalArgumentException("class " + obj.getClass() + " not supported for InjectView");
        }
        if (findViewById == null) {
            throw new IllegalArgumentException("view not found for " + obj.getClass() + " filed " + field);
        }
        try {
            field.set(obj, findViewById);
        } catch (Throwable e) {
            Log.e(TAG, "inject view failed " + injectView.id(), e);
            throw new RuntimeException(e);
        }
    }
}
