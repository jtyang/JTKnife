package com.android.jtknife.core.common.di;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface FieldCommand {
    public static final FieldCommand injectBean = new InjectViewCommand();
    public static final FieldCommand injectView = new InjectBeanCommand();

    void inject(DIContext dIContext, Field field, Annotation annotation, Object obj);

}
