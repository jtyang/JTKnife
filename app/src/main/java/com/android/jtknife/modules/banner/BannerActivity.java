package com.android.jtknife.modules.banner;

import android.net.Uri;
import android.support.annotation.IdRes;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.core.common.imageloader.FrescoImageView;
import com.android.jtknife.core.common.task.WeakHandler;
import com.android.jtknife.core.utils.DeviceUtils;
import com.android.jtknife.core.widgets.banner.Banner;
import com.android.jtknife.modules.banner.loader.FrescoImageLoader;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

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
    @Bind(R.id.fresco_image)
    FrescoImageView frescoImageView;

    WeakHandler mHandler = new WeakHandler();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_banner;
    }

    @Override
    protected void onInitView() {
        banner.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                DeviceUtils.getScreenHeight(mContext) / 4));
        banner.setImages(getImageUrls())
                .setImageLoader(new FrescoImageLoader())
                .setOnBannerListener(new Banner.OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Toast.makeText(getApplicationContext(), "你点击了：" + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
        //todo refresh: banner.update(arrayList);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (banner != null){
                    List<String> list = new ArrayList<>();
                    list.add("https://www.gstatic.com/webp/gallery/1.sm.jpg");
                    banner.update(list);
                }
            }
        }, 3000);

        Uri uri = Uri.parse("https://www.gstatic.com/webp/gallery/1.sm.jpg");
        frescoImageView.setDefaultAndErrorImageResId(R.drawable.meinv).loadBlurImage(uri.toString());
//        findViewAndLoadUri(R.id.fresco_image, "https://www.gstatic.com/webp/gallery/1.sm.jpg");

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


    private SimpleDraweeView findAndPrepare(@IdRes int viewId) {
        SimpleDraweeView view = (SimpleDraweeView) findViewById(viewId);
        view.getHierarchy().setProgressBarImage(new ProgressBarDrawable());
        return view;
    }

    private SimpleDraweeView findViewAndLoadUri(@IdRes int viewId, String uri) {
        SimpleDraweeView view = findAndPrepare(viewId);
        view.setImageURI(Uri.parse(uri));
        return view;
    }

}
