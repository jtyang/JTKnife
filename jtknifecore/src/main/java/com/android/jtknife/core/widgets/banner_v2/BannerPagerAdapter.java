package com.android.jtknife.core.widgets.banner_v2;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.android.jtknife.core.widgets.banner_v2.holder.BannerHolderCreator;
import com.android.jtknife.core.widgets.banner_v2.holder.BannerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Banner Adapter
 * AUTHOR: yangjiantong
 * DATE: 2017/10/21
 */
public class BannerPagerAdapter<T> extends PagerAdapter {

    private List<T> mDatas;
    private BannerHolderCreator mBannerHolderCreator;
    private ViewPager mViewPager;
    private boolean canLoop;
    private BannerLayout.BannerPageClickListener mPageClickListener;
    private final int mLooperCountFactor = 2 * 5 * 6 * 7 * 8 * 9;//保证能被2,3,4,5,6,7,8,9,10整除，但5x6x7x8x9已经能保证也被2,3,4整除，乘2是由于开始居中显示

    public BannerPagerAdapter(List<T> datas, BannerHolderCreator bannerHolderCreator, boolean canLoop) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        for (T t : datas) {
            mDatas.add(t);
        }
        mBannerHolderCreator = bannerHolderCreator;
        this.canLoop = canLoop;
    }

    public void setPageClickListener(BannerLayout.BannerPageClickListener pageClickListener) {
        mPageClickListener = pageClickListener;
    }

    /**
     * 初始化Adapter和设置当前选中的Item
     *
     * @param viewPager
     */
    public void setUpViewViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.setAdapter(this);
        mViewPager.getAdapter().notifyDataSetChanged();
        int currentItem = canLoop ? getStartSelectItem() : 0;
        //设置当前选中的Item
        mViewPager.setCurrentItem(currentItem);
    }

    private int getStartSelectItem() {
        // 我们设置当前选中的位置为Integer.MAX_VALUE / 2,这样开始就能往左滑动
        // 但是要保证这个值与getRealPosition 的 余数为0，因为要从第一页开始显示
        if (getRealCount() == 0) return 0;
        int currentItem = getRealCount() * mLooperCountFactor / 2;
        if (currentItem % getRealCount() == 0) {
            return currentItem;
        } else {
            int count = getRealCount();
            if (count > 0) {
                currentItem = getCount() / count + count;
            }
        }
        // 直到找到从0开始的位置
//        while (currentItem % getRealCount() != 0) {
//            currentItem++;
//        }
        return currentItem;
    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
    }

    @Override
    public int getCount() {
        // 如果getCount 的返回值为Integer.MAX_VALUE 的话，那么在setCurrentItem的时候会ANR(除了在onCreate 调用之外)
        return canLoop ? getRealCount() * mLooperCountFactor : getRealCount();//ViewPager返回int 最大值
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = getView(position, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        // 轮播模式才执行
        if (canLoop) {
            int position = mViewPager.getCurrentItem();
            if (position == getCount() - 1) {
                position = 0;
                setCurrentItem(position);
            }
        }
    }

    private void setCurrentItem(int position) {
        try {
            mViewPager.setCurrentItem(position, false);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取真实的Count
     *
     * @return
     */
    private int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    /**
     * @param position
     * @param container
     * @return
     */
    private View getView(int position, ViewGroup container) {
        if (getRealCount() == 0) return null;
        final int realPosition = position % getRealCount();
        BannerViewHolder holder = null;
        // create holder
        holder = mBannerHolderCreator.createViewHolder();

        if (holder == null) {
            throw new RuntimeException("can not return a null holder");
        }
        // create View
        View view = holder.createView(container.getContext());

        if (mDatas != null && mDatas.size() > 0) {
            holder.onBind(container.getContext(), realPosition, mDatas.get(realPosition));
        }

        // 添加点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPageClickListener != null) {
                    mPageClickListener.onPageClick(v, realPosition);
                }
            }
        });
        return view;
    }

}
