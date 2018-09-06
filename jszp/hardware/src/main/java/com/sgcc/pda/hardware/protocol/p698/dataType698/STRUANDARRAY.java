package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.structure.StruUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

import java.util.Arrays;

public class STRUANDARRAY  extends IPharesItem698 {
	byte  []   value = new byte[1024];
	static int  pos = 1; 
	@Override
	public String getResStr() {
		// TODO Auto-generated method stub
		return null;
	}

	/****
	 * structure 或array 传递的数据格式----------
	 * 数据类型*数据#数据类型*数据#structure*数据类型&数据^数据类型&数据^数据类型&数据^structure【array】&数据类型=数据==数据类型=数据==数据类型=数据#array*数据类型&数据^数据类型&数据^数据类型&数据.........
	 */
	//@Override
	public byte[] getBytes() {
		String[]  param = this.getInputValue().split("#");  //数组长度表示structure 或array  的元素个数
		String[]  temp;
		value[0] = CommUtils.hex2Binary(Integer.toHexString(param.length))[0];  //structure 或array  的元素个数
		for(String p :  param){
			byte  []   tv ;  //存储组装的数据类型数值
			temp = p.split("\\*");  //temp[0] 表示数据类型    temp[1] 表示数据
			  //组装数据类型
			 value[pos++] = StruUtils.assDataType(temp[0]);
			 if("structure".equals(temp[0])||"array".equals(temp[0])){
				  String tempvalue ="";
				  if(temp[1].contains("&")&&temp[1].contains("^")){
					  tempvalue = temp[1].replace("&", "*");
					  tempvalue = tempvalue.replace("^", "#");
				  }else{
					  if(temp[1].contains("=")&&temp[1].contains("==")){
						  tempvalue = temp[1].replace("==", "#");
						  tempvalue = tempvalue.replace("=", "*");
					  }else{
						  tempvalue = temp[1].replace("=", "*");
					  }
				  }
				 
				  byte []  temvalue  =assStrArr(tempvalue);
				  System.arraycopy(temvalue, 0, value, pos, temvalue.length);
				  pos += temvalue.length;
			 }else{
				 //组装数据
				 IPharesItem698  phare =  Constant698.getiPharesItem698(temp[0]);   //根据 数据类型 获取对应的 数据类型 组装类
				 phare.setInputValue(temp[1]);   //数据值
				 tv = phare.getBytes();
				 System.arraycopy(tv, 0, value, pos, tv.length);
				 pos += tv.length;
				 tv = null;
			 }
			 
		}
		byte  []   revalue =StruUtils.reAssValue(pos , value);
		Arrays.fill(value, (byte)0);
		pos = 1;
		return  revalue;
	}
	
	
	 private    byte []   assStrArr(String tempvalue){
		 String[]  param = tempvalue.split("#");  //数组长度表示structure 或array  的元素个数
		 byte  []   temp  =  new  byte[1024];
		 String[]   v = new String[2];
		 temp[0] = CommUtils.hex2Binary(Integer.toHexString(param.length))[0];  //structure 或array  的元素个数
		 int  pos = 1;
		 for(String p :  param){
				     byte  []   tv ;  //存储组装的数据类型数值
				     v = p.split("\\*");
				   //组装数据类型
					 temp[pos++] = StruUtils.assDataType(v[0]);
					 if("structure".equals(v[0])||"array".equals(v[0])){
						 byte  []   retemp = assStrArrOne(v[1]);
						 pos  =  retemp.length;
						 System.arraycopy(retemp, 0, temp, 0, pos);
					 }else{
						 IPharesItem698  phare =  Constant698.getiPharesItem698(v[0]);   //根据 数据类型 获取对应的 数据类型 组装类
						 phare.setInputValue(v[1]);   //数据值
						 tv = phare.getBytes();
						 System.arraycopy(tv, 0, temp, pos, tv.length);
						 pos += tv.length;
						 tv = null;
					 }
			}
		 return  StruUtils.reAssValue(pos , temp);
	 }
	
	 
	 private    byte []   assStrArrOne(String tempvalue){
		 String[]  param = tempvalue.split("#");  //数组长度表示structure 或array  的元素个数
		 byte  []   temp  =  new  byte[1024];
		 String[]   v = new String[2];
		 temp[0] = CommUtils.hex2Binary(Integer.toHexString(param.length))[0];  //structure 或array  的元素个数
		 int  pos = 1;
		 for(String p :  param){
				     byte  []   tv ;  //存储组装的数据类型数值
				     v = p.split("\\*");
				   //组装数据类型
					 temp[pos++] = StruUtils.assDataType(v[0]);
						 IPharesItem698  phare =  Constant698.getiPharesItem698(v[0]);   //根据 数据类型 获取对应的 数据类型 组装类
						 phare.setInputValue(v[1]);   //数据值
						 tv = phare.getBytes();
						 System.arraycopy(tv, 0, temp, pos, tv.length);
						 pos += tv.length;
						 tv = null;
			}
		 return  StruUtils.reAssValue(pos , temp);
	 }
}
