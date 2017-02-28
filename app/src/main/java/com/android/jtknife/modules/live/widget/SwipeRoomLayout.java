package com.android.jtknife.modules.live.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.android.jtknife.core.common.imageloader.FrescoImageView;
import com.android.jtknife.core.utils.DeviceUtils;

import static android.view.MotionEvent.INVALID_POINTER_ID;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/24
 */
public class SwipeRoomLayout extends FrameLayout {

    private FrescoImageView swipeImageView1;
    private FrescoImageView swipeImageView2;


    private int screenWidth;
    private int screenHeight;
    //触摸事件手指down的ｘ轴坐标
    private float startX;
    //触摸时间down的ｙ轴坐标
    private float startY;
    //横向的左右滑动的menu是否显示状态
    private boolean isMenuShow = true;

    //ｘ轴触发隐藏/显示菜单的最小距离
    private static final float minXScrollDistance = (float) DeviceUtils.getDensity(40);
    //ｙ轴触发显示切换房间view的手指最小移动距离
    private static final float MIN_EFFECT_MOVE_DISTANCE_Y = (float) DeviceUtils.getDensity(15);//30
    // The ‘active pointer’ is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;

    //当前手势方向
    private int gestureOrientation = GestureOrientation.NONE;

    //手势方向
    public interface GestureOrientation {
        int NONE = 0;
        int Y = 1;//y轴(上下)
        int RIGHT = 2;//向右
        int LEFT = 3;//向左
    }

    public SwipeRoomLayout(@NonNull Context context) {
        this(context, null);
    }

    public SwipeRoomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRoomLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = getMeasuredWidth();
        screenHeight = getMeasuredHeight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
//                Logger.e("ACTION_DOWN:" + event.getActionIndex());
                startX = event.getRawX();
                startY = event.getRawY();
                // Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(event, pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                // Find the index of the active pointer and fetch its position
                final int pointerIndexMove =
                        MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerIndexMove);
                if (pointerId != mActivePointerId) break;
                if (event.getPointerCount() < 1) break;
                float rawX = event.getRawX() - startX;
                float rawY = event.getRawY() - startY;
                if (gestureOrientation == GestureOrientation.NONE) {
                    //处理手势滑动方向
                    updateGestureOrientation(rawX, rawY);
                }
//                if (gestureOrientation == GestureOrientation.Y && isAbleSwitchRoom()) {
                if(gestureOrientation == GestureOrientation.Y){

                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;

        }
        return true;
    }


    /**
     * 根据滑动距离判断手势方向
     *
     * @param rawX
     * @param rawY
     */
    private void updateGestureOrientation(float rawX, float rawY) {
        if (Math.abs(rawY) >= MIN_EFFECT_MOVE_DISTANCE_Y) {
            //y轴滑动了一定距离,触发上下切换房间
            gestureOrientation = GestureOrientation.Y;
        } else if (rawX >= MIN_EFFECT_MOVE_DISTANCE_Y && this.isMenuShow) {
            //往右滑动
            gestureOrientation = GestureOrientation.RIGHT;
        } else if (rawX <= (-MIN_EFFECT_MOVE_DISTANCE_Y) && !this.isMenuShow) {
            //往左滑动
            gestureOrientation = GestureOrientation.LEFT;
        }
    }
}
