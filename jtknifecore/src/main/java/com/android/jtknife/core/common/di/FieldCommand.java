package com.android.jtknife.core.common.di;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface FieldCommand {
    FieldCommand injectView = new InjectViewCommand();
    FieldCommand injectBean = new InjectBeanCommand();

    void inject(DIContext dIContext, Field field, Annotation annotation, Object obj);

}
