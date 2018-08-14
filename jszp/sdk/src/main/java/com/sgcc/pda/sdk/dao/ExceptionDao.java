package com.sgcc.pda.sdk.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.sgcc.pda.sdk.utils.bean.BugInfoBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xuzl on 2016/12/7.
 */
public class ExceptionDao {
    private SDKDBHelper helper;
    private static ExceptionDao instance;
    private int maxCount = 100;//阈值
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String versionName, versionCode;

    private ExceptionDao(Context context) {
        helper = new SDKDBHelper(context);

        collectDeviceInfo(context);
    }

    public static synchronized ExceptionDao getInstance(Context context) {
        if (instance == null) {
            instance = new ExceptionDao(context);
        }
        return instance;
    }
    //---------------------------------------------------------------------------------
    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    private void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                versionName = pi.versionName == null ? "null" : pi.versionName;
                versionCode = pi.versionCode + "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 保存异常信息
     *
     * @param userid      用户ID
     * @param detail      异常描述
     * @param data        元数据
     * @param status      状态
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long  saveException(String userid, String detail, String data, String status,String etype) {
        if (getExceptionCount() >= maxCount) {
            String minId = getMinExceptionItemId();
            if (!TextUtils.isEmpty(minId)) {
                int a = deleteExceptionItem(minId);
            }
        }

        ContentValues values = new ContentValues();
        values.put("dev_info",  Build.MODEL);
        values.put("os_version", "android" + Build.VERSION.RELEASE);
        values.put("app_version", versionName);
        values.put("userid", userid);
        values.put("ctime", simpleDateFormat.format(new Date(System.currentTimeMillis())));
        values.put("detail", detail);
        values.put("data", data);
        values.put("status", status);
        values.put("etype", etype);

        return helper.getWritableDatabase().replace("LogInfo", null, values);
    }

    /**
     * 更新状态为“已上传”
     *
     * @param id 主键
     * @return the number of rows affected
     */
    public long updateExceptionItemStatus(String id) {
        ContentValues values = new ContentValues();
        values.put("status", "已上传");
        return helper.getWritableDatabase().update("LogInfo", values, BaseColumns._ID + "=?", new String[]{id});
    }

    /**
     * 查询所有的异常
     *
     * @return
     */
    public Cursor queryAllException() {
        return helper.getReadableDatabase().rawQuery("select * from LogInfo", null);
    }
    /**
     * 查询所有的异常
     */
    public List<BugInfoBean> getAllInfo(){
        SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from LogInfo where status = ?",new String[]{"未上传"});
            if(cursor!=null){
            List<BugInfoBean> infoBeanList = new ArrayList<>();
                while (cursor.moveToNext()){
                    BugInfoBean infoBean = new BugInfoBean();
                    infoBean.setId(cursor.getString(cursor.getColumnIndex(BaseColumns._ID )));
                     infoBean.setApp_version(cursor.getString(cursor.getColumnIndex("app_version")));
                     infoBean.setCtime(cursor.getString(cursor.getColumnIndex("ctime")));
                     infoBean.setData(cursor.getString(cursor.getColumnIndex("data")));
                     infoBean.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                     infoBean.setDev_info(cursor.getString(cursor.getColumnIndex("dev_info")));
                     infoBean.setEtype(cursor.getString(cursor.getColumnIndex("etype")));
                     infoBean.setOs_version(cursor.getString(cursor.getColumnIndex("os_version")));
                     infoBean.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                     infoBean.setUserid(cursor.getString(cursor.getColumnIndex("userid")));
                    infoBeanList.add(infoBean);
                }
                cursor.close();
                return infoBeanList;
            }
        return null;
    }

    /**
     * 获取已有异常总条数
     *
     * @return
     */
    private long getExceptionCount() {
        Cursor cursor = helper.getReadableDatabase().rawQuery("select count(*) from LogInfo", null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }

    /**
     * 获取最小的主键
     *
     * @return
     */
    private String getMinExceptionItemId() {
        Cursor cursor = helper.getReadableDatabase().rawQuery("select * from LogInfo order by " + BaseColumns._ID + " asc", null);
        cursor.moveToFirst();
        String id = cursor.getString(cursor.getColumnIndex(BaseColumns._ID));
        cursor.close();
        return id;
    }

    /**
     * 删除ID对应的记录
     *
     * @param id
     * @return the number of rows affected if a whereClause is passed in, 0
     * otherwise. To remove all rows and get a count pass "1" as the
     * whereClause.
     */
    private int deleteExceptionItem(String id) {
        ContentValues values = new ContentValues();
        values.put(BaseColumns._ID, id);
        return helper.getWritableDatabase().delete("LogInfo", BaseColumns._ID + "=?", new String[]{id});
    }
}
