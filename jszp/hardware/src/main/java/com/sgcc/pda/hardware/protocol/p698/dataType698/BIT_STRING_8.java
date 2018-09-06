package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

public class BIT_STRING_8 extends IPharesItem698 {

	@Override
	public String getResStr() {
		return CommUtils.bytesToHexString(this.getUpbyte());
	}
	
	@Override
	public byte[] getBytes(){
		String valueOf = this.getInputValue();
		if(valueOf.length() == 2){
			valueOf = "1"+valueOf;
			byte[] bytes = CommUtils.hex2Binary(valueOf);
			return bytes;
		}else if(valueOf.length()<16){
			String stringValue = Utils.addType(valueOf, 16);
			stringValue = "1"+stringValue;
			byte[] bytes = CommUtils.hex2Binary(stringValue);
			return bytes;
		}
		throw new RuntimeException("IPharesItem698数据格式解析类：[" + this.getClass().getName() + "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
	}
	
	public static void main(String[] args) {
		//8 0 0 0 0 0 0 17
		/*BIT_STRING_8 b = new BIT_STRING_8();
		String s="06";
		byte[] bytes = b.getBytes(s);
		System.out.println(bytes);*/
		/*byte [] bytes = {0x,0,0,0,0,0,17,-102};
		String bytesToHexString = CommUtils.bytesToHexString(bytes);
		Integer parseInt = Integer.parseInt(bytesToHexString, 16);
		System.out.println(parseInt);*/
	}
	
}
