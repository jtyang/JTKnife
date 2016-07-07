package com.android.jtknife.core.common.eventbus;

/**
 * EventBus事件消息实体类
 * AUTHOR: yangjiantong
 * DATE: 16/7/8
 */
public class MessageEvent<T> {

    public final int what;
    public T message;

    public MessageEvent(int what) {
        this.what = what;
    }
    public MessageEvent(int what,T message) {
        this.what = what;
        this.message = message;
    }
}
