package com.android.jtknife.modules.live.gift.model;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/1
 */
public class GiftDynamicModel {
    //    @c(a = "effect")
    private String effect;
    //    @c(a = "icon")
    private String icon;
    //    @c(a = "image")
    private String image;
    //    @c(a = "mini_icon")
    private String miniIcon;
    //    @c(a = "name")
    private String name;

    public String getMiniIcon() {
        return this.miniIcon;
    }

    public void setMiniIcon(String str) {
        this.miniIcon = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public String getEffect() {
        return this.effect;
    }

    public void setEffect(String str) {
        this.effect = str;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String str) {
        this.icon = str;
    }
}
