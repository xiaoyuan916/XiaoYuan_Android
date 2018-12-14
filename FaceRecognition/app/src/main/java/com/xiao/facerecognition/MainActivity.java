package com.xiao.facerecognition;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xiao.facerecognition.activity.FaceDetectionActivity;
import com.xiao.facerecognition.facedetector.activity.DetectActivity;
import com.xiao.facerecognition.facedetector.util.PermissionHelper;
import com.xiao.facerecognition.facedetector.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_face_detection)
    Button btFaceDetection;
    @BindView(R.id.bt_face_detection2)
    Button btFaceDetection2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
    }

    @OnClick({R.id.bt_face_detection,R.id.bt_face_detection2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_face_detection:
                requestCameraPermission(new PermissionHelper.RequestListener() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(MainActivity.this,
                                FaceDetectionActivity.class);
                        intent.putExtra("flag", FaceDetectionActivity.FLAG_REGISTER);
                        startActivityForResult(intent,
                                FaceDetectionActivity.FLAG_REGISTER);
                    }

                    @Override
                    public void onDenied() {
                        ToastUtil.showToast(MainActivity.this, "权限拒绝", 0);
                    }
                });
               break;
            case R.id.bt_face_detection2:
                requestCameraPermission(new PermissionHelper.RequestListener() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(MainActivity.this,
                                DetectActivity.class);
                        intent.putExtra("flag", DetectActivity.FLAG_REGISTER);
                        startActivityForResult(intent,
                                DetectActivity.FLAG_REGISTER);
                    }

                    @Override
                    public void onDenied() {
                        ToastUtil.showToast(MainActivity.this, "权限拒绝", 0);
                    }
                });
                break;

        }
    }

    private void requestCameraPermission(PermissionHelper.RequestListener listener) {
        PermissionHelper.with(MainActivity.this)
                .requestPermission(Manifest.permission.CAMERA)
                .requestCode(1)
                .setListener(listener)
                .request();
    }
}
