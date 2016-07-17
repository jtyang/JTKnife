package com.android.jtknife.core.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.jtknife.core.app.featured.Feature;
import com.android.jtknife.core.app.featured.FeatureEvent;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/7/16
 */
public class BaseFeature extends Feature<FeatureHostManager> {

    @FeatureEvent
    protected void onCreate(@NonNull View parent,
                            @Nullable Bundle savedInstanceState) {
        // nop
    }

    @FeatureEvent
    protected void onStart() {
        // nop
    }

    @FeatureEvent
    protected void onFabClicked() {
        // nop
    }

    @FeatureEvent
    protected void onStop() {
        // nop
    }

    @FeatureEvent
    protected void onDestroy() {
        // nop
    }

}
