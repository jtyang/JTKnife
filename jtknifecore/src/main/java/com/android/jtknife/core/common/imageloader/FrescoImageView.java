package com.android.jtknife.core.common.imageloader;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;


/**
 * 对Fresco中的SimpleDraweeView进一步封装
 * <p>
 * doc:https://www.fresco-cn.org/docs/index.html
 * <p>
 * AUTHOR: yangjiantong
 * DATE: 2017/2/18
 * <p>
 * useage
 * FrescoImageView.setImageURI()
 * <p>
 * <com.facebook.drawee.view.SimpleDraweeView
 * android:id="@+id/my_image_view"
 * android:layout_width="20dp"
 * android:layout_height="20dp"
 * fresco:fadeDuration="300"
 * fresco:actualImageScaleType="focusCrop"
 * fresco:placeholderImage="@color/wait_color"
 * fresco:placeholderImageScaleType="fitCenter"
 * fresco:failureImage="@drawable/error"
 * fresco:failureImageScaleType="centerInside"
 * fresco:retryImage="@drawable/retrying"
 * fresco:retryImageScaleType="centerCrop"
 * fresco:progressBarImage="@drawable/progress_bar"
 * fresco:progressBarImageScaleType="centerInside"
 * fresco:progressBarAutoRotateInterval="1000"
 * fresco:backgroundImage="@color/blue"
 * fresco:overlayImage="@drawable/watermark"
 * fresco:pressedStateOverlayImage="@color/red"
 * fresco:roundAsCircle="false"
 * fresco:roundedCornerRadius="1dp"
 * fresco:roundTopLeft="true"
 * fresco:roundTopRight="false"
 * fresco:roundBottomLeft="false"
 * fresco:roundBottomRight="true"
 * fresco:roundWithOverlayColor="@color/corner_color"
 * fresco:roundingBorderWidth="2dp"
 * fresco:roundingBorderColor="@color/border_color"
 * />
 * <p>
 * Drawees 不支持 wrap_content 属性
 * <p>
 * 只有希望显示固定的宽高比时，可以使用wrap_content.
 * <com.facebook.drawee.view.SimpleDraweeView
 * android:id="@+id/my_image_view"
 * android:layout_width="20dp"
 * android:layout_height="wrap_content"
 * fresco:viewAspectRatio="1.33">
 * 也可以在代码中指定显示比例：mSimpleDraweeView.setAspectRatio(1.33f);
 * <p>
 * ////////////
 * 暂停和恢复：
 * Fresco.getImagePipeline().resume() / Fresco.getImagePipeline().pause()
 * <p>
 * ///////////
 * 预加载
 * 使用预加载时，要预加载到内存并解码的，要注意ResizeOptions，和照片旋转照成的长宽对调。
 * 1)预缓存到本地文件，但不加载到内存，自动忽略ResizeOptions：
 * Fresco.getImagePipeline().prefetchToDiskCache(imageRequest, null)
 * <p>
 * 2)预加载到内存并解码（不需要立即拿到结果的），受ResizeOptions影响：
 * Fresco.getImagePipeline().prefetchToBitmapCache(imageRequest, null)
 * <p>
 * 3)加载一张图片，并缓存到内存，返回解码的结果，受ResizeOptions影响：
 * Fresco.getImagePipeline().fetchDecodedImage(imageRequest, null)
 * 但要注意，返回的DataSource是不能直接拿到结果，需要subscribe DataSource.
 * ////////////
 */
public class FrescoImageView extends SimpleDraweeView {

    private String url;
//    ControllerListener controllerListener;

    public FrescoImageView(Context context) {
        this(context, null);
    }

