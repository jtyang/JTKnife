package com.android.jtknife.core.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.jtknife.core.app.featured.FeatureHost;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/7/16
 *
 * https://github.com/beworker/featured
 */
public class FeatureHostManager extends FeatureHost<BaseFeature, FeatureHostManager> {
    public FeatureHostManager(@NonNull Context context) {
        super(context);
    }

    public void dispatchOnCreate(@NonNull View parent, @Nullable Bundle savedInstanceState) {
        dispatch(new OnCreateEvent(parent, savedInstanceState));
    }

    public void dispatchOnStart() {
        dispatch(new OnStartEvent());
    }

    public void dispatchOnFabClicked() {
        dispatch(new OnFabClickedEvent());
    }

    public void dispatchOnStop() {
        dispatch(new OnStopEvent());
    }

    public void dispatchOnDestroy() {
        dispatch(new OnDestroyEvent());
    }

    private static final class OnCreateEvent extends FeatureHost.Event<BaseFeature> {
        private final @NonNull View mParent;

        private final @Nullable Bundle mSavedInstanceState;

        OnCreateEvent(@NonNull View parent, @Nullable Bundle savedInstanceState) {
            mParent = parent;
            mSavedInstanceState = savedInstanceState;
        }

        @Override
        protected void dispatch(BaseFeature feature) {
            feature.onCreate(mParent, mSavedInstanceState);
        }
    }

    private static final class OnStartEvent extends FeatureHost.Event<BaseFeature> {
        @Override
        protected void dispatch(BaseFeature feature) {
            feature.onStart();
        }
    }

    private static final class OnFabClickedEvent extends FeatureHost.Event<BaseFeature> {
        @Override
        protected void dispatch(BaseFeature feature) {
            feature.onFabClicked();
        }
    }

    private static final class OnStopEvent extends FeatureHost.Event<BaseFeature> {
        @Override
        protected void dispatch(BaseFeature feature) {
            feature.onStop();
        }
    }

    private static final class OnDestroyEvent extends FeatureHost.Event<BaseFeature> {
        @Override
        protected void dispatch(BaseFeature feature) {
            feature.onDestroy();
        }
    }
}
