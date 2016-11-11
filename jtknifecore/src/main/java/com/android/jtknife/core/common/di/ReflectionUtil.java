package com.android.jtknife.core.common.di;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {
    public static Iterable<Field> getFieldsIncludingParents(Class<?> cls) {
        List arrayToList = arrayToList(cls.getDeclaredFields());
        Class superclass = cls.getSuperclass();
        if (!(superclass == null || superclass.equals(Activity.class) || superclass.equals(View.class) || superclass.equals(Object.class) || superclass.equals(Fragment.class))) {
            arrayToList.addAll((List) getFieldsIncludingParents(superclass));
        }
        return arrayToList;
    }

    private static List<Field> arrayToList(Field[] fieldArr) {
        List<Field> arrayList = new ArrayList(fieldArr.length);
        for (Field obj : fieldArr) {
            arrayList.add(obj);
        }
        return arrayList;
    }
}
