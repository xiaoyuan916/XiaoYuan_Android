package com.sgcc.pda.hardware.util;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * @author:HZWQ
 * @Date: 2016/3/30
 * @Time: 16:32
 */
public class Constant {


    //工单类型编码
    public static final String APP_TYPE_01 = "01";//采集异常
    public static final String APP_TYPE_04 = "04";//现场补抄
    public static final String APP_TYPE_05 = "05";//费控异常
    public static final String APP_TYPE_07 = "07";//现场巡视
    public static final String APP_TYPE_08 = "08";//计量异常
    public static final String APP_TYPE_15 = "15";//远程控制
    public static final String APP_TYPE_16 = "16";//现场充值
    public static final String APP_TYPE_17 = "17";//现场校时
    public static final String APP_TYPE_18 = "18";//电能表密钥下装
    public static final String APP_TYPE_19 = "19";//现场电价调整
    public static final String APP_TYPE_20 = "20";//集中器密钥下装
    public static final String APP_TYPE_21 = "21";//采集终端新装
    public static final String APP_TYPE_22 = "22";//采集终端更换
    public static final String APP_TYPE_23 = "23";//采集终端拆除
    public static final String APP_TYPE_24 = "24";//电能表新装
    public static final String APP_TYPE_25 = "25";//电能表更换
    public static final String APP_TYPE_26 = "26";//电能表拆除

    //异常设备类型
    public static final String DEV_TYPE_01 = "01";
    public static final String DEV_TYPE_02 = "02";
    public static final String DEV_TYPE_03 = "03";
    public static final String DEV_TYPE_04 = "04";
    public static final String DEV_TYPE_05 = "05";

    //用户类型
    public static final String USER_TYPE_01 = "01";
    public static final String USER_TYPE_02 = "02";
    public static final String USER_TYPE_03 = "03";

    /************************************
     * 排查方式编码
     **********************************/
    public static final String INV_TYPE_1 = "1";//直观检查
    public static final String INV_TYPE_2 = "2";//参数校验
    public static final String INV_TYPE_3 = "3";//外设排查


    /************************************
     * 参数检查编码
     **********************************/

    public static final String METER_TIME_COMPARED = "10014";//电能表时钟比对
    public static final String METER_TIME_SET = "10018";//电能表时钟设置
    public static final String TMNL_PARAM_COMPARED = "10004";//终端通讯参数比对
    public static final String TMNL_PARAM_SET = "10006";//终端通讯参数设置



    /************************************
     * 掌机密钥版本
     **********************************/
    public static final String CESAM_VERSION = "00000000000000000000000000000000";//CESAM_VERSION公钥
    public static final String YESAM_VERSION = "00000000000000000000000000000000";//YESAM_VERSION公钥
    public static final String YESAM_VERSION1 = "00000000200000000000000000000000";//YESAM_VERSION公钥
    public static final String GY_TYPE = "00";//公钥
  public static final String SY_TYPE = "01";//私钥
//public static final String SY_TYPE = "00";//测试 屏蔽检验密码使用



    /************************************
     * 外设排查编码
     **********************************/

    public static final String SIM_CHECK = "30001";//sim卡检测
    public static final String JZQ_ZJ_CHECK = "30002";//集中器整机测试
    public static final String CJQ_ZJ_CHECK = "30003";//采集器整机测试
    public static final String DNB_ZJ_CHECK = "30004";//电能表整机测试
    public static final String MK_ZJ_CHECK = "30005";//模块测试
    public static final String WGL_ZJ_CHECK = "30006";//微功率无线测试


    /***************************************
     * 申请主站服务
     ***************************************/
    //类型
    public static final String CMD_TYPE_1 = "1";//数据召测
    public static final String CMD_TYPE_2 = "2";//参数设置
    //命令编码
    public static final String CMD_CODE_1 = "200101";//终端电能表/交流采样装置配置参数召测设置
    public static final String CMD_CODE_2 = "200102";//终端版本信息召测
    public static final String CMD_CODE_3 = "200103";//终端时钟信息召测、设置
    public static final String CMD_CODE_4 = "200201";//电量任务设置/召测
    public static final String CMD_CODE_5 = "200202";//负荷任务设置/召测
    public static final String CMD_CODE_6 = "200301";//电表时钟误差
    public static final String CMD_CODE_7 = "200302";//直抄电能表当前正向有功电能数据块
    public static final String CMD_CODE_8 = "200401";//抄终端日冻结正向有功电能示值
    public static final String CMD_CODE_9 = "200501";//终端抄表运行参数
    public static final String CMD_CODE_10 = "200601";//台区集中抄表停抄/投抄
    public static final String CMD_CODE_11 = "200701";//上行通信流量门限
    public static final String CMD_CODE_12 = "200801";//1类数据配置设置
    public static final String CMD_CODE_13 = "200901";//2类数据配置设置
    //对象类型编码
    public static final String TG_TYPE_1 = "1";//电能表
    public static final String TG_TYPE_2 = "2";//采集终端

