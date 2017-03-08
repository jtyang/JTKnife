package com.android.jtknife.modules.live.gift.model;

import android.content.Context;
import android.text.SpannableString;

import com.google.gson.Gson;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/1
 */
public class BaseChatModel implements Cloneable {
    public static final int TYPE_BARRAGE = 700;
    public static final int TYPE_GIFT = 400;
    public static final int TYPE_LIKE = 300;
    public static final int TYPE_PUB = 200;
    public static final int TYPE_SHARED = 500;
    public static final int TYPE_STICKER = 600;
    public static final int TYPE_SYS = 100;
    protected SpannableString mSendableMessage;
//    @c(a = "room_id")
    protected int roomId;
//    @c(a = "type")
    protected int type;
//    @c(a = "user")
    protected SimpleUserModel user;

    protected BaseChatModel() {
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void setRoomId(int i) {
        this.roomId = i;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public SimpleUserModel getUser() {
        return this.user;
    }

    public void setUser(SimpleUserModel simpleUserModel) {
        this.user = simpleUserModel;
    }

    public SpannableString getSendableString(Context context) {
        return this.mSendableMessage;
    }

    public String toString() {
        return "BaseModel [room_id = " + this.roomId + '\'' + ", type = " + this.type + '\'' + ", user = " + this.user.toString() + "]";
    }

    public String toJson() {
        return new Gson().toJson(this);
//        return new f().b((Object) this);
    }
}
