package com.sgcc.pda.hardware.protocol.p698;


import com.sgcc.pda.hardware.hardware.ICommunicate;
import com.sgcc.pda.hardware.protocol.p698.bean698.AppConnReq;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.Frame698;
import com.sgcc.pda.hardware.protocol.p698.bean698.Security;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.utils.Constant;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.sdk.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * 698协议帧组装
 */

public class CommonP698EssentialMethod {

    public static String safeTrans(String meterAdress, String taskData) {
        Make698Frame make698 = new Make698Frame();
        Frame698 frame698 = new Frame698();
       // frame698.setObjclassifi((byte) 10); //对象分类
        frame698.setMeterComId(meterAdress); //地址
        frame698.setMeterComIdType("00"); //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd("00"); //逻辑地址
        String p698Code = make698.maker698safe(frame698,taskData);
        LogUtil.d(Constant.LogFilter, "执行远程控制 p698Code = " + p698Code);

        return p698Code;
    }

    /**
     * 密钥下装分帧 处理
     * @param meterAdress 通信地址
     * @param apduData  APDU
     * @return
     */
    public static String myxzsSafeTrans(String meterAdress, String apduData) {
        Make698Frame make698 = new Make698Frame();

        Frame698 frame698 = new Frame698();
        // frame698.setObjclassifi((byte) 10); //对象分类
        frame698.setMeterComId(meterAdress); //地址
        frame698.setMeterComIdType("00"); //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd("00"); //逻辑地址
        String p698Code = make698.maker698safeFz(frame698,apduData);
        LogUtil.d(Constant.LogFilter, "执行远程控制 p645Code = " + p698Code);

        return p698Code;
    }

    /**
     * 密钥协商建立连接
     * @return
     */
    public static String myxs(String meterAdress ,String m1,String s1){
        Constant698.M1 = m1;  //密文1
        Constant698.SIGE1 = s1; //签名1
        AppConnReq ac =getAc();  //获取请求连接对象
        //3、组装发送建立应用连接报文
        Frame698     frame698 = new  Frame698();
        frame698.setAppConnReq(ac);
        frame698.setMeterComId(meterAdress); //地址
        frame698.setMeterComIdType("00"); //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd("00"); //逻辑地址
        frame698.setObjclassifi((byte)2);    //----------对象操作
        Make698Frame mf =  new  Make698Frame();
        String  frame  =    mf.maker698(frame698);    //获取建立应用连接发送报文

        return  frame;
    }

