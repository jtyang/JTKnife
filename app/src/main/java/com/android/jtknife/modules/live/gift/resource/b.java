package com.android.jtknife.modules.live.gift.resource;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout.LayoutParams;

import com.android.jtknife.R;
import com.android.jtknife.modules.live.gift.model.anim.AnimFrame;
import com.android.jtknife.modules.live.gift.model.anim.AnimItem;
import com.android.jtknife.modules.live.gift.model.anim.GiftAnimEffect;
import com.android.jtknife.modules.live.gift.model.anim.Size;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/1
 */
public class b {

    private static final String a = b.class.getSimpleName();

    public static AnimatorSet a(File file, GiftAnimEffect giftAnimEffect, ViewGroup viewGroup) {
        AnimatorSet animatorSet = new AnimatorSet();
        Collection arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (AnimItem animItem : giftAnimEffect.getAnimItem()) {
            View view = null;
            switch (animItem.getType()) {
                case 1:
                    view = new SimpleDraweeView(viewGroup.getContext());
                    switch (animItem.getScaleType()) {
                        case 2:
                            ((GenericDraweeHierarchy) ((SimpleDraweeView) view).getHierarchy()).setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
                            break;
                    }
                    if (a((SimpleDraweeView) view, new File(file, animItem.getName()))) {
                        arrayList2.add((SimpleDraweeView) view);
                        break;
                    }
                    break;
                case 2:
                    view = View.inflate(viewGroup.getContext(), R.layout.layout_gift_sender, null);
                    break;
            }
            Size size = animItem.getSize();
            viewGroup.addView(view, new LayoutParams(a(viewGroup.getResources(), size.getWidth()), a(viewGroup.getResources(), size.getHeight()), size.getGravity()));
            AnimatorSet animatorSet2 = new AnimatorSet();
            List arrayList3 = new ArrayList();
            for (AnimFrame end : animItem.getAnimFrameSet()) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationX", new float[]{end.getStart().getX() * ((float) a(view.getContext())), end.getEnd().getX() * ((float) a(view.getContext()))});
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "translationY", new float[]{end.getStart().getY() * ((float) b(view.getContext())), end.getEnd().getY() * ((float) b(view.getContext()))});
                ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "scaleX", new float[]{end.getStart().getScale(), end.getEnd().getScale()});
                ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view, "scaleY", new float[]{end.getStart().getScale(), end.getEnd().getScale()});
                AnimatorSet animatorSet3 = new AnimatorSet();
                animatorSet3.playTogether(new Animator[]{ofFloat, ofFloat2, ofFloat3, ofFloat4});
                animatorSet3.setDuration((long) (end.getDuration() * 1000.0f));
                arrayList3.add(animatorSet3);
            }
            animatorSet2.playSequentially(arrayList3);
            arrayList.add(animatorSet2);
        }
        animatorSet.playTogether(arrayList);
        return animatorSet;
    }

    public static boolean a(SimpleDraweeView simpleDraweeView, File file) {
        if (c(file.getName())) {
            PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder();
            builder.setOldController(simpleDraweeView.getController());
            builder.setControllerListener(new BaseControllerListener<ImageInfo>() {
                public void onFinalImageSet(String str, ImageInfo obj, Animatable animatable) {
                    a(str, obj, animatable);
                }

                public void a(String str, ImageInfo imageInfo, Animatable animatable) {
                    if (animatable != null) {
                        animatable.start();
                    }
                }
            });
            builder.setImageRequest(ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(file)).build());
            simpleDraweeView.setController(builder.build());
            return true;
        }
        simpleDraweeView.setImageURI(Uri.fromFile(file));
        return false;
    }

    public static float a(Resources resources, float f) {
        return (resources.getDisplayMetrics().density * f) + 0.5f;
    }

    public static int a(Resources resources, int i) {
        return i < 0 ? i : (int) a(resources, (float) i);
    }

    public static int a(int i) {
        return Math.round(Resources.getSystem().getDisplayMetrics().density * ((float) i));
    }

    public static int a(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int b(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static boolean c(String str) {
        return str.endsWith(".webp");
    }
}
