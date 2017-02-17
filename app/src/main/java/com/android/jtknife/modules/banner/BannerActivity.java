package com.android.jtknife.modules.banner;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.core.utils.DeviceUtils;
import com.android.jtknife.core.widgets.banner.Banner;
import com.android.jtknife.modules.banner.loader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/16
 */
public class BannerActivity extends BaseActivity {

    @Bind(R.id.banner)
    Banner banner;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_banner;
    }

    @Override
    protected void onInitView() {
        banner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                DeviceUtils.getScreenHeight(mContext) / 4));
        banner.setImages(getImageUrls())
                .setImageLoader(new GlideImageLoader())
                .setOnBannerClickListener(new Banner.OnBannerClickListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Toast.makeText(getApplicationContext(), "你点击了：" + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
        //todo refresh: banner.update(arrayList);
    }

    private List<String> getImageUrls() {
        List<String> list = new ArrayList<>();
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        return list;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }


}
