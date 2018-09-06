package com.sgcc.pda.hardware.protocol.p698.utils;



import com.sgcc.pda.hardware.protocol.p698.Make698Frame;
import com.sgcc.pda.hardware.protocol.p698.Parser698;
import com.sgcc.pda.hardware.protocol.p698.bean698.AppConnReq;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.Frame698;
import com.sgcc.pda.hardware.protocol.p698.bean698.LinkBean;
import com.sgcc.pda.hardware.protocol.p698.bean698.RecordRcsdBean;
import com.sgcc.pda.hardware.protocol.p698.bean698.RecordRsdBean;
import com.sgcc.pda.hardware.protocol.p698.bean698.Security;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//衔接698页面和698后台代码
public class Sp698ProtoUtil {

    /**
     * 1号485口数据单元标识
     */
    private static final String ITF_CODE_01 = "01";
    /**
     * 3号红外口数据单元标识
     */
    private static final String ITF_CODE_03 = "03";
    //-----------------------  报文信息
    /**
     * 服务器地址
     */
    public static String METERCOM_ID = "888888888888";
    /**
     * 地址类型
     */
    public static String METERCOM_ID_TYPE = "00";
    /**
     * 逻辑地址
     */
    public static String LOGIN_ADD = "00";
    //------------------------------------对象相关属性
    /**
     * 对象标识
     */
    public static String OBJ_IDENT = "";
    /**
     * 成员编号
     */
    public static String MEM_CODE = "";
    /**
     * 对象特征
     */
    public static String OBJ_FEAT = "";
    /**
     * 元素索引
     */
    public static String ELE_INDEX = "";
    /**
     * 数据类型
     */
    public static String DATA_TYPE = "";
    /**
     * 对象名称
     */
    public static String OBJ_NAME = "";
    //------------------------生成报文的对象	
    /**
     * 存储将要生成报文的698数据项对象
     */
    public static Map<String, DataItem698> LIST_ITEM_698 = new HashMap<String, DataItem698>();  //采用Map数据结构避免重复
    //---------------操作相关信息
    /**
     * 操作类型
     */
    public static String OPT_TYPE = "05";  //默认读取
    /**
     * 安全模式
     */
    public static String SECURITY = "1";  //默认非安全模式
    /**
     * 执行设置、操作后是否读取
     */
    public static String SET_OR_OPT = null;
    /***
     *  设置属性、操作方法对象数据类型为array, 同时包含的元素结构复杂时，应用
     */
    public static Map<String, Object> SETOROPERMAP = new HashMap<String, Object>();
    /**
     * 数据加密方法    1:明文+MAC   2:密文  3:密文加MAC
     */
    public static int encryType;
    /**
     * 读取对象属性时，存储换算值   key  标识+属性编号+属性特征+元素索引    value   换算值
     */
    public static Map<String, String> conv = new HashMap<String, String>();

    //将传递过来的OAD转换成对象标识、 属性编号、 属性特征、 元素索引,  并将值赋值给di
    public static DataItem698 oadChangeParam(String oad, DataItem698 di) {
        di.setObjIdent(oad.substring(0, 4));//对象标识
        String parFae = Integer.toBinaryString(Integer.parseInt(oad.substring(4, 6), 16));   //  特征[5-7]   属性编号[0-4]
        if (parFae.length() < 8) {
            parFae = "00000000".substring(0, 8 - parFae.length()) + parFae;
        }
        di.setMemberCode(String.valueOf(Integer.parseInt(parFae.substring(3), 2)));//属性编号
        di.setObjFeatures(String.valueOf(Integer.parseInt(parFae.substring(0, 3), 2)));//属性特征
        di.setElementIndex(String.valueOf(Integer.parseInt(oad.substring(6, 8), 16))); //元素索引
        return di;
    }

