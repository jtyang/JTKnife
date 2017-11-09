package com.android.jtknife.modules.softinput;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/11/9
 */
public class SoftTest2Activity extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_soft_test2;
    }

    @Override
    protected void onInitView() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
//        AndroidBug5497Workaround.assistActivity(this);
    }
}
