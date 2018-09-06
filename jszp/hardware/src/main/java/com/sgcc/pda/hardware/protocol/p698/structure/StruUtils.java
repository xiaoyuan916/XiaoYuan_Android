package com.sgcc.pda.hardware.protocol.p698.structure;


import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.RecordRcsdBean;
import com.sgcc.pda.hardware.protocol.p698.bean698.Region;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//structure数据类型工具
public class StruUtils {
	
	private    byte  []  bytes = new byte[1024]; 
	private    int   pos = 0;
	 //根据数据类型 获取数据
	public  static  byte []  assValue(DataItem698 dataItem698){
		 IPharesItem698 phares = Constant698.getiPharesItem698(dataItem698.getDataType());
	   	 phares.setInputValue(dataItem698.getInputParam());
		 return   phares.getBytes();
	}

	//获取数据类型值
	public  static    byte[]   getObjDataType (DataItem698 dataItem698){
   	     return  CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(dataItem698.getDataType()))));
    }
	
	
	//组装CSD 数据类型数据
	public     byte []   optRcsd(RecordRcsdBean rcsd){
		   
		   List<Map<String, List<DataItem698>>> list  =  rcsd.getRecordRcse();
		   for(Map<String,   List<DataItem698>> map : list){
			     bytes[pos++] =   assDataType("CSD");
				   for(Map.Entry<String, List<DataItem698>>   entry :  map.entrySet()){
						     if("00".equals(entry.getKey())){    //对象属性描述符
							    	 bytes[pos++]=0x00;
							    	 assObjData(entry.getValue().get(0));    //对象属性描述符 OAD
						     }
						     if("01".equals(entry.getKey())){   //记录型对象属性描述符
							    	 bytes[pos++]=0x01;
							    	 List<DataItem698> item =  entry.getValue();
							    	 //记录型对象属性  标识
							    	 assObjData(item.get(0));   
							    	 //移除第一个记录型对象
							    	 item.remove(0);    //----第一个对象为记录型对象，后面的为记录型对象关联的对象
							    	 //关联对象个数
							    	 bytes[pos++]  =  CommUtils.hex2Binary(Integer.toHexString(item.size()))[0];
							    	 //关联对象
							    	 for(DataItem698    data :  item){
							    		 assObjData(data); 
							    	 }
						     }
				   }
		   }
		   
		   byte   []  reValue = getByteData(bytes ,  pos);
		   pos = 0;
		   Arrays.fill(bytes, (byte)0);
		   return    reValue;
	}
	
	
	
	
	//处理电能表集合 MS      
	/**
	 * ms   表示电能表集合编号  0-4
	 * list  表示集合数据
	 */
	public   byte []   optionMs(String ms, List<String> list){
		   switch(Integer.parseInt(ms)){
			   case  0:   //无电能表
				   bytes[pos++] =0x00;
				   break;
			   case  1:  //全部用户地址
				   bytes[pos++] =0x01;
				   break;
			   case  2:   //一组用户类型   unsigned   8位 一个字节
				   bytes[pos++] =0x02;
				   //个数
				   bytes[pos++]  =  CommUtils.hex2Binary(Integer.toHexString(list.size()))[0]; //pos已经定位到TSA第一个数据长度开始的位置
				   //数据类型 +  数据
				   DataItem698  di=null;
				   for(String value :  list){
					   di =  new  DataItem698();
					   di.setDataType("unsigned");
					   di.setInputParam(value);
					   assObjDataType(di);//组装数据类型
					   assObjValue(di);   //组装数据
				   }
				   break;
			   case  3:   //一组用户地址   TSA
				   bytes[pos++] =0x03;
				   //个数
				   bytes[pos++]  =  CommUtils.hex2Binary(Integer.toHexString(list.size()))[0]; //pos已经定位到TSA第一个数据长度开始的位置
				   //TSA
				   byte  []  btsa;
				   for(String tsa: list){
					      bytes[pos++]  =  CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("TSA"))))[0];
					  //整个长度 +地址长度+ 数据
					   //代理服务器地址    TSA
			    		  btsa =  new  byte[tsa.length()/2+1+1];  //+1数据长度  +1 地址信息长度
			    		  btsa[0] = CommUtils.hex2Binary(Integer.toHexString(tsa.length()/2+1))[0];  //地址数据 +  地址长度
			    		  btsa[1] = (byte) (0x0F&CommUtils.hex2Binary(Integer.toHexString(tsa.length()/2-1))[0]);  //地址数据长度
			    		  System.arraycopy(CommUtils.hex2Binary(tsa), 0, btsa, 2, btsa.length-2);
			    		  System.arraycopy(btsa, 0, bytes, pos, btsa.length);     // 将TSA数据放入bytes中
			    		  pos = pos + btsa.length;  //将下标定位到一个服务器超时时间
				   }
				   break;
			   case  4:   //一组配置序号     long-unsigned   64位  8个字节
				   bytes[pos++] =0x04;
				   //个数
				   bytes[pos++]  =  CommUtils.hex2Binary(Integer.toHexString(list.size()))[0]; //pos已经定位到TSA第一个数据长度开始的位置
				   //数据类型 +  数据
				   DataItem698  dit=null;
				   for(String value :  list){
					   dit =  new  DataItem698();
					   dit.setDataType("long-unsigned");
					   dit.setInputParam(value);
					   assObjDataType(dit);//组装数据类型
					   assObjValue(dit);   //组装数据
				   }
				   break;
		   }
		   byte   []  reValue = getByteData(bytes ,  pos);
		   pos = 0;
		   Arrays.fill(bytes, (byte)0);
		   return    reValue;
	}
	
	//处理电能表集合 MS      
	/**
	 * ms   表示电能表集合编号  5-7
	 * list  表示区间集合数据
	 */
	public   byte []   optionMsRegion(String ms, List<Region> list){
		   switch(Integer.parseInt(ms)){
			   case  5:   //一组用户类型区间   用户类型   unsigned   8位 一个字节
				   bytes[pos++] =0x05;
				   //个数
				   bytes[pos++]  =  CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];
				   //组装用户类型区间数据
				   //数据类型Region
				   for(Region   r : list){
					  assRegion(r,  "unsigned");
				   }
				   break;
			   case  6:    //一组用户地址区间   用户地址   TSA
				   bytes[pos++] =0x06;
				      //组装用户地址区间数据
				   //个数
				   bytes[pos++]  =  CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];
				   //组装用户类型区间数据
				   //数据类型Region
				   for(Region   r : list){
					  assRegion(r,  "TSA");
				   }
				   break;
			   case  7:    //一组配置序号区间     序号     long-unsigned   64位  8个字节
				   bytes[pos++] =0x07;
				  //组装配置序号区间数据
				   //个数
				   bytes[pos++]  =  CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];
				   //组装用户类型区间数据
				   //数据类型Region
				   for(Region   r : list){
					  assRegion(r,  "long-unsigned");
				   }
				   break;
			            
		   }
		   byte   []  reValue = getByteData(bytes ,  pos);
		   pos = 0;
		   Arrays.fill(bytes, (byte)0);
		   return    reValue;
	}
	
	//组装区间数据类型
	private   void    assRegion(Region  r,  String type){
		   DataItem698   di =  new  DataItem698();
		   di.setDataType("Region");   //区间类型
		   assObjDataType(di);//组装区间数据类型
		   //组装枚举数据类型  、 枚举单位数据值
		      di.setDataType("enum");  
		      di.setInputParam(r.getUnit());
		      assObjDataType(di);//组装枚举数据类型
		      assObjValue(di);   //组装枚举数据值
		 if("TSA".equals(type)){  //TSA----目标服务器地址
			      //整个长度 +地址长度+ 数据
			      //代理服务器地址    TSA
			      di.setDataType("TSA");  
			      assObjDataType(di);//组装枚举数据类型
			      //起始值
			      byte  []  btsa;
	    		  btsa =  new  byte[r.getBeginData().length()/2+1+1];  //+1数据长度  +1 地址信息长度
	    		  btsa[0] = CommUtils.hex2Binary(Integer.toHexString(r.getBeginData().length()/2+1))[0];  //地址数据 +  地址长度
	    		  btsa[1] = (byte) (0x0F&CommUtils.hex2Binary(Integer.toHexString(r.getBeginData().length()/2-1))[0]);  //地址数据长度
	    		  System.arraycopy(CommUtils.hex2Binary(r.getBeginData()), 0, btsa, 2, btsa.length-2);
	    		  System.arraycopy(btsa, 0, bytes, pos, btsa.length);     // 将TSA数据放入bytes中
	    		  pos = pos + btsa.length;  //将下标定位到一个服务器超时时间
	    		  di.setDataType("TSA");  
			      assObjDataType(di);//组装枚举数据类型
	    		  //结束值
	    		  btsa =  new  byte[r.getEndData().length()/2+1+1];  //+1数据长度  +1 地址信息长度
	    		  btsa[0] = CommUtils.hex2Binary(Integer.toHexString(r.getEndData().length()/2+1))[0];  //地址数据 +  地址长度
	    		  btsa[1] = (byte) (0x0F&CommUtils.hex2Binary(Integer.toHexString(r.getEndData().length()/2-1))[0]);  //地址数据长度
	    		  System.arraycopy(CommUtils.hex2Binary(r.getEndData()), 0, btsa, 2, btsa.length-2);
	    		  System.arraycopy(btsa, 0, bytes, pos, btsa.length);     // 将TSA数据放入bytes中
	    		  pos = pos + btsa.length;  //将下标定位到一个服务器超时时间
	    		  btsa =  null;
		 }else{
			//起始值
		      di.setDataType(type); 
		      di.setInputParam(r.getBeginData());
		      assObjDataType(di);//组装数据类型
		      assObjValue(di);   //组装起始数据值
		   //结束值
		      di.setDataType(type); 
		      di.setInputParam(r.getEndData());
		      assObjDataType(di);//组装数据类型
		      assObjValue(di);   //组装结束数据值
		 }
		 di  = null;
	}
	
	//设置对象属性时，拼接对象属性数据类型
    private    void    assObjDataType(DataItem698 dataItem698){
     
   	 bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(dataItem698.getDataType()))))[0];
    }
	
    //设置对象属性时，拼接对象属性值
    private   void     assObjValue(DataItem698 dataItem698){
   	 IPharesItem698   phares = Constant698.getiPharesItem698(dataItem698.getDataType());
   	 phares.setInputValue(dataItem698.getInputParam());
   	 byte []   value  =  phares.getBytes();
   	 System.arraycopy(value, 0, bytes, pos, value.length);
   	 pos = pos  +  value.length;  
    }
    
	//组装对象属性描述符【OAD】  -------对象标识、属性标识及其特征、属性内元素索引
    private     void   assObjData(DataItem698 dataItem698){
   	 byte []   objId =  CommUtils.hex2Binary(dataItem698.getObjIdent()); 
	     System.arraycopy(objId, 0, bytes, pos, objId.length);  ////对象标识
	     pos =pos+ objId.length;
	     bytes[pos++] = getParam(!"".equals(dataItem698.getObjFeatures())&&dataItem698.getObjFeatures()!=null? Integer.toHexString(Integer.parseInt(dataItem698.getObjFeatures())):"00",  Integer.toHexString(Integer.parseInt(dataItem698.getMemberCode()))); //特征(5--7) ------属性编号(0---4)
	     bytes[pos++] =!"".equals(dataItem698.getElementIndex())&& dataItem698.getElementIndex()!=null ? CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(dataItem698.getElementIndex())))[0] : 00; //属性内元素索引
    }
	
    //返回属性标识  及其特征
    private   static   byte   getParam(String fea, String param){
   	 byte    b = (byte) (((CommUtils.hex2Binary(fea)[0]<<5)&0xE0)|(CommUtils.hex2Binary(param)[0]&0x1F));
   	 return  b;
    }
	
	//根据传入的字节数组 和  长度  获取字节数组中的有效数据
	public  static   byte[]  getByteData(byte [] bytes ,  int  len){
		  byte  []  data =  new  byte[len];
		  for(int  i=0; i<len; i++){
			  data[i] = bytes[i];
		  }
		  return  data;
	}
	
	//传递的参数param  的格式类型：  数据类型|数据：数据类型|数据：数据类型|数据......     
	/***
	 * 传递的参数param  的格式类型：  数据类型|数据：数据类型|数据：数据类型|数据......  一般格式  
	 * 如果数据类型中包含多个值 eg: TI (单位、间隔时间)   TI|0#10   
	 * eg: structure | 数据类型#数据#数据类型#数据...... 
	 * 如果 structure 类型中包含 structure 或 array 的数据类型 则格式  eg: 数据类型#数据#数据类型#数据#【structure/array】#数据类型*数据
	 * structure{
	 *    数据类型   值；
	 *    数据类型   值；
	 *    structure{
	 *    }
	 *    structure{
	 *    }
	 *    array{
	 *    }
	 *    array{
	 *    }
	 *    数据类型  值；
	 * }
	 * 
	 * 
	 * eg: array |数据类型#数据#数据类型#数据......
	 *  
	 */
	public  static   byte[]   assMethodStru(String param, String type){
		byte  []   mid = new  byte[1024];  //存储 数据
		String[]   value = param.split(":");  //根据 ":" 将数据分割成数组，  数组长度就是 structure 结构体的元素个数
		String[]   temp =  new String[2];  //存储 数据类型 | 数据值
		int  pos  = 1;   // 游离下标
		boolean  flag = false;
		//组装  structure 元素个数 
		mid[0] = CommUtils.hex2Binary(Integer.toHexString(value.length))[0];
		//组装数据
		for(String p : value){
			byte  []   tv ;  //存储组装的数据类型数值
		    temp = p.split("\\|");	
	 	    if(temp[0].contains("SID_MAC")||temp[0].contains("sid_mac")){
		    	 mid[0] = CommUtils.hex2Binary(Integer.toHexString(value.length-1))[0];
		    	 temp[0] = "SID";
		    	 flag =true;
		    }
		    IPharesItem698  phare =  Constant698.getiPharesItem698(temp[0]);   //根据 数据类型 获取对应的 数据类型 组装类
		    phare.setInputValue(temp[1]);   //数据值
		    //组装数据类型
		    if(flag){
		    	mid[pos++] = assDataType("SID_MAC");
		    	flag =false;
		    }
		    mid[pos++] = assDataType(temp[0]);
		    //组装数据
		    tv = phare.getBytes();
		    System.arraycopy(tv, 0, mid, pos, tv.length);
		    pos += tv.length;
		    tv = null;
		}
		return  reAssValue(pos , mid);
	}

	
	
	public  static  byte  assDataType(String type){
		return     CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(type))))[0];
	}
	/***
	 * 根据传入的元素个数、 字节数组（包含一些无用数据） 返回字节数组中 有用数据值
	 * count   元素个数
	 * bytes   字节数组       
	 */
	public  static   byte[]    reAssValue(int  count,  byte []  bytes){
		      byte  []    reValue = new byte[count];
		      for(int i=0; i<count; i++){
		    	 reValue[i] = bytes[i];
		      }
		      return  reValue;
	}
	
	
	//根据传递的数据类型获取到数据类型对应的字节数据
	public   static   byte   getDataTypeByte(String type){
		return    CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(type))))[0];
	}
	
	
	public  static String getTypeData(String value){
		  String revalue="";
		  for(Map.Entry<String, String>  entry :  Constant698.DATA_TYPE_MAP.entrySet()){
			   if(value.equals(entry.getValue())){
				   revalue =  entry.getKey();
				   break;
			   }
		  }
		  return   revalue;
	}
}
