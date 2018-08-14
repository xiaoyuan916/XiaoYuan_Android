package com.sgcc.pda.sdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 临时数据存储
 *
 * @author:GuangJie-Wang
 * @Date: 2016/4/21
 * @Time: 15:31
 */
public class SharepreferenceUtil {
    // SharedPreferences名称
    /*******************
     * 操作员号和业务员号
     *******************/
    private static final String OPERATE = "operate";
    private static final String OPERATORNO = "operatorNo";//操作员号
    private static final String COMPANY = "company";
    private static final String COMPANYNO = "companyNo";//操作员号
    private static final String OPERATORNAME = "operatorName";//操作员号
    private static final String OPERATIONNO = "operationNo";//业务员号
    private static final String DEV_SNNO = "dev_snNo"; //服务终端序列号
    private static final String SIMCARD_SNNO = "simcard_snNo"; //sim卡序列号
    private static final String FAULTNUM = "faultnum"; //消缺达人

    /***********************
     * SIM卡测试参数设置
     ***********************/
    private static final String THINTA = "thinta";
    private static final String COMMUNICATION_IP = "communication_ip";
    private static final String COMMUNICATION_PORT = "communication_port";
    private static final String COMMUNICATION_APN = "communication_apn";

    /**********************
     * 通讯参数设置
     **********************/
    private static final String THINTA_COMM = "thinta_comm";
    private static final String COMM_USBEnable = "comm_usbenable";
    private static final String COMM_IP = "comm_ip";//IP地址
    private static final String COMM_PORT = "comm_port";//端口号
    private static final String COMM_IP_USB = "comm_ip_usb";//USB通信方式IP地址
    private static final String COMM_PORT_USB = "comm_port_usb";//USB通信方式端口号
    private static final String COMM_APNNAME = "comm_apnname";//APN名称
    private static final String COMM_APN = "comm_apn";//APN
    private static final String COMM_USER_NAME = "comm_user_name"; //APN用户名
    private static final String COMM_USER_PSWD = "comm_user_pswd"; //APN密码

    /**********************
     * 工单数量
     **********************/
    private static final String ERRORREASON = "errorreason";
    private static final String DANGERSWITCH = "dangerswitch";
    private static final String BUCHAORESULT = "buchaoresult";
    private static final String BUCHAOINFO = "buchaoinfo";
    private static final String TFRESULT = "tfresult";
    private static final String NEWSJSON = "newsjson";
    private static final String LOCALJSON = "localjson";
    private static final String TFRESON = "tfreson";
    private static final String CZRESON = "czreson";
    private static final String JSRESON = "jsreson";
    private static final String DJTZRESON = "djtzreson";
    private static final String MYRESON = "myreson";
    private static final String JZQMYRESON = "jzqmyreson";
    private static final String ETSBBH = "etsbbh";
    private static final String JLYCREASON = "jlycreson";
    private static final String DNBCSYZREASON = "dnbcsyzreson";
    private static final String TFEXCTIME = "tfexctime";
    private static final String WORKORDERFLAG = "workorderflag";
    private static final String QDWORKORDERFLAG = "qdworkorderflag";
    private static final String WORKORDERINFO = "workorder";
    private static final String BCPOSITION = "bcposition";

    private static final String APP_NUM = "sum_num";//工单总数量
    private static final String YCGD_NUM = "ycgd_num";//异常工单
    private static final String YLGD_NUM = "ylgd_num";//已领工单
    private static final String BCGD_NUM = "bcgd_num";//补抄工单
    private static final String FKGD_NUM = "fkgd_num";//费控工单
    private static final String XSGD_NUM = "xsgd_num";//巡视工单
    private static final String JLGD_NUM = "jlgd_num";//计量工单
    private static final String JLYCGD_NUM = "jlycgd_num";//计量异常工单
    private static final String DNBCSYZGD_NUM = "dnbcsyzgd_num";//电能表参数设置工单
    private static final String ARCHIVE_CHECK_NUM = "archive_check_num";//档案核查工单
    private static final String ZD_RECHARGE_NUM = "zd_recharge_num";//档案核查工单
    private static final String TFDGD_NUM = "tfdgd_num";//现场停复电工单
    private static final String CZGD_NUM = "czgd_num";//充值工单
    private static final String JSGD_NUM = "jsgd_num";//校时工单
    private static final String MYGD_NUM = "mygd_num";//密钥工单
    private static final String JZQMYGD_NUM = "jzqmygd_num";//密钥工单
    private static final String DJTZGD_NUM = "djtzgd_num";//电价调整工单
    private static final String DJTZRESULT = "djtzresult";//电价调整工单
    private static final String DNBCSYZRESULT = "dnbcsyzresult";//电能表参数设置工单
    private static final String JSRESULT = "jsresult";//电价调整工单
    private static final String DJSDTZGD_NUM = "djsdtzgd_num";//电价时段调整工单
    private static final String DIFFIGD_NUM = "diffigd_num";//疑难工单
    private static final String BC_POSITION = "bc_position";//疑难工单
    private static final String MINLENGTH = "minlength";
    private static final String MIN = "min";
    private static final String News_Num = "news_num";//未读新消息数量
    private static final String USERID = "userid";
    private static final String USERFLAG = "userflag";
    private static final String KNOWLEDGE = "knowledge";//知识库版本保存
    private static final String UPDATECLASSIC = "updateclassis";//典型案例库同步
    private static final String CLASSICTIME = "classistime";//典型案例库同步
    private static final String NVK = "nvk";//知识库版本号
    private static final String LAST_TIME = "last_time";//上次更新时间
    private static final String KNOWDV = "knowdv";//客户端保存dv

    private static final String POSITION_TIME = "position";//经纬度上传周期
    private static final String OLDTIME = "oldtime";//校时前时间
    private static final String O_TIME = "o_time";//校时前时间
    private static final String NEWTIME = "newtime";//校时后时间
    private static final String N_TIME = "n_time";//校时后时间
    private static final String LOAD_STYLE = "loadStyle";//上传方式
    private static final String TIME = "time";//时间
    private static final String XC = "xc";
    private static final String XCAPPNO = "xc_app_no";
    private static final String STATUS = "status";//现场巡视状态
    private static final String CONFIG = "config";
    private static final String CHOOSE = "choose";
    private static final String LAT = "lat";//纬度
    private static final String L_AT = "l_at";//纬度
    private static final String LOT = "lot";//经度
    private static final String L_OT = "l_ot";//经度
    private static final String LAT1 = "lat1";//纬度
    private static final String L_AT1 = "l_at1";//纬度
    private static final String LOT1 = "lot1";//经度
    private static final String L_OT1 = "l_ot1";//经度


    /*******************
     * 上传经纬度配置条件
     *******************/
    private static final String POSITION = "position";
    private static final String MAPAUTHKEY = "mapAuthKey";
    private static final String ISOPEN = "isopen";//是否打开
    private static final String COLLRATE = "collrate";//采集频率
    private static final String LOADRATE = "mapAuthKey";//上传频率
    private static final String ZHENGDUAN = "zhengduan";
    private static final String XIUFU = "xiufu";
    private static final String DEV = "DEV";
    private static final String SIMCARD_SN = "simcard_sn";

    /*******************
     * 实验室模式
     *******************/
    private static final String TESTMODE = "testmode";
    private static final String MODE = "mode";

    /* 息屏开关
    *******************/
    private static final String LIGHTFLAG = "lightflag";
    private static final String LIGHT = "light";

    /*******************
     * 现场电费下发
     *******************/
    private static final String RECHARGE = "recharge";
    private static final String Result = "Result";

    /*******************
     * 终端电量下发
     *******************/
    private static final String ZDRECHARGE = "zdrecharge";

    /*******************
     * 密钥下装
     *******************/
    private static final String KEY = "Key";
    private static final String MIYUE = "miyue";
    private static final String KeyResult = "key";
    /*******************
     * 电价调整
     *******************/
    private static final String DJTZR = "djtzr";
    private static final String XCJS = "xcjs";

    /*******************
     * 电能表参数设
     *******************/
    private static final String DNBCSYZR = "dnbcsyzr";
    /*******************
     * 集中器密钥下装
     *******************/
    private static final String JZQKEY = "JZQKey";
    private static final String JZQKeyResult = "jzqkey";

    /*******************
     * 实验室模式
     *******************/
    private static final String TIMERSTATUS = "testmode666";
    private static final String TIMER = "mode666";
    /**
     * 屏幕宽度
     */
    private static final String SCREENWIDTH = "screenwidth";
    private static final String WIDTH = "width";
    private static final String IS_M = "ism";
    /**
     * 版本升级更新内容
     */
    private static final String CONTENTUPDATE = "contentupdate";
    private static final String LOGINFLAG = "loginflag";
    private static final String USERCODE = "usercode";
    private static final String UPDATE_FLAG = "update_flag";
    private static final String LOGIN_FLAG = "login_flag";
    private static final String USER_CODE = "usercode";
    private static final String CONTENTFLAG = "contentflag";
    private static final String CONTENT = "content";
    private static final String DOWNALL = "downall";
    private static final String DOWN = "down";
    private static final String MAINFLAG = "mainflag";
    private static final String MAIN = "main";
    private static final String ISCLEAR = "isclear";
    private static final String CLEAR = "clear";
    private static final String REG_IDX = "regidx";//注册索引信息
    private static final String REG = "reg";
    private static final String ZDLJDZ = "Zdljdz";
    private static final String WORKFLAG = "workflag";
    private static final String WORK = "work";
    private static final String SCANFLAG = "scanflag";
    private static final String RIGHTNUM = "rightnum";
    private static final String WRONGNUM = "wrongnum";
    private static final String GRADE = "grade";
    private static final String MODEL = "model";
    private static final String COLLECTION = "collection";
    private static final String SCAN = "scan";
    private static final String RDOWNLOADFLAG = "rdownloadflag";
    private static final String COURSEDOWNLOADFLAG = "coursedownloadflag";
    private static final String STATUSFLAG = "statusflag";
    private static final String STATUS_FLAG = "status_flag";
    private static final String IM_FLAG = "im_flag";
    private static final String IM_NAME = "im_name";
    private static final String STATIONNEWS = "stationnews";
    private static final String NATIVENEWS = "nativenews";
    private static final String UPDATETIME = "updatetime";
    private static final String SAFEUPDATETIME = "safeupdatetime";
    private static final String IM_PSWD = "im_pswd";
    private static final String IM_LOGIN = "im_login";
    private static final String IM_LOGIN_FLAG = "im_login_flag";
    private static final String MYLAT = "mylat";//纬度
    private static final String SMYLAT = "mylat";//纬度
    private static final String MYLON = "mylon";//经度
    private static final String SMYLON = "mylon";//经度
    private static final String NOTIFICATION = "notification";
    private static final String NOTIFICATION_ID = "notification_id";
    private static final String SZHCISPING = "szhzisping";
    private static final String SZHCRESULT = "szhcresult";
    private static final String ISSZHC = "isszhc";
    private static final String ISSZHCRESLUT = "isszhcresult";
    /**
     * 终端装接
     */
    private static final String PARAM_CHECK_FLAG = "param_check_flag";//参数核查标志
    private static final String PARAM_CHECK_RESULT = "param_check_result";//参数核查结果
    private static final String RECORD_ISSUE_FLAG = "record_issue_flag";//档案下发标志
    private static final String RECORD_ISSUE_RESULT = "record_issue_result";//档案下发结果
    private static final String READMETER_PARAM_ISSUE_FLAG = "readmeter_param_issue_flag";//抄表参数下发标志
    private static final String READMETER_PARAM_ISSUE_RESULT = "readmeter_param_issue_result";//抄表参数下发结果
    private static final String READMETER_DEBUG_FLAG = "readmeter_debug_flag";//抄表联调标志
    private static final String READMETER_DEBUG_RESULT = "readmeter_debug_result";//抄表联调结果
    private static final String EVENT_ALLOCATION_FLAG = "event_allocation_flag";//事件重要性等级下发标志
    private static final String MUTUAL_COMPARE_FLAG = "mutual_compare_flag";//互感器比对标志
    private static final String BATCH_NO = "batch_no";//保存召测结果的批次号

    private static final String TERMINAL_INSTALL = "terminal_install";
    private static final String METER_INSTALL = "meter_install";