    //建立应用连接 --------------根据页面提交的数据信息 生成报文 并发送  获取到服务器的响应的报文
    public static Frame698 ass698BuildAppLinkReq(AppConnReq ac) {
        Frame698 frame698 = new Frame698();
        String backFrame = "";   //接收响应报文
        frame698.setAppConnReq(ac);
        frame698.setMeterComId(METERCOM_ID);  //服务器地址
        frame698.setMeterComIdType(METERCOM_ID_TYPE);  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd(LOGIN_ADD);  //逻辑地址
        frame698.setObjclassifi((byte) 2);    //----------对象操作
        /**
         * 密钥协商、建立应用连接
         * 1、 获取到表号、ESAM序列号、ESAM对称密钥版本、 ESAM计时器数据
         * 2、通过1读取到的数据调用加密机函数获取到主站随机数、密文1、签名1
         * 3、组装建立应用连接报文并发送
         * 4、解析建立应用连接返回报文、获取到 密文2、签名2
         * 5、通过密文2、 签名2调用加密机函数获取到回话密钥
         * 完成建立应用连接
         */
        //1、读取电能表表号  、读ESAM序列号  、 读ESAM对称密钥 、  读ESAM计数器信息
        getEsamInfoFrame();
        //2、获取主站随机数、 密文1、签名1
        getRandAndSessAndSign();
        //3、组装发送建立应用连接报文
        Make698Frame mf = new Make698Frame();
        frame698.setMeterComId(METERCOM_ID);  //服务器地址
        frame698.setMeterComIdType("0");      //地址类型
        String frame = mf.maker698(frame698);    //获取建立应用连接发送报文
        backFrame = get698MessagePack(false, frame, ITF_CODE_01);
        //4、解析建立应用连接返回报文、获取到 密文2、签名2
        Parser698 par = new Parser698();  //解析698报文对象
        byte[] b = CommUtils.hex2Binary(backFrame);
        frame698 = par.parser698(b);
        String sess = frame698.getAppConnReq().getConnRespInfo().getRn();
        String sign = frame698.getAppConnReq().getConnRespInfo().getSignInfo();
        //5、通过密文2、 签名2 获取到 回话协商密钥
        getSessionKey(sess, sign);  //密文2、  签名2
        return frame698;
    }

    //读取 电能表表号  、ESAM序列号  、 ESAM对称密钥 、  ESAM计数器信息
    private static void getEsamInfoFrame() {
        String backFrame = "";
        Frame698 frame698 = new Frame698();
        frame698.setMeterComId(METERCOM_ID);  //服务器地址
        frame698.setMeterComIdType(METERCOM_ID_TYPE);  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd(LOGIN_ADD);  //逻辑地址
        frame698.setObjclassifi((byte) 5);    //----------对象操作
        // 读取电能表通讯地址 、读取电能表表号 、读ESAM序列号  、 读ESAM对称密钥 、  读ESAM计数器
        DataItem698 di0 = new DataItem698("4001", "2");
        DataItem698 di1 = new DataItem698("4002", "2");
        DataItem698 di2 = new DataItem698("F100", "2");
        DataItem698 di3 = new DataItem698("F100", "4");
        DataItem698 di4 = new DataItem698("F100", "7");
        List<DataItem698> list = new ArrayList<DataItem698>();
        list.add(di0);
        list.add(di1);
        list.add(di2);
        list.add(di3);
        list.add(di4);
        frame698.setDataItem698(list);
        Make698Frame mf = new Make698Frame();
        String frame = mf.maker698(frame698);    //获取读取ESAM信息报文
        backFrame = get698MessagePack(false, frame, ITF_CODE_01);
//		   InitialOuterChipItfInfo   ioci = new  InitialOuterChipItfInfo();
//		   ioci.getESAMInfo(backFrame);  //解析报文  获取到  电能表表号 、 ESAM序列号、  对称密码版本号、  ESAM计时器
    }

    // 获取到主站随机数、 密文1、 客户机签名1
    private static void getRandAndSessAndSign() {
//		  InitialOuterChipItfInfo   ioci = new  InitialOuterChipItfInfo();
//		  List<String>   info =  ioci.getSessionInfo(InitialOuterChipItfInfo.esamSeriesNum, InitialOuterChipItfInfo.meterNumber, InitialOuterChipItfInfo.esamCounter, InitialOuterChipItfInfo.esamSymmetrickeyVersion);
//		  Constant698.RAND_HOST = info.get(0); //随机数
//		  Constant698.RN =info.get(0); //随机数
//		  Constant698.M1 = info.get(1);  //密文1
//		  Constant698.SIGE1 = info.get(2); //数字签名1
    }

    //通过密文2、 签名2 获取到 回话协商密钥
    private static void getSessionKey(String sess, String sign) {
//		  InitialOuterChipItfInfo   ioci = new  InitialOuterChipItfInfo();
//		  if("0".equals(Constant698.I_KEY_STATE)){
//			  Constant698.SESSION_KEY = ioci.getSessionKey(0, InitialOuterChipItfInfo.esamSeriesNum, Constant698.RAND_HOST, sess, sign);
//		  }else{
//			  Constant698.SESSION_KEY = ioci.getSessionKey(1, InitialOuterChipItfInfo.meterNumber, Constant698.RAND_HOST, sess, sign);
//		  }

    }

