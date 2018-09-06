package com.sgcc.pda.hardware.protocol.p698.cache698;



import com.sgcc.pda.hardware.protocol.p698.utils.DBUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class DataItemCache698 {
	
	   /***
	    * 为698报文解析组装类使用，  key  为对象标识+属性编号    value 为 换算 值
	    * 
	    */
	private  static Map<String,String> dataItem698Map =  new HashMap<String,String>();
	
	//查询数据库中所有698数据项，用于缓存供组装解析698使用
	
	private  static  final String ITEM_SQL ="  SELECT   SP.OBJ_IDENT, SP.MEMBER_TYPE, SP.MEMBER_CODE, SP.CONVERSION   FROM   SP_698_DATAITEM   SP   WHERE   SP.MEMBERTYPE = '0'  AND  SP.IS_USER='1' ";
	
	static {
		init698Cache();
	}

	/**
	 * 根据对象标识 和属性编号    获取对应的换算值    返回   换算值    
	 */
	public   static String getConversion(String objIdent){
		  String conv  =   dataItem698Map.get(objIdent);
		  if(conv==null){
			  if(dataItem698Map.isEmpty()||dataItem698Map.size()==0||dataItem698Map.entrySet()==null){
				 // LogHandler.info(LogHandler.DEVICE, "【698数据解析】未在缓存中找到编号【" + objIdent + "】的相关数据项信息,重新初始化缓存！");
				  init698Cache();
				  conv =   dataItem698Map.get(objIdent);
			  }else{
				//  LogHandler.error(LogHandler.DEVICE, "【698数据解析】数据项编号【" + objIdent + "】未在系统中找到对应的配置信息,请核实！");
				  return null;
			  }
		  }
		return  conv;
	}
	
	
	
	
	private static void init698Cache() {
		try{
		//	LogHandler.info(LogHandler.DEVICE, "【接口服务】初始化698协议数据项缓存信息");
			dataItem698Map.clear();
			List<Map> itemList  = DBUtils.queryBySQLRetMap(ITEM_SQL);
			for(Map item: itemList){
				//对象标识_成员类型_成员编号，  换算值
				dataItem698Map.put(item.get("OBJ_IDENT")+"_"+item.get("MEMBER_TYPE")+"_"+item.get("MEMBERCODE"), item.get("CONVERSION").toString().replace("-", ""));
			}
			//LogHandler.info(LogHandler.DEVICE, "【接口服务】698协议数据项缓存初始化完成");
		}catch(Exception e){
			//  LogHandler.error(LogHandler.DEVICE,"初始化698协议数据项缓存信息异常", e);
		}
		
	}
	

}
