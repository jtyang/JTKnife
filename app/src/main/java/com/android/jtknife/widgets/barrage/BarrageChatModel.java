package com.android.jtknife.widgets.barrage;

import android.content.Context;
import android.text.SpannableString;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/10
 */
public class BarrageChatModel {
    public static final int BARRAGE_TYPE_NORMAL = 2;
    public static final int BARRAGE_TYPE_REDPACKET_GET = 4;
    public static final int BARRAGE_TYPE_REDPACKET_SEND = 3;
    public static final int BARRAGE_TYPE_SYSTEM = 1;
    private static final String TAG = BarrageChatModel.class.getSimpleName();
    protected int barrageType = BARRAGE_TYPE_NORMAL;
    protected String content;

    public int id;
    public BarrageChatModel(int id){
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public int getBarrageType() {
        return this.barrageType;
    }

    public void setBarrageType(int i) {
        this.barrageType = i;
    }

    public SpannableString getSendableString(Context context) {
//        if (this.mSendableMessage == null) {
//            String a = a.a().a(this.content);
//            if (a == null) {
//                a = this.content;
//            }
//            Object string = context.getString(R.string.message_adc_chat, new Object[]{"# " + getUser().getNickname(), a});
//            int length = getUser().getNickname().length() + 4;
//            int length2 = this.content.length() + length;
//            this.mSendableMessage = new SpannableString(string);
//            this.mSendableMessage.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.text_icon)), length, length2, 33);
//            Drawable a2 = c.a(getUser().getLevel(), context);
//            a2.setBounds(0, 0, a2.getIntrinsicWidth(), a2.getIntrinsicHeight());
//            ImageSpan imageSpan = new ImageSpan(a2, 1);
//            int indexOf = string.indexOf("#");
//            this.mSendableMessage.setSpan(imageSpan, indexOf, indexOf + 1, 17);
//        }
//        return this.mSendableMessage;
        return new SpannableString("test send string");
    }

    public boolean isSystemMsg() {
        return this.barrageType == 1;
    }

    public boolean isRedPacketMsg() {
        return this.barrageType == 4 || this.barrageType == 3;
    }
}
