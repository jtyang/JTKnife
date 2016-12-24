package com.android.jtknife.core.common.eventbus;

/**
 * EventBus事件消息实体类
 * AUTHOR: yangjiantong
 * DATE: 16/7/8
 */
public class MessageEvent {

    private int what;
    public int msgId;
    public Object obj;

    public MessageEvent(int what) {
        this.what = what;
    }
    public MessageEvent(int what,Object message) {
        this.what = what;
        this.obj = message;
    }
}
