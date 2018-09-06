package com.sgcc.pda.jszp.util;

/**
 * 常亮
 */
public class JzspConstants {
    //以下参数仅用于测试
    public static final String UID="";
    public static final String userId="test01";
    public static final String baseNo="";
    public static final String MAC="";
    public static final String OrgNo="3240000";


    public static final int PageStart = 1;//列表开始页数
    public static final int PageSize = 10;//每页条数

    //车辆类型
    public static final String Car_Type_5T = "01";//小型车   5T
    public static final String Car_Type_3T = "02";//中型车  3T

    //派车状态
    public static final String Car_Status_UnSend = "01";//未派车
    public static final String Car_Status_Sended = "02";//已派车

    //平库出库计划明细状态
    public static final String Device_Out_UnSign = "00";//待签收
    public static final String Device_Out_New = "01";//新建
    public static final String Device_Out_Sended = "02";//已下发
    public static final String Device_Out_Finish = "03";//已完成

    //3.2.6.	设备类别
    public static final String Device_Category_DianNengBiao = "01";//电能表
    public static final String Device_Category_DianYaHuGanQi = "02";//电压互感器
    public static final String Device_Category_DianLiuHuGanQi = "03";//电流互感器
    public static final String Device_Category_ZuHeHuGanQi = "04";//组合互感器
    public static final String Device_Category_CaiJiZhongDuan = "09";//采集终端
    public static final String Device_Category_TongXunMoKuai = "54";//通讯模块
    public static final String Device_Category_ZhouZhuanXiang = "59";//周转箱


    //3.2.14.	配送任务状态/拆分任务状态
    public static final String Delivery_Task_Status_ls = "00";//临时
    public static final String Delivery_Task_Status_xj = "01";//新建
    public static final String Delivery_Task_Status_sx = "02";//生效
    public static final String Delivery_Task_Status_psck = "03";//配送出库
    public static final String Delivery_Task_Status_yck = "04";//已出库
    public static final String Delivery_Task_Status_yhqr = "0401";//用户确认
    public static final String Delivery_Task_Status_ybd = "0402";//已绑定
    public static final String Delivery_Task_Status_zxqr = "0403";//中心确认
    public static final String Delivery_Task_Status_psz = "05";//配送中
    public static final String Delivery_Task_Status_zxyqs = "0501";//正向已签收
    public static final String Delivery_Task_Status_fxpsz = "0502";//反向配送中
    public static final String Delivery_Task_Status_fxyqs = "0503";//反向已签收
    public static final String Delivery_Task_Status_js = "06";//结束
    public static final String Delivery_Task_Status_gd = "08";//归档


    //3.2.11.	返回类型
    public static final String Return_Type_ZhouZhuanXiang = "01";//空周转箱
    public static final String Return_Type_ChaoQiBiao = "02";//超期表
    public static final String Return_Type_FenJianBiao = "03";//分拣表
    public static final String Return_Type_Other = "09";//其他


    // 终端认证ip地址
    public static final String IP_AUTH = "10.230.24.185";
    // 终端认证端口号
    public static final String PORT_AUTH = "6002";
     //22.58.244.96:8002
    //public static final String IP_AUTH = "22.58.244.96";
    //public static final String PORT_AUTH = "8002";

    /**
     * 订单类型
     */
    public static final String SG_APP_TYPE_EXPANSION_INDUSTRY = "01";//业扩类订单
    public static final String SG_APP_TYPE_ENGINEERING = "02";//工程类订单
    public static final String SG_APP_TYPE_MAINTAIN = "03";//维护类订单
}
