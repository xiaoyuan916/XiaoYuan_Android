package com.sgcc.pda.jszp.base;

import android.app.Activity;
import android.app.Application;
import android.os.Build;

import com.sgcc.pda.cepriaidl.AIDLUtils;
import com.sgcc.pda.hardware.hardware.A1BSafetyUnitModule;
import com.sgcc.pda.hardware.hardware.ICommunicate;
import com.sgcc.pda.hardware.model.founation.safetyunit.CommonSafetyUnit;
import com.sgcc.pda.hardware.model.founation.safetyunit.ISafetyUnit;
import com.sgcc.pda.hardware.protocol.IBaseProtocol;
import com.sgcc.pda.hardware.protocol.safetyunit.CommonSafetyUnitEssentialMethod;
import com.sgcc.pda.hardware.protocol.safetyunit.ISafetyUnitEssentialMethod;
import com.sgcc.pda.hardware.protocol.safetyunit.SafetyUnitFrame;
import com.sgcc.pda.hardware.protocol.safetyunit.SafetyUnitProtocol;
import com.sgcc.pda.hardware.util.Constant;
import com.sgcc.pda.hardware.util.NewProtocol;
import com.sgcc.pda.jszp.BuildConfig;
import com.sgcc.pda.jszp.http.JSZPInitOkgo;
import com.sgcc.pda.jszp.util.ExceptionHandler;
import com.sgcc.pda.jszp.util.LogUtils;
import com.sgcc.pda.sdk.app.SDKApplication;
import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.sdk.utils.exception.ExceptionHandle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2017/10/26.
 */

public class BaseApplication extends Application {

    private static BaseApplication application;
    public String uid = "";
    private ISafetyUnit safetyUnit = null;
//    public DBCommit dbCommit;
//    private DbUtils dbUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Log日志工具类
        LogUtil.init("debug".equals(BuildConfig.BUILD_TYPE));
        application = this;
        Build bd = new Build();
        String model = bd.MODEL;
        LogUtil.d("TAG", "设备号：" + model);
        //安全单元初始化
//        safetyUnit();
//        NewProtocol.getInstance().init();
        //绑定AIDL服务
//        AIDLUtils.bind(this);
        //自定义异常捕获处理
        ExceptionHandler.getInstance().init(getApplicationContext());
        //初始化网络请求框架
        initOkhttp();
        //初始化日志打印
        initLog();
    }

    /**
     * 初始化日志使用
     */
    private void initLog() {
        LogUtils.init();
    }

    /**
     * 初始化okhttp
     */
    private void initOkhttp() {
        new JSZPInitOkgo().initOkGo(this);
    }

    /**
     * 安全单元初始化
     */
    public void safetyUnit() {
        /*
          A1BSafetyUnitModule:新A1-B设备安全单元接口
          功能包括：打开通讯端口，关闭通讯端口，获取通讯端口的状态，向通讯端口读写数据
         */
        ICommunicate safety = new A1BSafetyUnitModule();
        //安全单元协议实现
        IBaseProtocol<SafetyUnitFrame> safetyProtocol = new SafetyUnitProtocol();
        /**
         * ISafetyUnitEssentialMethod:安全单元通讯基础类
         * CommonSafetyUnitEssentialMethod:安全基础通讯类支持
         */
        ISafetyUnitEssentialMethod<SafetyUnitFrame> safetyMethod =
                new CommonSafetyUnitEssentialMethod(safetyProtocol, safety);
        //通用安全单元功能实现类
        safetyUnit = new CommonSafetyUnit(safetyMethod);
    }

    public ISafetyUnit getSafetyUnit() {
        return safetyUnit;
    }


    public static BaseApplication getInstance() {
        return application;
    }

    private static List<Activity> lists = new ArrayList<>();

    public static void addActivity(Activity activity) {
        lists.add(activity);
    }

    //  将不用的activity从集合中清楚，避免内存泄露
    public static void removeActivity(Activity act) {
        if (lists != null) {
            if (lists.contains(act)) {
                lists.remove(act);
            }

        }
    }


    public static void clearActivity() {
        if (lists != null) {
            for (Activity activity : lists) {
                activity.finish();
            }

            lists.clear();
        }
    }


}
