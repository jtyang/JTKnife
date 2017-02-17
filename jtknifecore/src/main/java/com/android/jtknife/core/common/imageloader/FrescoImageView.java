package com.android.jtknife.core.common.imageloader;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 对Fresco中的SimpleDraweeView进一步封装
 * AUTHOR: yangjiantong
 * DATE: 2017/2/18
 *
 * useage
 * FrescoImageView.setImageUri()
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
    }

    private void init(Context context, AttributeSet attrs) {

    }

    public void setIsAsCircle(boolean isAsCircle) {
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
    }

    public void setDefaultImageResId(int resId) {
        GenericDraweeHierarchy genericDraweeHierarchy = (GenericDraweeHierarchy) getHierarchy();
        if (genericDraweeHierarchy != null) {
            genericDraweeHierarchy.setPlaceholderImage(getContext().getResources().getDrawable(resId), ScalingUtils.ScaleType.CENTER_CROP);
        }
    }

    public void setErrorImageResId(int resId) {
        GenericDraweeHierarchy genericDraweeHierarchy = (GenericDraweeHierarchy) getHierarchy();
        if (genericDraweeHierarchy != null) {
            genericDraweeHierarchy.setFailureImage(getContext().getResources().getDrawable(resId), ScalingUtils.ScaleType.CENTER_CROP);
        }
    }

    public void setAnimRes(int i) {

    }
}
