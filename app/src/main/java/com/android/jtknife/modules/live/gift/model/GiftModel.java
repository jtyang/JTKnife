package com.android.jtknife.modules.live.gift.model;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/1
 */
public class GiftModel {
    public static final int TYPE_DYNAMIC = 2;
    public static final int TYPE_STATIC = 1;
    //    @c(a = "diamond") @SerializedName("Name")
    private int diamond;
    //    @c(a = "effect")
    private String effect;
    //    @c(a = "exp")
    private int exp;
    //    @c(a = "gift_id")
    private int giftId;
    //    @c(a = "type")
    private int giftType;
    //    @c(a = "icon")
    private String icon;
    //    @c(a = "image")
    private String image;
    private boolean isSelected = false;
    //    @c(a = "mini_icon")
    private String miniIcon;
    //    @c(a = "name")
    private String name;

    public int getGiftId() {
        return this.giftId;
    }

    public void setGiftId(int i) {
        this.giftId = i;
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

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String str) {
        this.icon = str;
    }

    public String getEffect() {
        return this.effect;
    }

    public void setEffect(String str) {
        this.effect = str;
    }

    public int getExp() {
        return this.exp;
    }

    public void setExp(int i) {
        this.exp = i;
    }

    public int getGiftType() {
        return this.giftType;
    }

    public void setGiftType(int i) {
        this.giftType = i;
    }

    public int getDiamond() {
        return this.diamond;
    }

    public void setDiamond(int i) {
        this.diamond = i;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public String getMiniIcon() {
        return this.miniIcon;
    }

    public void setMiniIcon(String str) {
        this.miniIcon = str;
    }

    public String toString() {
        return "GiftModel{giftId=" + this.giftId + ", name='" + this.name + '\'' + ", image='" + this.image + '\'' + ", icon='" + this.icon + '\'' + ", effect='" + this.effect + '\'' + ", exp='" + this.exp + '\'' + ", giftType=" + this.giftType + ", diamond=" + this.diamond + ", isSelected=" + this.isSelected + '}';
    }
}