    //断开应用连接   ---生成报文 并发送  获取到服务器的响应的报文
    public static Frame698 ass698NoLink() {
        Frame698 frame698 = new Frame698();
        String backFrame = "";   //接收响应报文
        frame698.setMeterComId(METERCOM_ID);  //服务器地址
        frame698.setMeterComIdType(METERCOM_ID_TYPE);  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd(LOGIN_ADD);  //逻辑地址
        frame698.setObjclassifi((byte) 3);    //----------对象操作
        Make698Frame mf = new Make698Frame();
        String frame = mf.maker698(frame698);    //获取断开应用连接报文
        //发送断开应用连接报文
        backFrame = get698MessagePack(false, frame, ITF_CODE_01);
        //获取服务器响应断开应用连接报文
        if ("".equals(backFrame)) {
            backFrame = "683200430588888888888800D67C830000D67C16";
        }
        Parser698 par = new Parser698();  //解析698报文对象
        byte[] b = CommUtils.hex2Binary(backFrame);  //上行报文
        frame698 = par.parser698(b);
        return frame698;
    }


    //执行预连接  --------------根据页面提交的数据信息 生成报文 并发送  获取到服务器的响应的报文
    public static Frame698 ass698WillLink(LinkBean lb) {
        Frame698 frame698 = new Frame698();
        String backFrame = "";
        frame698.setLinkBean(lb);
        frame698.setMeterComId(METERCOM_ID);  //服务器地址
        frame698.setMeterComIdType(METERCOM_ID_TYPE);  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd(LOGIN_ADD);  //逻辑地址
        frame698.setObjclassifi((byte) 1);    //----------对象操作
         /*
		  Make698Frame   mf =  new  Make698Frame();
		  String  frame  =    mf.maker698(frame698);   //获取698预连接报文
		  String  backFrame ="";
		  //发送698预连接报文 并获取响应报文
		  backFrame =  get698MessagePack(false, frame, ITF_CODE_01);  */
        if ("".equals(backFrame)) {
            backFrame = "683000010507091905162010000081008007E0051304080500008907E0051304080501025F07E005130408050202DA000016";
        }
        Parser698 par = new Parser698();  //解析698报文对象
        byte[] b = CommUtils.hex2Binary(backFrame);  //上行报文
        frame698 = par.parser698(b);
        return frame698;
    }

    //读取对象属性值 -----生成报文返回

    /**
     * List<DataItem698>   数据项集合
     * Security    sr  安全传输对象     如果不是安全传输  参数值为null    如果是安全传输  应用数据单元方式设置为 页面值
     * 页面值
     * 1       非安全传输
     * 2       明文+数据验证码
     * 3       明文+随机数
     * 4       明文+随机数+数据MAC
     * 5       明文+安全标识
     * 6       密文+数据验证码
     * 7       密文+随机数
     * 8       密文+随机数+数据MAC
     * 9       密文+安全标识
     * <p>
     * 协议值
     * 0  明文数据    1 密文数据
     * 0  数据验证码   1 随机数    2 随机数+数据MAC   3安全标识
     */
    public static String ass698FrameRead(List<DataItem698> di, Security sr) {
        Frame698 frame698 = new Frame698();
        frame698.setDataItem698(di);    //设置698数据项
        frame698.setMeterComId(METERCOM_ID);  //服务器地址
        frame698.setMeterComIdType(METERCOM_ID_TYPE);  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd(LOGIN_ADD);  //逻辑地址
        frame698.setObjclassifi((byte) 5);    //----------对象操作
        if (sr != null) {
            frame698.setIsSecurity(true);
            frame698.setSecurityObj(getSecurityObj(sr));
            frame698.setObjclassifi((byte) 10);
        }
        Make698Frame mf = new Make698Frame();
        return mf.maker698(frame698);
    }

    //读取记录型对象属性  -----生成报文返回

    /****
     *  List<DataItem698>  di       对象属性描述符
     *  List<RecordRsdBean>  rsd    记录选择描述符
     *  List<RecordRcsdBean>  rcsd  记录列选择描述符
     *  Security  sr    sr  安全传输 对象     如果不是安全传输  参数值为null
     */
    public static String ass698FrameRead(List<DataItem698> di, List<RecordRsdBean> rsd, List<RecordRcsdBean> rcsd, Security sr) {
        Frame698 frame698 = new Frame698();
        frame698.setDataItem698(di);  //记录型对象
        frame698.setRsd(rsd);   //记录选择描述符
        frame698.setRcsd(rcsd);  //记录列选择描述符
        frame698.setMeterComId(METERCOM_ID);  //服务器地址
        frame698.setMeterComIdType(METERCOM_ID_TYPE);  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd(LOGIN_ADD);  //逻辑地址
        frame698.setSetOrOpt("1");    //读取记录型对象标识
        frame698.setObjclassifi((byte) 5);    //----------对象操作
        if (sr != null) {
            frame698.setIsSecurity(true);
            frame698.setSecurityObj(getSecurityObj(sr));
            frame698.setObjclassifi((byte) 16);
        }
        Make698Frame mf = new Make698Frame();
        return mf.maker698(frame698);
    }

