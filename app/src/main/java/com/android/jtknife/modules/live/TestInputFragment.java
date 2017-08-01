package com.android.jtknife.modules.live;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseFragment;
import com.android.jtknife.core.utils.DeviceUtils;
import com.android.jtknife.modules.live.widget.FixSizeLayout;
import com.elvishew.xlog.XLog;

import butterknife.Bind;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/8/1
 */
public class TestInputFragment extends BaseFragment {

    @Bind(R.id.fix_layout)
    FixSizeLayout fixSizeLayout;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_testinput;
    }

    @Override
    protected void onInitView() {
        XLog.e("TestInputFragment ==================== " + DeviceUtils.getScreenWidth(getActivity()));
        fixSizeLayout.setFixSize(DeviceUtils.getScreenWidth(getActivity()), DeviceUtils.getScreenHeight(getActivity()));
    }

}
