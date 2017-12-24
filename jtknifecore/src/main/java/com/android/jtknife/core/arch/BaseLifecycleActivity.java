package com.android.jtknife.core.arch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/24
 */
public abstract class BaseLifecycleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
