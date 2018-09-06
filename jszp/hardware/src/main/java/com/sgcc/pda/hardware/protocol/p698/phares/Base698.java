package com.sgcc.pda.hardware.protocol.p698.phares;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

//通用数据类型
public class Base698  extends IPharesItem698 {

	@Override
	public String getResStr() {
		
		return  CommUtils.byteToHexString(this.getUpbyte());
		
	}

	
	@Override
	public   byte[] getBytes(){
	   byte  []  data=	CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(this.getInputValue())));    //将输入参数值转换成字节数组
	   //定义返回数据的数组
	   byte  []   value =  new  byte[data.length+1];   //+1  保存数据长度
	   value[0] =   CommUtils.hex2Binary(Integer.toHexString(data.length))[0] ;	  //数据长度
	   System.arraycopy(data, 0, value, 1, data.length);
	   return   value;
	}
}