    //获取安全传输对象
    private static Security getSecurityObj(Security sr) {
        Security sry = new Security();
        switch (Integer.parseInt(sr.getType())) {
            case 2:     //明文+数据验证码
                sry.setType("0");   //明文
                sry.setSecType("0");  //数据验证码
                sry.setSecSid(Constant698.SID_IDENT);  //标识
                sry.setAttrachData(Constant698.ATTACH_DATA);   //附件数据
                sry.setMac(Constant698.MAC);  //mac
                break;
            case 3:     //明文+随机数
                sry.setType("0");   //明文
                sry.setSecType("1");  //随机数
                sry.setRn(Constant698.RN);  //随机数
                break;
            case 4:     //明文+随机数+数据MAC
                sry.setType("0");   //明文
                sry.setSecType("2");  //随机数
                sry.setRn(Constant698.RN);  //随机数+数据MAC
                sry.setMac(Constant698.MAC);//数据MAC
                break;
            case 5:     //明文+安全标识
                sry.setType("0");   //明文
                sry.setSecType("3");  //随机数
                sry.setSecSid(Constant698.SID_IDENT);  //标识
                break;
            case 6:      //密文+数据验证码
                sry.setType("1");   //密文
                sry.setSecType("0");  //数据验证码
                sry.setSecSid(Constant698.SID_IDENT);  //标识
                sry.setAttrachData(Constant698.ATTACH_DATA);   //附件数据
                sry.setMac(Constant698.MAC);  //mac
                break;
            case 7:      //密文+随机数
                sry.setType("1");   //密文
                sry.setSecType("1");  //随机数
                sry.setRn(Constant698.RN);  //随机数
                break;
            case 8:      //密文+随机数+数据MAC
                sry.setType("1");   //密文
                sry.setSecType("2");  //随机数
                sry.setRn(Constant698.RN);  //随机数+数据MAC
                sry.setMac(Constant698.MAC);//数据MAC
                break;
            case 9:      //密文+安全标识
                sry.setType("1");   //密文
                sry.setSecType("3");  //随机数
                sry.setSecSid(Constant698.SID_IDENT);  //标识
                break;
        }
        return sry;
    }

    //设置对象属性值

    public static String ass698FrameSet(List<DataItem698> di, Security sr) {
        Frame698 frame698 = new Frame698();
        frame698.setDataItem698(di);    //设置698数据项
        frame698.setMeterComId(METERCOM_ID);  //服务器地址
        frame698.setMeterComIdType(METERCOM_ID_TYPE);  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd(LOGIN_ADD);  //逻辑地址
        frame698.setSetOrOpt(SET_OR_OPT);   //设置后是否读取
        frame698.setObjclassifi((byte) 6);    //----------对象属性设置
        if (sr != null) {
            frame698.setIsSecurity(true);
            frame698.setSecurityObj(getSecurityObj(sr));
            frame698.setObjclassifi((byte) 16);
        }
        SET_OR_OPT = null;
        Make698Frame mf = new Make698Frame();
        return mf.maker698(frame698);
    }


    //操作对象方法
    public static String ass698FrameOperator(List<DataItem698> di, Security sr) {
        Frame698 frame698 = new Frame698();
        frame698.setDataItem698(di);    //设置698数据项
        frame698.setMeterComId(METERCOM_ID);  //服务器地址
        frame698.setMeterComIdType(METERCOM_ID_TYPE);  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd(LOGIN_ADD);  //逻辑地址
        frame698.setSetOrOpt(SET_OR_OPT);   //设置后是否读取
        frame698.setObjclassifi((byte) 7);    //----------对象方法操作
        if (sr != null) {
            frame698.setIsSecurity(true);
            frame698.setSecurityObj(getSecurityObj(sr));
            frame698.setObjclassifi((byte) 16);
        }
        SET_OR_OPT = null;
        Make698Frame mf = new Make698Frame();
        return mf.maker698(frame698);
    }

