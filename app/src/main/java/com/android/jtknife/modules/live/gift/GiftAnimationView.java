package com.android.jtknife.modules.live.gift;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.jtknife.R;
import com.android.jtknife.modules.live.gift.model.GiftChatModel;
import com.android.jtknife.modules.live.gift.model.anim.GiftAnimEffect;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.util.LinkedList;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/1
 */
public class GiftAnimationView extends FrameLayout implements View.OnClickListener {

    private LinkedList<GiftChatModel> a;
    private boolean b;
    private File c;
    private AnimatorSet d;
    private GiftChatModel e;
//    private i f;

    public GiftAnimationView(Context context) {
        this(context, null);
    }

    public GiftAnimationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GiftAnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new LinkedList();
    }

    public void a(GiftChatModel giftChatModel) {
        this.a.offer(giftChatModel);
        if (!this.b) {
            b();
        }
    }

    private void b() {
        this.e = (GiftChatModel) this.a.poll();
        if (this.e != null) {
            this.b = true;
            this.c = new File(EnvironmentHelper.j + new File(FileDownloadUtils.getDefaultSaveFilePath(this.e.getGift().getEffect())).getName());
            a(this.e.getGiftAnimEffect());
        }
    }

    private void a(GiftAnimEffect giftAnimEffect) {
        try {
            setVisibility(View.VISIBLE);
            this.d = com.android.jtknife.modules.live.gift.resource.b.a(this.c, giftAnimEffect, this);
            View findViewById = findViewById(R.id.gift_sender_container);
            if (findViewById != null) {
                findViewById.setOnClickListener(this);
            }
//            ImageView imageView = (MarkedImageView) findViewById(R.id.iv_gift_sender);
//            imageView.a(getContext(), this.e.getUser());
            TextView textView = (TextView) findViewById(R.id.txt_gift_sender);
            TextView textView2 = (TextView) findViewById(R.id.txt_gift_desc);
//            if (imageView != null) {
//                com.kitty.android.base.image.b.a(getContext()).a(com.kitty.android.ui.user.c.a.a(this.e.getUser(), 4)).b(imageView);
//            }
            if (textView != null) {
                textView.setText(this.e.getUser().getNickname());
            }
            if (textView2 != null) {
                textView2.setText("test jason send gift");
//                textView2.setText(getContext().getString(R.string.serial_gift_desc, new Object[]{this.e.getGift().getName()}));
            }
            this.d.addListener(new AnimatorListener() {

                public void onAnimationStart(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    setVisibility(View.INVISIBLE);
                    removeAllViews();
                    if (a.size() > 0) {
                        b();
                    } else {
                        b = false;
                    }
                }

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }
            });
            this.d.start();
        } catch (Exception e) {
        }
    }

    public void a() {
        if (this.a != null) {
            this.a.clear();
        }
        if (this.d != null) {
            this.d.cancel();
        }
        this.b = false;
        setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
//        NBSEventTraceEngine.onClickEventEnter(view, this);
//        if (this.f != null) {
//            this.f.a(this.e.getUser());
//        }
//        NBSEventTraceEngine.onClickEventExit();
    }

//    public void setOnRoomPerListener(i iVar) {
//        this.f = iVar;
//    }
}
