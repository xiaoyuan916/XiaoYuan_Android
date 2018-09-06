package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

public  class  ENUM  extends IPharesItem698 {

	@Override
	public String getResStr() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public    byte []   getBytes(){
		try{
			byte  []   en =  new byte[1];
			byte  []  value = CommUtils.hex2Binary(this.getInputValue());
			System.arraycopy(value, 0, en, 0, 1);
			return  en;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}

}
