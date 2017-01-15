package com.android.jtknife.modules.stackblur;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.core.common.stackblur.NativeBlurProcess;
import com.android.jtknife.core.utils.BitmapUtils;

import butterknife.Bind;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/1/13
 */
public class StackBlurActivity extends BaseActivity {

    @Bind(R.id.blur_img)
    ImageView blurImg;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_stackblur;
    }

    @Override
    protected void onInitView() {
        Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(getResources(),R.mipmap.ic_launcher,50,50);
        blurImg.setImageBitmap(new NativeBlurProcess().blur(bitmap,25));
    }
}
