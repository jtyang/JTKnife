package com.android.jtknife.modules.live;

import android.opengl.GLSurfaceView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.core.widgets.VerticalViewPager;
import com.elvishew.xlog.XLog;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import butterknife.Bind;

/**
 * 直播间
 * <p>
 * 问题：
 * 每次上下切换会导致GLSurfaceView的render重新执行初始化 不知道会不会有问题----glthread id已经不一样了
 * 如果数据更改了，如何更新上下切换适配器
 * 进房的时候能够无数循环
 * <p>
 * AUTHOR: yangjiantong
 * DATE: 2017/1/12
 */
public class LiveRoomActivity extends BaseActivity {

    @Bind(R.id.vertical_viewpager)
    VerticalViewPager mViewPager;

    private MyPagerAdapter mPagerAdapter;
    private RelativeLayout mRoomContainer;
    private FrameLayout mFragmentContainer;
    private GLSurfaceView mVideoView;

    private int isLiveStreaming = 1;
    private int mCurrentItem;

    private int mRoomId = -1;
    private boolean mInit = false;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_live_room;
    }

    @Override
    protected void onInitView() {
        mRoomContainer = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.view_room_container, null);
        mFragmentContainer = (FrameLayout) mRoomContainer.findViewById(R.id.fragment_container);
        mVideoView = (GLSurfaceView) mRoomContainer.findViewById(R.id.texture_view);
        mVideoView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                XLog.e("onSurfaceCreated===========>>>>>>");
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                XLog.e("onSurfaceChanged===========>>>>>>");
            }

            @Override
            public void onDrawFrame(GL10 gl) {
//                XLog.e("onSurfaceCreated===========>>>>>>");
            }
        });


        mPagerAdapter = new MyPagerAdapter();
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                XLog.e("mCurrentId == " + position + ", positionOffset == " + positionOffset +
//                        ", positionOffsetPixels == " + positionOffsetPixels);
                mCurrentItem = position;
            }
//            @Override
//            public void onPageSelected(int position) {
//                mCurrentItem = position;
//            }
        });

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                ViewGroup viewGroup = (ViewGroup) page;
                XLog.e("page.id == " + page.getId() + ", position == " + position + "，currentItem=" + mCurrentItem);

                if ((position < 0 && viewGroup.getId() != mCurrentItem)) {
                    View roomContainer = viewGroup.findViewById(R.id.room_container);
                    if (roomContainer != null && roomContainer.getParent() != null && roomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (roomContainer.getParent())).removeView(roomContainer);
                    }
                }
                // 满足此种条件，表明需要加载直播视频，以及聊天室了
                if (viewGroup.getId() == mCurrentItem && position == 0 && mCurrentItem != mRoomId) {
                    if (mRoomContainer.getParent() != null && mRoomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (mRoomContainer.getParent())).removeView(mRoomContainer);
                    }
                    loadVideoAndChatRoom(viewGroup, mCurrentItem);
                }
            }
        });
        mViewPager.setAdapter(mPagerAdapter);
    }

    private void loadVideoAndChatRoom(ViewGroup viewGroup, int currentItem) {
        //聊天室的fragment只加载一次，以后复用
        if (!mInit) {
//            mFragmentManager.beginTransaction().add(mFragmentContainer.getId(), mRoomFragment).commitAllowingStateLoss();
            mInit = true;
        }
//        loadVideo(currentItem);
        viewGroup.addView(mRoomContainer);
//        mRoomId = currentItem;
    }


    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_room_item, null);
            view.setId(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(container.findViewById(position));
        }
    }
}