    public FrescoImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrescoImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }

    public FrescoImageView loadImageResId(@DrawableRes int resourceId) {
        setImageURI(UriUtil.getUriForResourceId(resourceId));
        return this;
    }

    public FrescoImageView setIsAsCircle(boolean isAsCircle) {
        GenericDraweeHierarchy genericDraweeHierarchy = (GenericDraweeHierarchy) getHierarchy();
        if (genericDraweeHierarchy != null) {
            RoundingParams roundingParams = genericDraweeHierarchy.getRoundingParams();
            if (roundingParams != null) {
                roundingParams.setRoundAsCircle(isAsCircle);
            } else if (isAsCircle) {
                roundingParams = new RoundingParams();
                roundingParams.setRoundAsCircle(isAsCircle);
                genericDraweeHierarchy.setRoundingParams(roundingParams);
            }
        }
        return this;
    }

    ///--------------
    public FrescoImageView setDefaultAndErrorImageResId(@DrawableRes int resId) {
        setDefaultImageResId(resId);
        setErrorImageResId(resId);
        return this;
    }

    public FrescoImageView setDefaultImageResId(@DrawableRes int resId) {
        GenericDraweeHierarchy genericDraweeHierarchy = (GenericDraweeHierarchy) getHierarchy();
        if (genericDraweeHierarchy != null) {
            genericDraweeHierarchy.setPlaceholderImage(getContext().getResources().getDrawable(resId), ScalingUtils.ScaleType.CENTER_CROP);
        }
        return this;
    }

    public FrescoImageView setErrorImageResId(@DrawableRes int resId) {
        GenericDraweeHierarchy genericDraweeHierarchy = (GenericDraweeHierarchy) getHierarchy();
        if (genericDraweeHierarchy != null) {
            genericDraweeHierarchy.setFailureImage(getContext().getResources().getDrawable(resId), ScalingUtils.ScaleType.CENTER_CROP);
        }
        return this;
    }

    ///-----------
    public FrescoImageView loadAnimatedRes(int animatedRes) {
        return loadAnimatedImageUri(getResUri(animatedRes));
    }

    public FrescoImageView loadAnimatedImageUrl(String uri) {
        return loadAnimatedImageUri(Uri.parse(uri));
    }

    public FrescoImageView loadAnimatedImageUri(Uri uri) {
        DraweeController animatedController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(uri)
                .build();
        setController(animatedController);
        return this;
    }

    /**
     * 手动开启渐进式加载
     * 在开始加载之后，图会从模糊到清晰渐渐呈现。你可以设置一个清晰度标准，在未达到这个清晰度之前，会一直显示占位图
     *
     * @param url
     */
    public void loadProgressJpg(String url) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(
                Uri.parse(url))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setRetainImageOnFailure(true)
                .build();
        setController(controller);
    }

    /**
     * 下载监听
     * ControllerListener listener = new BaseControllerListener() {...}
     *
     * @param url
     * @param listener
     */
    public void loadImageUrl(String url, ControllerListener listener) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
//                .setTapToRetryEnabled(true)
                .setOldController(getController())
                .setControllerListener(listener)
                .build();
        setController(controller);
    }

    ///-----------------
    public void loadImageUrlByLayoutParams(String url) {
        loadImageUrl(url, getLayoutParams().width, getLayoutParams().height);
    }

    public void loadImageUrl(String url, int width, int height) {
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setResizeOptions(
                        new ResizeOptions(width, height));
//        imageRequestBuilder.setPostprocessor(postprocessor);
        // Create the Builder
        PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequestBuilder.build());
        builder.setOldController(getController());
        setController(builder.build());
    }

    ///---
    public void loadBlurImage(String url) {
        ImageRequest request = ImageRequestBuilder
//                        .newBuilderWithResourceId(R.drawable.demo)
                .newBuilderWithSource(Uri.parse(url))
                .setPostprocessor(new BlurPostprocessor(getContext()))
                .build();

        PipelineDraweeController controller =
                (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(getController())
                        .build();
        setController(controller);
    }

    private Uri getResUri(@DrawableRes int resId) {
        return Uri.parse("res:///" + resId);
    }

}
