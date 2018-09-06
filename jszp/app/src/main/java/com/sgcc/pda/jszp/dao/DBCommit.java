//package com.sgcc.pda.jszp.dao;
//
//import android.content.ContentValues;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.lidroid.xutils.DbUtils;
//import com.lidroid.xutils.db.sqlite.Selector;
//import com.lidroid.xutils.exception.DbException;
//import com.sgcc.pda.sdk.utils.LogUtil;
//
//import java.util.List;
//
///**
// * Created by Beckham on 2017/10/13.
// */
//public class DBCommit {
//    private static final String TAG = "DBCommit";
//    private final String SITE_SEAL = "03";
//    private DbUtils dbUtils;
//
//    public DBCommit(DbUtils dbUtils) {
//        this.dbUtils = dbUtils;
//    }
//
//    /**
//     * 创建数据库中表
//     *
//     * @param T
//     */
//    public void createTable(Class<?> T) {
//        try {
//            dbUtils.createTableIfNotExist(T);
//        } catch (DbException e) {
//            LogUtil.e(TAG, "createTable()" + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 概要工单查询
//     *
//     * @param T
//     * @param
//     * @param <T>
//     * @return
//     */
//    public <T> T findByID(Class<?> T) {
//        T t = null;
//        try {
//            t = dbUtils.findFirst(Selector.from(T));
//        } catch (DbException e) {
//            LogUtil.e(TAG, "findByID()" + e.getMessage());
//            e.printStackTrace();
//        }
//        return t;
//    }
//
//
//    public void deleteReg() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("RegisterInfo", "", new String[]{});
//    }
//
//    /**
//     * 保存list数据
//     */
//    public int replaceAllData(List<?> entities) {
//        try {
//            dbUtils.replaceAll(entities);
//            return 0;
//        } catch (DbException e) {
//            LogUtil.e(TAG, "saveAllData()" + e.getMessage());
//            e.printStackTrace();
//            return -1;
//        }
//    }
//
//    /**
//     * 保存list数据
//     */
//    public int saveAllData(List<?> entities) {
//        try {
//            dbUtils.saveAll(entities);
//            return 0;
//        } catch (DbException e) {
//            LogUtil.e(TAG, "saveAllData()" + e.getMessage());
//            e.printStackTrace();
//            return -1;
//        }
//    }
//
//
//    /**
//     * 保存单个数据
//     */
//    public int saveData(Object entity) {
//        try {
//            dbUtils.replace(entity);
//            return 0;
//        } catch (DbException e) {
//            LogUtil.e(TAG, "saveData" + e.getMessage());
//            e.printStackTrace();
//            return -1;
//        }
//    }
//
//    /**
//     * 保存单个数据
//     */
//    public int replaceData(Object entity) {
//        try {
//            dbUtils.replace(entity);
//            return 0;
//        } catch (DbException e) {
//            LogUtil.e(TAG, "saveData" + e.getMessage());
//            e.printStackTrace();
//            return -1;
//        }
//    }
//
//    /**
//     * 查询所有结果
//     *
//     * @param T
//     * @return 查询结果
//     */
//    public <T> List<T> findAllInfo(Class<T> T) {
//        List<T> list = null;
//        try {
//            list = dbUtils.findAll(Selector.from(T));
//        } catch (DbException e) {
//            LogUtil.e(TAG, "findAllInfo: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return list;
//    }
//
//    public <T> List<T> findAllInfo(Class<?> T, String column, String value) {
//        List<T> list = null;
//        try {
//            if (null != column && null != value && !"".equals(column) && !"".equals(value)) {
//                list = dbUtils.findAll(Selector.from(T).where(column, "=", value));
//            } else {
//                list = dbUtils.findAll(Selector.from(T));
//            }
//
//        } catch (DbException e) {
//            LogUtil.e(TAG, "findAllInfo()" + e.getMessage());
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//
//    /**
//     * 根据列查询数据信息
//     *
//     * @param T
//     * @param column 查询列
//     * @param value  查询内容
//     * @param <T>    表名
//     * @return 查询结果
//     */
//    public <T> T findInfo(Class<T> T, String column, String value) {
//
//        T t = null;
//        try {
//            if (null != column && null != value && !"".equals(column) && !"".equals(value)) {
//
//                if (!column.isEmpty() && !value.isEmpty()) {
//                    t = dbUtils.findFirst(Selector.from(T).where(column, "=", value));
//                }
//            }
//
//        } catch (DbException e) {
//            LogUtil.e(TAG, "findAllInfo: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return t;
//    }
//
//    public <T> T findInfo(Class<T> T) {
//        T t = null;
//        try {
//            t = dbUtils.findFirst(Selector.from(T));
//        } catch (DbException e) {
//            LogUtil.e(TAG, "findInfo: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return t;
//    }
//
//
//    /**
//     * 更新News 是否被选中状态
//     */
//    public void updateNews(int newwsId, int isChecked) {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        ContentValues values = new ContentValues();
//        values.put("READFLAG", isChecked);
//        db.update("News", values, "id = ?", new String[]{String.valueOf(newwsId)});
//    }
//
//
//    public void deleteNewsById(String id) {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("News", "id = ?", new String[]{id});
//    }
//
//
//    //清理开盖事件表
//    public void clearOpenCover() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("MeterOfOpenedDetailInfo", null, null);
//    }
//
//    //清理台区列表
//    public void clearDayTgInfoList() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("DayTgListInfo", null, null);
//    }
//
//    public void clearMonTgInfoList() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("MonTgListInfo", null, null);
//    }
//
//    public void clearWarnTgInfoList() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("WarnTgListInfo", null, null);
//    }
//
//    //清理未处理台区列表
//    public void clearNoproTgInfoList() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("NoProListInfo", null, null);
//    }
//
//    //清理台区基本信息
//    public void clearTgBaseInfo() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("LABLEInfo", null, null);
//        db.delete("TgBaseInfo", null, null);
//
//    }
//
//    //清理历史反馈信息
//    public void clearFeedBack() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("FeedBackListInfo", null, null);
//    }
//
//
//    //清理趋势信息
//    public void clearQS() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("H_TGInfo", null, null);
//        db.delete("L_TGInfo", null, null);
//    }
//
//    //清理异常明细表
//    public void clearAbnDetail() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("AbnDetailInfo", null, null);
//        db.delete("AbnLABLEInfo", null, null);
//    }
//
//    //清理月线损
//    public void clearTgMonLmsHisInfo(long tg_id) {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("TgMonLmsHisInfo", "TG_ID = ?", new String[]{tg_id + ""});
//    }
//
//    //清理日线损
//    public void clearTgDayLmsHisInfo(long tg_id) {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("TgDayLmsHisInfo", "TG_ID = ?", new String[]{tg_id + ""});
//    }
//
//    //清理用电量信息
//    public void clearConsPQInfo(String org_no) {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("ConsPQInfo", "ORG_NO = ?", new String[]{org_no});
//    }
//
//    //清理历史采集覆盖率信息
//    public void clearTgCoverInfoHisInfo(long tg_id) {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("TgCoverInfoHisInfo", "TG_ID = ?", new String[]{tg_id + ""});
//    }
//
//    //清理月采集失败表
//    public void clearMonthFailInfo() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("MonthFailInfo", null, null);
//    }
//
//    //清理日采集失败表
//    public void clearDayFailInfo() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("DayFailInfo", null, null);
//    }
//
//    //清理历史日采集
//    public void clearDayHisInfo() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("DayHisInfo", null, null);
//    }
//
//    //清理历史月采集
//    public void clearMonthHisInfo() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("MonthHisInfo", null, null);
//    }
//    //清理历史月采集
//    public void clearDifPqInfo() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("DifPqListInfo", null, null);
//    }
//    //清理月未成功明细
//    public void clearMonFail() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("MonthFailInfo", null, null);
//    }
//    //清理日未成功明细
//    public void clearDayFail() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("DayFailInfo", null, null);
//    }
//    //清理日台区综合指标信息
//    public void clearDayCompQuotasInfo() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("DayCompQuotasInfo", null, null);
//    }
//    //清理月台区综合指标信息
//    public void clearMonCompQuotasInfo() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("MonCompQuotasInfo", null, null);
//    }
//    //清理预警台区综合指标信息
//    public void clearWarnCompQuotasInfo() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("WarnCompQuotasInfo", null, null);
//    }
//    //清理突增突减信息
//    public void clearTzTjInfo() {
//        SQLiteDatabase db = dbUtils.getDatabase();
//        db.delete("TzTjInfo", null, null);
//    }
//
//
//
//}
