package com.android.jtknife.widgets.barrage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.android.jtknife.R;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/10
 */
public class KittyBarrageView extends RelativeLayout implements View.OnClickListener {

    private Context context;
    private MyHandler myHandler;
    private boolean isCanPlay;
    private int currRoadIndex;
    private int e;
    private int f;
    private float[] roadDelayTimes;
    private LinkedList<BarrageChatModel> barrageChatModels;

    private static class MyHandler extends Handler {
        WeakReference<KittyBarrageView> weakReference;

        MyHandler(KittyBarrageView barrageView) {
            this.weakReference = new WeakReference(barrageView);
        }

        public void handleMessage(Message message) {
            KittyBarrageView barrageView = (KittyBarrageView) this.weakReference.get();
            if (barrageView != null) {
                switch (message.what) {
                    case 1:
                        barrageView.playBarrage();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public class BarrageItemModel {
        public BarrageItemView barrageItemView;
        public int duration;
        public int topMargin;
        public int animEndPosition;
        final /* synthetic */ KittyBarrageView kittyBarrageView;

        public BarrageItemModel(KittyBarrageView barrageView) {
            this.kittyBarrageView = barrageView;
        }
    }

    public KittyBarrageView(Context context) {
        super(context);
        init(context);
    }

    public KittyBarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KittyBarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.myHandler = new MyHandler(this);
        this.isCanPlay = true;
        this.currRoadIndex = 0;
        this.roadDelayTimes = new float[2];
        this.barrageChatModels = new LinkedList();
        this.context = context;
    }


    public void addBarrage(BarrageChatModel barrageChatModel) {
        if (barrageChatModel.isSystemMsg()) {
            this.barrageChatModels.addFirst(barrageChatModel);
        } else {
            this.barrageChatModels.add(barrageChatModel);
        }
        if (this.isCanPlay) {
            this.myHandler.sendEmptyMessage(1);
            this.isCanPlay = false;
        }
    }

    private void playBarrage() {
        if (this.barrageChatModels != null) {
            if (this.barrageChatModels.size() == 0) {
                this.isCanPlay = true;
                return;
            } else if (this.currRoadIndex == 0) {
                this.myHandler.sendEmptyMessage(1);
            } else {
                this.myHandler.sendEmptyMessageDelayed(1, (long) ((int) this.roadDelayTimes[this.currRoadIndex - 1]));
            }
        }
        this.currRoadIndex++;
        if (this.currRoadIndex > 2) {
            this.currRoadIndex = 1;
        }
        playBarrage(this.barrageChatModels.removeFirst());
    }

    private void playBarrage(BarrageChatModel barrageChatModel) {
        BarrageItemModel barrageItemModel = new BarrageItemModel(this);
        barrageItemModel.barrageItemView = new BarrageItemView(this.context, barrageChatModel);
        barrageItemModel.barrageItemView.setVisibility(View.GONE);
        barrageItemModel.barrageItemView.onFinishInflate();
        barrageItemModel.barrageItemView.setOnClickListener(this);
        barrageItemModel.animEndPosition = (int) getAnimEndPosition(barrageItemModel, barrageChatModel.getContent());
        barrageItemModel.duration = getBarrageItemDuration((float) barrageItemModel.animEndPosition);
        barrageItemModel.topMargin = (2 - this.currRoadIndex) * dipToPx(42);
        playAnim(barrageItemModel);
    }

    private void playAnim(final BarrageItemModel barrageItemModel) {
        int right = (getRight() - getLeft()) - getPaddingLeft();
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.topMargin = barrageItemModel.topMargin;
        addView(barrageItemModel.barrageItemView, layoutParams);
        ObjectAnimator animator = getAnimator(barrageItemModel, right);
        animator.addListener(new AnimatorListenerAdapter() {
//            final /* synthetic */ KittyBarrageView BarrageItemModel;

            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                barrageItemModel.barrageItemView.setVisibility(View.VISIBLE);
            }

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                barrageItemModel.barrageItemView.clearAnimation();
//                this.BarrageItemModel.removeView(bVar.context);
                removeView(barrageItemModel.barrageItemView);
            }
        });
        animator.start();
        this.roadDelayTimes[this.currRoadIndex - 1] = (((float) barrageItemModel.animEndPosition) / (((float) right) + ((float) barrageItemModel.animEndPosition))) * ((float) barrageItemModel.duration);
    }

    private ObjectAnimator getAnimator(BarrageItemModel barrageItemModel, int startPosition) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(barrageItemModel.barrageItemView,
                "translationX",
                new float[]{(float) startPosition, (float) (-barrageItemModel.animEndPosition)});
        ofFloat.setDuration((long) barrageItemModel.duration);
        ofFloat.setInterpolator(new LinearInterpolator());
        return ofFloat;
    }

    private float getAnimEndPosition(BarrageItemModel barrageItemModel, String str) {
        this.e = (int) getContext().getResources().getDimension(R.dimen.barrage_item_minWidth);
        this.f = ((int) getContext().getResources().getDimension(R.dimen.barrage_item_height)) + dipToPx(20);
        try {
            Rect rect = new Rect();
            barrageItemModel.barrageItemView.getBarrageContentTv().getPaint().getTextBounds(str, 0, str.length(), rect);
            if (rect.width() + this.f < this.e) {
                return (float) this.e;
            }
            return (float) (rect.width() + this.f);
        } catch (Exception e) {
            return (float) this.e;
        }
    }

    private int getBarrageItemDuration(float animEndPosition) {
        if (animEndPosition >= ((float) getScreenWidth(getContext())) * 1.5f) {
            return (int) ((animEndPosition * 6.0f) + (Math.abs(2.0f - (animEndPosition / ((float) this.e))) * animEndPosition));
        }
        return (int) ((animEndPosition * 6.0f) + ((5.0f * animEndPosition) * Math.abs(2.0f - (animEndPosition / ((float) this.e)))));
    }

    public void clear() {
        if (this.barrageChatModels != null) {
            this.barrageChatModels.clear();
        }
    }

    @Override
    public void onClick(View view) {
//        NBSEventTraceEngine.onClickEventEnter(view, this);
//        if (this.i != null) {
//            this.i.onBarrageItemClick(view);
//        }
//        NBSEventTraceEngine.onClickEventExit();
    }

    public static int dipToPx(int i) {
        return Math.round(Resources.getSystem().getDisplayMetrics().density * ((float) i));
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
