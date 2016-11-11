package com.android.jtknife.core.common.di;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class InjectOnClickCommand implements MethodCommand {
    private static final String TAG = "DI";

    /* renamed from: com.yy.androidlib.di.InjectOnClickCommand.1 */
    class AnonymousClass1 implements OnClickListener {
        final /* synthetic */ Method val$method;
        final /* synthetic */ Object val$target;

        AnonymousClass1(Method method, Object obj) {
            this.val$method = method;
            this.val$target = obj;
        }

        public void onClick(View view) {
            try {
                if (this.val$method.getParameterTypes().length == 0) {
                    this.val$method.invoke(this.val$target, new Object[0]);
                    return;
                }
                this.val$method.invoke(this.val$target, new Object[]{view});
            } catch (Throwable e) {
                Log.e(InjectOnClickCommand.TAG, "invoke onclick failed method " + this.val$method, e);
                throw new RuntimeException(e);
            }
        }
    }

    public void inject(DIContext dIContext, Method method, Annotation annotation, Object obj) {
        View findViewById;
        InjectOnClick injectOnClick = (InjectOnClick) annotation;
        if (obj instanceof Activity) {
            findViewById = ((Activity) obj).findViewById(injectOnClick.id());
        } else if (obj instanceof View) {
            findViewById = ((View) obj).findViewById(injectOnClick.id());
        } else {
            throw new IllegalArgumentException("class " + obj.getClass() + " not supported for InjectView");
        }
        if (findViewById == null) {
            throw new IllegalArgumentException("view not found for " + obj.getClass() + " method " + method);
        }
        findViewById.setOnClickListener(new AnonymousClass1(method, obj));
    }
}
