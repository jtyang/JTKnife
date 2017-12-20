package com.android.jtknife.core.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.jtknife.core.common.di.DI;
import com.android.jtknife.core.common.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/6/29
 */
public abstract class BaseAppFragment extends Fragment {

    public boolean isShow = false;

    public BaseAppFragment() {
        DI.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        NotificationCenter.INSTANCE.addObserver(this);
        int layoutId = getLayoutResource();
        if (layoutId > 0) {
            return inflater.inflate(layoutId, null);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        onInitView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        NotificationCenter.INSTANCE.removeObserver(this);
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.isShow = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        this.isShow = false;
    }


    /**
     * EventBus事件处理--主线程处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event == null) return;
        handleBusMessage(event);
        if (isShow) {
            handleBusMessageOnActivityShow(event);
        }
    }

    /**
     * 子类重写处理消息
     *
     * @param event
     */
    protected void handleBusMessage(MessageEvent event) {
    }

    protected void handleBusMessageOnActivityShow(MessageEvent event) {
    }


    //************* 子类必须实现的抽象方法 start **********
    protected abstract int getLayoutResource();

    protected abstract void onInitView();

    //************* 子类必须实现的抽象方法 end**********
}
