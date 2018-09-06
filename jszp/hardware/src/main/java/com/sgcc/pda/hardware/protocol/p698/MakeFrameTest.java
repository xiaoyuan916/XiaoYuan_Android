package com.sgcc.pda.hardware.protocol.p698;


public class MakeFrameTest {
	
	
	public static void main(String[] args) {
		
		
//		  InitialOuterChipItfInfo ic = new InitialOuterChipItfInfo();
//		获取电表通讯地址
//		ic.makeMeterNumber();
//		String meterStr = "FE FE FE FE 68 21 00C3 05 15 03 00 12 02 00 10 E4 CD 85 01 0140 01 02 00 01 09 06 00 02 12 00 03 15 0000 F2 4D 16".replace(" ", "");
//		System.out.println("电能表地址："+ic.getMeterNumber(meterStr));
//		HandheldInterface.makeESAMInfoFrame("111111111111");

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

		/***********************************************控制命令****************************************************/
		//生成明文跳闸报文
//		HandheldInterface.makeCtlTripOpt("000000000003");
		
		//生成合闸明文
//		HandheldInterface.makeCtlColseOpt("111111111111");
	

		//数据加密
//		String TsessionKey = "9C485FD62DC17178DA8686888058547ACB54F2A4440BA33B54108FF27368FDD11129FF105D7173A5EF6A1A22C0DD79C17EB4C84B94281EBCCD1074A21005A51896A7337F3B3108680CE2AF2EE3BF87E930E050289E542B98BE2225F4A9E809ADFC3838759DC60E23A8BC6ACB08A5B6CE247870645399334B47800378AEB356E890172BE274B5A96EE4EFBD50E75D2978798778C253CA29D2DF4818F27B00D1351B5429013F08F21631BADD5A40778702";
//		String hsessionKey = "45B03737529AFCCAF3C9A5B24ECB7FB8449A65CA425A11D2A259CD79B0AAD804D9ADC902E8CA12AE574C2B9250E9916A9C0606193A35567003324FB2D2829FF028B45E323B335C6CB2ED8D94E520A787C171A7083AC820074EBC5D11962E08DE43908BC59F2A361C21BEB175BE411ABE65EB90A56BDF70D04EF08083544C4944E8DC7314DC8752B68BF7DB0309D1383D2BFA0EFF03B17605E8843CCDE8BCB7A3139A775FE0D5AA61FFE82AE2F7A5D325";
//		List<String> eList = HandheldInterface.meterGetSessionData(3, "", TsessionKey, 8, "070100800082000101020251F2050200160100");
//		System.out.println("SID标识： " + eList.get(0));
//		System.out.println("附加数据： " + eList.get(1));
//		System.out.println("加密后数据： " + eList.get(2));
//		System.out.println("MAC： " + eList.get(3));
//		
//		//控制安全传输组装密文数据
//		Constant698.SID_IDENT = "811C8310";
//		Constant698.ATTACH_DATA = "0024";
//		Constant698.MAC ="AD489240";
//		Constant698.RN = "ED56CC50D5E94C530827B93F446E4157C1280D72E293E964D637E8A4C877A40C";
//		HandheldInterface.makeSafeTrans("111111111111", "1", "0", "080298D861E988FA5A248CF8245907D63278AAE4DF2E89C3873682B21BA147F5");
		
//		682200430511111111111100E0BE070100800082000101020251F2050200160000C87E16
		
//		String enretStr = "FE FE FE FE 68 29 00 C3 05 03 0000 00 00 00 00 D9 CF 90 01 10 FB C0 558F 2C 96 A5 A5 5C F7 6F 95 59 65 B2 C3 0100 04 6C D6 CB 79 2D 3B 16".replace(" ", "");
//		String hStr = "FE FE FE FE 68 29 00 C305 03 00 00 00 00 00 00 D9 CF 90 01 10 F132 1D E4 8C 6F 18 7F 55 BA C2 1B 9F 8A 29DA 01 00 04 9F 82 27 44 4A C316".replace(" ", "");
//		byte[] bytes = CommUtils.hex2Binary(enretStr);
//		Parser698 parser698 = new Parser698();
//		System.out.println("密文传输结果："+parser698.parser698(bytes).getDataItem698().get(0).getUpValue());
//		Security  security = parser698.parser698(bytes).getSecurityObj();
//		System.out.println("MAC : "+ security.getMac());
//		System.out.println("密文数据："+security.getTransferData());
		
		//加密数据解密
//		String mData = HandheldInterface.meterVerifyData(3,"", TsessionKey, "FBC0558F2C96A5A55CF76F955965B2C3", "6CD6CB79");
//		System.out.println(mData);
		
		/***********************************************电表校时****************************************************/
		
//		HandheldInterface.makeSetTimeFrame("000000000003", "20180808080800");
		//设置明文
//		681F00430503000000000000B66D060100400002001C07E2080808080000A3F816
		
//		String setTime = "99BBD4BCE63661AC50A210F9A0810C4682A39C84D383AE2BD5D2D27915032CC15203602458B2D84246D58154AB4A6E45E2224DBFFE5E5FD86A2B157CEEDAD28BFD4F388EBAD4E5C0B2CBC95C6651DD425035BDD9BB382A12C796A7C04DE7123F44E49BAEC5661B02254EFE309C28C5E0BEBA99DFB965905E4DAFDE31B1C9F079972694C0B0AED13CAB012FCB1AB8478CBD459C70E1D56FCA109DB51609C655C4FCE00D420BCAB9870B411ADE42FB98C5";
//		List<String> eList = HandheldInterface.meterGetSessionData(3, "", setTime, 5, "060100400002001C07E2080808080000");
//		System.out.println("SID标识： " + eList.get(0));
//		System.out.println("附加数据： " + eList.get(1));
//		System.out.println("加密后数据： " + eList.get(2));
//		System.out.println("MAC： " + eList.get(3));
		
		
		//校时安全传输组装密文数据
//		Constant698.SID_IDENT = "811C5310";
//		Constant698.ATTACH_DATA = "0024";
//		Constant698.MAC ="6890AFDA";
//		Constant698.RN = "730D8CD793B7B337730D8CD793B7B337";
//		HandheldInterface.makeSafeTrans("000000000003", "1", "0", "84CB8F3A7CB6CEE55BF3BA07D6969C52BC8095D7A1BCC529E6BF9609CA5D54AA");
		
//		String str = "FE FE FE FE 68 29 00 C3 05 03 00 00 00 0000 00 D9 CF 90 01 10 C4 D5 AD 21 42 EE 30C0 E3 2E 0B A0 AA 90 E6 F5 01 00 04 FB A9FE B1 1E 74 16".replace(" ", "");
//		byte[] bytes = CommUtils.hex2Binary(str);
//		Parser698 parser698 = new Parser698();
//		System.out.println("密文传输结果："+parser698.parser698(bytes).getDataItem698().get(0).getUpValue());
//		Security  security = parser698.parser698(bytes).getSecurityObj();
//		System.out.println("MAC : "+ security.getMac());
//		System.out.println("密文数据："+security.getTransferData());
		
	
		//加密数据解密
//		String mData = HandheldInterface.meterVerifyData(3,"", setTime, "C4D5AD2142EE30C0E32E0BA0AA90E6F5", "FBA9FEB1");
//		System.out.println(mData);
		
		
		
//		String mailingStr = "FE FE FE FE 68 19 00 C305 03 00 00 00 00 00 00 16 5C86 01 00 40 00 02 00 0F 00 00 4E FC 16".replace(" ", "");
//		byte[] bytes = CommUtils.hex2Binary(mailingStr);
//		Parser698 parser698 = new Parser698();
//		Frame698 frame698 = parser698.parser698(bytes);
//		String str = frame698.getDataItem698().get(0).getUpValue();
//		System.out.println("解析结果 : "+ str);
		
		
		/***********************************************开户充值****************************************************/

		//读电表购电累计金额
//		DataItem698 di = new DataItem698();
//			di.setObjIdent("4001");
//			di.setMemberCode("2");
//		HandheldInterface.makeReadProperty("AAAAAAAAAAAA", di);
		
		
//		String mailingStr = "FE FE FE FE 68 1A 00 C305 11 11 11 11 11 11 00 52 55 87 01 00 F100 06 00 05 00 00 00 B8 65 16".replace(" ", "");
//		byte[] bytes = CommUtils.hex2Binary(mailingStr);
//		Parser698 parser698 = new Parser698();
//		Frame698 frame698 = parser698.parser698(bytes);
//		String str = frame698.getDataItem698().get(0).getUpValue();
//		System.out.println("解析结果 : "+ str);
		
		
//		String kh = "9C485FD62DC17178DA8686888058547ACB54F2A4440BA33B54108FF27368FDD11129FF105D7173A5EF6A1A22C0DD79C17EB4C84B94281EBCCD1074A21005A51896A7337F3B3108680CE2AF2EE3BF87E930E050289E542B98BE2225F4A9E809ADFC3838759DC60E23A8BC6ACB08A5B6CE247870645399334B47800378AEB356E890172BE274B5A96EE4EFBD50E75D2978798778C253CA29D2DF4818F27B00D1351B5429013F08F21631BADD5A40778702";
//		String taskData = "0000006400000001000000000001";
//		List<String> list = HandheldInterface.meterGetPurseData(3, "", kh, 10, taskData);
//		System.out.println("开户SID: "+list.get(0));
//		System.out.println("开户附加数据: "+list.get(1));
//		System.out.println("开户密文数据: "+list.get(2));
//		System.out.println("开户MAC: "+list.get(3));
		
		//开户明文
//		HandheldInterface.makeOpenAccountOrRecharge("111111111111", "0", "100", "1", "000000000001", "81420000002530", "C6056F9C", "000000000001");
		
	// 明文: 684700430511111111111100AB9A070100F100060002060F000600000064060000000109060000000000015E5D068142000009030025305604C6056F9C090600000000000100D9F416
	
//		List<String> eList = HandheldInterface.meterGetSessionData(3, "", kh, 3, "070100F100060002060F000600000064060000000109060000000000015E5D068142000009030025305604C6056F9C090600000000000100");
//		System.out.println("SID标识： " + eList.get(0));
//		System.out.println("附加数据： " + eList.get(1));
//		System.out.println("加密后数据： " + eList.get(2));
//		System.out.println("MAC： " + eList.get(3));
		
		// 密文数据 531E29800092AB5028247C1BFA484BA2FD36E3A1C81642D2ECD0777AB2958551
		
//		Constant698.SID_IDENT = "811C3310";
//		Constant698.ATTACH_DATA = "0044";
//		Constant698.MAC ="99C344BE";
//		Constant698.RN = "9F0523C812F9A4729F0523C812F9A472";
//		HandheldInterface.makeSafeTrans("1111111111111", "1", "0", "FF672CE459359C1126DF1DBBDC315E9E6BBF870A6E433FD6E0AB8091068F5AB0");
		
		
		/***********************************************密钥更新****************************************************/
		
		//电表密钥更新
//		String updateKey = "AC1A88ACEFAEB8432ECAF098329D2C5AF632643737E5E3774BDE3660964CEDB19D47612107CC91CFB523199CF1EC6BADBB83EC5754F5DB1640BA241EAAC39359D0261FBFC3D40A7AC6733479E60EE7FE30C259E0EEA6FFF4C3ADED13D2E6C9447E0C599C24A543C4EA351923A1BF26015604364549954C95FA3696A895871F13B1F350C397D1BA4098F76F74A5BA4E55763E331E9B584C69055EDFC961AA9A388C870D80608C15765F4E97EAE18B307A";
//		List<String> eList = HandheldInterface.meterGetTrmKeyData(1, "510100000001664C", updateKey, "000000000001");
//		System.out.println("SID标识： " + eList.get(0));
//		System.out.println("附加数据： " + eList.get(1));
//		System.out.println("加密后数据： " + eList.get(2));
//		System.out.println("MAC： " + eList.get(3));
		
//		String enData = "A404A3A7577872D050D1F6DCFA758CED8F0ED525F0A5351686D01B2D9400BE5513F451ACC26112757BAA66391F1DF00625C9B73658D04A83354106A08987CD950A76CFDFE9451EBA278A50330B875912205B4342CBE2714B950508BEE5375F43A7F4C3FB1123F494C4EA10A583BB5DF284A7FEA69632B3E4352A5A6FC576392C21F2831E575CC97788B9F777C4087C87A0CF201D652E459D2905C97E5C3474CEF265DDC0F9F75DFE766C5370E54691CAFC5E6EC25165D39BD80E437A9014A94C28D0A5B9CAB1AD6B488331E482BEB934A474C8A39CC33715C994101F55F1AD2182903C7ABEE72548A33B990B3E24FD9FF9373B80C50984F98421F9EB0717822957F8DC5D2A8DDF21A0C533AB9EE8627C25DB13D380C25E127473358C43C70321FFEEAEC9E736F1F523D5C7D8B5225AA50F9A440E69C17DE920ED0D30B66858A7E51778BB828CAE513A7B4FC40CA9C4AE8D9F3547C8926332893B5607CA7D97E48E695EC06809AEF4A0E1E618C5545DD0BA5D4FFA75853D3B123C5F3A29EA778D2FEFCC30D25B82F2AF6FE96D50265619F7B8AB72F750EEB858AA2E6CBEB04F55A8BE829F6F711760A5A591F43C7EC95B94112095FEAF2FAA5487390EBA428706DA1ECC5760615F0931D5BE67BB8D18D3F7AC69D44FFC20C496A336FB1137BC5C1F08AF54300B7BD91C1EF1740FA975F260E3F8276ECE2A25185117CCC34F2F97C51ED6E5D34B79517EA0D32E8CB9AAC02317A322DB4D6801716702472ED130A0194E7D59D6359E6566D5D5F7F0233E1FA0BC279FBCA752F9D72DC008213E970BF260D5CAB6FC44FC439ED143618B101C5A0D7771A16E55E33F81A3C3229CC5F3DB807B003DAC643DB3E850F4A0636C4D22100916E841BC4A92D2017AFC70BE4FE77DE6615FAB6F1F3E369E13B7D9D08FFC61F0B72023D1C0F4EB91AD78D62853E29936C601494E5EA92B00BCF626CB822BD93F7E49319DFFACDC89706B10DFCC57FDE895CFE9FD8E6298CF60D0B20B10963262DDFE0EDC2E9C65456FB7AFA076EB8B8F745B47714E55B21D5D4E96A6692B31C04549EB4287F28778F741919825D29142E77AD48D5662EEFAD412F6B317C5802410E924AC8E1F52F300EF09E242202322D512B460AB45C909D93B58004CD48E0D61AA33839DF78F4C9A32F1B83444EBFE147E6461825CBC243AE3817B204B544879511BB6F10C1A771ACCB8BC1B6720BDBA5E54162286B291768D82C7B61BD1B1D9F24773497E882054659D01F06C2954DAE8F7C7924F0FE88DF3D7E5261741133B2A2749FC63F99E92C64DDC631D6E28DBBA841E9FA191520CAF0849882CD8BB5B950070AF3287133B4BF771C3CD0C305EF75883A74F02ACC1E0C55AE7827373017DD2F85EF62D162DCC2D47B5977A733C175892C534B661CDC498FE060E50BAEF9A6591B5E2002FFFA9DDF7064037BF4D17C93487534721E702F64B4E7CB896E315ABB2453157329418E1C21601799CFC59CBADDA2231EF9A36D8743B6A1C35A28656751BA5C84ADD669863F4E956758B311BB3D077D02B28EF52AF5D557D07734A46E920A7779AFB2634C04CFA517B9E7300B39247FD60E858C4D7C238B0168C0A9E91F38E8A7E900DCF82F27015BEE9D196D8999406061AEFA0F8207CFF6254C2C4E9376936C7499906326E0421520C6E05D47CC0078A3BD0F3FC7DFF65FFACEB2318BFCCD24A249C21ECE7C947A59CFC1A4AB928C810DD4CA33325F52158E4FAE507846936A29672500B38DF74731555D4311734E790277D9ADCE6752B9130F9A7F555EB40F71D594B34E3B273DD99130328D66B18CBF528F721BAA12FFEDF7B3DC82E900364731A4754DEF67911B74308B8D9526EFCC373F46BE2513869FFA95BEC4E3FBE6E205D16ED06933E07BA5A6BA654C4B82EFC78A0B5FAFCD1EAD61477BCCB5A0FF87BC750DF1B2A3E08978465D972667C65EB5F43410FAB1982E148FD8D5CCF24193FDB080906570B6514CA8CB44D4062487886D331983B48565DC43E930EBF3DD241FC27C340C7CA4E285E4F4FDBA52301FD9155B89260E186522A13076264EA69C5CF69904831383D3E614B4BAAF635C1A40A5A771D4CBA3E6478B0761B6CFC388DEDB4DE026735F24DDA03920FE9E3F2A5235C85535AA86A43A8D6FC94E08E68DEB8ADDD48F6C3A302F59840C2C5F21C507F2A51AE782EBB72EDDA2813F3D9F531C4B5DDC1F776FB2965809A7C52077DE20AA08AF5A742DD246EEC2BBE82D042DE12E990BAE825D87D006A11C268FBD33267C6666699323B43B60D26E03FB7B8B22909E52DE70504C8580ADC7C4119E7003AE947F9B8B3A44B5EB13A9D7E3463042CB3D698";
//		HandheldInterface.makeUpdateEsamKey("111111111111", enData, "812E0000068d000000000000000034", "0914D7A1");
		
		
		
		
		
		/***********************************************读冻结数据****************************************************/
		
//		Make698Frame make698 = new Make698Frame();	
//		List<DataItem698>    list  = new  ArrayList<DataItem698>();
//		//操作对象方法
//    	DataItem698   di = new DataItem698();
//    	di.setObjIdent("5000");   //对象id
//    	di.setMemberCode("03");
//    	list.add(di);
    	
		
//		Frame698 frame698 = new Frame698();
// 		frame698.setObjclassifi((byte) 5); //对象分类    操作
// 		frame698.setMeterComId("000000000003"); //地址
// 		frame698.setMeterComIdType("00"); //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
// 		frame698.setLogiAdd("00"); //逻辑地址
// 		frame698.setDataItem698(list);
//    	make698.maker698(frame698);
		
		
    	
//    	String mailingStr = "FE FE FE FE 68 42 00 C3 05 03 00 00 0000 00 00 4F A2 85 01 00 50 00 03 00 01 0103 02 03 12 00 00 51 00 10 02 00 12 0003 02 03 12 00 00 51 00 20 02 00 12 00 0302 03 12 00 00 51 20 04 02 00 1200 03 00 00 F7 ED 16".replace(" ", "");
//		byte[] bytes = CommUtils.hex2Binary(mailingStr);
//		Parser698 parser698 = new Parser698();
//		Frame698 frame = parser698.parser698(bytes);
//		for(int i=0;i<frame.getDataItem698().size();i++){
//			System.out.println(frame.getDataItem698().get(i).getUpValue());
//		}
    	
    	
	  //控制命令
	  //生成明文跳闸报文
	/*	Make698Frame make698 = new Make698Frame();	
		List<DataItem698>    list  =new  ArrayList<DataItem698>();
		//操作对象方法
    	DataItem698   method = new DataItem698();
    	method.setObjIdent("8000");   //对象id
	    method.setMemberCode("129");
	    method.setDataType("array");
	    //：structure|数据类型*数据#数据类型*数据#structure*数据类型&数据^数据类型&数据^数据类型&数据#array*数据类型&数据^数据类型&数据^数据类型&数据... 
	    method.setInputParam("structure|OAD*F2050200#unsigned*00#long-unsigned*0000#bool*1");
	    list.add(method); 
    	Frame698 frame698 = new Frame698();
 		frame698.setObjclassifi((byte) 7); //对象分类    操作
 		frame698.setMeterComId("376719000004"); //地址
 		frame698.setMeterComIdType("00"); //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
 		frame698.setLogiAdd("00"); //逻辑地址
 		frame698.setDataItem698(list);
    	make698.maker698(frame698);*/
    	
	/*	//生成明文合闸报文
		Make698Frame make698 = new Make698Frame();	
		List<DataItem698>    list  =new  ArrayList<DataItem698>();
		//操作对象方法
    	DataItem698   method = new DataItem698();
    	method.setObjIdent("8000");   //对象id
	    method.setMemberCode("130");
	    method.setDataType("array");
	    //：structure|数据类型*数据#数据类型*数据#structure*数据类型&数据^数据类型&数据^数据类型&数据#array*数据类型&数据^数据类型&数据^数据类型&数据... 
	    method.setInputParam("structure|OAD*F2050200#enum*0");
	    list.add(method); 
    	Frame698 frame698 = new Frame698();
 		frame698.setObjclassifi((byte) 7); //对象分类    操作
 		frame698.setMeterComId("000212000315"); //地址
 		frame698.setMeterComIdType("00"); //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
 		frame698.setLogiAdd("00"); //逻辑地址
 		frame698.setDataItem698(list);
    	make698.maker698(frame698);*/

	//
    	
    	//安全传输
	/*	Make698Frame make698 = new Make698Frame();
		
		Security  secObj = new Security();
		 secObj.setType("1");
		 secObj.setSecType("0");
		 secObj.setTransferData("AADCEC2CD6CB3A6378768CCB798B1FBA660E6A181336E1D9784AAAB7F6287433");
		 secObj.setSecSid("811C8310");
		 secObj.setAttrachData("0024");
		 secObj.setMac("61AF5B6D");
		 
		Frame698 frame698 = new Frame698();
 		frame698.setObjclassifi((byte)16);     //对象分类   安全传输
 		frame698.setMeterComId("FFFFFFFFFFFF"); //地址
 		frame698.setMeterComIdType("00");       //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
 		frame698.setLogiAdd("00"); 				//逻辑地址
 		frame698.setSecurityObj(secObj);
    	make698.maker698(frame698);*/
		 
		 
		 
		 /*Constant698.RAND_HOST = outStr.get(0);
		 Constant698.M1 = outStr.get(1);
		 Constant698.SIGE1 = outStr.get(2);
		 

		//建立应用连接
		Make698Frame make698 = new Make698Frame();
		AppConnReq acr  = new AppConnReq();
    	 acr.setVersion("16");
    	 acr.setSendMaxSize("1024");
    	 acr.setReviceMaxSize("1024");
    	 acr.setReviceMaxWinSize("1");
    	 acr.setMaxApduSize("0000");
    	 //期望的应用连接超时时间,建议设置一天24小时（即86400秒）
    	 acr.setLinkOverTime("86400");
    	 acr.setConnMechInfo("2");
    	 
    	Frame698 frame698 = new Frame698();
 		frame698.setObjclassifi((byte) 2); 	  //对象分类    读取
 		frame698.setMeterComId("000000000001");  //地址
 		frame698.setMeterComIdType("00"); 	  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
 		frame698.setLogiAdd("00"); 			  //逻辑地址
    	frame698.setAppConnReq(acr); 
    	System.out.println("建立连接时报文：");
	    make698.maker698(frame698);
		
//		Frame698 frame698 = new Frame698();
		
	    String mailingStr = "FE FE FE FE 68 90 00 C305 01 00 00 00 00 00 00 8D 10 82 00 30 31 33 30 30 30 30 31 31 37 30 34 31 33 30 3030 32 31 36 31 31 32 39 47 57 36 39 38 5630 39 00 16 FF FF FF FF FF FF FF FF FF FFFF FF FF FF FF FF FF FF FF FF FF FF FF FF02 00 02 00 01 08 00 00 00 06 CC 00 01 30F8 9B CF AC 86 97 4F EF F9 76 04 8B 54 C3 02C6 55 59 E0 80 FE 4A 5E 66 3F83 5D E1 A6 82 F9 00 4F 32 7C F8 78 C8 E579 A4 E5 29 5B BF 57 A2 42 04 96 2C 8C ED00 00 7E 01 16".replace(" ", "");
		byte[] bytes = CommUtils.hex2Binary(mailingStr);
		Parser698 parser698 = new Parser698();
		frame698 = parser698.parser698(bytes);
//		String str = frame698.getDataItem698().get(0).getUpValue();
		String ret = frame698.getAppConnReq().getConnRespInfo().getConnResult();
		String sessionData = frame698.getAppConnReq().getConnRespInfo().getRn();
		String signInfo = frame698.getAppConnReq().getConnRespInfo().getSignInfo();
		System.out.println("返回结果： " + ret);
		System.out.println("随机数M2： " + sessionData);
		System.out.println("数字签名2： "+signInfo);
		
		String sKey = ic.getSessionKey(0, "51010000000492CF", "969DC21A6A60FF0E969DC21A6A60FF0E", sessionData, signInfo);
		System.out.println("验证结果数据" + sKey);
		*/
		
		
	   /************************读取时间*******************************/
//		List<DataItem698>    list  = new  ArrayList<DataItem698>();
//		DataItem698   redTimeItem = new   DataItem698();
//	   	 redTimeItem.setObjIdent("4000");
//	   	 redTimeItem.setMemberCode("2");
//		list.add(redTimeItem);
//	   	Frame698 frame698 = new Frame698();
//	    frame698.setObjclassifi((byte) 5); 	     //对象分类    读取
// 		frame698.setMeterComId("111111111111");  //地址
// 		frame698.setMeterComIdType("00"); 	     //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
// 		frame698.setLogiAdd("00"); 	             //逻辑地址
// 		frame698.setDataItem698(list);
// 		
//	   	new Make698Frame().maker698(frame698);

//		String retStr = "FE FEFE FE 68 21 00 C3 05 01 00 00 00 00 00 00C8 44 85 01 01 40 00 02 00 01 1C 07 E2 0808 08 09 0A 00 00 B5 E8 16".replace(" ", "");
//	   	byte[] bytes = CommUtils.hex2Binary(retStr);
//		Parser698 parser698 = new Parser698();
//		frame698 = parser698.parser698(bytes);
//		String timeStr = frame698.getDataItem698().get(0).getUpValue();
//		System.out.println("当前电表时间："+timeStr);
		
		
		//电表校时
//		List<DataItem698>    list  = new  ArrayList<DataItem698>();
//		DataItem698   setTimeItem = new   DataItem698();
//	   	 setTimeItem.setObjIdent("F101");
//	   	 setTimeItem.setMemberCode("2");
//	   	 setTimeItem.setDataType("enum");
//	   	 setTimeItem.setInputParam("1");   
//	   	 list.add(setTimeItem);
//	   	
//	 	Frame698 frame698 = new Frame698();
//	    frame698.setObjclassifi((byte) 5); 	     //对象分类    设置
// 		frame698.setMeterComId("000000000003");  //地址
// 		frame698.setMeterComIdType("00"); 	     //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
// 		frame698.setLogiAdd("00"); 	             //逻辑地址
// 		frame698.setDataItem698(list);
// 		
   	//new Make698Frame().maker698(frame698);
	   	
	   	
	   	
	   	
//	   	String retStr = "FE FE FEFE 68 1B 00 C3 05 03 00 00 00 00 00 0034 F7 85 01 00 F1 01 02 00 01 16 01 00 005B DA 16".replace(" ", "");
//	   	byte[] bytes = CommUtils.hex2Binary(retStr);
//		Parser698 parser698 = new Parser698();
//		for(int i=0;i<parser698.parser698(bytes).getDataItem698().size();i++){
//			System.out.println(parser698.parser698(bytes).getDataItem698().get(i).getUpValue());
//		}
		
		/****************************电表密钥更新***************************/
//		List<DataItem698>    list  = new  ArrayList<DataItem698>();
//		
//		DataItem698   di =new   DataItem698();
//	   	 di.setObjIdent("F100");   //对象id
//	   	 di.setMemberCode("7");  //方法编号
//	   	 di.setDataType("structure");  //参数类型
//	   	 di.setInputParam("octet-string|AADCEC2CD6CB3A6378768CCB798B1FBA660E6A181336E1D9784AAAB7F6287433:SID_MAC|811C83100024:MAC|61AF5B6D");    //参数值
//	   	 list.add(di);
//		
//	   	Frame698 frame698 = new Frame698();
//	    frame698.setObjclassifi((byte) 7); 	     //对象分类    操作
// 		frame698.setMeterComId("000000000001");  //地址
// 		frame698.setMeterComIdType("00"); 	     //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
// 		frame698.setLogiAdd("00"); 	             //逻辑地址
// 		frame698.setDataItem698(list);
//		
// 		new Make698Frame().maker698(frame698);
		
//		/**************************************开户充值*******************************************/
//		List<DataItem698>    list  = new  ArrayList<DataItem698>();
//		
//		DataItem698   di =new   DataItem698();
//	   	 di.setObjIdent("F100");   //对象id
//	   	 di.setMemberCode("6");  //方法编号
//	   	 di.setDataType("structure");  //参数类型
//	   	 di.setInputParam("integer|0:double-long-unsigned|00000100:double-long-unsigned|00000001:octet-string|000000000001:SID_MAC|811C83100024:MAC|61AF5B6D:octet-string|0000376719000005");    //参数值
//	   	 list.add(di);
//		
//	   	Frame698 frame698 = new Frame698();
//	    frame698.setObjclassifi((byte) 7); 	     //对象分类    操作
// 		frame698.setMeterComId("000000000001");  //地址
// 		frame698.setMeterComIdType("00"); 	     //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
// 		frame698.setLogiAdd("00"); 	             //逻辑地址
// 		frame698.setDataItem698(list);
//		
// 		new Make698Frame().maker698(frame698);
		
		
		//设参数（修改第二套费率电价）
//		List<DataItem698>    list  = new  ArrayList<DataItem698>();
//		 DataItem698   di =new   DataItem698();
//    	 di.setObjIdent("4019");
//    	 di.setMemberCode("2");
//    	 di.setDataType("array");
//    	 di.setInputParam("double-long-unsigned*5.6000");
//    	 list.add(di);
//    	 
// 	   	Frame698 frame698 = new Frame698();
// 	    frame698.setObjclassifi((byte)6); 	     //对象分类   设置
//  		frame698.setMeterComId("000000000001");  //地址
//  		frame698.setMeterComIdType("00"); 	     //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
//  		frame698.setLogiAdd("00"); 	             //逻辑地址
//  		frame698.setDataItem698(list); 
//  		
//  		new Make698Frame().maker698(frame698);
  		
  		
  	//设参数（修改第二套费率电价）
//		List<DataItem698>    list  = new  ArrayList<DataItem698>();
//		 DataItem698   di =new   DataItem698();
//    	 di.setObjIdent("4019");
//    	 di.setMemberCode("2");
//    	 di.setDataType("array");
//    	 di.setInputParam("double-long-unsigned*5.6000");
//    	 list.add(di);
//    	 
// 	   	Frame698 frame698 = new Frame698();
// 	    frame698.setObjclassifi((byte)6); 	     //对象分类   设置
//  		frame698.setMeterComId("111111111111");  //地址
//  		frame698.setMeterComIdType("00"); 	     //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
//  		frame698.setLogiAdd("00"); 	             //逻辑地址
//  		frame698.setDataItem698(list); 
//  		
//  		new Make698Frame().maker698(frame698);
//  		
  		
//  		String retStr = "FE FE FE FE 68 21 00C3 05 11 11 11 11 11 11 00 84 84 85 01 0040 00 02 00 01 1C 07 E2 08 08 08 09 09 0000 C1 89 16".replace(" ", "");
//	   	byte[] bytes = CommUtils.hex2Binary(retStr);
//		Parser698 parser698 = new Parser698();
//		for(int i=0;i<parser698.parser698(bytes).getDataItem698().size();i++){
//			System.out.println(parser698.parser698(bytes).getDataItem698().get(i).getUpValue());
//		}
  		
//		String sessionKey ="BFA8D21DB3E05E0283ABB470A9E24E23A50B31CC2AE324184E693B16985FCB386E4387530ED8D87AE1831584E2A9452B659628ABB933E81C86AAAB94A74F9F47CCDE7AB57143AE3034D36807B13DB861CDE2C4D2446FACE5C7874A4358AB3E6949CA7AEAA4767254F8201DB087B5ABB3FBEC0C026CF10085B069ED2E17854658666344F61C0F95B1855FB7889ACBCA49FD90DB6102120916647243F91E72EDA4C17118303105F707C17118303105F707";
//		List<String> listStr = NewEncryptionMachine.getInstance().Obj_Meter_Formal_GetTrmKeyData(1, "510100000003695B", sessionKey, "0000000000000000", "00");
//		
//		for(int i=0;i<listStr.size();i++){
//			System.out.println(listStr.get(i));
//		}
		 
	}

}
