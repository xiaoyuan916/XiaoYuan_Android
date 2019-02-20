package com.xiao.project.activity;

import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;
import android.widget.Button;
import android.widget.TextView;

import com.xiao.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.simonlee.xcodescanner.core.CameraScanner;
import cn.simonlee.xcodescanner.core.GraphicDecoder;
import cn.simonlee.xcodescanner.core.NewCameraScanner;
import cn.simonlee.xcodescanner.core.OldCameraScanner;
import cn.simonlee.xcodescanner.view.AdjustTextureView;
import cn.simonlee.xcodescanner.view.ScannerFrameView;

public class XCodeScannerActivity extends AppCompatActivity {

    @BindView(R.id.textureview)
    AdjustTextureView textureview;
    @BindView(R.id.scannerframe)
    ScannerFrameView scannerframe;
    @BindView(R.id.textview)
    TextView textview;
    @BindView(R.id.btn_flash)
    Button btnFlash;

    private NewCameraScanner mCameraScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xcode_scanner);
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {
        textureview.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });
        /*
         * 注意，SDK21的设备是可以使用NewCameraScanner的，但是可能存在对新API支持不够的情况，比如红米Note3（双网通Android5.0.2）
         * 开发者可自行配置使用规则，比如针对某设备型号过滤，或者针对某SDK版本过滤
         * */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCameraScanner = new NewCameraScanner(new CameraScanner.CameraListener() {
                @Override
                public void openCameraSuccess(int surfaceWidth, int surfaceHeight, int surfaceDegree) {

                }

                @Override
                public void openCameraError() {

                }

                @Override
                public void noCameraPermission() {

                }

                @Override
                public void cameraDisconnected() {

                }

                @Override
                public void cameraBrightnessChanged(int brightness) {

                }
            });
        } else {
//            mCameraScanner = new OldCameraScanner(new c);
        }
    }
}
