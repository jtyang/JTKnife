package com.android.jtknife.modules.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.TextureView;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;

import butterknife.Bind;

public class CameraActivity extends BaseActivity {

    @Bind(R.id.camera_texture)
    TextureView cameraTexture;

    private int mCameraId;
    private Camera1Session mCameraSession;
    private SurfaceTexture mOESSurfaceTexture;
    private int mOESTextureId = -1;

    private CameraGLRender cameraGLRender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_camera;
    }

    @Override
    protected void onInitView() {

        cameraGLRender = new CameraGLRender();

        cameraTexture.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                mOESTextureId = GLUtils.createOESTextureObject();
                cameraGLRender.init(cameraTexture, mOESTextureId, CameraActivity.this);
                mOESSurfaceTexture = cameraGLRender.initOESTexture();

                mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                DisplayMetrics dm = new DisplayMetrics();
                mCameraSession = new Camera1Session(CameraActivity.this);
                if (!mCameraSession.openCamera(dm.widthPixels, dm.heightPixels, mCameraId)) {
                    return;
                }

                mCameraSession.setPreviewTexture(mOESSurfaceTexture);
                mCameraSession.startPreview();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                if (mCameraSession != null) {
                    mCameraSession.stopPreview();
                    mCameraSession.releaseCamera();
                    mCameraSession = null;
                }
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
    }


}
