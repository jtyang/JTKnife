package com.android.jtknife.modules.live.gift.model.anim;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/1
 */
public class AnimItem {
//    @c(a = "animations")
//    @a
    private List<AnimFrame> animFrameSet = new ArrayList();
//    @c(a = "name")
//    @a
    private String name;
//    @c(a = "repeat")
//    @a
    private int repeat;
//    @c(a = "scaleType")
//    @a
    private int scaleType;
//    @c(a = "size")
//    @a
    private Size size;
//    @c(a = "type")
//    @a
    private int type;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getScaleType() {
        return this.scaleType;
    }

    public void setScaleType(int i) {
        this.scaleType = i;
    }

    public int getRepeat() {
        return this.repeat;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public void setRepeat(int i) {
        this.repeat = i;
    }

    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public List<AnimFrame> getAnimFrameSet() {
        return this.animFrameSet;
    }

    public void setAnimFrameSet(List<AnimFrame> list) {
        this.animFrameSet = list;
    }
}
