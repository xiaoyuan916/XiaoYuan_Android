package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

public class OI extends IPharesItem698 {

	@Override
	public String getResStr() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public    byte []   getBytes(){
		  try{
				byte  []   oi  =  new byte[2];
				byte  []  value = CommUtils.hex2Binary(this.getInputValue());
				System.arraycopy(value, 0, oi, 0, 2);
				return  oi;
			} catch (Exception e) {
				throw new RuntimeException("IPharesItem698数据格式解析类：["
						+ this.getClass().getName()
						+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
			}
	}

}
