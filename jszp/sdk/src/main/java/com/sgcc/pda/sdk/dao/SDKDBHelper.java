package com.sgcc.pda.sdk.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.sdk.utils.ToastUtils;

/**
 * Created by xuzl on 2016/12/7.
 */
public class SDKDBHelper extends SQLiteOpenHelper {
    private static final String DB_Name = "sdk.db";
    private static final int DB_Version = 2;
    private final Context context;

    public SDKDBHelper(Context context) {
        //调用父类创建数据库
        super(context, DB_Name, null, DB_Version);
        this.context = context;
//        getWritableDatabase().enableWriteAheadLogging();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table LogInfo(" +//日志表
                BaseColumns._ID + " integer not null primary key," +//主键
                "dev_info text not null," +//设备信息
                "os_version text not null," +//Android版本
                "app_version text not null," +//版本号
                "userid text ," +//操作员
                "ctime text not null," +//异常时间
                "detail text not null," +//异常信息
                "data text ," +//元数据
                "status text not null," +//状态
                "etype text not null,"+  //类型
                "CONSTRAINT uc_vd UNIQUE (app_version,detail))");//约束

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.d("TAG"," oldVersion = "+oldVersion+" newVersion = "+newVersion);
//        ToastUtils.showToast(context,"执行数据库升级操作");
        if(oldVersion == 1 && newVersion == 2){
            try{
                db.execSQL("alter table LogInfo add etype text not null DEFAULT 系统崩溃");
//                db.execSQL("update LogInfo set etype = 系统崩溃 where data is null");
//                db.execSQL("update LogInfo set etype = 补抄失败 where data is not null");

            }catch (Exception sqle) {
                sqle.printStackTrace();
                ToastUtils.showToast(context, "数据库升级失败：" + sqle.getMessage());
            }
        }
    }
}
