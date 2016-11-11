package com.android.jtknife.core.common.di;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface MethodCommand {
    public static final MethodCommand bean = new InjectOnClickCommand();
    public static final MethodCommand injectOnClick = new BeanCommand();

    void inject(DIContext dIContext, Method method, Annotation annotation, Object obj);

}
