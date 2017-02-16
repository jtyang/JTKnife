package com.android.jtknife.widgets.barrage;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.jtknife.R;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/10
 */
public class BarrageItemView extends LinearLayout {

    private final int a = 15;
    private BarrageChatModel b;
    private Context c;
    @Bind(R.id.iv_barrage_avatar)
    ImageView mBarrageAvatarIv;
    @Bind(R.id.txt_barrage_content)
    TextView mBarrageContentTv;
    @Bind(R.id.txt_barrage_name)
    TextView mBarrageNameTv;
    @Bind(R.id.img_level)
    ImageView mLevelIv;

    public BarrageItemView(Context context, BarrageChatModel barrageChatModel) {
        super(context);
        this.c = context;
        this.b = barrageChatModel;
    }

    public BarrageItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BarrageItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int i, int i2) {
        int measureWidth = MeasureSpec.makeMeasureSpec((int) (((float) ((MeasureSpec.getSize(i) - getPaddingLeft()) - getPaddingRight())) * 2.0f), MeasureSpec.EXACTLY);
        super.onMeasure(measureWidth, i2);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if (this.BarrageItemModel.isSystemMsg()) {
//            layoutInflater.inflate(R.layout.layout_barrage_item_official, this, true);
//        } else {
//            layoutInflater.inflate(R.layout.layout_barrage_item_normal, this, true);
//        }
        layoutInflater.inflate(R.layout.layout_barrage_item_normal, this, true);
        ButterKnife.bind((Object) this, (View) this);
        a();
    }

    public TextView getBarrageContentTv() {
        return this.mBarrageContentTv;
    }

    public BarrageChatModel getBarrageChatModel() {
        return this.b;
    }

    public void a() {
//        try {
//            SimpleUserModel user = this.BarrageItemModel.getUser();
//            String str;
//            if (this.BarrageItemModel.isSystemMsg()) {
//                if (user.getNickname().length() >= 15) {
//                    str = user.getNickname().substring(0, 15) + "...";
//                } else {
//                    str = user.getNickname();
//                }
//                String[] split = getContext().getString(R.string.message_user_upgrade, new Object[]{str, Integer.valueOf(user.getLevel())}).split(":");
//                this.mBarrageNameTv.setText(split[0].trim());
//                this.mBarrageContentTv.setText(split[1].trim());
//                return;
//            }
//            CharSequence content;
//            this.mBarrageAvatarIv.a(this.c, user);
//            BarrageItemModel.a(getContext()).a(a.a(user, 4)).BarrageItemModel(this.mBarrageAvatarIv);
//            this.mLevelIv.setImageDrawable(c.a(user.getLevel(), getContext()));
//            str = com.kitty.android.function.BarrageItemModel.a.a.a().a(this.BarrageItemModel.getContent());
//            if (str == null) {
//                content = this.BarrageItemModel.getContent();
//            } else {
//                Object obj = str;
//            }
//            this.mBarrageNameTv.setText(user.getNickname());
//            if (this.BarrageItemModel.isRedPacketMsg()) {
//                RedPacketBarrageChatModel redPacketBarrageChatModel = (RedPacketBarrageChatModel) this.BarrageItemModel;
//                CharSequence spannableString = new SpannableString(content);
//                int lastIndexOf = content.lastIndexOf("@");
//                spannableString.setSpan(redPacketBarrageChatModel.getImageSpan(getContext()), lastIndexOf, lastIndexOf + 1, 17);
//                this.mBarrageContentTv.setText(spannableString);
//                return;
//            }
//            this.mBarrageContentTv.setText(content);
//        } catch (Exception e) {
//        }
        this.mBarrageContentTv.setText(getRandomText());
    }

    private String getRandomText() {
        String text = "AAAAAAAAAABBBBBBBBBBCCCCCCCCCCDDDDDDDDDDEEEEEEEEEEFFFFFFFFFF";
        Random random = new Random();
        int randomInt = random.nextInt(text.length());
        return text.substring(0, randomInt);
//        return text;
    }

}
