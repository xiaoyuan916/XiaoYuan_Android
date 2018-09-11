package com.sgcc.pda.jszp.util;

/**
 * author:xuxiaoyuan
 * date:2018/9/10
 */
public class JszpStatusUtils {
    public static String obtainAppStatus(String appStatus) {
        String str="";
        switch (appStatus) {
            case "00":
                str= "订单已终止";
            break;
            case "01":
                str= "订单已制定";
            break;
            case "02":
                str= "县公司已审核";
            break;
            case "03":
                str= "市公司已审核";
            break;
            case "035":
                str= "计量中心待审批";
            break;
            case "04":
                str= "计量中心已审批";
            break;
            case "045":
                str= "已平衡未确认";
            break;
            case "05":
                str= "已平衡确认";
            break;
            case "06":
                str= "配送计划已制定9";
            break;
            case "07":
                str= "配送任务已制定";
            break;
            case "08":
                str= "已出库";
            break;
            case "085":
                str= "配送中";
            break;
            case "09":
                str= "订单已签收";
            break;
            case "10":
                str= "已拒签";
            break;
            case "11":
                str= "已入库";
            break;
            case "12":
                str="拒签返回入库";
            break;
            case "13":
                str= "已完成";
            break;
            case "98":
                str= "订单已退回";
            break;
            case "99":
                str= "新建";
            break;
        }
        return str;
    }
}
