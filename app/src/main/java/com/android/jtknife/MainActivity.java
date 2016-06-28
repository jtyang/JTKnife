package com.android.jtknife;

import android.os.Bundle;

import com.android.jtknife.core.app.BaseAppCompatActivity;

public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}
