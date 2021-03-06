package com.android.jtknife.core.common.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus管理类
 * AUTHOR: yangjiantong
 * DATE: 16/7/8
 */
public class BusManager {

    private static BusManager ourInstance = new BusManager();

    public static BusManager getDefault() {
        return ourInstance;
    }

    private BusManager() {

    }

    public void sendMessage(MessageEvent event) {
        EventBus.getDefault().post(event);
    }
}
