package com.android.jtknife.modules.feature;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.jtknife.core.app.BaseFeature;
import com.elvishew.xlog.XLog;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/7/17
 */
public class Feature2 extends BaseFeature {

    @Override
    protected void onCreate(@NonNull View parent, @Nullable Bundle savedInstanceState) {
        super.onCreate(parent, savedInstanceState);
        XLog.i("Feature2 onCreate");
    }
}
