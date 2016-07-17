package com.android.jtknife.modules.feature;

import android.os.Bundle;
import android.view.View;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.core.app.FeatureHostManager;

import butterknife.Bind;

public class FeatureSampleActivity extends BaseActivity {

    @Bind(R.id.root_view)
    View rootView;

    FeatureHostManager featureSampleHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        featureSampleHost = new FeatureHostManager(mContext);
        featureSampleHost.with(new Feature1())
                .with(new Feature2())
                .dispatchOnCreate(rootView, savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_feature_sample;
    }

    @Override
    protected void onInitView() {

    }

    @Override
    protected void onDestroy() {
        featureSampleHost.dispatchOnDestroy();
        super.onDestroy();
    }
}
