package com.android.jtknife.modules.audiorecord;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.core.utils.FileUtils;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/11/6
 */
public class AudioRecordActivity extends BaseActivity {

    public final static String SDCARD_PATH = Environment.getExternalStorageDirectory() + "/JTKnife";
    public final static String WAV_FILE_PATH = SDCARD_PATH + "/test.wav";

    private static final int REQUEST_CODE_PERMISSION = 100;

    private static final int REQUEST_CODE_SETTING = 300;

    PcmRecorder mPcmRecorder;
    Button mBtnTrigger;
    ProgressBar mProgressBar;
    SmTimer mSmTimer;
    Button playerBtn;

    MediaPlayer mediaPlayer;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_audio_record;
    }

    @Override
    protected void onInitView() {
        mBtnTrigger = (Button) findViewById(R.id.btn_record_trigger);
        mBtnTrigger.setOnClickListener(mTriggerLsn);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_voice_ampli);
        mProgressBar.setMax(30 * 1000);
        playerBtn = (Button) findViewById(R.id.player_btn);
        playerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick();
            }
        });
        playerBtn.setTag(1);

        //request permission
        AndPermission.with(this)
                .requestCode(REQUEST_CODE_PERMISSION)
                .permission(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {

                        AlertDialog.newBuilder(AudioRecordActivity.this)
                                .setTitle("permission")
                                .setMessage("request permission")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        rationale.resume();
                                    }
                                })
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        rationale.cancel();
                                    }
                                }).show();

                    }
                })
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        if (requestCode == REQUEST_CODE_PERMISSION) {

                        }
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        if (requestCode == REQUEST_CODE_PERMISSION) {

                        }
                        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                        if (AndPermission.hasAlwaysDeniedPermission(AudioRecordActivity.this, deniedPermissions)) {
                            // 第一种：用默认的提示语。
                            AndPermission.defaultSettingDialog(AudioRecordActivity.this, REQUEST_CODE_SETTING).show();

                            // 第二种：用自定义的提示语。
//             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
//                     .setTitle("权限申请失败")
//                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                     .setPositiveButton("好，去设置")
//                     .show();

//            第三种：自定义dialog样式。
//            SettingService settingHandle = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingHandle.execute();
//            你的dialog点击了取消调用：
//            settingHandle.cancel();
                        }
                    }
                })
                .start();
    }

    private SmTimer.SmTimerCallback mTimerCallback = new SmTimer.SmTimerCallback() {
        @Override
        public void onTimeout() {
            if (null != mPcmRecorder && mSmTimer != null) {
//                mProgressBar.setProgress(mPcmRecorder.getAmplitude());
                mProgressBar.setProgress((int) mSmTimer.getTotalTime());
            }
        }
    };

    private View.OnClickListener mTriggerLsn = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (null == v.getTag()) {
                mBtnTrigger.setText("Stop Record");
                mProgressBar.setProgress(0);
                v.setTag(1);
                FileUtils.createDir(SDCARD_PATH);
                FileUtils.deleteFile(WAV_FILE_PATH);
                mPcmRecorder = new PcmRecorder(16000, 1, WAV_FILE_PATH);
                mPcmRecorder.startRecord();

                mSmTimer = new SmTimer(mTimerCallback);
                mSmTimer.startIntervalTimer(0, 100);
            } else {
                mPcmRecorder.stopRecord();
                mBtnTrigger.setText("Start Record");
                v.setTag(null);
                mSmTimer.stopTimer();
                mSmTimer = null;
            }
        }
    };

    private void playerClick() {
        int tag = (int) playerBtn.getTag();
        if (tag == 1) {
            startPlay();
        } else {
            stopPlay();
        }
    }

    private void startPlay() {
        stopPlay();
        playerBtn.setText("Stop play");
        playerBtn.setTag(2);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(AudioRecordActivity.this, Uri.fromFile(new File(WAV_FILE_PATH)));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlay() {
        playerBtn.setText("Play Wav");
        playerBtn.setTag(1);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                Toast.makeText(this, "setting_back", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

}
