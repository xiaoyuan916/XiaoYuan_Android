package com.sgcc.pda.hardware.protocol.p698;


import com.sgcc.pda.hardware.protocol.p698.bean698.AppConnReq;
import com.sgcc.pda.hardware.protocol.p698.bean698.ConnRespInfo;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.FactoryVersion;
import com.sgcc.pda.hardware.protocol.p698.bean698.Frame698;
import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.Security;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CrcUtil;
import com.sgcc.pda.sdk.utils.LogUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//解析698上行报文
public class Parser698 {
     //读取分帧响应的下一个数据块  中的分帧序号   默认为 "";
	  public   static String frameNum ="";
	   /**
	    * 解析698报文生成Frame698
	    */
	   public Frame698 parser698(byte []  upBytes){
		  // LogHandler.info(LogHandler.ANALYSIS,"[开始解析上行698报文]"+ CommUtils.byteToHexString(upBytes));
		   Frame698    frame698  =getFrame698(upBytes) ;
		   //获取执行操作类型
		   byte   optCode  =  frame698.getObjclassifi();   //读取  设置  操作  代理   上报

		   switch(optCode){
		             case Constant698.LINK_RESPONSE:  //预连接响应
		            	  parse81(frame698);
		            	  break;
		             case Constant698.CONNECT_RESPONSE:  //建立应用连接响应
		            	  parse82(frame698);
		            	 break;
		             case Constant698.RELEASE_RESPONSE:  //断开应用连接响应
		            	 parse83(frame698);
		            	 break;
		             case  Constant698.RELEASE_NOTIFICATION:  //断开应用连接通知
		            	 parse84(frame698);
		             case Constant698.GET_OBJECT_RESPONSE_DATA:   //响应读取请求
		             parse85(frame698);
		             break;
		             case  Constant698.SET_OBJECT_RESPONSE_DATA:  //响应设置请求
		             parse86(frame698);
		             break;
		             case  Constant698.ACTION_OBJECT_RESPONSE_DATA:  //响应操作请求
			         parse87(frame698);
			         break;
		             case  Constant698.REPORT_OBJECT_RESPONSE_DATA:  //上报通知
				     notification88(frame698);
				     break;
		             case  Constant698.PROXY_OBJECT_RESPONSE_DATA:  //响应代理请求
				     parse89(frame698);
				     break;
		             case   Constant698.SECURITY_RESPONSE_DATA:  //响应安全传输请求
		             parse90(frame698);
		   }

		   return  frame698;
	   }


