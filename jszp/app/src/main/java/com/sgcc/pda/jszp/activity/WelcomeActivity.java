package com.sgcc.pda.jszp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sgcc.pda.jszp.MainActivity;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.bean.UserList;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.util.securityunit.AuthenticationHelper;
import com.sgcc.pda.jszp.util.securityunit.StatusBean;

public class WelcomeActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContext = this;
        sleepFinish();

//        //todo 请添加权限
//        AuthenticationHelper.getInstance().certificationStart(mContext,new AuthenticationHelper.OnCertificationListener() {
//            @Override
//            public void onSuccess(String UID,String userId) {// UID以及操作者代码
//               // getHandsetUsers(UID);
//                startActivity(new Intent(mContext, MainActivity.class));
//            }
//
//            @Override
//            public void onError(StatusBean statusBean) {
//                Log.d("AuthenticationHelper", "onError: "+ statusBean.getErrorMessage());
//            }
//        });
    }

    public void sleepFinish() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivity(new Intent(mContext, MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }


    /**
     * // 获取终端设备持有人信息,
     *
     * @param UID
     */
    private void getHandsetUsers(String UID) {
        AuthenticationHelper.getInstance().getHandsetUsers(mContext, UID, new JSZPOkgoHttpUtils.JSZPCallcak<UserList>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(UserList response, String json) {
                startActivity(new Intent(mContext, MainActivity.class));
            }
        });
    }

}
