package com.android.jtknife.modules.banner;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.core.common.imageloader.FrescoImageView;
import com.android.jtknife.core.common.task.WeakHandler;
import com.android.jtknife.core.utils.DeviceUtils;
import com.android.jtknife.core.widgets.banner_v2.BannerLayout;
import com.android.jtknife.core.widgets.banner_v2.holder.BannerHolderCreator;
import com.android.jtknife.core.widgets.banner_v2.holder.BannerViewHolder;
import com.bumptech.glide.Glide;
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
public class BannerActivityV2 extends BaseActivity {

    @Bind(R.id.banner)
    BannerLayout banner;
    @Bind(R.id.fresco_image)
    FrescoImageView frescoImageView;

    WeakHandler mHandler = new WeakHandler();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_banner_v2;
    }

    @Override
    protected void onInitView() {
        banner.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                DeviceUtils.getScreenHeight(mContext) / 4));
//        banner.setImages(getImageUrls())
//                .setImageLoader(new FrescoImageLoader())
//                .setOnBannerListener(new Banner.OnBannerListener() {
//                    @Override
//                    public void OnBannerClick(int position) {
//                        Toast.makeText(getApplicationContext(), "你点击了：" + position, Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .start();
        List<String> datas = getImageUrls();
        banner.setBannerPageClickListener(new BannerLayout.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "你点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });
        banner.updateBanner(datas, new BannerHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new MyBannerHolder();
            }
        });
        banner.startBannerLoop();
        //todo refresh: banner.update(arrayList);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (banner != null) {
                    List<String> list = new ArrayList<>();
                    list.add("https://www.gstatic.com/webp/gallery/1.sm.jpg");
//                    banner.update(list);
                    banner.updateBanner(list, new BannerHolderCreator<BannerViewHolder>() {
                        @Override
                        public BannerViewHolder createViewHolder() {
                            return new MyBannerHolder();
                        }
                    });
                    banner.startBannerLoop();
                }
            }
        }, 9 * 1000);

        Uri uri = Uri.parse("https://www.gstatic.com/webp/gallery/1.sm.jpg");
        frescoImageView.setDefaultAndErrorImageResId(R.drawable.meinv).loadBlurImage(uri.toString());
//        findViewAndLoadUri(R.id.fresco_image, "https://www.gstatic.com/webp/gallery/1.sm.jpg");

    }

    private class MyBannerHolder implements BannerViewHolder<String> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            Glide.with(mContext).load(data).into(mImageView);
        }
    }

    private List<String> getImageUrls() {
        List<String> list = new ArrayList<>();
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        return list;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startBannerLoop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopBannerLoop();
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