    //对象类型
    public static final String TG_TYPE_NAME_1 = "电能表";
    public static final String TG_TYPE_NAME_2 = "采集终端";

    //工单下载
    public static final String IS_ALL_LOAD_NO = "0";//只下载现场处理的未下载的工单
    public static final String IS_ALL_LOAD_YES = "1";//下载全部现场处理的工单

    //电表权限
    public static final String INFRA = "11";//红外
    public static final String PARAM1 = "05";//1类参数
    public static final String PARAM2 = "03";//2类参数
    public static final String RATE1 = "06";//第1套费率
    public static final String RATE2 = "07";//第2套费率
    public static final String CONTROL = "02";//控制
    public static final String CHARGE = "04";//充值
    /************************************
     * 698执行结果标志位
     **********************************/
    public static final String ERROR698_0 = "00"; //成功
    public static final String ERROR698_1 = "硬件失效";
    public static final String ERROR698_2 = "暂时失效";
    public static final String ERROR698_3 = "拒绝读写";
    public static final String ERROR698_4 = "对象未定义";
    public static final String ERROR698_5 = "对象接口类不符合";
    public static final String ERROR698_6 = "对象不存在";
    public static final String ERROR698_7 = "类型不匹配";
    public static final String ERROR698_8 = "越界";
    public static final String ERROR698_9 = "数据块不可用";
    public static final String ERROR698_10 = "分帧传输已取消";
    public static final String ERROR698_11 = "不处于分帧传输状态";
    public static final String ERROR698_12 = "块写取消";
    public static final String ERROR698_13 = "不存在块写状态";
    public static final String ERROR698_14 = "数据块序号无效";
    public static final String ERROR698_15 = "密码错/未授权";
    public static final String ERROR698_16 = "通信速率不能更改";
    public static final String ERROR698_17 = "年时区数超";
    public static final String ERROR698_18 = "日时段数超";
    public static final String ERROR698_19 = "费率数超";
    public static final String ERROR698_20 = "安全认证不匹配";
    public static final String ERROR698_21 = "重复充值";
    public static final String ERROR698_22 = "ESAM验证失败";
    public static final String ERROR698_23 = "安全认证失败";
    public static final String ERROR698_24 = "客户编号不匹配";
    public static final String ERROR698_25 = "充值次数错误";
    public static final String ERROR698_26 = "购电超囤积";
    public static final String ERROR698_27 = "地址异常";
    public static final String ERROR698_28 = "对称解密错误";
    public static final String ERROR698_29 = "非对称解密错误";
    public static final String ERROR698_30 = "签名错误";
    public static final String ERROR698_31 = "电能表挂起";
    public static final String ERROR698_32 = "时间标签无效";
    public static final String ERROR698_33 = "请求超时";
    public static final String ERROR698_255 = "其它";

    /************************************
     * 文件路径
     *************************************/
    public static final String FILEPATH1 = "/storage/sdcard0/sitework/";
    public static final String FILEPATH = Environment.getExternalStorageDirectory() + File.separator + "sitework/";
    public static final String FILEPATH_LOG = FILEPATH + "log/";

    public static final String FILEPATH_DB = FILEPATH + "db/";
    //日志文件名称
    public static final String LOG_NAME = "marketLog.log";
    //数据库名称
    public static final String DB_NAME = "marketData.db";
    //数据库版本号
    public static final int DB_VERSION = 1;

    public static final String APP_FILE_NAME = "app-debug.apk";

    private static final String INSTRUCT_PATH = FILEPATH + "instruction/";
    private static final String DOC_PATH = INSTRUCT_PATH + "doc/";
    private static final String VIDEO_PATH = INSTRUCT_PATH + "video/";


