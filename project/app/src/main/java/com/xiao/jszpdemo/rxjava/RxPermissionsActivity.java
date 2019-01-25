package com.xiao.jszpdemo.rxjava;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiao.jszpdemo.MainActivity;
import com.xiao.jszpdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class RxPermissionsActivity extends AppCompatActivity {

    @BindView(R.id.bt_permission)
    Button btPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_permissions);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_permission)
    public void onViewClicked(View v) {
        startPermission();
    }

    @SuppressLint("CheckResult")
    private void startPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //申请的权限全部允许
                            Toast.makeText(RxPermissionsActivity.this, "允许了权限!", Toast.LENGTH_SHORT).show();
                        } else {
                            //只要有一个权限被拒绝，就会执行
                            Toast.makeText(RxPermissionsActivity.this, "未授权权限，部分功能不能使用", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
