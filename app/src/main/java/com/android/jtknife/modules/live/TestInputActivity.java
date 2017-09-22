package com.android.jtknife.modules.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/8/1
 */
public class TestInputActivity extends BaseActivity {

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;

    private List<Fragment> fragments = new ArrayList();
//    @Bind(R.id.fix_layout)
//    FixSizeLayout fixSizeLayout;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_testinput;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onInitView() {
//        fixSizeLayout.setFixSize(DeviceUtils.getScreenWidth(mContext), DeviceUtils.getScreenHeight(mContext));
        fragments.add(new TestInputFragment());
        fragments.add(new TestInputFragment());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "Tab" + position;
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

}