    /**
     * 根据工单类型编码获取工单类型值
     */
    public static String getGDLX(String code) {
        String appType = "";
        switch (code) {
            case APP_TYPE_01:
                appType = "采集异常";
                break;
            case APP_TYPE_04:
                appType = "补抄工单";
                break;
            case APP_TYPE_05:
                appType = "费控工单";
                break;
            case APP_TYPE_07:
                appType = "巡视工单";
                break;
            case APP_TYPE_08:
                appType = "计量异常";
                break;
            case APP_TYPE_15:
                appType = "远程控制";
        }
        return appType;
    }

    /**
     * 根据设备类型编码获取设备类型名称
     */
    public static String getSBLX(String code) {
        if (TextUtils.isEmpty(code)) {
            return "";
        }
        String sblx = "";
        switch (code) {
            case DEV_TYPE_01:
                sblx = "专变终端";
                break;
            case DEV_TYPE_02:
                sblx = "I型集中器";
                break;
            case DEV_TYPE_03:
                sblx = "II型集中器";
                break;
            case DEV_TYPE_04:
                sblx = "采集器";
                break;
            case DEV_TYPE_05:
                sblx = "电能表";
                break;
            default:
                sblx = "";
        }
        return sblx;
    }

    /**
     * 用户类型
     */
    public static String getYHLX(String code) {
        if (TextUtils.isEmpty(code)) {
            return "";
        }
        String yhlx = "";
        switch (code) {
            case USER_TYPE_01:
                yhlx = "专变";
                break;
            case USER_TYPE_02:
                yhlx = "公变";
                break;
            case USER_TYPE_03:
                yhlx = "低压";
                break;
        }
        return yhlx;
    }

    /**
     * 将原子操作编码转换为内容
     *
     * @param code
     * @return 原子操作名称
     */
    public static String getAT_CODE(String code) {
        if (TextUtils.isEmpty(code)) {
            return "";
        }
        String result = "";
        switch (code) {
            case Constant.SIM_CHECK:
                result = "SIM卡故障检测";
                break;
            case Constant.JZQ_ZJ_CHECK:
                result = "集中器整机检测";
                break;
            case Constant.CJQ_ZJ_CHECK:
                result = "采集器整机检测";
                break;
            case Constant.DNB_ZJ_CHECK:
                result = "电能表整机检测";
                break;
            case Constant.MK_ZJ_CHECK:
                result = "模块检测";
                break;
            case Constant.WGL_ZJ_CHECK:
                result = "微功率检测";
                break;
            case Constant.METER_TIME_COMPARED:
            case Constant.METER_TIME_SET:
                result = "电能表时钟校验";
                break;
            case Constant.TMNL_PARAM_COMPARED:
            case Constant.TMNL_PARAM_SET:
                result = "通讯参数校验";
                break;
        }
        return result;
    }

    //模块测试
    public static final int REQUEST_CJQ_CHECK = 0X001001;//采集器整机测试
    public static final int REQUEST_METER_CHECK = 0x001002;//电能表整机测试


    //持续多天无抄表数据
    public static final String REQUEST_485_CHECK_TYPE = "request_485_check_type";
    public static final int TYPE_METER_9010 = 0x003001;//电能表当前正向有功总电能
    public static final int TYPE_METER_TIME = 0x003002;//电能表时钟

    public static final int TYPE_METER_LAST_LABEL_ONE = 0x003003;//上1~3次冻结数据
    public static final int TYPE_METER_LAST_LABEL_TWO = 0x003004;//上4~6冻结数据
    public static final int TYPE_METER_LAST_LABEL_THREE = 0x003005;//上7~9次冻结数据

    public static final int LAST_DAY_DATA_TIME_DIFF = 100;
    public static final int LAST_DAY_1_DATA = 0x10101;
    public static final int LAST_DAY_2_DATA = 0x10102;
    public static final int LAST_DAY_3_DATA = 0x10103;
    public static final int LAST_DAY_4_DATA = 0x10104;
    public static final int LAST_DAY_5_DATA = 0x10105;
    public static final int LAST_DAY_6_DATA = 0x10106;
    public static final int LAST_DAY_7_DATA = 0x10107;
    public static final int LAST_DAY_8_DATA = 0x10108;
    public static final int LAST_DAY_9_DATA = 0x10109;

