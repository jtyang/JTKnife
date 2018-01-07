package com.android.jtknife.modules.mvp;

import com.android.jtknife.core.app.mvp.IBaseView;
import com.android.jtknife.modules.mvp.bean.UserInfo;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2018/1/7
 */
public interface ILoginView extends IBaseView {

    void showUserInfo(UserInfo userInfo);
}
