package com.xiao.project.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xiao.project.R;
import com.xxyuan.service.aidl.IServiceAidlInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AIDLActivity extends AppCompatActivity {

    @BindView(R.id.bt_aidl)
    Button btAidl;
    private IServiceAidlInterface iServiceAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);
        bindService();
    }

    @OnClick({R.id.bt_aidl})
    public void onclickViewed(View view) {
        switch (view.getId()) {
            case R.id.bt_aidl:
                try {
                    String str = iServiceAidlInterface.getString("调用");
                    Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.getStackTrace();
                }
                break;
        }
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClassName("com.xxyuan.service.aidl", "AIDLService");
        intent.setAction("com.xxyuan.service.AIDLService.action");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage(this.getPackageName());    //从 Android 5.0开始 隐式Intent绑定服务的方式已不能使用,所以这里需要设置Service所在服务端的包名
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);


    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iServiceAidlInterface = IServiceAidlInterface.Stub.asInterface(service);
            Log.d(getClass().getSimpleName(), "链接成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(getClass().getSimpleName(), "链接失败");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
