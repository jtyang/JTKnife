package com.android.jtknife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.core.common.di.InjectBean;
import com.android.jtknife.core.views.ConfirmDialog;
import com.android.jtknife.model.UserModel;
import com.android.jtknife.model.entity.UserInfo;
import com.android.jtknife.modules.banner.BannerActivityV2;
import com.android.jtknife.modules.feature.FeatureSampleActivity;
import com.android.jtknife.modules.gradient.TextGradientActivity;
import com.android.jtknife.modules.live.WatchActivity;
import com.android.jtknife.modules.rxjava.RxJavaDemoActivity;
import com.android.jtknife.modules.stackblur.StackBlurActivity;
import com.android.jtknife.modules.testlist.SwipeRefreshRecyclerViewSampleActivity;
import com.android.jtknife.modules.topactivity.JTService;
import com.elvishew.xlog.XLog;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.feature_btn)
    Button featureButton;
    @Bind(R.id.swiperefresh_btn)
    Button swipeRefreshButton;
    @Bind(R.id.liveroom_btn)
    Button liveRoomButton;
    @Bind(R.id.stackblur_btn)
    Button stackBlurButton;
    @Bind(R.id.banner_btn)
    Button bannerBtn;
    @Bind(R.id.rxjava_btn)
    Button rxjavaBtn;
    @Bind(R.id.text_gradient_btn)
    Button textGradientBtn;

    @InjectBean
    UserModel userModel;//InjectBean只能注解对象的实现无参数的构造函数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserInfo userInfo = userModel.login("ttt", "pwd");
        XLog.i("MainActivity login result:" + userInfo.toString());
//        startService();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {
        featureButton.setOnClickListener(this);
        swipeRefreshButton.setOnClickListener(this);
        liveRoomButton.setOnClickListener(this);
        stackBlurButton.setOnClickListener(this);
        bannerBtn.setOnClickListener(this);
        rxjavaBtn.setOnClickListener(this);
        textGradientBtn.setOnClickListener(this);
    }

    private void startService() {
        Intent intent = new Intent(mContext, JTService.class);
        startService(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feature_btn:
                startActivity(new Intent(mContext, FeatureSampleActivity.class));
                break;
            case R.id.swiperefresh_btn:
                startActivity(new Intent(mContext, SwipeRefreshRecyclerViewSampleActivity.class));
                break;
            case R.id.liveroom_btn:
                startActivity(new Intent(mContext, WatchActivity.class));
                break;
            case R.id.stackblur_btn:
                startActivity(new Intent(mContext, StackBlurActivity.class));
                break;
            case R.id.banner_btn:
                startActivity(new Intent(mContext, BannerActivityV2.class));
                break;
            case R.id.rxjava_btn:
                startActivity(new Intent(mContext, RxJavaDemoActivity.class));
                break;
            case R.id.text_gradient_btn:
                startActivity(new Intent(mContext, TextGradientActivity.class));
                break;
            default:
                break;

        }
    }

    @Override
    public void onBackPressed() {
//        testConfirmDialog();
        finish();
    }

    @Override
    public ActivityAnimType getActivityAnimType() {
        return ActivityAnimType.RIGHT;
    }

    private void testConfirmDialog() {
        ConfirmDialog confirmDialog = new ConfirmDialog(this, "logout?", "Are you sure logout?");
        confirmDialog.show();
    }
}
