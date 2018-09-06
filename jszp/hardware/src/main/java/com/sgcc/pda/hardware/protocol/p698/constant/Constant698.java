package com.sgcc.pda.hardware.protocol.p698.constant;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.dataType698.BIT_STRING_16;
import com.sgcc.pda.hardware.protocol.p698.dataType698.BIT_STRING_8;
import com.sgcc.pda.hardware.protocol.p698.dataType698.BOOL;
import com.sgcc.pda.hardware.protocol.p698.dataType698.COMDCB;
import com.sgcc.pda.hardware.protocol.p698.dataType698.CSD;
import com.sgcc.pda.hardware.protocol.p698.dataType698.DATE;
import com.sgcc.pda.hardware.protocol.p698.dataType698.DATE_TIME;
import com.sgcc.pda.hardware.protocol.p698.dataType698.DOUBLE_LONG_0;
import com.sgcc.pda.hardware.protocol.p698.dataType698.DOUBLE_LONG_1;
import com.sgcc.pda.hardware.protocol.p698.dataType698.DOUBLE_LONG_2;
import com.sgcc.pda.hardware.protocol.p698.dataType698.DOUBLE_LONG_3;
import com.sgcc.pda.hardware.protocol.p698.dataType698.DOUBLE_LONG_4;
import com.sgcc.pda.hardware.protocol.p698.dataType698.DOUBLE_LONG_UNSIGNED_0;
import com.sgcc.pda.hardware.protocol.p698.dataType698.DOUBLE_LONG_UNSIGNED_2;
import com.sgcc.pda.hardware.protocol.p698.dataType698.DOUBLE_LONG_UNSIGNED_4;
import com.sgcc.pda.hardware.protocol.p698.dataType698.ENUM;
import com.sgcc.pda.hardware.protocol.p698.dataType698.INTEGER_0;
import com.sgcc.pda.hardware.protocol.p698.dataType698.LONG64_0;
import com.sgcc.pda.hardware.protocol.p698.dataType698.LONG64_1;
import com.sgcc.pda.hardware.protocol.p698.dataType698.LONG64_4;
import com.sgcc.pda.hardware.protocol.p698.dataType698.LONG64_UNSIGNED_4;
import com.sgcc.pda.hardware.protocol.p698.dataType698.LONG_1;
import com.sgcc.pda.hardware.protocol.p698.dataType698.LONG_2;
import com.sgcc.pda.hardware.protocol.p698.dataType698.LONG_3;
import com.sgcc.pda.hardware.protocol.p698.dataType698.LONG_UNSIGNED_0;
import com.sgcc.pda.hardware.protocol.p698.dataType698.LONG_UNSIGNED_1;
import com.sgcc.pda.hardware.protocol.p698.dataType698.LONG_UNSIGNED_2;
import com.sgcc.pda.hardware.protocol.p698.dataType698.NULL;
import com.sgcc.pda.hardware.protocol.p698.dataType698.OAD;
import com.sgcc.pda.hardware.protocol.p698.dataType698.OCTET_STRING;
import com.sgcc.pda.hardware.protocol.p698.dataType698.OCTET_STRING_2;
import com.sgcc.pda.hardware.protocol.p698.dataType698.OCTET_STRING_3;
import com.sgcc.pda.hardware.protocol.p698.dataType698.OCTET_STRING_4;
import com.sgcc.pda.hardware.protocol.p698.dataType698.OI;
import com.sgcc.pda.hardware.protocol.p698.dataType698.ROAD;
import com.sgcc.pda.hardware.protocol.p698.dataType698.SID;
import com.sgcc.pda.hardware.protocol.p698.dataType698.STRUANDARRAY;
import com.sgcc.pda.hardware.protocol.p698.dataType698.TI;
import com.sgcc.pda.hardware.protocol.p698.dataType698.TIME_0;
import com.sgcc.pda.hardware.protocol.p698.dataType698.TSA;
import com.sgcc.pda.hardware.protocol.p698.dataType698.UNSIGNED_0;
import com.sgcc.pda.hardware.protocol.p698.dataType698.VISIBLE_STRING_2;
import com.sgcc.pda.hardware.protocol.p698.dataType698.VISIBLE_STRING_200;
import com.sgcc.pda.hardware.protocol.p698.dataType698.VISIBLE_STRING_32;
import com.sgcc.pda.hardware.protocol.p698.dataType698.VISIBLE_STRING_4;
import com.sgcc.pda.hardware.protocol.p698.dataType698.VISIBLE_STRING_6;
import com.sgcc.pda.hardware.protocol.p698.phares.Base698;
import com.sgcc.pda.hardware.protocol.p698.phares.DATE_TIME_S;
import com.sgcc.pda.hardware.protocol.p698.dataType698.MAC;

