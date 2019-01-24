package com.xiao.jszpdemo.http;

public class JSZPUrls {
    public static final String SERVER = "http://192.168.1.100:8080/";
    /**
     * 配送签收
     */
    public static final String URL_GET_WAIT_SIGN_POSITIVE_IN_PLAN_DET = SERVER + "ioPlan/getWaitSignPositiveInPlanDet";
    public static final String URL_SIGN_POSITIVE_IN_PLAN_DET = SERVER + "ioPlan/signPositiveInPlanDet";
    /**
     * 补库入库
     */
    public static final String URL_GET_WAIT_IN_POSITIVE_IN_PLAN_DET = SERVER + "ioPlan/getWaitInPositiveInPlanDet";
    /**
     * 出入库扫描查询
     */
    public static final String URL_OUTBOUND_STORAGE_DEVICE_SCAN_RESULT_QUERY = SERVER + "ioPlan/queryCodes";
    /**
     * 2.1.3.7.4.补库入库
     */
    public static final String URL_POSITIVE_IN = SERVER + "ioPlan/positiveIn";

    /**
     * 2.1.6.1.1.设备扫描
     */
    public static final String URL_SCAN_DEV = SERVER + "scan/dev";
    /**
     * 2.1.3.8.1.获取待装车返程出库计划
     */
    public static final String URL_GET_WAIT_LOAD_NEGATIVE_OUT_PLAN_DET = SERVER + "ioTask/getWaitLoadNegativeOutPlanDet";

    /**
     * 2.1.3.8.2.返程装车
     */
    public static final String URL_LOAD_NEGATIVE_OUT_PLAN_DET = SERVER + "ioTask/loadNegativeOutPlanDet";
    /**
     * 2.1.3.9.1.查询待签收返程入库计划
     */
    public static final String URL_QUERY_WAIT_SIGN_NEGATIVE_IN_PLAN_DETS = SERVER + "ioTask/queryWaitSignNegativeInPlanDets";
    /**
     * 2.1.3.9.2.获取待签收返程入库计划
     */
    public static final String URL_GET_WAIT_SIGN_NEGATIVE_IN_PLAN_DET = SERVER + "ioTask/getWaitSignNegativeInPlanDet";
    /**
     * 2.1.3.9.3.返程签收
     */
    public static final String URL_SIGN_NEGATIVE_IN_PLAN_DET = SERVER + "ioTask/signNegativeInPlanDet";


    /**
     * 2.1.3.1.1.	查询物流运输记录  物流派车列表
     */
    public static final String URL_GET_LOGISTIC_SEND_LIST = SERVER + "distTask/queryLogisticses";
    /**
     * 2.1.3.1.2 物流派车详情
     */
    public static final String URL_GET_LOGISTIC_DETAIL = SERVER + "distTask/getLogistics";
    /**
     * 2.1.3.1.4 查询车辆
     */
    public static final String URL_GET_DRIVER_LIST = SERVER + "autoDoc/queryDistAutoes";

    /**
     * 2.1.3.3.1 装车确认  列表
     */
    public static final String URL_GET_WAIT_CONFIRM_LOGISTICS_LIST = SERVER + "distTask/queryWaitConfirmLogisticsTasks";

    /**
     * 出发确认列表
     */
    public static final String URL_GET_TASKITEM = SERVER + "queryWaitStartExpressTasks";




    /**
     * 2.1.3.3.2装车确认详情  配送单
     */
    public static final String URL_GET_WAIT_CONFIRM_LOGISTICS_DETAIL = SERVER + "distTask/getWaitConfirmLogisticsTask";
    /**
     * 2.1.3.3.2装车确认详情  确认装车
     */
    public static final String URL_GLOGISTICS_CONFIRM= SERVER + "distTask/logisticsConfirm";

}
