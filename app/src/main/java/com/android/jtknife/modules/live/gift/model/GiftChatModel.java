package com.android.jtknife.modules.live.gift.model;

import android.content.Context;
import android.text.SpannableString;

import com.android.jtknife.modules.live.gift.model.anim.GiftAnimEffect;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/1
 */
public class GiftChatModel extends BaseChatModel implements Comparable<GiftChatModel> {
//    @c(a = "count")
    private int count;
//    @c(a = "gift")
    private GiftModel gift;
    private GiftAnimEffect giftAnimEffect;
//    @c(a = "sequence")
    private int sequence;

    public GiftChatModel() {
        this.type = BaseChatModel.TYPE_GIFT;
    }

    public GiftModel getGift() {
        return this.gift;
    }

    public void setGift(GiftModel giftModel) {
        this.gift = giftModel;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int i) {
        this.sequence = i;
    }

    public SpannableString getSendableString(Context context) {
        if (this.mSendableMessage == null) {
//            Object string = context.getString(R.string.message_user_gift, new Object[]{"# " + getUser().getNickname(), context.getString(R.string.room_send_gift_message)});
//            int indexOf = string.indexOf(r0);
//            int length = string.length();
//            this.mSendableMessage = new SpannableString(string);
//            this.mSendableMessage.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.pubchat_sys_text_content2)), indexOf, length, 33);
//            Drawable a = com.kitty.android.ui.user.c.c.a(getUser().getLevel(), context);
//            a.setBounds(0, 0, a.getIntrinsicWidth(), a.getIntrinsicHeight());
//            ImageSpan imageSpan = new ImageSpan(a, 1);
//            indexOf = string.indexOf("#");
//            this.mSendableMessage.setSpan(imageSpan, indexOf, indexOf + 1, 17);
        }
        return this.mSendableMessage;
    }

    public String toString() {
        return "GiftChatModel [type = " + this.type + '\'' + ", gift = " + this.gift.toString() + "]";
    }

    public int compareTo(GiftChatModel giftChatModel) {
        if (this.sequence > giftChatModel.getSequence()) {
            return 1;
        }
        return this.sequence == giftChatModel.getSequence() ? 0 : -1;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public GiftChatModel clone() {
        try {
            return (GiftChatModel) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public GiftAnimEffect getGiftAnimEffect() {
        return this.giftAnimEffect;
    }

    public void setGiftAnimEffect(GiftAnimEffect giftAnimEffect) {
        this.giftAnimEffect = giftAnimEffect;
    }
}
