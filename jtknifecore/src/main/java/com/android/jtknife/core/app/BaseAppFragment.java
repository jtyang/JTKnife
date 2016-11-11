package com.android.jtknife.core.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.jtknife.core.common.di.DI;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/6/29
 */
public abstract class BaseAppFragment extends Fragment {

    private boolean isPause = false;

    public BaseAppFragment() {
        DI.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.isPause = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        this.isPause = true;
    }

    public boolean isPaused() {
        return this.isPause;
    }

    //************* 子类必须实现的抽象方法 start **********
    protected abstract int getLayoutResource();

    protected abstract void onInitView();

    //************* 子类必须实现的抽象方法 end**********
}
