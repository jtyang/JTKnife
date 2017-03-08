package com.android.jtknife.modules.live.gift.model.anim;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/1
 */
public class GiftAnimEffect {
//    @c(a = "animation_list")
//    @a
    private List<AnimItem> animItem = new ArrayList();
//    @c(a = "duration")
//    @a
    private float duration;

    public float getDuration() {
        return this.duration;
    }

    public void setDuration(float f) {
        this.duration = f;
    }

    public List<AnimItem> getAnimItem() {
        return this.animItem;
    }

    public void setAnimItem(List<AnimItem> list) {
        this.animItem = list;
    }
}
