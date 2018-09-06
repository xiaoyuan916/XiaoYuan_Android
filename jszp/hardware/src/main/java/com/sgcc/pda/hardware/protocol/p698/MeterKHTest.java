package com.sgcc.pda.hardware.protocol.p698;



public class MeterKHTest {
	
	
    public static void main(String[] args) {
    	
	//	HandheldInterface.makeESAMInfoFrame("111111111111");

		/***********************************************会话协商****************************************************/
		
		//获取ESAM信息 调用加密机获取随机数、密文m1、签名1
//		String esamStr = "FE FE FE FE 68 5E 00 C3 05 1111 11 11 11 11 00 02 C6 85 02 00 04 4002 02 00 01 09 06 00 00 00 00 00 01 F1 0002 00 01 09 08 51 01 00 00 00 01 66 4CF1 00 04 00 01 09 10 7F FF FF FF F0 7F 80FF 80 00 00 00 00 00 00 00 F1 00 07 00 0102 03 06 00 00 00 0E 06 00 00 00 00 0600 00 00 00 00 00 7E 38 16".replace(" ", "");
//		List<String> eList = ic.getESAMInfo(esamStr);
//		System.out.println("电能表表号：" + eList.get(0));
//		System.out.println("ESAM序列号：" + eList.get(1));
//		System.out.println("对称密钥版本：" + eList.get(2));
//		System.out.println("ESAM 计数器：" + eList.get(3));
		
		
		//与电表进行会话协商验证会话协商
//		List<String> listStr = HandheldInterface.meterFormalInitSession("000000000001", "510100000001664C", "15", "7FFFFFFFF07F80FF8000000000000000");
//		System.out.println("随机数R1: " + listStr.get(0));
//		System.out.println("密文M1: " + listStr.get(1));
//		System.out.println("签名S1： " + listStr.get(2));
		
//		HandheldInterface.makeConnectFrame("111111111111", "27791E88153D5D7772603DD643B4C7AB636C36390A6CE72B9302D91FD4F48D57", "AB244433");
	
		
		
		// 解析获取m2和s2
//		String connStr = "FE FE FE FE 68 90 00C3 05 11 11 11 11 11 11 00 C1 D0 82 00 0000 00 00 00 00 00 00 00 00 00 00 00 00 0000 00 00 00 00 00 00 00 00 00 00 00 0000 00 00 00 00 10 CE 73 00 40 03 00 00 00B7 87 2F 50 00 00 00 00 00 00 00 00 0000 00 00 02 00 08 00 01 08 00 00 01 51 8000 01 30 10 83 34 45 11 32 DB 30 AF 7F99 ED 22 11 13 68 22 6A E5 06 BC 19 4F 8682 CE 45 21 2B FF DC C1 8A 45 C5 DC 27 80FA 98 6D 62 21 F5 20 A9 BC 3F 04 56 4F14 57 00 00 F6 12 16".replace(" ", "");
//		ic.getConnectInfo(connStr);
		 
		 
//		 String sKey = HandheldInterface.meterFormalVerifySession("000000000001", "510100000001664C", "9F0523C812F9A4729F0523C812F9A472", "108334451132DB30AF7F99ED22111368226AE506BC194F8682CE45212BFFDCC18A45C5DC2780FA986D6221F520A9BC3F", "564F1457", "7FFFFFFFF07F80FF8000000000000000");
//		 System.out.println("验证结果数据： " + sKey);
	}

}
