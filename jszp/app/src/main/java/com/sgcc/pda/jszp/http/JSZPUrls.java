package com.sgcc.pda.jszp.http;

import com.sgcc.pda.jszp.base.BaseApplication;
import com.sgcc.pda.jszp.util.JSZPAppInfoUtils;

public class JSZPUrls {
    public static final String SERVER = JSZPAppInfoUtils.isApkInDebug(BaseApplication.getInstance()) ?
            "http://192.168.20.211:9096/mod/":"http://192.168.20.211:9096/mod/";
    /**
     * 配送签收
     */
    public static final String URL_GET_WAIT_SIGN_POSITIVE_IN_PLAN_DET = SERVER + "ioTask/getWaitSignPositiveInPlanDet";
    public static final String URL_SIGN_POSITIVE_IN_PLAN_DET = SERVER + "ioTask/signPositiveInPlanDet";
    /**
     * 补库入库
     */
    public static final String URL_GET_WAIT_IN_POSITIVE_IN_PLAN_DET = SERVER + "ioTask/getWaitInPositiveInPlanDet";
    /**
     * 出入库扫描查询
     */
    public static final String URL_OUTBOUND_STORAGE_DEVICE_SCAN_RESULT_QUERY = SERVER + "ioTask/queryCodes";
    /**
     * 2.1.3.7.4.补库入库
     */
    public static final String URL_POSITIVE_IN = SERVER + "ioTask/positiveIn";

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
     * 物流出发确认列表
     */
    public static final String URL_GET_TASKITEM = SERVER + "distTask/queryWaitStartLogisticsTasks";

    /**
     * 快递出发确认列表
     */
    public static final String URL_POST_EXPRESS_GO = SERVER + "distTask/queryWaitStartExpressTasks";

    /**
     * 自提出发确认列表
     */
    public static final String URL_POST_SELF_GO = SERVER + "distTask/queryWaitStartTakeTasks";

    /**
     * 开始配送
     */
    public static final String URL_POST_CONFIRM_GO = SERVER + "distTask/startDist";

    /**
     * 物流出发详情
     */
    public static final String URL_POST_LOGISTICS_DETAIL_GO = SERVER + "distTask/getWaitStartLogisticsTask";


    /**
     * 2.1.3.3.2装车确认详情  配送单
     */
    public static final String URL_GET_WAIT_CONFIRM_LOGISTICS_DETAIL = SERVER + "distTask/getWaitConfirmLogisticsTask";
    /**
     * 2.1.3.3.2装车确认详情  确认装车
     */
    public static final String URL_GLOGISTICS_CONFIRM= SERVER + "distTask/logisticsConfirm";


    /**
     *2.1.3.4.1.	查询待确认快递任务  快递确认列表
     */
    public static final String URL_GET_WAIT_CONFIRM_EXPRESS_TASK= SERVER + "distTask/queryWaitConfirmExpressTasks";
    /**
     * 2.1.5.1.设备查询
     */
    public static final String URL_GET_EQUIP_INFO= SERVER + "assert/getEquipInfo";

    /**
     *2.1.3.1.3.派车   物流派车--》派车
     */
    public static final String URL_ASSIGN_CAR= SERVER + "distTask/assignCar";
    /**
     *2.1.3.1.5.	保存车辆信息
     */
    public static final String URL_AUTO_SAVE= SERVER + "autoDoc/save";

    /**
     * 返程入库列表
     */
    public static final String URL_RETURN_WAREHOUSE_LIST= SERVER + "ioTask/queryWaitInNegativeInPlanDets";
    /**
     * 返程入库商品
     */
    public static final String URL_RETURN_WAREHOUSE_GOODS_LIST= SERVER + "ioTask/getWaitInNegativeInPlanDet";
    /**
     * 返程入库商品
     */
    public static final String URL_RETURN_WAREHOUSE_GOODS_CONFIRM_LIST= SERVER + "ioTask/negativeIn";


    /**
     *出库任务查询
     */
    public static final String URL_QUERY_POSITIVE_OUT_PLAN= SERVER + "ioTask/queryPositiveOutPlanDets";


    /**
     *获取配送出库计划
     */
    public static final String URL_GET_POSITIVE_OUT_PLAN_DET= SERVER + "ioTask/getPositiveOutPlanDet";
    /**
     *查询待出发快递任务
     */
    public static final String URL_GET_WAIT_CONFIRM_EXPRESS_TASK_DETAIL= SERVER + "distTask/getWaitConfirmExpressTask";
    /**
     *快递确认
     */
    public static final String URL_EXPRESS_CONFIRM= SERVER + "distTask/expressConfirm";
    /**
     *快递绑定
     */
    public static final String URL_EXPRESS_BIND= SERVER + "distTask/expressBind";
    /**
     *获取待出发快递任务
     */
    public static final String URL_GET_WAIT_START_EXPRESS_TASK= SERVER + "distTask/getWaitStartExpressTask";
    /**
     *获取待出发自提任务
     */
    public static final String URL_GET_WAIT_START_TAKE_TASK= SERVER + "distTask/getWaitStartTakeTask";

    /**
     * 2.1.5.2.1.查询订单
     */
    public static final String URL_QUERY_DIST_APPS= SERVER + "distApp/queryDistApps";

    /**
     * 2.1.5.2.2.获取订单
     */
    public static final String URL_GET_DIST_APP= SERVER + "distApp/getDistApp";

    /**
     * 2.1.5.2.2.获取订单
     */
    public static final String URL_QUERY_SUB_WH_AND_DP= SERVER + "distApp/querySubWhAndDp";

    /**
     * 2.1.4.1.1.周转箱初始化
     */
    public static final String URL_INIT_TURNOVER_BOX= SERVER + "box/initTurnoverBox";
    /**
     * 2.1.4.1.1.周转箱初始化
     */
    public static final String URL_SCAN_TURNOVER_BOXS= SERVER + "box/scanTurnoverBoxs";
    /**
     * 2.1.4.1.3.查询已扫描周转箱
     */
    public static final String URL_QUERY_TURNOVER_BOXS= SERVER + "box/queryTurnoverBoxs";
    /**
     * 2.1.4.1.4.周转箱召回
     */
    public static final String URL_CALL_TURNOVER_BOXS= SERVER + "box/callTurnoverBoxs";
}
