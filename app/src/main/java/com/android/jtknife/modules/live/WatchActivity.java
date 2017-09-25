package com.android.jtknife.modules.live;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.core.common.task.WeakHandler;
import com.android.jtknife.widgets.barrage.BarrageChatModel;
import com.android.jtknife.widgets.barrage.BarrageHolder;
import com.android.jtknife.widgets.barrage.BarrageView;
import com.android.jtknife.widgets.barrage.KittyBarrageView;
import com.android.jtknife.widgets.barrage.NormalTextBarrageHolder;
import com.elvishew.xlog.XLog;

import butterknife.Bind;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/6
 */
public class WatchActivity extends BaseActivity {

    @Bind(R.id.input_say_text)
    TextView inputSayTextView;
    @Bind(R.id.bottom_input_layout)
    RoomEditChatView roomEditChatView;
    @Bind(R.id.barrage_screen)
    BarrageView barrageView;
    @Bind(R.id.kitty_barrageview)
    KittyBarrageView kittyBarrageView;

    private WeakHandler mHandler = new WeakHandler();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_watch;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onInitView() {
        roomEditChatView.setF(this);
        inputSayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomEditChatView.setInputVisible();
                roomEditChatView.showWithAlpha();
                toggleInput(roomEditChatView.getEditText());
//                roomEditChatView.requestLayout();
//                toggleInput(roomEditChatView.getEditText(),getBaseContext());
            }
        });
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                BarrageHolder barrageHolder = new NormalTextBarrageHolder();
                barrageHolder.onCreateViewHolder(mContext, barrageView);
                barrageView.z(barrageHolder, 1);

                kittyBarrageView.addBarrage(new BarrageChatModel(1));
                kittyBarrageView.addBarrage(new BarrageChatModel(2));
                kittyBarrageView.addBarrage(new BarrageChatModel(3));
                kittyBarrageView.addBarrage(new BarrageChatModel(4));
                kittyBarrageView.addBarrage(new BarrageChatModel(5));
                kittyBarrageView.addBarrage(new BarrageChatModel(6));
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    protected void toggleInput(EditText editText) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);// 48 不调整 输入法完全直接覆盖住
        toggleInput(editText, this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// 34
    }

    public void toggleInput(EditText editText, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);//2
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);//(2,1)
    }

    public static int a(Activity activity) {
        XLog.i("a1=" + DisplayUtils.getScreenHeight(activity) + "，BarrageItemModel=" + DisplayUtils.getWindowVisibleDisplayFrameTop(activity) + "，c=" + DisplayUtils.getWindowVisibleDisplayFrameHeight(activity));
        int a = (DisplayUtils.getScreenHeight(activity) - DisplayUtils.getWindowVisibleDisplayFrameTop(activity)) - DisplayUtils.getWindowVisibleDisplayFrameHeight(activity);
        if (a == 0) {
            return a((Context) activity, 266.0f);
        }
        return a;
    }

    public static int a(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    public static boolean b(Activity activity) {
        if ((DisplayUtils.getScreenHeight(activity) - DisplayUtils.getWindowVisibleDisplayFrameTop(activity)) - DisplayUtils.getWindowVisibleDisplayFrameHeight(activity) <= 0) {
            return false;
        }
        return true;
    }

}
