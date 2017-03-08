package com.android.jtknife.modules.live.gift.model.anim;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/1
 */
public class AnimFrame {
//    @c(a = "duration")
//    @a
    private float duration;
//    @c(a = "end")
//    @a
    private End end;
//    @c(a = "start")
//    @a
    private Start start;

    public float getDuration() {
        return this.duration;
    }

    public void setDuration(float f) {
        this.duration = f;
    }

    public Start getStart() {
        return this.start;
    }

    public void setStart(Start start) {
        this.start = start;
    }

    public End getEnd() {
        return this.end;
    }

    public void setEnd(End end) {
        this.end = end;
    }
}
