package com.sgcc.pda.cepriaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.cepri.service.IDEVBaseInterface;
import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.sdk.utils.ToastUtils;

/**
 * Created by lishuangfeng on 2016/11/14.
 * AIDL服务管理器
 */
public class CepriServiceManager {
    private static CepriServiceManager instance;
    private IDEVBaseInterface idevService = null;
    private boolean bindflag = false;

    public static synchronized CepriServiceManager getInstance() {
        if (instance == null) {
            instance = new CepriServiceManager();
        }
        return instance;
    }

    public IDEVBaseInterface getCepriService() {
        return idevService;
    }

    public void bind(Context context) {
        if (!bindflag) {
            Intent intent = new Intent(IDEVBaseInterface.class.getName());
            intent.setClassName("com.cepri.service", "com.cepri.service.IDEVBaseServer");

            try {
                boolean bindflag = context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
                if (!bindflag) {
                    ToastUtils.showToast(context, "应用基础服务绑定失败");
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public void unbind(Context context) {
        if (bindflag) context.unbindService(conn);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.i("TAG", "----onServiceConnected-----");
            bindflag = true;
            idevService = IDEVBaseInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.i("TAG", "----onServiceDisconnected-----");
            bindflag = false;
        }
    };
}