    public static  AppConnReq getAc() {
        AppConnReq ar = new  AppConnReq();    //定义建立应用连接请求对象
        ar.setVersion("0010");
        ar.setProtocal("FFFFFFFFFFFFFFFF");
        ar.setFunction("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
        ar.setSendMaxSize("0400");
        ar.setReviceMaxSize("0400");
        ar.setReviceMaxWinSize("01");
        ar.setMaxApduSize("0400");
        ar.setLinkOverTime("00000064");
        ar.setConnMechInfo("02");
        return  ar;
    }

    public static String getEsamInfoFrame(String meterAdress) {
        String backFrame = "";
        Frame698 frame698 = new Frame698();
        frame698.setMeterComId(meterAdress);  //服务器地址
        frame698.setMeterComIdType("00");  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd("00");  //逻辑地址
        frame698.setObjclassifi((byte) 5);    //----------对象操作
        DataItem698 di4 = new DataItem698("F100", "7");
        List<DataItem698> list = new ArrayList<DataItem698>();
        list.add(di4);
        frame698.setDataItem698(list);
        Make698Frame mf = new Make698Frame();
        String frame = mf.maker698(frame698);    //获取读取ESAM信息报文

        return  frame;
    }



    public static String getRandom1Ciper(String meterAdress,String random1) {
        String backFrame = "";
        Frame698 frame698 = new Frame698();
        frame698.setMeterComId(meterAdress);  //服务器地址
        frame698.setMeterComIdType("00");  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd("00");  //逻辑地址
        frame698.setObjclassifi((byte) 7);    //----------操作
        DataItem698 di4 = new DataItem698("F100", "11");
        di4.setDataType("RN");
        di4.setInputParam("08"+random1);
        List<DataItem698> list = new ArrayList<DataItem698>();
        list.add(di4);
        frame698.setDataItem698(list);
        Make698Frame mf = new Make698Frame();
        String frame = mf.maker698(frame698);    //获取读取ESAM信息报文

        return  frame;
    }

    public static String checkRandom2Ciper(String meterAdress,String random2) {
        String backFrame = "";
        Frame698 frame698 = new Frame698();
        frame698.setMeterComId(meterAdress);  //服务器地址
        frame698.setMeterComIdType("00");  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd("00");  //逻辑地址
        frame698.setObjclassifi((byte) 7);    //----------操作
        DataItem698 di4 = new DataItem698("F100", "12");
        di4.setDataType("octet-string");
        di4.setInputParam("08"+random2);
        List<DataItem698> list = new ArrayList<DataItem698>();
        list.add(di4);
        frame698.setDataItem698(list);
        Make698Frame mf = new Make698Frame();
        String frame = mf.maker698(frame698);    //获取读取ESAM信息报文

        return  frame;
    }


    public static String getEsamNum(String meterAdress) {
        String backFrame = "";
        Frame698 frame698 = new Frame698();
        frame698.setMeterComId(meterAdress);  //服务器地址
        frame698.setMeterComIdType("00");  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd("00");  //逻辑地址
        frame698.setObjclassifi((byte) 5);    //----------对象操作
        DataItem698 di4 = new DataItem698("F100", "2");
        List<DataItem698> list = new ArrayList<DataItem698>();
        list.add(di4);
        frame698.setDataItem698(list);
        Make698Frame mf = new Make698Frame();
        String frame = mf.maker698(frame698);    //获取读取ESAM信息报文

        return  frame;
    }
    public static String getMeterNum(String meterAdress) {
        String backFrame = "";
        Frame698 frame698 = new Frame698();
        frame698.setMeterComId(meterAdress); //服务器地址
        frame698.setMeterComIdType("00");  //地址类型 (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd("00");  //逻辑地址
        frame698.setObjclassifi((byte) 5);    //----------对象操作
        DataItem698 di4 = new DataItem698("4002", "2");
        List<DataItem698> list = new ArrayList<DataItem698>();
        list.add(di4);
        frame698.setDataItem698(list);
        Make698Frame mf = new Make698Frame();
        String frame = mf.maker698(frame698);
        return  frame;
    }
    public static String getMeterAddress(String meterAdress) {
        String backFrame = "";
        Frame698 frame698 = new Frame698();
        frame698.setMeterComId(meterAdress); //服务器地址
        frame698.setMeterComIdType("00");  //地址类型 (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd("00");  //逻辑地址
        frame698.setObjclassifi((byte) 5);    //----------对象操作
        DataItem698 di4 = new DataItem698("4001", "2");
        List<DataItem698> list = new ArrayList<DataItem698>();
        list.add(di4);
        frame698.setDataItem698(list);
        Make698Frame mf = new Make698Frame();
        String frame = mf.maker698(frame698);
        return  frame;
    }
    /**
     * 读取电表继电器状态
     * @param meterAdress
     * @return
     */
    public static String getMeterStatu(String meterAdress ,String taskData) {
//        String backFrame = "";
//        Frame698 frame698 = new Frame698();
//        frame698.setMeterComId(meterAdress); //服务器地址
//        frame698.setMeterComIdType("00"); //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
//        frame698.setLogiAdd("00");  //逻辑地址
//        frame698.setObjclassifi((byte) 5);//----------对象操作
//        DataItem698 di4 = new DataItem698("8000", "3");
//        List<DataItem698> list = new ArrayList<DataItem698>();
//        list.add(di4);
//        frame698.setDataItem698(list);
//        Make698Frame mf = new Make698Frame();
//        String frame = mf.maker698(frame698);    //读取电表继电器状态
        Make698Frame make698 = new Make698Frame();
        Frame698 frame698 = new Frame698();
        frame698.setMeterComId(meterAdress); //地址
        frame698.setMeterComIdType("00"); //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd("00"); //逻辑地址
        String p698Code = make698.maker698safe(frame698,taskData);
        return  p698Code;
    }
    public static String  checkTime() {
        Make698Frame make698 = new Make698Frame();
        List<DataItem698> list = new ArrayList<DataItem698>();
        DataItem698 dataItem = new DataItem698();
        dataItem.setObjIdent("4000");  //数据标识  日期时间
        dataItem.setMemberCode("2");   //属性
        dataItem.setDataType("date_time_s");
        String time = DataConvert.getCurrentTimeString("yyMMddHHmmss");
        time = DataConvert.toHexString(time);
        dataItem.setInputParam(time);
        list.add(dataItem);
        Frame698 frame698 = new Frame698();
        frame698.setObjclassifi((byte) 6); //对象分类    设置
        frame698.setMeterComId("000000000001"); //地址
        frame698.setMeterComIdType("00"); //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd("00"); //逻辑地址
        frame698.setDataItem698(list);

        String p698Code = make698.maker698(frame698);

        return  p698Code;
    }
}



