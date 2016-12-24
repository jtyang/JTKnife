package com.android.jtknife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.core.common.di.InjectBean;
import com.android.jtknife.core.common.logger.Logger;
import com.android.jtknife.core.views.ConfirmDialog;
import com.android.jtknife.model.UserModel;
import com.android.jtknife.model.entity.UserInfo;
import com.android.jtknife.modules.feature.FeatureSampleActivity;
import com.android.jtknife.modules.testlist.SwipeRefreshRecyclerViewSampleActivity;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.feature_btn)
    Button featureButton;
    @Bind(R.id.swiperefresh_btn)
    Button swipeRefreshButton;

    @InjectBean
    UserModel userModel;//InjectBean只能注解对象的实现无参数的构造函数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserInfo userInfo = userModel.login("ttt", "pwd");
        Logger.i("MainActivity login result:" + userInfo.toString());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {
        featureButton.setOnClickListener(this);
        swipeRefreshButton.setOnClickListener(this);
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
            default:
                break;

        }
    }

    @Override
    public void onBackPressed() {
        ConfirmDialog confirmDialog = new ConfirmDialog(this, "logout?", "Are you sure logout?");
        confirmDialog.show();
    }
}