    private static final String BLUE = "blue";
    private static final String BLUELIST = "bluelist";
    private static final String BLUENAME = "bluename";
    private static final String WSTYPE = "wstype";
    /**
     * 是否下拉刷新的标志
     *
     * @param context
     * @param flag
     */
    public static void setBlueConnectFlag(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                BLUE, 0).edit();
        editor.putBoolean("blueconnect", flag);
        editor.commit();
    }

    public static boolean getBlueConnectFlag(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                BLUE, 0);
        if (preferences.contains("blueconnect")) {
            return preferences.getBoolean("blueconnect", false);
        }
        return false;
    }

    public static void clearBlueConnectFlag(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BLUE, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 是否下拉刷新的标志
     *
     * @param context
     * @param flag
     */
    public static void setBlueListFlag(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                BLUELIST, 0).edit();
        editor.putBoolean("bluelist", flag);
        editor.commit();
    }

    public static boolean getBlueListFlag(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                BLUELIST, 0);
        if (preferences.contains("bluelist")) {
            return preferences.getBoolean("bluelist", false);
        }
        return false;
    }

    public static void clearBlueListFlag(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BLUELIST, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 获取当前经度
     */
    public static String getBlueName(Context context,String wsType) {
        SharedPreferences preferences = context.getSharedPreferences(
                BLUENAME, 0);
        return preferences.getString("blue_name_sp"+wsType, null);

    }

    /**
     * 设置当前经度
     */
    public static void setBlueName(Context context, String lon,String wsType) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                BLUENAME, 0).edit();
        editor.putString("blue_name_sp"+wsType, lon);
        editor.commit();
    }

    //清空当前经度
    public static void clearBlueName(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BLUENAME, 0).edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 获取当前经度
     */
    public static String getMyWsType(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WSTYPE, 0);
        return preferences.getString("ws_type_sp", null);

    }

    /**
     * 设置当前经度
     */
    public static void setMyWsType(Context context, String lon) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WSTYPE, 0).edit();
        editor.putString("ws_type_sp", lon);
        editor.commit();
    }

    //清空当前经度
    public static void clearMyWsType(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(WSTYPE, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 获取当前经度
     */
    public static String getMyLon(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                SMYLON, 0);
        return preferences.getString(MYLON, null);

    }

    /**
     * 设置当前经度
     */
    public static void setMyLon(Context context, String lon) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                SMYLON, 0).edit();
        editor.putString(MYLON, lon);
        editor.commit();
    }

    //清空当前经度
    public static void clearMyLon(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SMYLON, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 获取当前纬度
     */
    public static String getMyLat(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                SMYLAT, 0);
        return preferences.getString(MYLAT, null);

    }

    /**
     * 设置当前纬度
     */
    public static void setMyLat(Context context, String lat) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                SMYLAT, 0).edit();
        editor.putString(MYLAT, lat);
        editor.commit();
    }

    //清空当前纬度
    public static void clearMyLat(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SMYLAT, 0).edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 设置屏幕宽度
     */
    public static void setScreenWidth(Context context, int width) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                SCREENWIDTH, 0).edit();
        editor.putInt(WIDTH, width);
        editor.commit();
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                SCREENWIDTH, 0);
        if (preferences.contains(WIDTH)) {
            return preferences.getInt(WIDTH, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置当前纬度
     */
    public static void setLat(Context context, String lat) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                LAT, 0).edit();
        editor.putString(L_AT, lat);
        editor.commit();
    }

    /**
     * 获取当前纬度
     */
    public static String getLat(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                LAT, 0);
        if (preferences.contains(L_AT)) {
            return preferences.getString(L_AT, null);
        } else {
            return null;
        }
    }

    /**
     * 设置当前纬度
     */
    public static void setLat1(Context context, String lat1) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                LAT1, 0).edit();
        editor.putString(L_AT1, lat1);
        editor.commit();
    }

    /**
     * 获取当前纬度
     */
    public static String getLat1(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                LAT1, 0);
        if (preferences.contains(L_AT1)) {
            return preferences.getString(L_AT1, null);
        } else {
            return null;
        }
    }

    /**
     * 设置当前经度
     */
    public static void setLot(Context context, String lot) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                LOT, 0).edit();
        editor.putString(L_OT, lot);
        editor.commit();
    }


    /**
     * 获取当前经度
     */
    public static String getLOt(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                LOT, 0);
        if (preferences.contains(L_OT)) {
            return preferences.getString(L_OT, null);
        } else {
            return null;
        }
    }

    /**
     * 设置当前经度
     */
    public static void setLot1(Context context, String lot1) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                LOT1, 0).edit();
        editor.putString(L_OT1, lot1);
        editor.commit();
    }


    /**
     * 获取当前经度
     */
    public static String getLOt1(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                LOT1, 0);
        if (preferences.contains(L_OT1)) {
            return preferences.getString(L_OT1, null);
        } else {
            return null;
        }
    }


    /**
     * 设置上传频率
     *
     * @param context
     * @param is
     */
    public static void setLOADRATE(Context context, String is) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                POSITION, 0).edit();
        editor.putString(LOADRATE, is);
        editor.commit();
    }

    /**
     * 获取上传频率
     *
     * @param context
     * @return
     */
    public static String getLOADRATE(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                POSITION, 0);
        if (preferences.contains(LOADRATE)) {
            return preferences.getString(LOADRATE, "");
        } else {
            return "";
        }
    }

    /**
     * 设置离线地图鉴权key
     *
     * @param context
     * @param is
     */
    public static void setMAPAUTHKEY(Context context, String is) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                MAPAUTHKEY, 0).edit();
        editor.putString(MAPAUTHKEY, is);
        editor.commit();
    }

    /**
     * 获取离线地图鉴权key
     *
     * @param context
     * @return
     */
    public static String getMAPAUTHKEY(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                MAPAUTHKEY, 0);
        if (preferences.contains(MAPAUTHKEY)) {
            return preferences.getString(MAPAUTHKEY, "");
        } else {
            return "";
        }
    }

    /**
     * 上传经纬度时间清理
     */
    public static void clearspcjtime(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(COLLRATE, 0).edit();
        editor.clear();
    }

    public static void clearspsctime(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(LOADRATE, 0).edit();
        editor.clear();
    }

    /**
     * 清除sp中指定key的字段
     *
     * @param context 上下文
     */
    public static void clearSP(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(CONFIG, 0).edit();
//        editor.clear();
        editor.remove(appNo);
        editor.commit();
    }

    public static void clearSP1(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ZHENGDUAN, 0).edit();
