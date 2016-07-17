package com.android.jtknife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.jtknife.core.app.BaseAppCompatActivity;
import com.android.jtknife.modules.feature.FeatureSampleActivity;

import butterknife.Bind;

public class MainActivity extends BaseAppCompatActivity implements View.OnClickListener {

    @Bind(R.id.feature_btn)
    Button featureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {
        featureButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feature_btn:
                startActivity(new Intent(mContext, FeatureSampleActivity.class));
                break;
            default:
                break;

        }
    }
}
