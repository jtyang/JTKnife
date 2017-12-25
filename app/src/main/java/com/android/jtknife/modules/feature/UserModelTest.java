package com.android.jtknife.modules.feature;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/25
 */
public class UserModelTest extends ViewModel{

    public  MutableLiveData<String> testLD = new MutableLiveData<>();

}