import java.util.HashMap;
import java.util.Map;

//组装698报文所需数据常量
public class Constant698 {

	public static final int ContArea = 3; //控制域
	public static final int addArea = 4; //地址域
	public static final byte LINK_REQUEST = 0x01; //预连接
	public static final byte CONNECT_REQUEST = 0x02;//建立应用连接请求
	public static final byte RELEASE_REQUEST = 0x03; //断开应用连接请求
	public static final byte GET_OBJECT_DATA = 0x05; //读取请求
	public static final byte SET_OBJECT_DATA = 0x06; //设置请求
	public static final byte ACTION_OBJECT_DATA = 0X07; //操作请求
	public static final byte REPORT_OBJECT_DATA = 0x08; //上报响应
	public static final byte PROXY_OBJECT_DATA = 0x09; //代理请求
	public static final byte SECURITY_OBJECT_DATA = 0x10; //安全传输请求
	public static final byte LINK_RESPONSE = (byte) 0x81; //预连接响应
	public static final byte CONNECT_RESPONSE = (byte) 0x82; //建立应用连接响应
	public static final byte RELEASE_RESPONSE = (byte) 0x83; //断开应用连接响应
	public static final byte RELEASE_NOTIFICATION = (byte) 0x84; //断开应用连接通知
	public static final byte GET_OBJECT_RESPONSE_DATA = (byte) 0x85; //响应读取请求
	public static final byte SET_OBJECT_RESPONSE_DATA = (byte) 0x86; //响应设置请求
	public static final byte ACTION_OBJECT_RESPONSE_DATA = (byte) 0x87; //响应操作请求
	public static final byte REPORT_OBJECT_RESPONSE_DATA = (byte) 0x88; //响应上报请求
	public static final byte PROXY_OBJECT_RESPONSE_DATA = (byte) 0x89; //响应代理请求
	public static final byte SECURITY_RESPONSE_DATA = (byte) 0x90; //响应安全传输请求
	
	public static final byte CLIENT_ADDR = (byte)0x00;	//客户机地址
	public static final byte CLIENT_ADDR1 = (byte)0x01;	//客户机地址
	public static final String ADDRESS698_DOMAIN_AA = "AAAAAAAAAAAA";  //电能表通讯地址
	public static final String PROTOCOL_CONFORMANCE="FFFFFFFFFFFFFFFF"; //协议一致性协商内容
	public static final String FUNCTION_CONFORMANCE="FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"; //协议一致性协商内容
	
	//回话协商时用到的数据
	public static String M1 = "";        		//调用会话协商时加密机返回的密文1
	public static String SIGE1 = "";	 		//调用会话协商时加密机返回的数字签名1
	public static String RAND_HOST = ""; 		//调用会话协商时加密机返回的随机数R1
	public static String SESSION_KEY ="";		//会话协商验证是返回的会话协商key
	public static String I_KEY_STATE ="";       //回话密钥版本  0 或 1
	//安全传输过程需要用到的数据
	public static String SID_IDENT = "";		//调用加密机数据加密方法返回的 SID标示
	public static String ATTACH_DATA = "";		//调用加密机数据加密方法返回的 附加数据
	public static String EN_DATA = "";			//调用加密机数据加密方法返回的 加密后的数据
	public static String MAC = "";				//调用加密机数据加密方法返回的 MAC
	public static String RN = "";				//调用加密机数据加密方法返回的 RN

	/*保存数据类型DATA 数据          eg  基本数据类型  ----标号   arrau---1*/
	public static final Map<String, String> DATA_TYPE_MAP = new HashMap<String, String>();

	/*保存数据类型  和对应组装、解析类关系*/
	public static final Map<String, IPharesItem698> DATA_TYPE_CLASS_MAP = new HashMap<String, IPharesItem698>();

	/*保存数据访问结果        DAR 数据*/
	public static final Map<String, String> DATA_ACCESS_RESULT = new HashMap<String, String>();