	   //响应安全传输
	   private void parse90(Frame698 frame698) {
		    int len = 0;  //密文或明文长度
			byte unit = frame698.getOptCount(); // 应用数据单元 0明文 1密文 2异常数据
			byte[] dataBytes; // 存储【明文/密文】数据
			byte[] data = frame698.getLinkUserData(); // 链路层用户数据
			switch (unit) {
			case 0x00: { // 明文
				// 获取明文长度
				len = Integer.parseInt(byteToHex(data[2]), 16);
				if(0x01==data[4]){//执行什么操作 一个 或多个  01 单个  02、04多个个
					dataBytes = new byte[len - 3]; //去掉读取请求标识、读取操作标识、PIID-ACD
					System.arraycopy(data, 6, dataBytes, 0, dataBytes.length); // 获取明文数据[不包括读取请求标识、读取操作标识、PIID-ACD]
					// 读取对象个数
					getReadRespon(dataBytes, 1, frame698);
				}else if(0x02==data[4]){
					dataBytes = new byte[len - 4]; //去掉读取请求标识、读取操作标识、PIID-ACD、 个数
					System.arraycopy(data, 7, dataBytes, 0, dataBytes.length); // 获取明文数据[不包括读取请求标识、读取操作标识、PIID-ACD]
					// 读取对象个数
					getReadRespon(dataBytes, Integer.parseInt(byteToHex(data[6]), 16), frame698);
				}else  if(0x03==data[4]){
					dataBytes   =  new  byte[data.length-3]; //存储PIID-ACD之后的数据
					System.arraycopy(frame698.getLinkUserData(), 3, dataBytes, 0, dataBytes.length);
					//bytes 报文数据、    记录型对象个数、   698报文对象
					parseRecord(dataBytes,  1,  frame698);

				}else  if(0x04==data[4]){
					dataBytes   =  new  byte[data.length-4]; //存储PIID-ACD之后的数据
					System.arraycopy(frame698.getLinkUserData(), 3, dataBytes, 0, dataBytes.length);
					//bytes 报文数据、    记录型对象个数、   698报文对象
					parseRecord(dataBytes,  1,  frame698);
				}
				break;
			}
			// TODO: 2018/1/3  这个解析密文。。。需要连接加密机。。。加密机返回明文在解析
//			case 0x01: { // 密文
//				len = Integer.parseInt(byteToHex(data[2]), 16);// 密文长度
//				byte[] enByte = new byte[len];
//				System.arraycopy(data, 3, enByte, 0, len); //复制密文数据到 enByte数组
//				byte   []   express =  Sp698ProtoUtil.getExpress(enByte);  //获取明文
//				if(0x01==express[1]){//执行什么操作 一个 或多个  01 单个  02、04多个个
//					dataBytes = new byte[express.length - 3]; //去掉读取请求标识、读取操作标识、PIID-ACD
//					System.arraycopy(express, 3, dataBytes, 0, express.length - 3); // 获取明文数据[不包括读取请求标识、读取操作标识、PIID-ACD]
//					// 读取对象个数
//					getReadRespon(dataBytes, 1, frame698);
//				}else{
//					dataBytes = new byte[len - 4]; //去掉读取请求标识、读取操作标识、PIID-ACD、 个数
//					System.arraycopy(express, 4, dataBytes, 0, dataBytes.length-4); // 获取明文数据[不包括读取请求标识、读取操作标识、PIID-ACD]
//					// 读取对象个数
//					getReadRespon(dataBytes, Integer.parseInt(byteToHex(data[3]), 16), frame698);
//				}
//				break;
//			}
			case 0x02: { // 异常信息
				List<DataItem698> ldi = new ArrayList<DataItem698>();
				DataItem698 dataItem698 = new DataItem698();
				dataItem698.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(data[6]), 16))));
				ldi.add(dataItem698);
				frame698.setDataItem698(ldi);
				break;
			}
			}
	        //解析数据验证信息
			byte []  verify  = new  byte[data.length - len];
			System.arraycopy(data, len, verify, 0, verify.length);
			if(0x01==verify[0]){  //等于01 说明含数据验证信息
				Security secObj = new Security();
				if(0x00==verify[1]){  // MAC
					byte[] macByte = new byte[4];
					System.arraycopy(verify, 3, macByte, 0, 4);
					secObj.setMac(CommUtils.byteToHexString(macByte));
					frame698.setSecurityObj(secObj);
				}
			}
			dataBytes = null;
			data = null;
	}


		// 解析读取请求报文
	    private void parse85(Frame698 frame698) {
	          byte  opt=frame698.getOptCount();	   // 读取一个或多个对象
	          byte []  bytes  ;
              if(opt==0x01){
            	  bytes  = new  byte[frame698.getLinkUserData().length-3];  // 存储PIID-ACD之后的数据
            	  System.arraycopy(frame698.getLinkUserData(), 3, bytes, 0, bytes.length);
            	  getReadRespon(bytes,  1,  frame698);
              }else  if(opt==0x02){
            	  bytes  = new  byte[frame698.getLinkUserData().length-4];  //存储对象个数之后的数据
            	  System.arraycopy(frame698.getLinkUserData(), 4, bytes, 0, bytes.length);
            	  getReadRespon(bytes,  Integer.parseInt(byteToHex(frame698.getLinkUserData()[3]), 16),  frame698);
              }else  if(opt==0x03){  //解析读取一个记录型对象
            	  bytes   =  new  byte[frame698.getLinkUserData().length-3]; //存储PIID-ACD之后的数据
            	  System.arraycopy(frame698.getLinkUserData(), 3, bytes, 0, bytes.length);
            	  //bytes 报文数据、    记录型对象个数、   698报文对象
            	  parseRecord(bytes,  1,  frame698);
              }else  if(opt==0x04){ //读取若干个记录型对象属性响应
            	  bytes   =  new  byte[frame698.getLinkUserData().length-4]; //存储记录型对象个数之后的数据
            	  System.arraycopy(frame698.getLinkUserData(), 4, bytes, 0, bytes.length);
            	  //bytes 报文数据、    记录型对象个数、   698报文对象
            	  parseRecord(bytes,  Integer.parseInt(byteToHex(frame698.getLinkUserData()[3]), 16),  frame698);
              }else  if(opt==0x05){ //分帧响应一个数据块
            	  byte   temp =  frame698.getLinkUserData()[3];   //末帧标志   00：表示读取的报文数据未读完   01：读取报文已结束
                  if(0x00==temp){  //表示读取的报文数据未读完
                	  byte []  num = new  byte[2];
                	  System.arraycopy(frame698.getLinkUserData(), 4, num, 0, 2);
                	  frameNum = CommUtils.byteToHexString(num);  //分帧序号
                  }
                  if(0x00==frame698.getLinkUserData()[6]){ //错误
                	  //错误信息
		    			String error =   Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(frame698.getLinkUserData()[7]), 16)));
                        DataItem698  di  =  new DataItem698();
                        di.setUpValue(error);
                        List<DataItem698> list = new ArrayList<DataItem698>();
                        list.add(di);
                        frame698.setDataItem698(list);
                  }else if(0x01==frame698.getLinkUserData()[6]){  //对象属性
                	  bytes  = new  byte[frame698.getLinkUserData().length-7];  //存储对象个数之后的数据
                	  System.arraycopy(frame698.getLinkUserData(), 8, bytes, 0, bytes.length);
                	  getReadRespon(bytes,  Integer.parseInt(byteToHex(frame698.getLinkUserData()[7]), 16),  frame698);
                  }else{  //记录型对象
                	  bytes  = new  byte[frame698.getLinkUserData().length-7];  //存储对象个数之后的数据
                	  System.arraycopy(frame698.getLinkUserData(), 8, bytes, 0, bytes.length);
                	  parseRecord(bytes,  Integer.parseInt(byteToHex(frame698.getLinkUserData()[7]), 16),  frame698);
                  }

              }
		}

	public void parseDongJie(Frame698 frame698) {
		byte []  bytes  ;
		bytes   =  new  byte[frame698.getLinkUserData().length-3]; //存储PIID-ACD之后的数据
		System.arraycopy(frame698.getLinkUserData(), 3, bytes, 0, bytes.length);
		//bytes 报文数据、    记录型对象个数、   698报文对象
		parseRecord(bytes,  1,  frame698);
		//parseRecord1(bytes,  1,  frame698);
	}

	    //解析组装记录型对象数据
	    /**
	     * bytes 报文数据内容----从OAD位置开始
	     * count  记录型对象个数
	     * frame698  698报文对象
	     */
	    private   void   parseRecord(byte [] bytes ,  int  count  ,  Frame698  frame698){
	    	 DataItem698  dataItem698;
	    	 int  colCon=0;  //一个记录型对象列个数
	    	 int  rpos = 4;  //游离数组bytes的下标--------直接将下标定位到列个数位置
	    	 List<DataItem698> rlist  = new ArrayList<DataItem698>();    //存储列对象
	    	 byte []   oad  = new  byte[4];  //存储 读取列对象属性描述符
	    	 int	data; // 结构体包含元素个数  或 数组长度
	    	 for(int  i=0;  i<count; i++){
		    	    //一个记录型对象行记录列个数
		    		colCon = Integer.parseInt(byteToHex(bytes[rpos++]), 16); //获取列个数，下标定位到列选择描述符
		    		 //列选择描述符  00 一般对象   01记录型对象  ------------创建698数据项对象
		    		 for(int j=0; j<colCon; j++){
		    			  switch(bytes[rpos++]){ //下标指向OAD开始的位置
		    			     case   0x00:  //一般对象数据类型
								 LogUtil.e("TL","解析了一般对象数据类型"+j);
		    			    	 System.arraycopy(bytes, rpos, oad, 0, 4);
		    			    	 dataItem698 =new DataItem698();
		    			    	 parserOad(oad,  dataItem698);    //解析对象标识
		    			    	 dataItem698.setOad(CommUtils.byteToHexString(oad));  // 获取到对象属性描述符
		    			    	 rlist.add(dataItem698);
		    			    	 rpos = rpos + 4 ; //将下标定位到下一个OAD的位置 或者 响应数据的位置
		    			    	 break;
		    			     case   0x01:  //记录型数据对象类型j
								 LogUtil.e("TL","记录型数据对象类型j"+j);
		    			    	 rpos = rpos + 4;  //将下标定位到记录型对象对应的OAD个数
		    			    	 int  rcount;  //列所对应的记录型对象包括的OAD个数
		    			    	 rcount = Integer.parseInt(byteToHex(bytes[rpos++]), 16);
		    			    	 for(int k=0; k<rcount; k++){
		    			    		 System.arraycopy(bytes, rpos, oad, 0, 4);
			    			    	 dataItem698 =new DataItem698();
			    			    	 parserOad(oad,  dataItem698);    //解析对象标识
			    			    	 dataItem698.setOad(CommUtils.byteToHexString(oad));  // 获取到对象属性描述符
			    			    	 dataItem698.setIsrelation(true);
			    			    	 if(0==k){
			    			    		 dataItem698.setIsfrist(true);  //是否第一个关联对象
			    			    	 }
			    			    	 rlist.add(dataItem698);
			    			    	 rpos = rpos + 4 ; //将下标定位到下一个OAD的位置 或者 响应数据的位置
		    			    	 }
		    			    	 break;
		    			  }
		    		 }
		    		 //00 错误信息   01 记录条数
		    		 if(bytes[rpos++]==0x00){   //rpos已经定位到 响应数据的位置  , 如果等于00，rpos定位到错误信息编码，如果等于01，rpos定位到记录条数
		    			 //错误信息
		    			String error =   Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[rpos++]), 16)));
		    			for(DataItem698  di : rlist){
		    				di.setUpValue(error);    //设置错误信息
		    			}
		    		 }else{
		    			 //获取计量条数
		    			 int   infoCon  = Integer.parseInt(byteToHex(bytes[rpos++]), 16);   //下标定位到数据内容的数据第一个数据类型
		    			 //将数据内容 同对应的OAD 对应
		    			 StringBuffer sb =new StringBuffer();
		    			 for(int  p=0; p<infoCon; p++){
			    				   for(DataItem698  di : rlist){
			    					    sb.delete(0, sb.length());
			    					 if(!di.isIsrelation()){  //非记录型对象关联对象
			    						    di.setDataType(userCodeType(bytes[rpos++])); //数据类型   ------pos++定位到（长度  或 数据）
						 					if("structure".equals(di.getDataType())||"array".equals(di.getDataType())){
						 					     data=bytes[rpos++];  // 结构体包含元素个数  或 数组长度
						 						 rpos=ParserUpdata.handlerStructureOrArrayUnit(bytes, data, rpos, sb, di);
						 					}else{
						 						 rpos=ParserUpdata.handlerCommUnit(bytes,rpos,sb,di.getDataType(), di);
						 					}
			    					 }else{  //记录型对象的关联对象 +2 跳过记录型对象表示的两个字节  只有第一个记录型对象关联的对象执行前加2 后面的不需要再加2
			    						   if(di.isIsfrist()){
			    							   rpos +=2;
			    						   }
			    						    di.setDataType(userCodeType(bytes[rpos++])); //数据类型   ------pos++定位到（长度  或 数据）
						 					if("structure".equals(di.getDataType())||"array".equals(di.getDataType())){
						 					     data=bytes[rpos++];  // 结构体包含元素个数  或 数组长度
						 						 rpos=ParserUpdata.handlerStructureOrArrayUnit(bytes, data, rpos, sb, di);
						 					}else{
						 						 rpos=ParserUpdata.handlerCommUnit(bytes,rpos,sb,di.getDataType(), di);
						 					}
			    					 }
			    					  if(null!=di.getUpValue()&&!"".equals(di.getUpValue())){
			    						 // di.setUpValue(di.getUpValue()+"#"+sb.toString().substring(1).replace("#|", "|"));
			    						  di.setUpValue(di.getUpValue()+"#"+sb.toString().substring(1).replace("#|", "-"));
			    					  }else{
			    						 // di.setUpValue(sb.toString().substring(1).replace("#|", "|"));
			    						  di.setUpValue(sb.toString().substring(1).replace("#|", "-"));
			    					  }

			    				   }
		    			 }
		    		 }
	    	 }
	    	  frame698.setDataItem698(rlist);
	    }

	//解析组装记录型对象数据
	/**
	 * bytes 报文数据内容----从OAD位置开始
	 * count  记录型对象个数
	 * frame698  698报文对象
	 */
	private   void   parseRecord1(byte [] bytes ,  int  count  ,  Frame698  frame698){
		DataItem698  dataItem698;
		int  colCon=0;  //一个记录型对象列个数
		int  rpos = 4;  //游离数组bytes的下标--------直接将下标定位到列个数位置
		List<DataItem698> rlist  = new ArrayList<DataItem698>();    //存储列对象
		byte []   oad  = new  byte[4];  //存储 读取列对象属性描述符
		int	data; // 结构体包含元素个数  或 数组长度
		for(int  i=0;  i<count; i++){
			//一个记录型对象行记录列个数
			colCon = Integer.parseInt(byteToHex(bytes[rpos++]), 16); //获取列个数，下标定位到列选择描述符
			//列选择描述符  00 一般对象   01记录型对象  ------------创建698数据项对象
			for(int j=0; j<colCon; j++){
//				switch(bytes[rpos++]){ //下标指向OAD开始的位置
////					case   0x00:  //一般对象数据类型
////						System.arraycopy(bytes, rpos, oad, 0, 4);
////						dataItem698 =new DataItem698();
////						parserOad(oad,  dataItem698);    //解析对象标识
////						dataItem698.setOad(CommUtils.byteToHexString(oad));  // 获取到对象属性描述符
////						rlist.add(dataItem698);
////						rpos = rpos + 4 ; //将下标定位到下一个OAD的位置 或者 响应数据的位置
////						break;
//					case   0x00:  //记录型数据对象类型
						rpos = rpos + 4;  //将下标定位到记录型对象对应的OAD个数
						int  rcount;  //列所对应的记录型对象包括的OAD个数
						rcount = Integer.parseInt(byteToHex(bytes[rpos++]), 16);
						for(int k=0; k<rcount; k++){
							System.arraycopy(bytes, rpos, oad, 0, 4);
							dataItem698 =new DataItem698();
							parserOad(oad,  dataItem698);    //解析对象标识
							dataItem698.setOad(CommUtils.byteToHexString(oad));  // 获取到对象属性描述符
							dataItem698.setIsrelation(true);
							if(0==k){
								dataItem698.setIsfrist(true);  //是否第一个关联对象
							}
							rlist.add(dataItem698);
							rpos = rpos + 4 ; //将下标定位到下一个OAD的位置 或者 响应数据的位置
						}
//						break;
//				}
			}
//			//00 错误信息   01 记录条数
//			if(bytes[rpos++]==0x00){   //rpos已经定位到 响应数据的位置  , 如果等于00，rpos定位到错误信息编码，如果等于01，rpos定位到记录条数
//				//错误信息
//				String error =   Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[rpos++]), 16)));
//				for(DataItem698  di : rlist){
//					di.setUpValue(error);    //设置错误信息
//				}
//			}else{
				//获取计量条数
				int   infoCon  = Integer.parseInt(byteToHex(bytes[rpos++]), 16);   //下标定位到数据内容的数据第一个数据类型
				//将数据内容 同对应的OAD 对应
				StringBuffer sb =new StringBuffer();
				for(int  p=0; p<infoCon; p++){
					for(DataItem698  di : rlist){
						sb.delete(0, sb.length());
						if(!di.isIsrelation()){  //非记录型对象关联对象
							di.setDataType(userCodeType(bytes[rpos++])); //数据类型   ------pos++定位到（长度  或 数据）
							if("structure".equals(di.getDataType())||"array".equals(di.getDataType())){
								data=bytes[rpos++];  // 结构体包含元素个数  或 数组长度
								rpos=ParserUpdata.handlerStructureOrArrayUnit(bytes, data, rpos, sb, di);
							}else{
								rpos=ParserUpdata.handlerCommUnit(bytes,rpos,sb,di.getDataType(), di);
							}
						}else{  //记录型对象的关联对象 +2 跳过记录型对象表示的两个字节  只有第一个记录型对象关联的对象执行前加2 后面的不需要再加2
							if(di.isIsfrist()){
								rpos +=2;
							}
							di.setDataType(userCodeType(bytes[rpos++])); //数据类型   ------pos++定位到（长度  或 数据）
							if("structure".equals(di.getDataType())||"array".equals(di.getDataType())){
								data=bytes[rpos++];  // 结构体包含元素个数  或 数组长度
								rpos=ParserUpdata.handlerStructureOrArrayUnit(bytes, data, rpos, sb, di);
							}else{
								rpos=ParserUpdata.handlerCommUnit(bytes,rpos,sb,di.getDataType(), di);
							}
						}
						if(null!=di.getUpValue()&&!"".equals(di.getUpValue())){
							di.setUpValue(di.getUpValue()+"#"+sb.toString().substring(1).replace("#|", "|"));
						}else{
							di.setUpValue(sb.toString().substring(1).replace("#|", "|"));
						}

					}
				}
			}
//		}
		frame698.setDataItem698(rlist);
	}
	    //解析设置请求报文
	    private  void  parse86(Frame698 frame698){
	    	 byte  opt = frame698.getOptCount();  //设置对象个数   一个或多个
	    	 byte  []   setdata ;
	    	 if(opt ==0x01){    //设置一个
	    		 setdata = new   byte[frame698.getLinkUserData().length-3];  //存储  除去OAD前面以外的数据
	    		 System.arraycopy(frame698.getLinkUserData(), 3, setdata, 0, setdata.length);
	    		 frame698.setDataItem698(getSetRespon(setdata, 1));
	    	 }else  if(opt ==0x02){   //设置多个
	    		 setdata = new   byte[frame698.getLinkUserData().length-4];  //存储  除去OAD前面以外的数据
	    		 System.arraycopy(frame698.getLinkUserData(), 4, setdata, 0, setdata.length);
	    		 frame698.setDataItem698(getSetRespon(setdata, Integer.parseInt(byteToHex(frame698.getLinkUserData()[3]), 16)));
	    	 }else   if(opt ==0x03){  //设置对象属性后读取对象属性
	    		 setdata = new   byte[frame698.getLinkUserData().length-4];  //存储  除去OAD前面以外的数据
	    		 System.arraycopy(frame698.getLinkUserData(), 4, setdata, 0, setdata.length);
	    		 //解析设置后读取对象属性
	    		 frame698.setDataItem698(getSetAndReadRespon(setdata,  Integer.parseInt(byteToHex(frame698.getLinkUserData()[3]), 16)));
	    	 }

	    }


	    //解析操作请求报文
	    private  void  parse87(Frame698  frame698){
	    	 byte  opt = frame698.getOptCount();  //操作对象个数   一个或多个
	    	 byte  []   actiondata ;
	    	 if(opt ==0x01){    //解析操作一个对象方法
	    		 actiondata = new   byte[frame698.getLinkUserData().length-3];  //存储  除去OMD前面以外的数据
	    		 System.arraycopy(frame698.getLinkUserData(), 3, actiondata, 0, actiondata.length);
	    		 frame698.setDataItem698(getActionRespon(actiondata, 1));
	    	 }else  if(opt ==0x02){   //操作多个对象方法
	    		 actiondata = new   byte[frame698.getLinkUserData().length-4];  //存储  除去OMD前面以外的数据
	    		 System.arraycopy(frame698.getLinkUserData(), 4, actiondata, 0, actiondata.length);
	    		 frame698.setDataItem698(getActionRespon(actiondata, Integer.parseInt(byteToHex(frame698.getLinkUserData()[3]), 16)));
	    	 }else if(opt ==0x03){   //操作方法后读取对象属性
	    		 actiondata = new   byte[frame698.getLinkUserData().length-4];  //存储  除去OMD前面以外的数据
	    		 System.arraycopy(frame698.getLinkUserData(), 4, actiondata, 0, actiondata.length);
	    		 frame698.setDataItem698(getActionAndRead(actiondata, Integer.parseInt(byteToHex(frame698.getLinkUserData()[3]), 16)));
	    	 }
	    }


	    //通知上报
	    private  void   notification88(Frame698 frame698){
	    	  byte  opt =  frame698.getOptCount();   //上报通知类型   ：[1]  上报若干个对象属性       [2]  上报若干个记录型对象属性
	          byte  []   bytes;
	    	  if(opt==0x01){ // [1]  上报通知若干个对象属性
	        	  bytes  = new  byte[frame698.getLinkUserData().length-4];  //上报通知对象个数之后的数据
            	  System.arraycopy(frame698.getLinkUserData(), 4, bytes, 0, bytes.length);
            	  getReadRespon(bytes,  Integer.parseInt(byteToHex(frame698.getLinkUserData()[3]), 16),  frame698);
	          }
	          if(opt==0x02){ //[2]  上报若干个记录型对象属性
	        	  bytes   =  new  byte[frame698.getLinkUserData().length-4]; //上报通知记录型对象个数之后的数据
            	  System.arraycopy(frame698.getLinkUserData(), 4, bytes, 0, bytes.length);
            	  //bytes 报文数据、    记录型对象个数、   698报文对象
            	  parseRecord(bytes,  Integer.parseInt(byteToHex(frame698.getLinkUserData()[3]), 16),  frame698);
	          }
	    }

	    //解析代理请求报文
	    private  void   parse89(Frame698 frame698){
	    	//执行什么代理操作
	    	byte    opt  =  frame698.getOptCount();
	    	byte  []    data;
	    	switch(opt){
	    	 case   0x01:    //代理读取多个服务器的多个对象属性
	    		 data = new  byte[frame698.getLinkUserData().length-4];   //去除第一个代理服务器地址之前的数据
	    		 System.arraycopy(frame698.getLinkUserData(), 4, data, 0, data.length);
	    		 frame698.setDataItem698(parseProxyReadParam(data,  Integer.parseInt(byteToHex(frame698.getLinkUserData()[3]), 16)));  // 数据、 代理服务器个数
	    		 break;
	    	 case   0x02:    //代理读取一个服务器的一个记录型对象属性
	    		 data = new  byte[frame698.getLinkUserData().length-4];   //去除代理服务器地址之前的数据
	    		 System.arraycopy(frame698.getLinkUserData(), 3, data, 0, data.length);
	    		 parseProxyRecord(data,  frame698);
	    		 break;
	    	 case   0x03:    //代理设置多个服务器的多个对象属性
	    		 data = new  byte[frame698.getLinkUserData().length-4];   //去除第一个代理服务器地址之前的数据
	    		 System.arraycopy(frame698.getLinkUserData(), 4, data, 0, data.length);
	    		 frame698.setDataItem698(parseProxySetParam(data,  Integer.parseInt(byteToHex(frame698.getLinkUserData()[3]), 16)));  // 数据、 代理服务器个数
	    		 break;
	    	 case   0x04:    //代理设置后读取多个服务器的多个对象属性
	    		 data = new  byte[frame698.getLinkUserData().length-4];   //去除第一个代理服务器地址之前的数据
	    		 System.arraycopy(frame698.getLinkUserData(), 4, data, 0, data.length);
	    		 frame698.setDataItem698(parseProxySetAndReadParam(data,  Integer.parseInt(byteToHex(frame698.getLinkUserData()[3]), 16)));  // 数据、 代理服务器个数
	    		 break;
	    	 case   0x05:    //代理操作多个服务器的多个对象方法
	    		 data = new  byte[frame698.getLinkUserData().length-4];   //去除第一个代理服务器地址之前的数据
	    		 System.arraycopy(frame698.getLinkUserData(), 4, data, 0, data.length);
	    		 frame698.setDataItem698(parseProxyOptMethod(data,  Integer.parseInt(byteToHex(frame698.getLinkUserData()[3]), 16)));  // 数据、 代理服务器个数
	    		 break;
	    	 case   0x06:    //代理操作后读取多个服务器的多个对象方法和属性
	    		 data = new  byte[frame698.getLinkUserData().length-4];   //去除第一个代理服务器地址之前的数据
	    		 System.arraycopy(frame698.getLinkUserData(), 4, data, 0, data.length);
	    		 frame698.setDataItem698(parseProxyOptAndReadParam(data,  Integer.parseInt(byteToHex(frame698.getLinkUserData()[3]), 16)));  // 数据、 代理服务器个数
	    		 break;
	    	 case   0x07:    //代理操作透明转发命令响应
	    		 data  =  new  byte[frame698.getLinkUserData().length-3];   //去除数据转发端口之前的数据
	    		 System.arraycopy(frame698.getLinkUserData(), 3, data, 0, data.length);
	    		 frame698.setDataItem698(parsePaoxyTransForward(data));
	    		 break;
	    	}

	    }

	    //解析 代理读取多个服务器的多个对象属性
	    private List<DataItem698> parseProxyReadParam(byte  []  bytes, int  count){
	    	List<DataItem698> dataItem =  new ArrayList<DataItem698>();
	    	DataItem698      dataItem698  ;
	    	int   pos=0;  //当前下标所在的位置
	    	byte  []  tsa ; //代理服务器的地址
	    	int   len=0; //tsa的长度
	    	int   cou=0 ;//一个服务器所代理读取的对象个数
	    	byte  [] oad =new  byte[4];  //OAD
	    	//int  dataTypeLen =0;   //数据类型长度
			int  data ; //数据长度
		  // String  dataType1;  //内循环数据类型
			//int  dataLen1;   //内循环数据类型长度
		//	byte  []  byte1 ; //存储内循环数据
			String onetsa;    //代理服务器地址
	    	for(int i=0; i<count;  i++){   //循环代理服务器的个数  开始
	    		len = Integer.parseInt(byteToHex(bytes[pos++]), 16); //index 定位到地址数据开始的位置
		    	tsa  =  new  byte[len];
		    	System.arraycopy(bytes, pos, tsa, 0, len);  //将tsa数据复制到字节数组tsa中
		    	pos = pos + len;  //将下标定位到服务器代理对象个数的位置
	    		cou = Integer.parseInt(byteToHex(bytes[pos++]), 16);  //某一个服务器所代理的读取对象的个数      将下标定位到OAD开始的位置
	    		onetsa = CommUtils.byteToHexString(tsa);
	    		for(int  j=0;  j<cou; j++){  //循环某一个服务器所读取对象的属性  开始
	    			  dataItem698  = new  DataItem698();
	    			  dataItem698.setTsa(onetsa);    //tsa
		    		  System.arraycopy(bytes, pos, oad, 0, 4);  //获取数据标识
		   			  parserOad(oad,  dataItem698);    //解析对象标识
		   			  dataItem698.setOad(CommUtils.byteToHexString(oad));
		   			  pos+=4;   //result 标识下标
		   			  //  RESULT     0  错误信息 DAR   1  数据 DATA
		   			  if(0x00==bytes[pos]){  //错误信息
		   				    pos +=1;
		   				    dataItem698.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16))));  //设置读取结果  并将下标定义到下一个对象标识
		   			  }else if(0x01==bytes[pos]){  //数据
			   				pos +=1;     //数据类型下标
							dataItem698.setDataType(userCodeType(bytes[pos++])); //数据类型   ------pos++定位到（长度  或 数据）
							StringBuffer sb=new StringBuffer();
							if("structure".equals(dataItem698.getDataType())||"array".equals(dataItem698.getDataType())){
								data=bytes[pos++];    // 结构体包含元素个数  或 数组长度            pos
								pos=ParserUpdata.handlerStructureOrArrayUnit(bytes,data,pos, sb, dataItem698);
							}else{
								pos=ParserUpdata.handlerCommUnit(bytes,pos,sb,dataItem698.getDataType(), dataItem698);
							}
							dataItem698.setUpValue(sb.toString().substring(1).replace("#|", "|"));
		   				/*	pos +=1;     //数据类型下标
		   					dataItem698.setDataType(userCodeType(bytes[pos++])); //数据类型   ------pos++定位到（长度  或 数据）
		   					//数据类型长度
		   					dataTypeLen =Constant698.DATA_TYPE_GET_LEGTH.get(dataItem698.getDataType())==null? 0 :Integer.parseInt(Constant698.DATA_TYPE_GET_LEGTH.get(dataItem698.getDataType()));
		   				    if(dataTypeLen==0){ //等于0说明数据类型长度为可变的。。。。则下一个字节数据为数据长度
		   				    	   data  = Integer.parseInt(byteToHex(bytes[pos++]), 16);//获取数据长度     pos++  定位到数据
		   				    	   if(dataItem698.getDataType().equals("array")){  //数组
		   				    		     for(int k=0; k<data; k++){
			   				    		    	 dataType1 = userCodeType(bytes[pos++]);	 //获取数据类型    pos定位到长度或数据开始的位置
			   				    		    	 dataLen1 =Constant698.DATA_TYPE_GET_LEGTH.get(dataType1)==null ? 0 :Integer.parseInt(Constant698.DATA_TYPE_GET_LEGTH.get(dataType1));//类型长度
			   				    		    	 if(0 ==dataLen1){ //说明对应的数据类型给定的数据长度不确定
			   				    		    	   dataLen1 = Integer.parseInt(byteToHex(bytes[pos++]), 16);  //获取数据长度，并将下标定位到数据开始的位置
			   				    		    	 }
			   				    		    	 if(dataType1.equals("array")){
			   				    		    		        //个数  、pos当前位置以后的所有数据
			   				    		    		        byte  []   layer  =  new  byte[bytes.length-pos+1];  //pos指向当前位置以后的数据长度
			   				    		    		        System.arraycopy(bytes, pos, layer, 0, layer.length);  //如果是 数组类型  pos 当前指向的是数据类型
			   				    		    		        String [] value =  oneLayer(dataLen1,  layer, dataItem698).split(",");
			   				    		    		        pos  =  Integer.parseInt(value[0]);
				   				    		    		    if(!"".equals(dataItem698.getUpValue()) && null!=dataItem698.getUpValue()){
						   				    		        	 dataItem698.setUpValue(dataItem698.getUpValue()+"|"+value[1]);   //多个值
						   				    		        }else{
						   				    		        	 dataItem698.setUpValue(value[1]);
						   				    		        }
			   				    		    	 }else{
					   				    		    		 byte1=new  byte[dataLen1];//数据
						   			    		    		 System.arraycopy(bytes, pos, byte1, 0, dataLen1);  //pos已经定位到数据开始的位置
						   			    		    		 IPharesItem698   phares = Constant698.getiPharesItem698(dataType1); // +"_"+conv  根据成员数据类型 + 该成员对应的换算值 获取数据类型解析类
						   				    		         phares.setUpbyte(byte1);
						   				    		         if(!"".equals(dataItem698.getUpValue()) && null!=dataItem698.getUpValue()){
						   				    		        	 dataItem698.setUpValue(dataItem698.getUpValue()+"|"+phares.getResStr());   //多个值
						   				    		         }else{
						   				    		        	 dataItem698.setUpValue(phares.getResStr());
						   				    		         }
						   				    		         pos +=dataLen1;    //将 pos 定位到下一个对象标识开始的位置
			   				    		    	 }
		   				    		     }
		   				    	   }else {
		   				    		   byte1  =new  byte[data];
		   				    		   System.arraycopy(bytes, pos, byte1, 0, data);  // pos 位置在数据开始的位置
		   				    		   IPharesItem698   phares = Constant698.getiPharesItem698(dataItem698.getDataType()); // +"_"+conv  根据成员数据类型 + 该成员对应的换算值 获取数据类型解析类
		   			    		       phares.setUpbyte(byte1);
		   			    		       dataItem698.setUpValue(phares.getResStr());
		   			    		       pos += data;   //将pos定位到下一个对象标识的开始位置
		   				    	   }
		   				    }else{  //数据类型长度是固定长度
		   				    	  byte1 =new  byte[dataTypeLen];
		   				    	  System.arraycopy(bytes, pos, byte1, 0, dataTypeLen); //pos 位置在数据开始的位置
		   				    	  IPharesItem698   phares = Constant698.getiPharesItem698(dataItem698.getDataType()); // +"_"+conv  根据成员数据类型 + 该成员对应的换算值 获取数据类型解析类
		   		    		      phares.setUpbyte(byte1);
		   		    		      dataItem698.setUpValue(phares.getResStr());
		   		    		      pos += dataTypeLen;   //将pos定位到下一个对象标识的开始位置  +1 数据类型
		   				    }*/
		   			  }
	    			 dataItem.add(dataItem698);
	    		}  //循环某一个服务器所读取对象的属性  结束

	    	}  //循环代理服务器的个数  结束
	    	return   dataItem;
	    }

	    /**
	     *
	     * @param count    数组数据个数
	     * @param layer    数据
	     * @param dataItem698
	     * @return     一个下标指向 ， 组装的数组数据
	     */
	    private String oneLayer(int count, byte[] layer, DataItem698  dataItem698) {
	    	   int  lpos=0;
	    	   String type;  //数据类型
	    	   int  length;   //数据类型长度
	    	   StringBuffer sb =  new StringBuffer();
	    	   byte  []   data;  //存储要处理的数据
	    	 //获取对象对应的换算值   由于表尚未建立   暂时注释
	   		 //	String   conv =DataItemCache698.getConversion(dataItem698.getObjIdent()+"_0_"+dataItem698.getMemberCode());   //根据对象标识 +成员类型（0：属性；  1：方法）+ 成员编号  获取对象换算值
	    	   for(int  i=0;  i<count; i++){
	    		   type=userCodeType(layer[lpos++]);  //数据类型
	    		   length =Constant698.DATA_TYPE_GET_LEGTH.get(type)==null ? 0 : Integer.parseInt(Constant698.DATA_TYPE_GET_LEGTH.get(type));//数据类型长度
	    	       if(length==0){
	    	    	   length =  Integer.parseInt(byteToHex(layer[lpos++]), 16);  //获取数据长度，并将下标定位到数据或数据类型【数组】开始的位置
	    	       }
	    	       if(type.equals("array")){
	    	    	   //个数  、pos当前位置以后的所有数据
		    		        byte  []   tlayer  =  new  byte[layer.length-lpos+1];  //pos指向当前位置以后的数据长度
		    		        System.arraycopy(layer, lpos, tlayer, 0, tlayer.length);  //如果是 数组类型  pos 当前指向的是数据类型
		    		        String[] value =  twoLayer(length,  tlayer, dataItem698).split(",");
	    		    		sb.append(value[1]).append("|");
		    	    	    lpos = Integer.parseInt(value[0]) + length;
	    	       }else{
	    	    	         data=new  byte[length];//数据
	    		    		 System.arraycopy(layer, lpos, data, 0, length);  //pos已经定位到数据开始的位置
	    		    		 IPharesItem698 phares = Constant698.getiPharesItem698(type); // +"_"+conv  根据成员数据类型 + 该成员对应的换算值 获取数据类型解析类
		    		         phares.setUpbyte(data);
	    	    	         sb.append(phares.getResStr()).append("|");
	    	    	         lpos = lpos + length;
	    	       }
	    	   }
	    	return  String.valueOf(lpos)+",["+sb.toString().substring(0, sb.toString().length()-1)+"]";
		}


	    /**
	     *
	     * @param count    数组数据个数
	     * @param layer    数据
	     * @param dataItem698
	     * @return     一个下标指向 ， 组装的数组数据
	     */
	    private String twoLayer(int count, byte[] layer, DataItem698  dataItem698) {
	    	   int  lpos=0;
	    	   String type;  //数据类型
	    	   int  length;   //数据类型长度
	    	   StringBuffer sb =  new StringBuffer();
	    	   byte  []   data;  //存储要处理的数据
	    	 //获取对象对应的换算值   由于表尚未建立   暂时注释
	   		 //	String   conv =DataItemCache698.getConversion(dataItem698.getObjIdent()+"_0_"+dataItem698.getMemberCode());   //根据对象标识 +成员类型（0：属性；  1：方法）+ 成员编号  获取对象换算值
	    	   for(int  i=0;  i<count; i++){
	    		   type=userCodeType(layer[lpos++]);  //数据类型
	    		   length =Constant698.DATA_TYPE_GET_LEGTH.get(type)==null ? 0 : Integer.parseInt(Constant698.DATA_TYPE_GET_LEGTH.get(type));//数据类型长度
	    	       if(length==0){
	    	    	   length =  Integer.parseInt(byteToHex(layer[lpos++]), 16);  //获取数据长度，并将下标定位到数据或数据类型【数组】开始的位置
	    	       }
	    	       data=new  byte[length];//数据
	    		   System.arraycopy(layer, lpos, data, 0, length);  //pos已经定位到数据开始的位置
	    		   IPharesItem698   phares = Constant698.getiPharesItem698(type); // +"_"+conv  根据成员数据类型 + 该成员对应的换算值 获取数据类型解析类
		    	   phares.setUpbyte(data);
	    	       sb.append(phares.getResStr()).append("|");
	    	       lpos = lpos + length;
	    	   }
	    	return  String.valueOf(lpos)+",["+sb.toString().substring(0, sb.toString().length()-1)+"]";
		}

		private   void  parseProxyRecord(byte []  bytes,  Frame698  frame698){
	    	byte  []   bdata ;
	    	//获取目标服务器地址长度
	    	int  len  =  Integer.parseInt(byteToHex(frame698.getLinkUserData()[0]), 16);
	    	bdata  =  new   byte[bytes.length-len-1];  //len目标服务器的长度  -1 长度本身所占字节
	    	 //去掉目标服务器地址
	    	System.arraycopy(bytes, len+1, bdata, 0, bdata.length);
	    	parseRecord(bdata, 1, frame698);
	    }

	    //解析 代理设置多个服务器的多个对象属性
	    private List<DataItem698> parseProxySetParam(byte  []  bytes, int  count){
	    	List<DataItem698> dataItem =  new ArrayList<DataItem698>();
	    	DataItem698   dataItem698;
	    	int   pos =0  ; //bytes数组下标
	    	int   len =0;  //代理服务器地址长度
	    	byte  []   tsa ;  //存储代理服务器地址
	    	int   cou =0;  //某一个代理服务器设置对象个数
	    	String onetsa ; //代理服务器地址
	    	byte  []  oad  =  new  byte[4];  //OAD
	    	for(int  i=0; i<count; i++){     //循环代理服务器 开始
	    		len   =   Integer.parseInt(byteToHex(bytes[pos++]), 16);    //代理服务器地址长度、  将下标定位到地址数据开始的位置
	    		tsa  =  new  byte[len];
	    		System.arraycopy(bytes, pos, tsa, 0, len);
	    		pos =  pos +  len;  //将下标定位到某一个服务器代理设置对象个数
	    		cou  = Integer.parseInt(byteToHex(bytes[pos++]), 16);   //cou  一个服务器设置对象个数   、 将下标定位到OAD开始的位置
	    		onetsa  =  CommUtils.byteToHexString(tsa);
	    		for(int  j=0;  j<cou;  j++){    //循环某一个服务器设置对象  开始
	    			  dataItem698  =  new  DataItem698();
	    			  dataItem698.setTsa(onetsa);   //设置代理服务器地址
		    		  System.arraycopy(bytes, pos, oad, 0, 4);   //oad
		  			  parserOad(oad, dataItem698);
		  			  pos = pos + 4;  //将下标定位到设置结果的位置
		  			  dataItem698.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos]), 16))));  //设置结果
		  			  pos += 1;
		    		  dataItem.add(dataItem698);
	    		}  //循环某一个服务器设置对象  结束
	    	}   //循环代理服务器  结束
	    	return   dataItem;
	    }
	    //代理设置后读取多个服务器的多个对象属性
	    private List<DataItem698> parseProxySetAndReadParam(byte  []  bytes, int  count){
	    	List<DataItem698> dataItem =  new ArrayList<DataItem698>();
	    	DataItem698   dataItem698;  //设置结果
	    	DataItem698   rdataItem698; //读取结果
	    	int   pos =0  ; //bytes数组下标
	    	int   len =0;  //代理服务器地址长度
	    	byte  []   tsa ;  //存储代理服务器地址
	    	int   cou =0;  //某一个代理服务器设置对象个数
	    	String onetsa ; //代理服务器地址
	    	byte  []  oad  =  new  byte[4];  //设置对象属性的OAD
	    /*	String  result  =""; //读取对象属性结果
	    	String  type ="";  //读取对象属性的属性数据类型
	    	String  length ="";  //读取对象属性的数据类型长度
	    	byte  []  rbyte ;    //存储 返回数据
			byte  []   mbyte = new  byte[1]  ;  //数据长度
*/			StringBuffer sb= new StringBuffer();
	    	for(int  i=0; i<count; i++){     //循环代理服务器 开始
	    		len   =   Integer.parseInt(byteToHex(bytes[pos++]), 16);    //代理服务器地址长度、  将下标定位到地址数据开始的位置
	    		tsa  =  new  byte[len];
	    		System.arraycopy(bytes, pos, tsa, 0, len);
	    		pos =  pos +  len;  //将下标定位到某一个服务器代理设置对象个数
	    		cou  = Integer.parseInt(byteToHex(bytes[pos++]), 16);   //cou  一个服务器设置对象后读取属性个数   、 将下标定位到OAD开始的位置
	    		onetsa  =  CommUtils.byteToHexString(tsa);
	    		for(int  j=0;  j<cou;  j++){    //循环某一个服务器设置对象  开始
	    			  dataItem698  =  new  DataItem698();
	    			  dataItem698.setTsa(onetsa);   //设置代理服务器地址
		    		  System.arraycopy(bytes, pos, oad, 0, 4);   //oad
		  			  parserOad(oad, dataItem698);
		  			  dataItem698.setOad(CommUtils.byteToHexString(oad));  // 获取到设置对象属性描述符
		  			  pos = pos + 4;
		  			  dataItem698.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16))));  //设置结果，将下标定位到读取对象属性的位置
		  			  dataItem.add(dataItem698);
		  			  //读取对象属性值
		  			  rdataItem698 =  new  DataItem698();
					  if(0x00==bytes[pos+4]){  //数据访问结果  等于00  说明访问结果出错
						  pos  += 5;  //错误信息
						  rdataItem698.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16)))) ;  //pos++ 将下标定位到下一个对象标识
					  }else{
						    System.arraycopy(bytes, pos, oad, 0, 4);
						    parserOad(oad, rdataItem698);
						    rdataItem698.setOad(CommUtils.byteToHexString(oad));  // 获取到读取对象属性描述符
						    pos+=5;     //下标定位到数据类型位置
						    rdataItem698.setDataType(userCodeType(bytes[pos++])); //数据类型   ------pos++定位到（长度  或 数据）
						    sb.delete(0, sb.length());
							if("structure".equals(rdataItem698.getDataType())||"array".equals(rdataItem698.getDataType())){
							    int	data=bytes[pos++];  // 结构体包含元素个数  或 数组长度
								pos=ParserUpdata.handlerStructureOrArrayUnit(bytes, data, pos, sb, rdataItem698);
							}else{
								pos=ParserUpdata.handlerCommUnit(bytes,pos,sb,rdataItem698.getDataType(), rdataItem698);
							}
							rdataItem698.setUpValue(sb.toString().substring(1));
							dataItem.add(rdataItem698);


		  			  /*//读取对象属性   pos下标已经定位到读取对象属性OAD的开始位置
				  			  if(0x00==bytes[pos+4]){  //数据访问结果  等于00  说明访问结果出错
								  pos  += 5;  //错误信息
								  result = Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16)));  //pos++ 将下标定位到下一个对象标识
							  }else{
								  type  = userCodeType(bytes[pos+5]); //获取数据类型
								  length =  Constant698.DATA_TYPE_GET_LEGTH.get(type); //根据数据类型（获取读取属性的数据类型）获取数据长度
								  if(null==length){   //说明没有固定长度，说明数据类型的下一个字节为数据长度
									    if("array".equals(type)){
									    	int  arrlen = Integer.parseInt(byteToHex(bytes[pos+6]), 16);   //数组长度   pos定位到数组中元素的数据类型开始的位置
									    	int  index = pos+7 ;  //index 表示数组中数据类型开始的下标
									    	String  arrType;   //数组中的数据类型
									    	int   arrlength;  //数组中数据的长度
									    	int   arrTotlen=0; //记录数据的总长度
									    	for(int  k=0; k<arrlen;  k++){  //获取到数据的长度
									    		arrType = userCodeType(bytes[index]);  //数组中数据类型
									    		arrlength  =  Constant698.DATA_TYPE_GET_LEGTH.get(arrType)==null ? 0 :Integer.parseInt(Constant698.DATA_TYPE_GET_LEGTH.get(arrType)) ;
									    	    if(arrlength ==0){  //说明长度不固定
									    	    	arrlength  =  Integer.parseInt(byteToHex(bytes[index+1]), 16);//获取数据长度
									    	    	arrTotlen += arrlength+1+1; // 累加数据长度   +1数据类型 +1数据长度   arrlength数据长度
									    	    	index  = arrlength+1;  //将下标定位到下一个数据类型的位置   +1数据类型   arrlength数据长度
									    	    }else{
									    	    	arrTotlen += arrlength+1; // 累加数据长度   +1数据类型   arrlength数据长度
									    	    	index  += arrlength+1;  //将下标定位到下一个数据类型的位置   +1数据类型   arrlength数据长度
									    	    }
									    	}
									    	rbyte =new  byte[4+1+1+1+arrTotlen];   //对象标识 4、 结果1、 数据类型  1 、  长度 1、  数据  arrTotlen
									    	System.arraycopy(bytes, pos, rbyte, 0, rbyte.length);
									    	result	= parser(rbyte);   //解析读取的返回值    rbyte(对象标识 4、 结果1、  数据类型  1 、  长度 1、  数据  )
											pos =pos + 7 + arrTotlen ;  //将pos  定位到下一个设置对象属性OAD开始的位置  [ +7 （对象标识 4、结果1  数据类型 1 、 长度 1 ）  +arrTotlen (读取数据的长度)]
									    }else{
									    	 mbyte[0] =bytes[pos+6];   //数据长度
											 int  ldata  = Integer.parseInt(CommUtils.byteToHexString(mbyte), 16) ;
											 rbyte  =  new  byte[4+1+1+1+ldata];   //对象标识 4、 结果1、 数据类型  1 、  长度 1、  数据  ldata
											 System.arraycopy(bytes, pos, rbyte, 0, rbyte.length);
											 result	= parser(rbyte);   //解析读取的返回值    rbyte(对象标识 4、 结果1、  数据类型  1 、  长度 1、  数据  )
											 pos =pos + 7 + ldata ;  //将pos  定位到下一个设置对象属性OAD开始的位置  [ +7 （对象标识 4、结果1  数据类型 1 、 长度 1 ）  +ldata (读取数据的长度)]
									    }
								  }else{  //有固定长度
										rbyte  =  new  byte[4+1+1+Integer.parseInt(length)];   //对象标识 4、  结果1、数据类型  1 、  数据  ldata
									    System.arraycopy(bytes, pos, rbyte, 0, rbyte.length);
									    result	= parser(rbyte);   //解析读取的返回值      rbyte(对象标识 4、  数据类型  1 、  数据  )
									    pos = pos +6 +Integer.parseInt(length);  //将pos  定位到下一个设置对象属性OAD开始的位置  +5 [对象标识+4、结果1、数据类型+1]  len  数据长度
								  }*/
							  }
	    		}  //循环某一个服务器设置对象  结束
	    	}   //循环代理服务器  结束
	    	return   dataItem;
	    }

	  //代理操作多个服务器的多个对象方法
	    private List<DataItem698> parseProxyOptMethod(byte  []  bytes, int  count){
	    	List<DataItem698> dataItem =  new ArrayList<DataItem698>();
	    	DataItem698   dataItem698 ; //操作结果
	    	int  pos =0  ;  //bytes字节数组下标
	    	int  len  =0 ; //代理服务器地址长度
	    	byte []  tsa ; //代理服务器地址
	    	int  cou = 0 ;   //某一个服务器代理的操作对象个数
	    	String onetsa ;  //服务器地址
	    	byte  []   omd =  new  byte[4];  //对象方法描述符
	    	for(int  i=0;  i<count;  i++){    //循环代理服务器地址   开始
	    		len  =    Integer.parseInt(byteToHex(bytes[pos++]), 16);   //代理服务器地址长度，  将下标定位到地址数据开始的位置
	    	    tsa  =  new  byte[len];
	    		System.arraycopy(bytes, pos, tsa, 0, len);
	    		onetsa  =  CommUtils.byteToHexString(tsa);  //代理服务器地址
	    		pos  =  pos  +  len ;  //将下标定位到 某一个服务器代理操作对象个数
	    		cou  =  Integer.parseInt(byteToHex(bytes[pos++]), 16);   //一个服务器代理操作对象个数
	    		for(int j=0;  j<cou;  j++){   //循环代理服务器操作对象  开始
	    			  dataItem698  =  new  DataItem698();
	    			  dataItem698.setTsa(onetsa);   //代理服务器地址
	    			  System.arraycopy(bytes, pos, omd, 0, 4);
	    			  parserOmd(omd, dataItem698);//解析omd
	    			  dataItem698.setOad(CommUtils.byteToHexString(omd));  // 获取到设置对象属性描述符
	    			  pos +=4; //将下标定位到  DAR
	    			  dataItem698.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16))));  //DAR数据
	    			  if(bytes[pos]==0x00){  //没有数据返回
	    				   pos +=1;  //将下标向下移到下一个标识
	    			  }else{
	    				    //数据类型   【长度】   数据
		  				    pos+=1;     //下标定位到数据类型位置
		  				    dataItem698.setDataType(userCodeType(bytes[pos++])); //数据类型   ------pos++定位到（长度  或 数据）
		  				    StringBuffer sb =new StringBuffer();
		  					if("structure".equals(dataItem698.getDataType())||"array".equals(dataItem698.getDataType())){
		  					    int	data=bytes[pos++];  // 结构体包含元素个数  或 数组长度
		  						pos=ParserUpdata.handlerStructureOrArrayUnit(bytes, data, pos, sb, dataItem698);
		  					}else{
		  						pos=ParserUpdata.handlerCommUnit(bytes,pos,sb,dataItem698.getDataType(), dataItem698);
		  					}
		  					dataItem698.setUpValue(dataItem698.getUpValue()+"["+sb.toString().substring(1)+"]");
	    			  }
                       dataItem.add(dataItem698);
	    		} //循环代理服务器操作对象  结束
	    	}  //循环代理服务器地址  结束
	    	return   dataItem;
	    }

	  //代理操作后读取多个服务器的多个对象方法和属性
	    private List<DataItem698> parseProxyOptAndReadParam(byte  []  bytes, int  count){
	    	List<DataItem698> dataItem =  new ArrayList<DataItem698>();
		    	DataItem698   dataItem698 ; //操作结果
		    	DataItem698   rdataItem698;  //读取属性结果
		    	int  pos =0  ;  //bytes字节数组下标
		    	int  len  =0 ; //代理服务器地址长度
		    	byte []  tsa ; //代理服务器地址
		    	int  cou = 0 ;   //某一个服务器代理的操作对象个数
		    	String onetsa ;  //服务器地址
		    	//String   result ="";   //读取对象属性结果
		    	//String   type ="";  //读取对象属性的数据类型
		    	byte  []   omd =  new  byte[4];  //对象方法描述符
		    	//String  length="";
		    	//byte  []  rbyte ;    //存储 返回数据
				//byte  []   mbyte = new  byte[1]  ;  //数据长度
				byte  []   oad  = new  byte[4];
				StringBuffer sb =new StringBuffer();  //存储操作方法返回数据 或 读取数据
		    	for(int  i=0;  i<count;  i++){    //循环代理服务器地址   开始
		    		len  =    Integer.parseInt(byteToHex(bytes[pos++]), 16);   //代理服务器地址长度，  将下标定位到地址数据开始的位置
		    	    tsa  =  new  byte[len];
		    		System.arraycopy(bytes, pos, tsa, 0, len);
		    		onetsa  =  CommUtils.byteToHexString(tsa);  //代理服务器地址
		    		pos  =  pos  +  len ;  //将下标定位到 某一个服务器代理操作对象个数
		    		cou  =  Integer.parseInt(byteToHex(bytes[pos++]), 16);   //一个服务器代理操作对象个数
		    		for(int j=0;  j<cou;  j++){   //循环代理服务器操作对象  开始
		    			  dataItem698  =  new  DataItem698();
		    			  dataItem698.setTsa(onetsa);   //代理服务器地址
		    			  System.arraycopy(bytes, pos, omd, 0, 4);
		    			  parserOmd(omd, dataItem698);//解析omd
		    			  dataItem698.setOad(CommUtils.byteToHexString(omd));  // 获取到设置对象属性描述符
		    			  pos +=4; //将下标定位到  DAR
		    			  dataItem698.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16))));  //DAR数据
		    			  if(bytes[pos]==0x00){  //没有数据返回
		    				   pos +=1;  //将下标向下移到下一个标识
		    			  }else{
		    				   //数据类型   【长度】   数据
			  				    pos+=1;     //下标定位到数据类型位置
			  				    dataItem698.setDataType(userCodeType(bytes[pos++])); //数据类型   ------pos++定位到（长度  或 数据）
			  				    sb.delete(0,  sb.length());
			  					if("structure".equals(dataItem698.getDataType())||"array".equals(dataItem698.getDataType())){
			  					    int	data=bytes[pos++];  // 结构体包含元素个数  或 数组长度
			  						pos=ParserUpdata.handlerStructureOrArrayUnit(bytes, data, pos, sb, dataItem698);
			  					}else{
			  						pos=ParserUpdata.handlerCommUnit(bytes,pos,sb,dataItem698.getDataType(), dataItem698);
			  					}
			  					dataItem698.setUpValue(dataItem698.getUpValue()+"["+sb.toString().substring(1)+"]");
		    			  }
		    			  dataItem.add(dataItem698); //操作方法结果
		    			  //将pos下标定位到读取对象属性符OAD的位置
		    			  //读取对象属性值
		    			  //步骤： 1：对象属性描述符  2：结果[数据类型、（长度）、数据结果]
		    			  rdataItem698   =  new  DataItem698();
		    			  if(0x00==bytes[pos+4]){  //数据访问结果  等于00  说明访问结果出错
		    				  pos  += 5;  //错误信息
		    				  rdataItem698.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16))));  //pos++ 将下标定位到下一个对象标识
		    			  }else{
		    				    System.arraycopy(bytes, pos, oad, 0, 4);
							    parserOad(oad, rdataItem698);
							    rdataItem698.setOad(CommUtils.byteToHexString(oad));  // 获取到读取对象属性描述符
							    pos+=5;     //下标定位到数据类型位置
							    rdataItem698.setDataType(userCodeType(bytes[pos++])); //数据类型   ------pos++定位到（长度  或 数据）
							    sb.delete(0, sb.length());
								if("structure".equals(rdataItem698.getDataType())||"array".equals(rdataItem698.getDataType())){
								    int	data=bytes[pos++];  // 结构体包含元素个数  或 数组长度
									pos=ParserUpdata.handlerStructureOrArrayUnit(bytes, data, pos, sb, rdataItem698);
								}else{
									pos=ParserUpdata.handlerCommUnit(bytes,pos,sb,rdataItem698.getDataType(), rdataItem698);
								}
								rdataItem698.setUpValue(sb.toString().substring(1));
								dataItem.add(rdataItem698);

		    				  /*type  = userCodeType(bytes[pos+5]); //获取数据类型
		    				  length =  Constant698.DATA_TYPE_GET_LEGTH.get(type); //根据数据类型（获取读取属性的数据类型）获取数据长度
		    				  if(null==length){   //说明没有固定长度，说明数据类型的下一个字节为数据长度
		    					    if("array".equals(type)){
		    					    	int  arrlen = Integer.parseInt(byteToHex(bytes[pos+6]), 16);   //数组长度   pos定位到数组中元素的数据类型开始的位置
		    					    	int  index = pos+7 ;  //index 表示数组中数据类型开始的下标
		    					    	String  arrType;   //数组中的数据类型
		    					    	int   arrlength;  //数组中数据的长度
		    					    	int   arrTotlen=0; //记录数据的总长度
		    					    	for(int  k=0; k<arrlen;  k++){  //获取到数据的长度
		    					    		arrType = userCodeType(bytes[index]);  //数组中数据类型
		    					    		arrlength  =  Constant698.DATA_TYPE_GET_LEGTH.get(arrType)==null ? 0 :Integer.parseInt(Constant698.DATA_TYPE_GET_LEGTH.get(arrType)) ;
		    					    	    if(arrlength ==0){  //说明长度不固定
		    					    	    	arrlength  =  Integer.parseInt(byteToHex(bytes[index+1]), 16);//获取数据长度
		    					    	    	arrTotlen += arrlength+1+1; // 累加数据长度   +1数据类型 +1数据长度   arrlength数据长度
		    					    	    	index  = arrlength+1;  //将下标定位到下一个数据类型的位置   +1数据类型   arrlength数据长度
		    					    	    }else{
		    					    	    	arrTotlen += arrlength+1; // 累加数据长度   +1数据类型   arrlength数据长度
		    					    	    	index  += arrlength+1;  //将下标定位到下一个数据类型的位置   +1数据类型   arrlength数据长度
		    					    	    }
		    					    	}
		    					    	rbyte =new  byte[4+1+1+1+arrTotlen];   //对象标识 4、 结果1、 数据类型  1 、  长度 1、  数据  arrTotlen
		    					    	System.arraycopy(bytes, pos, rbyte, 0, rbyte.length);
		    					    	result	= parser(rbyte);   //解析读取的返回值    rbyte(对象标识 4、 结果1、  数据类型  1 、  长度 1、  数据  )
		    							pos =pos + 7 + arrTotlen ;  //将pos  定位到下一个设置对象属性OAD开始的位置  [ +7 （对象标识 4、结果1  数据类型 1 、 长度 1 ）  +arrTotlen (读取数据的长度)]
		    					     }else{
		    					    	 mbyte[0] =bytes[pos+6];   //数据长度
		    							 int  ldata  = Integer.parseInt(CommUtils.byteToHexString(mbyte), 16) ;
		    							 rbyte  =  new  byte[4+1+1+1+ldata];   //对象标识 4、 结果1、 数据类型  1 、  长度 1、  数据  ldata
		    							 System.arraycopy(bytes, pos, rbyte, 0, rbyte.length);
		    							 result	= parser(rbyte);   //解析读取的返回值    rbyte(对象标识 4、 结果1、  数据类型  1 、  长度 1、  数据  )
		    							 pos =pos + 7 + ldata ;  //将pos  定位到下一个设置对象属性OAD开始的位置  [ +7 （对象标识 4、结果1  数据类型 1 、 长度 1 ）  +ldata (读取数据的长度)]
		    					    }
		    				   }else{  //有固定长度
		    						rbyte  =  new  byte[4+1+1+Integer.parseInt(length)];   //对象标识 4、  结果1、数据类型  1 、  数据  ldata
		    					    System.arraycopy(bytes, pos, rbyte, 0, rbyte.length);
		    					    result	= parser(rbyte);   //解析读取的返回值      rbyte(对象标识 4、  数据类型  1 、  数据  )
		    					    pos = pos +6 +Integer.parseInt(length);  //将pos  定位到下一个设置对象属性OAD开始的位置  +5 [对象标识+4、结果1、数据类型+1]  len  数据长度
		    				   }*/
		    			   }
		    		} //循环代理服务器操作对象  结束
		    	}  //循环代理服务器地址  结束
	    	return   dataItem;
	    }

	   //解析代理操作透明转发命令
	    private List<DataItem698> parsePaoxyTransForward(byte  []  bytes){ //-----数据从转发端口 OAD开始
	    	List<DataItem698> dataItem =  new ArrayList<DataItem698>();
	    	DataItem698   di  =  new  DataItem698();
	    	//获取 转发端口
	    	byte  []   port  =  new   byte[4];
	    	System.arraycopy(bytes, 0, port, 0, 4);  //前四个字节为数据转发端口 --OAD
	    	parserOad(port,  di);
	    	//第五个字节判断  0：错误信息、  1 返回数据  octet-string
	    	int   index =5 ;
	    	int    length;
	    	if(0x00==bytes[index++]){  //错误信息
				    di.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[index]), 16))));  //获取编号错误信息
			}else if(0x01==bytes[index++]){  //数据-----下标索引定位到数据类型
	    	         //数据类型(octet-string)
				     di.setDataType("octet-string");  //设置数据类型
				     //数据长度
				     index =  index +1;  //将下标定位到数据长度
				     length  = Integer.parseInt(byteToHex(bytes[index++]), 16);//获取数据长度     index  定位到数据
				     //数据
				     byte  []  value  =  new  byte[length];
				     System.arraycopy(bytes, index, value, 0,  length);
				     IPharesItem698   phares = Constant698.getiPharesItem698(di.getDataType()); // +"_"+conv  根据成员数据类型 + 该成员对应的换算值 获取数据类型解析类
	    		     phares.setUpbyte(value);
	    		     di.setUpValue(phares.getResStr());
			}
	    	dataItem.add(di);
	    	return  dataItem;
	    }
	//解析OAD    bytes  OAD数据
	  private   void   parserOad(byte  []  bytes, DataItem698   dataItem698){
		  byte  []   objId  =new  byte[2] ;   //对象标识
    	  System.arraycopy(bytes, 0, objId, 0, 2);
    	  dataItem698.setObjIdent(CommUtils.byteToHexString(objId));
    	  byte  membercode  = (byte) (bytes[2] & 0x1F);
    	  dataItem698.setMemberCode(String.valueOf(Integer.parseInt(byteToHex(membercode), 16)));   //对象属性编号
    	  byte  fea = (byte)((bytes[2]& 0xE0)>>5);
    	  dataItem698.setObjFeatures(String.valueOf(Integer.parseInt(byteToHex(fea), 16)));    //对象特征
    	  dataItem698.setElementIndex(String.valueOf(Integer.parseInt(byteToHex(bytes[3]), 16)));   //属性元素索引
	  }


	  //根据数据类型编号获取数据类型
	  public String userCodeType(byte b) {
			byte  []   type ={b};
			String dataType ="";   //数据类型
		    String typeCode =	String.valueOf(Integer.parseInt(CommUtils.byteToHexString(type), 16));  //类型编号
			for(Map.Entry<String, String>   entry : Constant698.DATA_TYPE_MAP.entrySet()){    //根据数据类型编号获取数据类型
    	         if(entry.getValue().equals(typeCode)){
    	        	 dataType  = entry.getKey();
    	        	 break;
    	         }
              }
			return  dataType;
     }


	  //一个字节 转换成 16进制
	  private String byteToHex(byte b){
		  byte  []   bit =  {b};
		  String hex =  CommUtils.byteToHexString(bit);
		  return  hex;
	  }

	  //解析读取对象请求    bytes数组从对象标识开始    count  对象个数
	  private    void    getReadRespon(byte []  bytes,  int  count, Frame698 frame698){
		  List<DataItem698> list =  new ArrayList<DataItem698>();
		  int   pos =0;  //指示当前数据下标的位置
		  byte  []  oad  =  new  byte[4];
		  int  data ; //数据长度
		  DataItem698   dataItem698 =null;
		  for(int  i=0; i<count; i++){
			  dataItem698 =  new  DataItem698();
			  System.arraycopy(bytes, pos, oad, 0, 4);  //获取数据标识
			  parserOad(oad,  dataItem698);    //解析对象标识
			  dataItem698.setOad(CommUtils.byteToHexString(oad));  //设置读取的对象属性描述符
			  pos+=4;   //result 标识下标
			  //  RESULT     0  错误信息 DAR   1  数据 DATA
			  if(0x00==bytes[pos]){  //错误信息
				    pos +=1;
				    dataItem698.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16))));  //设置读取结果  并将下标定义到下一个对象标识
			  }else if(0x01==bytes[pos]){  //数据
					pos +=1;     //数据类型下标
					dataItem698.setDataType(userCodeType(bytes[pos++])); //数据类型   ------pos++定位到（长度  或 数据）
					StringBuffer sb=new StringBuffer();
					if("structure".equals(dataItem698.getDataType())||"array".equals(dataItem698.getDataType())){
						data=bytes[pos++];    // 结构体包含元素个数  或 数组长度            pos
						pos=ParserUpdata.handlerStructureOrArrayUnit(bytes,data,pos, sb, dataItem698);
					}else{
						pos=ParserUpdata.handlerCommUnit(bytes,pos,sb,dataItem698.getDataType(), dataItem698);
					}
					dataItem698.setUpValue(sb.toString().substring(1));
			  }
			  list.add(dataItem698);
		  }
		  frame698.setDataItem698(list);
	  }

	  //解析设置请求  报文  bytes  报文数据   count  对象个数
	  private List<DataItem698> getSetRespon(byte  []  bytes, int   count){
		  List<DataItem698> list  = new ArrayList<DataItem698>();
		  int  pos  = 0;  //当前数组下标
		  byte  []  oad = new byte[4];   //存储OAD数据
		  DataItem698  dataItem698 =null;
		  for(int i=0 ; i<count; i++){
			  dataItem698 = new DataItem698();
			  System.arraycopy(bytes, pos, oad, 0, 4);
			  parserOad(oad, dataItem698);
			  dataItem698.setOad(CommUtils.byteToHexString(oad));  // 获取到设置对象属性描述符
			  pos = pos + 4;
			  dataItem698.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos]), 16))));  //设置结果
			  pos += 1;
			  list.add(dataItem698);
		  }
		  return  list;
	  }


	  //解析设置对象属性后读取对象属性  报文  bytes  报文数据（去除了第一个对象标识前面和时间标签以后的数据）   count  对象个数
	  private List<DataItem698> getSetAndReadRespon(byte  []  bytes, int   count){
		  List<DataItem698> list  = new ArrayList<DataItem698>();
		  int  pos  = 0;  //当前数组下标
		  byte  []  oad = new byte[4];   //存储OAD数据
		  DataItem698  dataItem698 =null;  //设置对象属性结果
		  DataItem698  rdataItem698 =null; //读取对象属性结果
		  /*String  type ;   //读取对象属性的数据类型
		  String   len  ;  //读取对象属性数据类型的长度
		  byte  []  rbyte ;    //存储 返回数据
		  byte  []   mbyte = new  byte[1]  ;  //数据长度
*/		  StringBuffer sb =  new StringBuffer();    //暂存读取对象属性的解析结果
		  String result ; // 存储 解析的设置数据结果
		  for(int i=0 ; i<count; i++){
			  sb.delete(0, sb.length());  //去除上一个数据
			  dataItem698 = new DataItem698();
			  System.arraycopy(bytes, pos, oad, 0, 4);
			  parserOad(oad, dataItem698);
			  dataItem698.setOad(CommUtils.byteToHexString(oad));  // 获取到设置对象属性描述符
			  pos = pos + 4;
			 //if(sb.length()==0){
			  result=Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16)));    //设置结果
			//}
			  /*else{
				  sb.append(" ; "+Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16))));    //设置结果
			  }*/
			  dataItem698.setUpValue(result);
			  list.add(dataItem698);  //存储设置对象属性结果
			  //读取对象属性
			  //pos  已经定位到读取对象属性oad开始的位置
			  rdataItem698 =  new  DataItem698();
			  if(0x00==bytes[pos+4]){  //数据访问结果  等于00  说明访问结果出错
				  pos  += 5;  //错误信息
				  result = Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16)));  //pos++ 将下标定位到下一个对象标识
			  }else{
				    System.arraycopy(bytes, pos, oad, 0, 4);
				    parserOad(oad, rdataItem698);
				    rdataItem698.setOad(CommUtils.byteToHexString(oad));  // 获取到读取对象属性描述符
				    pos+=5;     //下标定位到数据类型位置
				    rdataItem698.setDataType(userCodeType(bytes[pos++])); //数据类型   ------pos++定位到（长度  或 数据）
				    sb.delete(0, sb.length());
					if("structure".equals(rdataItem698.getDataType())||"array".equals(rdataItem698.getDataType())){
					    int	data=bytes[pos++];  // 结构体包含元素个数  或 数组长度
						pos=ParserUpdata.handlerStructureOrArrayUnit(bytes, data, pos, sb, rdataItem698);
					}else{
						pos=ParserUpdata.handlerCommUnit(bytes,pos,sb,rdataItem698.getDataType(), rdataItem698);
					}
					rdataItem698.setUpValue(sb.toString().substring(1));
					list.add(rdataItem698);
					  /*type  = userCodeType(bytes[pos+5]); //获取数据类型
					  len =  Constant698.DATA_TYPE_GET_LEGTH.get(type); //根据数据类型（获取读取属性的数据类型）获取数据长度
					  if(null==len){   //说明没有固定长度，说明数据类型的下一个字节为数据长度
						    if("array".equals(type)){
						    	int  arrlen = Integer.parseInt(byteToHex(bytes[pos+6]), 16);   //数组长度   pos定位到数组中元素的数据类型开始的位置
						    	int  index = pos+7 ;  //index 表示数组中数据类型开始的下标
						    	String  arrType;   //数组中的数据类型
						    	int   arrlength;  //数组中数据的长度
						    	int   arrTotlen=0; //记录数据的总长度
						    	for(int  j=0; j<arrlen;  j++){  //获取到数据的长度
						    		arrType = userCodeType(bytes[index]);  //数组中数据类型
						    		arrlength  =  Constant698.DATA_TYPE_GET_LEGTH.get(arrType)==null ? 0 :Integer.parseInt(Constant698.DATA_TYPE_GET_LEGTH.get(arrType)) ;
						    	    if(arrlength ==0){  //说明长度不固定
						    	    	arrlength  =  Integer.parseInt(byteToHex(bytes[index+1]), 16);//获取数据长度
						    	    	arrTotlen += arrlength+1+1; // 累加数据长度   +1数据类型 +1数据长度   arrlength数据长度
						    	    	index  = arrlength+1;  //将下标定位到下一个数据类型的位置   +1数据类型   arrlength数据长度
						    	    }else{
						    	    	arrTotlen += arrlength+1; // 累加数据长度   +1数据类型   arrlength数据长度
						    	    	index  += arrlength+1;  //将下标定位到下一个数据类型的位置   +1数据类型   arrlength数据长度
						    	    }
						    	}
						    	rbyte =new  byte[4+1+1+1+arrTotlen];   //对象标识 4、 结果1、 数据类型  1 、  长度 1、  数据  arrTotlen
						    	System.arraycopy(bytes, pos, rbyte, 0, rbyte.length);
						    	result	= parser(rbyte);   //解析读取的返回值    rbyte(对象标识 4、 结果1、  数据类型  1 、  长度 1、  数据  )
								pos =pos + 7 + arrTotlen ;  //将pos  定位到下一个设置对象属性OAD开始的位置  [ +7 （对象标识 4、结果1  数据类型 1 、 长度 1 ）  +arrTotlen (读取数据的长度)]
						    }else{
						    	 mbyte[0] =bytes[pos+6];   //数据长度
								 int  ldata  = Integer.parseInt(CommUtils.byteToHexString(mbyte), 16) ;
								 rbyte  =  new  byte[4+1+1+1+ldata];   //对象标识 4、 结果1、 数据类型  1 、  长度 1、  数据  ldata
								 System.arraycopy(bytes, pos, rbyte, 0, rbyte.length);
								 result	= parser(rbyte);   //解析读取的返回值    rbyte(对象标识 4、 结果1、  数据类型  1 、  长度 1、  数据  )
								 pos =pos + 7 + ldata ;  //将pos  定位到下一个设置对象属性OAD开始的位置  [ +7 （对象标识 4、结果1  数据类型 1 、 长度 1 ）  +ldata (读取数据的长度)]
						    }
					  }else{  //有固定长度
							rbyte  =  new  byte[4+1+1+Integer.parseInt(len)];   //对象标识 4、  结果1、数据类型  1 、  数据  ldata
						    System.arraycopy(bytes, pos, rbyte, 0, rbyte.length);
						    result	= parser(rbyte);   //解析读取的返回值      rbyte(对象标识 4、  数据类型  1 、  数据  )
						    pos = pos +6 +Integer.parseInt(len);  //将pos  定位到下一个设置对象属性OAD开始的位置  +5 [对象标识+4、结果1、数据类型+1]  len  数据长度
					  }*/
			  }
		  }
		  return  list;
	  }

	  //解析操作请求   报文  报文  bytes  报文数据   count  对象个数
	  private List<DataItem698> getActionRespon(byte  []  bytes, int   count){
		  List<DataItem698> list  = new ArrayList<DataItem698>();
		  int  pos =0;   //定位数组下标
		  byte  []   omd  =  new  byte[4];   //存储 omd  对象方法描述符
		  DataItem698  dataItem698 =null;
		  for(int  i=0; i<count;  i++){
			  dataItem698 =new  DataItem698();
			  System.arraycopy(bytes, pos, omd, 0, 4);
			  parserOmd(omd, dataItem698);//解析omd
			  dataItem698.setOad(CommUtils.byteToHexString(omd));  // 获取到对象方法描述符
			  pos +=4; //将下标定位到  DAR
			  dataItem698.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16))));  //DAR数据
			  //pos  已经定位到是否有数据返回的位置
			  if(bytes[pos]==0x00){  //没有数据返回
				   pos +=1;  //将下标向下移到下一个标识
			  }else{
				    //数据类型   【长度】   数据
				    pos+=1;     //下标定位到数据类型位置
				    dataItem698.setDataType(userCodeType(bytes[pos++])); //数据类型   ------pos++定位到（长度  或 数据）
				    StringBuffer sb =new StringBuffer();
					if("structure".equals(dataItem698.getDataType())||"array".equals(dataItem698.getDataType())){
					    int	data=bytes[pos++];  // 结构体包含元素个数  或 数组长度
						pos=ParserUpdata.handlerStructureOrArrayUnit(bytes, data, pos, sb, dataItem698);
					}else{
						pos=ParserUpdata.handlerCommUnit(bytes,pos,sb,dataItem698.getDataType(), dataItem698);
					}
					dataItem698.setUpValue(dataItem698.getUpValue()+"["+sb.toString().substring(1)+"]");
			  }
			  list.add(dataItem698);
		  }
		  return   list;
	  }

	  //解析操作对象方法后读取对象属性  报文  bytes  报文数据（去除了第一个对象标识前面和时间标签以后的数据）   count  对象个数
	  private List<DataItem698> getActionAndRead(byte[] bytes, int count) {
		  List<DataItem698> list  = new ArrayList<DataItem698>();
		  int  pos =0 ; //当前数组下标
		  byte  []  omd  =  new  byte[4];   //对象方法描述
		 // String  type ;   //读取对象属性的数据类型
		 // String   len  ;  //读取对象属性数据类型的长度
		  StringBuffer sb  =  new StringBuffer();   //存储解析结果
		 // byte  []  rbyte ;    //存储 返回数据
		 // byte  []   mbyte = new  byte[1]  ;  //数据长度
		  DataItem698   dataItem698 = null;
		  DataItem698   rdataItem698 =null;
		 // String   result ;  //暂存读取对象属性的解析结果
		  for(int i=0; i<count;  i++){
			  sb.delete(0, sb.length());
			  dataItem698 = new  DataItem698();
			  System.arraycopy(bytes, pos, omd, 0, 4);
			  parserOmd(omd, dataItem698);//解析omd
			  dataItem698.setOad(CommUtils.byteToHexString(omd));  // 获取到对象方法描述符
			  pos +=4;   //将下标定位到  DAR
			  dataItem698.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16))));//DAR数据
			  //pos  已经定位到是否有数据返回的位置
			  if(bytes[pos]==0x00){  //没有数据返回
				   pos +=1;  //将下标向下移到读取对象属性标识
			  }else{
				    //数据类型   【长度】   数据
				    pos+=1;     //下标定位到数据类型位置
				    dataItem698.setDataType(userCodeType(bytes[pos++])); //数据类型   ------pos++定位到（长度  或 数据）
					if("structure".equals(dataItem698.getDataType())||"array".equals(dataItem698.getDataType())){
					    int	data=bytes[pos++];  // 结构体包含元素个数  或 数组长度
						pos=ParserUpdata.handlerStructureOrArrayUnit(bytes, data, pos, sb, dataItem698);
					}else{
						pos=ParserUpdata.handlerCommUnit(bytes,pos,sb,dataItem698.getDataType(), dataItem698);
					}
					dataItem698.setUpValue(dataItem698.getUpValue()+"["+sb.toString().substring(1)+"]");
			  }
			  list.add(dataItem698);   //存储对象方法操作结果、和返回的数据值
			  //读取对象属性
			  rdataItem698 =  new  DataItem698();
			  //pos  已经定位到读取对象属性oad开始的位置
			  if(0x00==bytes[pos+4]){  //数据访问结果  等于00  说明访问结果出错
				  pos+=5;  //错误信息
				  rdataItem698.setUpValue(Constant698.DATA_ACCESS_RESULT.get(String.valueOf(Integer.parseInt(byteToHex(bytes[pos++]), 16))));//pos++ 将下标定位到下一个对象标识
			  }else{
				    byte  []  oad =  new  byte[4];  //读取对象属性描述符
				    System.arraycopy(bytes, pos, oad, 0, 4);
				    parserOad(oad, rdataItem698);
				    rdataItem698.setOad(CommUtils.byteToHexString(oad));  // 获取到读取对象属性描述符
				    pos+=5;     //下标定位到数据类型位置
				    rdataItem698.setDataType(userCodeType(bytes[pos++])); //数据类型   ------pos++定位到（长度  或 数据）
				    StringBuffer sb1=new StringBuffer();
					if("structure".equals(rdataItem698.getDataType())||"array".equals(rdataItem698.getDataType())){
					    int	data=bytes[pos++];  // 结构体包含元素个数  或 数组长度
						pos=ParserUpdata.handlerStructureOrArrayUnit(bytes, data, pos, sb1, rdataItem698);
					}else{
						pos=ParserUpdata.handlerCommUnit(bytes,pos,sb1,rdataItem698.getDataType(), rdataItem698);
					}
					rdataItem698.setUpValue(sb1.toString().substring(1));
					list.add(rdataItem698);
				 /* type  = userCodeType(bytes[pos+5]); //获取数据类型
				  len =  Constant698.DATA_TYPE_GET_LEGTH.get(type); //根据数据类型（获取读取属性的数据类型）获取数据长度
				  if(null==len){   //说明没有固定长度，说明数据类型的下一个字节为数据长度
					    if("array".equals(type)){
					    	int  arrlen = Integer.parseInt(byteToHex(bytes[pos+6]), 16);   //数组长度   pos定位到数组中元素的数据类型开始的位置
					    	int  index = pos+7 ;  //index 表示数组中数据类型开始的下标
					    	String  arrType;   //数组中的数据类型
					    	int   arrlength;  //数组中数据的长度
					    	int   arrTotlen=0; //记录数据的总长度
					    	for(int  j=0; j<arrlen;  j++){  //获取到数据的长度
					    		arrType = userCodeType(bytes[index]);  //数组中数据类型
					    		arrlength  =  Constant698.DATA_TYPE_GET_LEGTH.get(arrType)==null ? 0 :Integer.parseInt(Constant698.DATA_TYPE_GET_LEGTH.get(arrType)) ;
					    	    if(arrlength ==0){  //说明长度不固定
					    	    	arrlength  =  Integer.parseInt(byteToHex(bytes[index+1]), 16);//获取数据长度
					    	    	arrTotlen += arrlength+1+1; // 累加数据长度   +1数据类型 +1数据长度   arrlength数据长度
					    	    	index  = arrlength+1;  //将下标定位到下一个数据类型的位置   +1数据类型   arrlength数据长度
					    	    }else{
					    	    	arrTotlen += arrlength+1; // 累加数据长度   +1数据类型   arrlength数据长度
					    	    	index  += arrlength+1;  //将下标定位到下一个数据类型的位置   +1数据类型   arrlength数据长度
					    	    }
					    	}
					    	rbyte =new  byte[4+1+1+1+arrTotlen];   //对象标识 4、 结果1、 数据类型  1 、  长度 1、  数据  arrTotlen
					    	System.arraycopy(bytes, pos, rbyte, 0, rbyte.length);
					    	result	= parser(rbyte);   //解析读取的返回值    rbyte(对象标识 4、 结果1、  数据类型  1 、  长度 1、  数据  )
							pos =pos + 7 + arrTotlen ;  //将pos  定位到下一个设置对象属性OAD开始的位置  [ +7 （对象标识 4、结果1  数据类型 1 、 长度 1 ）  +arrTotlen (读取数据的长度)]
					    }else{
					    	 mbyte[0] =bytes[pos+6];   //数据长度
							 int  ldata  = Integer.parseInt(CommUtils.byteToHexString(mbyte), 16) ;
							 rbyte  =  new  byte[4+1+1+1+ldata];   //对象标识 4、 结果1、 数据类型  1 、  长度 1、  数据  ldata
							 System.arraycopy(bytes, pos, rbyte, 0, rbyte.length);
							 result	= parser(rbyte);   //解析读取的返回值    rbyte(对象标识 4、 结果1、  数据类型  1 、  长度 1、  数据  )
							 pos =pos + 7 + ldata ;  //将pos  定位到下一个设置对象属性OAD开始的位置  [ +7 （对象标识 4、结果1  数据类型 1 、 长度 1 ）  +ldata (读取数据的长度)]
					    }
				  }else{  //有固定长度
						rbyte  =  new  byte[4+1+1+Integer.parseInt(len)];   //对象标识 4、  结果1、数据类型  1 、  数据  ldata
					    System.arraycopy(bytes, pos, rbyte, 0, rbyte.length);
					    result	= parser(rbyte);   //解析读取的返回值      rbyte(对象标识 4、  数据类型  1 、  数据  )
					    pos = pos +6 +Integer.parseInt(len);  //将pos  定位到下一个设置对象属性OAD开始的位置  +5 [对象标识+4、结果1、数据类型+1]  len  数据长度
				  }*/
			  }
		  }
		  return list;
	  }
	  //解析omd
	  private    void   parserOmd(byte []  bytes,  DataItem698  dataitem698){
		  byte  []  objid = new  byte[2];  //对象标识
		  System.arraycopy(bytes, 0, objid, 0, 2);
		  dataitem698.setObjIdent(CommUtils.byteToHexString(objid));   //对象标识
		  dataitem698.setMemberCode(String.valueOf(Integer.parseInt(byteToHex(bytes[2]), 16)));        //方法编号
		  dataitem698.setElementIndex(String.valueOf(Integer.parseInt(byteToHex(bytes[2]), 16)));//操作模式
	  }

	  //解析包含对象标识OAD 数据类型  [长度]  读取数据
	  private String parser(byte  []  bytes){
		 // 对象属性描述符
		  byte  [] oad = new  byte[4];
		  //当前下标的位置
		  int  pos=0;
		  //对象属性的数据类型
		  String type;
		  //数据长度
		  int   len;
		  //数据信息
		  byte  []  data;
		  //返回结果
		  StringBuffer result =new StringBuffer();
		  DataItem698   dataItem698 =new DataItem698();  //该对象仅仅用于获取解析的对象属性描述符（OAD）
		  System.arraycopy(bytes, pos, oad, 0, 4);
		  parserOad(oad,  dataItem698);    //解析标识   获取对象标识、读取属性编号
		  //获取对象属性的数据类型
		   type  = userCodeType(bytes[pos+5]);
		 //获取对象对应的换算值
		//   String   conv =DataItemCache698.getConversion(dataItem698.getObjIdent()+"_0_"+dataItem698.getMemberCode());   //根据对象标识 +成员类型（0：属性；  1：方法）+ 成员编号  获取对象换算值
		   len = Constant698.DATA_TYPE_GET_LEGTH.get(type)==null? 0 : Integer.parseInt(Constant698.DATA_TYPE_GET_LEGTH.get(type)); ; //根据数据类型（获取读取属性的数据类型）获取数据长度
		   if(0 == len){   //说明数量类型对应的长度不确定
			    len  = Integer.parseInt(byteToHex(bytes[pos+6]), 16);   //数据长度
			    if("array".equals(type)){   //数据类型为数组
			    	String ntype;  //数据类型
			    	int     nlen;  //数据长度
			    	pos= pos +7;  //将下标定位到第一个数据类型开始的位置
			    	byte  []  nbyte; //存储数据
			    	for(int  i=0; i<len; i++ ){
			    		     ntype= userCodeType(bytes[pos]);
			    		     nlen =  Constant698.DATA_TYPE_GET_LEGTH.get(ntype)==null? 0 : Integer.parseInt(Constant698.DATA_TYPE_GET_LEGTH.get(ntype)); //根据数据类型（获取读取属性的数据类型）获取数据长度
			    	         if(nlen ==0){
			    	        	 nlen = Integer.parseInt(byteToHex(bytes[pos+1]), 16);//获取数据长度
			    	        	 nbyte = new byte[nlen];
			    	        	 System.arraycopy(bytes, pos+2, nbyte, 0, nlen);  //+2   数据类型  、数据长度
			    	        	 pos = pos + 2 + nlen; //第二个 pos下标的位置是指向数据类型  +2 (数据类型、长度)  + nlen(数据长度) -----将下标指向下一个数据类型
			    	         }else{
			    	        	 nbyte = new  byte[nlen];
			    	        	 System.arraycopy(bytes, pos+1, nbyte, 0, nlen);  //pos下标的位置是指向数据类型   +1  将下标定位到数据开始的位置
			    	        	 pos = pos + 1 +nlen;  //第二个 pos下标的位置是指向数据类型  +1 (数据类型)  + nlen(数据长度) -----将下标指向下一个数据类型
			    	         }
			    	         IPharesItem698   phares = Constant698.getiPharesItem698(ntype); // +"_"+conv  根据成员数据类型 + 该成员对应的换算值 获取数据类型解析类
		    	        	 phares.setUpbyte(nbyte);
		    	        	 result.append("[").append(phares.getResStr()).append("]");
			    	        //将下标 pos 定位到下一个数据类型开始的位置
			    	}
			    }else{
				    data   =   new   byte[len];  //pos+5
				    System.arraycopy(bytes, pos+7, data, 0, data.length) ;    //对象标识4、 数据类型1、  长度1
				    IPharesItem698   phares = Constant698.getiPharesItem698(type); // +"_"+conv  根据成员数据类型 + 该成员对应的换算值 获取数据类型解析类
				    phares.setUpbyte(data);
				    result.append(phares.getResStr());    //解析结果
			    }
		   }else{  //数量类型对应的长度确定
			   data  =new  byte[len];
			   System.arraycopy(bytes, pos+6, data, 0, data.length);  //+5  (对象标识、数据类型)
			   IPharesItem698   phares = Constant698.getiPharesItem698(type); //+"_"+conv  根据成员数据类型 + 该成员对应的换算值 获取数据类型解析类
		       phares.setUpbyte(data);
		       result.append(phares.getResStr());    //解析结果
		   }
		  return  result.toString();
	  }

	  //预连接响应
	   private   void   parse81(Frame698 frame698){
		    //响应结果数据--------------结果【一个字节】 、请求时间、  收到时间、 响应时间
		     byte  []   data  =  frame698.getLinkUserData();  //获取链路层用户数据
		     byte  []   time = new  byte[10];      //暂存 响应结果返回的各种时间：【请求时间、  收到时间、 响应时间】
		     StringBuffer result =  new StringBuffer();
		     //获取预连接的响应结果
		     result.append(parseResult(byteToHex(data[2])));
		     // 获取date_time数据类型解析类
		     IPharesItem698   phares = Constant698.getiPharesItem698("date_time");
		     //处理请求时间
		     System.arraycopy(data, 3, time, 0, 10);
       	 phares.setUpbyte(time);
       	 result.append(", 请求时间:"+phares.getResStr());
       	 //收到时间
       	 System.arraycopy(data, 13, time, 0, 10);
       	 phares.setUpbyte(time);
       	 result.append(", 收到时间:"+phares.getResStr());
       	//响应时间
       	 System.arraycopy(data, 23, time, 0, 10);
       	 phares.setUpbyte(time);
       	 result.append(", 响应时间:"+phares.getResStr());
       	 DataItem698   di  =  new  DataItem698();
       	 di.setUpValue(result.toString());
       	 List<DataItem698> ldi =new ArrayList<DataItem698>();
       	 ldi.add(di);
       	 frame698.setDataItem698(ldi);
	   }


	  //处理预连接中返回的结果  --result  结构如下：
	  /* Result∷=bit-string（SIZE（8））
	   {
	     时钟可信标志	（0），
	         保留bit6   	（1），
	         保留bit5         （2），
	         保留bit4         （3），
	         保留bit3         （4），
	     结果bit2         （5），
	     结果bit1         （6），
	     结果bit0         （7）
	   }
	   时钟可信标志——用于表示响应方的时钟是否可信（准确），0：不可信，1：可信。
	   结果bit0…结果bit2——二进制编码表示：0：成功，1：地址重复，2：非法设备，3：容量不足，其它值：保留*/
	   private String parseResult(String result){
		  StringBuffer info = new StringBuffer(); //  返回信息
		   //将数据转化成二进制数据
		   String bin =  Integer.toBinaryString(Integer.parseInt(result, 16));
		   bin  =  "00000000".substring(0, 8-bin.length())+bin;
		   //截取字节的第8位   0 ：不可信   1：可信
		   if("0".equals(bin.substring(0, 1))){
			   info.append("不可信");
		   }else{
			   info.append("可信");
		   }
		   //截取0-2位   0：成功     1：地址重复   2：非法设备   3：容量不足   其他值保留
		   switch(Integer.parseInt(bin.substring(5), 2)){
		   case  0:
			   info.append(",成功");
			   break;
		   case  1:
			   info.append(",地址重复");
			   break;
		   case  2:
			   info.append(",非法设备");
			   break;
		   case  3:
			   info.append(",容量不足");
			   break;
			   default:
				   info.append(", 保留");
		   }
		   return  info.toString();
	   }


	   //解析建立应用连接响应
	   private  void   parse82(Frame698 frame698){
		   //游离下标
		   int   index =  34;  //从服务器厂商版本信息以后的数据下标开始
		   byte  []   temp;  //暂存数据
		   //获取用户链路数据
		   byte  []  data  = frame698.getLinkUserData();
		   //暂存储 服务器厂商版本信息
		   byte  []  factory   =  new  byte[32];
		   System.arraycopy(data, 2, factory, 0, 32);
		   FactoryVersion fv = parseFactVersion(factory);
		   AppConnReq acr  =  new AppConnReq();
		   acr.setFactoryVarsion(fv);   //服务器厂商版本信息
		   //商定的应用层协议版本号    long-unsigned，
		    temp = new byte[2];
		    System.arraycopy(data, index, temp, 0, 2);
		    acr.setVersion(changeData("long_unsigned", temp));
		    index += 2;
		   //商定的协议一致性块        ProtocolConformance，8个字节
		    temp = new byte[8];
		    System.arraycopy(data, index, temp, 0, 8);
		    acr.setProtocal(CommUtils.byteToHexString(temp));
		    index += 8;
		   //商定的功能一致性块        FunctionConformance，16个字节
		    temp = new byte[16];
		    System.arraycopy(data, index, temp, 0, 16);
		    acr.setFunction(CommUtils.byteToHexString(temp));
		    index += 16;
		   //服务器发送帧最大尺寸      long-unsigned，
		    temp = new byte[2];
		    System.arraycopy(data, index, temp, 0, 2);
		    acr.setSendMaxSize(changeData("long_unsigned", temp));
		    index+=2;
		   //服务器接收帧最大尺寸      long-unsigned，
		    temp = new byte[2];
		    System.arraycopy(data, index, temp, 0, 2);
		    acr.setReviceMaxSize(changeData("long_unsigned", temp));
		    index+=2;
		   //服务器接收帧最大窗口尺寸   unsigned，
		    temp = new byte[1];
		    System.arraycopy(data, index, temp, 0, 1);
		    acr.setReviceMaxWinSize(changeData("unsigned", temp));
		    index+=1;
		   //服务器最大可处理APDU尺寸  long-unsigned，
		    temp = new byte[2];
		    System.arraycopy(data, index, temp, 0, 2);
		    acr.setMaxApduSize(changeData("long_unsigned", temp));
		    index+=2;
		   //应用连接超时时间     double-long-unsigned，
		    temp = new byte[4];
		    System.arraycopy(data, index, temp, 0, 4);
		    acr.setLinkOverTime(changeData("double_long_unsigned", temp));
		    index+=4;
		   //连接响应对象              ConnectResponseInfo
		    ConnRespInfo cri =  new ConnRespInfo();
		    //连接响应对象
		    temp = new byte[1];
		    System.arraycopy(data, index, temp, 0, 1);
		    cri.setConnResult(responseObject(byteToHex(temp[0])));
		    index +=1;  //下标定位到认证附加信息
		    System.arraycopy(data, index, temp, 0, 1);
		    if(0x0 ==temp[0]){ //表示没有认证附加信息
		    	acr.setIsInfo("没有认证附加信息");
		    }else {
		    	int   len = 0;
		    	//服务器随机数  RN    octet-string    数据长度
		    	//获取服务器随机数长度
//		    	len  =Integer.parseInt(byteToHex(data[index]), 16);
		    	len  =48;
		    	temp = new byte[len];
		    	index += 2;
		    	System.arraycopy(data, index, temp, 0, len);
		    	cri.setRn(CommUtils.byteToHexString(temp));
		    	index+=len+1;
		    	//服务器签名信息        octet-string
		    	//获取服务器签名信息长度
		    	len  = 4;
		    	temp = new byte[len];
		    	System.arraycopy(data, index, temp, 0, len);
		    	cri.setSignInfo(CommUtils.byteToHexString(temp));
		    }
		    acr.setConnRespInfo(cri);
		    frame698.setAppConnReq(acr);
	   }

	   //应用连接请求认证结果
	   private String responseObject(String value){
		   String result  ="其他错误";
		   switch(Integer.parseInt(value, 16)){
		   case  0:
			   result  ="允许建立应用连接";
		        break;
		   case  1:
			   result  ="密码错误";
			   break;
		   case  2:
			   result  ="对称解密错误";
			   break;
		   case  3:
			   result  ="非对称解密错误";
			   break;
		   case  4:
			   result  ="签名错误";
			   break;
		   case  5:
			   result  ="协议版本不匹配";
			   break;
		   }
		   return  result;
	   }



	   //根据传入的数据类型 和  数据  转换成对应的数据格式
	   private String changeData(String dataType, byte []  value){
		   IPharesItem698   phares = Constant698.getiPharesItem698(dataType);
		   phares.setUpbyte(value);
		   return phares.getResStr();
	   }


	   //解析组装服务器厂商版本信息
	   private   FactoryVersion   parseFactVersion(byte  []  versionInfo){
		   FactoryVersion   fv  = new  FactoryVersion();
		   byte  []   temp;  //暂存数据信息
		   int  index=0;  //游离下标
		   //服务器厂商版本信息        FactoryVersion，
             //厂商代码       visible-string(SIZE (4))，
            temp  =  new  byte[4];
            System.arraycopy(versionInfo, index, temp, 0, 4);
            fv.setFactCode(CommUtils.byteToHexString(temp));   //Utils.bytesToAsciiStr(temp)
            index +=4;
		   //软件版本号     visible-string(SIZE (4))，
            temp  =  new  byte[4];
            System.arraycopy(versionInfo, index, temp, 0, 4);
            fv.setSoftVersion(CommUtils.byteToHexString(temp));  //Utils.bytesToAsciiStr(temp)
            index +=4;
		   //软件版本日期   visible-string(SIZE (6))，
            temp  =  new  byte[6];
            System.arraycopy(versionInfo, index, temp, 0, 6);
            fv.setVersionDate(CommUtils.byteToHexString(temp));  //Utils.bytesToAsciiStr(temp)
            index +=6;
		   //硬件版本号     visible-string(SIZE (4))，
            temp  =  new  byte[4];
            System.arraycopy(versionInfo, index, temp, 0, 4);
            fv.setHardVersion(CommUtils.byteToHexString(temp));  //Utils.bytesToAsciiStr(temp)
            index +=4;
		   //硬件版本日期   visible-string(SIZE (6))，
            temp  =  new  byte[6];
            System.arraycopy(versionInfo, index, temp, 0, 6);
            fv.setHardDate(CommUtils.byteToHexString(temp));    //Utils.bytesToAsciiStr(temp)
            index +=6;
		   //厂家扩展信息   visible-string(SIZE (8))
            temp  =  new  byte[8];
            System.arraycopy(versionInfo, index, temp, 0, 8);
            fv.setFactExtendInfo(CommUtils.byteToHexString(temp));  //Utils.bytesToAsciiStr(temp)
		    return  fv;
	   }

	   //断开应用连接响应
	   private  void    parse83(Frame698 frame698){
		   byte  []   data  =  frame698.getLinkUserData();
		   DataItem698   di  =new  DataItem698();
		   if(0x00 == data[2]){   //结果数据类型 ENUMERATED     0  成功
			    di.setUpValue("应用连接断开成功！");
		   }else{
			   di.setUpValue("应用连接断开失败！");
		   }
		   List<DataItem698> ldi =new ArrayList<DataItem698>();
	       ldi.add(di);
	       frame698.setDataItem698(ldi);
	   }
	   //断开应用连接通知
	   private   void    parse84(Frame698 frame698){
		    byte  []   data  =  frame698.getLinkUserData();
		    byte  []   time   =  new byte[7];
		    StringBuffer sb  =  new StringBuffer();  //存储断开应用连接通知结果数据信息
		    IPharesItem698   phares = Constant698.getiPharesItem698("date_time_s");
		    //应用连接建立时间
		    System.arraycopy(data, 2, time, 0, 7);
		    phares.setUpbyte(time);
		    sb.append("应用连接建立时间:"+ phares.getResStr());
		    //服务器当前时间
		    System.arraycopy(data, 9, time, 0, 7);
		    phares.setUpbyte(time);
		    sb.append("服务器当前时间:"+ phares.getResStr());
		    DataItem698   di  =  new  DataItem698();
	       	di.setUpValue(sb.toString());
	       	List<DataItem698> ldi =new ArrayList<DataItem698>();
	       	ldi.add(di);
	       	frame698.setDataItem698(ldi);
	   }



	//判断返回的上行  报文是执行的什么操作    读取  设置  操作   上报  代理
	private Frame698 getFrame698(byte[] upBytes) {
		Frame698  frame698 =new  Frame698();
		 //698报文存在前到码
		int    count  =0 ;
		for(byte   b : upBytes){
			   if(b == 104){
				   break;
			   }
			   count++;
		}

		byte []   bytes = new byte[upBytes.length - count];
	    System.arraycopy(upBytes, count, bytes, 0, bytes.length);   //将前到码去除  将报文数据放入upBytes数组
		frame698.setFrameCont(bytes);     //698上行报文

		//地址类型
		frame698.setMeterComIdType(byteToHex((byte)(bytes[4]& 0xC0)));
		frame698.setControlCode(bytes[3]);
		//逻辑地址
		frame698.setLogiAdd(byteToHex((byte)(bytes[4]& 0xC0)));
		//地址的字节数，取值范围：0…15，对应表示1…16个字节长度 +1表示将值加1表示地址字节长度+1表示客户机地址
		int  addlen= CommUtils.bcd2Int((byte)(bytes[4] & 0x0F))+1+1;
		byte  []   add  =new  byte[addlen-1];    //-1  减去客户机地址
		System.arraycopy(bytes, 5, add, 0, add.length);
		//帧头校验
		cs698(4+ addlen +1,  bytes);     //  =====服务器地址下标+addlen(地址长度+客户机地址)  +1 表示头校验下标 4 表示服务器地址的下标  固定
		//帧尾校验
		cs698(bytes.length - 3,  bytes);    //上行报文尾校验开始下标
		int   resp  =  4+ addlen +3;    //  +3  上行报文中应用层数据开始的下标
		frame698.setMeterComId(CommUtils.byteToHexString(add));  //地址
		frame698.setObjclassifi(bytes[resp]);  //操作类标识     (0x85)读取		(0x86)设置		(0x87)操作		(0x88)上报	  (0x89)代理	  （0x90）安全传输
		frame698.setOptCount(bytes[resp+1]) ; //操作一个 或多个标识    -------如果是安全传输，该字段属性获取的值是应用数据单元 0 明文  1 密文  2异常错误
		byte []  linkUserData  =new  byte[bytes.length - 3 - resp];   //存储链路用户数据    -3：尾校验下标   -resp应用层数据开始的下标
		System.arraycopy( bytes,resp, linkUserData, 0, bytes.length - 3 - resp);
		frame698.setLinkUserData(linkUserData);

		return frame698;
	}


	/***
	 * 验证帧校验位
	 * index   上行报文中校验位的开始下标
	 * bytes   上行报文
	 */
	public  static   void   cs698(int  index, byte []  bytes){

		byte []  upcs  = new  byte[2] ; //存储上行报文的帧头或帧尾校验
		System.arraycopy(bytes, index, upcs, 0, 2);
		String csValue1  =  CommUtils.byteToHexString(upcs);   //上行报文帧头或帧尾校验值
		byte [] csbyte =new  byte[index+1];   //存储头或尾校验要校验的数据
		for(int  i=1; i<index; i++){
			csbyte[i-1] = bytes[i];
		}
		String csValue2=CommUtils.byteToHexString(CommUtils.hex2Binary(CrcUtil.tryfcs16(csbyte, index-1)));
		if(!csValue1.equals(csValue2)){
			//LogHandler.info(LogHandler.ANALYSIS,"[解析上行698报文]"+CommUtils.byteToHexString(bytes)+"帧校验错误");
		}

	}


	  public  static   void  main(String[]  args){

		  //解析读取报文
		  /*String    v= "FEFEFEFE682100C10500000000000010665085010040000200011C07E00B02120D3300001E7C16";
		  byte  []   b =  CommUtils.hex2Binary(v);
		  Parser698   parser698  =  new  Parser698();
		  Frame698  frame698 = parser698.parser698(b);
		  System.out.println(frame698.getDataItem698().get(0).getUpValue()+"=======");*/

		  /*	  *
		  String   vv="FEFEFEFE682100C10500000000000010665085010040020200010906220000000011000064A416";
		  byte  []   b1 =  CommUtils.hex2Binary(vv);
		  Parser698   parser6981  =  new  Parser698();
		  Frame698  frame6981 = parser6981.parser698(b1);
		  System.out.println(frame6981.getDataItem698().get(0).getUpValue());


		  //解析设置报文
		 String   setv = "FEFEFEFE682100C1050000000000001066508602030240000200004001020001000064A416";
		 byte []  s1 = CommUtils.hex2Binary(setv);
		 Parser698   parser6981  =  new  Parser698();
		 Frame698  frame6981 = parser6981.parser698(s1);
		 for(DataItem698  dataItem698 : frame6981.getDataItem698()){
			 System.out.println(dataItem698.getUpValue());
		 }*/

		 //操作方法后读取属性----
		/* String   info  = "00100100000000100200010105060000000006000000000600000000060000000006000000000000";
		 byte  []  in1 = CommUtils.hex2Binary(info);
		 Parser698   p1 =new  Parser698();
		 p1.getActionAndRead(in1, 1);*/


		 //解析读取多个对象属性
		/* String    s  ="2000020001010312096D12096D12096D2001020001010305000003E805000003E805000003E8";
		 byte   []   s1 = CommUtils.hex2Binary(s);
		 Parser698   ss1 =new  Parser698();
		 Frame698  fr =new Frame698();
		 ss1.getReadRespon(s1, 2, fr); */

		//解析复杂类型
		/*	 String    s  ="60 00 02 00 01 01 03 02 04 12 00 01 02 0A 55 06 04 00 00 00 22 21 16 03 16 03 51 F2 09 02 01 09 06 00 00 00 00 00 00 11 04 11 04 16 01 12 08 98 12 00 0F 02 04 55 06 00 00 00 00 00 00 09 06 00 00 00 00 00 00 12 00 01 12 00 01 01 00 02 04 12 00 02 02 0A 55 06 04 00 00 00 22 22 16 03 16 03 51 F2 09 02 01 09 06 00 00 00 00 00 00 11 04 11 04 16 01 12 08 98 12 00 0F 02 04 55 06 00 00 00 00 00 00 09 06 00 00 00 00 00 00 12 00 01 12 00 01 01 00 02 04 12 00 03 02 0A 55 06 04 00 00 00 22 23 16 03 16 03 51 F2 09 02 01 09 06 00 00 00 00 00 00 11 04 11 04 16 01 12 08 98 12 00 0F 02 04 55 06 00 00 00 00 00 00 09 06 00 00 00 00 00 00 12 00 01 12 00 01 01 00 00 00";
			 s=s.replace(" ", "");
			 byte   []   s1 = CommUtils.hex2Binary(s);
			 Parser698   ss1 =new  Parser698();
			 Frame698  fr =new Frame698();
			 ss1.getReadRespon(s1, 1, fr);
			 System.out.println(fr.getDataItem698().size()+"=======");
			 System.out.println(fr.getDataItem698().get(0).getUpValue()+"=======");*/

		  //解析预连接 响应报文
		 /* String    v= "683000010507091905162010665081008007E0051304080500008907E0051304080501025F07E005130408050202DA665016";
		  byte  []   b =  CommUtils.hex2Binary(v);
		  Parser698   parser698  =  new  Parser698();
		  Frame698  frame698 = parser698.parser698(b);
		  System.out.println(frame698.getDataItem698().get(0).getUpValue()+"=======");*/

		  //解析读取记录型对象
		//  String   v ="50 04 02 00 02 00 20 21 02 00 00 00 10 02 00 01 01 1C 07 E0 01 14 00 00 00 01 05 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 00 00";
	      String v ="60 12 03 00 05 00 40 01 02 00 00 60 40 02 00 00 60 41 02 00 00 60 42 02 00 01 50 04 "+
	      " 02 00 02 00 10 02 00 00 20 02 00 01 05 09 05 00 00 00 01 21 1C 07 E0 01 14 00 00 00 1C 07 E0 "+
	      " 01 14 00 00 00 1C 07 E0 01 14 00 00 00 01 02 01 05 06 00 00 00 00 06 00 00 00 00 06 00 00 00 "+
	      " 00 06 00 00 00 00 06 00 00 00 00 01 05 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 "+
	      " 00 00 06 00 00 00 00 09 05 00 00 00 01 22 1C 07 E0 01 14 00 00 00 1C 07 E0 01 14 00 00 00 1C "+
	      " 07 E0 01 14 00 00 00 01 02 01 05 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00  "+
	      " 06 00 00 00 00 01 05 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 "+
	      " 00 09 05 00 00 00 01 23 1C 07 E0 01 14 00 00 00 1C 07 E0 01 14 00 00 00 1C 07 E0 01 14 00 00 "+
	      " 00 01 02 01 05 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 01 "+
	      " 05 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 09 05 00 00 00 "+
	      " 01 24 1C 07 E0 01 14 00 00 00 1C 07 E0 01 14 00 00 00 1C 07 E0 01 14 00 00 00 01 02 01 05 06 "+
	      " 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 01 05 06 00 00 00 00 "+
	      " 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 09 05 00 00 00 01 25 1C 07 E0 01  "+
	      " 14 00 00 00 1C 07 E0 01 14 00 00 00 1C 07 E0 01 14 00 00 00 01 02 01 05 06 00 00 00 00 06 00  "+
	      " 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 01 05 06 00 00 00 00 06 00 00 00 00 06 "+
	      " 00 00 00 00 06 00 00 00 00 06 00 00 00 00 00 00 ";
		  v = v.replace(" ", "");
	      byte  []  v1 = CommUtils.hex2Binary(v);
	      Parser698   ss1 =new  Parser698();
	      Frame698  fr =new Frame698();
	      ss1.parseRecord(v1, 1, fr);
	      for(DataItem698  di :  fr.getDataItem698()){
	    	  System.out.println(di.getUpValue());
	      }

	  }
}