    public static final int LAST_DAY_1_TIME = LAST_DAY_1_DATA + LAST_DAY_DATA_TIME_DIFF;
    public static final int LAST_DAY_2_TIME = LAST_DAY_2_DATA + LAST_DAY_DATA_TIME_DIFF;
    public static final int LAST_DAY_3_TIME = LAST_DAY_3_DATA + LAST_DAY_DATA_TIME_DIFF;
    public static final int LAST_DAY_4_TIME = LAST_DAY_4_DATA + LAST_DAY_DATA_TIME_DIFF;
    public static final int LAST_DAY_5_TIME = LAST_DAY_5_DATA + LAST_DAY_DATA_TIME_DIFF;
    public static final int LAST_DAY_6_TIME = LAST_DAY_6_DATA + LAST_DAY_DATA_TIME_DIFF;
    public static final int LAST_DAY_7_TIME = LAST_DAY_7_DATA + LAST_DAY_DATA_TIME_DIFF;
    public static final int LAST_DAY_8_TIME = LAST_DAY_8_DATA + LAST_DAY_DATA_TIME_DIFF;
    public static final int LAST_DAY_9_TIME = LAST_DAY_9_DATA + LAST_DAY_DATA_TIME_DIFF;

    public static final String COLUMN_ALL = "ALL";

    public static final String USER_NO_01 = "HH";//户号
    public static final String USER_NO_02 = "DBTXDZ";//电表通讯地址
    public static final String USER_NO_03 = "ZDJH";//终端局号

    public static final String USER_NAME_01 = "HM";//户名/台区名称

    /**
     * 解析698电表成功标志位
     *
     * @param dar
     * @return
     */
    public static String paser698Dar(String dar) {
        String result = "";
        dar = Integer.parseInt(dar, 10) + ""; //16进制 转10进制
        switch (dar) {
            case "00":
                result = Constant.ERROR698_0;
                break;
            case "01":
                result = Constant.ERROR698_1;
                break;
            case "02":
                result = Constant.ERROR698_2;
                break;
            case "03":
                result = Constant.ERROR698_3;
                break;
            case "04":
                result = Constant.ERROR698_4;
                break;
            case "05":
                result = Constant.ERROR698_5;
                break;
            case "06":
                result = Constant.ERROR698_6;
                break;
            case "07":
                result = Constant.ERROR698_7;
                break;
            case "08":
                result = Constant.ERROR698_8;
                break;
            case "09":
                result = Constant.ERROR698_9;
                break;
            case "10":
                result = Constant.ERROR698_10;
                break;
            case "11":
                result = Constant.ERROR698_11;
                break;
            case "12":
                result = Constant.ERROR698_12;
                break;
            case "13":
                result = Constant.ERROR698_13;
                break;
            case "14":
                result = Constant.ERROR698_14;
                break;
            case "15":
                result = Constant.ERROR698_15;
                break;
            case "16":
                result = Constant.ERROR698_16;
                break;
            case "17":
                result = Constant.ERROR698_17;
                break;
            case "18":
                result = Constant.ERROR698_18;
                break;
            case "19":
                result = Constant.ERROR698_19;
                break;
            case "20":
                result = Constant.ERROR698_20;
                break;
            case "21":
                result = Constant.ERROR698_21;
                break;
            case "22":
                result = Constant.ERROR698_22;
                break;
            case "23":
                result = Constant.ERROR698_23;
                break;
            case "24":
                result = Constant.ERROR698_24;
                break;
            case "25":
                result = Constant.ERROR698_25;
                break;
            case "26":
                result = Constant.ERROR698_26;
                break;
            case "27":
                result = Constant.ERROR698_27;
                break;
            case "28":
                result = Constant.ERROR698_28;
                break;
            case "29":
                result = Constant.ERROR698_29;
                break;
            case "30":
                result = Constant.ERROR698_30;
                break;
            case "31":
                result = Constant.ERROR698_31;
                break;
            case "32":
                result = Constant.ERROR698_32;
                break;
            case "33":
                result = Constant.ERROR698_33;
                break;
            case "255":
                result = Constant.ERROR698_255;
                break;
        }
        return result;
    }
}
