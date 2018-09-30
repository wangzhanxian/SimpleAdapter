package com.wzx.app.smartadapter.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.wzx.app.smartadapter.LayoutId;
import com.wzx.app.smartadapter.LayoutResName;

public class ComnUtil {

    public static @LayoutRes
    int getLayoutId(@NonNull Object object) {
        Class clazz = object instanceof Class ? (Class) object : object.getClass();
        LayoutId annoId = (LayoutId) clazz.getAnnotation(LayoutId.class);
        return annoId != null ? annoId.value() : 0;
    }

    public static String getLayoutName(@NonNull Object object){
        Class clazz = object instanceof Class ? (Class) object : object.getClass();
        LayoutResName annoName = (LayoutResName) clazz.getAnnotation(LayoutResName.class);
        return annoName != null ? annoName.value() : null;
    }


    public static int getLayoutIdByName(Context context,String layoutName){
        if (layoutName == null){
            return 0;
        }
        return context.getResources().getIdentifier(layoutName,"layout",context.getPackageName());
    }

}
