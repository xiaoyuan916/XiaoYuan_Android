package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.structure.StruUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

public class TSA extends IPharesItem698 {

	@Override
	public String getResStr() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public  byte []  getBytes(){
		try{ 
			//格式 ： 所有数据长度 +  地址数据长度 + 地址数据
			byte []  value =  new  byte[19];   //定义TSA 规定的最大长度 17    +1 所有数据长度   +1地址数据长度
			byte  []  add= CommUtils.hex2Binary(this.getInputValue());
			value[0] = CommUtils.hex2Binary(Integer.toHexString(add.length+1))[0];//所有数据长度
		    value[1] = CommUtils.hex2Binary(Integer.toHexString(add.length-1))[0];//地址数据长度
		    System.arraycopy(add, 0, value, 2, add.length);
			return StruUtils.reAssValue(add.length+2, value);
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}
}
