package com.sgcc.pda.hardware.protocol.p698;


import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.dataType698.Utils;
import com.sgcc.pda.hardware.protocol.p698.resultBean698.COMDCB;
import com.sgcc.pda.hardware.protocol.p698.resultBean698.CSD;
import com.sgcc.pda.hardware.protocol.p698.resultBean698.MS;
import com.sgcc.pda.hardware.protocol.p698.resultBean698.OAD;
import com.sgcc.pda.hardware.protocol.p698.resultBean698.OMD;
import com.sgcc.pda.hardware.protocol.p698.resultBean698.RCSD;
import com.sgcc.pda.hardware.protocol.p698.resultBean698.ROAD;
import com.sgcc.pda.hardware.protocol.p698.resultBean698.RSD;
import com.sgcc.pda.hardware.protocol.p698.resultBean698.Region;
import com.sgcc.pda.hardware.protocol.p698.resultBean698.SID;
import com.sgcc.pda.hardware.protocol.p698.resultBean698.TI;
import com.sgcc.pda.hardware.protocol.p698.resultBean698.TSA;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.Constant;
import com.sgcc.pda.sdk.utils.LogUtil;

/**
 * @author  ZXS 
 * 解析上行数据单元
 */
public class ParserUpdata {

	
	/**
	 * 处理上行报文中除Structure和array之外的数据单元
	 * @param bytes
	 * @param pos
	 * @return
	 */
	public static int handlerCommUnit(byte[] bytes, int pos, StringBuffer sb, String userCodeType, DataItem698 dataItem) {
		byte  []  byte1 ; //存储内循环数据
		int dataTypeLen = Constant698.DATA_TYPE_GET_LEGTH.get(userCodeType) == null ? 0
				: Integer.parseInt(Constant698.DATA_TYPE_GET_LEGTH.get(userCodeType));
		if (dataTypeLen != 0) {//普通类型数据处理
			byte1 =new  byte[dataTypeLen];
			System.arraycopy(bytes, pos, byte1, 0, dataTypeLen); //pos 位置在数据开始的位置
//			if(null!=Sp698ProtoUtil.conv&&Sp698ProtoUtil.conv.size()>0){
//				String  conv = Sp698ProtoUtil.conv.get(dataItem.getObjIdent()+dataItem.getMemberCode()+dataItem.getObjFeatures()+dataItem.getElementIndex());
//				if(!"0".equals(conv) && null!=conv){
//					userCodeType = userCodeType+"_"+conv;  //根据换算值  获取到对应类  解析报文
//				}
//			}
			IPharesItem698 phares = Constant698.getiPharesItem698(userCodeType); // +"_"+conv  根据成员数据类型 + 该成员对应的换算值 获取数据类型解析类
	      	phares.setUpbyte(byte1);
	      	sb.append("|"+phares.getResStr());
	      	pos += dataTypeLen;   //将pos定位到下一个对象标识的开始位置  +1 数据类型
		} else if (dataTypeLen == 0) {
			if ("enum".equals(userCodeType)) {// 枚举
				dataTypeLen = 1;
				int choice=bytes[pos];
				sb.append("|"+choice);
				pos += dataTypeLen;
			} else if ("OI".equals(userCodeType)) { // OI类型
				dataTypeLen = 2;//OI类型同long-unsigned,长度为16位，两个字节
				byte1 = new byte[dataTypeLen];
				System.arraycopy(bytes, pos, byte1, 0, dataTypeLen);
				String hexString = CommUtils.byteToHexString(byte1);
				sb.append("|"+ Integer.valueOf(hexString, 16).toString());
				pos += dataTypeLen;
			}else if("bit-string".equals(userCodeType)) { // bit-string类型
				dataTypeLen = bytes[pos++];//获取长度
				byte1 = new byte[dataTypeLen];
		    	System.arraycopy(bytes, pos, byte1, 0, dataTypeLen);
		    	StringBuffer strBuffer=new StringBuffer();
		    	for(int k=0;k<dataTypeLen;k++){
		    		String str= Integer.toBinaryString(byte1[k]);
		    		str= Utils.judgeLen(str, 8);
		    		strBuffer.append(str);
		    	}
		    	sb.append("|"+strBuffer.toString());
    		    pos += dataTypeLen;   //将pos定位到下一个对象标识的开始位置  +1 数据类型
			}else if("OAD".equals(userCodeType)){//OAD类型
				OAD oad=new OAD();
				dataTypeLen=oad.getTotalLength();
				byte1 = new byte[dataTypeLen];
				System.arraycopy(bytes, pos, byte1, 0, dataTypeLen);
				oad.valuAtion(byte1);
				sb.append("|"+oad.toString());
				pos += dataTypeLen;
			}else if ("NULL".equals(userCodeType)){//NULL类型
				sb.append("|"+"null");
			}else if ("TSA".equals(userCodeType)){//TSA类型
				TSA tsa=new TSA();
				tsa.setTotalLength(new Integer(bytes[pos])+1);//带定义长度的那个字节，为总长度
				byte1=new byte[tsa.getTotalLength()];
				System.arraycopy(bytes, pos, byte1, 0, byte1.length);
				tsa.valuAtion(byte1);
				sb.append("|"+tsa.toString());
				dataTypeLen=tsa.getTotalLength();
				pos += dataTypeLen;
			}else if ("bool".equals(userCodeType)){//bool类型
				dataTypeLen = 1;
				int value=bytes[pos];
				sb.append("|"+value);
				pos += dataTypeLen;
			}else if ("TI".equals(userCodeType)){//TI类型
				TI ti=new TI();
				dataTypeLen=ti.getTotalLength();
				byte1=new byte[dataTypeLen];
				System.arraycopy(bytes, pos, byte1, 0, dataTypeLen);
				ti.valuAtion(byte1);
				if(ti.getValue()==0){
					sb.append("|"+ti.getValue());
				}else {
					sb.append("|"+ti.toString());
				}
				pos += dataTypeLen;
			}else if("OMD".equals(userCodeType)){//OMD类型
				OMD omd=new OMD();
				dataTypeLen=omd.getTotalLength();
				byte1 = new byte[dataTypeLen];
				System.arraycopy(bytes, pos, byte1, 0, dataTypeLen);
				omd.valuAtion(byte1);
				sb.append("|"+omd.toString());
				pos += dataTypeLen;
			}else if("CSD".equals(userCodeType)){//CSD类型
				dataTypeLen = 1;
				CSD csd=new CSD();
				csd.valuAtion(bytes[pos]);
				sb.append("|"+csd.toString());
				pos += dataTypeLen;
			}else if("ROAD".equals(userCodeType)){//ROAD类型
				int size=bytes[pos+4];
				ROAD road=new ROAD();
				road.getTotalLengthByByte(size);
				byte1=new byte[size];
				System.arraycopy(bytes, pos, byte1, 0, byte1.length);
				road.valuAtion(byte1);
				sb.append("|"+road.toString());
				dataTypeLen = road.getTotalLength();
				pos += dataTypeLen;
			}else if("RCSD".equals(userCodeType)){//RCSD类型
				RCSD rcsd =new RCSD();
				rcsd.setTotalLengthByByte(bytes[pos]);
				byte1=new byte[rcsd.getTotalLength()];
				System.arraycopy(bytes, pos, byte1, 0, byte1.length);
				rcsd.valuAtion(byte1);
				sb.append("|"+rcsd.toString());
				dataTypeLen = rcsd.getTotalLength();
				pos += dataTypeLen;
			}else if("MS".equals(userCodeType)){//MS类型
				MS ms=new MS();//MS类型
				ms.valuAtion(pos, bytes);
				sb.append("|"+ms.toString());
				dataTypeLen = ms.getTotalLength();
				pos += dataTypeLen;
			}else if("octet-string".equals(userCodeType)||"MAC".equals(userCodeType)||"RN".equals(userCodeType)){//octet-string类型
				dataTypeLen = bytes[pos++];
				byte1 = new byte[dataTypeLen];
		    	System.arraycopy(bytes, pos, byte1, 0, dataTypeLen);
		    	sb.append("|"+CommUtils.byteToHexString(byte1));
    		    pos += dataTypeLen;
			}else if("RSD".equals(userCodeType)){//RSD
		    	pos = new RSD().valuAtion(pos,bytes,sb,dataItem);
			}else if("visible-string".equals(userCodeType)){//visible-string
				dataTypeLen = bytes[pos++];
				byte1 = new byte[dataTypeLen];
		    	System.arraycopy(bytes, pos, byte1, 0, dataTypeLen);
		    	sb.append("|"+Utils.bytesToAsciiStr(byte1));
		    	pos += dataTypeLen;
			}else if("Region".equals(userCodeType)){//Region
				pos = new Region().valuAtion(pos,bytes,sb, dataItem);
			}else if("Scaler_Unit".equals(userCodeType)){//Scaler_Unit
				int value = bytes[pos++];
				int key=bytes[pos++];
				sb.append("|"+value+" "+key);
			}else if("SID".equals(userCodeType)){//SID
		    	SID sid=new SID();
		    	sid.valuAtion(bytes, pos);
		    	sb.append("|"+sid.toString());
				pos+=sid.getTotalLength();
			}else if("SID_MAC".equals(userCodeType)){//SID_MAC---由SID、MAC组成
		    	//解析SID
				SID sid=new SID();
		    	sid.valuAtion(bytes, pos);
		    	pos+=sid.getTotalLength();
		    	//解析MAC
		    	dataTypeLen = bytes[pos++];
				byte1 = new byte[dataTypeLen];
		    	System.arraycopy(bytes, pos, byte1, 0, dataTypeLen);
		    	sb.append("|"+sid.toString()+" "+CommUtils.byteToHexString(byte1));
				pos+=dataTypeLen;
			}else if("COMDCB".equals(userCodeType)){//COMDCB
		    	COMDCB comdcm=new COMDCB();
		    	dataTypeLen=comdcm.getTotalLength();
		    	byte1=new byte[dataTypeLen];
		    	System.arraycopy(bytes, pos, byte1, 0, byte1.length);
		    	comdcm.valuAtion(byte1);
		    	sb.append("|"+comdcm.toString());
				pos+=dataTypeLen;
			}else if("UTF8-string".equals(userCodeType)){//UTF8-string
		    	//TODO
			}
			
		}
		return pos;
	}
	
	
	