    //  调用加密机  获取密文
    public static byte[] getCiphertext(byte[] express, Frame698 frame698) {
        String data = CommUtils.byteToHexString(express);
        String oi = data.substring(6, 10);//获取对象标识
        byte[] cipher;   //密文
        int paramfore = 3; //第四个参数值
        List<String> value = new ArrayList<String>();
        /*****
         *  2明文+数据验证码 3明文+随机数  4	明文+随机数+数据MAC     5明文+安全标识
         *  6密文+数据验证码 7密文+随机数  8密文+随机数+数据MAC      9密文+安全标识
         */
        switch (Integer.parseInt(frame698.getSecurityObj().getSecType())) {
            case 3:
            case 7:
                encryType = 2;  //解密需要用到该参数
            case 2:
            case 4:
            case 5:
                encryType = 1;
            case 6:
            case 8:
            case 9:
                encryType = 3;
        }
        if ("F101".equals(oi)) { //安全模式参数
            paramfore = 4;
        } else if ("8000".equals(oi)) { //拉闸任务
            if ("82".equals(data.substring(10, 12))) { //是否操作方法130  合闸
                paramfore = 8;
            }
        } else if ("400A".equals(oi) || "400B".equals(oi) || "4018".equals(oi) || "4019".equals(oi)
                || "401A".equals(oi) || "401B".equals(oi)) { // 切换时间  费率
            paramfore = 5;
        }
        /***
         *    第一个参数：
         *    1,明文+MAC; 2,密文; 3,密文+MAC （此处值同解密值）
         *    第四个参数值：
         *    2,设置会话实效门限;
         * 	4,安全模式参数、会话时效门限;
         * 	5,电价、电价切换时间、费率时段、对时;
         * 	8,拉闸任务
         * 	3,除上述操作外的数据加密，密钥更新、写ESAM 操作和钱包操作数据下发通过此函数进行安全计算;
         *     返回值
         *     标识【SID】、  附加数据【ATTACHDATA】、 密文【DATA】、消息鉴别码 【MAC】
         */
//		  value=NewEncryptionMachine.getInstance().Obj_Meter_Formal_GetSessionData(encryType, "", Constant698.SESSION_KEY, paramfore, data);
        frame698.getSecurityObj().setSecSid(value.get(0));  //SID
        frame698.getSecurityObj().setAttrachData(value.get(1));  //附件数据
        frame698.getSecurityObj().setMac(value.get(3));  //mac
        Constant698.MAC = value.get(3);
        cipher = CommUtils.hex2Binary(value.get(2));  //密文
        return cipher;
    }

    // 调用加密机  解密密文  获取明文
    public static byte[] getExpress(byte[] ciphertext) {
        byte[] cipher = null;
        String data = CommUtils.byteToHexString(ciphertext);
		    /*String  express = NewEncryptionMachine.getInstance().Obj_Meter_Formal_VerifyMeterData(encryType, "",  Constant698.SESSION_KEY,  data, Constant698.MAC);
		    cipher =  CommUtils.hex2Binary(express); */
        return cipher;
    }

    /**
     * 下发698报文并获取返回698数据内容
     *
     * @param isQueryCmd 是否为查询命令
     * @param chipPort   外围接口板数据单元标识
     * @return
     */
    private static String get698MessagePack(boolean isQueryCmd, String msgForSend, String chipPort) {

        if ((msgForSend == null || msgForSend.length() <= 0) && !isQueryCmd) {
            throw new RuntimeException("【外围接口初始化】未成功获取下行报文数据内容,无法发送下行命令获取外围接口板初始化信息.", new NullPointerException());
        }
        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put(chipPort, msgForSend);
        dataList.add(map);
			
			/*IDeviceAfn itfAfn=new ItfAfnImpl();									//下发抄读命令
			ExecResult result =itfAfn.afn06(SyncStateContract.Constants.ADDRESS_0A_ITF,dataList);
			if(result.isSuccess() && !result.getUnitMap().isEmpty()){
				String resValue = (String) result.getUnitMap().get(chipPort);
				if(resValue!=null&&resValue.length()>2){										//数据长度大于1字节,为数据内容 否则认为时链路状态
					return resValue;
				}
			}
			try{																//如果电表未返回报文数据，继续下发查询命令
				Thread.sleep(1000);
			}catch(Exception e){
				LogHandler.error(LogHandler.DEVICE,"【初始化外围接口】下发698报文线程休眠异常",e);
				throw new RuntimeException("【初始化外围接口】下发698报文线程休眠异常",e);
			}*/
        return get698MessagePack(true, msgForSend, chipPort);
    }

}
