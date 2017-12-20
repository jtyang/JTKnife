package com.android.jtknife.core.app;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.jtknife.core.common.di.DI;
import com.android.jtknife.core.common.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.ButterKnife;


/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/6/29
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    public Context mContext;
    public boolean isShow = false;
    public boolean isAlive = false;
    //cache delegates
    private ArrayList<LifecycleObserver> mDelegateCaches = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        isAlive = true;
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        DI.inject(this);
        EventBus.getDefault().register(this);
        onInitView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShow = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShow = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearDelegates();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
        mContext = null;
        isAlive = false;
    }

    /**
     * EventBus事件处理--主线程处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event != null) {
            handleBusMessage(event);
            if (isShow) {
                handleBusMessageIfShow(event);
            }
        }
    }

    /**
     * 子类重写处理消息
     *
     * @param event
     */
    public void handleBusMessage(MessageEvent event) {

    }

    public void handleBusMessageIfShow(MessageEvent event) {

    }

    //================= delegate ======================
    protected void registerDelegate(LifecycleObserver... los) {
        if (los != null && los.length > 0) {
            for (LifecycleObserver lo : los) {
                registerDelegate(lo);
            }
        }
    }

    protected void registerDelegate(LifecycleObserver lo) {
        if (lo != null) {
            getLifecycle().addObserver(lo);
            mDelegateCaches.add(lo);
        }
    }

    private void clearDelegates() {
        for (LifecycleObserver lo : mDelegateCaches) {
            if (lo != null) getLifecycle().removeObserver(lo);
        }
        mDelegateCaches.clear();
    }

    //************* 系统函数封装toast/dialog 等 **********
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int msgRes) {
        showToast(getString(msgRes));
    }

    //************* 子类必须实现的抽象方法 start **********
    protected abstract int getLayoutResource();

    protected abstract void onInitView();

    //************* 子类必须实现的抽象方法 end**********

}
