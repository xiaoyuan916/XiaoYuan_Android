package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.structure.StruUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

public class COMDCB extends IPharesItem698 {

	@Override
	public String getResStr() {
		// TODO Auto-generated method stub
		return null;
	}
    //格式：数据#数据#数据#数据#数据
	@Override
	public byte[] getBytes() {
		    try {
		    	int  pos=0;
		    	String[]   value =   this.getInputValue().split("#");
		    	byte  []   temp  = new  byte[10];
		    	for(String v :  value){
		    		 //组装enum 数据类型
		    		 temp[pos] = StruUtils.getDataTypeByte("enum");
		    		 pos +=1;  // 下标定位到 数据开始的位置  
		    		 //组装数据
		    		 temp[pos] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(v)))[0];
		    		 pos +=1;     //下标定位到下一个 enum 位置
		    	}
				
			    return    temp;
		    } catch (Exception e) {
				throw new RuntimeException("IPharesItem698数据格式解析类：["
						+ this.getClass().getName()
						+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		   }
	}
}
