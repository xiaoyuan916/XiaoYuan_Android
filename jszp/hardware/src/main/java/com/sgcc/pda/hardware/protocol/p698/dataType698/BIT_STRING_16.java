package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

public class BIT_STRING_16 extends IPharesItem698 {
	
	@Override
	public String getResStr() {
		return CommUtils.bytesToHexString(this.getUpbyte());
	}
	
	@Override
	public byte[] getBytes(){
		String valueOf = this.getInputValue();
		if(valueOf.length() == 4){
			valueOf = "2"+valueOf;
			byte[] bytes = CommUtils.hex2Binary(valueOf);
			return bytes;
		}else if(valueOf.length()<4){
			String stringValue = Utils.addType(valueOf, 2);
			stringValue = "2"+stringValue;
			byte[] bytes = CommUtils.hex2Binary(stringValue);
			return bytes;
		}
		throw new RuntimeException("IPharesItem698数据格式解析类：[" + this.getClass().getName() + "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
	}

}
