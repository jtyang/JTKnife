package com.android.jtknife.modules.live.gift.model.anim;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/1
 */
public class Size {
//    @c(a = "gravity")
    private int gravity = 1;
//    @c(a = "height")
//    @a
    private int height;
//    @c(a = "width")
//    @a
    private int width;

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public int getGravity() {
        return this.gravity;
    }

    public void setGravity(int i) {
        this.gravity = i;
    }
}