	/**
	 * 处理Structure或Array类型数据,最大的外层单元加了结束符#
	 * @param bytes
	 * @param data
	 * @param pos
	 * @return
	 */
	public static int handlerStructureOrArrayUnit(byte[] bytes, int data, Integer pos, StringBuffer sb, DataItem698  dataItem698) {
		for (int i = 0; i < data; i++) {
			if(i!=data-1){
				String userCodeType = new Parser698().userCodeType(bytes[pos++]);
				if("structure".equals(userCodeType)||"array".equals(userCodeType)){
					int num = bytes[pos++];// 获取Structure数据单元的个数
					if(num>0){
						pos=handlerInner(bytes,num, pos,sb,dataItem698);
					}
				}else {
					pos=handlerCommUnit(bytes,pos,sb,userCodeType, dataItem698);
				}
				sb.append("#");
			}else{
				String userCodeType = new Parser698().userCodeType(bytes[pos++]);
				if("structure".equals(userCodeType)||"array".equals(userCodeType)){
					int num = bytes[pos++];// 获取Structure数据单元的个数
					if(num>0){
						pos=handlerInner(bytes,num, pos,sb, dataItem698);
					}
				}else {
					pos=handlerCommUnit(bytes,pos,sb,userCodeType, dataItem698);
				}
			}
			
		}
		return pos;
	}
	/**
	 * 处理Structure或Array类型数据(内嵌)
	 * @param bytes
	 * @param data
	 * @param pos
	 * @return
	 */
	public static int handlerInner(byte[] bytes, int data, Integer pos, StringBuffer sb, DataItem698 dataItem698) {
		for (int i = 0; i < data; i++) {
			String userCodeType = new Parser698().userCodeType(bytes[pos++]);
			if("structure".equals(userCodeType)||"array".equals(userCodeType)){
				int num = bytes[pos++];// 获取Structure数据单元的个数
				if(num>0){
					pos=handlerInner(bytes,num, pos,sb,dataItem698);
				}
			}else {
				pos=handlerCommUnit(bytes,pos,sb,userCodeType, dataItem698);
			}
			
		}
		return pos;
	}
}
