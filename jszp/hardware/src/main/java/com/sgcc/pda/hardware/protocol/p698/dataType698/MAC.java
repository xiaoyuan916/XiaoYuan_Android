package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

public class MAC extends IPharesItem698 {

	@Override
	public String getResStr() {
		byte[] bytes = this.getUpbyte();
		return String.valueOf(Integer.parseInt(CommUtils
				.bytesToHexString(bytes), 16));
	}
	
	@Override
	public byte[] getBytes(){
		try {
			byte[] byteLen = CommUtils.hex2Binary( this.getInputValue().replace(".", ""));
			byte[] bytes= new byte[byteLen.length+1];
			bytes[0] = CommUtils.hex2Binary(Integer.toHexString(byteLen.length))[0];
			System.arraycopy(byteLen, 0, bytes, 1, byteLen.length);
			return bytes;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}
}
