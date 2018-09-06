package com.sgcc.pda.jszp.base;

public class MyComment {
    /*
      0是配送装车，1是返回装车，2是返回装车清点，3是配送签收，4是配送扫码签收
      5是返程签收
     */

    public static final int SCAN_DELIVERY =0;
    public static final int SCAN_RETURN =1;
    public static final int RETURN_CHECK =2;
    public static final int SCAN_SIGN_FOR=3;
    public static final int SIGN_FOR=4;
    public static final int SCAN_RETURN_SIGN=5;//sub_type=0是扫描任务，1是扫描单，2是扫描设备
    public static final int SCAN_DEVICE_OUT=6;//sub_type=-1是搜索，0是接收单位任务编号，1是扫描设备资产编号
    public static final int SCAN_DEVICE_IN=7;//0是扫描配送单编号，1是扫描设备资产编号
    public static final int SCAN_DEVICE_PICK=9;//0是开始设备编号，1是结束设备编号
    public static final int QUERY_DEVICE =10;
    public static final int SCAN_EXPRESS_DEVICE =11;//快递绑定扫描设备  0扫描设备  1扫描快递单号
    public static final int SCAN_IN_DEVICE =12;//扫描出库入库返回
    public static final int SCAN_RETURN_WAREHOUSE =13;//返程签收
}