	/*根据数据类型获取数据长度*/
	public static final Map<String, String> DATA_TYPE_GET_LEGTH = new HashMap<String, String>();
	/*根据数值获取时间间隔的单位*/
	public static final Map<Integer, String> TI_UNITS = new HashMap<Integer, String>();
	/*根据数值获取对应的数值单位*/
	public static final Map<Integer, String> SCALER_UNIT_MAP = new HashMap<Integer, String>();
	//COMDCB数据类型号中波特率（枚举型）的对应关系
	public static final Map<Integer, String> COMDCB_BAUDRATE_MAP = new HashMap<Integer, String>();
	static {
		//   数据类型  -----编号
		DATA_TYPE_MAP.put("NULL", "0");
		DATA_TYPE_MAP.put("array", "1");
		DATA_TYPE_MAP.put("structure", "2");
		DATA_TYPE_MAP.put("bool", "3");
		DATA_TYPE_MAP.put("bit-string", "4");
		DATA_TYPE_MAP.put("double-long", "5");
		DATA_TYPE_MAP.put("double-long-unsigned", "6");
		DATA_TYPE_MAP.put("octet-string", "9");
		DATA_TYPE_MAP.put("visible-string", "10");
		DATA_TYPE_MAP.put("UTF8-string", "12");
		DATA_TYPE_MAP.put("integer", "15");
		DATA_TYPE_MAP.put("long", "16");
		DATA_TYPE_MAP.put("unsigned", "17");
		DATA_TYPE_MAP.put("long-unsigned", "18");
		DATA_TYPE_MAP.put("long64", "20");
		DATA_TYPE_MAP.put("long64-unsigned", "21");
		DATA_TYPE_MAP.put("enum", "22");
		DATA_TYPE_MAP.put("float32", "23");
		DATA_TYPE_MAP.put("float64", "24");
		DATA_TYPE_MAP.put("date_time", "25");
		DATA_TYPE_MAP.put("date", "26");
		DATA_TYPE_MAP.put("time", "27");
		DATA_TYPE_MAP.put("date_time_s", "28");
		DATA_TYPE_MAP.put("OI", "80");
		DATA_TYPE_MAP.put("OAD", "81");
		DATA_TYPE_MAP.put("ROAD", "82");
		DATA_TYPE_MAP.put("OMD", "83");
		DATA_TYPE_MAP.put("TI", "84");
		DATA_TYPE_MAP.put("TSA", "85");
		DATA_TYPE_MAP.put("MAC", "86");
		DATA_TYPE_MAP.put("RN", "87");
		DATA_TYPE_MAP.put("Region", "88");
		DATA_TYPE_MAP.put("Scaler_Unit", "89");
		DATA_TYPE_MAP.put("RSD", "90");
		DATA_TYPE_MAP.put("CSD", "91");
		DATA_TYPE_MAP.put("MS", "92");
		DATA_TYPE_MAP.put("SID", "93");
		DATA_TYPE_MAP.put("SID_MAC", "94");
		DATA_TYPE_MAP.put("COMDCB", "95");
		DATA_TYPE_MAP.put("RCSD", "96");
		
		//-------  数据类型   和  对应类
		DATA_TYPE_CLASS_MAP.put("double_long_2", new DOUBLE_LONG_2());
		DATA_TYPE_CLASS_MAP.put("long64_4", new LONG64_4());
		DATA_TYPE_CLASS_MAP.put("double_long_unsigned_2", new DOUBLE_LONG_UNSIGNED_2());
		DATA_TYPE_CLASS_MAP.put("long64_unsigned_4", new LONG64_UNSIGNED_4());
		DATA_TYPE_CLASS_MAP.put("long_unsigned_2", new LONG_UNSIGNED_2());
		DATA_TYPE_CLASS_MAP.put("date_time_s", new DATE_TIME_S());
		DATA_TYPE_CLASS_MAP.put("long_unsigned_1", new LONG_UNSIGNED_1());
		DATA_TYPE_CLASS_MAP.put("long_3", new LONG_3());
		DATA_TYPE_CLASS_MAP.put("double_long_unsigned", new DOUBLE_LONG_UNSIGNED_0());
		DATA_TYPE_CLASS_MAP.put("double_long_unsigned_4", new DOUBLE_LONG_UNSIGNED_4());
		DATA_TYPE_CLASS_MAP.put("date_time", new DATE_TIME());
		DATA_TYPE_CLASS_MAP.put("long_unsigned", new LONG_UNSIGNED_0());
		DATA_TYPE_CLASS_MAP.put("double-long-unsigned", new DOUBLE_LONG_UNSIGNED_0());
		DATA_TYPE_CLASS_MAP.put("double_long_unsigned", new DOUBLE_LONG_UNSIGNED_0());
		DATA_TYPE_CLASS_MAP.put("long-unsigned", new LONG_UNSIGNED_0());
		DATA_TYPE_CLASS_MAP.put("unsigned", new UNSIGNED_0());
		DATA_TYPE_CLASS_MAP.put("OAD", new OAD());
		DATA_TYPE_CLASS_MAP.put("OI", new OI());
		DATA_TYPE_CLASS_MAP.put("NULL", new NULL());
		DATA_TYPE_CLASS_MAP.put("TSA", new TSA());
		DATA_TYPE_CLASS_MAP.put("TI", new TI());
		DATA_TYPE_CLASS_MAP.put("enum", new ENUM());
		DATA_TYPE_CLASS_MAP.put("integer", new INTEGER_0());
		DATA_TYPE_CLASS_MAP.put("structure", new STRUANDARRAY());
		DATA_TYPE_CLASS_MAP.put("array", new STRUANDARRAY());
		DATA_TYPE_CLASS_MAP.put("double-long", new DOUBLE_LONG_0());
		DATA_TYPE_CLASS_MAP.put("bool", new BOOL());
		DATA_TYPE_CLASS_MAP.put("mac", new MAC());
		DATA_TYPE_CLASS_MAP.put("sid", new SID());
		DATA_TYPE_CLASS_MAP.put("MAC", new MAC());
		DATA_TYPE_CLASS_MAP.put("SID", new SID());
		DATA_TYPE_CLASS_MAP.put("octet-string", new OCTET_STRING());
		DATA_TYPE_CLASS_MAP.put("COMDCB", new COMDCB());
		DATA_TYPE_CLASS_MAP.put("CSD", new CSD());
		DATA_TYPE_CLASS_MAP.put("long", new LONG_2());
		DATA_TYPE_CLASS_MAP.put("unsigned", new UNSIGNED_0());
		DATA_TYPE_CLASS_MAP.put("date", new DATE());
		DATA_TYPE_CLASS_MAP.put("COMDCB", new COMDCB());
		DATA_TYPE_CLASS_MAP.put("ROAD", new ROAD());
		DATA_TYPE_CLASS_MAP.put("long64", new LONG64_0());
		DATA_TYPE_CLASS_MAP.put("bit-string_16", new BIT_STRING_16());
		DATA_TYPE_CLASS_MAP.put("bit-string_8", new BIT_STRING_8());
		DATA_TYPE_CLASS_MAP.put("double-long_1", new DOUBLE_LONG_1());
		DATA_TYPE_CLASS_MAP.put("double-long_3", new DOUBLE_LONG_3());
		DATA_TYPE_CLASS_MAP.put("double-long_4", new DOUBLE_LONG_4());
		DATA_TYPE_CLASS_MAP.put("long_1", new LONG_1());
		DATA_TYPE_CLASS_MAP.put("long64_1", new LONG64_1());
		DATA_TYPE_CLASS_MAP.put("octet-string_2", new OCTET_STRING_2());
		DATA_TYPE_CLASS_MAP.put("octet-string_3", new OCTET_STRING_3());
		DATA_TYPE_CLASS_MAP.put("octet-string_4", new OCTET_STRING_4());
		DATA_TYPE_CLASS_MAP.put("time", new TIME_0());
		DATA_TYPE_CLASS_MAP.put("visible-string_2", new VISIBLE_STRING_2());
		DATA_TYPE_CLASS_MAP.put("visible-string_200", new VISIBLE_STRING_200());
		DATA_TYPE_CLASS_MAP.put("visible-string_32", new VISIBLE_STRING_32());
		DATA_TYPE_CLASS_MAP.put("visible-string_4", new VISIBLE_STRING_4());
		DATA_TYPE_CLASS_MAP.put("visible-string_6", new VISIBLE_STRING_6());
		DATA_TYPE_CLASS_MAP.put("long64_unsigned", new LONG64_UNSIGNED_4());
		DATA_TYPE_CLASS_MAP.put("long64-unsigned", new LONG64_UNSIGNED_4());
		
		//数据访问结果  DAR   上行报文中错误信息
		DATA_ACCESS_RESULT.put("0", "成功");
		DATA_ACCESS_RESULT.put("1", "硬件失效");
		DATA_ACCESS_RESULT.put("2", "暂时失效");
		DATA_ACCESS_RESULT.put("3", "拒绝读写");
		DATA_ACCESS_RESULT.put("4", "对象未定义");
		DATA_ACCESS_RESULT.put("5", "对象接口类不符合");
		DATA_ACCESS_RESULT.put("6", "对象不存在");
		DATA_ACCESS_RESULT.put("7", "类型不匹配");
		DATA_ACCESS_RESULT.put("8", "越界");
		DATA_ACCESS_RESULT.put("9", "数据块不可用");
		DATA_ACCESS_RESULT.put("10", "分帧传输已取消");
		DATA_ACCESS_RESULT.put("11", "不处于分帧传输状态");
		DATA_ACCESS_RESULT.put("12", "块写取消");
		DATA_ACCESS_RESULT.put("13", "不存在块写状态");
		DATA_ACCESS_RESULT.put("14", "数据块序号无效");
		DATA_ACCESS_RESULT.put("15", "密码错/未授权");
		DATA_ACCESS_RESULT.put("16", "通信速率不能更改");
		DATA_ACCESS_RESULT.put("17", "年时区数超");
		DATA_ACCESS_RESULT.put("18", "日时段数超");
		DATA_ACCESS_RESULT.put("19", "费率数超");
		DATA_ACCESS_RESULT.put("20", "安全认证不匹配");
		DATA_ACCESS_RESULT.put("21", "重复充值");
		DATA_ACCESS_RESULT.put("22", "ESAM验证失败");
		DATA_ACCESS_RESULT.put("23", "安全认证失败");
		DATA_ACCESS_RESULT.put("24", "客户编号不匹配");
		DATA_ACCESS_RESULT.put("25", "充值次数错误");
		DATA_ACCESS_RESULT.put("26", "购电超囤积");
		DATA_ACCESS_RESULT.put("27", "地址异常");
		DATA_ACCESS_RESULT.put("28", "对称解密错误");
		DATA_ACCESS_RESULT.put("29", "非对称解密错误");
		DATA_ACCESS_RESULT.put("30", "签名错误");
		DATA_ACCESS_RESULT.put("31", "电能表挂起");
		DATA_ACCESS_RESULT.put("32", "时间标签无效");
		DATA_ACCESS_RESULT.put("33", "请求超时");
		DATA_ACCESS_RESULT.put("34", "ESAM的P1P2不正确");
		DATA_ACCESS_RESULT.put("35", "ESAM的LC错误");
		DATA_ACCESS_RESULT.put("255", "其它");

		//根据数据类型获取数据长度  只有固定长度的数据类型   
		DATA_TYPE_GET_LEGTH.put("date_time_s", "7");
		DATA_TYPE_GET_LEGTH.put("time", "3");
		DATA_TYPE_GET_LEGTH.put("date", "5");
		DATA_TYPE_GET_LEGTH.put("date_time", "10");
		DATA_TYPE_GET_LEGTH.put("float64", "8");
		DATA_TYPE_GET_LEGTH.put("float32", "4");
		DATA_TYPE_GET_LEGTH.put("long64-unsigned", "8");
		DATA_TYPE_GET_LEGTH.put("long64", "8");
		DATA_TYPE_GET_LEGTH.put("long-unsigned", "2");
		DATA_TYPE_GET_LEGTH.put("unsigned", "1");
		DATA_TYPE_GET_LEGTH.put("long", "2");
		DATA_TYPE_GET_LEGTH.put("integer", "1");
		DATA_TYPE_GET_LEGTH.put("double-long-unsigned", "4");
		DATA_TYPE_GET_LEGTH.put("double-long", "4");

		/*根据数值获取时间间隔的单位*/
		TI_UNITS.put(0, "秒");
		TI_UNITS.put(1, "分");
		TI_UNITS.put(2, "时");
		TI_UNITS.put(3, "日");
		TI_UNITS.put(4, "月");
		TI_UNITS.put(5, "年");

		/*根据数值获取所对应的单位*/
		/*57…253为保留*/
		SCALER_UNIT_MAP.put(1, "a");
		SCALER_UNIT_MAP.put(2, "mo");
		SCALER_UNIT_MAP.put(3, "wk");
		SCALER_UNIT_MAP.put(4, "d");
		SCALER_UNIT_MAP.put(5, "h");
		SCALER_UNIT_MAP.put(6, "min");
		SCALER_UNIT_MAP.put(7, "s");
		SCALER_UNIT_MAP.put(8, "°");
		SCALER_UNIT_MAP.put(9, "℃");
		SCALER_UNIT_MAP.put(10, "货币");

		SCALER_UNIT_MAP.put(11, "m");
		SCALER_UNIT_MAP.put(12, "m/s");
		SCALER_UNIT_MAP.put(13, "m3");
		SCALER_UNIT_MAP.put(14, "m3");
		SCALER_UNIT_MAP.put(15, "m3/h");
		SCALER_UNIT_MAP.put(16, "m3/h");
		SCALER_UNIT_MAP.put(17, "m3/d");
		SCALER_UNIT_MAP.put(18, "m3/d");
		SCALER_UNIT_MAP.put(19, "l");
		SCALER_UNIT_MAP.put(20, "kg");

		SCALER_UNIT_MAP.put(21, "N");
		SCALER_UNIT_MAP.put(22, "Nm");
		SCALER_UNIT_MAP.put(23, "P");
		SCALER_UNIT_MAP.put(24, "bar");
		SCALER_UNIT_MAP.put(25, "J");
		SCALER_UNIT_MAP.put(26, "J/h");
		SCALER_UNIT_MAP.put(27, "W");
		SCALER_UNIT_MAP.put(28, "kW");
		SCALER_UNIT_MAP.put(29, "VA");
		SCALER_UNIT_MAP.put(30, "kVA");

		SCALER_UNIT_MAP.put(31, "var");
		SCALER_UNIT_MAP.put(32, "kvar");
		SCALER_UNIT_MAP.put(33, "kWh");
		SCALER_UNIT_MAP.put(34, "kVAh");
		SCALER_UNIT_MAP.put(35, "kvarh");
		SCALER_UNIT_MAP.put(36, "A");
		SCALER_UNIT_MAP.put(37, "C");
		SCALER_UNIT_MAP.put(38, "V");
		SCALER_UNIT_MAP.put(39, "V/m");
		SCALER_UNIT_MAP.put(40, "F");

		SCALER_UNIT_MAP.put(41, "Ω");
		SCALER_UNIT_MAP.put(42, "Ωm2/m");
		SCALER_UNIT_MAP.put(43, "Wb");
		SCALER_UNIT_MAP.put(44, "T");
		SCALER_UNIT_MAP.put(45, "A/m");
		SCALER_UNIT_MAP.put(46, "H");
		SCALER_UNIT_MAP.put(47, "Hz");
		SCALER_UNIT_MAP.put(48, "1/(Wh)");
		SCALER_UNIT_MAP.put(49, "1/(varh)");
		SCALER_UNIT_MAP.put(50, "1/(VAh)");

		SCALER_UNIT_MAP.put(51, "%");
		SCALER_UNIT_MAP.put(52, "byte");
		SCALER_UNIT_MAP.put(53, "dBm");
		SCALER_UNIT_MAP.put(54, "元/kWh");
		SCALER_UNIT_MAP.put(55, "Ah");
		SCALER_UNIT_MAP.put(56, "ms");

		SCALER_UNIT_MAP.put(254, "其他单位");
		SCALER_UNIT_MAP.put(255, "无单位、缺单位、计数");
		//COMDCB数据类型号中波特率（枚举型）的对应关系
		COMDCB_BAUDRATE_MAP.put(0, "300bps");
		COMDCB_BAUDRATE_MAP.put(1, "600bps");
		COMDCB_BAUDRATE_MAP.put(2, "1200bps");
		COMDCB_BAUDRATE_MAP.put(3, "2400bps");
		COMDCB_BAUDRATE_MAP.put(4, "4800bps");
		COMDCB_BAUDRATE_MAP.put(5, "7200bps");
		COMDCB_BAUDRATE_MAP.put(6, "9600bps");
		COMDCB_BAUDRATE_MAP.put(7, "19200bps");
		COMDCB_BAUDRATE_MAP.put(8, "38400bps");
		COMDCB_BAUDRATE_MAP.put(9, "57600bps");
		COMDCB_BAUDRATE_MAP.put(10, "115200bps");
		COMDCB_BAUDRATE_MAP.put(255, "自适应");
	}

	//根据数据类型   获取对应类
	public static IPharesItem698 getiPharesItem698(String format) {
		IPharesItem698 iPharesItem698 = DATA_TYPE_CLASS_MAP.get(format);
		if (iPharesItem698 == null) {
			iPharesItem698 = new Base698();
		}
		return iPharesItem698;
	}
}
