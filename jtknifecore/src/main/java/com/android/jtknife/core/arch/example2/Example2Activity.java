package com.android.jtknife.core.arch.example2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.jtknife.core.arch.BaseLifecycleActivity;

import java.util.List;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/24
 */
public class Example2Activity extends BaseLifecycleActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Example2UserModel viewModel = ViewModelProviders.of(this).get(Example2UserModel.class);
        viewModel.users.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {

            }
        });

    }
}