//        editor.clear();
        editor.remove(appNo);
        editor.commit();
    }

    public static void clearSP2(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(XIUFU, 0).edit();
//        editor.clear();
        editor.remove(appNo);
        editor.commit();
    }

    public static void clearErrorReason(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ERRORREASON, 0).edit();
        //editor.clear();
        editor.remove(appNo);
        editor.commit();
    }

    //清除补抄结果
    public static void clearBuChaoResult(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BUCHAORESULT, 0).edit();
        //editor.clear();
        editor.remove(appNo + "BuChaoResult");
        editor.commit();
    }

    public static void clearBuChaoResult(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BUCHAORESULT, 0).edit();
        editor.clear();
        editor.commit();
    }

    //清除补抄结果
    public static void clearBuChaoInfo(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BUCHAOINFO, 0).edit();
        //editor.clear();
        editor.remove(appNo + "BUCHAOINFO");
        editor.commit();
    }

    //清除补抄结果
    public static void clearBuChaoInfo(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BUCHAOINFO, 0).edit();
        editor.clear();
        editor.commit();
    }


    //清除停服点结果
    public static void clearTFResult(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TFRESULT, 0).edit();
        //editor.clear();
        editor.remove(appNo + "TFResult");
        editor.commit();
    }

    public static void clearTFResult(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TFRESULT, 0).edit();
        editor.clear();
        editor.commit();
    }

    //清除停服点结果
    public static void clearTFReson(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TFRESON, 0).edit();
        //editor.clear();
        editor.remove(appNo + "TFReson");
        editor.commit();
    }

    //清除停服点结果
    public static void clearTFReson(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TFRESON, 0).edit();
        editor.clear();
        editor.commit();
    }

    //清除停服点结果
    public static void clearTFExctime(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TFEXCTIME, 0).edit();
        //editor.clear();
        editor.remove(appNo + "TFExctime");
        editor.commit();
    }

    //清除停服点结果
    public static void clearTFExctime(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TFEXCTIME, 0).edit();
        editor.clear();
        editor.commit();
    }

    //清除采集补抄结果
    public static void clearCaiJiBuChaoResult(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BUCHAORESULT, 0).edit();
        //editor.clear();
        editor.remove(appNo + "CaiJiBuChaoResult");
        editor.commit();
    }

    public static void clearCaiJiBuChaoResult(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BUCHAORESULT, 0).edit();
        editor.clear();
        editor.commit();
    }

    public static void clearPriceAdjustResult(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(DJTZRESULT, 0).edit();
        editor.clear();
        editor.commit();
    }

    public static void clearMeterParamPresetResult(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(DNBCSYZRESULT, 0).edit();
        editor.clear();
        editor.commit();
    }

    public static void clearDnbcsyzDealResult(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(DNBCSYZR, 0).edit();
        editor.clear();
        editor.commit();
    }

    public static void clearUpdateTime(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(UPDATETIME, 0).edit();
        editor.clear();
        editor.commit();
    }

    public static void clearCourseDownFlag(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(COURSEDOWNLOADFLAG, 0).edit();
        editor.clear();
        editor.commit();
    }

    public static void clearCourseModel(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MODEL, 0).edit();
        editor.clear();
        editor.commit();
    }

    public static void clearTestGrade(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(GRADE, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 设置采集频率
     *
     * @param context
     * @param is
     */
    public static void setCOLLRATE(Context context, String is) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                POSITION, 0).edit();
        editor.putString(COLLRATE, is);
        editor.commit();
    }

    /**
     * 获取采集频率
     *
     * @param context
     * @return
     */
    public static String getCOLLRATE(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                POSITION, 0);
        if (preferences.contains(COLLRATE)) {
            return preferences.getString(COLLRATE, "");
        } else {
            return "";
        }
    }

    /**
     * 设置是否打开定位服务
     *
     * @param context
     * @param is
     */
    public static void setIsopen(Context context, String is) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                POSITION, 0).edit();
        editor.putString(ISOPEN, is);
        editor.commit();
    }

    /**
     * 获取是否打开地图服务
     *
     * @param context
     * @return
     */
    public static String getIsopen(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                POSITION, 0);
        if (preferences.contains(ISOPEN)) {
            return preferences.getString(ISOPEN, "");
        } else {
            return "";
        }
    }


    /**
     * 设置是否打开定位服务
     *
     * @param context
     * @param
     */
    public static void setTimer(Context context, boolean isShowTimer) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                TIMERSTATUS, 0).edit();
        editor.putBoolean(TIMER, isShowTimer);
        editor.commit();
    }

    /**
     * 获取是否打开地图服务
     *
     * @param context
     * @return
     */
    public static boolean getTimer(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                TIMERSTATUS, 0);
        if (preferences.contains(TIMER)) {
            return preferences.getBoolean(TIMER, false);
        }
        return false;
    }

    /**
     * 设置未读消息数量
     *
     * @author:yang
     * @Date: 2016/4/21
     * @Time: 15:36
     */
    public static void setNewsNum(Context context, int newsNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(News_Num, newsNum);
        editor.commit();
    }

    /**
     * 获取未读消息数量
     *
     * @author:yang
     * @Date: 2016/4/21
     * @Time: 15:36
     */
    public static int getNewsNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);
        if (preferences.contains(News_Num)) {
            return preferences.getInt(News_Num, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置实验室模式开关
     *
     * @param context
     * @param flag:标记
     */
    public static void setLight(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                LIGHTFLAG, 0).edit();
        editor.putBoolean(LIGHT, flag);
        editor.commit();
    }

    /**
     * 获取实验室模式开关状态
     *
     * @param context
     * @return
     */
    public static boolean getLight(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                LIGHTFLAG, 0);
        if (preferences.contains(LIGHT)) {
            return preferences.getBoolean(LIGHT, false);
        } else {
            return false;
        }
    }

    /**
     * 设置实验室模式开关
     *
     * @param context
     * @param flag:标记
     */
    public static void setTestMode(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                TESTMODE, 0).edit();
        editor.putBoolean(MODE, flag);
        editor.commit();
    }

    /**
     * 获取实验室模式开关状态
     *
     * @param context
     * @return
     */
    public static boolean getTestMode(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                TESTMODE, 0);
        if (preferences.contains(MODE)) {
            return preferences.getBoolean(MODE, false);
        } else {
            return false;
        }
    }


    //保存密钥下装执行结果  1--成功    0--失败
    public static void setMiyueResult(Context context, String appNo, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                MIYUE, 0).edit();
        editor.putString(appNo + "MIYUE", flag);
        editor.commit();
    }

    public static String getMiyueResult(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                MIYUE, 0);
        if (preferences.contains(appNo + "MIYUE")) {
            return preferences.getString(appNo + "MIYUE", "0");
        } else {
            return "0";
        }
    }

    //保存电价调整执行结果  1--成功    0--失败
    public static void setDjtzResult(Context context, String appNo, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                DJTZR, 0).edit();
        editor.putString(appNo + "DJTZR", flag);
        editor.commit();
    }

    public static String getDjtzResult(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                DJTZR, 0);
        if (preferences.contains(appNo + "DJTZR")) {
            return preferences.getString(appNo + "DJTZR", "0");
        } else {
            return "0";
        }
    }

    //保存电能表参数设置执行结果  1--成功    0--失败
    public static void setDnbcsyzDealResult(Context context, String appNo, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                DNBCSYZR, 0).edit();
        editor.putString(appNo + "DNBCSYZR", flag);
        editor.commit();
    }

    public static String getDnbcsyzDealResult(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                DNBCSYZR, 0);
        if (preferences.contains(appNo + "DNBCSYZR")) {
            return preferences.getString(appNo + "DNBCSYZR", "0");
        } else {
            return "0";
        }
    }

    //保存现场校时执行结果  1--成功    0--失败
    public static void setXcjsResult(Context context, String appNo, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                XCJS, 0).edit();
        editor.putString(appNo + "XCJS", flag);
        editor.commit();
    }

    public static String getXcjsResult(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                XCJS, 0);
        if (preferences.contains(appNo + "XCJS")) {
            return preferences.getString(appNo + "XCJS", "0");
        } else {
            return "0";
        }
    }


    //保存充值执行结果  1--成功    0--失败
    public static void setRechargeResult(Context context, String appNo, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                RECHARGE, 0).edit();
        editor.putString(appNo + "RECHARGE", flag);
        editor.commit();
    }

    public static String getRechargeResult(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                RECHARGE, 0);
        if (preferences.contains(appNo + "RECHARGE")) {
            return preferences.getString(appNo + "RECHARGE", "0");
        } else {
            return "0";
        }
    }

    public static void saveRechargeInfo(Context context, String appNo, String info) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                RECHARGE, 0).edit();
        editor.putString(appNo + "RECHARGE", info);
        editor.commit();
    }

    public static String getRechargeInfo(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                RECHARGE, 0);

        if (preferences.contains(appNo + "RECHARGE")) {
            return preferences.getString(appNo + "RECHARGE", "");
        } else {
            return "";
        }
    }
    public static void clearRechargeInfo(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(RECHARGE, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存终端电量下发执行结果
     * @param context
     * @param appNo
     * @param info
     */
    public static void saveZDRechargeInfo(Context context, String appNo, String info) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                ZDRECHARGE, 0).edit();
        editor.putString(appNo + "ZDRECHARGE", info);
        editor.commit();
    }

    public static String getZDRechargeInfo(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                ZDRECHARGE, 0);

        if (preferences.contains(appNo + "ZDRECHARGE")) {
            return preferences.getString(appNo + "ZDRECHARGE", "");
        } else {
            return "";
        }
    }

    public static void clearZDRechargeInfo(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ZDRECHARGE, 0).edit();
        editor.clear();
        editor.commit();
    }



    /**
     * 操作密钥下装结果
     *
     * @param context
     * @param appNo
     * @param info
     */
    public static void saveKeyInfo(Context context, String appNo, String info) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                KEY, 0).edit();
        editor.putString(appNo + "KEY", info);
        editor.commit();
    }

    public static String getKeyInfo(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                KEY, 0);

        if (preferences.contains(appNo + "KEY")) {
            return preferences.getString(appNo + "KEY", "");
        } else {
            return "";
        }
    }

    public static void clearKeyInfo(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 操作采集终端密钥下装结果
     *
     * @param context
     * @param appNo
     * @param info
     */
    public static void saveJzqKeyInfo(Context context, String appNo, String info) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                JZQKEY, 0).edit();
        editor.putString(appNo + "JZQKEY", info);
        editor.commit();
    }

    public static String getJzqKeyInfo(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                JZQKEY, 0);

        if (preferences.contains(appNo + "JZQKEY")) {
            return preferences.getString(appNo + "JZQKEY", "");
        } else {
            return "";
        }
    }

    public static void clearJzqKeyInfo(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(JZQKEY, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 注销登陆时清空
     *
     * @param context
     */
    public static void clearTestMode(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TESTMODE, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 操作员姓名
     *
     * @param context
     * @return
     */
    public static String getUserName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                OPERATE, 0);

        if (preferences.contains(OPERATORNAME)) {
            return preferences.getString(OPERATORNAME, "");
        } else {
            return "";
        }
    }

    public static void setUserName(Context context, String userName) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                OPERATE, 0).edit();
        editor.putString(OPERATORNAME, userName);
        editor.commit();
    }


    /**
     * 操作员号
     *
     * @param context
     * @return
     */
    public static String getOperatorno(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                OPERATE, 0);

        if (preferences.contains(OPERATORNO)) {
            return preferences.getString(OPERATORNO, "");
        } else {
            return "";
        }
    }

    public static void setOperatorno(Context context, String operatorNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                OPERATE, 0).edit();
        editor.putString(OPERATORNO, operatorNo);
        editor.commit();
    }

    /**
     * 归属单位公司编号
     *
     * @param context
     * @return
     */
    public static String getCompanyNo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                COMPANY, 0);

        if (preferences.contains(COMPANYNO)) {
            return preferences.getString(COMPANYNO, "");
        } else {
            return "";
        }
    }

    public static void setCompanyNo(Context context, String CompanyNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                COMPANY, 0).edit();
        editor.putString(COMPANYNO, CompanyNo);
        editor.commit();
    }


    /**
     * 业务员号
     *
     * @param context
     * @return
     */
    public static String getOperationno(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                OPERATE, 0);

        if (preferences.contains(OPERATIONNO)) {
            return preferences.getString(OPERATIONNO, "");
        } else {
            return "";
        }
    }

    public static void setOperationno(Context context, String operationNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                OPERATE, 0).edit();
        editor.putString(OPERATIONNO, operationNo);
        editor.commit();
    }

    //清空保存的业务员卡，操作员卡，服务终端序列号,消缺达人接口返回的数据
    public static void clearUserInfo(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(OPERATE, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 服务终端序列号
     *
     * @param context
     * @return
     */
    public static String getDevsnno(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                DEV, 0);

        if (preferences.contains(DEV_SNNO)) {
            return preferences.getString(DEV_SNNO, "");
        } else {
            return "";
        }
    }

    public static void setSimCardSn(Context context, String simcard_snNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                SIMCARD_SN, 0).edit();
        editor.putString(SIMCARD_SNNO, simcard_snNo);
        editor.commit();
    }

    /**
     * SIM卡序列号
     *
     * @param context
     * @return
     */
    public static String getSimCardSn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                SIMCARD_SN, 0);

        if (preferences.contains(SIMCARD_SNNO)) {
            return preferences.getString(SIMCARD_SNNO, "");
        } else {
            return "";
        }
    }

    public static void setDevsnno(Context context, String operationNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                DEV, 0).edit();
        editor.putString(DEV_SNNO, operationNo);
        editor.commit();
    }

    /**
     * 消缺达人
     *
     * @param context
     * @return
     */
    public static String getFAULTNUM(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                OPERATE, 0);

        if (preferences.contains(FAULTNUM)) {
            return preferences.getString(FAULTNUM, "");
        } else {
            return "";
        }
    }

    public static void setFAULTNUM(Context context, String operationNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                OPERATE, 0).edit();
        editor.putString(FAULTNUM, operationNo);
        editor.commit();
    }

    //清除保存的消缺达人信息
    public static void clearFAULTNUM(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(OPERATE, 0).edit();
        editor.clear();
    }


    /**
     * @param context
     * @param num     0：增量下载   1：全量下载
     */
    public static void setDownFlag(Context context, int num) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERFLAG, 0).edit();
        editor.putInt("Flag", num);
        editor.commit();
    }

    public static int getDownFlag(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERFLAG, 0);
        return preferences.getInt("Flag", 1);
    }

    /**
     * 抢单下载标记
     *
     * @param context
     * @param flag    0：增量下载   1：全量下载
     */
    public static void setQDDownFlag(Context context, int flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                QDWORKORDERFLAG, 0).edit();
        editor.putInt("Flag", flag);
        editor.commit();
    }

    public static int getQDDownFlag(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                QDWORKORDERFLAG, 0);
        return preferences.getInt("Flag", 1);
    }


    /**
     * 主站通讯ip
     *
     * @param context
     * @return
     */
    public static String getCommunicationIp(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                THINTA, 0);

        if (preferences.contains(COMMUNICATION_IP)) {
            return preferences.getString(COMMUNICATION_IP, "");
        } else {
            return "";
        }
    }

    public static void setCommunicationIp(Context context, String communicationIp) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                THINTA, 0).edit();
        editor.putString(COMMUNICATION_IP, communicationIp);
        editor.commit();
    }


    /**
     * 主站通讯port
     *
     * @param context
     * @return
     */
    public static String getCommunicationPort(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                THINTA, 0);

        if (preferences.contains(COMMUNICATION_PORT)) {
            return preferences.getString(COMMUNICATION_PORT, "");
        } else {
            return "";
        }
    }

    public static void setCommunicationPort(Context context, String communicationPort) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                THINTA, 0).edit();
        editor.putString(COMMUNICATION_PORT, communicationPort);
        editor.commit();
    }

    /**
     * 主站通讯apn
     *
     * @param context
     * @return
     */
    public static String getCommunicationApn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                THINTA, 0);

        if (preferences.contains(COMMUNICATION_APN)) {
            return preferences.getString(COMMUNICATION_APN, "");
        } else {
            return "";
        }
    }

    public static void setCommunicationApn(Context context, String communicationApn) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                THINTA, 0).edit();
        editor.putString(COMMUNICATION_APN, communicationApn);
        editor.commit();
    }

    public static String getCommIp(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                THINTA_COMM, 0);

        if (preferences.contains(COMM_IP)) {
            return preferences.getString(COMM_IP, "");
        } else {
            return "";
        }
    }

    public static String getCommIpUSB(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                THINTA_COMM, 0);

        if (preferences.contains(COMM_IP_USB)) {
            return preferences.getString(COMM_IP_USB, "");
        } else {
            return "";
        }
    }

    public static void setCommIp(Context context, String commIP) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                THINTA_COMM, 0).edit();
        editor.putString(COMM_IP, commIP);
        editor.commit();
    }

    public static void setCommUsbEnable(Context context, boolean usbenable) {
        SharedPreferences.Editor editor = context.getSharedPreferences(THINTA_COMM, 0).edit();
        editor.putBoolean(COMM_USBEnable, usbenable);
        editor.commit();
    }

    public static boolean getCommUsbEnable(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(THINTA_COMM, 0);
        if (preferences.contains(COMM_USBEnable)) {
            return preferences.getBoolean(COMM_USBEnable, false);
        } else {
            return false;
        }
    }


    public static void setDangerEnable(Context context, boolean usbenable) {
        SharedPreferences.Editor editor = context.getSharedPreferences(DANGERSWITCH, 0).edit();
        editor.putBoolean(DANGERSWITCH, usbenable);
        editor.commit();
    }

    public static boolean getDangerEnable(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(DANGERSWITCH, 0);
        if (preferences.contains(DANGERSWITCH)) {
            return preferences.getBoolean(DANGERSWITCH, false);
        } else {
            return false;
        }
    }


    public static void setCommIpUSB(Context context, String commIP) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                THINTA_COMM, 0).edit();
        editor.putString(COMM_IP_USB, commIP);
        editor.commit();
    }

    public static String getCommPort(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                THINTA_COMM, 0);

        if (preferences.contains(COMM_PORT)) {
            return preferences.getString(COMM_PORT, "");
        } else {
            return "";
        }
    }

    public static String getCommPortUSB(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                THINTA_COMM, 0);

        if (preferences.contains(COMM_PORT_USB)) {
            return preferences.getString(COMM_PORT_USB, "");
        } else {
            return "";
        }
    }

    public static void setCommPort(Context context, String commPort) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                THINTA_COMM, 0).edit();
        editor.putString(COMM_PORT, commPort);
        editor.commit();
    }

    public static void setCommPortUSB(Context context, String commPort) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                THINTA_COMM, 0).edit();
        editor.putString(COMM_PORT_USB, commPort);
        editor.commit();
    }

    public static String getCommApnName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                THINTA_COMM, 0);

        if (preferences.contains(COMM_APNNAME)) {
            return preferences.getString(COMM_APNNAME, "");
        } else {
            return "";
        }
    }

    public static void setCommApnName(Context context, String apnname) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                THINTA_COMM, 0).edit();
        editor.putString(COMM_APNNAME, apnname);
        editor.commit();
    }

    public static String getCommApn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                THINTA_COMM, 0);

        if (preferences.contains(COMM_APN)) {
            return preferences.getString(COMM_APN, "");
        } else {
            return "";
        }
    }

    public static void setCommApn(Context context, String apn) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                THINTA_COMM, 0).edit();
        editor.putString(COMM_APN, apn);
        editor.commit();
    }

    public static String getCommUserName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                THINTA_COMM, 0);

        if (preferences.contains(COMM_USER_NAME)) {
            return preferences.getString(COMM_USER_NAME, "");
        } else {
            return "";
        }
    }

    public static void setCommUserName(Context context, String apn) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                THINTA_COMM, 0).edit();
        editor.putString(COMM_USER_NAME, apn);
        editor.commit();
    }

    public static String getCommUserPswd(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                THINTA_COMM, 0);

        if (preferences.contains(COMM_USER_PSWD)) {
            return preferences.getString(COMM_USER_PSWD, "");
        } else {
            return "";
        }
    }

    public static void setCommUserPswd(Context context, String apn) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                THINTA_COMM, 0).edit();
        editor.putString(COMM_USER_PSWD, apn);
        editor.commit();
    }


    /**
     * 获取工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/21
     * @Time: 15:36
     */
    public static int getAppNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(APP_NUM)) {
            return preferences.getInt(APP_NUM, 0);
        } else {
            return 0;
        }
    }


    /**
     * 设置工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/21
     * @Time: 15:36
     */
    public static void setAppNum(Context context, int appNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(APP_NUM, appNum);
        editor.commit();
    }


    /**
     * 获取异常工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/27
     * @Time: 13:00
     */
    public static int getYcgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(YCGD_NUM)) {
            return preferences.getInt(YCGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置异常工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/27
     * @Time: 13:04
     */
    public static void setYcgdNum(Context context, int ycgd) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(YCGD_NUM, ycgd);
        editor.commit();
    }

    /**
     * 获取已领工单数量
     */
    public static int getYlgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(YLGD_NUM)) {
            return preferences.getInt(YLGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置已领工单数量
     */
    public static void setYlgdNum(Context context, int ylgd) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(YLGD_NUM, ylgd);
        editor.commit();
    }

    /**
     * 获取补抄工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/27
     * @Time: 13:05
     */
    public static int getBcgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(BCGD_NUM)) {
            return preferences.getInt(BCGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置补抄工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/27
     * @Time: 13:06
     */
    public static void setBcgdNum(Context context, int bcgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(BCGD_NUM, bcgdNum);
        editor.commit();
    }

    /**
     * 获取费控工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/27
     * @Time: 13:06
     */
    public static int getFkgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(FKGD_NUM)) {
            return preferences.getInt(FKGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置费控工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/27
     * @Time: 13:12
     */
    public static void setFkgdNum(Context context, int fkgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(FKGD_NUM, fkgdNum);
        editor.commit();
    }

    /**
     * 获取巡视工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/27
     * @Time: 13:07
     */
    public static int getXsgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(XSGD_NUM)) {
            return preferences.getInt(XSGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置巡视工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/27
     * @Time: 13:12
     */
    public static void setXsgdNum(Context context, int xsgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(XSGD_NUM, xsgdNum);
        editor.commit();
    }

    /**
     * 设置校时工单数量
     */
    public static void setJsgdNum(Context context, int jsgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(JSGD_NUM, jsgdNum);
        editor.commit();
    }

    /**
     * 获取校时工单数量  2
     */
    public static int getJsgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(JSGD_NUM)) {
            return preferences.getInt(JSGD_NUM, 0);
        } else {
            return 0;
        }
    }


    /**
     * 设置文件大小
     */
    public static void setFileSize(Context context, long filesize) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "filesize", 0).edit();
        editor.putLong("filesize", filesize);
        editor.commit();
    }


    public static long getFileSize(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "filesize", 0);

        if (preferences.contains("filesize")) {
            return preferences.getLong("filesize", 0);
        } else {
            return 0;
        }
    }




    /**
     * 设置充值工单数量
     */
    public static void setCzgdNum(Context context, int czgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(CZGD_NUM, czgdNum);
        editor.commit();
    }

    /**
     * 获取充值工单数量
     */
    public static int getCzgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(CZGD_NUM)) {
            return preferences.getInt(CZGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置密钥下装工单数量
     */
    public static void setMyxzNum(Context context, int myxzNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(MYGD_NUM, myxzNum);
        editor.commit();
    }

    /**
     * 获取密钥下装工单数量
     */
    public static int getMyxzNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);
        if (preferences.contains(MYGD_NUM)) {
            return preferences.getInt(MYGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置采集终端密钥下装工单数量
     */
    public static void setJzqMyxzNum(Context context, int myxzNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(JZQMYGD_NUM, myxzNum);
        editor.commit();
    }

    /**
     * 获取采集终端密钥下装工单数量
     */
    public static int getJzqMyxzNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);
        if (preferences.contains(JZQMYGD_NUM)) {
            return preferences.getInt(JZQMYGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置疑难工单数量
     */
    public static void setDiffNum(Context context, int diffNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(DIFFIGD_NUM, diffNum);
        editor.commit();
    }

    /**
     * 获取疑难工单数量
     */
    public static int getDiffNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);
        if (preferences.contains(DIFFIGD_NUM)) {
            return preferences.getInt(DIFFIGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置补抄失败原因下拉框position
     */
    public static void setBcPosition(Context context, String appNo, int position) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                BCPOSITION + appNo, 0).edit();
        editor.putInt(BC_POSITION + appNo, position);
        editor.commit();
    }

    /**
     * 获取补抄失败原因下拉框position
     */
    public static int getBcPosition(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                BCPOSITION + appNo, 0);
        if (preferences.contains(BC_POSITION + appNo)) {
            return preferences.getInt(BC_POSITION + appNo, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置现场电价调整工单数量
     */
    public static void setDjtzNum(Context context, int djtzNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(DJTZGD_NUM, djtzNum);
        editor.commit();
    }

    /**
     * 获取现场电价调整工单数量
     */
    public static int getDjtzNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);
        if (preferences.contains(DJTZGD_NUM)) {
            return preferences.getInt(DJTZGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置现场电价时段调整工单数量
     */
    public static void setDjsdtzNum(Context context, int djsdtzNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(DJSDTZGD_NUM, djsdtzNum);
        editor.commit();
    }

    /**
     * 获取现场电价时段调整工单数量
     */
    public static int getDjsdtzNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);
        if (preferences.contains(DJSDTZGD_NUM)) {
            return preferences.getInt(DJSDTZGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 获取计量工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/27
     * @Time: 13:08
     */
    public static int getJlgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(JLGD_NUM)) {
            return preferences.getInt(JLGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置计量工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/27
     * @Time: 13:12
     */
    public static void setJlgdNum(Context context, int jlgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(JLGD_NUM, jlgdNum);
        editor.commit();
    }

    /**
     * 获取计量异常工单数量
     */
    public static int getJlycgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(JLYCGD_NUM)) {
            return preferences.getInt(JLYCGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置计量异常工单数量
     */
    public static void setJlycgdNum(Context context, int jlycgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(JLYCGD_NUM, jlycgdNum);
        editor.commit();
    }

    /**
     * 获取电能表参数设置工单数量
     */
    public static int getDnbcsyzgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(DNBCSYZGD_NUM)) {
            return preferences.getInt(DNBCSYZGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置采集终端新装工单数量
     */
    public static void setDnbcsyzgdNum(Context context, int dnbcsyzgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(DNBCSYZGD_NUM, dnbcsyzgdNum);
        editor.commit();
    }

    /**
     * 获取采集终端新装工单数量
     */
    public static int getCjzdxzgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(DNBCSYZGD_NUM)) {
            return preferences.getInt(DNBCSYZGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置采集终端更换工单数量
     */
    public static void setCjzdxzgdNum(Context context, int dnbcsyzgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(DNBCSYZGD_NUM, dnbcsyzgdNum);
        editor.commit();
    }

    /**
     * 获取采集终端更换工单数量
     */
    public static int getCjzdghgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(DNBCSYZGD_NUM)) {
            return preferences.getInt(DNBCSYZGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置采集终端更换工单数量
     */
    public static void setCjzdghgdNum(Context context, int dnbcsyzgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(DNBCSYZGD_NUM, dnbcsyzgdNum);
        editor.commit();
    }

    /**
     * 获取采集终端拆除工单数量
     */
    public static int getCjzdccgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(DNBCSYZGD_NUM)) {
            return preferences.getInt(DNBCSYZGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置采集终端拆除工单数量
     */
    public static void setCjzdccgdNum(Context context, int dnbcsyzgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(DNBCSYZGD_NUM, dnbcsyzgdNum);
        editor.commit();
    }

    /**
     * 获取电能表新装工单数量
     */
    public static int getDnbxzgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(DNBCSYZGD_NUM)) {
            return preferences.getInt(DNBCSYZGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置电能表新装工单数量
     */
    public static void setDnbxzgdNum(Context context, int dnbcsyzgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(DNBCSYZGD_NUM, dnbcsyzgdNum);
        editor.commit();
    }

    /**
     * 获取电能表更换工单数量
     */
    public static int getDnbghgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(DNBCSYZGD_NUM)) {
            return preferences.getInt(DNBCSYZGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置电能表更换工单数量
     */
    public static void setDnbghgdNum(Context context, int dnbcsyzgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(DNBCSYZGD_NUM, dnbcsyzgdNum);
        editor.commit();
    }

    /**
     * 获取电能表拆除工单数量
     */
    public static int getDnbccgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(DNBCSYZGD_NUM)) {
            return preferences.getInt(DNBCSYZGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置电能表拆除工单数量
     */
    public static void setDnbccgdNum(Context context, int dnbcsyzgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(DNBCSYZGD_NUM, dnbcsyzgdNum);
        editor.commit();
    }

    /**
     * 获取档案核查工单数量
     */
    public static int getArchiveCheckNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(ARCHIVE_CHECK_NUM)) {
            return preferences.getInt(ARCHIVE_CHECK_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置档案核查工单数量
     */
    public static void setArchiveCheckNum(Context context, int num) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(ARCHIVE_CHECK_NUM, num);
        editor.commit();
    }

    /**
     * 获取终端电量下发工单数量
     */
    public static int getZDRechargeNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);

        if (preferences.contains(ZD_RECHARGE_NUM)) {
            return preferences.getInt(ZD_RECHARGE_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置终端电量下发工单数量
     */
    public static void setZDRechargeNum(Context context, int num) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(ZD_RECHARGE_NUM, num);
        editor.commit();
    }


    /**
     * 获取点击的位置
     */
    public static int getClickPosition(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "clickposition", 0);

        if (preferences.contains("clickposition")) {
            return preferences.getInt("clickposition", -1);
        } else {
            return -1;
        }
    }

    /**
     * 设置电能表拆除工单数量
     */
    public static void setClickPosition(Context context, int position) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "clickposition", 0).edit();
        editor.putInt("clickposition", position);
        editor.commit();
    }
    /**
     * 获取点击的位置
     */
    public static int getDangerPosition(Context context,String reson_code) {
        SharedPreferences preferences = context.getSharedPreferences(
                "Dangerclickposition"+reson_code, 0);

        if (preferences.contains("Dangerclickposition"+reson_code)) {
            return preferences.getInt("Dangerclickposition"+reson_code, -1);
        } else {
            return -1;
        }
    }

    /**
     * 设置电能表拆除工单数量
     */
    public static void setDangerPosition(Context context, int position,String reson_code) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "Dangerclickposition"+reson_code, 0).edit();
        editor.putInt("Dangerclickposition"+reson_code, position);
        editor.commit();
    }


    /**
     * 获取停复电工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/27
     * @Time: 13:08
     */
    public static int getTfdgdNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKORDERINFO, 0);
        if (preferences.contains(TFDGD_NUM)) {
            return preferences.getInt(TFDGD_NUM, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置停复电工单数量
     *
     * @author:GuangJie-Wang
     * @Date: 2016/4/27
     * @Time: 13:12
     */
    public static void setTfdgdNum(Context context, int tfdgdNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKORDERINFO, 0).edit();
        editor.putInt(TFDGD_NUM, tfdgdNum);
        editor.commit();
    }

    /**
     * 知识库版本号保存
     *
     * @param context 上下文
     * @param nvk     版本号
     */
    public static void setKnowledgeNVK(Context context, String nvk) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                KNOWLEDGE, 0).edit();
        editor.putString(NVK, nvk);
        editor.commit();
    }

    /**
     * 知识库版本号获取
     *
     * @param context 上下文
     * @return 知识库版本号
     */
    public static String getKnowledgeNVK(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                KNOWLEDGE, 0);

        if (preferences.contains(NVK)) {
            return preferences.getString(NVK, "");
        } else {
            return "";
        }
    }

    /**
     * 设置典型案例库同步时间
     *
     * @param context 上下文
     * @param time    同步时间
     */
    public static void setUpdateClassicTime(Context context, String time) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                UPDATECLASSIC, 0).edit();
        editor.putString(CLASSICTIME, time);
        editor.commit();
    }

    /**
     * 获取典型案例库同步时间
     *
     * @param context 上下文
     * @return 同步时间
     */
    public static String getUpdateClassicTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                UPDATECLASSIC, 0);
        if (preferences.contains(CLASSICTIME)) {
            return preferences.getString(CLASSICTIME, "");
        } else {
            return "";
        }
    }

    /**
     * 知识库更新时间
     *
     * @param context  上下文
     * @param lastTime 更新时间
     */
    public static void setKnowledgeLastTime(Context context, String lastTime) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                KNOWLEDGE, 0).edit();
        editor.putString(LAST_TIME, lastTime);
        editor.commit();
    }

    /**
     * 知识库上次更新时间获取
     *
     * @param context 上下文
     * @return 知识库更新时间
     */
    public static String getKnowledgeLastTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                KNOWLEDGE, 0);
        if (preferences.contains(LAST_TIME)) {
            return preferences.getString(LAST_TIME, "");
        } else {
            return "";
        }
    }

    /**
     * 经纬度上传方式
     *
     * @param context   上下文
     * @param loadStyle 上传方式   1：采集后实时上传
     *                  2：工单同步时上传
     *                  3：定时上传
     */
    public static void setLoadStyle(Context context, int loadStyle) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                POSITION_TIME, 0).edit();
        editor.putInt(LOAD_STYLE, loadStyle);
        editor.commit();
    }

    /**
     * 经纬度上传方式
     *
     * @param context 上下文
     * @return 经纬度上传方式
     */
    public static int getLoadStyle(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                POSITION_TIME, 0);
        if (preferences.contains(LOAD_STYLE)) {
            return preferences.getInt(LOAD_STYLE, -1);
        } else {
            return -1;
        }
    }

    /**
     * 设置经纬度上传周期
     *
     * @param context  上下文
     * @param lastTime 经纬度上传周期
     */
    public static void setPositonTime(Context context, String lastTime) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                POSITION_TIME, 0).edit();
        editor.putString(TIME, lastTime);
        editor.commit();
    }

    /**
     * 获取经纬度上传周期
     *
     * @param context 上下文
     * @return 经纬度上传周期
     */
    public static String getPositonTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                POSITION_TIME, 0);
        if (preferences.contains(TIME)) {
            return preferences.getString(TIME, null);
        } else {
            return null;
        }
    }

    /**
     * 未读消息个数
     *
     * @param context
     * @param number
     */
    public static void setnumber(Context context, int number) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "Notify_number", 0).edit();
        editor.putInt("notify", number);
        editor.commit();
    }


    public static int getnumber(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "Notify_number", 0);
        if (preferences.contains("notify")) {
            return preferences.getInt("notify", 0);
        }
        return 0;
    }


    /**
     * 学习次数
     */

    public static void setStudyNumber(Context context, int number,String course_no) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "study_no"+course_no, 0).edit();
        editor.putInt("study_no"+course_no, number);
        editor.commit();
    }


    public static int getStudyNumber(Context context,String course_no) {
        SharedPreferences preferences = context.getSharedPreferences(
                "study_no"+course_no, 0);
        if (preferences.contains("study_no"+course_no)) {
            return preferences.getInt("study_no"+course_no, 0);
        }
        return 0;
    }

    /**
     * 最高分
     */

    public static void setHighestGrade(Context context, int number,String course_no) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "highest"+course_no, 0).edit();
        editor.putInt("highest"+course_no, number);
        editor.commit();
    }


    public static int getHighestGrade(Context context,String course_no) {
        SharedPreferences preferences = context.getSharedPreferences(
                "highest"+course_no, 0);
        if (preferences.contains("highest"+course_no)) {
            return preferences.getInt("highest"+course_no, 0);
        }
        return 0;
    }

    /**
     * 做题数量
     */

    public static void setPracticeNo(Context context, int number,String course_no) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "practice"+course_no, 0).edit();
        editor.putInt("practice"+course_no, number);
        editor.commit();
    }


    public static int getPracticeNo(Context context,String course_no) {
        SharedPreferences preferences = context.getSharedPreferences(
                "practice"+course_no, 0);
        if (preferences.contains("practice"+course_no)) {
            return preferences.getInt("practice"+course_no, 0);
        }
        return 0;
    }

    /**
     * 执行时间
     */
    public static void setExTime(Context context, String oldTime,String appno) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "extime"+appno, 0).edit();
        editor.putString( "extime"+appno, oldTime);
        editor.commit();
    }


    public static String getExTime(Context context,String appno) {
        SharedPreferences preferences = context.getSharedPreferences(
                "extime"+appno, 0);
        if (preferences.contains( "extime"+appno)) {
            return preferences.getString( "extime"+appno, null);
        } else {
            return null;
        }
    }
    /**
     * 校时前时间
     */
    public static void setOldTime(Context context, String oldTime,String appno) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                OLDTIME+appno, 0).edit();
        editor.putString(O_TIME+appno, oldTime);
        editor.commit();
    }


    public static String getOldTime(Context context,String appno) {
        SharedPreferences preferences = context.getSharedPreferences(
                OLDTIME+appno, 0);
        if (preferences.contains(O_TIME+appno)) {
            return preferences.getString(O_TIME+appno, null);
        } else {
            return null;
        }
    }

    /**
     * 校时后时间
     */
    public static void setNewTime(Context context, String oldTime,String appno) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                NEWTIME+appno, 0).edit();
        editor.putString(N_TIME+appno, oldTime);
        editor.commit();
    }


    public static String getNewTime(Context context,String appno) {
        SharedPreferences preferences = context.getSharedPreferences(
                NEWTIME+appno, 0);
        if (preferences.contains(N_TIME+appno)) {
            return preferences.getString(N_TIME+appno, null);
        } else {
            return null;
        }
    }


    /**
     * 现场补抄工单工单号
     *
     * @param context
     * @return
     */
    public static String getXcAppNo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                XC, 0);

        if (preferences.contains(XCAPPNO)) {
            return preferences.getString(XCAPPNO, "");
        } else {
            return "";
        }
    }

    public static void setXcAppNo(Context context, String xcAppNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                XC, 0).edit();
        editor.putString(XCAPPNO, xcAppNo);
        editor.commit();
    }

    public static String getVersion(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != appInfo) {
                if (appInfo.metaData != null) {
                    String appVrsionCode = appInfo.metaData.getString("appVrsionCode");
                    LogUtil.d("TAG", "appVrsionCode=" + appVrsionCode);
                    return TextUtils.isEmpty(appVrsionCode) ? "00.00.00.00" : appVrsionCode;
                } else {
                    return "00.00.00.00";
                }
            } else {
                return "00.00.00.00";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "00.00.00.00";
        }
    }

    /**
     * 保存异常原因
     *
     * @param context
     * @param reason
     */
    public static void setReason(Context context, String app_no, String reason) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                CONFIG, 0).edit();
        editor.putString(app_no, reason);
        editor.commit();
    }

    public static String getReason(Context context, String app_no) {
        SharedPreferences preferences = context.getSharedPreferences(
                CONFIG, 0);

        if (preferences.contains(app_no)) {
            return preferences.getString(app_no, "");
        } else {
            return "";
        }
    }


    public static void setErrorReason(Context context, String appNo, String reason) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                ERRORREASON, 0).edit();
        editor.putString(appNo, reason);
        editor.commit();
    }

    public static String getErrorReason(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                ERRORREASON, 0);

        if (preferences.contains(appNo)) {
            return preferences.getString(appNo, "");
        } else {
            return "";
        }
    }

    /**
     * 设置用户点击的按钮
     *
     * @param context
     * @param app_no  工单号
     * @param flag    01：停复电   02：校时
     */
    public static void setChoose(Context context, String app_no, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                CHOOSE, 0).edit();
        editor.putString(app_no, flag);
        editor.commit();
    }

    public static String getChoose(Context context, String app_no) {
        SharedPreferences preferences = context.getSharedPreferences(
                CHOOSE, 0);

        if (preferences.contains(app_no)) {
            return preferences.getString(app_no, "");
        } else {
            return "";
        }
    }

    //清空选择按钮的SP
    public static void clearChooseSP(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(CHOOSE, 0).edit();
//        editor.clear();
        editor.remove(appNo);
        editor.commit();
    }

    /**
     * 保存停复电结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setTFResult(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                TFRESULT, 0).edit();
        editor.putString(appNo + "TFResult", result);
        editor.commit();
    }

    public static String getTFResult(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                TFRESULT, 0);

        if (preferences.contains(appNo + "TFResult")) {
            return preferences.getString(appNo + "TFResult", "");
        } else {
            return "";
        }
    }


    /**
     * 根据时间保存消息json
     */
    public static void setJsonWithTime(Context context, String time, String json) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                NEWSJSON + time, 0).edit();
        editor.putString(NEWSJSON + time, json);
        editor.commit();
    }

    public static String getJsonWithTime(Context context, String time) {
        SharedPreferences preferences = context.getSharedPreferences(
                NEWSJSON + time, 0);

        if (preferences.contains(NEWSJSON + time)) {
            return preferences.getString(NEWSJSON + time, "");
        } else {
            return "";
        }
    }

    /**
     * 根据时间保存localjson
     */
    public static void setLocalJsonWithTime(Context context, String time, String json) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                LOCALJSON, 0).edit();
        editor.putString("localjson", json);
        editor.commit();
    }

    public static String getLocalJsonWithTime(Context context, String time) {
        SharedPreferences preferences = context.getSharedPreferences(
                LOCALJSON, 0);

        if (preferences.contains("localjson")) {
            return preferences.getString("localjson", "");
        } else {
            return "";
        }
    }

    /**
     * 保存电价调整结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setPriceAdjustResult(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                DJTZRESULT, 0).edit();
        editor.putString(appNo + "DJResult", result);
        editor.commit();
    }

    public static String getPriceAdjustResult(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                DJTZRESULT, 0);

        if (preferences.contains(appNo + "DJResult")) {
            return preferences.getString(appNo + "DJResult", "");
        } else {
            return "";
        }
    }

    /**
     * 保存电能表参数设置结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setMeterParamPresetResult(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                DNBCSYZRESULT, 0).edit();
        editor.putString(appNo + "DnbcsyzResult", result);
        editor.commit();
    }

    public static String getMeterParamPresetResult(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                DNBCSYZRESULT, 0);

        if (preferences.contains(appNo + "DnbcsyzResult")) {
            return preferences.getString(appNo + "DnbcsyzResult", "");
        } else {
            return "";
        }
    }

    /**
     * 保存现场校时结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setJsResult(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                JSRESULT, 0).edit();
        editor.putString(appNo + "JSResult", result);
        editor.commit();
    }

    public static String getJsResult(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                JSRESULT, 0);

        if (preferences.contains(appNo + "JSResult")) {
            return preferences.getString(appNo + "JSResult", "");
        } else {
            return "";
        }
    }

    public static void clearJsResult(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(JSRESULT, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存充值结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setCZReson(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                CZRESON, 0).edit();
        editor.putString(appNo + "CZReson", result);
        editor.commit();
    }

    public static String getCZReson(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                CZRESON, 0);

        if (preferences.contains(appNo + "CZReson")) {
            return preferences.getString(appNo + "CZReson", "");
        } else {
            return "";
        }
    }
    /**
     * 保存终端电量下发失败原因
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setZDRechargeReson(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "ZDRechargeReson", 0).edit();
        editor.putString(appNo + "ZDRechargeReson", result);
        editor.commit();
    }

    public static String getZDRechargeReson(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                "ZDRechargeReson", 0);

        if (preferences.contains(appNo + "ZDRechargeReson")) {
            return preferences.getString(appNo + "ZDRechargeReson", "");
        } else {
            return "";
        }
    }

    public static void clearZDRechargeReson(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ZDRechargeReson", 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存计量异常结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setJLYCReson(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                JLYCREASON, 0).edit();
        editor.putString(appNo + "JLYCReson", result);
        editor.commit();
    }

    public static String getJLYCReson(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                JLYCREASON, 0);

        if (preferences.contains(appNo + "JLYCReson")) {
            return preferences.getString(appNo + "JLYCReson", "");
        } else {
            return "";
        }
    }

    /**
     * 保存校时结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setJSReson(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                JSRESON, 0).edit();
        editor.putString(appNo + "JSReson", result);
        editor.commit();
    }

    public static String getJSReson(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                JSRESON, 0);

        if (preferences.contains(appNo + "JSReson")) {
            return preferences.getString(appNo + "JSReson", "");
        } else {
            return "";
        }
    }

    /**
     * 保存电价调整结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setDjtzReson(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                DJTZRESON, 0).edit();
        editor.putString(appNo + "DjtzReson", result);
        editor.commit();
    }

    public static String getDjtzReson(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                DJTZRESON, 0);

        if (preferences.contains(appNo + "DjtzReson")) {
            return preferences.getString(appNo + "DjtzReson", "");
        } else {
            return "";
        }
    }

    /**
     * 保存电能表参数设置结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setDnbcsyzReson(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                DNBCSYZREASON, 0).edit();
        editor.putString(appNo + "DnbcsyzReson", result);
        editor.commit();
    }

    public static String getDnbcsyzReson(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                DNBCSYZREASON, 0);

        if (preferences.contains(appNo + "DnbcsyzReson")) {
            return preferences.getString(appNo + "DnbcsyzReson", "");
        } else {
            return "";
        }
    }

    //删除电能表参数设置 失败原因
    public static void clearDnbcsyzReson(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(DNBCSYZREASON, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存密钥下装结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setMyReson(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                MYRESON, 0).edit();
        editor.putString(appNo + "MyReson", result);
        editor.commit();
    }

    public static String getMyReson(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                MYRESON, 0);

        if (preferences.contains(appNo + "MyReson")) {
            return preferences.getString(appNo + "MyReson", "");
        } else {
            return "";
        }
    }

    /**
     * 保存采集终端密钥下装失败原因
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setJzqmyReson(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                JZQMYRESON, 0).edit();
        editor.putString(appNo + "JZQMyReson", result);
        editor.commit();
    }

    public static String getJzqmyReson(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                JZQMYRESON, 0);

        if (preferences.contains(appNo + "JZQMyReson")) {
            return preferences.getString(appNo + "JZQMyReson", "");
        } else {
            return "";
        }
    }

    public static void setSbbh(Context context, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                ETSBBH, 0).edit();
        editor.putString("ETSBBH", result);
        editor.commit();
    }

    public static String getSbbh(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                ETSBBH, 0);

        if (preferences.contains("ETSBBH")) {
            return preferences.getString("ETSBBH", "");
        } else {
            return "";
        }
    }

    /**
     * 保存停复电结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setTFReson(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                TFRESON, 0).edit();
        editor.putString(appNo + "TFReson", result);
        editor.commit();
    }

    public static String getTFReson(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                TFRESON, 0);

        if (preferences.contains(appNo + "TFReson")) {
            return preferences.getString(appNo + "TFReson", "");
        } else {
            return "";
        }
    }

    /**
     * 保存停复电结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setTFExcTime(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                TFEXCTIME, 0).edit();
        editor.putString(appNo + "TFExctime", result);
        editor.commit();
    }

    public static String getTFExcTime(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                TFEXCTIME, 0);

        if (preferences.contains(appNo + "TFExctime")) {
            return preferences.getString(appNo + "TFExctime", "");
        } else {
            return "";
        }
    }

    /**
     * 保存现场补抄结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setBuChaoInfo(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                BUCHAOINFO, 0).edit();
        editor.putString(appNo + "BUCHAOINFO", result);
        editor.commit();
    }

    public static String getBuChaoInfo(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                BUCHAOINFO, 0);

        if (preferences.contains(appNo + "BUCHAOINFO")) {
            return preferences.getString(appNo + "BUCHAOINFO", "");
        } else {
            return "";
        }
    }

    /**
     * 保存现场补抄结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setBuChaoResult(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                BUCHAORESULT, 0).edit();
        editor.putString(appNo + "BuChaoResult", result);
        editor.commit();
    }

    public static String getBuChaoResult(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                BUCHAORESULT, 0);

        if (preferences.contains(appNo + "BuChaoResult")) {
            return preferences.getString(appNo + "BuChaoResult", "");
        } else {
            return "";
        }
    }


    /**
     * 保存采集补抄结果
     *
     * @param context
     * @param appNo
     * @param result
     */
    public static void setCaiJiBuChaoResult(Context context, String appNo, String result) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                BUCHAORESULT, 0).edit();
        editor.putString(appNo + "CaiJiBuChaoResult", result);
        editor.commit();
    }

    public static String getCaiJiBuChaoResult(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                BUCHAORESULT, 0);

        if (preferences.contains(appNo + "CaiJiBuChaoResult")) {
            return preferences.getString(appNo + "CaiJiBuChaoResult", "");
        } else {
            return "";
        }
    }

    /**
     * 保存诊断方法
     *
     * @param context
     * @param
     */
    public static void setMethod(Context context, String app_no, String method) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                ZHENGDUAN, 0).edit();
        editor.putString(app_no, method);
        editor.commit();
    }

    public static String getMethod(Context context, String app_no) {
        SharedPreferences preferences = context.getSharedPreferences(
                ZHENGDUAN, 0);

        if (preferences.contains(app_no)) {
            return preferences.getString(app_no, "");
        } else {
            return "";
        }
    }

    /**
     * 保存修复方法
     *
     * @param context
     * @param
     */
    public static void setFix(Context context, String app_no, String method) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                XIUFU, 0).edit();
        editor.putString(app_no, method);
        editor.commit();
    }

    public static String getFix(Context context, String app_no) {
        SharedPreferences preferences = context.getSharedPreferences(
                XIUFU, 0);

        if (preferences.contains(app_no)) {
            return preferences.getString(app_no, "");
        } else {
            return "";
        }
    }


    /**
     * 设置显示更新内容的标记
     */
    public static void setUpdateContent(Context context, String updateFlag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                CONTENTUPDATE, 0).edit();
        editor.putString(UPDATE_FLAG, updateFlag);
        editor.commit();

    }

    /**
     * 获取显示更新内容的标记
     */
    public static String getUpdateContent(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                CONTENTUPDATE, 0);
        if (preferences.contains(UPDATE_FLAG)) {
            return preferences.getString(UPDATE_FLAG, "");
        } else {
            return "";
        }
    }

    //清空显示更新内容的标记
    public static void clearUpdateContent(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(CONTENTUPDATE, 0).edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 设置登录方式 0  无需登录人  1 需要登录人
     */
    public static void setLoginFlag(Context context, String loginFlag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                LOGINFLAG, 0).edit();
        editor.putString(LOGIN_FLAG, loginFlag);
        editor.commit();
    }

    /**
     * 获取登录方式
     */
    public static String getLoginFlag(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                LOGINFLAG, 0);
        if (preferences.contains(LOGIN_FLAG)) {
            return preferences.getString(LOGIN_FLAG, "");
        } else {
            return "";
        }
    }

    /**
     * 设置登录人账号
     */
    public static void setUserCode(Context context, String user_code) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                USERCODE, 0).edit();
        editor.putString(USER_CODE, user_code);
        editor.commit();
    }

    /**
     * 获取登录人账号
     */
    public static String getUserCode(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                USERCODE, 0);
        if (preferences.contains(USER_CODE)) {
            return preferences.getString(USER_CODE, "");
        } else {
            return "";
        }
    }


    /**
     * 设置登录人账号
     */
    public static void setUserID(Context context, String id) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                USERFLAG, 0).edit();
        editor.putString(USERID, id);
        editor.commit();
    }


    /**
     * 获取登录人账号
     */
    public static String getUserID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                USERFLAG, 0);
        if (preferences.contains(USERID)) {
            return preferences.getString(USERID, "");
        } else {
            return "";
        }
    }


    /**
     * 设置是否更新的标志
     *
     * @param context
     * @param
     */
    public static void setFlag(Context context, boolean isShowContent) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                CONTENTFLAG, 0).edit();
        editor.putBoolean(CONTENT, isShowContent);
        editor.commit();
    }

    /**
     * 获取是否打开地图服务
     *
     * @param context
     * @return
     */
    public static boolean getFlag(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                CONTENTFLAG, 0);
        if (preferences.contains(CONTENT)) {
            return preferences.getBoolean(CONTENT, false);
        }
        return false;
    }

    /**
     * 设置是否已经全部下载过
     *
     * @param context
     * @param
     */
    public static void setDownAll(Context context, boolean isDownAll, String usercode) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                DOWNALL + usercode, 0).edit();
        editor.putBoolean(DOWN, isDownAll);
        editor.commit();
    }

    /**
     * 获取当前操作员是否已经全部下载过
     *
     * @param context
     * @return
     */
    public static boolean getDownAll(Context context, String usercode) {
        SharedPreferences preferences = context.getSharedPreferences(
                DOWNALL + usercode, 0);
        if (preferences.contains(DOWN)) {
            return preferences.getBoolean(DOWN, false);
        }
        return false;
    }

    /**
     * 是否下拉刷新的标志
     *
     * @param context
     * @param flag
     */
    public static void setWorkFlag(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WORKFLAG, 0).edit();
        editor.putBoolean(WORK, flag);
        editor.commit();
    }

    public static boolean getWorkFlag(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WORKFLAG, 0);
        if (preferences.contains(WORK)) {
            return preferences.getBoolean(WORK, false);
        }
        return false;
    }

    public static void clearWorkFlag(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(WORKFLAG, 0).edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 是否是末端融合功能
     *
     * @param context
     * @param flag
     */
    public static void setScanDown(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                SCANFLAG, 0).edit();
        editor.putBoolean(SCAN, flag);
        editor.commit();
    }

    public static boolean getScanDown(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                SCANFLAG, 0);
        if (preferences.contains(SCAN)) {
            return preferences.getBoolean(SCAN, false);
        }
        return false;
    }

    //设置错误个数
    public static void setWrongNum(Context context, int wrong) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                WRONGNUM, 0).edit();
        editor.putInt(WRONGNUM, wrong);
        editor.commit();
    }

    public static int getWrongNum(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                WRONGNUM, 0);
        if (preferences.contains(WRONGNUM)) {
            return preferences.getInt(WRONGNUM, 0);
        }
        return 0;
    }

    //根据考试课程编号设置考试成绩
    public static void setGrade(Context context, String course_no, int grade) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                GRADE + course_no, 0).edit();
        editor.putInt(GRADE + course_no, grade);
        editor.commit();
    }

    public static int getGrade(Context context, String course_no) {
        SharedPreferences preferences = context.getSharedPreferences(
                GRADE + course_no, 0);
        if (preferences.contains(GRADE + course_no)) {
            return preferences.getInt(GRADE + course_no, 0);
        }
        return 0;
    }

    /**
     * 是否是背题模式
     *
     * @param context
     * @param flag
     */
    public static void setModel(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                MODEL, 0).edit();
        editor.putBoolean(MODEL, flag);
        editor.commit();
    }

    public static boolean getModel(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                MODEL, 0);
        if (preferences.contains(MODEL)) {
            return preferences.getBoolean(MODEL, false);
        }
        return false;
    }

    /**
     * 是否收藏
     */
    public static void setCollection(Context context, int position, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                COLLECTION + position, 0).edit();
        editor.putString(COLLECTION + position, flag);
        editor.commit();
    }

    public static String getCollection(Context context, int position) {
        SharedPreferences preferences = context.getSharedPreferences(
                COLLECTION + position, 0);
        if (preferences.contains(COLLECTION + position)) {
            return preferences.getString(COLLECTION + position, "0");
        }
        return "0";
    }

    /**
     * 示值核查是否平
     *
     * @param context
     * @param flag
     */
    public static void setSzhc(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                SZHCISPING, 0).edit();
        editor.putBoolean(SZHCRESULT, flag);
        editor.commit();
    }

    public static boolean getSzhc(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                SZHCISPING, 0);
        if (preferences.contains(SZHCRESULT)) {
            return preferences.getBoolean(SZHCRESULT, false);
        }
        return false;
    }

    public static void clearSzhc(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SZHCISPING, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 是否进行了示值核查
     *
     * @param context
     * @param flag
     */
    public static void setIsSzhc(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                ISSZHC, 0).edit();
        editor.putBoolean(ISSZHCRESLUT, flag);
        editor.commit();
    }

    public static boolean getIsSzhc(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                ISSZHC, 0);
        if (preferences.contains(ISSZHCRESLUT)) {
            return preferences.getBoolean(ISSZHCRESLUT, false);
        }
        return false;
    }

    public static void clearIsSzhc(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ISSZHC, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 作业指导书下载标记
     *
     * @param context
     * @param num     0：增量下载   1：全量下载
     */
    public static void setReportDownFlag(Context context, int num) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                RDOWNLOADFLAG, 0).edit();
        editor.putInt("downFlag", num);
        editor.commit();
    }

    public static int getReportDownFlag(Context context) {
        if (null != context) {
            SharedPreferences preferences = context.getSharedPreferences(
                    RDOWNLOADFLAG, 0);
            return preferences.getInt("downFlag", 1);
        }
        return 1;
    }

    /**
     * 课程信息下载标记
     *
     * @param context
     * @param num     0：增量下载   1：全量下载
     */
    public static void setCourseDownFlag(Context context, int num) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                COURSEDOWNLOADFLAG, 0).edit();
        editor.putInt(COURSEDOWNLOADFLAG, num);
        editor.commit();
    }

    public static int getCourseDownFlag(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                COURSEDOWNLOADFLAG, 0);
        return preferences.getInt(COURSEDOWNLOADFLAG, 1);
    }

    /**
     * 作业指导书是否下载的标志
     *
     * @param context
     * @param status
     */
    public static void setStatus(Context context, String fileId, String status) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                STATUSFLAG, 0).edit();
        editor.putString(fileId, status);
        editor.commit();
    }

    public static String getStatus(Context context, String fileId) {
        SharedPreferences preferences = context.getSharedPreferences(
                STATUSFLAG, 0);
        if (preferences.contains(fileId)) {
            return preferences.getString(fileId, "");
        } else {
            return "";
        }
    }

    public static void clearStatus(Context context, String fileId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(STATUSFLAG, 0).edit();
        editor.remove(fileId);
        editor.commit();
    }

    public static void clearStatus1(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(STATUSFLAG, 0).edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 判断登录的是否是掌机实际使用人
     */

    public static void setMainLogin(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                MAINFLAG, 0).edit();
        editor.putBoolean(MAIN, flag);
        editor.commit();
    }

    public static boolean getMainLogin(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                MAINFLAG, 0);
        if (preferences.contains(MAIN)) {
            return preferences.getBoolean(MAIN, false);
        }
        return false;
    }

    /**
     * 判断是否清理了数据
     */

    public static void setIsClear(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                ISCLEAR, 0).edit();
        editor.putBoolean(CLEAR, flag);
        editor.commit();
    }

    public static boolean getIsClear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                ISCLEAR, 0);
        if (preferences.contains(CLEAR)) {
            return preferences.getBoolean(CLEAR, false);
        }
        return false;
    }


    /**
     * 得到操作员列表
     *
     * @param key
     * @return
     */
    public static String[] getPerson(Context context, String key) {
        String regularEx = "#";
        String[] str = null;
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String values;
        values = sp.getString(key, "");
        str = values.split(regularEx);
        return str;
    }

    /**
     * 设置操作员列表
     *
     * @param key
     * @param values
     */
    public static void setPerson(Context context, String key, String[] values) {
        String regularEx = "#";
        String str = "";
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (values != null && values.length > 0) {
            for (String value : values) {
                str += value;
                str += regularEx;
            }
            SharedPreferences.Editor et = sp.edit();
            et.putString(key, str);
            et.commit();
        }
    }

    /**
     * 得到消息信息
     *
     * @param key
     * @return
     */
    public static String[] getNews(Context context, String key, String appNo) {
        String regularEx = "#";
        String[] str = null;
        SharedPreferences sp = context.getSharedPreferences("newsInfo" + appNo, Context.MODE_PRIVATE);
        String values;
        values = sp.getString(key, "");
        str = values.split(regularEx);
        return str;
    }

    /**
     * 设置消息信息
     *
     * @param key
     * @param values
     */
    public static void setNews(Context context, String key, String[] values, String appNo) {
        String regularEx = "#";
        String str = "";
        SharedPreferences sp = context.getSharedPreferences("newsInfo" + appNo, Context.MODE_PRIVATE);
        if (values != null && values.length > 0) {
            for (String value : values) {
                str += value;
                str += regularEx;
            }
            SharedPreferences.Editor et = sp.edit();
            et.putString(key, str);
            et.commit();
        }
    }

    public static void clearNews(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences("newsInfo" + appNo, 0).edit();
        editor.clear();
        editor.commit();
    }

    //保存主站推送的疑难消息
    public static void setStationNews(Context context, String appNo, String msg) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                STATIONNEWS + appNo, 0).edit();
        editor.putString(STATIONNEWS + appNo, msg);
        editor.commit();
    }

    public static String getStationNews(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                STATIONNEWS + appNo, 0);

        if (preferences.contains(STATIONNEWS + appNo)) {
            return preferences.getString(STATIONNEWS + appNo, "");
        } else {
            return "";
        }
    }

    public static void clearStationNews(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(STATIONNEWS + appNo, 0).edit();
        editor.clear();
        editor.commit();
    }

    //保存掌机发送的疑难消息
    public static void setNativeNews(Context context, String appNo, String msg) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                NATIVENEWS + appNo, 0).edit();
        editor.putString(NATIVENEWS + appNo, msg);
        editor.commit();
    }

    public static String getNativeNews(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(
                NATIVENEWS + appNo, 0);

        if (preferences.contains(NATIVENEWS + appNo)) {
            return preferences.getString(NATIVENEWS + appNo, "");
        } else {
            return "";
        }
    }

    //保存在线学习课程列表更新时间
    public static void setUpdateTime(Context context, String time) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                UPDATETIME, 0).edit();
        editor.putString(UPDATETIME, time);
        editor.commit();
    }

    public static String getUpdateTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                UPDATETIME, 0);

        if (preferences.contains(UPDATETIME)) {
            return preferences.getString(UPDATETIME, "");
        } else {
            return "";
        }
    }

    //保存下载安全管控更新时间
    public static void setSafeUpdateTime(Context context, String time) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                SAFEUPDATETIME, 0).edit();
        editor.putString(SAFEUPDATETIME, time);
        editor.commit();
    }

    public static String getSafeUpdateTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                SAFEUPDATETIME, 0);

        if (preferences.contains(SAFEUPDATETIME)) {
            return preferences.getString(SAFEUPDATETIME, "");
        } else {
            return "";
        }
    }

    public static void clearUSafepdateTime(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SAFEUPDATETIME, 0).edit();
        editor.clear();
        editor.commit();
    }


    public static void clearNativeNews(Context context, String appNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NATIVENEWS + appNo, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存即时通讯姓名
     *
     * @param context
     * @param
     */
    public static void setIMName(Context context, String name) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                IM_FLAG, 0).edit();
        editor.putString(IM_NAME, name);
        editor.commit();
    }

    public static String getIMName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                IM_FLAG, 0);

        if (preferences.contains(IM_NAME)) {
            return preferences.getString(IM_NAME, "");
        } else {
            return "";
        }
    }

    /**
     * 保存即时通讯 密码
     *
     * @param context
     * @param
     */
    public static void setIMPswd(Context context, String pswd) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                IM_FLAG, 0).edit();
        editor.putString(IM_PSWD, pswd);
        editor.commit();
    }

    public static String getIMPswd(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                IM_FLAG, 0);
        if (preferences.contains(IM_PSWD)) {
            return preferences.getString(IM_PSWD, "");
        } else {
            return "";
        }
    }

    public static void clearIM(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(IM_FLAG, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 获取最近距离
     */
    public static String getMinLength(Context context, String tgName) {
        SharedPreferences preferences = context.getSharedPreferences(
                MINLENGTH + tgName, 0);
        if (preferences.contains(MIN)) {
            return preferences.getString(MIN, "");
        } else {
            return "";
        }
    }

    /**
     * 设置最近距离
     */
    public static void setMinLength(Context context, String length, String tgName) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                MINLENGTH + tgName, 0).edit();
        editor.putString(MIN, length);
        editor.commit();
    }


    /**
     * 即时通讯 重新登录
     *
     * @param context
     * @param flag
     */
    public static void setIMReLogin(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                IM_LOGIN_FLAG, 0).edit();
        editor.putBoolean(IM_LOGIN, flag);
        editor.commit();
    }

    public static boolean getIMReLogin(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                IM_LOGIN_FLAG, 0);
        if (preferences.contains(IM_LOGIN)) {
            return preferences.getBoolean(IM_LOGIN, false);
        }
        return false;
    }

    public static void clearIMReLogin(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(IM_LOGIN_FLAG, 0).edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 即时通讯 重新登录
     *
     * @param context
     * @param flag
     */
    public static void setIMSave(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "save_flag", 0).edit();
        editor.putBoolean("save", flag);
        editor.commit();
    }

    public static boolean getIMSave(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "save_flag", 0);
        if (preferences.contains("save")) {
            return preferences.getBoolean("save", false);
        }
        return false;
    }

    public static void clearIMSave(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("save_flag", 0).edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 即时通讯 重新登录
     *
     * @param context
     * @param flag
     */
    public static void setIMMessage(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "save_message", 0).edit();
        editor.putBoolean("savemsg", flag);
        editor.commit();
    }

    public static boolean getIMMessage(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "save_message", 0);
        if (preferences.contains("savemsg")) {
            return preferences.getBoolean("savemsg", false);
        }
        return false;
    }

    public static void clearIMMessage(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("save_message", 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存即时通讯 密码
     *
     * @param context
     * @param
     */
    public static void setNotificationId(Context context, String id) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                NOTIFICATION, 0).edit();
        editor.putString(NOTIFICATION_ID, id);
        editor.commit();
    }

    public static String getNotificationId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                NOTIFICATION, 0);
        if (preferences.contains(NOTIFICATION_ID)) {
            return preferences.getString(NOTIFICATION_ID, "");
        } else {
            return "";
        }
    }

    /**
     * 保存即时通讯 密码
     *
     * @param context
     * @param
     */
    public static void setIMUserID(Context context, String pswd) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "im_user_id_flag", 0).edit();
        editor.putString("im_user_id", pswd);
        editor.commit();
    }

    public static String getIMUserID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "im_user_id_flag", 0);
        if (preferences.contains("im_user_id")) {
            return preferences.getString("im_user_id", "");
        } else {
            return "";
        }
    }

    public static void clearIMUserID(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("im_user_id_flag", 0).edit();
        editor.clear();
        editor.commit();
    }

    public static void saveAllWorkSize(Context baseContext, int allCount) {
        SharedPreferences.Editor editor = baseContext.getSharedPreferences(
                "work_All_size", 0).edit();
        editor.putInt("work_All_size", allCount);
        editor.commit();
    }

    public static void saveDownLoadingWorkSize(Context baseContext, int size) {
        SharedPreferences.Editor editor = baseContext.getSharedPreferences(
                "download_work_size", 0).edit();
        editor.putInt("download_work_size", size);
        editor.commit();
    }

    public static int getDownLoadingWorkSize(Context baseContext) {
        if (baseContext != null) {

            SharedPreferences preferences = baseContext.getSharedPreferences(
                    "download_work_size", 0);
            if (preferences.contains("download_work_size")) {
                return preferences.getInt("download_work_size", 0);
            } else {
                return 0;
            }
        }
        return 0;
    }

    public static int getAllWorkSize(Context context) {

        if (context != null) {

            SharedPreferences preferences = context.getSharedPreferences(
                    "work_All_size", 0);
            if (preferences.contains("work_All_size")) {
                return preferences.getInt("work_All_size", 0);
            } else {
                return 0;
            }
        }
        return 0;
    }

    //保存地图鉴权
    public static void saveauthentication(Context baseContext, String str) {
        SharedPreferences.Editor editor = baseContext.getSharedPreferences(
                "authentication", 0).edit();
        editor.putString("authentication", str);
        editor.commit();
    }

    public static String getauthentication(Context baseContext) {
        if (baseContext != null) {

            SharedPreferences preferences = baseContext.getSharedPreferences(
                    "authentication", 0);
            if (preferences.contains("authentication")) {
                return preferences.getString("authentication", "");
            } else {
                return null;
            }
        }
        return null;
    }

    public static void clearauthentication(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("authentication", 0).edit();
        editor.clear();
        editor.commit();
    }

    //保存参数核查标志
    public static void saveParamsCheckFlag(Context context, String appNo, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TERMINAL_INSTALL + appNo, 0).edit();
        editor.putString(PARAM_CHECK_FLAG, flag);
        editor.commit();
    }

    //获取参数核查标志
    public static String getParamCheckFlag(Context context, String appno) {
        SharedPreferences preferences = context.getSharedPreferences(TERMINAL_INSTALL + appno, 0);
        String flag = preferences.getString(PARAM_CHECK_FLAG, "");
        return flag;
    }

    //保存参数核查结果
    public static void saveParamCheckResult(Context context, String appNo, Object o) {
        saveObject(context, appNo, PARAM_CHECK_RESULT, o);
    }

    //获取参数核查结果
    public static Object getParamCheckResult(Context context, String appNo) {
        return getObject(context, appNo, PARAM_CHECK_RESULT);
    }

    //保存档案下发标志
    public static void saveRecordIssueFlag(Context context, String appNo, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TERMINAL_INSTALL + appNo, 0).edit();
        editor.putString(RECORD_ISSUE_FLAG, flag);
        editor.commit();
    }

    //获取档案下发标志
    public static String getRecordIssueFlag(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(TERMINAL_INSTALL + appNo, 0);
        String flag = preferences.getString(RECORD_ISSUE_FLAG, "");
        return flag;
    }

    //保存档案下发结果
    public static void saveRecordIssueResult(Context context, String appNo, Object o) {
        saveObject(context, appNo, RECORD_ISSUE_RESULT, o);
    }

    //获取档案下发结果
    public static Object getRecordIssueResult(Context context, String appNo) {
        return getObject(context, appNo, RECORD_ISSUE_RESULT);
    }

    //保存抄表参数下发标志
    public static void saveReadMeterParamIssueFlag(Context context, String appNo, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TERMINAL_INSTALL + appNo, 0).edit();
        editor.putString(READMETER_PARAM_ISSUE_FLAG, flag);
        editor.commit();
    }

    //获取抄表参数下发标志
    public static String getReadMeterParamIssueFlag(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(TERMINAL_INSTALL + appNo, 0);
        String flag = preferences.getString(READMETER_PARAM_ISSUE_FLAG, "");
        return flag;
    }

    //保存抄表参数下发结果
    public static void saveReadMeterParamIssueResult(Context context, String appNo, Object o) {
        saveObject(context, appNo, READMETER_PARAM_ISSUE_RESULT, o);
    }

    //获取抄表参数下发结果
    public static Object getReadMeterParamIssueResult(Context context, String appNo) {
        return getObject(context, appNo, READMETER_PARAM_ISSUE_RESULT);
    }

    //保存抄表联调标志
    public static void saveReadMeterDebugFlag(Context context, String appNo, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TERMINAL_INSTALL + appNo, 0).edit();
        editor.putString(READMETER_DEBUG_FLAG, flag);
        editor.commit();
    }

    //获取抄表联调标志
    public static String getReadMeterDebugFlag(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(TERMINAL_INSTALL + appNo, 0);
        String flag = preferences.getString(READMETER_DEBUG_FLAG, "0");
        return flag;
    }

    //保存抄表联调结果
    public static void saveReadMeterDebugResult(Context context, String appNo, Object o) {
        saveObject(context, appNo, READMETER_DEBUG_RESULT, o);
    }

    //获取抄表联调结果
    public static Object getReadMeterDebugResult(Context context, String appNo) {
        return getObject(context, appNo, READMETER_DEBUG_RESULT);
    }

    //保存事件重要性等级下发标志
    public static void saveEventAllocationFlag(Context context, String appNo, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TERMINAL_INSTALL + appNo, 0).edit();
        editor.putString(EVENT_ALLOCATION_FLAG, flag);
        editor.commit();
    }

    //获取事件重要性等级下发标志
    public static String getEventAllocationFlag(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(TERMINAL_INSTALL + appNo, 0);
        String flag = preferences.getString(EVENT_ALLOCATION_FLAG, "0");
        return flag;
    }

    //保存互感器比对标志
    public static void saveMutualCompareFlag(Context context, String appNo, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(METER_INSTALL + appNo, 0).edit();
        editor.putString(MUTUAL_COMPARE_FLAG, flag);
        editor.commit();
    }

    /**
     * 保存召测结果的批次号
     *
     * @param context
     * @param appNo
     * @param batchNo
     */
    public static void saveCMDBatchNo(Context context, String appNo, String batchNo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(METER_INSTALL + appNo, 0).edit();
        editor.putString(BATCH_NO, batchNo);
        editor.commit();
    }

    /**
     * 获取召测结果的批次号
     *
     * @param context
     * @param appNo
     * @return
     */
    public static String getCMDBatchNo(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(METER_INSTALL + appNo, 0);
        String batchNo = preferences.getString(BATCH_NO, "");
        return batchNo;
    }

    //获取互感器比对标志
    public static String getMutualCompareFlag(Context context, String appNo) {
        SharedPreferences preferences = context.getSharedPreferences(METER_INSTALL + appNo, 0);
        String flag = preferences.getString(MUTUAL_COMPARE_FLAG, "");
        return flag;
    }

    //保存互感器比对标志
    public static void saveMutualCompareFlag(Context context, String appNo, String barcode, String flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(METER_INSTALL + appNo, 0).edit();
        editor.putString(MUTUAL_COMPARE_FLAG + "_" + barcode, flag);
        editor.commit();
    }

    //获取互感器比对标志
    public static String getMutualCompareFlag(Context context, String appNo, String barcode) {
        SharedPreferences preferences = context.getSharedPreferences(METER_INSTALL + appNo, 0);
        String flag = preferences.getString(MUTUAL_COMPARE_FLAG + "_" + barcode, "");
        return flag;
    }

    public static void saveObject(Context context, String appNo, String key, Object o) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TERMINAL_INSTALL + appNo, 0).edit();
        ByteArrayOutputStream toByte = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(toByte);
            oos.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String payCityMapBase64 = new String(Base64.encode(toByte.toByteArray(), Base64.DEFAULT));
        editor.putString(key, payCityMapBase64);
        editor.commit();
    }

    public static Object getObject(Context context, String appNo, String key) {
        SharedPreferences preferences = context.getSharedPreferences(TERMINAL_INSTALL + appNo, 0);
        byte[] base64Bytes = Base64.decode(preferences.getString(key, ""), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存拆除异常原因
     *
     * @param appNo
     * @param disExcCase
     * @param context
     */
    public static void saveDisExcCase(String appNo, String disExcCase, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(appNo, 0).edit();
        editor.putString(appNo, disExcCase);
        editor.commit();
    }

    public static String getDisExcCase(String appNo, Context context) {
        SharedPreferences sp = context.getSharedPreferences(appNo, 0);
        String disExcCase = sp.getString(appNo, "");
        return disExcCase;
    }


    /**
     * 保存即时通讯 ip
     *
     * @param context
     * @param
     */
    public static void setIMIP(Context context, String pswd) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "im_ip_flag", 0).edit();
        editor.putString("im_ip", pswd);
        editor.commit();
    }

    public static String getIMIP(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "im_ip_flag", 0);
        if (preferences.contains("im_ip")) {
            return preferences.getString("im_ip", "");
        } else {
            return "";
        }
    }


    public static void clearIMIP(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("im_ip_flag", 0).edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 保存即时通讯 port
     *
     * @param context
     * @param
     */
    public static void setIMPort(Context context, String pswd) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "im_port_flag", 0).edit();
        editor.putString("im_port", pswd);
        editor.commit();
    }

    public static String getIMPort(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "im_port_flag", 0);
        if (preferences.contains("im_port")) {
            return preferences.getString("im_port", "");
        } else {
            return "";
        }
    }

    public static void clearIMPort(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("im_port_flag", 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * resam 二次发行 序列号
     *
     * @param context
     * @param
     */
    public static void setResamTwoNo(Context context, String pswd) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "resam_two", 0).edit();
        editor.putString("resam_two_no", pswd);
        editor.commit();
    }

    public static String getResamTwoNo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "resam_two", 0);
        if (preferences.contains("resam_two_no")) {
            return preferences.getString("resam_two_no", "");
        } else {
            return "";
        }
    }

    public static void clearResamTwoNo(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("resam_two", 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * resam 二次发行 数据
     *
     * @param context
     * @param
     */
    public static void setResamTwoData(Context context, String pswd) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "resam_two", 0).edit();
        editor.putString("resam_two_data", pswd);
        editor.commit();
    }

    public static String getResamTwoData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "resam_two", 0);
        if (preferences.contains("resam_two_data")) {
            return preferences.getString("resam_two_data", "");
        } else {
            return "";
        }
    }

    public static void clearResamTwoData(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("resam_two", 0).edit();
        editor.clear();
        editor.commit();
    }


    //保存lsit
    public static void setArray(Context context, String key, ArrayList<String> sArray) {
        SharedPreferences.Editor mEdit1 = context.getSharedPreferences("arraylist", Context.MODE_PRIVATE).edit();
        mEdit1.putInt(key + "_size", sArray.size());
        for (int i = 0; i < sArray.size(); i++) {
            mEdit1.remove(key + "_" + i);
            mEdit1.putString(key + "_" + i, sArray.get(i));
        }
        mEdit1.commit();
    }

    //读取list
    public static ArrayList<String> getArray(Context context, String key) {
        SharedPreferences mSp = context.getSharedPreferences("arraylist", Context.MODE_PRIVATE);
        ArrayList<String> sArray = new ArrayList<String>();
        int size = mSp.getInt(key + "_size", 0);
        for (int i = 0; i < size; i++) {
            sArray.add(mSp.getString(key + "_" + i, null));
        }
        return sArray;
    }

    //清理list
    public static void clearArray(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("arraylist", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }







    /**
     * 保存危险点异常原因编码
     */
    public static void setDangerReasonCode(Context context, String reason_code) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "danger", 0).edit();
        editor.putString("danger_reason_code", reason_code);
        editor.commit();
    }

    public static String getDangerReasonCode(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "danger", 0);
        if (preferences.contains("danger_reason_code")) {
            return preferences.getString("danger_reason_code", "");
        } else {
            return "";
        }
    }

    public static void clearDangerReasonCode(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("danger", 0).edit();
        editor.clear();
        editor.commit();
    }



    /**
     * 保存选择的工器具的json字符串
     */
    public static void setToolsJson(Context context, String mjson) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "checktools", 0).edit();
        editor.putString("checktools", mjson);
        editor.commit();
    }

    public static String getToolsJson(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "checktools", 0);
        if (preferences.contains("checktools")) {
            return preferences.getString("checktools", "");
        } else {
            return "";
        }
    }


    public static void clearToolsJson(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("checktools", 0).edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 保存是否展示工器具标识 1是 0 否
     */
    public static void setIsShowTools(Context context, int name) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "isShowTools", 0).edit();
        editor.putInt("showTools", name);
        editor.commit();
    }

    public static int getIsShowTools(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "isShowTools", 0);

        if (preferences.contains("showTools")) {
            return preferences.getInt("showTools", 0);
        } else {
            return 0;
        }
    }


    /**
     * 保存选择的工器具的json字符串
     */
    public static void setWSAdress(Context context, String mjson) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "WS_INFO", 0).edit();
        editor.putString("WSADRESS", mjson);
        editor.commit();
    }

    public static String getWSAdress(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "WS_INFO", 0);
        if (preferences.contains("WSADRESS")) {
            return preferences.getString("WSADRESS", "");
        } else {
            return "";
        }
    }


    public static void clearWSAdress(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("WS_INFO", 0).edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 保存选择的工器具的json字符串
     */
    public static void setWSType(Context context, String mjson) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "WS_INFO2", 0).edit();
        editor.putString("WS_TYPE", mjson);
        editor.commit();
    }

    public static String getWSType(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "WS_INFO2", 0);
        if (preferences.contains("WS_TYPE")) {
            return preferences.getString("WS_TYPE", "");
        } else {
            return "";
        }
    }


    public static void clearWSType(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("WS_INFO2", 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存召测记录的list集合  json字符串
     */
    public static void setZhaoCeRecords(Context context,String zhaoCeRecords) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "ZHAOCE_RECORDS", 0).edit();
        editor.putString("ZHAOCE_RECORDS", zhaoCeRecords);
        editor.commit();
    }
    /**
     * 读取召测记录的list集合
     */
    public static String getZhaoCeRecords(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "ZHAOCE_RECORDS", 0);
        if (preferences.contains("ZHAOCE_RECORDS")) {
            return preferences.getString("ZHAOCE_RECORDS", "");
        } else {
            return "";
        }
    }
    public static void clearZhaoCeRecords(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ZHAOCE_RECORDS", 0).edit();
        editor.clear();
        editor.commit();
    }

}
