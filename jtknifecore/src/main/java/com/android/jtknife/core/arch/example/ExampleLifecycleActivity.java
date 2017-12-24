package com.android.jtknife.core.arch.example;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.jtknife.core.arch.BaseLifecycleActivity;

import java.util.List;

/**
 * 文件描述:
 *
 * ref: https://github.com/quangctkm9207/mvp-android-arch-component
 * @author yangjiantong
 * @date 2017/12/24
 */
public class ExampleLifecycleActivity extends BaseLifecycleActivity implements ExampleContract.View {

    private ExamplePresenter examplePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        examplePresenter = new ExamplePresenter(this);
//        getLifecycle().addObserver(examplePresenter);
        examplePresenter.loadTestData();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showData(List<String> datas) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (examplePresenter != null) {
//            getLifecycle().removeObserver(examplePresenter);
//        }

    }

}
