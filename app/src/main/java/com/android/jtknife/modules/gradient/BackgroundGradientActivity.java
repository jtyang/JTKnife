package com.android.jtknife.modules.gradient;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.common.gradient.RevelyGradient;
import com.android.jtknife.common.gradient.drawables.Gradient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/11/4
 */
public class BackgroundGradientActivity extends BaseActivity {

    @Bind(R.id.container)
    ViewPager viewPager;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    public static List<Gradient> GRADIENT_DATAS = new ArrayList<>();
    
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_background_gradient;
    }

    @Override
    protected void onInitView() {
        GRADIENT_DATAS.clear();
        GRADIENT_DATAS.add(new Gradient(0f, new String[]{"#17EA19", "#6078EA"}));
        GRADIENT_DATAS.add(new Gradient(-19f, new String[]{"#21D4FD", "#B721FF"}));
        GRADIENT_DATAS.add(new Gradient(90f, new String[]{"#FEE140", "#FA709A"}));
        GRADIENT_DATAS.add(new Gradient(-45f, new String[]{"#FA8BFF", "#2BD2FF", "#2BFF88"}));
        GRADIENT_DATAS.add(new Gradient(-43f, new String[]{"#4158D0", "#C850C0", "#FFCC70"}));
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mSectionsPagerAdapter);
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BackgroundGradientFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return GRADIENT_DATAS.size();
        }
    }

    @SuppressLint("ValidFragment")
    static class BackgroundGradientFragment extends Fragment {

        private static final String ARG_POSITION = "position";

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_background_gradient, container, false);
            Gradient gradientData = BackgroundGradientActivity.GRADIENT_DATAS.get(getArguments().getInt(ARG_POSITION));

            int[] colors = new int[gradientData.getColorStr().length];
            for (int i = 0; i < gradientData.getColorStr().length; i++) {
                colors[i] = Color.parseColor(gradientData.getColorStr()[i]);
            }
            RevelyGradient.linear()
                    .angle(gradientData.getAngle())
                    .colors(colors)
                    .onBackgroundOf(rootView.findViewById(R.id.constraintLayout));
            SpannableStringBuilder colorsString = new SpannableStringBuilder();
            for (String color : gradientData.getColorStr()) {
                colorsString.append(color);
                colorsString.setSpan(new ForegroundColorSpan(Color.parseColor(color)), colorsString.toString().length() - color.length(), colorsString.toString().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                colorsString.append(" ");
            }

            TextView textView = (TextView) rootView.findViewById(R.id.colors);
            textView.setText(colorsString);
            return rootView;
        }

        public static BackgroundGradientFragment newInstance(int position) {
            BackgroundGradientFragment fragment = new BackgroundGradientFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_POSITION, position);
            fragment.setArguments(args);
            return fragment;
        }
    }

}
