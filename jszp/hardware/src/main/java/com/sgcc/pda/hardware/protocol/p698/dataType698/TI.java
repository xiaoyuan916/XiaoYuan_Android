package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.structure.StruUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

//时间间隔
public class TI extends IPharesItem698 {

	@Override
	public String getResStr() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public byte[] getBytes() {
		try{
			  String[]   up = new String[2];
			  up = this.getInputValue().split("#");
			  if(null==up[1]||"".equals(up[1])){
				  up[0]= this.getInputValue().substring(0, 1);
				  up[1]= this.getInputValue().substring(1);
			  }
			  byte  []  value  = new  byte[5];
			  //enum  的数据类型
			  value[0]= StruUtils.assDataType("enum");
			  // 间隔单位值 
			  if(!up[0].contains("*")){
				  value[1] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(up[0])))[0];
			  }else{
				  value[1] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(up[0].split("\\*")[1])))[0];
			  } 
			  //间隔值数据类型
			  value[2]= StruUtils.assDataType("long-unsigned");
			  //间隔值
			  String hex="";
			  if(!up[1].contains("*")){
				  hex=  Integer.toHexString(Integer.parseInt(up[1]));
			  }else{
				  hex=  Integer.toHexString(Integer.parseInt(up[1].split("\\*")[1]));
			  }
			  if(hex.length()<4){
				  hex  = "0000".substring(0, 4-hex.length())+hex;
			  }
			  System.arraycopy(CommUtils.hex2Binary(hex), 0, value, 3, 2);
			  return  value;
		 } catch (Exception e) {
		throw new RuntimeException("IPharesItem698数据格式解析类：["
				+ this.getClass().getName()
				+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
	    }
		  
	}

}
